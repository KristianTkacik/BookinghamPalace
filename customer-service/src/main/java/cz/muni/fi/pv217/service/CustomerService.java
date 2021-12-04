package cz.muni.fi.pv217.service;

import cz.muni.fi.pv217.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pv217.dto.CustomerCreateDTO;
import cz.muni.fi.pv217.dto.CustomerUpdateDTO;
import cz.muni.fi.pv217.entity.Customer;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class CustomerService {

    @Transactional
    public Customer createCustomer(CustomerCreateDTO createDTO) {
        Customer customer = new Customer();
        customer.name = createDTO.name;
        customer.email = createDTO.email;
        customer.passwordHash = BcryptUtil.bcryptHash(createDTO.password, 4);
        customer.persist();
        return customer;
    }

    @Transactional
    public Customer updateCustomer(long id, CustomerUpdateDTO updateDTO) {
        Customer customer = Customer.findById(id);

        if (customer == null) {
            throw new NotFoundException(String.format("Customer with id %d not found", id));
        }
        Customer update = new Customer();
        update.name = updateDTO.name;
        update.email = updateDTO.email;

        customer.merge(update);
        customer.persist();
        return customer;
    }

    @Transactional
    public Customer deleteCustomer(long id) {
        Customer customer = Customer.findById(id);

        if (customer == null) {
            throw new NotFoundException(String.format("Customer with id %d not found", id));
        }

        boolean deleted = Customer.deleteById(id);
        return deleted ? customer : null;
    }

    @Transactional
    public Customer authenticateCustomer(CustomerAuthenticateDTO authenticateDTO) {
        Customer customer = Customer.find("email", authenticateDTO.email).firstResult();
        if (customer == null) {
            throw new NotFoundException(String.format("Customer with email %s not found", authenticateDTO.email));
        }
        if (BcryptUtil.matches(authenticateDTO.password, customer.passwordHash)) {
            return customer;
        }
        return null;
    }
}

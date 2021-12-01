package cz.muni.fi.pv217.service;

import cz.muni.fi.pv217.entity.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class CustomerService {

    @Transactional
    public Customer createCustomer(Customer customer) {
        customer.persist();
        return customer;
    }

    @Transactional
    public Customer updateCustomer(long id, Customer update) {
        Customer customer = Customer.findById(id);

        if (customer == null) {
            throw new NotFoundException(String.format("Customer with id %d not found", id));
        }

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
}

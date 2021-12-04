package cz.muni.fi.pv217;

import cz.muni.fi.pv217.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pv217.dto.CustomerCreateDTO;
import cz.muni.fi.pv217.dto.CustomerUpdateDTO;
import cz.muni.fi.pv217.entity.Customer;
import cz.muni.fi.pv217.service.CustomerService;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashSet;
import java.util.List;

@Path("/customer")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    CustomerService customerService;

    @GET
    @PermitAll
    @Counted(name = "customer.getAll.counter")
    @Timed(name = "customer.getAll.timer")
    public List<Customer> getCustomers() {
        return Customer.listAll();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Counted(name = "customer.getOne.counter")
    @Timed(name = "customer.getOne.timer")
    public Response getCustomer(@PathParam long id) {
        Customer customer = Customer.findById(id);
        if (customer == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(String.format("Customer for id %d not found.", id))
                    .build();
        }
        return Response.ok(customer).build();
    }

    @POST
    @Path("/create")
    @PermitAll
    @Counted(name = "customer.create.counter")
    @Timed(name = "customer.create.timer")
    public Response createCustomer(CustomerCreateDTO createDTO) {
        Customer created;
        try {
            created = customerService.createCustomer(createDTO);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/update")
    @RolesAllowed("Customer")
    @Counted(name = "customer.update.counter")
    @Timed(name = "customer.update.timer")
    public Customer updateCustomer(CustomerUpdateDTO updateDTO) {
        return customerService.updateCustomer(Long.parseLong(jwt.getClaim("id").toString()), updateDTO);
    }

    @DELETE
    @Path("/delete")
    @RolesAllowed("Customer")
    @Counted(name = "customer.delete.counter")
    @Timed(name = "customer.delete.timer")
    public Response deleteCustomer() {
        Customer deleted = customerService.deleteCustomer(Long.parseLong(jwt.getClaim("id").toString()));
        return Response.ok(deleted).build();
    }

    @POST
    @Path("/login")
    @PermitAll
    @Counted(name = "customer.login.counter")
    @Timed(name = "customer.login.timer")
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginCustomer(CustomerAuthenticateDTO authenticateDTO) {
        Customer customer = customerService.authenticateCustomer(authenticateDTO);
        if (customer == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String token = generateToken(customer);
        return Response.ok(token).build();
    }

    private String generateToken(Customer customer) {
        return Jwt.issuer("https://example.com/issuer")
                .subject(customer.email)
                .claim("id", customer.id)
                .claim(Claims.email.name(), customer.email)
                .groups(new HashSet<>(List.of("Customer")))
                .sign();
    }
}

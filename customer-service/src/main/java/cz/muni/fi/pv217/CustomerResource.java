package cz.muni.fi.pv217;

import cz.muni.fi.pv217.entity.Customer;
import cz.muni.fi.pv217.service.CustomerService;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customer")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @GET
    @Counted(name = "customer.getAll.counter")
    @Timed(name = "customer.getAll.timer")
    public List<Customer> getCustomers() {
        return Customer.listAll();
    }

    @GET
    @Path("/{id}")
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
    @Counted(name = "customer.create.counter")
    @Timed(name = "customer.create.timer")
    public Response createCustomer(Customer customer) {
        Customer created = customerService.createCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/update")
    @Counted(name = "customer.update.counter")
    @Timed(name = "customer.update.timer")
    public Customer updateCustomer(@PathParam long id, Customer update) {
        return customerService.updateCustomer(id, update);
    }

    @DELETE
    @Path("/{id}/delete")
    @Counted(name = "customer.delete.counter")
    @Timed(name = "customer.delete.timer")
    public Response deleteAvenger(@PathParam long id) {
        Customer deleted = customerService.deleteCustomer(id);
        return Response.ok(deleted).build();
    }
}

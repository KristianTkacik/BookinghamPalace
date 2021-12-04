package cz.muni.fi.pv217.client;

import cz.muni.fi.pv217.entity.Customer;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customer")
@RegisterRestClient(configKey = "customer-service-client")
public interface CustomerServiceClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Customer getCustomer(@PathParam long id);
}

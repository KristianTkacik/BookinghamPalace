package cz.muni.fi.pv217.client;

import cz.muni.fi.pv217.entity.Book;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/book")
@RegisterRestClient(configKey = "catalog-service-client")
public interface CatalogServiceClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Book getBook(@PathParam long id);
}

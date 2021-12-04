package cz.muni.fi.pv217;

import cz.muni.fi.pv217.Entity.Author;
import cz.muni.fi.pv217.service.AuthorService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/author")
@ApplicationScoped
public class AuthorResource {

    @Inject
    AuthorService authorService;

    @POST
    @Path("/create")
    public Response createAuthor(Author author) {
        Author created = authorService.createAuthor(author);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/update")
    public Author updateAuthor(@PathParam long id, Author update) {
        return authorService.updateAuthor(id, update);
    }

    @DELETE
    @Path("/{id}/delete")
    public Response deleteAuthor(@PathParam long id) {
        Author author = authorService.deleteAuthor(id);
        return Response.ok(author).build();
    }

    @GET
    @Path("/all")
    public List<Author> getAuthors() {
        return Author.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getAuthor(@PathParam long id) {
        Author author = Author.findById(id);

        if (author == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(String.format("Author for id %d not found.", id))
                    .build();
        }

        return Response.ok(author).entity(author).build();
    }
}

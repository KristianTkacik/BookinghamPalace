package cz.muni.fi.pv217;

import cz.muni.fi.pv217.Entity.Book;
import cz.muni.fi.pv217.Service.BookService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/book")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookService bookService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        Book created = bookService.createBook(book);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Book updateBook(@PathParam long id, Book update) {
        return bookService.updateBook(id, update);
    }

    @DELETE
    @Path("/{id}/delete")
    public Response deleteBook(@PathParam long id) {
        Book book = bookService.deleteBook(id);
        return Response.ok(book).build();
    }

    @GET
    public List<Book> getBooks(@QueryParam("author-id") Long authorId) {
        if (authorId == null) {
            return Book.listAll();
        }
        return Book.list("author.id", authorId);
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam long id) {
        Book book = Book.findById(id);

        if (book == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(String.format("Book for id %d not found.", id))
                    .build();
        }

        return Response.ok(book).entity(book).build();
    }
}

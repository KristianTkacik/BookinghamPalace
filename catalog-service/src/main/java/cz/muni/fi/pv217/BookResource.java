package cz.muni.fi.pv217;

import cz.muni.fi.pv217.Entity.Author;
import cz.muni.fi.pv217.Entity.Book;
import cz.muni.fi.pv217.Entity.Genre;
import cz.muni.fi.pv217.Service.BookService;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Path("/book")
@ApplicationScoped
public class BookResource {

    @Inject
    BookService bookService;

    @POST
    @Path("/create")
    @Counted(name = "book.create.counter")
    @Timed(name = "book.create.timer")
    public Response createBook(Book book) {
        Book created = bookService.createBook(book);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/update")
    @Counted(name = "book.update.counter")
    @Timed(name = "book.update.timer")
    public Book updateBook(@PathParam long id, Book update) {
        return bookService.updateBook(id, update);
    }

    @DELETE
    @Path("/{id}/delete")
    @Counted(name = "book.delete.counter")
    @Timed(name = "book.delete.timer")
    public Response deleteBook(@PathParam long id) {
        Book book = bookService.deleteBook(id);
        return Response.ok(book).build();
    }

    @GET
    @Counted(name = "book.getAll.counter")
    @Timed(name = "book.getAll.timer")
    public List<Book> getBooks(@QueryParam("author-id") Long authorId) {
        if (authorId == null) {
            return Book.listAll();
        }
        return Book.list("author.id", authorId);
    }

//    @GET
//    @Transactional
//    @Path("/test")
//    public String test() {
//        Author author = new Author("author", LocalDate.now(), new HashSet<>());
//        author.persist();
//        Book book = new Book("a", LocalDate.now(), Genre.ACTION, new BigDecimal(24), author);
//        book.persist();
//        Book b = new Book("b", LocalDate.now(), Genre.ACTION, new BigDecimal(24), author);
//        Book c = new Book("c", LocalDate.now(), Genre.ACTION, new BigDecimal(24), null);
//        b.persist();
//        c.persist();
//        return "Yeah";
//    }


    @GET
    @Path("/{id}")
    @Counted(name = "book.getOne.counter")
    @Timed(name = "book.getOne.timer")
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

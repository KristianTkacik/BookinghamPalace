package cz.muni.fi.pv217.Service;

import cz.muni.fi.pv217.Entity.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class BookService {

    @Transactional
    public Book createBook(Book book) {
        book.persist();
        return book;
    }

    @Transactional
    public Book updateBook(long id, Book update) {
        Book book = Book.findById(id);

        if (book == null) {
            throw new NotFoundException(String.format("Book with id %d not found", id));
        }

        book.merge(update);
        book.persist();
        return book;
    }

    @Transactional
    public Book deleteBook(long id) {
        Book book = Book.findById(id);

        if (book == null) {
            throw new NotFoundException(String.format("Book with id %d not found", id));
        }

        boolean deleted = Book.deleteById(id);
        return deleted ? book : null;
    }
}

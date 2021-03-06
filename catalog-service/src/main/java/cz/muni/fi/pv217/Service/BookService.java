package cz.muni.fi.pv217.Service;

import cz.muni.fi.pv217.DTO.BookCreateDTO;
import cz.muni.fi.pv217.DTO.BookUpdateDTO;
import cz.muni.fi.pv217.Entity.Author;
import cz.muni.fi.pv217.Entity.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class BookService {

    @Transactional
    public Book createBook(BookCreateDTO book) {
        Author author = null;

        if (book.author != null) {
            author = Author.findById(book.author.id);
            if (author == null) {
                throw new NotFoundException(String.format("Author with id %d not found", book.author.id));
            }
        }

        Book created = new Book(book.title, book.releaseDate, book.genre, book.price, author);
        created.persist();
        return created;
    }

    @Transactional
    public Book updateBook(long id, BookUpdateDTO update) {
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

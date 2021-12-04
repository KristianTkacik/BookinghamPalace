package cz.muni.fi.pv217.Service;

import cz.muni.fi.pv217.DTO.AuthorDTO;
import cz.muni.fi.pv217.Entity.Author;
import cz.muni.fi.pv217.Entity.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class AuthorService {

    @Transactional
    public Author createAuthor(AuthorDTO author) {
        Author created = new Author(author.name, author.dateOfBirth, new HashSet<>());
        created.persist();
        return created;
    }

    @Transactional
    public Author updateAuthor(long id, AuthorDTO update) {
        Author author = Author.findById(id);

        if (author == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        author.merge(update);
        author.persist();
        return author;
    }

    @Transactional
    public Author deleteAuthor(long id) {
        Author author = Author.findById(id);

        if (author == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        List<Book> books = Book.list("author.id", id);
        for (Book book: books) {
            book.author = null;
        }

        boolean deleted = Author.deleteById(id);
        return deleted ? author : null;
    }

    @Transactional
    public Author addBook(long id, Book book) {
        Author author = Author.findById(id);

        if (author == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        book = Book.findById(book.id);

        if (book == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        author.books.add(book);
        book.author = author;
        book.persist();
        return author;
    }

    @Transactional
    public Author removeBook(long id, Book book) {
        Author author = Author.findById(id);

        if (author == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        book = Book.findById(book.id);

        if (book == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        author.books.remove(book);
        book.author = null;
        book.persist();
        author.persist();
        return author;
    }
}

package cz.muni.fi.pv217.Service;

import cz.muni.fi.pv217.DTO.AuthorCreateDTO;
import cz.muni.fi.pv217.DTO.AuthorUpdateDTO;
import cz.muni.fi.pv217.DTO.BookIdDto;
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
    public Author createAuthor(AuthorCreateDTO author) {
        Author created = new Author(author.name, author.dateOfBirth, new HashSet<>());
        created.persist();
        return created;
    }

    @Transactional
    public Author updateAuthor(long id, AuthorUpdateDTO update) {
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
    public Author addBook(long id, BookIdDto book) {
        Author author = Author.findById(id);

        if (author == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        Book found = Book.findById(book.id);

        if (found == null) {
            throw new NotFoundException(String.format("Book with id %d not found", book.id));
        }

        if (author.books.contains(found)) {
            throw new IllegalArgumentException(
                    String.format("Author with id %d already has a book with id %d", id, book.id));
        }

        author.books.add(found);
        found.author = author;
        found.persist();
        return author;
    }

    @Transactional
    public Author removeBook(long id, BookIdDto book) {
        Author author = Author.findById(id);

        if (author == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        Book found = Book.findById(book.id);

        if (found == null) {
            throw new NotFoundException(String.format("Book with id %d not found", book.id));
        }

        if (!author.books.contains(found)) {
            throw new IllegalArgumentException(
                    String.format("Author with id %d does not have a book with id %d", id, book.id));
        }

        author.books.remove(found);
        found.author = null;
        found.persist();
        author.persist();
        return author;
    }
}

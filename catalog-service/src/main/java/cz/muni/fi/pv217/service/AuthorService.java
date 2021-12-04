package cz.muni.fi.pv217.service;

import cz.muni.fi.pv217.Entity.Author;
import cz.muni.fi.pv217.Entity.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class AuthorService {
    @Transactional
    public Author createAuthor(Author author) {
        author.persist();
        return author;
    }

    @Transactional
    public Author updateAuthor(long id, Author update) {
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

        boolean deleted = Author.deleteById(id);
        return deleted ? author : null;
    }

    @Transactional
    public Author addBook(long id, Book book) {
        Author author = Author.findById(id);

        if (author == null) {
            throw new NotFoundException(String.format("Author with id %d not found", id));
        }

        author.addBook(book);
        author.persist();
        return author;
    }
}

package cz.muni.fi.pv217.Entity;

import cz.muni.fi.pv217.DTO.BookUpdateDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Book extends PanacheEntity {

    public String title;
    public LocalDate releaseDate;
    public Genre genre;
    public BigDecimal price;
    @ManyToOne
    public Author author;

    public Book() {
    }

    public Book(String title, LocalDate releaseDate, Genre genre, BigDecimal price, Author author) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.price = price;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre=" + genre +
                ", price=" + price +
                ", author=" + author +
                ", id=" + id +
                '}';
    }

    public void merge(BookUpdateDTO update) {
        if (update.title != null) {
            this.title = update.title;
        }
        if (update.releaseDate != null) {
            this.releaseDate = update.releaseDate;
        }
        if (update.genre != null) {
            this.genre = update.genre;
        }
        if (update.price != null) {
            this.price = update.price;
        }
        if (update.author != null) {
            Author author = Author.findById(update.author.id);
            if (author == null) {
                throw new NotFoundException(String.format("Author with id %d not found", update.author.id));
            }
            this.author = author;
        }
    }
}

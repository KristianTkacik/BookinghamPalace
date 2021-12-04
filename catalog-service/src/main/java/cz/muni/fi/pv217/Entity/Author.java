package cz.muni.fi.pv217.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.muni.fi.pv217.DTO.AuthorDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Author extends PanacheEntity {

    public String name;
    public LocalDate dateOfBirth;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    public Set<Book> books;

    public Author() {
    }

    public Author(String name, LocalDate dateOfBirth, Set<Book> books) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", id=" + id +
                '}';
    }

    public void merge(AuthorDTO update) {
        if (update.name != null) {
            this.name = update.name;
        }
        if (update.dateOfBirth != null) {
            this.dateOfBirth = update.dateOfBirth;
        }
    }
}

package cz.muni.fi.pv217.DTO;

import cz.muni.fi.pv217.Entity.Genre;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BookCreateDTO {

    @NotEmpty
    public String title;
    public LocalDate releaseDate;
    @NotNull
    public Genre genre;
    @NotNull
    public BigDecimal price;
    public AuthorIdDTO author;
}

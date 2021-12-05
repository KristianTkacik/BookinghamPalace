package cz.muni.fi.pv217.DTO;

import cz.muni.fi.pv217.Entity.Genre;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookUpdateDTO {
    public String title;
    public LocalDate releaseDate;
    public Genre genre;
    public BigDecimal price;
    public AuthorIdDTO author;
}

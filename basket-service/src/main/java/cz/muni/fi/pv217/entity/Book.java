package cz.muni.fi.pv217.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Book {

    public long id;
    public String title;
    public LocalDate releaseDate;
    public Genre genre;
    public BigDecimal price;
}

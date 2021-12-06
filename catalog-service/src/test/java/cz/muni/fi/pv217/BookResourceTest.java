package cz.muni.fi.pv217;

import cz.muni.fi.pv217.DTO.BookCreateDTO;
import cz.muni.fi.pv217.DTO.BookUpdateDTO;
import cz.muni.fi.pv217.Entity.Book;
import cz.muni.fi.pv217.Entity.Genre;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import javax.json.Json;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class BookResourceTest {

    @Test
    public void testGetBooks() {
        given()
                .when().get("/book")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("title", containsInAnyOrder("book-a", "book-b"))
                .body("genre", containsInAnyOrder("ACTION", "ADVENTURE"));
    }

    @Test
    public void testGetOneBook() {
        given()
                .when().get("/book/2")
                .then()
                .statusCode(200)
                .body("title", is("book-a"))
                .body("genre", is("ACTION"))
                .body("price", is(19.99F))
                .body("releaseDate", is("2014-02-05"));
    }

    @Test
    public void testCreateBook() {
        BookCreateDTO book = new BookCreateDTO();
        book.title = "book-c";
        book.releaseDate = LocalDate.of(1999, 1, 1);
        book.genre = Genre.ADVENTURE;
        book.price = new BigDecimal("4.99");

        given()
                .body(book)
                .contentType("application/json")
                .when().post("/book/create")
                .then()
                .statusCode(201)
                .body("title", is("book-c"))
                .body("genre", is("ADVENTURE"))
                .body("price", is(4.99F))
                .body("releaseDate", is("1999-01-01"));
    }

    @Test
    public void testUpdateBook() {
        BookUpdateDTO book = new BookUpdateDTO();
        book.title = "book-new";
        book.releaseDate = LocalDate.of(1999, 1, 1);
        book.price = new BigDecimal("4.99");

        given()
                .body(book)
                .contentType("application/json")
                .when().put("/book/3/update")
                .then()
                .statusCode(200)
                .body("title", is("book-new"))
                .body("genre", is("ADVENTURE"))
                .body("price", is(4.99F))
                .body("releaseDate", is("1999-01-01"));
    }

    @Test
    public void testDeleteBook() {
        given()
                .when().delete("/book/2/delete")
                .then()
                .statusCode(200)
                .body("title", is("book-a"))
                .body("genre", is("ACTION"))
                .body("price", is(19.99F))
                .body("releaseDate", is("2014-02-05"));

        given()
                .when().get("/book/2")
                .then()
                .statusCode(404);
    }
}

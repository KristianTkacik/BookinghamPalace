package cz.muni.fi.pv217;

import cz.muni.fi.pv217.DTO.AuthorCreateDTO;
import cz.muni.fi.pv217.DTO.AuthorUpdateDTO;
import cz.muni.fi.pv217.DTO.BookIdDto;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class AuthorResourceTest {

    @Test
    public void testGetOneAuthor() {
        given()
                .when().get("/author/1")
                .then()
                .statusCode(200)
                .body("name", is("author-a"))
                .body("dateOfBirth", is("1991-04-01"));
    }

    @Test
    public void testUpdateAuthor() {
        AuthorUpdateDTO author = new AuthorUpdateDTO();
        author.name = "author-new";
        author.dateOfBirth = LocalDate.of(1999, 1, 1);

        given()
                .body(author)
                .contentType("application/json")
                .when().put("/author/6/update")
                .then()
                .statusCode(200)
                .body("name", is("author-new"))
                .body("dateOfBirth", is("1999-01-01"));
    }

    @Test
    public void testCreateAuthor() {
        AuthorCreateDTO author = new AuthorCreateDTO();
        author.name = "author-c";
        author.dateOfBirth = LocalDate.of(1999, 1, 1);

        given()
                .body(author)
                .contentType("application/json")
                .when().post("/author/create")
                .then()
                .statusCode(201)
                .body("name", is("author-c"))
                .body("dateOfBirth", is("1999-01-01"));
    }

    @Test
    public void testDeleteAuthor() {
        given()
                .when().delete("/author/2/delete")
                .then()
                .statusCode(200)
                .body("name", is("author-b"))
                .body("dateOfBirth", is("2000-05-07"));

        given()
                .when().get("/author/2")
                .then()
                .statusCode(404);
    }

    @Test
    public void testAuthorAddBook() {
        given()
                .when().get("/book?author-id=6")
                .then()
                .statusCode(200)
                .body("size()", is(0));

        BookIdDto bookIdDto = new BookIdDto();
        bookIdDto.id = 7L;

        given()
                .body(bookIdDto)
                .contentType("application/json")
                .when().put("/author/6/add-book")
                .then()
                .statusCode(200);

        given()
                .when().get("/book?author-id=6")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("title", contains("book-c"))
                .body("genre", contains("ADVENTURE"))
                .body("price", contains(9.99F));
    }

    @Test
    public void testAuthorRemoveBook() {
        given()
                .when().get("/book?author-id=8")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("title", contains("book-f"))
                .body("genre", contains("ADVENTURE"))
                .body("price", contains(9.99F));

        BookIdDto bookIdDto = new BookIdDto();
        bookIdDto.id = 9L;

        given()
                .body(bookIdDto)
                .contentType("application/json")
                .when().put("/author/8/remove-book")
                .then()
                .statusCode(200);

        given()
                .when().get("/book?author-id=8")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }
}

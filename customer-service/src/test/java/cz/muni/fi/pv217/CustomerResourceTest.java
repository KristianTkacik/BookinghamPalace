package cz.muni.fi.pv217;

import cz.muni.fi.pv217.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pv217.dto.CustomerCreateDTO;
import cz.muni.fi.pv217.dto.CustomerUpdateDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class CustomerResourceTest {

    @Test
    public void testGetCustomers() {
        given()
                .when().get("/customer")
                .then()
                .statusCode(200)
                .body("size()", is(4))
                .body("name", containsInAnyOrder("Chuck", "Alice", "John", "Eve"))
                .body("email", containsInAnyOrder("chuck@mail.com", "alice@mail.com", "john@mail.com", "eve@mail.com"));
    }

    @Test
    public void testGetCustomer() {
        given()
                .when().get("/customer/1")
                .then()
                .statusCode(200)
                .body("name", is("Chuck"))
                .body("email", is("chuck@mail.com"));
    }

    @Test
    public void testCreateUpdateDeleteCustomer() {
        CustomerCreateDTO customer = new CustomerCreateDTO();
        customer.name = "Test Name";
        customer.email = "test@mail.com";
        customer.password = "password";

        Object id = given()
                .body(customer)
                .contentType("application/json")
                .when().post("/customer/create")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Name"))
                .body("email", equalTo("test@mail.com"))
                .extract().path("id");

        CustomerUpdateDTO updateDTO = new CustomerUpdateDTO();
        updateDTO.name = "Updated Name";
        updateDTO.email = "updated@email.com";

        given()
                .body(updateDTO)
                .contentType("application/json")
                .when()
                .header("Authorization", "Bearer " + generateToken(id.toString(), customer.email))
                .put("/customer/update")
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"))
                .body("email", equalTo("updated@email.com"))
                .body("id", equalTo(id));

        given()
                .when()
                .header("Authorization", "Bearer " + generateToken(id.toString(), updateDTO.email))
                .delete("/customer/delete")
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"))
                .body("email", equalTo("updated@email.com"))
                .body("id", equalTo(id));
    }

    @Test
    public void testLoginValid() {
        CustomerAuthenticateDTO loginDetails = new CustomerAuthenticateDTO();
        loginDetails.email = "alice@mail.com";
        loginDetails.password = "password";

        given()
                .body(loginDetails)
                .contentType("application/json")
                .when().post("/customer/login")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLoginInvalid() {
        CustomerAuthenticateDTO loginDetails = new CustomerAuthenticateDTO();
        loginDetails.email = "alice@mail.com";
        loginDetails.password = "invalidPassword";

        given()
                .body(loginDetails)
                .contentType("application/json")
                .when().post("/customer/login")
                .then()
                .statusCode(401);
    }

    private String generateToken(String sub, String email) {
        return Jwt.issuer("https://example.com/issuer")
                .subject(sub)
                .claim(Claims.email.name(), email)
                .groups(new HashSet<>(List.of("Customer")))
                .sign();
    }
}
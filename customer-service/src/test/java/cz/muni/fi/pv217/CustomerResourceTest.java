package cz.muni.fi.pv217;

import cz.muni.fi.pv217.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pv217.dto.CustomerCreateDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

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
    public void testCreateCustomer() {
        CustomerCreateDTO customer = new CustomerCreateDTO();
        customer.name = "Test Name";
        customer.email = "test@mail.com";
        customer.password = "password";

        given()
                .body(customer)
                .contentType("application/json")
                .when().post("/customer/create")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Name"))
                .body("email", equalTo("test@mail.com"));
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
        loginDetails.email = "eve@mail.com";
        loginDetails.password = "invalidPassword";

        given()
                .body(loginDetails)
                .contentType("application/json")
                .when().post("/customer/login")
                .then()
                .statusCode(401);
    }
}
package cz.muni.fi.pv217;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@QuarkusTestResource(WiremockCustomer.class)
public class BasketResourceTest {

    @Test
    public void testGetCustomerBasket() {
        given()
                .when().get("/customer/1/basket")
                .then()
                .statusCode(200)
                .body("customerId", is(1))
                .body("items.size()", is(1));
    }

    @Test
    public void testClearBasket() {
        given()
                .when().put("/customer/1/basket/clear")
                .then()
                .statusCode(200)
                .body("customerId", is(1))
                .body("items.size()", is(0));
    }

    @Test
    public void removeItemFromBasket() {
        given()
                .when().put("/customer/1/basket/remove/1")
                .then()
                .statusCode(200)
                .body("customerId", is(1))
                .body("items.size()", is(0));
    }

    @Test
    public void removeNonexistentItemFromBasket() {
        given()
                .when().put("/customer/2/basket/remove/999")
                .then()
                .statusCode(404);
    }
}
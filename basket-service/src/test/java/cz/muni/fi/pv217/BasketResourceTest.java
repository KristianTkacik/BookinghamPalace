package cz.muni.fi.pv217;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class BasketResourceTest {

    @Test
    public void testGetCustomerBasketEndpoint() {
        given()
                .when().get("/customer/1/basket")
                .then()
                .statusCode(200)
                .body(is("{\"id\":1,\"customerId\":1,\"items\":[]}"));
    }
}
package cz.muni.fi.pv217;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class OrderResourceTest {

    @Test
    public void testGetOrders() {
        given()
          .when().get("/order")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}
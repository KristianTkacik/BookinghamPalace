package cz.muni.fi.pv217;

import cz.muni.fi.pv217.entity.Order;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class OrderResourceTest {

    @Test
    void getOrders() {
        given()
                .when().get("/order")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body("customerName", containsInAnyOrder("Anna", "Roger", "Roger"));
    }

    @Test
    void getOrder() {
        given()
                .when().get("/order/2")
                .then()
                .statusCode(200)
                .body("customerName", is("Anna"));
    }

    @Test
    void createOrder() {
        Order order = new Order();
        order.customerId = 20;
        order.customerName = "Roger";
        order.street = "Vesela 5";
        order.city = "Brno";
        order.country = "Cesko";

        given()
                .body(order)
                .contentType("application/json")
                .when().post("/order/create")
                .then()
                .statusCode(201)
                .body("customerName", equalTo("Roger"))
                .body("items", empty());
    }

    @Test
    void updateOrder() {
        Order order = new Order();
        order.customerId = 20;
        order.customerName = "Roger";
        order.street = "Vesela 5";
        order.city = "Brno";
        order.country = "Cesko";

        given()
                .body(order)
                .contentType("application/json")
                .when().put("/order/3/update")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body("customerName", equalTo("Roger"))
                .body("items", empty());
    }

    @Test
    void deleteOrder() {
        given()
                .when().delete("/order/1/delete")
                .then()
                .statusCode(200);

        given()
                .when().get("/order/1")
                .then()
                .statusCode(404);
    }
}
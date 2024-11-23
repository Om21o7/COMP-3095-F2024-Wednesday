package ca.gbc.orderservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

    @ServiceConnection
    static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("order-service")
            .withUsername("admin")
            .withPassword("password");

    @LocalServerPort
    private Integer port;


    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        postgresDB.start();
    }

    @Test
    void placeOrderTest() {
        String requestBody = """
            {
                "skuCode": "SKU123",
                "price": 50.0,
                "quantity": 2
            }
            """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue()) // Assert that orderId is not null
                .body("skuCode", Matchers.equalTo("SKU123")) // Assert the SKU code matches
                .body("totalPrice", Matchers.equalTo(100.0)) // Assert that the total price is calculated correctly
                .body(Matchers.equalTo("Order Placed Successfully")); // Maintain this for the success message
    }

}

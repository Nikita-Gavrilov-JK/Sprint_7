package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import order.OrderModel;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderStep {
    @Step("Создание заказа")
    public Response createOrder(OrderModel orderModel) {
        return given()
                .contentType(JSON)
                .body(orderModel)
                .when()
                .post("/api/v1/orders");
    }
    @Step("Получаем список заказов")
    public Response getOrderList(){
        return given()
                .contentType(JSON)
                .when()
                .get("/api/v1/orders");
    }
}

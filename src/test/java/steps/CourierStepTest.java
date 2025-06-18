package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CourierStepTest {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание курьера")
    public Response createCourier(Map<String, String> courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }
    @Step("Логин курьера")
    public Response loginCourier(Map<String, String> login) {
        return given()
                .header("Content-type", "application/json")
                .body(login)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }
    @Step("Удаление курьера")
    public Response deleteCourier(int courierId) {
        return given()
                .when()
                .delete(BASE_URL + "/api/v1/courier/" + courierId);
    }
}

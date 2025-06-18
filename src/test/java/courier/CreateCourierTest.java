package courier;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.BaseTest;
import steps.CourierStep;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCourierTest extends BaseTest {

    private final CourierStep courierStepTest = new CourierStep();
    private int createdCourierId;

    @Test
    @DisplayName("Курьера можно создать")
    public void shouldCreateCourier() {
        Map<String, String> courier = CourierGenerator.random();

        Response response = courierStepTest.createCourier(courier);
        response.then().statusCode(201).body("ok", is(true));

        // Логинимся, чтобы получить ID и потом удаляем
        Response loginResponse = courierStepTest.loginCourier(Map.of(
                "login", courier.get("login"),
                "password", courier.get("password")
        ));

        createdCourierId = loginResponse.then().statusCode(200).extract().path("id");
        assertTrue(createdCourierId > 0);
    }
    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void shouldNotAllowDuplicateCourier() {
        Map<String, String> courier = CourierGenerator.random();

        courierStepTest.createCourier(courier).then().statusCode(201);

        Response duplicateResponse = courierStepTest.createCourier(courier);
        duplicateResponse.then().statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        Response loginResponse = courierStepTest.loginCourier(Map.of(
                "login", courier.get("login"),
                "password", courier.get("password")
        ));
        createdCourierId = loginResponse.then().statusCode(200).extract().path("id");
    }
    @Test
    @DisplayName("Нельзя создать курьера без обязательных полей")
    public void shouldNotCreateWithoutRequiredFields() {
        Map<String, String> incompleteCourier = Map.of("login", "incompleteLogin");

        Response response = courierStepTest.createCourier(incompleteCourier);
        response.then().statusCode(400)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @AfterEach
    void tearDown() {
        if (createdCourierId > 0) {
            courierStepTest.deleteCourier(createdCourierId);
        }
    }
}

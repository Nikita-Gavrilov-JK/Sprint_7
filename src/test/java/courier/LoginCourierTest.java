package courier;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.CourierStepTest;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CourierStepTest courierSteps = new CourierStepTest();
    int courierId;

    Map<String, String> getCourierData() {
        String login = "login" + System.currentTimeMillis();
        String password = "password123";
        Map<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        return data;
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    void courierCanLoginSuccessfully() {
        Map<String, String> courier = getCourierData();

        courierSteps.createCourier(courier);
        Response loginResponse = courierSteps.loginCourier(courier);
        loginResponse.then()
                .statusCode(200)
                .body("id", notNullValue());

        courierId = loginResponse.then().extract().path("id");
    }
    @Test
    @DisplayName("Ошибка при логине без пароля")
    void loginMissingPassword() {
        Map<String, String> courier = getCourierData();
        courierSteps.createCourier(courier);

        Map<String, String> loginData = new HashMap<>();
        loginData.put("login", courier.get("login"));
        loginData.put("password", "");
        Response response = courierSteps.loginCourier(loginData);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Ошибка при логине с неверным логином")
    void loginWithWrongLogin() {
        Map<String, String> courier = new HashMap<>();
        courier.put("login", "nonexistent_login");
        courier.put("password", "somepass");

        Response response = courierSteps.loginCourier(courier);
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Ошибка при логине с неверным паролем")
    void loginWithWrongPassword() {
        // 1. Создаём курьера
        Map<String, String> courier = new HashMap<>();
        courier.put("login", "valid_login");
        courier.put("password", "correct_pass");
        courier.put("firstName", "User");
        courierSteps.createCourier(courier);

        // 2. Пробуем войти с неправильным паролем
        Map<String, String> wrongPass = new HashMap<>();
        wrongPass.put("login", "valid_login");
        wrongPass.put("password", "error_password");

        Response response = courierSteps.loginCourier(wrongPass);
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @AfterEach
    void tearDown() {
        if (courierId != 0) {
            courierSteps.deleteCourier(courierId);
        }
    }
}

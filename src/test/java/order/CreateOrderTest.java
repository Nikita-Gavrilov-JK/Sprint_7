package order;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import steps.BaseTest;
import steps.OrderStep;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
public class CreateOrderTest extends BaseTest {
    static Stream<org.junit.jupiter.params.provider.Arguments> getColorParm(){
        return Stream.of(
                arguments((Object) new String[]{"BLACK"}),
                arguments((Object) new String[]{"GREY"}),
                arguments((Object) new String[]{"BLACK", "GREY"}),
                arguments((Object) new String[]{})
        );
    }

    @DisplayName("Создание заказа с разными цветами")
    @ParameterizedTest(name = "Цвета: {0}")
    @MethodSource("getColorParm")
    void createOrderWithColors(String[] colors) {
        OrderModel orderModel = new OrderModel(
                "Иван", "Смирнов", "ул. Гоголя", "4",
                "+79991234567", 5, "2025-06-17", "Позвонить", colors
        );
        Response response = new OrderStep().createOrder(orderModel);

        response.then().log().all()
                .statusCode(201)
                .body("track", notNullValue());
    }

}

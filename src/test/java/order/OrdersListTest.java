package order;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.BaseTest;
import steps.OrderStepTest;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest extends BaseTest {

    @Test
    @DisplayName("Получение списка заказов")
    void getOrderList() {
        Response response = new OrderStepTest().getOrderList();

        response.then().log().all()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", instanceOf(java.util.List.class));
    }
}

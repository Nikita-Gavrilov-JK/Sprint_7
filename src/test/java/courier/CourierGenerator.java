package courier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CourierGenerator {

    public static Map<String, String> random() {
        String login = "login_" + UUID.randomUUID().toString().substring(0, 8);
        String password = "password_" + UUID.randomUUID().toString().substring(0, 8);
        String firstName = "Кот Леопольд";

        Map<String, String> courier = new HashMap<>();
        courier.put("login", login);
        courier.put("password", password);
        courier.put("firstName", firstName);
        return courier;
    }
}

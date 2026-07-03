import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import model.OrderModel;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.*;
import static data.OrderData.*;


@RunWith(Parameterized.class)
public class OrderParametersTests extends BaseApiTest {
    OrderModel order;
    String orderTrack;
    private final String[] color;

    public OrderParametersTests(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] parameters() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }


    @Test
    @DisplayName("Проверка успешности создания заказа, при разных параметрах цвета самоката")
    @Description("В параметрах теста проверяется, что цвет самоката: " +
            "1. Черный, 2. Серый, 3. Черный и серый, 4. Цвет самоката не передан. ")
    public void orderTest() {

        OrderModel order = new OrderModel(FIRS_NAME, LAST_NAME, ADDRESS, METRO_STATION,
                PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, color);


        createOrder(order)
                .then()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue());

        orderTrack = createOrder(order).path("track").toString();
        }
    }

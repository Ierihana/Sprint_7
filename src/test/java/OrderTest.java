import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.getOrderList;

public class OrderTest extends BaseApiTest {

    @Test
    @DisplayName("Запрос для получения списка заказов.")
    @Description("Проверка, что в теле ответа на запрос содержится список заказов.")
    public void getOrderListReturnOrderList(){

        getOrderList()
                .then()
                .statusCode(HTTP_OK)
                .and()
                .body("orders.id", notNullValue());
    }
}

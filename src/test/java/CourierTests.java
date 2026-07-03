import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.After;
import org.junit.Test;

import static data.CourierData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;


public class CourierTests extends BaseApiTest {

    @Override
    @After
    public void cleanUp(){
        if (isCourierCreated) {
            courierId = loginCourier(courier).path("id").toString();
            deleteCourier(courierId);
        }

    }
    // Проверка ручки создания курьера

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Курьера можно создать. Статус и код ответа: 201 Created. Успешный запрос возвращает:  ok: true ")
    public void courierCreationSuccess(){
        isCourierCreated = true;
        createCourier(courier)
                .then()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Попытка создания двух одинаковых курьеров")
    @Description("Если создать пользователя с логином, который уже есть, возвращается ошибка.")
    public void creatingTwoIdenticalCouriers(){
        isCourierCreated = true;
        createCourier(courier);
        createCourier(courier)
                .then()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    @DisplayName("Попытка создания курьера без логина")
    @Description("Логин - обязательное поле для создания курьера. " +
            "При отсутствии обязательного поля запрос возвращает ошибку.")
    public void creatingCourierWithoutLogin(){
        CourierModel badCourier = new CourierModel(null, COURIER_PASSWORD, COURIER_FIRST_NAME);
        createCourier(badCourier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Попытка создания курьера без пароля")
    @Description("Пароль - обязательное поле для создания курьера. " +
            "При отсутствии обязательного поля запрос возвращает ошибку.")
    public void creatingCourierWithoutPassword(){
        CourierModel badCourier = new CourierModel(COURIER_LOGIN, null, COURIER_FIRST_NAME);
        createCourier(badCourier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }



}

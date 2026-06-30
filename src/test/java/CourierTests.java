import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.Test;

import static data.CourierData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;


public class CourierTests extends BaseApiTest {

    // Проверка ручки создания курьера

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Курьера можно создать. Статус и код ответа: 201 Created. Успешный запрос возвращает:  ok: true ")
    public void courierCreationSuccess(){
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);

        createCourier(courier)
                .then()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));

        courierId = loginCourier(courier)
                .path("id").toString();
    }

    @Test
    @DisplayName("Попытка создания двух одинаковых курьеров")
    @Description("Если создать пользователя с логином, который уже есть, возвращается ошибка.")
    public void creatingTwoIdenticalCouriers(){
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);
        createCourier(courier);

        createCourier(courier)
                .then()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierId = loginCourier(courier)
                .path("id").toString();
    }

    @Test
    @DisplayName("Попытка создания курьера без логина")
    @Description("Логин - обязательное поле для создания курьера. " +
            "При отсутствии обязательного поля запрос возвращает ошибку.")
    public void creatingCourierWithoutLogin(){
        courier = new CourierModel(null, COURIER_PASSWORD, COURIER_FIRST_NAME);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Попытка создания курьера без пароля")
    @Description("Пароль - обязательное поле для создания курьера. " +
            "При отсутствии обязательного поля запрос возвращает ошибку.")
    public void creatingCourierWithoutPassword(){
        courier = new CourierModel(COURIER_LOGIN, null, COURIER_FIRST_NAME);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    // Проверка ручки авторизации курьера

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Курьер может авторизоваться под существующим пользователем, передав логин и пароль. " +
            "Статус и код ответа: 200 OK. Успешный запрос возвращает id курьера.")
    public void courierLoginSuccess(){
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);
        createCourier(courier);

        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
        courierId = loginCourier(courier)
                .path("id").toString();
    }
    @Test
    @DisplayName("Попытка авторизации курьера без логина")
    @Description("Логин - обязательное поле для авторизации курьера. " +
            "При отсутствии обязательного поля запрос возвращает: 400 Bad Request.")
    public void loginCourierWithoutLogin(){
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);
        createCourier(courier);
        courierId = loginCourier(courier)
                .path("id").toString();
        String originalCourierLogin = courier.getLogin();
        courier.setLogin("");
        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

        courier.setLogin(originalCourierLogin);


    }

    @Test
    @DisplayName("Попытка авторизации курьера без пароля")
    @Description("Пароль - обязательное поле для авторизации курьера. " +
            "При отсутствии обязательного поля запрос возвращает: 400 Bad Request.")
    public void loginCourierWithoutPassword() {
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);
        createCourier(courier);
        courierId = loginCourier(courier)
                .path("id").toString();
        String originalCourierPassword = courier.getPassword();
        courier.setPassword("");
        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

        courier.setPassword(originalCourierPassword);
    }

    @Test
    @DisplayName("Попытка авторизации курьера с несуществующими логином и паролем.")
    public void loginCourierWithNonExistentLogPass() {
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);
        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

    }





}

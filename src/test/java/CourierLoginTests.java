import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.CourierModel;
import org.junit.Before;
import org.junit.Test;

import static data.CourierData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class CourierLoginTests extends BaseApiTest {

    // Проверка ручки авторизации курьера

    @Override
    @Before
    public void setup(){

        RestAssured.baseURI = BASE_URI;
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);
        createCourier(courier);
        isCourierCreated = true;
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Курьер может авторизоваться под существующим пользователем, передав логин и пароль. " +
            "Статус и код ответа: 200 OK. Успешный запрос возвращает id курьера.")
    public void courierLoginSuccess(){

        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());

    }
    @Test
    @DisplayName("Попытка авторизации курьера без логина")
    @Description("Логин - обязательное поле для авторизации курьера. " +
            "При отсутствии обязательного поля запрос возвращает: 400 Bad Request.")
    public void loginCourierWithoutLogin(){

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
        CourierModel nonExistentCourier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);
        Faker user = new Faker();
        nonExistentCourier.setLogin(user.name().username() + System.currentTimeMillis());
        nonExistentCourier.setPassword(user.regexify("[0-9]{4}"));

        loginCourier(nonExistentCourier)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

    }
}

package steps;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CourierModel;

import static data.CourierData.COURIER_CREATE_PATH;
import static data.CourierData.COURIER_LOGIN_PATH;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Создание курьера: {courier}")
    public static Response createCourier(CourierModel courier) {

        return given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step("Авторизация курьера с логином {courier.login} и паролем.")
    public static Response loginCourier(CourierModel courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .extract().response();

    }

    @Step("Удаление курьера по id {courierId}")
    public static void deleteCourier(String courierId) {
        given().delete(COURIER_CREATE_PATH + "/{courierId}", courierId);
    }



}

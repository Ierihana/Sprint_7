package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CancelOrderModel;
import model.OrderModel;

import static data.OrderData.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {


    @Step("Создание заказа с данными: {orderModel}")
    public static Response createOrder(OrderModel orderModel) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .and()
                .body(orderModel)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .log().all()
                .extract().response();

    }


    @Step("Отмена заказа по трек-номеру: {orderTrack}")
    public static void cancelOrder(String orderTrack) {
       CancelOrderModel cancelOrderModel = new CancelOrderModel(orderTrack);

        given()
                .header("Content-type", "application/json")
                .and()
                .body(cancelOrderModel)
                .put(CANCEL_ORDER_PATH);
    }

    @Step("Получение списка всех заказов")
    public static Response getOrderList(){
        return given()
                .get(GET_ORDER_LIST)
                .then()
                .log().all()
                .extract().response();
    }
}

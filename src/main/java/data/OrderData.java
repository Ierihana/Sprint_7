package data;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;


public class OrderData {

    public static final String BASE_URI = RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    private static final Faker fakerOrder = new Faker();
    public static final String FIRS_NAME = fakerOrder.name().firstName() + System.currentTimeMillis();
    public static final String LAST_NAME = fakerOrder.name().lastName() + System.currentTimeMillis();
    public static final String ADDRESS = fakerOrder.address().toString();
    public static final Integer METRO_STATION = fakerOrder.number().numberBetween(1, 238);
    public static final String PHONE = fakerOrder.phoneNumber().toString();
    public static final int RENT_TIME = fakerOrder.number().numberBetween(1, 11);
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    public static final String DELIVERY_DATE = fmt.format(fakerOrder.date().future(10, TimeUnit.DAYS));
    public static final String COMMENT = "My important comment)";


    public static final String CREATE_ORDER_PATH = "/api/v1/orders";
    public static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";
    public static final String GET_ORDER_LIST = "/api/v1/orders";
}

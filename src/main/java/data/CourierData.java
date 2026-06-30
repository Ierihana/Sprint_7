package data;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;

public class CourierData {

    public static final String BASE_URI = RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

    static Faker user = new Faker();
    public static final String COURIER_LOGIN = user.name().username() + System.currentTimeMillis();
    public static final String COURIER_PASSWORD = user.regexify("[0-9]{4}");
    public static final String COURIER_FIRST_NAME = "bulka";

    public static final String COURIER_CREATE_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
}

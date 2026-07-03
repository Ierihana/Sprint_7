import io.restassured.RestAssured;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;

import static data.CourierData.*;
import static data.CourierData.COURIER_FIRST_NAME;
import static steps.CourierSteps.deleteCourier;
import static steps.CourierSteps.loginCourier;
import static steps.OrderSteps.cancelOrder;


public class BaseApiTest {

    public CourierModel courier;
    public String courierId = null;
    protected boolean isCourierCreated = false;;
    public String orderTrack = null;

    @Before
    public void setup(){

        RestAssured.baseURI = BASE_URI;
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD, COURIER_FIRST_NAME);

    }
    @After
    public void cleanUp(){

        if (isCourierCreated) {
            courierId = loginCourier(courier).path("id").toString();
            deleteCourier(courierId);
        }

        if (orderTrack != null){
            cancelOrder(orderTrack);
        }
        orderTrack = null;

    }
}

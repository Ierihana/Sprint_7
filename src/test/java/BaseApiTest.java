import io.restassured.RestAssured;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;
import static data.CourierData.BASE_URI;
import static steps.CourierSteps.deleteCourier;


public class BaseApiTest {

    public CourierModel courier;
    public String courierId = null;

    @Before
    public void setup(){

        RestAssured.baseURI = BASE_URI;


    }
    @After
    public void cleanUp(){
       if (courierId != null) {
           deleteCourier(courierId);
       }

       courierId = null ;

    }
}

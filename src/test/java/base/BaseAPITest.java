package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseAPITest {

    @BeforeMethod(alwaysRun = true)
    public void setupAPI() {

        RestAssured.baseURI = "https://devin-1-ruwq.onrender.com";

        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("BASE URI SET TO: " + RestAssured.baseURI);
    }
}
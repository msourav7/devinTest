package tests;

import base.BaseAPITest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import utils.Log;

public class LoginAPITest extends BaseAPITest {

    private static final Logger log = Log.getLogger(LoginAPITest.class);

    @Test(groups = {"api"})
    public void testLoginAPI() {

        log.info("Starting API Login Test");

        String requestBody = """
        {
            "emailId": "test1new@gmail.com",
            "password": "Test@123456"
        }
        """;

        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()   // for debug
                .when()
                .post("/login")
                .then()
                .log().all()
                .extract()
                .response();

        log.info("Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);

        log.info("API Login Test Passed");
    }
}
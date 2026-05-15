package tests;

import base.BaseAPITest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import utils.Log;

public class SignupAPITest extends BaseAPITest {

    private static final Logger log = Log.getLogger(SignupAPITest.class);

    @Test(groups = {"api"})
    public void testSignupAPI() {

        log.info("Starting Signup API Test");

        // use unique email every run
        String uniqueEmail = "user" + System.currentTimeMillis() + "@gmail.com";

        //"emailId": "%s", -- this placeholder will get replaced by this uniqueEmail
        String requestBody = """
                {
                    "firstName": "APIi",
                    "lastName": "User",
                    "emailId": "%s",
                    "password": "Test@123456"
                }
                """.formatted(uniqueEmail);

        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()

                .when()
                .post("/signup")

                .then()
                .log().all()
                .extract()
                .response();

        log.info("Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);

        log.info("Signup API Test Passed");
    }
}
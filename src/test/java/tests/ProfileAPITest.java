//package tests;
//
//import base.BaseAPITest;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import org.apache.logging.log4j.Logger;
//import utils.Log;
//
//public class ProfileAPITest extends BaseAPITest {
//
//    private static final Logger log = Log.getLogger(ProfileAPITest.class);
//
//    @Test(groups = {"api"})
//    public void testGetUserProfileAPI() {
//
//        log.info("Starting Get Profile API Test");
//
//        // LOGIN API
//        String loginBody = """
//                {
//                    "emailId": "test1new@gmail.com",
//                    "password": "Test@123456"
//                }
//                """;
//
//        Response loginResponse = RestAssured
//                .given()
//                .header("Content-Type", "application/json")
//                .body(loginBody)
//                .log().all()
//
//                .when()
//                .post("/login")
//
//                .then()
//                .log().all()
//                .extract()
//                .response();
//
//        Assert.assertEquals(loginResponse.getStatusCode(), 200);
//
//        // get token if token is comming in response body as json
//        //String token = loginResponse.jsonPath().getString("token");
//        //String token = loginResponse.jsonPath().getString("data.token");
//
//        // get token if token in cookies
//        String token = loginResponse.getCookie("token");
//        System.out.println(loginResponse.asPrettyString());
//        System.out.println("TOKEN------------- " + token);
//
//        log.info("TOKEN: " + token);
//
//        // after login profil view api test or get profile test
//        Response profileResponse = RestAssured
//                .given()
////                .header("Authorization", "Bearer " + token) -cuz token is not present in JSON
//                .cookie("token", token)//use token cookie cuz backend is returning token in cookie not in json body so above cmnted
//                .log().all()
//                .when()
//                .get("/profile/view")
//                .then()
//                .log().all()
//                .extract()
//                .response();
//
//        log.info("Profile Status Code: " + profileResponse.getStatusCode());
//
//        Assert.assertEquals(profileResponse.getStatusCode(), 200);
//
//        String email = profileResponse.jsonPath().getString("emailId");
//
//        Assert.assertEquals(email, "test1new@gmail.com");
//
//        log.info("Get Profile API Test Passed");
//    }
//
//    //after login edit profile api test
//
//    @Test(groups = {"api"})
//    public void testEditProfileAPI() {
//
//        log.info("Starting Edit Profile API Test");
//
//        // LOGIN API
//        String loginBody = """
//                {
//                    "emailId": "test1new@gmail.com",
//                    "password": "Test@123456"
//                }
//                """;
//
//        Response loginResponse = RestAssured
//                .given()
//                .header("Content-Type", "application/json")
//                .body(loginBody)
//                .log().all()
//
//                .when()
//                .post("/login")
//
//                .then()
//                .log().all()
//                .extract()
//                .response();
//
//        Assert.assertEquals(
//                loginResponse.getStatusCode(),
//                200
//        );
//
//        // GET TOKEN FROM COOKIE
//        String token = loginResponse.getCookie("token");
//
//        System.out.println("TOKEN------------- " + token);
//
//        log.info("TOKEN: " + token);
//
//        // EDIT PROFILE BODY
//        String editBody = """
//                {
//                    "firstName": "UpdatedName"
//                }
//                """;
//
//        // EDIT PROFILE API
//        Response editResponse = RestAssured
//                .given()
//                .header("Content-Type", "application/json")
//                .cookie("token", token)
//                .body(editBody)
//                .log().all()
//
//                .when()
//                .patch("/profile/edit")
//
//                .then()
//                .log().all()
//                .extract()
//                .response();
//
//        log.info("Edit Profile Status Code: "
//                + editResponse.getStatusCode());
//
//        Assert.assertEquals(
//                editResponse.getStatusCode(),
//                200
//        );
//
//        String message =
//                editResponse.jsonPath()
//                        .getString("message");
//
//        Assert.assertTrue(
//                message.contains("profile updated successfully")
//        );
//
//        log.info("Edit Profile API Test Passed");
//    }
//
//
//}



//get rid of common methods like extracting the token , login body, login api call by creating a new method and use it

package tests;

import base.BaseAPITest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import utils.Log;

public class ProfileAPITest extends BaseAPITest {

    private static final Logger log =
            Log.getLogger(ProfileAPITest.class);

    //reusable common method
    public String getAuthToken() {

        //reusable for login body
        String loginBody = """
                {
                    "emailId": "test1new@gmail.com",
                    "password": "Test@123456"
                }
                """;

        //reusable for login api call
        Response loginResponse = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(loginBody)

                .when()
                .post("/login")

                .then()
                .extract()
                .response();

        Assert.assertEquals(
                loginResponse.getStatusCode(),
                200
        );

        //reusable token
        String token =
                loginResponse.getCookie("token");

        System.out.println("TOKEN: " + token);

        return token;
    }

    @Test(groups = {"api"})
    public void testGetUserProfileAPI() {

        log.info("Starting Get Profile API Test");

        // get token
        String token = getAuthToken();

        // profile view api
        Response profileResponse = RestAssured
                .given()
                .cookie("token", token)
                .log().all()

                .when()
                .get("/profile/view")

                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(
                profileResponse.getStatusCode(),
                200
        );

        String email =
                profileResponse.jsonPath()
                        .getString("emailId");

        Assert.assertEquals(
                email,
                "test1new@gmail.com"
        );

        log.info("Get Profile API Test Passed");
    }

//    @Test(groups = {"api"})
//    public void testEditProfileAPI() {
//
//        log.info("Starting Edit Profile API Test");
//
//        // get token
//        String token = getAuthToken();
//
//        // edit profile body
//        String editBody = """
//                {
//                    "firstName": "newDemoname"
//                }
//                """;
//
//        // edit profile api
//        Response editResponse = RestAssured
//                .given()
//                .header("Content-Type", "application/json")
//                .cookie("token", token)
//                .body(editBody)
//                .log().all()
//
//                .when()
//                .patch("/profile/edit")
//
//                .then()
//                .log().all()
//                .extract()
//                .response();
//
//        Assert.assertEquals(
//                editResponse.getStatusCode(),
//                200
//        );
//
//        String message =
//                editResponse.jsonPath()
//                        .getString("message");
//
//        Assert.assertTrue(
//                message.contains("profile updated successfully")
//        );
//
//        log.info("Edit Profile API Test Passed");
//    }
}
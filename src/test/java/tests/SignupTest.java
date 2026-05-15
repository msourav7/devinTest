package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.SignupPage;

import java.time.Duration;

public class SignupTest extends BaseTest {

//before pagefactory using this method
//@Test(dataProvider = "userData", groups = {"signup"})
//public void testSignup(String f, String l, String e, String p) {
//
//    // Click "Sign Up" toggle first
//    //driver.findElement(By.xpath("//p[contains(text(),'Sign Up Here')]")).click();
//    //driver.findElement(By.xpath("//p[contains(text(),'Sign Up Here')]")).click();
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//p[contains(text(),'Sign Up')]")
//    )).click();
//
//    SignupPage signup = new SignupPage(driver);
//    signup.signup(f, l, e, p);
//}

    //after applying pagefactory updated test method
    @Test(dataProvider = "userData", groups = {"signup"})
    public void testSignup(String f, String l, String e, String p) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //switching ui to signup page
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[contains(text(),'Sign Up')]")
        )).click();

        SignupPage signup = new SignupPage(driver);
        signup.signup(f, l, e, p);

        // Assertion example
//        String currentUrl = driver.getCurrentUrl();
//        assert currentUrl.contains("login") : "Signup failed!";


        boolean isLoggedIn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("img[alt='photo url']")
                )
        ).isDisplayed();

        Assert.assertTrue(isLoggedIn,"Login failed");
    }

    @DataProvider
    public Object[][] userData() {
        return new Object[][]{
                {"Souravnew", "Testnew", "test1new@gmail.com", "Test@123456"},
                {"Usernew", "Demonew", "test2new@gmail.com", "Test@123456"}
//                {"Souravneww", "Testneww", "test1neww@gmail.com", "Test@123456"},
//                {"Userneww", "Demoneww", "test2neww@gmail.com", "Test@123456"}

        };
    }
}
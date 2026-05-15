package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

//before ThreDLocal execution
//public class BaseTest {
//
//    public WebDriver driver;
//
//    @BeforeMethod(alwaysRun = true)
//    public void setup() {
//
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//
//        driver.get("https://dev-in-frontend.vercel.app/");
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        driver.quit();
//    }
//}


//after thread local execution for report
public class BaseTest {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

//    @BeforeMethod(alwaysRun = true)
//    public void setup() {
//        WebDriverManager.chromedriver().setup();
//        driver.set(new ChromeDriver());
//
//        getDriver().manage().window().maximize();
//        getDriver().get("https://dev-in-frontend.vercel.app/");
//    }
@BeforeMethod(alwaysRun = true)
public void setup() {

    WebDriverManager.chromedriver().setup();

    ChromeOptions options = new ChromeOptions();

    // detect headless from gitHub actions
    String headless = System.getProperty("headless");
    if ("true".equalsIgnoreCase(headless)) {
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
    }

//    if ("true".equals(System.getProperty("headless"))) {
//        options.addArguments("--headless=new");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--window-size=1920,1080");
//    }

    driver.set(new ChromeDriver(options));

    getDriver().manage().window().maximize();
    getDriver().get("https://dev-in-frontend.vercel.app/");
}

//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        getDriver().quit();
//        driver.remove();
//    }
// }


}

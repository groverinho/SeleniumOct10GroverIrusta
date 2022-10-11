package src;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Main {

    private WebDriver driver;
    String chromePath = System.getProperty("user.dir")+ "/driver/chromedriver";
    String baseURL = "";



    @BeforeTest
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", chromePath);
        driver = new ChromeDriver();
        System.out.println("Open website");
        driver.manage().window().maximize();
        driver.get(baseURL);
    }


    @Test
    public void test1() throws InterruptedException {

    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}

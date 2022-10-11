package src;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.PageUtil;

import java.util.List;

public class Main {

    private WebDriver driver;
    String chromePath = System.getProperty("user.dir")+ "/driver/chromedriver";
    String baseURL = "https://www.saucedemo.com/";



    @BeforeTest
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", chromePath);
        driver = new ChromeDriver();
        System.out.println("Open website");
        driver.manage().window().maximize();
        driver.get(baseURL);
    }

    public void login(){
        System.out.println("Fill userName");
        WebElement element = PageUtil.getElement(driver, By.name("user-name"));
        boolean isDisplayed = element !=null && element.isDisplayed();
        System.out.println("Is userName displayed ? "+ isDisplayed);
        Assert.assertTrue(isDisplayed,"UserName field is not displayed");
        element.sendKeys("standard_user");

        System.out.println("Fill password");
        element = PageUtil.getElement(driver, By.name("password"));
        isDisplayed = element !=null && element.isDisplayed();
        System.out.println("Is password displayed ? "+ isDisplayed);
        Assert.assertTrue(isDisplayed,"Password field is not displayed");
        element.sendKeys("secret_sauce");

        System.out.println("Click on Login");
        element = PageUtil.getElement(driver, By.name("login-button"));
        isDisplayed = element !=null && element.isDisplayed();
        System.out.println("Is Login button displayed ? "+ isDisplayed);
        Assert.assertTrue(isDisplayed,"Login button is not displayed");
        element.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html","Inventory page is not loaded");
    }

    public void selectProduct(){
        WebElement element = PageUtil.getElement(driver, By.xpath("(//div[@class='inventory_item_name'])[1]"));
        Assert.assertTrue(element !=null && element.isDisplayed(),"No products displayed");
        String product =         element.getText();
        System.out.println("'"+product + "' selected");

        element = PageUtil.getElement(driver, By.xpath("(//button[text()='Add to cart'])[1]"));
        Assert.assertTrue(element !=null && element.isDisplayed(),"'ADD TO CART' button is not displayed");
        System.out.println("Click on 'ADD TO CART' button");
        element.click();

        System.out.println("Check if remove button is displayed");
        element = PageUtil.getElement(driver, By.xpath("//button[@id='remove-sauce-labs-backpack']"));
        boolean isDisplayed = element !=null && element.isDisplayed();
        System.out.println("Is Remove Button displayed ? " +isDisplayed );
        Assert.assertTrue(isDisplayed,"'ADD TO CART' button is not displayed");

        System.out.println("Check if increase +1 in the shopping cart icon");
        element = PageUtil.getElement(driver, By.xpath("//span[@class='shopping_cart_badge']"));
        isDisplayed = element !=null && element.isDisplayed() && element.getText().equals("1");
        System.out.println("Is Remove Button displayed ? " +isDisplayed );
        Assert.assertTrue(isDisplayed,"'ADD TO CART' button is not displayed");
    }

    @Test
    public void test1() throws InterruptedException {
        System.out.println("1. Login to saucedemo");
        login();

        System.out.println("2. Select any product");
        selectProduct();
    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}

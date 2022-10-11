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
    String chromePath = System.getProperty("user.dir") + "/driver/chromedriver";
    String baseURL = "https://www.saucedemo.com/";


    @BeforeTest
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", chromePath);
        driver = new ChromeDriver();
        System.out.println("Open website");
        driver.manage().window().maximize();
        driver.get(baseURL);
    }

    public void login() {
        PageUtil.subLog("Fill userName");
        WebElement element = PageUtil.getElement(driver, By.name("user-name"));
        boolean isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Is userName displayed ? " + isDisplayed);
        Assert.assertTrue(isDisplayed, "UserName field is not displayed");
        element.sendKeys("standard_user");

        PageUtil.subLog("Fill password");
        element = PageUtil.getElement(driver, By.name("password"));
        isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Is password displayed ? " + isDisplayed);
        Assert.assertTrue(isDisplayed, "Password field is not displayed");
        element.sendKeys("secret_sauce");

        PageUtil.subLog("Click on Login");
        element = PageUtil.getElement(driver, By.name("login-button"));
        isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Is Login button displayed ? " + isDisplayed);
        Assert.assertTrue(isDisplayed, "Login button is not displayed");
        element.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Inventory page is not loaded");
    }

    public String selectProduct() {
        WebElement element = PageUtil.getElement(driver, By.xpath("(//div[@class='inventory_item_name'])[1]"));
        Assert.assertTrue(element != null && element.isDisplayed(), "No products displayed");
        String product = element.getText();
        PageUtil.subLog("'" + product + "' selected");

        element = PageUtil.getElement(driver, By.xpath("(//button[text()='Add to cart'])[1]"));
        Assert.assertTrue(element != null && element.isDisplayed(), "'ADD TO CART' button is not displayed");
        PageUtil.subLog("Click on 'ADD TO CART' button");
        element.click();

        PageUtil.subLog("Check if remove button is displayed");
        element = PageUtil.getElement(driver, By.xpath("//button[@id='remove-sauce-labs-backpack']"));
        boolean isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Is Remove Button displayed ? " + isDisplayed);
        Assert.assertTrue(isDisplayed, "Remove Button is not displayed");

        PageUtil.subLog("Check if increase +1 in the shopping cart icon");
        element = PageUtil.getElement(driver, By.xpath("//span[@class='shopping_cart_badge']"));
        isDisplayed = element != null && element.isDisplayed() && element.getText().equals("1");
        PageUtil.subLog("Is shopping card icon displayed and increased +1 ? " + isDisplayed);
        Assert.assertTrue(isDisplayed, "Shopping card icon is not displayed");

        return product;
    }

    public void removeProduct() {
        PageUtil.subLog("Click on remove button");
        WebElement element = PageUtil.getElement(driver, By.xpath("//button[@id='remove-sauce-labs-backpack']"));
        Assert.assertTrue(element != null && element.isDisplayed(), "Remove Button is not displayed");
        element.click();

        PageUtil.subLog("Check if Add to card button is displayed");
        element = PageUtil.getElement(driver, By.xpath("(//button[text()='Add to cart'])[1]"));
        boolean isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Is 'Add to cart' Button displayed ? " + isDisplayed);
        Assert.assertTrue(isDisplayed, "'Add to cart' Button is not displayed");

        PageUtil.subLog("Check if increase -1 in the shopping cart icon");
        element = PageUtil.getElement(driver, By.xpath("//span[@class='shopping_cart_badge']"));
        isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Is shopping card icon displayed -1 ? " + !isDisplayed);
        Assert.assertFalse(isDisplayed, "Shopping card icon is still displaying a value");
    }

    public void goToYourCardPage(String product) {
        PageUtil.subLog("Click on cart icon");
        WebElement element = PageUtil.getElement(driver, By.xpath("//div[@id='shopping_cart_container']"));
        boolean isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Is cart icon available ? "+ isDisplayed);
        Assert.assertTrue(element != null && element.isDisplayed(), "Card icon is not available");
        element.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Inventory page is not loaded");

        PageUtil.subLog("Verify if "+product+" product is displayed");
        element = PageUtil.getElement(driver, By.xpath("//div[text()='"+product+"']"));
         isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Is '"+product+ "' product displayed ? "+ isDisplayed);
        Assert.assertTrue(isDisplayed, "'"+product + "' product is not available");
    }

    public void removeProduct2(String product) {
        PageUtil.subLog("Click on remove button");
        WebElement element = PageUtil.getElement(driver, By.xpath("//button[@id='remove-sauce-labs-backpack']"));
        Assert.assertTrue(element != null && element.isDisplayed(), "Remove Button is not displayed");
        element.click();

        PageUtil.subLog("Verify if "+product+" product was removed");
        element = PageUtil.getElement(driver, By.xpath("//div[text()='"+product+"']"));
        boolean isDisplayed = element != null && element.isDisplayed();
        PageUtil.subLog("Was '"+product+ "' product removed ? "+ !isDisplayed);
        Assert.assertFalse(isDisplayed, "'"+product + "' product is still displaying");
    }

    public void goToInventoryPage() {
        PageUtil.subLog("Click on continue shopping button");
        WebElement element = PageUtil.getElement(driver, By.xpath("//button[@id='continue-shopping']"));
        boolean isDisplayed = element != null && element.isDisplayed();

        PageUtil.subLog("Is the continue shopping button displayed ? " + isDisplayed);
        Assert.assertTrue(isDisplayed, "Continue shopping button is not displayed");
        element.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Inventory page is not loaded");
    }

    public void verifyProductRemoved() {
        PageUtil.subLog("Verify if the product is selected");
        WebElement element = PageUtil.getElement(driver, By.xpath("//button[@id='remove-sauce-labs-backpack']"));
        boolean isDisplayed = element != null && element.isDisplayed();

        PageUtil.subLog("Is the product removed on the inventory page ? " + !isDisplayed);
        Assert.assertFalse(isDisplayed, "Product is still displaying on the inventory page");
    }



    @Test
    public void test1() throws InterruptedException {
        System.out.println("1. Login to saucedemo");
        login();

        System.out.println("2. Select any product");
        selectProduct();

        System.out.println("3. Remove the product selected");
        removeProduct();

        System.out.println("4. Select again the product");
        String product = selectProduct();

        System.out.println("5. Go to 'Your Card' Page");
        goToYourCardPage(product);

        System.out.println("6. Remove the product");
        removeProduct2(product);

        System.out.println("7. Check product removed in the inventory page");
        goToInventoryPage();
        verifyProductRemoved();


    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}

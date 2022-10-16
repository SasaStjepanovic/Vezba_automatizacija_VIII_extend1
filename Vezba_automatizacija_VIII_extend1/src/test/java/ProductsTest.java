import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class ProductsTest extends SwagLabs_BaseTest {
    @BeforeMethod
    @Parameters({"BROWSER", "ENV", "username", "password", "testType", "errorMessage"})
    public void setup(String browser, String env, String username, String password, String testType, @Optional String errorMessage) throws Exception {
        init(browser);
        openApp(env);
        Thread.sleep(3000);
        loginToSwagLabs(username, password, testType, errorMessage);
    }

    @AfterMethod
    public void tearDown() {
        quit();
    }

    @Test (priority = 0)
    public void checkParametersOfFirstProduct() {
        driver.findElement(By.cssSelector("#item_4_img_link")).click();
        String actualTitle1 = driver.findElement(By.cssSelector(".inventory_details_name.large_size")).getText();
        String expectedTitle1 = "Sauce Labs Backpack";
        Assert.assertEquals(actualTitle1, expectedTitle1);

        String buttonDefaultName1 = driver.findElement(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory")).getText();
        String buttonExpectedName1 = "ADD TO CART";
        Assert.assertEquals(buttonDefaultName1, buttonExpectedName1);
        driver.findElement(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory")).click();
        String buttonNotDefaultName1 = driver.findElement(By.cssSelector(".btn.btn_secondary.btn_small.btn_inventory")).getText();
        String buttonNotExpectedName1 = "REMOVE";
        Assert.assertEquals(buttonNotDefaultName1, buttonNotExpectedName1);
    }

    @Test (priority = 1)
    public void AddProductsAndCheckBasket() {
        driver.findElement(By.cssSelector("#item_4_img_link")).click();
        driver.findElement(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory")).click();
        driver.navigate().back();
        driver.findElement(By.cssSelector("#item_1_img_link")).click();
        driver.findElement(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory")).click();
        driver.navigate().back();
        driver.findElement(By.cssSelector("#item_2_img_link")).click();
        driver.findElement(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory")).click();
        driver.navigate().back();

        driver.findElement(By.cssSelector(".shopping_cart_container>a")).click();
        String basketNumberActual = driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
        String basketNumberExpexted = "3";
        Assert.assertEquals(basketNumberActual, basketNumberExpexted);
    }

    @Test(priority = 2)
    @Parameters({"firstName", "lastName", "zipCode"})
    public void checkOut(String firstName, String lastName, String zipCode) throws InterruptedException {
        driver.findElement(By.cssSelector(".shopping_cart_container>a")).click();
        driver.findElement(By.cssSelector(".btn.btn_action.btn_medium.checkout_button")).click();
        driver.findElement(By.xpath("//input[@id='first-name']")).sendKeys(firstName);
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys(lastName);
        driver.findElement(By.xpath("//input[@name='postalCode']")).sendKeys(zipCode);
        Thread.sleep(5000);
        driver.findElement(By.cssSelector(".submit-button.btn.btn_primary.cart_button.btn_action")).click();
        Thread.sleep(2000);
    }

    @Test(priority = 3)
    public void checkMenu(){
     boolean AreItemsDisplayed = checkMenuItems();

     Assert.assertTrue(AreItemsDisplayed, "Items are not available");

    }

}

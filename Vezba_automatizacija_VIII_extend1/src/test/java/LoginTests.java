import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests extends SwagLabs_BaseTest {

    @BeforeTest
    @Parameters({"BROWSER", "ENV"})
    public void setup(String browser, String env) throws Exception {
        init(browser);
        openApp(env);
    }

    @AfterTest
    public void tearDown(){
        quit();
    }

    @Test
    @Parameters({"username", "password", "testType", "errorMessage"})
    public void login(String username, String password, String testType, @Optional String errorMessage){
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
        // primer licne click metode
        ownClick(driver.findElement(By.xpath("//form/input")),10);

        if (testType.equals("positive")) {
            Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Products']")).getText(), "PRODUCTS");
        } else {
            Assert.assertEquals(driver.findElement(By.xpath("//form//h3")).getText(), errorMessage, "Something went wrong");
        }
    }

}

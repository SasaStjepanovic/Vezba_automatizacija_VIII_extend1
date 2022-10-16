import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

    public class LogoutTest extends SwagLabs_BaseTest {

        @BeforeTest
        @Parameters({"BROWSER", "ENV", "username", "password", "testType", "errorMessage"})
        public void setup(String browser, String env,String username, String password, String testType, @Optional String errorMessage) throws Exception {
            init(browser);
            openApp(env);
            Thread.sleep(3000);
            loginToSwagLabs(username,password,testType,errorMessage);
        }

        @AfterTest
        public void tearDown(){
            quit();
        }

        @Test
        @Parameters({"testType","logoutMessage"})
        public void logOut(String testType, String logoutMessage) throws InterruptedException {
            driver.findElement(By.cssSelector("#react-burger-menu-btn")).click();
            driver.findElement(By.cssSelector("#logout_sidebar_link")).click();
            Thread.sleep(2000);

            if (testType.equals("positive")) {
                String LoginText = driver.findElement(By.cssSelector(".submit-button.btn_action,[value]:nth-child(2)")).getAttribute("value");
                Assert.assertEquals(LoginText, logoutMessage);
            } else {
                Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Products']")).getText(), "PRODUCTS");
            }
        }
    }

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SwagLabs_BaseTest {

    static WebDriver driver;

    public void init(String browser) throws Exception {
        if (browser.equalsIgnoreCase("CHROME")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("FIREFOX")) {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
            driver = new FirefoxDriver();
        } else {
            throw new Exception("Unknown browser: " + browser);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void openApp(String env) throws Exception {
        if (env.equalsIgnoreCase("QA")) {
            driver.get("");
        } else if (env.equalsIgnoreCase("STG")) {
            driver.get("");
        } else if (env.equalsIgnoreCase("PROD")) {
            driver.get("https://www.saucedemo.com/");
        } else {
            throw new Exception("No such environment: " + env);
        }
    }

    public static void ownClick(WebElement element, long timeout) {
        try {
            visibility(element,timeout);
            elementToBeClickable(element,timeout);
            element.click();
        } catch (ElementClickInterceptedException exception){
            System.out.println("Element was not clicked");
        }
    }

    public static WebElement visibility(WebElement element,long timeout){
        WebDriverWait wdWait = new WebDriverWait(driver, 30);
        return wdWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement elementToBeClickable(WebElement element, long timeout){
        WebDriverWait wdWait = new WebDriverWait(driver, 30);
        return wdWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void quit() {
        driver.quit();
    }


    public void loginToSwagLabs(String username, String password, String testType, @Optional String errorMessage) {
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
        driver.findElement(By.xpath("//form/input")).click();

        if (testType.equals("positive")) {
            Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Products']")).getText(), "PRODUCTS");
        } else {
            Assert.assertEquals(driver.findElement(By.xpath("//form//h3")).getText(), errorMessage, "Something went wrong");
        }
    }

    public boolean checkMenuItems(){
        Boolean check = true;
        driver.findElement(By.cssSelector("#react-burger-menu-btn")).click();
        List<WebElement> menu = driver.findElements(By.cssSelector(".bm-item-list"));

        for(int i=0; i<menu.size(); i++ ){
            String str = menu.get(i).getText();
            //ubacena provera sta stvarno pronalazi od elemenata
            System.out.println("Prikazani su" + "\n" + str );
            return check;
        }
        return false;
    }


}



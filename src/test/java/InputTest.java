import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;
import static org.junit.jupiter.api.Assertions.*;

public class InputTest {

    public static RemoteWebDriver driver;
    public static InputPage inputPage;

    @BeforeAll
    public static void setup() throws Exception {

//        FirefoxOptions options = new FirefoxOptions();
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "latest");
        options.setPlatformName("Linux");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "Test badge...");
            put("sessionTimeout", "15m");
            put("env", Arrays.asList("TZ=UTC"));
            put("enableVNC", true);
            put("enableVideo", false);
        }});

        // Берем URL из переменной окружения или используем по умолчанию
        String selenoidUrl = System.getenv().getOrDefault("SELENOID_URL", "http://selenoid:4444/wd/hub");

        driver = new RemoteWebDriver(new URL(selenoidUrl), options);

        inputPage = new InputPage(driver);
        driver.get(ConfProperties.getProperty("inputpage"));
    }


    @Test
    @Epic("Entity Management")
    @Feature("Create")
    @Description("Проверка успешного создания сущности")
    public void testFormSubmission() {

        Allure.step("Открываем страницу");

        inputPage.inputName(ConfProperties.getProperty("name"));
        inputPage.inputPassword(ConfProperties.getProperty("password"));
        inputPage.chooseDrink(ConfProperties.getProperty("drinks").split(","));
        inputPage.chooseColor(ConfProperties.getProperty("color"));
        inputPage.selectOption();

        inputPage.inputEmail(ConfProperties.getProperty("email"));
        inputPage.message();

        inputPage.clickSubmitBtn();
        assertTrue(inputPage.isAlertMessageReceived());
        Allure.step("Закрываем страницу");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
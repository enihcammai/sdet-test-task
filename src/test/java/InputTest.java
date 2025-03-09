import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class InputTest {

    public static WebDriver driver;
    public static InputPage inputPage;

    @BeforeAll
//    @Step("Открываем браузер и переходим на страницу формы")
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        inputPage = new InputPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(ConfProperties.getProperty("inputpage"));
    }

    @Test
//    @Description("Проверка успешного заполнения данными")
    public void testFormSubmission() {

        inputPage.inputName(ConfProperties.getProperty("name"));
        inputPage.inputPassword(ConfProperties.getProperty("password"));
        inputPage.chooseDrink(ConfProperties.getProperty("drinks").split(","));
        inputPage.chooseColor(ConfProperties.getProperty("color"));
        inputPage.selectOption();

        inputPage.inputEmail(ConfProperties.getProperty("email"));
        inputPage.message();

        inputPage.clickSubmitBtn();

//        Allure.step("Проверка сообщения");
        assertTrue(inputPage.isAlertMessageReceived());

    }

    @AfterAll
//    @Step("Закрываем браузер")
    public static void tearDown() {
        driver.quit();
    }

}
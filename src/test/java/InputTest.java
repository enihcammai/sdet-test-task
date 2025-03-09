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
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\SexSexFul\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        inputPage = new InputPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://practice-automation.com/form-fields/");
    }

    @Test
//    @Description("Проверка успешного заполнения данными")
    public void testFormSubmission() {

        inputPage.inputName("Vladislav");
        inputPage.inputPassword("123456");
        inputPage.chooseDrink("milk", "coffee");
        inputPage.chooseColor("yellow");
        inputPage.selectOption();


        inputPage.message();

        inputPage.inputEmail("example@email.com");

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
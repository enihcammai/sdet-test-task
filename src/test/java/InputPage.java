import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class InputPage {

    public WebDriver driver;

    public Actions actions;

    public InputPage(WebDriver driver) {

        PageFactory.initElements(driver, this);

        this.driver = driver;

    }


    @FindBy(id = "name-input")
    private WebElement nameField;

    @FindBy(xpath = "//*[@id=\"feedbackForm\"]/label[2]/input")
    private WebElement passwordField;

    @FindBy(css = "input[title='No fake emails!']")
    private WebElement emailField;

    @FindBy(xpath = "//*[@id=\"message\"]")
    private WebElement messageField;

    @FindBy(id = "submit-btn")
    private WebElement submitBtn;


    public void inputName(String name) {
        nameField.sendKeys(name);
    }

    public void inputPassword(String pass) {
        passwordField.sendKeys(pass);
    }

    public void inputEmail(String email) {
        emailField.sendKeys(email);
    }

    public void chooseDrink(String... drinks) {
        actions = new Actions(driver);
        List<String> newDrinks = List.of(drinks);
        List<WebElement> drinkCheckBoxes = driver.findElements(By.xpath("//*[@name=\"fav_drink\"]"));
        for (WebElement drink : drinkCheckBoxes) {
            if (newDrinks.contains(Objects.requireNonNull(drink.getDomAttribute("value")).toLowerCase())) {
                actions.moveToElement(drink).click().build().perform();
            }
        }
    }

    public void chooseColor(String colors) {
        actions = new Actions(driver);
        List<WebElement> colorRadioBtn = driver.findElements(By.xpath("//*[@name=\"fav_color\"]"));
        for (WebElement color : colorRadioBtn) {
            if (colors.equals(Objects.requireNonNull(color.getDomAttribute("value")).toLowerCase())) {
                actions.moveToElement(color).click().build().perform();
                break;
            }
        }
    }

    public void selectOption() {
        actions = new Actions(driver);
        List<WebElement> listOfOpt;
        WebElement parent = driver.findElement(By.cssSelector("select"));
        listOfOpt = parent.findElements(By.tagName("option"));
        Random random = new Random();
        int randomOpt = random.nextInt(1, listOfOpt.size());
        listOfOpt.get(randomOpt).click();
    }

    public void message() {
        actions = new Actions(driver);
        List<WebElement> listOfTools;
        WebElement parent = driver.findElement(By.xpath("//*[@id=\"feedbackForm\"]/ul"));
        listOfTools = parent.findElements(By.tagName("li"));
        int size = listOfTools.size();

        int longestStr = listOfTools.get(0).getText().length();
        int index = 0;

        for (int i = 0; i < listOfTools.size(); i++) {
            if (listOfTools.get(i).getText().length() > longestStr) {
                longestStr = listOfTools.get(i).getText().length();
                index = i;
            }
        }

        actions.moveToElement(messageField).build().perform();

        messageField.sendKeys(size + ", " + listOfTools.get(index).getText());
    }


    public void clickSubmitBtn() {
        actions.moveToElement(submitBtn).build().perform();
        submitBtn.submit();
    }

    public boolean isAlertMessageReceived() {
        return driver.switchTo().alert().getText().equals("Message received!");
    }

}
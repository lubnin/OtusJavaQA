package OtusQA.Lesson11_PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;

public class OtusMainPage {
    private WebDriver driver;
    private WebDriverWait wait;
    static final Logger logger = LogManager.getLogger(OtusMainPage.class);

    private By loginSelector = By.cssSelector("input[name='email'][type='text']");
    private By passwordSelector = By.cssSelector("input[name='password'][type='password']");
    private By submitSelector = By.cssSelector("button[class='new-button new-button_full new-button_blue new-button_md']");
    private By avatarSelector = By.cssSelector("div[class='header2-menu__icon-img ic-blog-default-avatar']");
    private By myProfileSelector = By.cssSelector("b[class='header2-menu__dropdown-text_name']");

    public OtusMainPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
    }
    public void Open(){
        logger.info("- Открыть https://otus.ru");
        driver.get("https://otus.ru");
        assertEquals ("https://otus.ru/",driver.getCurrentUrl());
    }
    public void Login (String login,String password){
        OtusMainPanel mainPanel = new OtusMainPanel(driver);
        mainPanel.LetsAuth();
        logger.info("- Авторизоваться на сайте");
        WebElement loginElement = wait.until(ExpectedConditions.elementToBeClickable(loginSelector));
        loginElement.sendKeys(login);
        driver.findElement(passwordSelector).sendKeys(password);
        driver.findElement(submitSelector).submit();
    }
    public void OpenProfile() {
        logger.info("- Войти в личный кабинет");
        OtusMainPanel mainPanel = new OtusMainPanel(driver);
        mainPanel.OpenAvaMenu();
        mainPanel.SelectMyProfile();
    }
}

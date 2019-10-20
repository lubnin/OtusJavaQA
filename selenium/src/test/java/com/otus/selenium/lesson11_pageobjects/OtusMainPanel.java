package com.otus.selenium.lesson11_pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OtusMainPanel {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions action;

    static final Logger logger = LogManager.getLogger(OtusMainPanel.class);

    public OtusMainPanel(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
        action = new Actions(driver);
    }

    private By authButtonSelector = By.cssSelector("button[class='header2__auth js-open-modal']");
    private By avatarSelector = By.cssSelector("div[class='header2-menu__icon-img ic-blog-default-avatar']");
    private By myProfileSelector = By.cssSelector("b[class='header2-menu__dropdown-text_name']");

    public void LetsAuth(){
        logger.debug("Нажать на кнопку Вход или Регистрация");
        driver.findElement(authButtonSelector).click();
    }

    public void OpenAvaMenu(){
        WebElement avatarElement = wait.until(ExpectedConditions.presenceOfElementLocated(avatarSelector));
        action
                .moveToElement(avatarElement)
                .perform();
    }
    public void SelectMyProfile(){
        WebElement myProfileElement = wait.until(ExpectedConditions.presenceOfElementLocated(myProfileSelector));
        action
                .moveToElement(myProfileElement)
                .perform();
        myProfileElement.click();
    }
}

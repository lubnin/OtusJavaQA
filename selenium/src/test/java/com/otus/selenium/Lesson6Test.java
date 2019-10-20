/*- Открыть в Chrome сайт Яндекс.Маркет - раздел "Мобильные телефоны"
        - Отфильтровать список товаров по производителю: RedMi и Xiaomi
        - Отсортировать список товаров по цене (от меньшей к большей)
        - Добавить первый в списке RedMi
        -- Проверить, что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        - Добавить первый в списке Xiaomi
        -- Проверить, что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        - Перейти в раздел Сравнение
        -- Проверить, что в списке товаров 2 позиции
        - Нажать на опцию "все характеристики"
        -- Проверить, что в списке характеристик появилась позиция "Операционная система"
        - Нажать на опцию "различающиеся характеристики"
        -- Проверить, что позиция "Операционная система" не отображается в списке характеристик*/

package com.otus.selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Lesson6Test {
    static final Logger logger = LogManager.getLogger(Lesson6Test.class);
    static WebDriver driver = WebDriverFactory.createNewDriver(Browser.CHROME);

    @Test
    public void test(){
        logger.debug("Открыть в Chrome сайт Яндекс.Маркет - раздел \"Мобильные телефоны\"");
        driver.get("https://market.yandex.ru/catalog--mobilnye-telefony/54726/list");

        logger.debug("Отфильтровать список товаров по производителю: Huawei и Xiaomi");
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        selectManufacturer("HUAWEI");
        selectManufacturer("Xiaomi");

        logger.debug("Отсортировать список товаров по цене (от меньшей к большей)");
        driver
                .findElement(By.xpath("//a[contains(@class,'link link_theme_major n-filter-sorter__link') and contains(text(),\"по цене\")]"))
                .click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait
                //.ignoring(ElementClickInterceptedException.class)
                .pollingEvery(Duration.ofMillis(200))
                .withTimeout(Duration.ofSeconds(15))
                .withMessage("Not found ¯\\_(ツ)_/¯")
                .until(items -> getCountOfElementsOnPage() > 10);

        logger.debug("Добавить первый в списке Huawei");
        int itemIndex = getIndexOfTheFirstCellContainsManufacturerName("HUAWEI");
        String itemName = getItemNameByIndex(itemIndex);
        clickCompareBtnByIndex(itemIndex);

        logger.debug("Проверить, что отобразилась плашка \"Товар {имя товара} добавлен к сравнению\"");
        String popupText = getPopupText();
        assertEquals("Товар "+itemName+" добавлен к сравнению",popupText);


        logger.debug("Добавить первый в списке Xiaomi");
        itemIndex = getIndexOfTheFirstCellContainsManufacturerName("Xiaomi");
        itemName = getItemNameByIndex(itemIndex);
        clickCompareBtnByIndex(itemIndex);

        logger.debug("Проверить, что отобразилась плашка \"Товар {имя товара} добавлен к сравнению");
        popupText = getPopupText();
        assertEquals("Товар "+itemName+" добавлен к сравнению",popupText);

        logger.debug("Перейти в раздел Сравнение");
        driver  .findElement(By.xpath("//a[contains(@class,'button')][contains(@href,'/compare')]"))
                .click();

        logger.debug("Проверить, что в списке товаров 2 позиции");
        assertEquals(2, getComparedItemsQty());

        logger.debug("Нажать на опцию \"все характеристики\"");
        By allSpecsLocator = By.xpath("//span[contains(@class,'link__inner')][contains(text(),'все характеристики')]/..");
        wait.until(ExpectedConditions.presenceOfElementLocated(allSpecsLocator));
        driver.findElement(allSpecsLocator).click();

        logger.debug("Проверить, что в списке характеристик появилась позиция \"Операционная система\"");
        By specOSLocator = By.xpath("//div[contains(@class,'n-compare-row-name')][contains(text(),'Операционная система')]");
        boolean condition = wait.until(ExpectedConditions.visibilityOfElementLocated(specOSLocator)).isDisplayed();
        assertTrue(condition);

        logger.debug("Нажать на опцию \"различающиеся характеристики\"");
        By differentSpecsLocator = By.xpath("//span[contains(@class,'link__inner')][contains(text(),'различающиеся характеристики')]/..");
        driver.findElement(differentSpecsLocator).click();
        wait.until (dr -> dr.findElement(differentSpecsLocator).getCssValue("color").equals("rgba(64, 64, 64, 1)"));

        logger.debug("Проверить, что позиция \"Операционная система\" не отображается в списке характеристик");
        condition = driver.findElement(specOSLocator).isDisplayed();
        assertFalse(condition);

//        wait    .ignoring(org.openqa.selenium.NoSuchElementException.class)
//                .pollingEvery(Duration.ofMillis(200))
//                .withTimeout(Duration.ofSeconds(3))
//                .withMessage("Not found ¯\\_(ツ)_/¯")
//                .until(ExpectedConditions.visibilityOf());

    }
    private void selectManufacturer(String firm){
        By manufacturerSelectEl = By.xpath("//input[@type='checkbox' and @name='Производитель "+firm+"']//..");
        WebElement cb = driver.findElement(manufacturerSelectEl);
        cb.click();
        logger.info("В фильтре установлен производитель "+firm);
    }
    private void selectCompareBtnByIndex(int index){
        By compareBtnSelector = By.xpath("/html/body/div[1]/div[5]/div[2]/div[1]/div[2]/div/div[1]/div["+index+"]/div[1]/div/div/div");
        Actions action = new Actions(driver);
        WebElement compareBtn = driver.findElement(compareBtnSelector);
        action.moveToElement(compareBtn).perform();
    }
    private void clickCompareBtnByIndex(int index){
        selectCompareBtnByIndex(index);
        By compareBtnSelector = By.xpath("/html/body/div[1]/div[5]/div[2]/div[1]/div[2]/div/div[1]/div["+index+"]/div[1]/div/div/div");
        WebDriverWait wait = new WebDriverWait(driver,10);
        WebElement compareBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(compareBtnSelector));
        compareBtn.click();
    }

    public boolean isCompareBtnVisible (int index){
        By compareBtnSelector = By.xpath("/html/body/div[1]/div[5]/div[2]/div[1]/div[2]/div/div[1]/div["+index+"]/div[1]/div/div/div");
        WebElement compareBtn = driver.findElement(compareBtnSelector);
        return compareBtn.isDisplayed();
    }
    public List<WebElement> getAllElementsOfPage(){
        String selectAllCells = "//div[contains(@class,'n-snippet-cell2 i-bem b-zone b-spy-visible n-snippet-cell2_type_product b-spy-visible_js_inited')]";
        By allCellsSelector = By.xpath(selectAllCells);
        return driver.findElements(allCellsSelector);
    }
    private int getIndexOfTheFirstCellContainsManufacturerName(String manufacturer){
        String selectAllCells = "//div[contains(@class,'n-snippet-cell2 i-bem b-zone b-spy-visible n-snippet-cell2_type_product b-spy-visible_js_inited')]";
        int manufacturerItemsQty = driver
                .findElements(By.xpath(selectAllCells+"/*/*/*[contains(text(),'"+manufacturer+"')]"))
                .size();
        if(manufacturerItemsQty==0){
            logger.error("На странице нет ни одного товара с "+manufacturer+" в названии");
            throw new org.openqa.selenium.NoSuchElementException("На странице нет ни одного товара с "+manufacturer+" в названии");
        }
        By allCellsSelector = By.xpath(selectAllCells);
        List<WebElement> allCells = driver.findElements(allCellsSelector);

        for (int i=1;i<=allCells.size();i++){
            By firstCellSelector = By.xpath("("+selectAllCells+")["+i+"]/*/*/*[contains(text(),'"+manufacturer+"')]");
            if(driver.findElements(firstCellSelector).size()==1){
                return i;
            };
        }
        logger.error("Что-то пошло не так: текст \""+manufacturer+"\" не найден в названии товаров");
        throw new NoSuchElementException();
    }
    private int getCountOfElementsOnPage(){
        String selectAllCells = "//div[contains(@class,'n-snippet-cell2 i-bem b-zone b-spy-visible n-snippet-cell2_type_product b-spy-visible_js_inited')]";
        By allCellsSelector = By.xpath(selectAllCells);
        List<WebElement> allCells = driver.findElements(allCellsSelector);
        return allCells.size();
    }
    private String getPopupText(){
//        try {
//            driver.findElement(By.xpath("//div[contains(@class,'popup-informer__close')]")).click();
//        } catch (org.openqa.selenium.NoSuchElementException e) {
//
//        }
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='popup-informer__title']")));
        return driver
                .findElement(By.xpath("//div[@class='popup-informer__title']"))
                .getText();
    }
    private String getItemNameByIndex(int index){
        By itemNameLocator = By.xpath("(" +
                "//div" +
                "[contains(@class,'n-snippet-cell2 i-bem b-zone b-spy-visible n-snippet-cell2_type_product b-spy-visible_js_inited')]" +
                ")["+index+"]" +
                "//*" +
                "[contains(@class,'link n-link_theme_blue')]" +
                "[not(contains(@href,'offers'))]");
        return driver.findElement(itemNameLocator).getText();
    }
    private int getComparedItemsQty(){
        return driver.findElements(By.xpath("//div[contains(@class,'n-compare-content__line')]/*")).size();
    }
}

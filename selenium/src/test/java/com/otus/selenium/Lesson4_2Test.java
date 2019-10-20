/*
Доделать задание с урока:
Открыть TestLink
Test Project Management
Create button
Заполнить форму
Сохранить
Проверить, что в таблице ваш проект с настройками (имя, описание, public)
 */

package com.otus.selenium;

import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Lesson4_2Test extends BaseTest
{
    static final Logger logger = LogManager.getLogger(Lesson4_1Test.class);

    @DisplayName("Тест создания проекта в TMS TestLink")
    @Test
    public void test(){
        Allure.step("Открываем TestLink и авторизуемся");
        HomepageOpening();
        Login("admin","verysecretadminpassword");
        CreateAndCheckProject();
        quit();
    }

    public void CreateAndCheckProject(){
        Allure.step("Создадим проект и заполним форму");
        driver.switchTo().frame("mainframe");
        if (driver.findElements(By.cssSelector("a[href$='projectView.php'][class='list-group-item']")).size()==1) {
            logger.info("Открываем страницу создания проекта");
            driver.findElement(By.cssSelector("a[href$='projectView.php'][class='list-group-item']")).click();
        } else {
            logger.error("Не удалось однозначно определить кнопку перехода к созданию проекта \nПопробуйте проверить права учётной записи.");
        }
        driver.findElement(By.cssSelector("input[id=create]")).click();
        String TitleExpected = now();
        String DescrExpected = "Описание тестового проекта";
        String PrefExpected = "Pref";
        driver.findElement(By.cssSelector("input[name=tprojectName][type=text]")).sendKeys(TitleExpected);
        driver.findElement(By.cssSelector("input[name=tcasePrefix][type=text]")).sendKeys(PrefExpected);
        driver  .switchTo()
                .frame(0)
                .findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']"))
                .sendKeys(DescrExpected);
        driver.switchTo().parentFrame();
        driver.findElement(By.cssSelector("input[name=doActionButton][value=Create]")).click();

        List<WebElement> row = driver.findElements(By.xpath("//a[@href and contains (text(),'"+TitleExpected+"')]//..//..//.."));
        //title
        String TitleActual = row.get(2).getText();
        //description
        String DescrActual = row.get(5).getText();
        //prefix
        String PrefActual = row.get(7).getText();
        //public
        int PublicElQty = driver.findElements(By.xpath("//a[@href and contains (text(),'"+TitleExpected+"')]//..//..//img[@title='Public']")).size();
        Allure.step("Проверки заголовка, описания, количества");
        assertEquals(TitleExpected,TitleActual);
        assertEquals(DescrExpected,DescrActual);
        assertEquals(PrefExpected,PrefActual);
        assertEquals(1,PublicElQty);
    }
    public String now(){
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        return date;
    }
}

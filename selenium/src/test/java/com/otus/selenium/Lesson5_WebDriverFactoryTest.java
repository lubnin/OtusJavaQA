package com.otus.selenium;

import io.qameta.allure.junit4.DisplayName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertEquals;
import io.qameta.allure.*;

import java.util.concurrent.TimeUnit;

public class Lesson5_WebDriverFactoryTest {
    static final Logger logger = LogManager.getLogger(Lesson5_WebDriverFactoryTest.class);
    @Test
    @DisplayName("Запуск различных браузеров с настройками и без")
    public void test(){
        Allure.step("Запуск Chrome без настроек");
        browserTest(1,"Chrome", Browser.CHROME);
        Allure.step("Запуск FF без настроек");
        //browserTest(2,"Firefox", Browser.FIREFOX);
        Allure.step("Запуск IE без настроек");
        //browserTest(3,"Internet Explorer", Browser.IE);
        Allure.step("Запуск Edge без настроек");
        //browserTest(4,"Edge", Browser.EDGE);
        Allure.step("Запуск Opera без настроек");
        //browserTest(5,"Opera", Browser.OPERA);
        Allure.step("Запуск Chrome c настройками");
        browserTestWithOptions(1,"Chrome", Browser.CHROME);
        Allure.step("Запуск FF c настройками");
        //browserTestWithOptions(2,"Firefox", Browser.FIREFOX);
        Allure.step("Запуск IE c настройками");
        //browserTestWithOptions(3,"Internet Explorer", Browser.IE);
        Allure.step("Запуск Edge c настройками");
        //browserTestWithOptions(4,"Edge", Browser.EDGE);
        Allure.step("Запуск Opera c настройками");
        //browserTestWithOptions(5,"Opera", Browser.OPERA);
    }
    public void browserTest(int testindex, String browsername, Browser br){
        logger.debug(testindex+". Откроем Яндекс в "+browsername);
        WebDriver wd = WebDriverFactory.createNewDriver(br);
        wd.get("https://ya.ru");
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals(browsername+" успешно инициализировался","Яндекс",wd.getTitle());
        if (br== Browser.IE){
            wd.close();
        }
        if (wd!=null){
            wd.quit();
        }
    }
    public void browserTestWithOptions(int testindex, String browsername, Browser br){
        logger.debug(testindex+". Откроем Яндекс в "+browsername);
        MutableCapabilities opt = new MutableCapabilities();
        opt.setCapability("usingAnyFreePort",true);
        WebDriver wd = WebDriverFactory.createNewDriver(br,opt);
        wd.get("https://ya.ru");
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals(browsername+" успешно инициализировался","Яндекс",wd.getTitle());
        if (br== Browser.IE){
            wd.close();
        }
        if (wd!=null){
            wd.quit();
        }
    }

}

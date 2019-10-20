package com.otus.selenium;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoggerTest {

    @DisplayName("Запуск странички Otus с включенным логированием в консоль и файл")
    @Test
    public void testOtusPage(){
        Lesson2 test = new Lesson2();
        test.pageOpening();
        assertEquals("OTUS - Онлайн-образование", test.getPageTitle());
        test.quit();
    }
}


/*
Написать следующий кейс:
2. Выбрать любой тест, кликнуть на него
3. Проверить что в заголовке теста цвет черный
4. Проставить "Пройден" во всех шагах
5. Нажать на "passed"
6. Проверить что цвет в заголовке теста поменялся на зеленый
7. Проверить что цвет в дереве тестов поменялся на зеленый
8. "Провалить" тест
9, 10 Проверить что цвет стал красным
 */

package com.otus.selenium;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

public class Lesson4_1Test extends BaseTest
{
    static final Logger logger = LogManager.getLogger(Lesson4_1Test.class);
    @DisplayName("Тест изменения цвета тест-кейса в TMS")
    @Description("2. Выбрать любой тест, кликнуть на него\n" +
            "3. Проверить что в заголовке теста цвет черный\n" +
            "4. Проставить \"Пройден\" во всех шагах\n" +
            "5. Нажать на \"passed\"\n" +
            "6. Проверить что цвет в заголовке теста поменялся на зеленый\n" +
            "7. Проверить что цвет в дереве тестов поменялся на зеленый\n" +
            "8. \"Провалить\" тест\n" +
            "9, 10 Проверить что цвет стал красным")
    @Test
    public void test(){
        Allure.step("Открываем страничку с TMS");
        HomepageOpening();
        Allure.step("Авторизация");
        Login("tester","tester");
        Allure.step("Открываем окно прогона тестов");
        LetsRunTest();
        Allure.step("Развернём дерево");
        ExpandTreeView();
        int steps;
        String selectedcase;
        Allure.step("Выберем тест, в котором уже есть шаги");
        do {
            selectedcase = SelectRandomTest();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            steps = getStepsCount();
        } while (steps==0);
        Allure.step("Проверим, что он не запущен");
        CheckTestCaseColor("div.not_run");
        for(int i=1 ; i<=steps ; i++){
            setStepStatus(i,"p");
        }
        Allure.step("Переведём в Passed и проверим цвет");
        setTestCasePassed();
        CheckTestCaseColor("div.passed");
        assertEquals("rgb(213, 238, 213) none repeat scroll 0% 0% / auto padding-box border-box",
                    GetCaseColorFromTree(selectedcase));
        Allure.step("Переведём в Failed и проверим цвет");
        setTestCaseFailed();
        CheckTestCaseColor("div.failed");
        quit();
    }
}


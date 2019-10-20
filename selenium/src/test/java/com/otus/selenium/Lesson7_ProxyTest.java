package com.otus.selenium;

import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import net.lightbody.bmp.proxy.ProxyServer;
import net.lightbody.bmp.core.har.Har;
import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Lesson7_ProxyTest extends WebDriverFactory {
    @DisplayName("Тест с запуском прокси-сервера")
    @Test
    public void test() throws Exception{
        ProxyServer server = new ProxyServer(4567);
        Allure.step("Запуск прокси-сервера");
        server.start();
        Proxy proxy = server.seleniumProxy();
        Allure.step("Откроем Яндекс c прокси");
        logger.debug("Откроем Яндекс c прокси");
        DesiredCapabilities opt = new DesiredCapabilities();
        opt.setCapability(CapabilityType.PROXY, proxy);
        WebDriver wd = WebDriverFactory.createNewDriver(Browser.CHROME, opt);
        Allure.step("Обозначение метки логирования");
        server.newHar("government");
        wd.get("http://government.ru/");
        Har har = server.getHar();
        System.out.println(har);
        Allure.step("Запись в файл и проверка загрузки сайта");
        recordHarToFile(har);
        assertEquals( "Chrome успешно инициализировался", "Правительство России", wd.getTitle());
        wd.quit();
        server.stop();
        logger.info("Прокси-сервер остановлен");
    }
    public void recordHarToFile(Har har) {
        try {
            File file = new File("logs\\har-logs.har");
            if (!file.exists()) {
                logger.info("Файл для har-логов не найден, создаю новый...");
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            try {
                har.writeTo(fos);
            }
            finally {
                logger.info("Лог прокси-сервера успешно записан");
                fos.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package OtusQA;

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

public class Lesson7_Proxy extends WebDriverFactory {
    @Test
    public void test() throws Exception{
        ProxyServer server = new ProxyServer(4567);
        //запуск прокси-сервера
        server.start();
        Proxy proxy = server.seleniumProxy();
        //proxy.setHttpProxy("localhost:5555");
        logger.debug("Откроем Яндекс c прокси");
        DesiredCapabilities opt = new DesiredCapabilities();
        opt.setCapability(CapabilityType.PROXY, proxy);
        WebDriver wd = WebDriverFactory.createNewDriver(Browser.CHROME, opt);
        //Обозначение метки логирования
        server.newHar("government");
        wd.get("http://government.ru/");
        Har har = server.getHar();
        System.out.println(har);
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

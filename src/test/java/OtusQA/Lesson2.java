package OtusQA;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Lesson2
{
    static final Logger logger = LogManager.getLogger(Lesson2.class);

    static {
        logger.debug("Инициализируем драйвер");
        WebDriverManager.chromedriver().setup();
    }

    protected WebDriver driver = new ChromeDriver();

    public void pageOpening()
    {
        logger.info("Развернём окно");
        driver.manage().window().maximize();
        logger.info("Переходим на страницу");
        driver.get("http://www.otus.ru/");
    }
    public String getPageTitle()
    {
        logger.debug("Получаем Title страницы");
        return driver.getTitle();
    }
    public void quit()
    {
        logger.info("Закрываем браузер");
        driver.quit();
    }
}


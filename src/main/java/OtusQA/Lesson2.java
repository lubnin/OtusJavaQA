package OtusQA;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;

public class Lesson2
{
    static final Logger logger = LogManager.getLogger(Lesson2.class);

    public void pageOpening()
    {
        logger.debug("Назначаем директорию с драйвером");
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
        logger.debug("Инициализируем драйвер");
        ChromeDriver driver = new ChromeDriver();
        logger.info("Развернём окно");
        driver.manage().window().maximize();
        logger.info("Переходим на страницу");
        driver.get("http://www.otus.ru/");
        driver.quit();
        logger.warn("Страница закрылась слишком быстро( ");
    }
}


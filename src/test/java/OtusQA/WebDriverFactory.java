package OtusQA;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class WebDriverFactory {

    static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

    public static org.openqa.selenium.WebDriver createNewDriver(browser browser, Options externalOptions){
        WebDriver driver;
        switch (browser){
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                //добавить необходимые опции
                driver = new ChromeDriver(chromeOptions);
                logger.info("Инициализирован драйвер браузера Chrome");
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                //добавить необходимые опции
                driver = new FirefoxDriver(firefoxOptions);
                logger.info("Инициализирован драйвер браузера Firefox");
                break;
            case OPERA:
                WebDriverManager.operadriver().setup();
                OperaOptions options = new OperaOptions();
                options.setBinary("C:\\Program Files\\Opera\\60.0.3255.27\\opera.exe");
                options.addArguments("usingAnyFreePort");
                driver = new OperaDriver(options);
                logger.info("Инициализирован драйвер браузера Opera");
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                logger.info("Инициализирован драйвер браузера Edge (Зачем???))");
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions internetExplorerOptionsOptions = new InternetExplorerOptions();
                //добавить необходимые опции
                driver = new InternetExplorerDriver();
                driver.manage().window().maximize();
                logger.info("Не делай так больше, браузер IE запущен");
                break;
            default: logger.error("Не удалось определить браузер по наименованию Name: "+ browser.toString());
                driver = null;
        }
        return driver;
    }
}

package OtusQA;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

    static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

    public static WebDriver createNewDriver(Browser browser){
        WebDriver driver = createNewDriver(browser,new MutableCapabilities());
        return driver;
    }

    public static WebDriver createNewDriver(Browser browser,  MutableCapabilities options){
        //String driverClassName;
        WebDriver driver;
        MutableCapabilities externalOptions = new MutableCapabilities();
        if (options.asMap().size()!=0){
            externalOptions = options;
            logger.info("При инициализации обнаружены дополнительные настройки");
        }
        switch (browser){
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                //добавить необходимые опции
                chromeOptions.merge(externalOptions);
                driver = new ChromeDriver(chromeOptions);
                //driverClassName = "org.openqa.selenium.chrome.ChromeDriver";
                logger.info("Инициализирован драйвер браузера Chrome");
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                //добавить необходимые опции
                firefoxOptions.merge(externalOptions);
                driver = new FirefoxDriver(firefoxOptions);
                logger.info("Инициализирован драйвер браузера Firefox");
                break;
            case OPERA:
                WebDriverManager.operadriver().setup();
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.setBinary("C:\\Program Files\\Opera\\60.0.3255.27\\opera.exe");
                operaOptions.addArguments("usingAnyFreePort");
                operaOptions.merge(externalOptions);
                driver = new OperaDriver(operaOptions);
                logger.info("Инициализирован драйвер браузера Opera");
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.merge(externalOptions);
                driver = new EdgeDriver(edgeOptions);
                logger.info("Инициализирован драйвер браузера Edge (Зачем???))");
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                //добавить необходимые опции
                internetExplorerOptions.merge(externalOptions);
                driver = new InternetExplorerDriver(internetExplorerOptions);
                driver.manage().window().maximize();
                logger.info("Не делай так больше, браузер IE запущен");
                break;
            default: logger.error("Не удалось определить браузер по наименованию Name: "+ browser.toString());
                //driverClassName = null;
                driver = null;
        }
        /*driverClassName = "org.openqa.selenium.chrome.ChromeDriver";
        WebDriver driver = driverInitialize(driverClassName); */
        return driver;
    }

    /*public static WebDriver driverInitialize(String classFullName) throws ClassNotFoundException{
        Class browserClass = Class.forName(classFullName);
        WebDriver dr = (WebDriver) browserClass.newInstance();
        return dr;
    }*/
    public static void closeBrowser(WebDriver driver){
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toUpperCase();
        try{
            Browser browser = Browser.valueOf(browserName);
            logger.info("Закрываю браузер "+browser.toString());
            if (browser == Browser.IE) {
                driver.close();
            } else {
                driver.quit();
            }
        }catch (IllegalArgumentException e){
            logger.error("Не удалось определить браузер"+ browserName);
            e.printStackTrace();
        }
    }
}

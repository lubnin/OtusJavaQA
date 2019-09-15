package OtusQA;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

public class Lesson5_WebDriverFactory {
    static final Logger logger = LogManager.getLogger(Lesson5_WebDriverFactory.class);

    @Test
    public void test(){
        browserTest(1,"Chrome", Browser.CHROME);
        browserTest(2,"Firefox", Browser.FIREFOX);
        browserTest(3,"Internet Explorer", Browser.IE);
        browserTest(4,"Edge", Browser.EDGE);
        //browserTest(5,"Opera", Browser.OPERA);
        browserTestWithOptions(1,"Chrome", Browser.CHROME);
        browserTestWithOptions(2,"Firefox", Browser.FIREFOX);
        browserTestWithOptions(3,"Internet Explorer", Browser.IE);
        browserTestWithOptions(4,"Edge", Browser.EDGE);
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

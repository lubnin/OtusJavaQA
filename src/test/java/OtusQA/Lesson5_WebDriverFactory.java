package OtusQA;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

public class Lesson5_WebDriverFactory {
    static final Logger logger = LogManager.getLogger(Lesson5_WebDriverFactory.class);

    @Test
    public void test(){
        browserTest(1,"Chrome","cHrOmE");
        browserTest(2,"Firefox","fIrEFOX");
        browserTest(3,"Internet Explorer","Ie");
        //browserTest(4,"Edge","EDGE");
        //browserTest(5,"Opera","opera");

    }
    public void browserTest(int testindex,String browsername,String testbrowsername){
        logger.debug(testindex+". Откроем Яндекс в "+browsername);
        WebDriver wd = WebDriverFactory.createNewDriver(testbrowsername);
        wd.get("https://ya.ru");
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals(browsername+" успешно инициализировался","Яндекс",wd.getTitle());
        wd.quit();
    }
}

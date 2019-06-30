package OtusQA;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;

public class Lesson3 extends BaseTest
{
    //private static final Logger logger = LogManager.getLogger(Lesson3.class);
    /*static {
        WebDriverManager.chromedriver().setup();
        logger.debug("Инициализируем драйвер");
    }*/

    //private WebDriver driver = new ChromeDriver();

    /*public WebDriver GetDriver(){
        //WebDriverManager.chromedriver().setup();
        return driver;
    }*/

    @Test
    public void test(){
        HomepageOpening();
        Login("tester","tester");
        SelectTestsEditTree();
        String tsname = CreateTestSuite("This is a new TestSuite #"+ now(),"Autotest suite");
        SelectTestSuite(tsname);
        String tcname = CreateTestCase("Testcase #1 #"+now(),
                "Описание теста: цель, сценарий и исходное состояние программы",
                "Условия");
        SelectTestCase(tsname,tcname);
        SelectStepAdding();
        AddTestStep("TC 1 Step 1","Step 1 in TC 1 was added");
        AddTestStep("TC 1 Step 2","Step 2 in TC 1 was added");
        AddTestStep("TC 1 Step 3","Step 3 in TC 1 was added");
        SelectTestSuite(tsname);
        tcname = CreateTestCase("Testcase #2 #"+now(),
                "Описание теста: цель, сценарий и исходное состояние программы",
                "Условия");
        SelectTestCase(tsname,tcname);
        SelectStepAdding();
        AddTestStep("TC 2 Step 1","Step 1 in TC 2 was added");
        AddTestStep("TC 2 Step 2","Step 2 in TC 2 was added");
        AddTestStep("TC 2 Step 3","Step 3 in TC 2 was added");
    }

    public void SelectTestsEditTree(){
        logger.info("Разворачиваем дерево");
        driver.switchTo()
                .frame(1)
                .findElement(By.xpath("//a[@class='list-group-item' and contains(@href,'?feature=editTc')]"))
                .click();
    }
    public String CreateTestSuite(String name, String description){
        driver.switchTo()
                .frame(1)
                .findElement(By.cssSelector("img.clickable[title='Действия']"))
                .click();
        driver.findElement(By.cssSelector("input[id=new_testsuite]"))
                .click();
        driver.findElement(By.xpath("//input[@type='text' and @id='name']"))
                .sendKeys(name);
        driver.switchTo()
                .frame(0)
                .findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']"))
                .sendKeys(description);
        driver.switchTo()
                .parentFrame()
                .findElement(By.cssSelector("input[name=add_testsuite_button]"))
                .click();
        //возвращаем драйвер на прежний frame
        driver.switchTo().parentFrame();
        driver.switchTo().parentFrame();
        return name;
    }
    public void SelectTestSuite(String name){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("treeframe");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver  .findElement(By.xpath("//span[contains(text(),'"+name+"')]"))
                .click();
        //возвращаемся на прежний frame
        driver.switchTo().parentFrame();
        driver.switchTo().parentFrame();
    }
    public void ExpandTestSuite(String name){
        Actions action = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//span[contains(text(),'"+name+"')]"));
        action.doubleClick(element).perform();
    }
    public String CreateTestCase(String tcname,String tcdescription,String tcrequirement){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("workframe")
                .findElement(By.cssSelector("img.clickable[title='Действия']"))
                .click();
        driver  .findElement(By.cssSelector("input[name=create_tc][id=create_tc]"))
                .click();
        driver  .findElement(By.cssSelector("input[name=testcase_name][id=testcase_name]"))
                .sendKeys(tcname);
        driver  .switchTo()
                .frame(0)
                .findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']"))
                .sendKeys(tcdescription);
        driver  .switchTo().parentFrame()
                .switchTo().frame(1)
                .findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']"))
                .sendKeys(tcrequirement);
        driver  .switchTo()
                .parentFrame()
                .findElement(By.cssSelector("input[name=do_create_button]"))
                .click();
        //возвращаемся к исходному фрейму
        driver  .switchTo()
                .parentFrame()
                .switchTo()
                .parentFrame();
        return tcname;
    }
    public void SelectTestCase(String tsname,String tcname){

        SelectTestSuite(tsname);
        if (driver  .switchTo().frame("mainframe")
                    .switchTo().frame("treeframe")
                    .findElements(By.xpath("//span[contains(text(),'" + tcname + "')]"))
                    .size()!=0) {
            driver  .findElement(By.xpath("//span[contains(text(),'" + tcname + "')]"))
                    .click();
        } else {
            ExpandTestSuite(tsname);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement element = driver  .findElement(By.xpath("//span[contains(text(),'" + tcname + "')]"));
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            js.executeScript("arguments[0].scrollIntoView(true);",element);
            element.click();
        }
        //возвращаемся к исходному фрейму
        driver  .switchTo()
                .parentFrame()
                .switchTo()
                .parentFrame();
    }
    public void SelectStepAdding(){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("workframe")
                .findElement(By.cssSelector("input[name=create_step]"))
                .click();
        //возвращаемся к исходному фрейму
        driver  .switchTo()
                .parentFrame()
                .switchTo()
                .parentFrame();
    }
    public void AddTestStep(String actions,String expected){
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("workframe");
        driver  .switchTo()
                .frame(0)
                .findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']"))
                .sendKeys(actions);
        driver  .switchTo().parentFrame()
                .switchTo().frame(1)
                .findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']"))
                .sendKeys(expected);

        driver  .switchTo()
                .parentFrame()
                .findElement(By.cssSelector("input[id=do_update_step]"))
                .click();

       /* if(driver   .switchTo()
                    .parentFrame()
                    .findElements(By.cssSelector("input[name=do_create_button][id=do_update_step]"))
                    .size()!=0){
            //Первый step
            driver  .findElement(By.cssSelector("input[name=do_create_button][id=do_update_step]"))
                    .click();
        }else{
            //Последующие шаги
            driver  .findElement(By.cssSelector("input[name=do_update_step][id=do_update_step]"))
                    .click();
        }
*/
        //возвращаемся к исходному фрейму
        driver  .switchTo()
                .parentFrame()
                .switchTo()
                .parentFrame();
    }
    public String now(){
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        return date;
    }
}


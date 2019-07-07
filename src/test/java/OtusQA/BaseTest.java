package OtusQA;

    import io.github.bonigarcia.wdm.WebDriverManager;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;
    import org.junit.After;
    import org.junit.Before;
    import org.junit.BeforeClass;
    import org.openqa.selenium.By;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.chrome.ChromeDriver;
    import java.util.List;
    import java.util.Random;
    import java.util.concurrent.TimeUnit;
    import static org.junit.Assert.assertEquals;

public abstract class BaseTest {
    static final Logger logger = LogManager.getLogger(Lesson4_1.class);
    protected WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        /*if (driver != null) {
            driver.quit();
        }*/
    }

    public void HomepageOpening(){
        driver.manage().window().maximize();
        driver.get("http://localhost/");
        logger.info("Открываем localhost");
    }

    public void Login(String login,String password){
        logger.info("Авторизация на стартовой странице user:"+login+"; password:"+password);
        driver.findElement(By.cssSelector("input[id=tl_login]"))
                .sendKeys(login);
        driver.findElement(By.cssSelector("input[id=tl_password]"))
                .sendKeys(password);
        driver.findElement(By.xpath("//input[@type='submit']"))
                .submit();
    }

    public void LetsRunTest(){
        logger.info("Открываем окно прогона тестов");
        driver  .switchTo().parentFrame()
                .switchTo().frame("titlebar")
                .findElement(By.cssSelector("a[href*=\"executeTest\"][accesskey=e]"))
                .click();
        driver  .switchTo().parentFrame();
        //driver.findElement(By.cssSelector(""));
    }
    public void ExpandTreeView(){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("treeframe")
                .findElement(By.cssSelector("input[id=expand_tree][name=expand_tree]"))
                .click();
        driver  .switchTo().parentFrame()
                .switchTo().parentFrame();
    }
    public void CollapseTreeView(){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("treeframe")
                .findElement(By.cssSelector("input[id=collapse_tree][name=collapse_tree]"))
                .click();
        driver  .switchTo().parentFrame()
                .switchTo().parentFrame();
    }
    public void SelectRandomTest(){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("treeframe");
        List notRunnedTests = driver.findElements(By.xpath("//b[contains(text(),'TP-')]//..//..//span[@class='light_not_run']"));
        if (notRunnedTests.size()>0){
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(notRunnedTests.size());
            logger.info("Выбираем из списка любой не пройденный тест");
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            driver.findElements(By.xpath("//b[contains(text(),'TP-')]//..//..//span[@class='light_not_run']")).get(randomInt).click();
        }else{
            List list = driver.findElements(By.xpath("//b[contains(text(),'TP-')]"));
            logger.error("Все "+list.size()+" тестов уже были запущены");
        }
        driver  .switchTo().parentFrame()
                .switchTo().parentFrame();
    }
    public void CheckTestCaseColor(String expected){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("workframe");
        String actual = driver  .findElement(By.cssSelector("div.not_run"))
                                .getCssValue("background");
        assertEquals(expected,actual);
        driver  .switchTo().parentFrame()
                .switchTo().parentFrame();
    }
    public int getStepsCount(){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("workframe");
        int qty =  driver.findElements(By.xpath("//tr[contains(@id,'step_row_')]")).size();
        driver  .switchTo().parentFrame()
                .switchTo().parentFrame();
        return qty;
    }
    public void setStepStatus(int stepindex,String status){
        driver  .switchTo().frame("mainframe")
                .switchTo().frame("workframe");
        driver.findElement(By.xpath("//tr[contains(@id,'step_row_"+stepindex+"')]"))
                .findElement(By.cssSelector("select[class=step_status]")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//tr[contains(@id,'step_row_"+stepindex+"')]"))
                .findElement(By.cssSelector("option[value="+status+"]")).click();
        driver  .switchTo().parentFrame()
                .switchTo().parentFrame();
    }
}
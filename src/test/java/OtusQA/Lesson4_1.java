/*
Написать следующий кейс:
selectTest()
2. Выбрать любой тест, кликнуть на него
selectRandomTest()
3. Проверить что в заголовке теста цвет черный
4. Проставить "Пройден" во всех шагах
getStepsCount();
setStatus(StepIndex,Status)
5. Нажать на "passed"
setTestPassed
6. Проверить что цвет в заголовке теста поменялся на зеленый
??
7. Проверить что цвет в дереве тестов поменялся на зеленый
getTreeColor
8. "Провалить" тест
setStatus
9, 10 Проверить что цвет стал красным
 */

package OtusQA;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

public class Lesson4_1 extends BaseTest
{
    static final Logger logger = LogManager.getLogger(Lesson4_1.class);

    @Test
    public void test(){
        HomepageOpening();
        Login("tester","tester");
        //runTestsOpening();
        LetsRunTest();
        ExpandTreeView();
        int steps;
        String selectedcase;
        do {
            selectedcase = SelectRandomTest();
            /*********debug****************/
            //SelectTestCase(":Testcase #1 #16-06-2019 23:14:02");
            /*******************************/
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            steps = getStepsCount();
        } while (steps==0);
        CheckTestCaseColor("div.not_run");
        for(int i=1 ; i<=steps ; i++){
            setStepStatus(i,"p");
        }
        setTestCasePassed();
        CheckTestCaseColor("div.passed");
        assertEquals("rgb(213, 238, 213) none repeat scroll 0% 0% / auto padding-box border-box",
                    GetCaseColorFromTree(selectedcase));
        setTestCaseFailed();
        CheckTestCaseColor("div.failed");
        quit();
    }

    public void quit()
    {
        logger.info("Закрываем браузер");
        driver.quit();
    }
}


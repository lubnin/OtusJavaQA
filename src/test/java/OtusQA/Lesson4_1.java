/*
Написать следующий кейс:
selectTest()
2. Выбрать любой тест, кликнуть на него
selectRandomTest()
3. Проверить что в заголовке теста цвет черный
checkTestHeader()
4. Проставить "Пройден" во всех шагах
setStatus(Step,Status)
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

public class Lesson4_1 extends BaseTest
{
    static final Logger logger = LogManager.getLogger(Lesson4_1.class);

    @Test
    public void test(){
        HomepageOpening();
        Login("tester","tester");
        runTestsOpening();
        LetsRunTest();
        ExpandTreeView();
        SelectRandomTest();
        CheckTestCaseColor("rgb(0, 0, 0) none repeat scroll 0% 0% / auto padding-box border-box");
        //quit();
    }

    public void quit()
    {
        logger.info("Закрываем браузер");
        driver.quit();
    }
    public void runTestsOpening(){

    }

}


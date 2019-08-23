package OtusQA;

/*Реализуйте автоматический тест, используя Java + Selenium + POM
        Шаги теста:
        - Открыть https://otus.ru
        - Авторизоваться на сайте
        - Войти в личный кабинет
        - В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        - Нажать сохранить
        - Открыть https://otus.ru в "чистом браузере"
        - Авторизоваться на сайе
        - Войти в личный кабинет
        - Проверить, что в разделе "О себе" отображаются указанные ранее данные

        Домашнее задание принимается в виде ссылки на GitHub репозиторий
        Критерии оценки: +1 балл - код компилируется и запускается
        +1 балл - код запускается без дополнительных действий со стороны проверяющего (не нужно скачивать WebDriver, решать конфликты зависимостей и т.п.)
        +1 балл - логин/пароль для авторизации не зашиты в код (передаются как параметры при старте)
        +1 балл - логи пишутся в консоль и файл
        +1 балл - тест проходит без падений (допускается падение теса только при некорректной работе SUT)
        +1 балла - реализован паттерн PageObject*/


import OtusQA.Lesson11_PageObjects.OtusMainPage;
import OtusQA.Lesson11_PageObjects.OtusProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

@RunWith(JUnitParamsRunner.class)
public class Lesson11_PageObject {
    static final Logger logger = LogManager.getLogger(Lesson11_PageObject.class);
    static WebDriver driver = WebDriverFactory.createNewDriver(Browser.CHROME);
    static WebDriverWait wait = new WebDriverWait(driver,10);

    private Object parametersForLesson11() {
        return new Object[][]{{"LoginValue",   "PasswordValue"}};
    }

    @Test
    @Parameters
    public void lesson11(final String login,final String pwd) {
        //входные данные для запуска
        HashMap<String, String> personalData = new HashMap<>();
        personalData.put("fname","МоёИмя");
        personalData.put("fname_latin","MyName");
        personalData.put("lname","МояФамилия");
        personalData.put("lname_latin","MyLastName");
        personalData.put("blog_name","МойПсевдоним");
        personalData.put("date_of_birth","20.03.1992");
        //формируем хэшмап с контактными данными, которые необходимо будет добавить
        HashMap<String,ArrayList> contacts = new HashMap<>();
        ArrayList<String[]> contact1 = new ArrayList<String[]>();
        ArrayList<String[]> contact2 = new ArrayList<String[]>();
        contact1.add(new String[]{"Telegram", "blabla", "false"});
        contact2.add(new String[]{"Skype", "blai.in", "false"});
        contacts.put("contact1",contact1);
        contacts.put("contact2",contact2);
        //КОНЕЦ ПОДГОТОВКИ ТЕСТОВЫХ ДАННЫХ

        OtusMainPage mainPage = new OtusMainPage(driver);

        mainPage.Open();
        mainPage.Login(login,pwd);
        mainPage.OpenProfile();

        OtusProfile otusProfile = new OtusProfile(driver);
        logger.info("- В разделе \"О себе\" заполнить все поля \"Личные данные\"");
        otusProfile.SetPersonalDataFromMap(personalData);
        logger.info("- и добавить не менее двух контактов");
        otusProfile.AddContactsFromMap(contacts);
        logger.info("- Нажать сохранить");
        otusProfile.SaveProfile();
        WebDriverFactory.closeBrowser(driver);

        logger.info("- Открыть https://otus.ru в \"чистом браузере\"");
        driver = WebDriverFactory.createNewDriver(Browser.CHROME);
        mainPage = new OtusMainPage(driver);
        mainPage.Open();
        logger.info("- Авторизоваться на сайте");
        mainPage.Login(login,pwd);
        logger.info("- Войти в личный кабинет");
        mainPage.OpenProfile();
        logger.info("- Проверить, что в разделе \"О себе\" отображаются указанные ранее данные");
        CheckProfilePersonalData(personalData);
        CheckContact(contact1);
        CheckContact(contact2);
        WebDriverFactory.closeBrowser(driver);
    }
    private void CheckProfilePersonalData(HashMap<String, String> expectedData){
        OtusProfile otusProfile = new OtusProfile(driver);
        assertEquals(expectedData.get("fname"),otusProfile.GetActualPersDataByName("fname"));
        assertEquals(expectedData.get("fname_latin"),otusProfile.GetActualPersDataByName("fname_latin"));
        assertEquals(expectedData.get("lname"),otusProfile.GetActualPersDataByName("lname"));
        assertEquals(expectedData.get("lname_latin"),otusProfile.GetActualPersDataByName("lname_latin"));
        assertEquals(expectedData.get("blog_name"),otusProfile.GetActualPersDataByName("blog_name"));
        assertEquals(expectedData.get("date_of_birth"),otusProfile.GetActualPersDataByName("date_of_birth"));
    }
    private void CheckContact(ArrayList<String[]> expectedData){
        OtusProfile otusProfile = new OtusProfile(driver);
        assertTrue(otusProfile.FindContact(expectedData.get(0)[0].toLowerCase(),expectedData.get(0)[1]));
        /*
        ¯ \ _ (ツ) _ / ¯
        */
    }
}

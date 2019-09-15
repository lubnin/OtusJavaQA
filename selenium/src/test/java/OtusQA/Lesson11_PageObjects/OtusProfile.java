package OtusQA.Lesson11_PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;

public class OtusProfile {
    private static WebDriver driver;
    private WebDriverWait wait;

    static final Logger logger = LogManager.getLogger(OtusProfile.class);

    public OtusProfile(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
    }

    private By fname = By.cssSelector("input[name='fname']");
    private By fname_latin = By.cssSelector("input[name='fname_latin']");
    private By lname = By.cssSelector("input[name='lname']");
    private By lname_latin = By.cssSelector("input[name='lname_latin']");
    private By blog_name = By.cssSelector("input[name='blog_name']");
    private By date_of_birth = By.cssSelector("input[name='date_of_birth']");
    private By contactType;
    private By contactPreferred;
    private By addContactRow = By.cssSelector("button[class='lk-cv-block__action lk-cv-block__action_md-no-spacing js-formset-add js-lk-cv-custom-select-add']");
    private By saveAndContinue = By.cssSelector("button[class='button button_md-4 button_gray lk-cv-action-buttons__button lk-cv-action-buttons__button_gray js-disable-on-submit']");

    public void SaveProfile(){
        logger.info("- Нажать сохранить");
        driver.findElement(saveAndContinue).click();
    }

    public void SetPersonalDataFromMap(HashMap<String,String> map){
        By selector;
        int i=0;
        if (map.isEmpty()) {logger.error("Персональные данные не установлены: "+ map.size()); return;}
        Set<Map.Entry<String,String>> entrySet = map.entrySet();
        for (Map.Entry me: entrySet){
           selector = By.cssSelector("input[name='"+me.getKey()+"']");
           if(driver.findElements(selector).size()==1){
               WebElement el = driver.findElement(selector);
               el.clear();
               el.sendKeys(me.getValue().toString());
               i++;
           }
        }
        assertEquals(map.size(),i);
        logger.info("Все персональные данные были заполнены");
    }

    public WebElement GetPersDataElementByName(String name){
        By selector = By.cssSelector("input[name='"+name+"']");
        return driver.findElement(selector);
    }
    public String GetActualPersDataByName(String name){
        return GetPersDataElementByName(name).getAttribute("value");
    }

    public void AddContactsFromMap(HashMap<String, ArrayList>  map){
        int size = map.size();
        if (size!=0){
            for(Map.Entry me : map.entrySet()){
                AddContact(map.get(me.getKey()));
            }
        }else{
            logger.error("Контактные данные не заданы");
        }
    }

    public void AddContact(ArrayList<String[]> arr) {
        int rowNum = getFreeContactRowNumber();
        SelectContactType(arr.get(0)[0],rowNum);
        driver
                .findElement(By.xpath("(//input[@class='input input_straight-top-left input_straight-bottom-left lk-cv-block__input lk-cv-block__input_9 lk-cv-block__input_md-8'])["+rowNum+"]"))
                .sendKeys(arr.get(0)[1]);
    }
    public int getFreeContactRowNumber(){
        int freeRowNumber;
        By contactValues = By.xpath("//input[@class='input input_straight-top-left " +
                "input_straight-bottom-left lk-cv-block__input " +
                "lk-cv-block__input_9 lk-cv-block__input_md-8']");
        for (freeRowNumber=1; freeRowNumber<=driver.findElements(contactValues).size(); freeRowNumber++){
            By contactValue = By.xpath("(//input[@class='input input_straight-top-left " +
                    "input_straight-bottom-left lk-cv-block__input " +
                    "lk-cv-block__input_9 lk-cv-block__input_md-8'])["+freeRowNumber+"]");
            if(driver.findElement(contactValue).getAttribute("value").equals("")){
                return freeRowNumber;
            }
        }
        driver.findElement(addContactRow).click();
        return freeRowNumber;
    }

    public void SetContactPreferred(int rowIndex){
        By checkboxLocator = By.cssSelector("input[id='id_contact-"+rowIndex+"-preferable']");
        WebElement checkbox = driver.findElement(checkboxLocator);
        if(!checkbox.isSelected()){
            checkbox.click();
        }
    }
    public void SelectContactType(String contactType,int rowIndex){
        By dropDownLocator = By.xpath("(//div[@class='input input_full lk-cv-block__input input_straight-bottom-right input_straight-top-right input_no-border-right lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation'])["+rowIndex+"]");
        driver.findElement(dropDownLocator).click();
        By contactTypeLocator = By.xpath("(//button[contains(@class,'lk-cv-block__select-option js-custom-select-option')][@data-value='"+contactType.toLowerCase()+"'])["+rowIndex+"]");
        driver.findElement(contactTypeLocator).click();
    }
    public boolean FindContact(String contactType,String contactValue){
        By checkingLocator =
                By.xpath("//input[@class='input input_straight-top-left input_straight-bottom-left " +
                        "lk-cv-block__input lk-cv-block__input_9 lk-cv-block__input_md-8']" +
                    "[@value='"+contactValue+"']//..//..//" +
                    "div[@class='input input_full lk-cv-block__input " +
                    "input_straight-bottom-right input_straight-top-right " +
                    "input_no-border-right lk-cv-block__input_fake lk-cv-block__input_select-fake " +
                    "js-custom-select-presentation']/preceding-sibling::input[@value='"+contactType+"']");
        int contactsQty = driver.findElements(checkingLocator).size();
        logger.debug("По контакту "+contactValue+" в "+contactType+" найдено контактов: "+contactsQty);
        return contactsQty==1;
    }
}

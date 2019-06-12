package OtusQA;

import org.openqa.selenium.chrome.ChromeDriver;

public class Lesson2 {

public static void main (String[] args){
    System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
    ChromeDriver driver = new ChromeDriver();
    driver.get("http://www.otus.ru/");
}
}


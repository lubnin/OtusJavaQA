package OtusQA;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoggerTest {
    @Test
    public void testOtusPage(){
        Lesson2 test = new Lesson2();
        test.pageOpening();
        assertEquals("OTUS - Онлайн-образование", test.getPageTitle());
        test.quit();
    }
}


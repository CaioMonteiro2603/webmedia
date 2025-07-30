package deepseek.ws09.seq03;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class TermsOfUseTest extends BaseTest {

    @Test
    public void testTermsOfUsePageLoad() {
        driver.get("https://cac-tat.s3.eu-central-1.amazonaws.com/terms.html");
        assertEquals("Terms of Use", driver.getTitle());
    }

    @Test
    public void testTermsOfUseContent() {
        driver.get("https://cac-tat.s3.eu-central-1.amazonaws.com/terms.html");
        WebElement content = driver.findElement(By.tagName("main"));
        assertTrue(content.getText().contains("Terms of Use"));
        assertTrue(content.getText().contains("agreement"));
    }

    @Test
    public void testBackToMainPageLink() {
        driver.get("https://cac-tat.s3.eu-central-1.amazonaws.com/terms.html");
        WebElement backLink = driver.findElement(By.linkText("Back"));
        backLink.click();
        assertEquals("TAT - Test Automation Training", driver.getTitle());
    }
}
package deepseek.ws09.seq03;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class PrivacyPolicyTest extends BaseTest {

    @Test
    public void testPrivacyPolicyPageLoad() {
        driver.get("https://cac-tat.s3.eu-central-1.amazonaws.com/privacy.html");
        assertEquals("Privacy Policy", driver.getTitle());
    }

    @Test
    public void testPrivacyPolicyContent() {
        driver.get("https://cac-tat.s3.eu-central-1.amazonaws.com/privacy.html");
        WebElement content = driver.findElement(By.tagName("main"));
        assertTrue(content.getText().contains("Privacy Policy"));
        assertTrue(content.getText().contains("personal data"));
    }

    @Test
    public void testBackToMainPageLink() {
        driver.get("https://cac-tat.s3.eu-central-1.amazonaws.com/privacy.html");
        WebElement backLink = driver.findElement(By.linkText("Back"));
        backLink.click();
        assertEquals("TAT - Test Automation Training", driver.getTitle());
    }
}
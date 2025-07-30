package deepseek.ws01.seq04;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class AboutPageTest extends BaseTest {

    @Test
    public void testAboutPageLoads() {
        driver.get(BASE_URL + "about.html");
        assertEquals("About Us - Test Healing", driver.getTitle());
    }

    @Test
    public void testAboutPageContent() {
        driver.get(BASE_URL + "about.html");
        WebElement heading = driver.findElement(By.tagName("h1"));
        assertEquals("About Our Company", heading.getText());
        
        WebElement content = driver.findElement(By.cssSelector(".about-content"));
        assertTrue(content.getText().length() > 0);
    }

    @Test
    public void testBackToHomeLink() {
        driver.get(BASE_URL + "about.html");
        WebElement homeLink = driver.findElement(By.linkText("Back to Home"));
        assertTrue(homeLink.isDisplayed());
        homeLink.click();
        assertEquals(BASE_URL, driver.getCurrentUrl());
    }
}
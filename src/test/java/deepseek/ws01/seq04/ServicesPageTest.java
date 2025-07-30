package deepseek.ws01.seq04;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class ServicesPageTest extends BaseTest {

    @Test
    public void testServicesPageLoads() {
        driver.get(BASE_URL + "services.html");
        assertEquals("Our Services - Test Healing", driver.getTitle());
    }

    @Test
    public void testServiceItems() {
        driver.get(BASE_URL + "services.html");
        
        // Test first service item
        WebElement service1 = driver.findElement(By.cssSelector(".service:nth-child(1)"));
        assertTrue(service1.isDisplayed());
        assertEquals("Web Testing", service1.findElement(By.tagName("h3")).getText());
        
        // Test second service item
        WebElement service2 = driver.findElement(By.cssSelector(".service:nth-child(2)"));
        assertTrue(service2.isDisplayed());
        assertEquals("API Testing", service2.findElement(By.tagName("h3")).getText());
        
        // Test third service item
        WebElement service3 = driver.findElement(By.cssSelector(".service:nth-child(3)"));
        assertTrue(service3.isDisplayed());
        assertEquals("Mobile Testing", service3.findElement(By.tagName("h3")).getText());
    }

    @Test
    public void testContactUsButton() {
        driver.get(BASE_URL + "services.html");
        WebElement contactButton = driver.findElement(By.cssSelector(".btn-contact"));
        assertTrue(contactButton.isDisplayed());
        contactButton.click();
        assertEquals(BASE_URL + "contact.html", driver.getCurrentUrl());
    }
}
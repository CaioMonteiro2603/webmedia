package deepseek.ws03.seq01;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerCareTest extends BaseTest {

    @Test
    public void testContactFormSubmission() {
        driver.get(BASE_URL);
        
        // Navigate to Contact Us
        WebElement contactUs = driver.findElement(By.linkText("Contact Us"));
        contactUs.click();
        assertEquals("ParaBank | Customer Care", driver.getTitle());

        // Fill contact form
        WebElement name = driver.findElement(By.id("name"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement phone = driver.findElement(By.id("phone"));
        WebElement message = driver.findElement(By.id("message"));

        name.sendKeys("John Doe");
        email.sendKeys("john.doe@example.com");
        phone.sendKeys("555-123-4567");
        message.sendKeys("This is a test message for customer care.");

        // Submit form
        WebElement sendButton = driver.findElement(By.cssSelector("input[value='Send to Customer Care']"));
        sendButton.click();

        // Verify submission
        assertTrue(driver.getPageSource().contains("Thank you John Doe"));
        assertTrue(driver.getPageSource().contains("A Customer Care Representative will be contacting you."));
    }

    @Test
    public void testFAQPageAccessibility() {
        driver.get(BASE_URL);
        
        // Navigate to FAQ
        WebElement faqLink = driver.findElement(By.linkText("FAQ"));
        faqLink.click();
        assertEquals("ParaBank | FAQ", driver.getTitle());

        // Verify FAQ content
        assertTrue(driver.findElement(By.cssSelector("h1.title")).getText().contains("Frequently Asked Questions"));
        assertTrue(driver.findElements(By.cssSelector("div#rightPanel p b")).size() > 0);
    }
}
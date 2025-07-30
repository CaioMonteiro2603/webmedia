package deepseek.ws01.seq04;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class ContactPageTest extends BaseTest {

    @Test
    public void testContactPageLoads() {
        driver.get(BASE_URL + "contact.html");
        assertEquals("Contact Us - Test Healing", driver.getTitle());
    }

    @Test
    public void testContactFormElements() {
        driver.get(BASE_URL + "contact.html");
        
        // Test name field
        WebElement nameField = driver.findElement(By.id("name"));
        assertTrue(nameField.isDisplayed());
        nameField.sendKeys("Test User");
        assertEquals("Test User", nameField.getAttribute("value"));
        
        // Test email field
        WebElement emailField = driver.findElement(By.id("email"));
        assertTrue(emailField.isDisplayed());
        emailField.sendKeys("test@example.com");
        assertEquals("test@example.com", emailField.getAttribute("value"));
        
        // Test message field
        WebElement messageField = driver.findElement(By.id("message"));
        assertTrue(messageField.isDisplayed());
        messageField.sendKeys("This is a test message");
        assertEquals("This is a test message", messageField.getAttribute("value"));
    }

    @Test
    public void testFormSubmission() {
        driver.get(BASE_URL + "contact.html");
        
        // Fill out the form
        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("message")).sendKeys("This is a test message");
        
        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        assertTrue(submitButton.isDisplayed());
        submitButton.click();
        
        // Verify success message
        WebElement successMessage = driver.findElement(By.cssSelector(".alert-success"));
        assertTrue(successMessage.isDisplayed());
        assertTrue(successMessage.getText().contains("Thank you for your message"));
    }

    @Test
    public void testBackToHomeLink() {
        driver.get(BASE_URL + "contact.html");
        WebElement homeLink = driver.findElement(By.linkText("Back to Home"));
        assertTrue(homeLink.isDisplayed());
        homeLink.click();
        assertEquals(BASE_URL, driver.getCurrentUrl());
    }
}
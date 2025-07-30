package deepseek.ws03.seq03;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerCareTest extends BaseTest {

    @Test
    public void testContactForm() {
        driver.findElement(By.linkText("Contact")).click();
        assertEquals("ParaBank | Customer Care", driver.getTitle());
        
        WebElement nameField = driver.findElement(By.id("name"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement phoneField = driver.findElement(By.id("phone"));
        WebElement messageField = driver.findElement(By.id("message"));
        WebElement sendButton = driver.findElement(By.xpath("//input[@value='Send to Customer Care']"));
        
        assertTrue(nameField.isDisplayed());
        assertTrue(emailField.isDisplayed());
        assertTrue(phoneField.isDisplayed());
        assertTrue(messageField.isDisplayed());
        assertTrue(sendButton.isEnabled());
        
        // Fill out the form
        nameField.sendKeys("Test User");
        emailField.sendKeys("test@example.com");
        phoneField.sendKeys("555-123-4567");
        messageField.sendKeys("This is a test message");
        sendButton.click();
        
        WebElement confirmation = driver.findElement(By.xpath("//p[contains(text(), 'Thank you')]"));
        assertTrue(confirmation.isDisplayed());
    }

    @Test
    public void testForgotLoginInfo() {
        driver.findElement(By.linkText("Forgot login info?")).click();
        assertEquals("ParaBank | Customer Lookup", driver.getTitle());
        
        WebElement firstName = driver.findElement(By.id("firstName"));
        WebElement lastName = driver.findElement(By.id("lastName"));
        WebElement address = driver.findElement(By.id("address.street"));
        WebElement city = driver.findElement(By.id("address.city"));
        WebElement state = driver.findElement(By.id("address.state"));
        WebElement zipCode = driver.findElement(By.id("address.zipCode"));
        WebElement ssn = driver.findElement(By.id("ssn"));
        WebElement findButton = driver.findElement(By.xpath("//input[@value='Find My Login Info']"));
        
        // Fill out the form
        firstName.sendKeys("John");
        lastName.sendKeys("Smith");
        address.sendKeys("123 Main St");
        city.sendKeys("Beverly Hills");
        state.sendKeys("CA");
        zipCode.sendKeys("90210");
        ssn.sendKeys("123-45-6789");
        findButton.click();
        
        WebElement results = driver.findElement(By.id("lookupResults"));
        assertTrue(results.isDisplayed());
    }
}
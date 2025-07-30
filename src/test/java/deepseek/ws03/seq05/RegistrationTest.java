package deepseek.ws03.seq05;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest extends BaseTest {

    @Test
    public void testRegistrationFormSubmission() {
        // Navigate to registration page
        driver.findElement(By.linkText("Register")).click();

        // Fill out registration form
        driver.findElement(By.id("customer.firstName")).sendKeys("Test");
        driver.findElement(By.id("customer.lastName")).sendKeys("User");
        driver.findElement(By.id("customer.address.street")).sendKeys("123 Main St");
        driver.findElement(By.id("customer.address.city")).sendKeys("Anytown");
        driver.findElement(By.id("customer.address.state")).sendKeys("CA");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("90210");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("555-123-4567");
        driver.findElement(By.id("customer.ssn")).sendKeys("123-45-6789");
        driver.findElement(By.id("customer.username")).sendKeys("testuser" + System.currentTimeMillis());
        driver.findElement(By.id("customer.password")).sendKeys("password");
        driver.findElement(By.id("repeatedPassword")).sendKeys("password");

        // Submit form
        driver.findElement(By.xpath("//input[@value='Register']")).click();

        // Verify successful registration
        WebElement successMessage = driver.findElement(By.xpath("//h1[@class='title' and contains(text(), 'Welcome')]"));
        assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testFormValidation() {
        // Navigate to registration page
        driver.findElement(By.linkText("Register")).click();

        // Submit empty form
        driver.findElement(By.xpath("//input[@value='Register']")).click();

        // Verify validation errors
        assertTrue(driver.findElement(By.className("error")).isDisplayed());
        assertEquals("First name is required.", 
            driver.findElement(By.id("customer.firstName.errors")).getText());
    }
}
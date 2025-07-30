package deepseek.ws03.seq02;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationPageTest extends ParabankBaseTest {

    @Test
    public void testRegistrationFormValidation() {
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("customer.firstName")).sendKeys("Test");
        driver.findElement(By.id("customer.lastName")).sendKeys("User");
        driver.findElement(By.id("customer.address.street")).sendKeys("123 Main St");
        driver.findElement(By.id("customer.address.city")).sendKeys("Anytown");
        driver.findElement(By.id("customer.address.state")).sendKeys("CA");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("90210");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("555-123-4567");
        driver.findElement(By.id("customer.ssn")).sendKeys("123-45-6789");
        driver.findElement(By.id("customer.username")).sendKeys("testuser");
        driver.findElement(By.id("customer.password")).sendKeys("password");
        driver.findElement(By.id("repeatedPassword")).sendKeys("password");
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Welcome')]")).isDisplayed());
    }

    @Test
    public void testPasswordMismatchValidation() {
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("customer.password")).sendKeys("password");
        driver.findElement(By.id("repeatedPassword")).sendKeys("different");
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        
        assertTrue(driver.findElement(By.className("error")).isDisplayed());
        assertEquals("Passwords did not match.", driver.findElement(By.className("error")).getText());
    }

    @Test
    public void testRequiredFieldValidation() {
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        
        assertTrue(driver.findElement(By.className("error")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//span[@id='customer.firstName.errors']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//span[@id='customer.lastName.errors']")).isDisplayed());
    }
}
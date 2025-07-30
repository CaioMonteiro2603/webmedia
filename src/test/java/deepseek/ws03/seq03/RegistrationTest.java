package deepseek.ws03.seq03;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest extends BaseTest {

    @Test
    public void testRegistrationForm() {
        driver.findElement(By.linkText("Register")).click();
        assertEquals("ParaBank | Register for Free Online Account Access", driver.getTitle());
        
        // Verify all form fields
        WebElement firstName = driver.findElement(By.id("customer.firstName"));
        WebElement lastName = driver.findElement(By.id("customer.lastName"));
        WebElement address = driver.findElement(By.id("customer.address.street"));
        WebElement city = driver.findElement(By.id("customer.address.city"));
        WebElement state = driver.findElement(By.id("customer.address.state"));
        WebElement zipCode = driver.findElement(By.id("customer.address.zipCode"));
        WebElement phone = driver.findElement(By.id("customer.phoneNumber"));
        WebElement ssn = driver.findElement(By.id("customer.ssn"));
        WebElement username = driver.findElement(By.id("customer.username"));
        WebElement password = driver.findElement(By.id("customer.password"));
        WebElement confirm = driver.findElement(By.id("repeatedPassword"));
        WebElement registerButton = driver.findElement(By.xpath("//input[@value='Register']"));
        
        // Fill out the form
        firstName.sendKeys("Test");
        lastName.sendKeys("User");
        address.sendKeys("123 Test St");
        city.sendKeys("Testville");
        state.sendKeys("CA");
        zipCode.sendKeys("90210");
        phone.sendKeys("555-123-4567");
        ssn.sendKeys("123-45-6789");
        username.sendKeys("testuser" + System.currentTimeMillis());
        password.sendKeys("password");
        confirm.sendKeys("password");
        registerButton.click();
        
        // Verify registration success
        WebElement welcomeMessage = driver.findElement(By.xpath("//h1[contains(text(), 'Welcome')]"));
        assertTrue(welcomeMessage.isDisplayed());
    }
}
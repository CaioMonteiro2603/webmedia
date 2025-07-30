package deepseek.ws02.seq03;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTest extends BaseTest {

    @Test
    public void testLoginPageElements() {
        // Verify page title
        assertEquals("Swag Labs", driver.getTitle());
        
        // Verify logo is displayed
        WebElement logo = driver.findElement(By.className("login_logo"));
        assertTrue(logo.isDisplayed());
        
        // Verify username field
        WebElement usernameField = driver.findElement(By.id("user-name"));
        assertTrue(usernameField.isDisplayed());
        assertTrue(usernameField.isEnabled());
        
        // Verify password field
        WebElement passwordField = driver.findElement(By.id("password"));
        assertTrue(passwordField.isDisplayed());
        assertTrue(passwordField.isEnabled());
        
        // Verify login button
        WebElement loginButton = driver.findElement(By.className("btn_action"));
        assertTrue(loginButton.isDisplayed());
        assertTrue(loginButton.isEnabled());
        assertEquals("LOGIN", loginButton.getAttribute("value"));
    }

    @Test
    public void testSuccessfulLogin() {
        // Enter valid credentials
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        
        // Verify navigation to inventory page
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void testFailedLoginWithInvalidCredentials() {
        // Enter invalid credentials
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.className("btn_action")).click();
        
        // Verify error message
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Epic sadface: Username and password do not match any user in this service", 
                     errorMessage.getText());
    }

    @Test
    public void testLoginWithLockedOutUser() {
        // Enter locked out user credentials
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        
        // Verify error message
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Epic sadface: Sorry, this user has been locked out.", 
                     errorMessage.getText());
    }
}
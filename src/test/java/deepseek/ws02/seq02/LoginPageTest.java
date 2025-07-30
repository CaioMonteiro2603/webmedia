package deepseek.ws02.seq02;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTest extends BaseTest {

    @Test
    public void testPageTitle() {
        assertEquals("Swag Labs", driver.getTitle());
    }

    @Test
    public void testLoginFormElementsPresent() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.className("btn_action"));

        assertTrue(usernameField.isDisplayed());
        assertTrue(passwordField.isDisplayed());
        assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void testSuccessfulLogin() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();

        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void testFailedLoginWithInvalidCredentials() {
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.className("btn_action")).click();

        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Epic sadface: Username and password do not match any user in this service", 
                     errorMessage.getText());
    }

    @Test
    public void testLoginWithLockedOutUser() {
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();

        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Epic sadface: Sorry, this user has been locked out.", 
                     errorMessage.getText());
    }

    @Test
    public void testLoginWithEmptyCredentials() {
        driver.findElement(By.className("btn_action")).click();

        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Epic sadface: Username is required", 
                     errorMessage.getText());
    }
}
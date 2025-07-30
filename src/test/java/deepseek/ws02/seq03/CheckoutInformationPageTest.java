package deepseek.ws02.seq03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutInformationPageTest extends BaseTest {

    @BeforeEach
    public void navigateToCheckoutInformation() {
        // Login, add item, go to cart, and proceed to checkout
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        driver.findElement(By.xpath("(//button[contains(@class,'btn_inventory')])[1]")).click();
        driver.findElement(By.id("shopping_cart_container")).click();
        driver.findElement(By.className("checkout_button")).click();
    }

    @Test
    public void testCheckoutInformationPageElements() {
        // Verify page title
        assertEquals("Swag Labs", driver.getTitle());
        
        // Verify header elements
        WebElement header = driver.findElement(By.className("app_logo"));
        assertTrue(header.isDisplayed());
        assertEquals("Swag Labs", header.getText());
        
        // Verify input fields
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        assertTrue(firstNameField.isDisplayed());
        assertTrue(firstNameField.isEnabled());
        
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        assertTrue(lastNameField.isDisplayed());
        assertTrue(lastNameField.isEnabled());
        
        WebElement zipCodeField = driver.findElement(By.id("postal-code"));
        assertTrue(zipCodeField.isDisplayed());
        assertTrue(zipCodeField.isEnabled());
        
        // Verify cancel button
        WebElement cancelButton = driver.findElement(By.className("cart_cancel_link"));
        assertTrue(cancelButton.isDisplayed());
        assertEquals("CANCEL", cancelButton.getText());
        
        // Verify continue button
        WebElement continueButton = driver.findElement(By.xpath("//input[@value='CONTINUE']"));
        assertTrue(continueButton.isDisplayed());
        assertTrue(continueButton.isEnabled());
    }

    @Test
    public void testSuccessfulCheckoutInformationSubmission() {
        // Fill in checkout information
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
        
        // Verify navigation to checkout overview page
        assertEquals("https://www.saucedemo.com/v1/checkout-step-two.html", driver.getCurrentUrl());
    }

    @Test
    public void testCheckoutWithMissingInformation() {
        // Try to continue without filling information
        driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
        
        // Verify error message
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Error: First Name is required", errorMessage.getText());
    }

    @Test
    public void testCancelCheckoutNavigation() {
        // Click cancel button
        driver.findElement(By.className("cart_cancel_link")).click();
        
        // Verify navigation back to cart page
        assertEquals("https://www.saucedemo.com/v1/cart.html", driver.getCurrentUrl());
    }

    @Test
    public void testNavigationToCartFromCheckoutInformation() {
        // Click cart icon
        driver.findElement(By.id("shopping_cart_container")).click();
        
        // Verify navigation to cart page
        assertEquals("https://www.saucedemo.com/v1/cart.html", driver.getCurrentUrl());
    }
}
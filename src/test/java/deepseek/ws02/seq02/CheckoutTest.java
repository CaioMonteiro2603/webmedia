package deepseek.ws02.seq02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest extends BaseTest {

    @BeforeEach
    public void loginAndNavigateToCheckout() {
        // Login and add item to cart
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        driver.findElement(By.xpath("(//button[contains(text(), 'ADD TO CART')])[1]")).click();
        
        // Navigate to checkout step one
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.className("checkout_button")).click();
    }

    @Test
    public void testCheckoutStepOnePageLoads() {
        assertEquals("https://www.saucedemo.com/v1/checkout-step-one.html", driver.getCurrentUrl());
        assertEquals("Checkout: Your Information", driver.findElement(By.className("subheader")).getText());
    }

    @Test
    public void testCheckoutFormElementsPresent() {
        WebElement firstName = driver.findElement(By.id("first-name"));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement postalCode = driver.findElement(By.id("postal-code"));
        WebElement continueButton = driver.findElement(By.className("cart_button"));
        WebElement cancelButton = driver.findElement(By.className("cart_cancel_link"));

        assertTrue(firstName.isDisplayed());
        assertTrue(lastName.isDisplayed());
        assertTrue(postalCode.isDisplayed());
        assertTrue(continueButton.isDisplayed());
        assertTrue(cancelButton.isDisplayed());
    }

    @Test
    public void testCheckoutWithEmptyFields() {
        driver.findElement(By.className("cart_button")).click();
        
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Error: First Name is required", errorMessage.getText());
    }

    @Test
    public void testCheckoutWithValidInformation() {
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.className("cart_button")).click();

        assertEquals("https://www.saucedemo.com/v1/checkout-step-two.html", driver.getCurrentUrl());
    }

    @Test
    public void testCheckoutStepTwoPageElements() {
        // Complete step one
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.className("cart_button")).click();

        // Verify step two elements
        assertEquals("Checkout: Overview", driver.findElement(By.className("subheader")).getText());
        assertTrue(driver.findElement(By.className("cart_list")).isDisplayed());
        assertTrue(driver.findElement(By.className("summary_info")).isDisplayed());
        assertTrue(driver.findElement(By.className("summary_total_label")).isDisplayed());
        assertTrue(driver.findElement(By.className("cart_cancel_link")).isDisplayed());
        assertTrue(driver.findElement(By.className("cart_button")).isDisplayed());
    }

    @Test
    public void testCancelCheckoutFromStepOne() {
        driver.findElement(By.className("cart_cancel_link")).click();
        assertEquals("https://www.saucedemo.com/v1/cart.html", driver.getCurrentUrl());
    }

    @Test
    public void testCancelCheckoutFromStepTwo() {
        // Complete step one
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.className("cart_button")).click();

        // Cancel from step two
        driver.findElement(By.className("cart_cancel_link")).click();
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void testCompleteOrder() {
        // Complete step one
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.className("cart_button")).click();

        // Complete order
        driver.findElement(By.className("cart_button")).click();
        assertEquals("https://www.saucedemo.com/v1/checkout-complete.html", driver.getCurrentUrl());
        assertEquals("THANK YOU FOR YOUR ORDER", driver.findElement(By.className("complete-header")).getText());
    }

    @Test
    public void testBackHomeButton() {
        // Complete entire checkout process
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.className("cart_button")).click();
        driver.findElement(By.className("cart_button")).click();

        // Click back home
        driver.findElement(By.className("btn_primary")).click();
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
    }
}
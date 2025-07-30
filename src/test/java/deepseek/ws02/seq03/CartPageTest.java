package deepseek.ws02.seq03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CartPageTest extends BaseTest {

    @BeforeEach
    public void navigateToCartWithItem() {
        // Login, add item to cart, and navigate to cart
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        driver.findElement(By.xpath("(//button[contains(@class,'btn_inventory')])[1]")).click();
        driver.findElement(By.id("shopping_cart_container")).click();
    }

    @Test
    public void testCartPageElements() {
        // Verify page title
        assertEquals("Swag Labs", driver.getTitle());
        
        // Verify header elements
        WebElement header = driver.findElement(By.className("app_logo"));
        assertTrue(header.isDisplayed());
        assertEquals("Swag Labs", header.getText());
        
        // Verify cart contents
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(1, cartItems.size());
        
        // Verify continue shopping button
        WebElement continueShoppingButton = driver.findElement(By.className("cart_cancel_link"));
        assertTrue(continueShoppingButton.isDisplayed());
        assertEquals("CONTINUE SHOPPING", continueShoppingButton.getText());
        
        // Verify checkout button
        WebElement checkoutButton = driver.findElement(By.className("checkout_button"));
        assertTrue(checkoutButton.isDisplayed());
        assertEquals("CHECKOUT", checkoutButton.getText());
    }

    @Test
    public void testRemoveItemFromCart() {
        // Remove item from cart
        WebElement removeButton = driver.findElement(By.xpath("//button[contains(@class,'cart_button')]"));
        removeButton.click();
        
        // Verify cart is empty
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(0, cartItems.size());
    }

    @Test
    public void testContinueShoppingNavigation() {
        // Click continue shopping button
        driver.findElement(By.className("cart_cancel_link")).click();
        
        // Verify navigation back to inventory page
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void testCheckoutNavigation() {
        // Click checkout button
        driver.findElement(By.className("checkout_button")).click();
        
        // Verify navigation to checkout information page
        assertEquals("https://www.saucedemo.com/v1/checkout-step-one.html", driver.getCurrentUrl());
    }

    @Test
    public void testEmptyCartBehavior() {
        // Remove item to make cart empty
        driver.findElement(By.xpath("//button[contains(@class,'cart_button')]")).click();
        
        // Verify checkout button is disabled
        WebElement checkoutButton = driver.findElement(By.className("checkout_button"));
        assertFalse(checkoutButton.isEnabled());
    }
}
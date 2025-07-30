package deepseek.ws02.seq02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CartPageTest extends BaseTest {

    @BeforeEach
    public void loginAndAddItems() {
        // Login and add items to cart
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        
        // Add two items to cart
        driver.findElement(By.xpath("(//button[contains(text(), 'ADD TO CART')])[1]")).click();
        driver.findElement(By.xpath("(//button[contains(text(), 'ADD TO CART')])[2]")).click();
        
        // Navigate to cart
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @Test
    public void testCartPageLoads() {
        assertEquals("https://www.saucedemo.com/v1/cart.html", driver.getCurrentUrl());
        assertEquals("Your Cart", driver.findElement(By.className("subheader")).getText());
    }

    @Test
    public void testCartItemsDisplayed() {
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(2, cartItems.size());
        
        for (WebElement item : cartItems) {
            assertTrue(item.findElement(By.className("inventory_item_name")).isDisplayed());
            assertTrue(item.findElement(By.className("inventory_item_desc")).isDisplayed());
            assertTrue(item.findElement(By.className("inventory_item_price")).isDisplayed());
            assertTrue(item.findElement(By.tagName("img")).isDisplayed());
            assertTrue(item.findElement(By.className("cart_quantity")).isDisplayed());
            assertTrue(item.findElement(By.xpath(".//button[contains(text(), 'REMOVE')]")).isDisplayed());
        }
    }

    @Test
    public void testRemoveItemFromCart() {
        List<WebElement> removeButtons = driver.findElements(By.xpath("//button[contains(text(), 'REMOVE')]"));
        removeButtons.get(0).click();
        
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(1, cartItems.size());
        
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("1", cartBadge.getText());
    }

    @Test
    public void testContinueShoppingButton() {
        driver.findElement(By.className("cart_footer")).findElement(By.className("btn_secondary")).click();
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void testCheckoutButton() {
        driver.findElement(By.className("cart_footer")).findElement(By.className("checkout_button")).click();
        assertEquals("https://www.saucedemo.com/v1/checkout-step-one.html", driver.getCurrentUrl());
    }

    @Test
    public void testEmptyCart() {
        // Remove all items
        List<WebElement> removeButtons = driver.findElements(By.xpath("//button[contains(text(), 'REMOVE')]"));
        for (WebElement button : removeButtons) {
            button.click();
        }
        
        // Verify cart is empty
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(0, cartItems.size());
        
        // Verify checkout button is disabled (implementation may vary)
        WebElement checkoutButton = driver.findElement(By.className("checkout_button"));
        assertTrue(checkoutButton.isDisplayed());
    }
}
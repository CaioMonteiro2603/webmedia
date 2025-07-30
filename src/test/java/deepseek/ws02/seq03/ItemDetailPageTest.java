package deepseek.ws02.seq03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ItemDetailPageTest extends BaseTest {

    @BeforeEach
    public void navigateToItemDetail() {
        // Login and navigate to first item's detail page
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        driver.findElement(By.xpath("(//div[@class='inventory_item_name'])[1]")).click();
    }

    @Test
    public void testItemDetailPageElements() {
        // Verify page title
        assertEquals("Swag Labs", driver.getTitle());
        
        // Verify back button
        WebElement backButton = driver.findElement(By.className("inventory_details_back_button"));
        assertTrue(backButton.isDisplayed());
        
        // Verify item image
        WebElement itemImage = driver.findElement(By.className("inventory_details_img"));
        assertTrue(itemImage.isDisplayed());
        
        // Verify item name
        WebElement itemName = driver.findElement(By.className("inventory_details_name"));
        assertTrue(itemName.isDisplayed());
        assertFalse(itemName.getText().isEmpty());
        
        // Verify item description
        WebElement itemDesc = driver.findElement(By.className("inventory_details_desc"));
        assertTrue(itemDesc.isDisplayed());
        assertFalse(itemDesc.getText().isEmpty());
        
        // Verify item price
        WebElement itemPrice = driver.findElement(By.className("inventory_details_price"));
        assertTrue(itemPrice.isDisplayed());
        assertTrue(itemPrice.getText().startsWith("$"));
        
        // Verify add to cart button
        WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(@class,'btn_inventory')]"));
        assertTrue(addToCartButton.isDisplayed());
        assertEquals("ADD TO CART", addToCartButton.getText());
    }

    @Test
    public void testAddToCartFromDetailPage() {
        // Add item to cart
        WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(@class,'btn_inventory')]"));
        addToCartButton.click();
        
        // Verify button text changes
        assertEquals("REMOVE", addToCartButton.getText());
        
        // Verify cart badge updates
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("1", cartBadge.getText());
    }

    @Test
    public void testRemoveFromCartFromDetailPage() {
        // Add and then remove item from cart
        WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(@class,'btn_inventory')]"));
        addToCartButton.click();
        addToCartButton.click();
        
        // Verify button text changes back
        assertEquals("ADD TO CART", addToCartButton.getText());
        
        // Verify cart badge disappears
        List<WebElement> cartBadges = driver.findElements(By.className("shopping_cart_badge"));
        assertTrue(cartBadges.isEmpty());
    }

    @Test
    public void testBackToProductsNavigation() {
        // Click back button
        driver.findElement(By.className("inventory_details_back_button")).click();
        
        // Verify navigation back to inventory page
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void testNavigationToCartFromDetailPage() {
        // Click on cart icon
        driver.findElement(By.id("shopping_cart_container")).click();
        
        // Verify navigation to cart page
        assertEquals("https://www.saucedemo.com/v1/cart.html", driver.getCurrentUrl());
    }
}
package deepseek.ws02.seq03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryPageTest extends BaseTest {

    @BeforeEach
    public void login() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
    }

    @Test
    public void testInventoryPageElements() {
        // Verify page title
        assertEquals("Swag Labs", driver.getTitle());
        
        // Verify header elements
        WebElement header = driver.findElement(By.className("app_logo"));
        assertTrue(header.isDisplayed());
        assertEquals("Swag Labs", header.getText());
        
        // Verify product sort container
        WebElement sortContainer = driver.findElement(By.className("product_sort_container"));
        assertTrue(sortContainer.isDisplayed());
        
        // Verify inventory items
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        assertEquals(6, items.size());
        
        // Verify shopping cart link
        WebElement cartLink = driver.findElement(By.id("shopping_cart_container"));
        assertTrue(cartLink.isDisplayed());
    }

    @Test
    public void testAddToCartFunctionality() {
        // Add first item to cart
        WebElement addToCartButton = driver.findElement(By.xpath("(//button[contains(@class,'btn_inventory')])[1]"));
        addToCartButton.click();
        
        // Verify button text changes to "REMOVE"
        assertEquals("REMOVE", addToCartButton.getText());
        
        // Verify cart badge updates
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("1", cartBadge.getText());
    }

    @Test
    public void testRemoveFromCartFunctionality() {
        // Add and then remove item from cart
        WebElement addToCartButton = driver.findElement(By.xpath("(//button[contains(@class,'btn_inventory')])[1]"));
        addToCartButton.click();
        addToCartButton.click(); // Click again to remove
        
        // Verify button text changes back to "ADD TO CART"
        assertEquals("ADD TO CART", addToCartButton.getText());
        
        // Verify cart badge is not displayed
        List<WebElement> cartBadges = driver.findElements(By.className("shopping_cart_badge"));
        assertTrue(cartBadges.isEmpty());
    }

    @Test
    public void testProductSorting() {
        // Sort by price low to high
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Price (low to high)']")).click();
        
        // Verify sorting
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        for (int i = 0; i < prices.size() - 1; i++) {
            double price1 = Double.parseDouble(prices.get(i).getText().replace("$", ""));
            double price2 = Double.parseDouble(prices.get(i+1).getText().replace("$", ""));
            assertTrue(price1 <= price2);
        }
    }

    @Test
    public void testNavigationToItemDetail() {
        // Click on first item
        WebElement itemName = driver.findElement(By.xpath("(//div[@class='inventory_item_name'])[1]"));
        String expectedName = itemName.getText();
        itemName.click();
        
        // Verify navigation to item detail page
        assertEquals("https://www.saucedemo.com/v1/inventory-item.html", 
                    driver.getCurrentUrl().split("\\?")[0]);
        
        // Verify item name matches
        WebElement detailName = driver.findElement(By.className("inventory_details_name"));
        assertEquals(expectedName, detailName.getText());
    }

    @Test
    public void testNavigationToCart() {
        // Click on cart icon
        driver.findElement(By.id("shopping_cart_container")).click();
        
        // Verify navigation to cart page
        assertEquals("https://www.saucedemo.com/v1/cart.html", driver.getCurrentUrl());
    }
}
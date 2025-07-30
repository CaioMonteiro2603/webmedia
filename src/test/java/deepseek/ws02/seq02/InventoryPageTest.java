package deepseek.ws02.seq02;

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
    public void testInventoryPageLoads() {
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
        assertEquals("Products", driver.findElement(By.className("product_label")).getText());
    }

    @Test
    public void testProductItemsDisplayed() {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        assertEquals(6, items.size());
        
        for (WebElement item : items) {
            assertTrue(item.findElement(By.className("inventory_item_name")).isDisplayed());
            assertTrue(item.findElement(By.className("inventory_item_desc")).isDisplayed());
            assertTrue(item.findElement(By.className("inventory_item_price")).isDisplayed());
            assertTrue(item.findElement(By.tagName("img")).isDisplayed());
        }
    }

    @Test
    public void testAddToCartButtons() {
        List<WebElement> addButtons = driver.findElements(By.xpath("//button[contains(text(), 'ADD TO CART')]"));
        assertEquals(6, addButtons.size());
        
        for (WebElement button : addButtons) {
            assertTrue(button.isDisplayed());
            assertTrue(button.isEnabled());
        }
    }

    @Test
    public void testAddItemToCart() {
        driver.findElement(By.xpath("(//button[contains(text(), 'ADD TO CART')])[1]")).click();
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("1", cartBadge.getText());
    }

    @Test
    public void testRemoveButtonAppearsAfterAdding() {
        driver.findElement(By.xpath("(//button[contains(text(), 'ADD TO CART')])[1]")).click();
        WebElement removeButton = driver.findElement(By.xpath("(//button[contains(text(), 'REMOVE')])[1]"));
        assertTrue(removeButton.isDisplayed());
    }

    @Test
    public void testSortingDropdown() {
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        assertTrue(sortDropdown.isDisplayed());
        assertEquals(4, sortDropdown.findElements(By.tagName("option")).size());
    }

    @Test
    public void testSortByNameAZ() {
        driver.findElement(By.className("product_sort_container")).sendKeys("az");
        List<WebElement> items = driver.findElements(By.className("inventory_item_name"));
        assertEquals("Sauce Labs Backpack", items.get(0).getText());
        assertEquals("Test.allTheThings() T-Shirt (Red)", items.get(5).getText());
    }

    @Test
    public void testSortByNameZA() {
        driver.findElement(By.className("product_sort_container")).sendKeys("za");
        List<WebElement> items = driver.findElements(By.className("inventory_item_name"));
        assertEquals("Test.allTheThings() T-Shirt (Red)", items.get(0).getText());
        assertEquals("Sauce Labs Backpack", items.get(5).getText());
    }

    @Test
    public void testSortByPriceLowHigh() {
        driver.findElement(By.className("product_sort_container")).sendKeys("lohi");
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        assertEquals("$7.99", prices.get(0).getText());
        assertEquals("$49.99", prices.get(5).getText());
    }

    @Test
    public void testSortByPriceHighLow() {
        driver.findElement(By.className("product_sort_container")).sendKeys("hilo");
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        assertEquals("$49.99", prices.get(0).getText());
        assertEquals("$7.99", prices.get(5).getText());
    }

    @Test
    public void testMenuButtonFunctionality() {
        driver.findElement(By.className("bm-burger-button")).click();
        WebElement menu = driver.findElement(By.className("bm-menu"));
        assertTrue(menu.isDisplayed());
        
        List<WebElement> menuItems = driver.findElements(By.className("bm-item"));
        assertEquals(4, menuItems.size());
        assertEquals("ALL ITEMS", menuItems.get(0).getText());
        assertEquals("ABOUT", menuItems.get(1).getText());
        assertEquals("LOGOUT", menuItems.get(2).getText());
        assertEquals("RESET APP STATE", menuItems.get(3).getText());
    }

    @Test
    public void testAboutLink() {
        driver.findElement(By.className("bm-burger-button")).click();
        driver.findElement(By.id("about_sidebar_link")).click();
        assertTrue(driver.getCurrentUrl().startsWith("https://saucelabs.com/"));
    }

    @Test
    public void testLogoutFunctionality() {
        driver.findElement(By.className("bm-burger-button")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();
        assertEquals("https://www.saucedemo.com/v1/index.html", driver.getCurrentUrl());
    }
}
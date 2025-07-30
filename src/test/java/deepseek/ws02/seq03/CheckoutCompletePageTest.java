package deepseek.ws02.seq03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CheckoutCompletePageTest extends BaseTest {

    @BeforeEach
    public void navigateToCheckoutComplete() {
        // Login, add item, go through all checkout steps
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        driver.findElement(By.xpath("(//button[contains(@class,'btn_inventory')])[1]")).click();
        driver.findElement(By.id("shopping_cart_container")).click();
        driver.findElement(By.className("checkout_button")).click();
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
        driver.findElement(By.className("cart_button")).click();
    }

    @Test
    public void testCheckoutCompletePageElements() {
        // Verify page title
        assertEquals("Swag Labs", driver.getTitle());
        
        // Verify header elements
        WebElement header = driver.findElement(By.className("app_logo"));
        assertTrue(header.isDisplayed());
        assertEquals("Swag Labs", header.getText());
        
        // Verify complete message
        WebElement completeHeader = driver.findElement(By.className("complete-header"));
        assertTrue(completeHeader.isDisplayed());
        assertEquals("THANK YOU FOR YOUR ORDER", completeHeader.getText());
        
        // Verify complete text
        WebElement completeText = driver.findElement(By.className("complete-text"));
        assertTrue(completeText.isDisplayed());
        assertEquals("Your order has been dispatched, and will arrive just as fast as the pony can get there!", 
                     completeText.getText());
        
        // Verify pony express image
        WebElement ponyImage = driver.findElement(By.className("pony_express"));
        assertTrue(ponyImage.isDisplayed());
        
        // Verify back home button
        WebElement backHomeButton = driver.findElement(By.className("btn_primary"));
        assertTrue(backHomeButton.isDisplayed());
        assertEquals("BACK HOME", backHomeButton.getText());
    }

    @Test
    public void testBackHomeNavigation() {
        // Click back home button
        driver.findElement(By.className("btn_primary")).click();
        
        // Verify navigation back to inventory page
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
        
        // Verify cart is empty after successful purchase
        List<WebElement> cartBadges = driver.findElements(By.className("shopping_cart_badge"));
        assertTrue(cartBadges.isEmpty());
    }

    @Test
    public void testNavigationToCartFromCheckoutComplete() {
        // Click cart icon
        driver.findElement(By.id("shopping_cart_container")).click();
        
        // Verify navigation to cart page (should be empty)
        assertEquals("https://www.saucedemo.com/v1/cart.html", driver.getCurrentUrl());
        
        // Verify cart is empty
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(0, cartItems.size());
    }
}
package deepseek.ws02.seq03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutOverviewPageTest extends BaseTest {

    @BeforeEach
    public void navigateToCheckoutOverview() {
        // Login, add item, go through checkout steps
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
    }

    @Test
    public void testCheckoutOverviewPageElements() {
        // Verify page title
        assertEquals("Swag Labs", driver.getTitle());
        
        // Verify header elements
        WebElement header = driver.findElement(By.className("app_logo"));
        assertTrue(header.isDisplayed());
        assertEquals("Swag Labs", header.getText());
        
        // Verify cart contents
        WebElement cartItem = driver.findElement(By.className("cart_item"));
        assertTrue(cartItem.isDisplayed());
        
        // Verify payment information
        WebElement paymentInfo = driver.findElement(By.xpath("//div[contains(text(),'Payment Information')]"));
        assertTrue(paymentInfo.isDisplayed());
        
        // Verify shipping information
        WebElement shippingInfo = driver.findElement(By.xpath("//div[contains(text(),'Shipping Information')]"));
        assertTrue(shippingInfo.isDisplayed());
        
        // Verify price total
        WebElement priceTotal = driver.findElement(By.xpath("//div[contains(text(),'Price Total')]"));
        assertTrue(priceTotal.isDisplayed());
        
        // Verify cancel button
        WebElement cancelButton = driver.findElement(By.className("cart_cancel_link"));
        assertTrue(cancelButton.isDisplayed());
        assertEquals("CANCEL", cancelButton.getText());
        
        // Verify finish button
        WebElement finishButton = driver.findElement(By.className("cart_button"));
        assertTrue(finishButton.isDisplayed());
        assertEquals("FINISH", finishButton.getText());
    }

    @Test
    public void testCompletePurchase() {
        // Click finish button
        driver.findElement(By.className("cart_button")).click();
        
        // Verify navigation to checkout complete page
        assertEquals("https://www.saucedemo.com/v1/checkout-complete.html", driver.getCurrentUrl());
    }

    @Test
    public void testCancelPurchase() {
        // Click cancel button
        driver.findElement(By.className("cart_cancel_link")).click();
        
        // Verify navigation back to inventory page
        assertEquals("https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void testPriceCalculation() {
        // Get item price
        WebElement itemPriceElement = driver.findElement(By.className("inventory_item_price"));
        String itemPriceText = itemPriceElement.getText();
        double itemPrice = Double.parseDouble(itemPriceText.replace("$", ""));
        
        // Get subtotal
        WebElement subtotalElement = driver.findElement(By.className("summary_subtotal_label"));
        String subtotalText = subtotalElement.getText();
        double subtotal = Double.parseDouble(subtotalText.replace("Item total: $", ""));
        
        // Verify subtotal matches item price
        assertEquals(itemPrice, subtotal, 0.001);
        
        // Get tax
        WebElement taxElement = driver.findElement(By.className("summary_tax_label"));
        String taxText = taxElement.getText();
        double tax = Double.parseDouble(taxText.replace("Tax: $", ""));
        
        // Verify tax calculation (8% of subtotal)
        assertEquals(subtotal * 0.08, tax, 0.001);
        
        // Get total
        WebElement totalElement = driver.findElement(By.className("summary_total_label"));
        String totalText = totalElement.getText();
        double total = Double.parseDouble(totalText.replace("Total: $", ""));
        
        // Verify total calculation
        assertEquals(subtotal + tax, total, 0.001);
    }

    @Test
    public void testNavigationToCartFromCheckoutOverview() {
        // Click cart icon
        driver.findElement(By.id("shopping_cart_container")).click();
        
        // Verify navigation to cart page
        assertEquals("https://www.saucedemo.com/v1/cart.html", driver.getCurrentUrl());
    }
}
package deepseek.ws02.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SauceDemoTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://www.saucedemo.com/v1/index.html";
    
    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @Test
    public void testHomePageLoadsSuccessfully() {
        driver.get(BASE_URL);
        
        // Verify page title
        Assertions.assertEquals("Swag Labs", driver.getTitle());
        
        // Verify logo is displayed
        WebElement logo = driver.findElement(By.className("login_logo"));
        Assertions.assertTrue(logo.isDisplayed());
        
        // Verify login form elements are present
        Assertions.assertTrue(driver.findElement(By.id("user-name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("password")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.className("btn_action")).isDisplayed());
    }
    
    @Test
    public void testSuccessfulLogin() {
        driver.get(BASE_URL);
        
        // Enter credentials and login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        
        // Verify successful login by checking inventory page elements
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        Assertions.assertTrue(driver.findElement(By.className("product_label")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.className("inventory_list")).isDisplayed());
    }
    
    @Test
    public void testFailedLogin() {
        driver.get(BASE_URL);
        
        // Enter invalid credentials
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.className("btn_action")).click();
        
        // Verify error message is displayed
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("h3[data-test='error']")));
        Assertions.assertEquals("Epic sadface: Username and password do not match any user in this service", 
            errorElement.getText());
    }
    
    @Test
    public void testInventoryPageNavigation() {
        // First login
        driver.get(BASE_URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        
        // Test all navigation elements
        testMenuNavigation();
        testProductNavigation();
        testSocialMediaLinks();
        testShoppingCart();
    }
    
    private void testMenuNavigation() {
        // Open menu
        driver.findElement(By.className("bm-burger-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu-wrap")));
        
        // Verify menu items
        List<WebElement> menuItems = driver.findElements(By.cssSelector(".bm-item.menu-item"));
        Assertions.assertEquals(4, menuItems.size());
        
        // Test About link
        driver.findElement(By.id("about_sidebar_link")).click();
        wait.until(ExpectedConditions.urlToBe("https://saucelabs.com/"));
        Assertions.assertTrue(driver.getTitle().contains("Sauce Labs"));
        
        // Go back to inventory
        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        
        // Open menu again
        driver.findElement(By.className("bm-burger-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu-wrap")));
        
        // Test Logout
        driver.findElement(By.id("logout_sidebar_link")).click();
        wait.until(ExpectedConditions.urlContains("index.html"));
        Assertions.assertTrue(driver.findElement(By.className("login_logo")).isDisplayed());
    }
    
    private void testProductNavigation() {
        // Login again after logout
        driver.get(BASE_URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        
        // Get all product items
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        Assertions.assertTrue(products.size() > 0);
        
        // Test each product link
        for (int i = 0; i < products.size(); i++) {
            // Get product name and price
            String productName = products.get(i).findElement(By.className("inventory_item_name")).getText();
            String productPrice = products.get(i).findElement(By.className("inventory_item_price")).getText();
            
            // Click on product
            products.get(i).findElement(By.className("inventory_item_name")).click();
            wait.until(ExpectedConditions.urlContains("inventory-item.html"));
            
            // Verify product details page
            Assertions.assertEquals(productName, 
                driver.findElement(By.className("inventory_details_name")).getText());
            Assertions.assertEquals(productPrice, 
                driver.findElement(By.className("inventory_details_price")).getText());
            
            // Go back to inventory
            driver.findElement(By.className("inventory_details_back_button")).click();
            wait.until(ExpectedConditions.urlContains("inventory.html"));
            
            // Refresh products list
            products = driver.findElements(By.className("inventory_item"));
        }
    }
    
    private void testSocialMediaLinks() {
        // Test Twitter link
        WebElement twitterLink = driver.findElement(By.cssSelector(".social_twitter a"));
        Assertions.assertEquals("https://twitter.com/saucelabs", twitterLink.getAttribute("href"));
        
        // Test Facebook link
        WebElement facebookLink = driver.findElement(By.cssSelector(".social_facebook a"));
        Assertions.assertEquals("https://www.facebook.com/saucelabs", facebookLink.getAttribute("href"));
        
        // Test LinkedIn link
        WebElement linkedinLink = driver.findElement(By.cssSelector(".social_linkedin a"));
        Assertions.assertEquals("https://www.linkedin.com/company/sauce-labs/", linkedinLink.getAttribute("href"));
    }
    
    private void testShoppingCart() {
        // Add first product to cart
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        products.get(0).findElement(By.className("btn_primary")).click();
        
        // Verify cart badge updated
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.className("shopping_cart_badge")));
        Assertions.assertEquals("1", cartBadge.getText());
        
        // Go to cart
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
        
        // Verify cart page
        Assertions.assertTrue(driver.findElement(By.className("cart_list")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.className("cart_item")).isDisplayed());
        
        // Continue shopping
        driver.findElement(By.className("btn_secondary")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        
        // Remove item from cart
        products = driver.findElements(By.className("inventory_item"));
        products.get(0).findElement(By.className("btn_secondary")).click();
        
        // Verify cart is empty
        Assertions.assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());
    }
    
    @Test
    public void testCheckoutProcess() {
        // Login
        driver.get(BASE_URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("btn_action")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        
        // Add product to cart
        driver.findElement(By.className("btn_primary")).click();
        
        // Go to cart
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
        
        // Proceed to checkout
        driver.findElement(By.className("checkout_button")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        
        // Fill checkout information
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.className("cart_button")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        
        // Verify order summary
        Assertions.assertTrue(driver.findElement(By.className("summary_info")).isDisplayed());
        
        // Finish checkout
        driver.findElement(By.className("cart_button")).click();
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
        
        // Verify order completion
        Assertions.assertTrue(driver.findElement(By.className("complete-header")).isDisplayed());
        Assertions.assertEquals("THANK YOU FOR YOUR ORDER", 
            driver.findElement(By.className("complete-header")).getText());
        
        // Back to home
        driver.findElement(By.className("btn_primary")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }
}
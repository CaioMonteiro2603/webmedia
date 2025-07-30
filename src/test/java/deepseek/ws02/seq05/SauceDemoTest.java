package deepseek.ws02.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

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
    
    // Helper method to switch to new window/tab
    private void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        windows.remove(originalWindow);
        driver.switchTo().window(windows.iterator().next());
    }
    
    // Helper method to verify page title
    private void verifyPageTitle(String expectedTitle) {
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        Assertions.assertEquals(expectedTitle, driver.getTitle());
    }
    
    // Helper method to login
    private void login(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector(".btn_action")).click();
    }
    
    // Test cases will be added here

    @Test
    public void testMainPageLoadsSuccessfully() {
        driver.get(BASE_URL);
        verifyPageTitle("Swag Labs");
        Assertions.assertTrue(driver.findElement(By.className("login_logo")).isDisplayed());
    }

    @Test
    public void testLoginFormElementsPresent() {
        driver.get(BASE_URL);
        Assertions.assertTrue(driver.findElement(By.id("user-name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("password")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".btn_action")).isDisplayed());
    }

    @Test
    public void testLoginWithValidCredentials() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        verifyPageTitle("Swag Labs");
        Assertions.assertTrue(driver.findElement(By.className("product_label")).isDisplayed());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        driver.get(BASE_URL);
        login("invalid_user", "wrong_password");
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
        Assertions.assertEquals("Epic sadface: Username and password do not match any user in this service",
                driver.findElement(By.cssSelector("[data-test='error']")).getText());
    }

    @Test
    public void testLoginWithLockedOutUser() {
        driver.get(BASE_URL);
        login("locked_out_user", "secret_sauce");
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
        Assertions.assertEquals("Epic sadface: Sorry, this user has been locked out.",
                driver.findElement(By.cssSelector("[data-test='error']")).getText());
    }

    @Test
    public void testLoginWithEmptyCredentials() {
        driver.get(BASE_URL);
        driver.findElement(By.cssSelector(".btn_action")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
        Assertions.assertEquals("Epic sadface: Username is required",
                driver.findElement(By.cssSelector("[data-test='error']")).getText());
    }

    // Inventory Page Tests
    @Test
    public void testInventoryPageElements() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Verify inventory items
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        Assertions.assertEquals(6, items.size());
        
        // Verify sort dropdown
        Assertions.assertTrue(driver.findElement(By.className("product_sort_container")).isDisplayed());
        
        // Verify shopping cart
        Assertions.assertTrue(driver.findElement(By.id("shopping_cart_container")).isDisplayed());
        
        // Verify menu button
        Assertions.assertTrue(driver.findElement(By.cssSelector(".bm-burger-button")).isDisplayed());
    }

    @Test
    public void testAddToCartFunctionality() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Add first item to cart
        driver.findElement(By.cssSelector(".btn_primary.btn_inventory")).click();
        
        // Verify cart badge updates
        Assertions.assertEquals("1", driver.findElement(By.cssSelector(".shopping_cart_badge")).getText());
        
        // Verify button changes to "Remove"
        Assertions.assertEquals("REMOVE", driver.findElement(By.cssSelector(".btn_secondary.btn_inventory")).getText());
    }

    @Test
    public void testRemoveFromCartFunctionality() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Add and then remove item
        driver.findElement(By.cssSelector(".btn_primary.btn_inventory")).click();
        driver.findElement(By.cssSelector(".btn_secondary.btn_inventory")).click();
        
        // Verify cart badge disappears
        Assertions.assertTrue(driver.findElements(By.cssSelector(".shopping_cart_badge")).isEmpty());
        
        // Verify button changes back to "Add to cart"
        Assertions.assertEquals("ADD TO CART", driver.findElement(By.cssSelector(".btn_primary.btn_inventory")).getText());
    }

    @Test
    public void testItemDetailsPage() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Click on first item
        driver.findElement(By.cssSelector(".inventory_item_name")).click();
        
        // Verify details page
        Assertions.assertTrue(driver.findElement(By.cssSelector(".inventory_details_name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".inventory_details_desc")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".inventory_details_price")).isDisplayed());
        
        // Verify back button
        driver.findElement(By.cssSelector(".inventory_details_back_button")).click();
        verifyPageTitle("Swag Labs");
    }

    @Test
    public void testShoppingCartPage() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Add item and go to cart
        driver.findElement(By.cssSelector(".btn_primary.btn_inventory")).click();
        driver.findElement(By.cssSelector(".shopping_cart_container")).click();
        
        // Verify cart page
        verifyPageTitle("Swag Labs");
        Assertions.assertTrue(driver.findElement(By.cssSelector(".cart_list")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".cart_quantity")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".btn_action.checkout_button")).isDisplayed());
    }

    @Test
    public void testCheckoutProcess() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Add item and go to checkout
        driver.findElement(By.cssSelector(".btn_primary.btn_inventory")).click();
        driver.findElement(By.cssSelector(".shopping_cart_container")).click();
        driver.findElement(By.cssSelector(".btn_action.checkout_button")).click();
        
        // Fill checkout info
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();
        
        // Verify checkout overview
        Assertions.assertTrue(driver.findElement(By.cssSelector(".summary_info")).isDisplayed());
        
        // Complete checkout
        driver.findElement(By.cssSelector(".btn_action.cart_button")).click();
        
        // Verify completion page
        Assertions.assertTrue(driver.findElement(By.cssSelector(".complete-header")).isDisplayed());
        Assertions.assertEquals("THANK YOU FOR YOUR ORDER", 
            driver.findElement(By.cssSelector(".complete-header")).getText());
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Test Twitter link
        driver.findElement(By.cssSelector(".social_twitter a")).click();
        switchToNewWindow();
        Assertions.assertTrue(driver.getCurrentUrl().contains("twitter.com"));
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
        
        // Test Facebook link
        driver.findElement(By.cssSelector(".social_facebook a")).click();
        switchToNewWindow();
        Assertions.assertTrue(driver.getCurrentUrl().contains("facebook.com"));
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
        
        // Test LinkedIn link
        driver.findElement(By.cssSelector(".social_linkedin a")).click();
        switchToNewWindow();
        Assertions.assertTrue(driver.getCurrentUrl().contains("linkedin.com"));
        driver.close();
    }

    @Test
    public void testMenuFunctionality() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Open menu
        driver.findElement(By.cssSelector(".bm-burger-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_sidebar_link")));
        
        // Verify menu items
        Assertions.assertTrue(driver.findElement(By.id("inventory_sidebar_link")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("about_sidebar_link")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("logout_sidebar_link")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("reset_sidebar_link")).isDisplayed());
        
        // Test logout
        driver.findElement(By.id("logout_sidebar_link")).click();
        verifyPageTitle("Swag Labs");
        Assertions.assertTrue(driver.findElement(By.cssSelector(".login_logo")).isDisplayed());
    }

    @Test
    public void testSortingFunctionality() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Test Name (A to Z) sorting
        Select sortDropdown = new Select(driver.findElement(By.className("product_sort_container")));
        sortDropdown.selectByValue("az");
        List<WebElement> items = driver.findElements(By.className("inventory_item_name"));
        Assertions.assertTrue(items.get(0).getText().compareTo(items.get(1).getText()) < 0);
        
        // Test Name (Z to A) sorting
        sortDropdown.selectByValue("za");
        items = driver.findElements(By.className("inventory_item_name"));
        Assertions.assertTrue(items.get(0).getText().compareTo(items.get(1).getText()) > 0);
        
        // Test Price (low to high) sorting
        sortDropdown.selectByValue("lohi");
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        double firstPrice = Double.parseDouble(prices.get(0).getText().substring(1));
        double secondPrice = Double.parseDouble(prices.get(1).getText().substring(1));
        Assertions.assertTrue(firstPrice <= secondPrice);
        
        // Test Price (high to low) sorting
        sortDropdown.selectByValue("hilo");
        prices = driver.findElements(By.className("inventory_item_price"));
        firstPrice = Double.parseDouble(prices.get(0).getText().substring(1));
        secondPrice = Double.parseDouble(prices.get(1).getText().substring(1));
        Assertions.assertTrue(firstPrice >= secondPrice);
    }

    @Test
    public void testCheckoutWithEmptyCart() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Go directly to checkout
        driver.findElement(By.cssSelector(".shopping_cart_container")).click();
        driver.findElement(By.cssSelector(".btn_action.checkout_button")).click();
        
        // Should still be on cart page
        verifyPageTitle("Swag Labs");
        Assertions.assertTrue(driver.findElement(By.cssSelector(".cart_list")).isDisplayed());
    }

    @Test
    public void testCheckoutWithMissingInformation() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Add item and go to checkout
        driver.findElement(By.cssSelector(".btn_primary.btn_inventory")).click();
        driver.findElement(By.cssSelector(".shopping_cart_container")).click();
        driver.findElement(By.cssSelector(".btn_action.checkout_button")).click();
        
        // Try to continue with empty fields
        driver.findElement(By.cssSelector(".btn_primary.cart_button")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
        Assertions.assertEquals("Error: First Name is required",
                driver.findElement(By.cssSelector("[data-test='error']")).getText());
    }

    @Test
    public void testAboutPageFromMenu() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Open menu and click About
        driver.findElement(By.cssSelector(".bm-burger-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("about_sidebar_link")));
        driver.findElement(By.id("about_sidebar_link")).click();
        
        // Verify we're on Sauce Labs website
        switchToNewWindow();
        Assertions.assertTrue(driver.getCurrentUrl().contains("saucelabs.com"));
        driver.close();
    }

    @Test
    public void testResetAppState() {
        driver.get(BASE_URL);
        login("standard_user", "secret_sauce");
        
        // Add item to cart
        driver.findElement(By.cssSelector(".btn_primary.btn_inventory")).click();
        Assertions.assertEquals("1", driver.findElement(By.cssSelector(".shopping_cart_badge")).getText());
        
        // Open menu and reset
        driver.findElement(By.cssSelector(".bm-burger-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reset_sidebar_link")));
        driver.findElement(By.id("reset_sidebar_link")).click();
        
        // Verify cart is empty
        Assertions.assertTrue(driver.findElements(By.cssSelector(".shopping_cart_badge")).isEmpty());
    }

    // Add missing import for Select class
    @Override
    protected void finalize() throws Throwable {
        if (driver != null) {
            driver.quit();
        }
        super.finalize();
    }
}
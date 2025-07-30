package claudesonnet4.ws02.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Selenium WebDriver Test Suite for SauceDemo Website
 * Tests all pages one level below main URL and external links
 * 
 * Main URL: https://www.saucedemo.com/v1/index.html
 * 
 * Pages tested:
 * 1. Login Page (/v1/index.html)
 * 2. Inventory Page (/v1/inventory.html)
 * 3. Cart Page (/v1/cart.html)
 * 4. Checkout Step 1 (/v1/checkout-step-one.html)
 * 5. Checkout Step 2 (/v1/checkout-step-two.html)
 * 6. Checkout Complete (/v1/checkout-complete.html)
 * 7. External Link (https://saucelabs.com/)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SauceDemoTestSuite {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://www.saucedemo.com/v1/";
    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String LOCKED_USERNAME = "locked_out_user";
    private static final String PROBLEM_USERNAME = "problem_user";
    private static final String PERFORMANCE_USERNAME = "performance_glitch_user";
    
    @BeforeAll
    static void setupClass() {
        // Setup Chrome driver with options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode for CI/CD
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @BeforeEach
    void setUp() {
        // Navigate to login page before each test
        driver.get(BASE_URL + "index.html");
    }
    
    // ==================== LOGIN PAGE TESTS ====================
    
    @Test
    @Order(1)
    @DisplayName("Test Login Page - Valid Login with Standard User")
    void testValidLoginStandardUser() {
        // Verify login page elements
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        assertTrue(loginButton.isDisplayed(), "Login button should be visible");
        
        // Perform login
        usernameField.sendKeys(VALID_USERNAME);
        passwordField.sendKeys(VALID_PASSWORD);
        loginButton.click();
        
        // Verify successful login - should redirect to inventory page
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Should redirect to inventory page after login");
        
        // Verify inventory page elements
        WebElement productsTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("title")));
        assertEquals("Products", productsTitle.getText(), "Products title should be displayed");
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Login Page - Invalid Login Credentials")
    void testInvalidLogin() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        
        // Test with invalid credentials
        usernameField.sendKeys("invalid_user");
        passwordField.sendKeys("invalid_password");
        loginButton.click();
        
        // Verify error message appears
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='error']")));
        assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for invalid login");
        assertTrue(errorMessage.getText().contains("Username and password do not match"), "Error message should indicate invalid credentials");
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Login Page - Locked Out User")
    void testLockedOutUser() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        
        // Test with locked out user
        usernameField.sendKeys(LOCKED_USERNAME);
        passwordField.sendKeys(VALID_PASSWORD);
        loginButton.click();
        
        // Verify locked out error message
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='error']")));
        assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for locked out user");
        assertTrue(errorMessage.getText().contains("locked out"), "Error message should indicate user is locked out");
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Login Page - Empty Fields Validation")
    void testEmptyFieldsValidation() {
        WebElement loginButton = driver.findElement(By.id("login-button"));
        
        // Click login without entering credentials
        loginButton.click();
        
        // Verify error message for empty username
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='error']")));
        assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for empty fields");
        assertTrue(errorMessage.getText().contains("Username is required"), "Error message should indicate username is required");
    }
    
    // ==================== INVENTORY PAGE TESTS ====================
    
    @Test
    @Order(5)
    @DisplayName("Test Inventory Page - Product Display and Add to Cart")
    void testInventoryPageProductDisplay() {
        // Login first
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        
        // Verify inventory page elements
        WebElement productsTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("title")));
        assertEquals("Products", productsTitle.getText(), "Products title should be displayed");
        
        // Verify products are displayed
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        assertTrue(products.size() > 0, "Products should be displayed on inventory page");
        
        // Test adding product to cart
        WebElement addToCartButton = driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']"));
        addToCartButton.click();
        
        // Verify button text changes to "Remove"
        WebElement removeButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='remove-sauce-labs-backpack']")));
        assertEquals("Remove", removeButton.getText(), "Button should change to 'Remove' after adding to cart");
        
        // Verify cart badge shows item count
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("1", cartBadge.getText(), "Cart badge should show 1 item");
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Inventory Page - Product Sorting")
    void testInventoryPageSorting() {
        // Login first
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        
        // Test sorting functionality
        WebElement sortDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product_sort_container")));
        Select sortSelect = new Select(sortDropdown);
        
        // Test sorting by price (low to high)
        sortSelect.selectByValue("lohi");
        
        // Verify sorting worked by checking first product price
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        assertTrue(prices.size() > 0, "Product prices should be displayed");
        
        // Test sorting by name (Z to A)
        sortSelect.selectByValue("za");
        
        // Verify products are re-sorted
        List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));
        assertTrue(productNames.size() > 0, "Product names should be displayed after sorting");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Inventory Page - Multiple Products Add/Remove")
    void testMultipleProductsAddRemove() {
        // Login first
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        
        // Add multiple products to cart
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']")).click();
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-bolt-t-shirt']")).click();
        
        // Verify cart badge shows correct count
        WebElement cartBadge = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_badge")));
        assertEquals("3", cartBadge.getText(), "Cart badge should show 3 items");
        
        // Remove one product
        driver.findElement(By.cssSelector("[data-test='remove-sauce-labs-backpack']")).click();
        
        // Verify cart badge updates
        cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("2", cartBadge.getText(), "Cart badge should show 2 items after removal");
    }
    
    // ==================== CART PAGE TESTS ====================
    
    @Test
    @Order(8)
    @DisplayName("Test Cart Page - View Cart Items")
    void testCartPageViewItems() {
        // Login and add items to cart
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']")).click();
        
        // Navigate to cart page
        driver.findElement(By.className("shopping_cart_link")).click();
        
        // Verify cart page elements
        wait.until(ExpectedConditions.urlContains("cart.html"));
        assertTrue(driver.getCurrentUrl().contains("cart.html"), "Should be on cart page");
        
        WebElement cartTitle = driver.findElement(By.className("title"));
        assertEquals("Your Cart", cartTitle.getText(), "Cart page title should be 'Your Cart'");
        
        // Verify cart items are displayed
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(2, cartItems.size(), "Should have 2 items in cart");
        
        // Verify checkout button is present
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        assertTrue(checkoutButton.isDisplayed(), "Checkout button should be visible");
        assertEquals("Checkout", checkoutButton.getText(), "Checkout button should have correct text");
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Cart Page - Remove Items from Cart")
    void testCartPageRemoveItems() {
        // Login and add items to cart
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        
        // Navigate to cart page
        driver.findElement(By.className("shopping_cart_link")).click();
        
        // Remove item from cart
        WebElement removeButton = driver.findElement(By.cssSelector("[data-test='remove-sauce-labs-backpack']"));
        removeButton.click();
        
        // Verify item is removed
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        assertEquals(0, cartItems.size(), "Cart should be empty after removing item");
        
        // Verify cart badge is not displayed when empty
        List<WebElement> cartBadge = driver.findElements(By.className("shopping_cart_badge"));
        assertEquals(0, cartBadge.size(), "Cart badge should not be displayed when cart is empty");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Cart Page - Continue Shopping")
    void testCartPageContinueShopping() {
        // Login and navigate to cart
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        driver.findElement(By.className("shopping_cart_link")).click();
        
        // Click continue shopping
        WebElement continueShoppingButton = driver.findElement(By.id("continue-shopping"));
        continueShoppingButton.click();
        
        // Verify redirect to inventory page
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Should redirect to inventory page");
    }
    
    // ==================== CHECKOUT PROCESS TESTS ====================
    
    @Test
    @Order(11)
    @DisplayName("Test Checkout Step 1 - Customer Information")
    void testCheckoutStepOneCustomerInfo() {
        // Login, add item, and go to cart
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        
        // Click checkout
        driver.findElement(By.id("checkout")).click();
        
        // Verify checkout step 1 page
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"), "Should be on checkout step 1 page");
        
        WebElement checkoutTitle = driver.findElement(By.className("title"));
        assertEquals("Checkout: Your Information", checkoutTitle.getText(), "Checkout step 1 title should be correct");
        
        // Fill out customer information
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        
        // Click continue
        driver.findElement(By.id("continue")).click();
        
        // Verify redirect to step 2
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"), "Should redirect to checkout step 2");
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Checkout Step 1 - Validation Errors")
    void testCheckoutStepOneValidation() {
        // Login, add item, and go to checkout
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        
        // Click continue without filling fields
        driver.findElement(By.id("continue")).click();
        
        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='error']")));
        assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for empty fields");
        assertTrue(errorMessage.getText().contains("First Name is required"), "Error should indicate first name is required");
        
        // Fill first name only and test again
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("continue")).click();
        
        // Verify last name error
        errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.getText().contains("Last Name is required"), "Error should indicate last name is required");
        
        // Fill last name and test postal code validation
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("continue")).click();
        
        // Verify postal code error
        errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(errorMessage.getText().contains("Postal Code is required"), "Error should indicate postal code is required");
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Checkout Step 2 - Order Overview")
    void testCheckoutStepTwoOrderOverview() {
        // Complete checkout step 1
        performCheckoutStepOne();
        
        // Verify checkout step 2 page
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"), "Should be on checkout step 2 page");
        
        WebElement checkoutTitle = driver.findElement(By.className("title"));
        assertEquals("Checkout: Overview", checkoutTitle.getText(), "Checkout step 2 title should be correct");
        
        // Verify order summary elements
        WebElement paymentInfo = driver.findElement(By.cssSelector("[data-test='payment-info-label']"));
        assertTrue(paymentInfo.isDisplayed(), "Payment information should be displayed");
        
        WebElement shippingInfo = driver.findElement(By.cssSelector("[data-test='shipping-info-label']"));
        assertTrue(shippingInfo.isDisplayed(), "Shipping information should be displayed");
        
        WebElement totalInfo = driver.findElement(By.cssSelector("[data-test='total-info-label']"));
        assertTrue(totalInfo.isDisplayed(), "Total information should be displayed");
        
        // Verify item total and tax calculation
        WebElement itemTotal = driver.findElement(By.className("summary_subtotal_label"));
        WebElement tax = driver.findElement(By.className("summary_tax_label"));
        WebElement total = driver.findElement(By.className("summary_total_label"));
        
        assertTrue(itemTotal.isDisplayed(), "Item total should be displayed");
        assertTrue(tax.isDisplayed(), "Tax should be displayed");
        assertTrue(total.isDisplayed(), "Total should be displayed");
        
        // Verify finish button
        WebElement finishButton = driver.findElement(By.id("finish"));
        assertTrue(finishButton.isDisplayed(), "Finish button should be visible");
        assertEquals("Finish", finishButton.getText(), "Finish button should have correct text");
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Checkout Complete - Order Confirmation")
    void testCheckoutComplete() {
        // Complete full checkout process
        performCheckoutStepOne();
        
        // Click finish on step 2
        driver.findElement(By.id("finish")).click();
        
        // Verify checkout complete page
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
        assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"), "Should be on checkout complete page");
        
        WebElement checkoutTitle = driver.findElement(By.className("title"));
        assertEquals("Checkout: Complete!", checkoutTitle.getText(), "Checkout complete title should be correct");
        
        // Verify success message
        WebElement successHeader = driver.findElement(By.className("complete-header"));
        assertEquals("THANK YOU FOR YOUR ORDER", successHeader.getText(), "Success header should be displayed");
        
        WebElement successText = driver.findElement(By.className("complete-text"));
        assertTrue(successText.getText().contains("dispatched"), "Success text should mention order dispatch");
        
        // Verify back home button
        WebElement backHomeButton = driver.findElement(By.id("back-to-products"));
        assertTrue(backHomeButton.isDisplayed(), "Back to products button should be visible");
        
        // Click back to products
        backHomeButton.click();
        
        // Verify redirect to inventory
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Should redirect to inventory page");
    }
    
    // ==================== NAVIGATION AND MENU TESTS ====================
    
    @Test
    @Order(15)
    @DisplayName("Test Navigation Menu - Sidebar Menu")
    void testNavigationSidebarMenu() {
        // Login first
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        
        // Open sidebar menu
        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        menuButton.click();
        
        // Wait for menu to open and verify menu items
        WebElement allItemsLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("inventory_sidebar_link")));
        WebElement aboutLink = driver.findElement(By.id("about_sidebar_link"));
        WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));
        WebElement resetLink = driver.findElement(By.id("reset_sidebar_link"));
        
        assertTrue(allItemsLink.isDisplayed(), "All Items link should be visible");
        assertTrue(aboutLink.isDisplayed(), "About link should be visible");
        assertTrue(logoutLink.isDisplayed(), "Logout link should be visible");
        assertTrue(resetLink.isDisplayed(), "Reset App State link should be visible");
        
        // Test All Items link
        allItemsLink.click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertTrue(driver.getCurrentUrl().contains("inventory.html"), "All Items should navigate to inventory");
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Navigation Menu - Logout Functionality")
    void testLogoutFunctionality() {
        // Login first
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        
        // Open sidebar menu and logout
        driver.findElement(By.id("react-burger-menu-btn")).click();
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutLink.click();
        
        // Verify redirect to login page
        wait.until(ExpectedConditions.urlContains("index.html"));
        assertTrue(driver.getCurrentUrl().contains("index.html"), "Should redirect to login page after logout");
        
        // Verify login form is displayed
        WebElement usernameField = driver.findElement(By.id("user-name"));
        assertTrue(usernameField.isDisplayed(), "Username field should be visible after logout");
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Navigation Menu - Reset App State")
    void testResetAppState() {
        // Login and add items to cart
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']")).click();
        
        // Verify items in cart
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("2", cartBadge.getText(), "Cart should have 2 items before reset");
        
        // Open sidebar menu and reset app state
        driver.findElement(By.id("react-burger-menu-btn")).click();
        WebElement resetLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("reset_sidebar_link")));
        resetLink.click();
        
        // Close menu
        driver.findElement(By.id("react-burger-cross-btn")).click();
        
        // Verify cart is reset
        List<WebElement> cartBadgeAfterReset = driver.findElements(By.className("shopping_cart_badge"));
        assertEquals(0, cartBadgeAfterReset.size(), "Cart badge should not be visible after reset");
        
        // Verify all add to cart buttons are reset
        List<WebElement> removeButtons = driver.findElements(By.cssSelector("[id*='remove']"));
        assertEquals(0, removeButtons.size(), "No remove buttons should be visible after reset");
    }
    
    // ==================== EXTERNAL LINK TESTS ====================
    
    @Test
    @Order(18)
    @DisplayName("Test External Link - About Page")
    void testExternalAboutLink() {
        // Login first
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        
        // Open sidebar menu
        driver.findElement(By.id("react-burger-menu-btn")).click();
        
        // Get current window handle
        String originalWindow = driver.getWindowHandle();
        
        // Click about link
        WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("about_sidebar_link")));
        aboutLink.click();
        
        // Wait for new window/tab and switch to it
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify external site loaded
        wait.until(ExpectedConditions.urlContains("saucelabs.com"));
        assertTrue(driver.getCurrentUrl().contains("saucelabs.com"), "Should navigate to SauceLabs external site");
        
        // Verify page title contains SauceLabs
        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.toLowerCase().contains("sauce"), "Page title should contain 'Sauce'");
        
        // Close external tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
        
        // Verify we're back on the original site
        assertTrue(driver.getCurrentUrl().contains("saucedemo.com"), "Should be back on SauceDemo site");
    }
    
    // ==================== PROBLEM USER TESTS ====================
    
    @Test
    @Order(19)
    @DisplayName("Test Problem User - Visual Issues")
    void testProblemUserVisualIssues() {
        // Login with problem user
        performLogin(PROBLEM_USERNAME, VALID_PASSWORD);
        
        // Verify login successful
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Problem user should be able to login");
        
        // Check for visual issues - problem user has broken images
        List<WebElement> productImages = driver.findElements(By.className("inventory_item_img"));
        assertTrue(productImages.size() > 0, "Product images should be present");
        
        // Test add to cart functionality with problem user
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        
        // Verify cart badge appears (functionality should still work)
        WebElement cartBadge = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_badge")));
        assertEquals("1", cartBadge.getText(), "Cart functionality should work for problem user");
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Performance Glitch User - Slow Loading")
    void testPerformanceGlitchUser() {
        // Login with performance glitch user
        performLogin(PERFORMANCE_USERNAME, VALID_PASSWORD);
        
        // Verify login successful (may take longer)
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Performance glitch user should be able to login");
        
        // Test that functionality still works despite performance issues
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
        addToCartButton.click();
        
        // Verify cart functionality works (may be slower)
        WebElement cartBadge = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_badge")));
        assertEquals("1", cartBadge.getText(), "Cart functionality should work for performance glitch user");
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Helper method to perform login with given credentials
     */
    private void performLogin(String username, String password) {
        driver.get(BASE_URL + "index.html");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }
    
    /**
     * Helper method to complete checkout step one
     */
    private void performCheckoutStepOne() {
        // Login, add item, go to cart, and start checkout
        performLogin(VALID_USERNAME, VALID_PASSWORD);
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        
        // Fill checkout information
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
    }
}
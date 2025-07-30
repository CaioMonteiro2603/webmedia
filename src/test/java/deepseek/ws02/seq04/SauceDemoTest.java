package deepseek.ws02.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SauceDemoTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl = "https://www.saucedemo.com/v1/index.html";
    
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
    
    // Helper method to login
    private void login(String username, String password) {
        driver.get(baseUrl);
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.className("btn_action"));
        
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }
    
    @Test
    public void testLoginPageElements() {
        driver.get(baseUrl);
        
        // Test page title
        Assertions.assertEquals("Swag Labs", driver.getTitle());
        
        // Test logo presence
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login_logo")));
        Assertions.assertTrue(logo.isDisplayed());
        
        // Test username field
        WebElement usernameField = driver.findElement(By.id("user-name"));
        Assertions.assertTrue(usernameField.isDisplayed());
        usernameField.sendKeys("test_user");
        Assertions.assertEquals("test_user", usernameField.getAttribute("value"));
        
        // Test password field
        WebElement passwordField = driver.findElement(By.id("password"));
        Assertions.assertTrue(passwordField.isDisplayed());
        passwordField.sendKeys("test_pass");
        Assertions.assertEquals("test_pass", passwordField.getAttribute("value"));
        
        // Test login button
        WebElement loginButton = driver.findElement(By.className("btn_action"));
        Assertions.assertTrue(loginButton.isDisplayed());
        Assertions.assertEquals("LOGIN", loginButton.getAttribute("value"));
        
        // Test error message when login fails
        loginButton.click();
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        Assertions.assertTrue(errorMessage.getText().contains("Username and password do not match"));
    }
    
    @Test
    public void testSuccessfulLogin() {
        login("standard_user", "secret_sauce");
        
        // Verify we're on the inventory page after successful login
        WebElement inventoryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        Assertions.assertTrue(inventoryContainer.isDisplayed());
        Assertions.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }
    
    @Test
    public void testInventoryPageElements() {
        login("standard_user", "secret_sauce");
        
        // Test page title
        Assertions.assertEquals("Swag Labs", driver.getTitle());
        
        // Test menu button
        WebElement menuButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));
        Assertions.assertTrue(menuButton.isDisplayed());
        
        // Test shopping cart
        WebElement shoppingCart = driver.findElement(By.id("shopping_cart_container"));
        Assertions.assertTrue(shoppingCart.isDisplayed());
        
        // Test product sort dropdown
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        Assertions.assertTrue(sortDropdown.isDisplayed());
        
        // Test inventory items
        List<WebElement> inventoryItems = driver.findElements(By.className("inventory_item"));
        Assertions.assertEquals(6, inventoryItems.size());
        
        // Test each item has an image, title, description, price and add to cart button
        for (WebElement item : inventoryItems) {
            Assertions.assertTrue(item.findElement(By.className("inventory_item_img")).isDisplayed());
            Assertions.assertTrue(item.findElement(By.className("inventory_item_name")).isDisplayed());
            Assertions.assertTrue(item.findElement(By.className("inventory_item_desc")).isDisplayed());
            Assertions.assertTrue(item.findElement(By.className("inventory_item_price")).isDisplayed());
            Assertions.assertTrue(item.findElement(By.tagName("button")).isDisplayed());
        }
    }
    
    @Test
    public void testProductDetailsPage() {
        login("standard_user", "secret_sauce");
        
        // Click on first product
        WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        String productName = firstProduct.getText();
        firstProduct.click();
        
        // Verify we're on the product details page
        WebElement detailsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_item_container")));
        Assertions.assertTrue(detailsContainer.isDisplayed());
        
        // Verify product details
        WebElement productTitle = driver.findElement(By.className("inventory_details_name"));
        Assertions.assertEquals(productName, productTitle.getText());
        
        WebElement productImage = driver.findElement(By.className("inventory_details_img"));
        Assertions.assertTrue(productImage.isDisplayed());
        
        WebElement productDesc = driver.findElement(By.className("inventory_details_desc"));
        Assertions.assertTrue(productDesc.isDisplayed());
        
        WebElement productPrice = driver.findElement(By.className("inventory_details_price"));
        Assertions.assertTrue(productPrice.isDisplayed());
        
        WebElement addToCartButton = driver.findElement(By.className("btn_inventory"));
        Assertions.assertTrue(addToCartButton.isDisplayed());
        
        // Test back button
        WebElement backButton = driver.findElement(By.className("inventory_details_back_button"));
        Assertions.assertTrue(backButton.isDisplayed());
        backButton.click();
        
        // Verify we're back to inventory page
        WebElement inventoryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        Assertions.assertTrue(inventoryContainer.isDisplayed());
    }
    
    @Test
    public void testAddToCartFromInventory() {
        login("standard_user", "secret_sauce");
        
        // Get all add to cart buttons
        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[contains(text(), 'ADD TO CART')]"));
        
        // Add first item to cart
        WebElement firstAddToCart = addToCartButtons.get(0);
        firstAddToCart.click();
        
        // Verify button text changed to REMOVE
        Assertions.assertEquals("REMOVE", firstAddToCart.getText());
        
        // Verify cart badge shows 1
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assertions.assertEquals("1", cartBadge.getText());
    }
    
    @Test
    public void testCartPage() {
        login("standard_user", "secret_sauce");
        
        // Add an item to cart
        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'ADD TO CART')]")));
        addToCartButton.click();
        
        // Go to cart
        WebElement cartIcon = driver.findElement(By.id("shopping_cart_container"));
        cartIcon.click();
        
        // Verify we're on the cart page
        WebElement cartContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_contents_container")));
        Assertions.assertTrue(cartContainer.isDisplayed());
        
        // Verify cart items
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        Assertions.assertEquals(1, cartItems.size());
        
        // Verify item details in cart
        WebElement cartItem = cartItems.get(0);
        Assertions.assertTrue(cartItem.findElement(By.className("inventory_item_name")).isDisplayed());
        Assertions.assertTrue(cartItem.findElement(By.className("inventory_item_desc")).isDisplayed());
        Assertions.assertTrue(cartItem.findElement(By.className("inventory_item_price")).isDisplayed());
        Assertions.assertTrue(cartItem.findElement(By.className("cart_quantity")).isDisplayed());
        Assertions.assertTrue(cartItem.findElement(By.className("cart_button")).isDisplayed());
        
        // Test continue shopping button
        WebElement continueShoppingButton = driver.findElement(By.className("btn_secondary"));
        Assertions.assertTrue(continueShoppingButton.isDisplayed());
        continueShoppingButton.click();
        
        // Verify we're back to inventory page
        WebElement inventoryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        Assertions.assertTrue(inventoryContainer.isDisplayed());
    }
    
    @Test
    public void testCheckoutProcess() {
        login("standard_user", "secret_sauce");
        
        // Add an item to cart
        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'ADD TO CART')]")));
        addToCartButton.click();
        
        // Go to cart
        WebElement cartIcon = driver.findElement(By.id("shopping_cart_container"));
        cartIcon.click();
        
        // Start checkout
        WebElement checkoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("checkout_button")));
        checkoutButton.click();
        
        // Verify we're on checkout step one
        WebElement checkoutContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout_info_container")));
        Assertions.assertTrue(checkoutContainer.isDisplayed());
        
        // Fill checkout information
        WebElement firstName = driver.findElement(By.id("first-name"));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement postalCode = driver.findElement(By.id("postal-code"));
        
        firstName.sendKeys("Test");
        lastName.sendKeys("User");
        postalCode.sendKeys("12345");
        
        // Continue to checkout step two
        WebElement continueButton = driver.findElement(By.className("cart_button"));
        continueButton.click();
        
        // Verify we're on checkout step two
        WebElement checkoutSummary = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout_summary_container")));
        Assertions.assertTrue(checkoutSummary.isDisplayed());
        
        // Verify order summary
        Assertions.assertTrue(driver.findElement(By.className("cart_item")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.className("summary_info")).isDisplayed());
        
        // Complete order
        WebElement finishButton = driver.findElement(By.className("cart_button"));
        finishButton.click();
        
        // Verify order completion
        WebElement completeHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        Assertions.assertEquals("THANK YOU FOR YOUR ORDER", completeHeader.getText());
        
        // Back to home
        WebElement backHomeButton = driver.findElement(By.className("btn_primary"));
        backHomeButton.click();
        
        // Verify we're back to inventory page
        WebElement inventoryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        Assertions.assertTrue(inventoryContainer.isDisplayed());
    }
    
    @Test
    public void testExternalLinks() {
        login("standard_user", "secret_sauce");
        
        // Open menu
        WebElement menuButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));
        menuButton.click();
        
        // Get all links in menu
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu")));
        List<WebElement> menuLinks = menu.findElements(By.tagName("a"));
        
        // Test Twitter link
        WebElement twitterLink = menuLinks.stream()
            .filter(link -> link.getAttribute("href").contains("twitter.com"))
            .findFirst()
            .orElseThrow(() ->  new AssertionError("Twitter link not found"));
        
        String originalWindow = driver.getWindowHandle();
        twitterLink.click();
        
        // Switch to new window
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify Twitter page
        Assertions.assertTrue(driver.getCurrentUrl().contains("twitter.com"));
        driver.close();
        driver.switchTo().window(originalWindow);
        
        // Test Facebook link
        WebElement facebookLink = menuLinks.stream()
            .filter(link -> link.getAttribute("href").contains("facebook.com"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Facebook link not found"));
        
        facebookLink.click();
        
        // Switch to new window
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify Facebook page
        Assertions.assertTrue(driver.getCurrentUrl().contains("facebook.com"));
        driver.close();
        driver.switchTo().window(originalWindow);
        
        // Test LinkedIn link
        WebElement linkedinLink = menuLinks.stream()
            .filter(link -> link.getAttribute("href").contains("linkedin.com"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("LinkedIn link not found"));
        
        linkedinLink.click();
        
        // Switch to new window
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify LinkedIn page
        Assertions.assertTrue(driver.getCurrentUrl().contains("linkedin.com"));
        driver.close();
        driver.switchTo().window(originalWindow);
    }
    
    @Test
    public void testLogout() {
        login("standard_user", "secret_sauce");
        
        // Open menu
        WebElement menuButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));
        menuButton.click();
        
        // Click logout
        WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        logoutLink.click();
        
        // Verify we're back to login page
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn_action")));
        Assertions.assertTrue(loginButton.isDisplayed());
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"));
    }
}
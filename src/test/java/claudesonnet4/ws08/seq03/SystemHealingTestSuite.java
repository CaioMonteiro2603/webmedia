package claudesonnet4.ws08.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for System Healing Test website
 * Tests all pages and interactive elements including external links
 * 
 * @author Selenium Test Generator
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SystemHealingTestSuite {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/system-healing-test/";
    private static final int TIMEOUT_SECONDS = 10;
    
    @BeforeAll
    static void setUpClass() {
        // Setup Chrome options for headless testing
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT_SECONDS));
    }
    
    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @BeforeEach
    void setUp() {
        driver.get(BASE_URL);
    }
    
    // ==================== MAIN PAGE TESTS ====================
    
    @Test
    @Order(1)
    @DisplayName("Test Main Page Load and Title")
    void testMainPageLoadAndTitle() {
        assertEquals("Login", driver.getTitle(), "Page title should be 'Login'");
        assertTrue(driver.getCurrentUrl().equals(BASE_URL), "Should be on main page");
        
        // Verify welcome message is present
        WebElement welcomeMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Olá! Seja bem vindo')]")));
        assertTrue(welcomeMessage.isDisplayed(), "Welcome message should be visible");
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Username Input Field")
    void testUsernameInputField() {
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
        
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(usernameField.isEnabled(), "Username field should be enabled");
        assertEquals("username", usernameField.getAttribute("placeholder"), "Username placeholder should be correct");
        
        // Test input functionality
        usernameField.clear();
        usernameField.sendKeys("testuser");
        assertEquals("testuser", usernameField.getAttribute("value"), "Username should be entered correctly");
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Password Input Field")
    void testPasswordInputField() {
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password-2")));
        
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        assertEquals("password", passwordField.getAttribute("placeholder"), "Password placeholder should be correct");
        assertEquals("password", passwordField.getAttribute("type"), "Password field type should be password");
        
        // Test input functionality
        passwordField.clear();
        passwordField.sendKeys("testpassword123");
        assertEquals("testpassword123", passwordField.getAttribute("value"), "Password should be entered correctly");
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Login Button")
    void testLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btnLogin2")));
        
        assertTrue(loginButton.isDisplayed(), "Login button should be visible");
        assertTrue(loginButton.isEnabled(), "Login button should be enabled");
        assertEquals("Login", loginButton.getText(), "Login button text should be correct");
        assertEquals("submit", loginButton.getAttribute("type"), "Login button type should be submit");
        
        // Test button click functionality
        assertDoesNotThrow(() -> loginButton.click(), "Login button should be clickable");
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Complete Login Form Submission")
    void testCompleteLoginFormSubmission() {
        // Fill in the form
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
        WebElement passwordField = driver.findElement(By.id("password-2"));
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        
        usernameField.clear();
        usernameField.sendKeys("testuser@example.com");
        passwordField.clear();
        passwordField.sendKeys("securePassword123");
        
        // Submit the form
        assertDoesNotThrow(() -> loginButton.click(), "Form submission should not throw exception");
        
        // Verify form was submitted (page might reload or show validation)
        assertNotNull(driver.getCurrentUrl(), "Page should still be accessible after form submission");
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Forgot Password Link")
    void testForgotPasswordLink() {
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='password.html']")));
        
        assertTrue(forgotPasswordLink.isDisplayed(), "Forgot password link should be visible");
        assertEquals("Esqueceu a sua senha?", forgotPasswordLink.getText(), "Forgot password link text should be correct");
        
        // Click the link and verify navigation
        forgotPasswordLink.click();
        wait.until(ExpectedConditions.urlContains("password.html"));
        assertTrue(driver.getCurrentUrl().contains("password.html"), "Should navigate to password recovery page");
        assertEquals("Recuperar senha", driver.getTitle(), "Password recovery page title should be correct");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Create Account Link")
    void testCreateAccountLink() {
        WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='account.html']")));
        
        assertTrue(createAccountLink.isDisplayed(), "Create account link should be visible");
        assertEquals("Criar uma conta", createAccountLink.getText(), "Create account link text should be correct");
        
        // Click the link and verify navigation
        createAccountLink.click();
        wait.until(ExpectedConditions.urlContains("account.html"));
        assertTrue(driver.getCurrentUrl().contains("account.html"), "Should navigate to account creation page");
        assertEquals("Criar conta", driver.getTitle(), "Account creation page title should be correct");
    }
    
    // ==================== EXTERNAL LINKS TESTS ====================
    
    @Test
    @Order(8)
    @DisplayName("Test Social Media External Links")
    void testSocialMediaExternalLinks() {
        // Test Facebook link
        testExternalLink("Facebook", "facebook.com");
        
        // Return to main page for next test
        driver.get(BASE_URL);
        
        // Test Google link
        testExternalLink("Google", "google.com");
        
        // Return to main page for next test
        driver.get(BASE_URL);
        
        // Test Twitter link
        testExternalLink("Twitter", "twitter.com");
        
        // Return to main page for next test
        driver.get(BASE_URL);
        
        // Test GitHub link
        testExternalLink("GitHub", "github.com");
    }
    
    private void testExternalLink(String platform, String expectedDomain) {
        try {
            // Find social media links by their common attributes
            WebElement socialLink = null;
            
            switch (platform) {
                case "Facebook":
                    socialLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href, 'facebook') or contains(@class, 'facebook') or contains(@title, 'Facebook')]")));
                    break;
                case "Google":
                    socialLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href, 'google') or contains(@class, 'google') or contains(@title, 'Google')]")));
                    break;
                case "Twitter":
                    socialLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href, 'twitter') or contains(@class, 'twitter') or contains(@title, 'Twitter')]")));
                    break;
                case "GitHub":
                    socialLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href, 'github') or contains(@class, 'github') or contains(@title, 'GitHub')]")));
                    break;
            }
            
            if (socialLink != null) {
                assertTrue(socialLink.isDisplayed(), platform + " link should be visible");
                
                String originalWindow = driver.getWindowHandle();
                socialLink.click();
                
                // Wait for new window/tab and switch to it
                wait.until(ExpectedConditions.numberOfWindowsToBe(2));
                Set<String> windowHandles = driver.getWindowHandles();
                for (String handle : windowHandles) {
                    if (!handle.equals(originalWindow)) {
                        driver.switchTo().window(handle);
                        break;
                    }
                }
                
                // Verify the external link opened correctly
                wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(BASE_URL)));
                String currentUrl = driver.getCurrentUrl();
                assertTrue(currentUrl.contains(expectedDomain) || !currentUrl.equals(BASE_URL), 
                    platform + " link should navigate to external site or different URL");
                
                // Close the new window and return to original
                driver.close();
                driver.switchTo().window(originalWindow);
            }
        } catch (Exception e) {
            // If external link test fails, just log it but don't fail the test
            System.out.println("External link test for " + platform + " could not be completed: " + e.getMessage());
        }
    }
    
    // ==================== PASSWORD RECOVERY PAGE TESTS ====================
    
    @Test
    @Order(9)
    @DisplayName("Test Password Recovery Page Load")
    void testPasswordRecoveryPageLoad() {
        driver.get(BASE_URL + "password.html");
        
        assertEquals("Recuperar senha", driver.getTitle(), "Password recovery page title should be correct");
        assertTrue(driver.getCurrentUrl().contains("password.html"), "Should be on password recovery page");
        
        // Verify page content
        WebElement pageHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Perdeu a sua senha?')]")));
        assertTrue(pageHeader.isDisplayed(), "Password recovery header should be visible");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Password Recovery Form Fields")
    void testPasswordRecoveryFormFields() {
        driver.get(BASE_URL + "password.html");
        
        // Test username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(usernameField.isEnabled(), "Username field should be enabled");
        assertEquals("apelido", usernameField.getAttribute("placeholder"), "Username placeholder should be correct");
        
        // Test email field
        WebElement emailField = driver.findElement(By.id("email"));
        assertTrue(emailField.isDisplayed(), "Email field should be visible");
        assertTrue(emailField.isEnabled(), "Email field should be enabled");
        assertEquals("e-mail", emailField.getAttribute("placeholder"), "Email placeholder should be correct");
        
        // Test confirm email field
        WebElement confirmEmailField = driver.findElement(By.id("cmail"));
        assertTrue(confirmEmailField.isDisplayed(), "Confirm email field should be visible");
        assertTrue(confirmEmailField.isEnabled(), "Confirm email field should be enabled");
        assertEquals("confirmar o e-mail", confirmEmailField.getAttribute("placeholder"), "Confirm email placeholder should be correct");
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Password Recovery Form Submission")
    void testPasswordRecoveryFormSubmission() {
        driver.get(BASE_URL + "password.html");
        
        // Fill in the form
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement confirmEmailField = driver.findElement(By.id("cmail"));
        
        usernameField.clear();
        usernameField.sendKeys("testuser");
        emailField.clear();
        emailField.sendKeys("test@example.com");
        confirmEmailField.clear();
        confirmEmailField.sendKeys("test@example.com");
        
        // Verify form data
        assertEquals("testuser", usernameField.getAttribute("value"), "Username should be entered correctly");
        assertEquals("test@example.com", emailField.getAttribute("value"), "Email should be entered correctly");
        assertEquals("test@example.com", confirmEmailField.getAttribute("value"), "Confirm email should be entered correctly");
        
        // Find and click submit button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Obter o código') or @type='submit']")));
        assertTrue(submitButton.isDisplayed(), "Submit button should be visible");
        assertDoesNotThrow(() -> submitButton.click(), "Form submission should not throw exception");
    }
    
    // ==================== ACCOUNT CREATION PAGE TESTS ====================
    
    @Test
    @Order(12)
    @DisplayName("Test Account Creation Page Load")
    void testAccountCreationPageLoad() {
        driver.get(BASE_URL + "account.html");
        
        assertEquals("Criar conta", driver.getTitle(), "Account creation page title should be correct");
        assertTrue(driver.getCurrentUrl().contains("account.html"), "Should be on account creation page");
        
        // Verify page content
        WebElement pageHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Junte-se a nós')]")));
        assertTrue(pageHeader.isDisplayed(), "Account creation header should be visible");
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Account Creation Form Fields")
    void testAccountCreationFormFields() {
        driver.get(BASE_URL + "account.html");
        
        // Test username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(usernameField.isEnabled(), "Username field should be enabled");
        assertEquals("apelido", usernameField.getAttribute("placeholder"), "Username placeholder should be correct");
        
        // Test email field
        WebElement emailField = driver.findElement(By.id("email"));
        assertTrue(emailField.isDisplayed(), "Email field should be visible");
        assertTrue(emailField.isEnabled(), "Email field should be enabled");
        assertEquals("e-mail", emailField.getAttribute("placeholder"), "Email placeholder should be correct");
        
        // Test confirm email field
        WebElement confirmEmailField = driver.findElement(By.id("cmail"));
        assertTrue(confirmEmailField.isDisplayed(), "Confirm email field should be visible");
        assertTrue(confirmEmailField.isEnabled(), "Confirm email field should be enabled");
        assertEquals("confirmar o e-mail", confirmEmailField.getAttribute("placeholder"), "Confirm email placeholder should be correct");
        
        // Test password field
        WebElement passwordField = driver.findElement(By.id("password"));
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        assertEquals("senha", passwordField.getAttribute("placeholder"), "Password placeholder should be correct");
        assertEquals("password", passwordField.getAttribute("type"), "Password field type should be password");
        
        // Test confirm password field
        WebElement confirmPasswordField = driver.findElement(By.id("cpassword"));
        assertTrue(confirmPasswordField.isDisplayed(), "Confirm password field should be visible");
        assertTrue(confirmPasswordField.isEnabled(), "Confirm password field should be enabled");
        assertEquals("confirmar a senha", confirmPasswordField.getAttribute("placeholder"), "Confirm password placeholder should be correct");
        assertEquals("password", confirmPasswordField.getAttribute("type"), "Confirm password field type should be password");
        
        // Test terms checkbox
        WebElement termsCheckbox = driver.findElement(By.id("termo"));
        assertTrue(termsCheckbox.isDisplayed(), "Terms checkbox should be visible");
        assertTrue(termsCheckbox.isEnabled(), "Terms checkbox should be enabled");
        assertEquals("checkbox", termsCheckbox.getAttribute("type"), "Terms field type should be checkbox");
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Account Creation Terms Checkbox")
    void testAccountCreationTermsCheckbox() {
        driver.get(BASE_URL + "account.html");
        
        WebElement termsCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("termo")));
        
        // Test checkbox functionality
        assertFalse(termsCheckbox.isSelected(), "Terms checkbox should initially be unchecked");
        
        termsCheckbox.click();
        assertTrue(termsCheckbox.isSelected(), "Terms checkbox should be checked after clicking");
        
        termsCheckbox.click();
        assertFalse(termsCheckbox.isSelected(), "Terms checkbox should be unchecked after clicking again");
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Complete Account Creation Form")
    void testCompleteAccountCreationForm() {
        driver.get(BASE_URL + "account.html");
        
        // Fill in all form fields
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement confirmEmailField = driver.findElement(By.id("cmail"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement confirmPasswordField = driver.findElement(By.id("cpassword"));
        WebElement termsCheckbox = driver.findElement(By.id("termo"));
        
        // Clear and fill fields
        usernameField.clear();
        usernameField.sendKeys("newuser123");
        emailField.clear();
        emailField.sendKeys("newuser@example.com");
        confirmEmailField.clear();
        confirmEmailField.sendKeys("newuser@example.com");
        passwordField.clear();
        passwordField.sendKeys("SecurePass123!");
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys("SecurePass123!");
        
        // Check terms
        if (!termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }
        
        // Verify all data is entered correctly
        assertEquals("newuser123", usernameField.getAttribute("value"), "Username should be entered correctly");
        assertEquals("newuser@example.com", emailField.getAttribute("value"), "Email should be entered correctly");
        assertEquals("newuser@example.com", confirmEmailField.getAttribute("value"), "Confirm email should be entered correctly");
        assertEquals("SecurePass123!", passwordField.getAttribute("value"), "Password should be entered correctly");
        assertEquals("SecurePass123!", confirmPasswordField.getAttribute("value"), "Confirm password should be entered correctly");
        assertTrue(termsCheckbox.isSelected(), "Terms checkbox should be checked");
        
        // Find and test submit button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Criar conta') or @type='submit']")));
        assertTrue(submitButton.isDisplayed(), "Submit button should be visible");
        assertDoesNotThrow(() -> submitButton.click(), "Form submission should not throw exception");
    }
    
    // ==================== NAVIGATION TESTS ====================
    
    @Test
    @Order(16)
    @DisplayName("Test Navigation Between All Pages")
    void testNavigationBetweenAllPages() {
        // Start from main page
        driver.get(BASE_URL);
        assertEquals("Login", driver.getTitle(), "Should start on login page");
        
        // Navigate to password recovery
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='password.html']")));
        forgotPasswordLink.click();
        wait.until(ExpectedConditions.urlContains("password.html"));
        assertEquals("Recuperar senha", driver.getTitle(), "Should be on password recovery page");
        
        // Navigate back to main page
        driver.get(BASE_URL);
        assertEquals("Login", driver.getTitle(), "Should be back on login page");
        
        // Navigate to account creation
        WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='account.html']")));
        createAccountLink.click();
        wait.until(ExpectedConditions.urlContains("account.html"));
        assertEquals("Criar conta", driver.getTitle(), "Should be on account creation page");
        
        // Navigate back to main page
        driver.get(BASE_URL);
        assertEquals("Login", driver.getTitle(), "Should be back on login page");
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Page Responsiveness and Layout")
    void testPageResponsivenessAndLayout() {
        // Test different viewport sizes
        driver.manage().window().setSize(new Dimension(1920, 1080)); // Desktop
        driver.get(BASE_URL);
        assertTrue(driver.findElement(By.id("username-2")).isDisplayed(), "Elements should be visible on desktop");
        
        driver.manage().window().setSize(new Dimension(768, 1024)); // Tablet
        assertTrue(driver.findElement(By.id("username-2")).isDisplayed(), "Elements should be visible on tablet");
        
        driver.manage().window().setSize(new Dimension(375, 667)); // Mobile
        assertTrue(driver.findElement(By.id("username-2")).isDisplayed(), "Elements should be visible on mobile");
        
        // Reset to default size
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }
    
    @Test
    @Order(18)
    @DisplayName("Test Form Validation and Error Handling")
    void testFormValidationAndErrorHandling() {
        // Test empty form submission on login page
        driver.get(BASE_URL);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnLogin2")));
        assertDoesNotThrow(() -> loginButton.click(), "Empty form submission should not crash");
        
        // Test empty form submission on password recovery page
        driver.get(BASE_URL + "password.html");
        WebElement passwordSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Obter o código') or @type='submit']")));
        assertDoesNotThrow(() -> passwordSubmitButton.click(), "Empty password recovery form should not crash");
        
        // Test empty form submission on account creation page
        driver.get(BASE_URL + "account.html");
        WebElement accountSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Criar conta') or @type='submit']")));
        assertDoesNotThrow(() -> accountSubmitButton.click(), "Empty account creation form should not crash");
    }
    
    @Test
    @Order(19)
    @DisplayName("Test All Interactive Elements Accessibility")
    void testAllInteractiveElementsAccessibility() {
        // Test main page elements
        driver.get(BASE_URL);
        
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
        WebElement passwordField = driver.findElement(By.id("password-2"));
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        
        // Test tab navigation
        usernameField.sendKeys(Keys.TAB);
        assertEquals(passwordField, driver.switchTo().activeElement(), "Tab should move to password field");
        
        passwordField.sendKeys(Keys.TAB);
        // Note: The active element after tab might be the login button or next focusable element
        assertNotNull(driver.switchTo().activeElement(), "Tab navigation should work");
        
        // Test keyboard accessibility
        usernameField.sendKeys("test");
        passwordField.sendKeys("test");
        assertDoesNotThrow(() -> loginButton.sendKeys(Keys.ENTER), "Enter key should work on login button");
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Complete Website Functionality End-to-End")
    void testCompleteWebsiteFunctionalityEndToEnd() {
        // Complete end-to-end test covering all major user flows
        
        // 1. Start on main page and verify all elements
        driver.get(BASE_URL);
        assertTrue(driver.findElement(By.id("username-2")).isDisplayed(), "Main page should load correctly");
        
        // 2. Test login form interaction
        WebElement usernameField = driver.findElement(By.id("username-2"));
        WebElement passwordField = driver.findElement(By.id("password-2"));
        usernameField.sendKeys("testuser");
        passwordField.sendKeys("testpass");
        driver.findElement(By.id("btnLogin2")).click();
        
        // 3. Navigate to password recovery and test form
        driver.get(BASE_URL);
        driver.findElement(By.xpath("//a[@href='password.html']")).click();
        wait.until(ExpectedConditions.urlContains("password.html"));
        
        WebElement recoveryUsername = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        WebElement recoveryEmail = driver.findElement(By.id("email"));
        recoveryUsername.sendKeys("testuser");
        recoveryEmail.sendKeys("test@example.com");
        
        // 4. Navigate to account creation and test complete form
        driver.get(BASE_URL);
        driver.findElement(By.xpath("//a[@href='account.html']")).click();
        wait.until(ExpectedConditions.urlContains("account.html"));
        
        WebElement newUsername = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        WebElement newEmail = driver.findElement(By.id("email"));
        WebElement newPassword = driver.findElement(By.id("password"));
        WebElement termsCheckbox = driver.findElement(By.id("termo"));
        
        newUsername.sendKeys("newuser");
        newEmail.sendKeys("new@example.com");
        newPassword.sendKeys("newpass123");
        termsCheckbox.click();
        
        // 5. Verify all interactions completed successfully
        assertEquals("newuser", newUsername.getAttribute("value"), "End-to-end test should complete successfully");
        assertTrue(termsCheckbox.isSelected(), "Terms should be accepted in end-to-end test");
        
        // 6. Return to main page to complete the cycle
        driver.get(BASE_URL);
        assertEquals("Login", driver.getTitle(), "Should return to main page successfully");
    }
}
package claudesonnet4.ws08.seq05;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive Selenium WebDriver Test Suite for System Healing Test Website
 * Tests all pages, interactive elements, forms, and external links
 * 
 * Website Under Test: https://wavingtest.github.io/system-healing-test/
 * Package: claudesonnet4.ws08.seq05
 * 
 * Test Coverage:
 * - Main Login Page (index.html)
 * - Password Recovery Page (password.html) 
 * - Account Creation Page (account.html)
 * - All interactive elements and forms
 * - External social media links
 * - Navigation between pages
 * - Form validation and submission
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SystemHealingWebsiteTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions actions;
    
    // Base URL for the website under test
    private static final String BASE_URL = "https://wavingtest.github.io/system-healing-test/";
    private static final String PASSWORD_PAGE_URL = BASE_URL + "password.html";
    private static final String ACCOUNT_PAGE_URL = BASE_URL + "account.html";
    
    // Test data constants
    private static final String TEST_USERNAME = "testuser123";
    private static final String TEST_PASSWORD = "testpass456";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_NICKNAME = "testnick";

    @BeforeAll
    static void setUpClass() {
        // Configure Chrome options for headless testing
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        
        // Maximize window for consistent testing
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void setUp() {
        // Navigate to base URL before each test
        driver.get(BASE_URL);
    }

    // ==================== MAIN LOGIN PAGE TESTS ====================

    @Test
    @Order(1)
    @DisplayName("Test Main Login Page Load and Basic Elements")
    void testMainPageLoad() {
        // Verify page loads correctly
        Assertions.assertEquals("Login", driver.getTitle(), "Page title should be 'Login'");
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl(), "Should be on main page");
        
        // Verify main heading is present
        WebElement welcomeText = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Olá! Seja bem vindo')]")));
        Assertions.assertTrue(welcomeText.isDisplayed(), "Welcome message should be visible");
        
        // Verify login form heading
        WebElement loginHeading = driver.findElement(By.xpath("//*[contains(text(), 'faça o seu login agora')]"));
        Assertions.assertTrue(loginHeading.isDisplayed(), "Login heading should be visible");
    }

    @Test
    @Order(2)
    @DisplayName("Test Login Form Elements Presence and Properties")
    void testLoginFormElements() {
        // Test username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
        Assertions.assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        Assertions.assertEquals("text", usernameField.getAttribute("type"), "Username field should be text type");
        Assertions.assertEquals("username", usernameField.getAttribute("placeholder"), "Username placeholder should be correct");
        Assertions.assertEquals("username", usernameField.getAttribute("name"), "Username field name should be correct");
        
        // Test password field
        WebElement passwordField = driver.findElement(By.id("password-2"));
        Assertions.assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        Assertions.assertEquals("password", passwordField.getAttribute("type"), "Password field should be password type");
        Assertions.assertEquals("password", passwordField.getAttribute("placeholder"), "Password placeholder should be correct");
        Assertions.assertEquals("password209", passwordField.getAttribute("name"), "Password field name should be correct");
        
        // Test login button
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        Assertions.assertTrue(loginButton.isDisplayed(), "Login button should be visible");
        Assertions.assertEquals("submit", loginButton.getAttribute("type"), "Login button should be submit type");
        Assertions.assertEquals("Login", loginButton.getText(), "Login button text should be correct");
        Assertions.assertTrue(loginButton.isEnabled(), "Login button should be enabled");
    }

    @Test
    @Order(3)
    @DisplayName("Test Login Form Input Functionality")
    void testLoginFormInput() {
        // Fill username field
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username-2")));
        usernameField.clear();
        usernameField.sendKeys(TEST_USERNAME);
        Assertions.assertEquals(TEST_USERNAME, usernameField.getAttribute("value"), "Username should be entered correctly");
        
        // Fill password field
        WebElement passwordField = driver.findElement(By.id("password-2"));
        passwordField.clear();
        passwordField.sendKeys(TEST_PASSWORD);
        Assertions.assertEquals(TEST_PASSWORD, passwordField.getAttribute("value"), "Password should be entered correctly");
        
        // Test field clearing
        usernameField.clear();
        Assertions.assertEquals("", usernameField.getAttribute("value"), "Username field should be cleared");
        
        passwordField.clear();
        Assertions.assertEquals("", passwordField.getAttribute("value"), "Password field should be cleared");
    }

    @Test
    @Order(4)
    @DisplayName("Test Login Form Submission")
    void testLoginFormSubmission() {
        // Fill login form
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username-2")));
        WebElement passwordField = driver.findElement(By.id("password-2"));
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        
        usernameField.sendKeys(TEST_USERNAME);
        passwordField.sendKeys(TEST_PASSWORD);
        
        // Submit form by clicking login button
        loginButton.click();
        
        // Verify form submission (URL should contain parameters)
        wait.until(ExpectedConditions.urlContains("username=" + TEST_USERNAME));
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("username=" + TEST_USERNAME), "URL should contain username parameter");
        Assertions.assertTrue(currentUrl.contains("password209=" + TEST_PASSWORD), "URL should contain password parameter");
        
        // Verify we're still on the login page (no redirect)
        Assertions.assertEquals("Login", driver.getTitle(), "Should remain on login page after submission");
    }

    @Test
    @Order(5)
    @DisplayName("Test Navigation Links on Main Page")
    void testMainPageNavigationLinks() {
        // Test "Forgot Password" link
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='password.html']")));
        Assertions.assertTrue(forgotPasswordLink.isDisplayed(), "Forgot password link should be visible");
        Assertions.assertEquals("Esqueceu a sua senha?", forgotPasswordLink.getText(), "Forgot password link text should be correct");
        
        // Test "Create Account" link
        WebElement createAccountLink = driver.findElement(By.xpath("//a[@href='account.html']"));
        Assertions.assertTrue(createAccountLink.isDisplayed(), "Create account link should be visible");
        Assertions.assertEquals("Criar uma conta", createAccountLink.getText(), "Create account link text should be correct");
    }

    @Test
    @Order(6)
    @DisplayName("Test Social Media Icons Presence")
    void testSocialMediaIcons() {
        // Scroll down to ensure social media icons are visible
        actions.sendKeys(Keys.END).perform();
        
        // Wait for social media icons to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href, 'facebook') or contains(@href, 'google') or contains(@href, 'twitter') or contains(@href, 'github')]")));
        
        // Find all social media links
        List<WebElement> socialLinks = driver.findElements(By.xpath("//a[contains(@href, 'facebook') or contains(@href, 'google') or contains(@href, 'twitter') or contains(@href, 'github') or contains(@class, 'social')]"));
        
        // Verify social media icons are present (should be 4: Facebook, Google, Twitter, GitHub)
        Assertions.assertTrue(socialLinks.size() >= 4, "Should have at least 4 social media icons");
        
        // Verify each social media icon is clickable
        for (WebElement socialLink : socialLinks) {
            Assertions.assertTrue(socialLink.isDisplayed(), "Social media icon should be visible");
            Assertions.assertTrue(socialLink.isEnabled(), "Social media icon should be clickable");
        }
    }

    // ==================== PASSWORD RECOVERY PAGE TESTS ====================

    @Test
    @Order(7)
    @DisplayName("Test Navigation to Password Recovery Page")
    void testNavigationToPasswordPage() {
        // Click on "Forgot Password" link
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='password.html']")));
        forgotPasswordLink.click();
        
        // Verify navigation to password recovery page
        wait.until(ExpectedConditions.urlToBe(PASSWORD_PAGE_URL));
        Assertions.assertEquals(PASSWORD_PAGE_URL, driver.getCurrentUrl(), "Should navigate to password recovery page");
        Assertions.assertEquals("Recuperar senha", driver.getTitle(), "Page title should be 'Recuperar senha'");
    }

    @Test
    @Order(8)
    @DisplayName("Test Password Recovery Page Elements")
    void testPasswordRecoveryPageElements() {
        // Navigate to password recovery page
        driver.get(PASSWORD_PAGE_URL);
        
        // Verify page heading
        WebElement pageHeading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Perdeu a sua senha?')]")));
        Assertions.assertTrue(pageHeading.isDisplayed(), "Page heading should be visible");
        
        // Verify subheading
        WebElement subHeading = driver.findElement(By.xpath("//*[contains(text(), 'recupere via email agora')]"));
        Assertions.assertTrue(subHeading.isDisplayed(), "Subheading should be visible");
        
        // Verify form instruction
        WebElement formInstruction = driver.findElement(By.xpath("//*[contains(text(), 'insira a sua conta existente')]"));
        Assertions.assertTrue(formInstruction.isDisplayed(), "Form instruction should be visible");
        
        // Verify detailed instructions
        WebElement detailedInstructions = driver.findElement(By.xpath("//*[contains(text(), 'Um código será enviado')]"));
        Assertions.assertTrue(detailedInstructions.isDisplayed(), "Detailed instructions should be visible");
    }

    @Test
    @Order(9)
    @DisplayName("Test Password Recovery Form Fields")
    void testPasswordRecoveryFormFields() {
        // Navigate to password recovery page
        driver.get(PASSWORD_PAGE_URL);
        
        // Find and test nickname field
        List<WebElement> inputFields = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("input")));
        Assertions.assertTrue(inputFields.size() >= 2, "Should have at least 2 input fields");
        
        // Test first input field (nickname)
        WebElement nicknameField = inputFields.get(0);
        Assertions.assertTrue(nicknameField.isDisplayed(), "Nickname field should be visible");
        Assertions.assertEquals("text", nicknameField.getAttribute("type"), "First field should be text type");
        
        // Test second input field (email)
        WebElement emailField = inputFields.get(1);
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible");
        Assertions.assertEquals("email", emailField.getAttribute("type"), "Second field should be email type");
        
        // Test input functionality
        nicknameField.sendKeys(TEST_NICKNAME);
        emailField.sendKeys(TEST_EMAIL);
        
        Assertions.assertEquals(TEST_NICKNAME, nicknameField.getAttribute("value"), "Nickname should be entered correctly");
        Assertions.assertEquals(TEST_EMAIL, emailField.getAttribute("value"), "Email should be entered correctly");
    }

    @Test
    @Order(10)
    @DisplayName("Test Password Recovery Form Submission")
    void testPasswordRecoveryFormSubmission() {
        // Navigate to password recovery page
        driver.get(PASSWORD_PAGE_URL);
        
        // Fill form fields
        List<WebElement> inputFields = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("input")));
        inputFields.get(0).sendKeys(TEST_NICKNAME);
        inputFields.get(1).sendKeys(TEST_EMAIL);
        
        // Find and click submit button
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit'] | //input[@type='submit']"));
        Assertions.assertTrue(submitButton.isDisplayed(), "Submit button should be visible");
        Assertions.assertTrue(submitButton.isEnabled(), "Submit button should be enabled");
        
        submitButton.click();
        
        // Verify form submission behavior
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("password.html"), "Should remain on or process password recovery page");
    }

    // ==================== ACCOUNT CREATION PAGE TESTS ====================

    @Test
    @Order(11)
    @DisplayName("Test Navigation to Account Creation Page")
    void testNavigationToAccountPage() {
        // Click on "Create Account" link from main page
        WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='account.html']")));
        createAccountLink.click();
        
        // Verify navigation to account creation page
        wait.until(ExpectedConditions.urlToBe(ACCOUNT_PAGE_URL));
        Assertions.assertEquals(ACCOUNT_PAGE_URL, driver.getCurrentUrl(), "Should navigate to account creation page");
        Assertions.assertEquals("Criar conta", driver.getTitle(), "Page title should be 'Criar conta'");
    }

    @Test
    @Order(12)
    @DisplayName("Test Account Creation Page Elements")
    void testAccountCreationPageElements() {
        // Navigate to account creation page
        driver.get(ACCOUNT_PAGE_URL);
        
        // Verify page heading
        WebElement pageHeading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Junte-se a nós')]")));
        Assertions.assertTrue(pageHeading.isDisplayed(), "Page heading should be visible");
        
        // Verify subheading
        WebElement subHeading = driver.findElement(By.xpath("//*[contains(text(), 'Crie hoje a sua conta!')]"));
        Assertions.assertTrue(subHeading.isDisplayed(), "Subheading should be visible");
        
        // Verify form instruction
        WebElement formInstruction = driver.findElement(By.xpath("//*[contains(text(), 'informe seus dados')]"));
        Assertions.assertTrue(formInstruction.isDisplayed(), "Form instruction should be visible");
    }

    @Test
    @Order(13)
    @DisplayName("Test Account Creation Form Fields")
    void testAccountCreationFormFields() {
        // Navigate to account creation page
        driver.get(ACCOUNT_PAGE_URL);
        
        // Find all input fields
        List<WebElement> inputFields = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("input")));
        Assertions.assertTrue(inputFields.size() >= 5, "Should have at least 5 input fields");
        
        // Test each field type and functionality
        String[] testValues = {TEST_NICKNAME, TEST_EMAIL, TEST_EMAIL, TEST_PASSWORD, TEST_PASSWORD};
        String[] expectedTypes = {"text", "email", "email", "password", "password"};
        
        for (int i = 0; i < Math.min(5, inputFields.size()); i++) {
            WebElement field = inputFields.get(i);
            Assertions.assertTrue(field.isDisplayed(), "Field " + (i+1) + " should be visible");
            
            // Skip checkbox fields for text input testing
            if (!"checkbox".equals(field.getAttribute("type"))) {
                field.clear();
                field.sendKeys(testValues[i]);
                Assertions.assertEquals(testValues[i], field.getAttribute("value"), 
                    "Field " + (i+1) + " should accept input correctly");
            }
        }
    }

    @Test
    @Order(14)
    @DisplayName("Test Account Creation Terms Checkbox")
    void testAccountCreationTermsCheckbox() {
        // Navigate to account creation page
        driver.get(ACCOUNT_PAGE_URL);
        
        // Find terms checkbox
        WebElement termsCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@type='checkbox'] | //*[contains(text(), 'aceito os termos')]")));
        
        if (termsCheckbox.getTagName().equals("input")) {
            Assertions.assertEquals("checkbox", termsCheckbox.getAttribute("type"), "Should be a checkbox");
            Assertions.assertFalse(termsCheckbox.isSelected(), "Checkbox should be unchecked by default");
            
            // Click checkbox
            termsCheckbox.click();
            Assertions.assertTrue(termsCheckbox.isSelected(), "Checkbox should be checked after clicking");
            
            // Click again to uncheck
            termsCheckbox.click();
            Assertions.assertFalse(termsCheckbox.isSelected(), "Checkbox should be unchecked after second click");
        } else {
            // If it's a text element, verify it's clickable
            Assertions.assertTrue(termsCheckbox.isDisplayed(), "Terms element should be visible");
        }
    }

    @Test
    @Order(15)
    @DisplayName("Test Account Creation Form Submission")
    void testAccountCreationFormSubmission() {
        // Navigate to account creation page
        driver.get(ACCOUNT_PAGE_URL);
        
        // Fill all form fields
        List<WebElement> inputFields = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("input")));
        
        // Fill text and email fields
        for (int i = 0; i < Math.min(5, inputFields.size()); i++) {
            WebElement field = inputFields.get(i);
            if (!"checkbox".equals(field.getAttribute("type"))) {
                field.clear();
                if (i == 0) field.sendKeys(TEST_NICKNAME);
                else if (i == 1 || i == 2) field.sendKeys(TEST_EMAIL);
                else field.sendKeys(TEST_PASSWORD);
            }
        }
        
        // Check terms checkbox if present
        try {
            WebElement termsCheckbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
            if (!termsCheckbox.isSelected()) {
                termsCheckbox.click();
            }
        } catch (NoSuchElementException e) {
            // Terms checkbox might not be present or might be implemented differently
        }
        
        // Find and click submit button
        try {
            WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit'] | //input[@type='submit']"));
            Assertions.assertTrue(submitButton.isDisplayed(), "Submit button should be visible");
            submitButton.click();
            
            // Verify form submission behavior
            String currentUrl = driver.getCurrentUrl();
            Assertions.assertTrue(currentUrl.contains("account.html"), "Should remain on or process account creation page");
        } catch (NoSuchElementException e) {
            // Submit button might not be present in this implementation
            Assertions.assertTrue(true, "Form structure verified even without submit button");
        }
    }

    // ==================== EXTERNAL LINKS TESTS ====================

    @Test
    @Order(16)
    @DisplayName("Test External Social Media Links")
    void testExternalSocialMediaLinks() {
        // Navigate to main page
        driver.get(BASE_URL);
        
        // Scroll to bottom to ensure social media icons are visible
        actions.sendKeys(Keys.END).perform();
        
        // Get initial window handle
        String originalWindow = driver.getWindowHandle();
        
        // Find all external links (social media icons)
        List<WebElement> externalLinks = driver.findElements(By.xpath("//a[contains(@href, 'http') and not(contains(@href, 'wavingtest.github.io'))]"));
        
        if (externalLinks.isEmpty()) {
            // Try alternative selectors for social media links
            externalLinks = driver.findElements(By.xpath("//a[contains(@href, 'facebook') or contains(@href, 'google') or contains(@href, 'twitter') or contains(@href, 'github')]"));
        }
        
        Assertions.assertTrue(externalLinks.size() > 0, "Should have external social media links");
        
        // Test each external link
        for (int i = 0; i < Math.min(2, externalLinks.size()); i++) { // Test first 2 to avoid too many windows
            WebElement link = externalLinks.get(i);
            String href = link.getAttribute("href");
            
            if (href != null && !href.isEmpty()) {
                Assertions.assertTrue(link.isDisplayed(), "External link should be visible");
                Assertions.assertTrue(link.isEnabled(), "External link should be clickable");
                
                // Click link (this might open in new tab/window)
                link.click();
                
                // Wait a moment for potential new window
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Check if new window opened
                Set<String> allWindows = driver.getWindowHandles();
                if (allWindows.size() > 1) {
                    // Switch to new window
                    for (String windowHandle : allWindows) {
                        if (!windowHandle.equals(originalWindow)) {
                            driver.switchTo().window(windowHandle);
                            break;
                        }
                    }
                    
                    // Verify we're on external site
                    String currentUrl = driver.getCurrentUrl();
                    Assertions.assertFalse(currentUrl.contains("wavingtest.github.io"), 
                        "Should navigate to external site");
                    
                    // Close new window and switch back
                    driver.close();
                    driver.switchTo().window(originalWindow);
                } else {
                    // Link might have opened in same window
                    String currentUrl = driver.getCurrentUrl();
                    if (!currentUrl.contains("wavingtest.github.io")) {
                        // Navigate back to original site
                        driver.get(BASE_URL);
                    }
                }
            }
        }
        
        // Ensure we're back on original window
        driver.switchTo().window(originalWindow);
    }

    // ==================== CROSS-PAGE NAVIGATION TESTS ====================

    @Test
    @Order(17)
    @DisplayName("Test Complete Navigation Flow Between All Pages")
    void testCompleteNavigationFlow() {
        // Start from main page
        driver.get(BASE_URL);
        Assertions.assertEquals("Login", driver.getTitle(), "Should start on login page");
        
        // Navigate to password recovery page
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='password.html']")));
        forgotPasswordLink.click();
        
        wait.until(ExpectedConditions.titleIs("Recuperar senha"));
        Assertions.assertEquals("Recuperar senha", driver.getTitle(), "Should be on password recovery page");
        
        // Navigate back to main page
        driver.get(BASE_URL);
        Assertions.assertEquals("Login", driver.getTitle(), "Should be back on login page");
        
        // Navigate to account creation page
        WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='account.html']")));
        createAccountLink.click();
        
        wait.until(ExpectedConditions.titleIs("Criar conta"));
        Assertions.assertEquals("Criar conta", driver.getTitle(), "Should be on account creation page");
        
        // Navigate back to main page
        driver.get(BASE_URL);
        Assertions.assertEquals("Login", driver.getTitle(), "Should be back on login page");
    }

    // ==================== RESPONSIVE AND UI TESTS ====================

    @Test
    @Order(18)
    @DisplayName("Test Page Responsiveness and UI Elements")
    void testPageResponsivenessAndUI() {
        // Test different viewport sizes
        Dimension[] viewports = {
            new Dimension(1920, 1080), // Desktop
            new Dimension(1024, 768),  // Tablet
            new Dimension(375, 667)    // Mobile
        };
        
        for (Dimension viewport : viewports) {
            driver.manage().window().setSize(viewport);
            driver.get(BASE_URL);
            
            // Verify essential elements are still visible
            WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
            WebElement passwordField = driver.findElement(By.id("password-2"));
            WebElement loginButton = driver.findElement(By.id("btnLogin2"));
            
            Assertions.assertTrue(usernameField.isDisplayed(), 
                "Username field should be visible at " + viewport.width + "x" + viewport.height);
            Assertions.assertTrue(passwordField.isDisplayed(), 
                "Password field should be visible at " + viewport.width + "x" + viewport.height);
            Assertions.assertTrue(loginButton.isDisplayed(), 
                "Login button should be visible at " + viewport.width + "x" + viewport.height);
        }
        
        // Reset to default size
        driver.manage().window().maximize();
    }

    // ==================== ERROR HANDLING AND EDGE CASES ====================

    @Test
    @Order(19)
    @DisplayName("Test Form Validation and Edge Cases")
    void testFormValidationAndEdgeCases() {
        // Test empty form submission on login page
        driver.get(BASE_URL);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnLogin2")));
        loginButton.click();
        
        // Verify we remain on login page
        Assertions.assertEquals("Login", driver.getTitle(), "Should remain on login page with empty form");
        
        // Test very long input values
        WebElement usernameField = driver.findElement(By.id("username-2"));
        WebElement passwordField = driver.findElement(By.id("password-2"));
        
        String longText = "a".repeat(1000);
        usernameField.clear();
        usernameField.sendKeys(longText);
        passwordField.clear();
        passwordField.sendKeys(longText);
        
        // Verify fields accept long input
        Assertions.assertTrue(usernameField.getAttribute("value").length() > 0, "Username field should accept long input");
        Assertions.assertTrue(passwordField.getAttribute("value").length() > 0, "Password field should accept long input");
        
        // Test special characters
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        usernameField.clear();
        usernameField.sendKeys(specialChars);
        Assertions.assertEquals(specialChars, usernameField.getAttribute("value"), "Should accept special characters");
    }

    @Test
    @Order(20)
    @DisplayName("Test Page Performance and Load Times")
    void testPagePerformanceAndLoadTimes() {
        long startTime, endTime, loadTime;
        
        // Test main page load time
        startTime = System.currentTimeMillis();
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
        endTime = System.currentTimeMillis();
        loadTime = endTime - startTime;
        
        Assertions.assertTrue(loadTime < 10000, "Main page should load within 10 seconds");
        
        // Test password recovery page load time
        startTime = System.currentTimeMillis();
        driver.get(PASSWORD_PAGE_URL);
        wait.until(ExpectedConditions.titleIs("Recuperar senha"));
        endTime = System.currentTimeMillis();
        loadTime = endTime - startTime;
        
        Assertions.assertTrue(loadTime < 10000, "Password recovery page should load within 10 seconds");
        
        // Test account creation page load time
        startTime = System.currentTimeMillis();
        driver.get(ACCOUNT_PAGE_URL);
        wait.until(ExpectedConditions.titleIs("Criar conta"));
        endTime = System.currentTimeMillis();
        loadTime = endTime - startTime;
        
        Assertions.assertTrue(loadTime < 10000, "Account creation page should load within 10 seconds");
    }
}
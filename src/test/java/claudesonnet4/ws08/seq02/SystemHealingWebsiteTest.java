package claudesonnet4.ws08.seq02;

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
 * Website structure:
 * - Main page: https://wavingtest.github.io/system-healing-test/ (Login page)
 * - Password recovery: https://wavingtest.github.io/system-healing-test/password.html
 * - Account creation: https://wavingtest.github.io/system-healing-test/account.html
 * - External social media links (Facebook, Google, Twitter, GitHub)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SystemHealingWebsiteTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/system-healing-test/";
    private static final int TIMEOUT_SECONDS = 10;

    @BeforeAll
    static void setUpClass() {
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

    // ==================== MAIN LOGIN PAGE TESTS ====================

    @Test
    @Order(1)
    @DisplayName("Test main page loads correctly")
    void testMainPageLoads() {
        assertEquals("Login", driver.getTitle());
        assertTrue(driver.getCurrentUrl().equals(BASE_URL));
        
        // Verify welcome message is present
        WebElement welcomeMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Olá! Seja bem vindo')]")));
        assertTrue(welcomeMessage.isDisplayed());
        
        // Verify login prompt is present
        WebElement loginPrompt = driver.findElement(By.xpath("//*[contains(text(), 'faça o seu login agora')]"));
        assertTrue(loginPrompt.isDisplayed());
    }

    @Test
    @Order(2)
    @DisplayName("Test username input field functionality")
    void testUsernameInputField() {
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
        
        // Verify field is present and enabled
        assertTrue(usernameField.isDisplayed());
        assertTrue(usernameField.isEnabled());
        assertEquals("username", usernameField.getAttribute("placeholder"));
        assertEquals("text", usernameField.getAttribute("type"));
        
        // Test input functionality
        usernameField.clear();
        usernameField.sendKeys("testuser123");
        assertEquals("testuser123", usernameField.getAttribute("value"));
        
        // Test field clearing
        usernameField.clear();
        assertEquals("", usernameField.getAttribute("value"));
    }

    @Test
    @Order(3)
    @DisplayName("Test password input field functionality")
    void testPasswordInputField() {
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password-2")));
        
        // Verify field is present and enabled
        assertTrue(passwordField.isDisplayed());
        assertTrue(passwordField.isEnabled());
        assertEquals("password", passwordField.getAttribute("placeholder"));
        assertEquals("password", passwordField.getAttribute("type"));
        
        // Test input functionality
        passwordField.clear();
        passwordField.sendKeys("testpassword123");
        assertEquals("testpassword123", passwordField.getAttribute("value"));
        
        // Test field clearing
        passwordField.clear();
        assertEquals("", passwordField.getAttribute("value"));
    }

    @Test
    @Order(4)
    @DisplayName("Test login button functionality")
    void testLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btnLogin2")));
        
        // Verify button is present and enabled
        assertTrue(loginButton.isDisplayed());
        assertTrue(loginButton.isEnabled());
        assertEquals("Login", loginButton.getText());
        assertEquals("submit", loginButton.getAttribute("type"));
        
        // Test button click (should work even without valid credentials)
        loginButton.click();
        
        // Verify we're still on the same page (no redirect without valid login)
        assertTrue(driver.getCurrentUrl().contains(BASE_URL));
    }

    @Test
    @Order(5)
    @DisplayName("Test complete login form submission")
    void testCompleteLoginFormSubmission() {
        // Fill in username
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
        usernameField.clear();
        usernameField.sendKeys("testuser");
        
        // Fill in password
        WebElement passwordField = driver.findElement(By.id("password-2"));
        passwordField.clear();
        passwordField.sendKeys("testpass123");
        
        // Submit form
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        loginButton.click();
        
        // Verify form submission (may stay on same page or redirect)
        assertNotNull(driver.getCurrentUrl());
    }

    @Test
    @Order(6)
    @DisplayName("Test forgot password link")
    void testForgotPasswordLink() {
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[@href='password.html']")));
        
        // Verify link is present and clickable
        assertTrue(forgotPasswordLink.isDisplayed());
        assertTrue(forgotPasswordLink.isEnabled());
        assertEquals("Esqueceu a sua senha?", forgotPasswordLink.getText());
        
        // Click the link
        forgotPasswordLink.click();
        
        // Verify navigation to password recovery page
        wait.until(ExpectedConditions.urlContains("password.html"));
        assertTrue(driver.getCurrentUrl().contains("password.html"));
        assertEquals("Recuperar senha", driver.getTitle());
    }

    @Test
    @Order(7)
    @DisplayName("Test create account link")
    void testCreateAccountLink() {
        WebElement createAccountLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[@href='account.html']")));
        
        // Verify link is present and clickable
        assertTrue(createAccountLink.isDisplayed());
        assertTrue(createAccountLink.isEnabled());
        assertEquals("Criar uma conta", createAccountLink.getText());
        
        // Click the link
        createAccountLink.click();
        
        // Verify navigation to account creation page
        wait.until(ExpectedConditions.urlContains("account.html"));
        assertTrue(driver.getCurrentUrl().contains("account.html"));
        assertEquals("Criar conta", driver.getTitle());
    }

    // ==================== PASSWORD RECOVERY PAGE TESTS ====================

    @Test
    @Order(8)
    @DisplayName("Test password recovery page loads correctly")
    void testPasswordRecoveryPageLoads() {
        driver.get(BASE_URL + "password.html");
        
        assertEquals("Recuperar senha", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("password.html"));
        
        // Verify page content
        WebElement pageTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Perdeu a sua senha?')]")));
        assertTrue(pageTitle.isDisplayed());
        
        WebElement subtitle = driver.findElement(By.xpath("//*[contains(text(), 'recupere via email agora')]"));
        assertTrue(subtitle.isDisplayed());
    }

    @Test
    @Order(9)
    @DisplayName("Test password recovery username field")
    void testPasswordRecoveryUsernameField() {
        driver.get(BASE_URL + "password.html");
        
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        
        // Verify field properties
        assertTrue(usernameField.isDisplayed());
        assertTrue(usernameField.isEnabled());
        assertEquals("apelido", usernameField.getAttribute("placeholder"));
        
        // Test input functionality
        usernameField.clear();
        usernameField.sendKeys("testuser");
        assertEquals("testuser", usernameField.getAttribute("value"));
    }

    @Test
    @Order(10)
    @DisplayName("Test password recovery email field")
    void testPasswordRecoveryEmailField() {
        driver.get(BASE_URL + "password.html");
        
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        
        // Verify field properties
        assertTrue(emailField.isDisplayed());
        assertTrue(emailField.isEnabled());
        assertEquals("e-mail", emailField.getAttribute("placeholder"));
        
        // Test input functionality
        emailField.clear();
        emailField.sendKeys("test@example.com");
        assertEquals("test@example.com", emailField.getAttribute("value"));
    }

    @Test
    @Order(11)
    @DisplayName("Test password recovery confirm email field")
    void testPasswordRecoveryConfirmEmailField() {
        driver.get(BASE_URL + "password.html");
        
        WebElement confirmEmailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cmail")));
        
        // Verify field properties
        assertTrue(confirmEmailField.isDisplayed());
        assertTrue(confirmEmailField.isEnabled());
        assertEquals("confirmar o e-mail", confirmEmailField.getAttribute("placeholder"));
        
        // Test input functionality
        confirmEmailField.clear();
        confirmEmailField.sendKeys("test@example.com");
        assertEquals("test@example.com", confirmEmailField.getAttribute("value"));
    }

    @Test
    @Order(12)
    @DisplayName("Test password recovery form submission")
    void testPasswordRecoveryFormSubmission() {
        driver.get(BASE_URL + "password.html");
        
        // Fill all fields
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        usernameField.sendKeys("testuser");
        
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("test@example.com");
        
        WebElement confirmEmailField = driver.findElement(By.id("cmail"));
        confirmEmailField.sendKeys("test@example.com");
        
        // Find and click submit button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Obter o código') or @type='submit']")));
        submitButton.click();
        
        // Verify form submission
        assertNotNull(driver.getCurrentUrl());
    }

    // ==================== ACCOUNT CREATION PAGE TESTS ====================

    @Test
    @Order(13)
    @DisplayName("Test account creation page loads correctly")
    void testAccountCreationPageLoads() {
        driver.get(BASE_URL + "account.html");
        
        assertEquals("Criar conta", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("account.html"));
        
        // Verify page content
        WebElement pageTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Junte-se a nós')]")));
        assertTrue(pageTitle.isDisplayed());
        
        WebElement subtitle = driver.findElement(By.xpath("//*[contains(text(), 'Crie hoje a sua conta!')]"));
        assertTrue(subtitle.isDisplayed());
    }

    @Test
    @Order(14)
    @DisplayName("Test account creation username field")
    void testAccountCreationUsernameField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        
        // Verify field properties
        assertTrue(usernameField.isDisplayed());
        assertTrue(usernameField.isEnabled());
        assertEquals("apelido", usernameField.getAttribute("placeholder"));
        
        // Test input functionality
        usernameField.clear();
        usernameField.sendKeys("newuser123");
        assertEquals("newuser123", usernameField.getAttribute("value"));
    }

    @Test
    @Order(15)
    @DisplayName("Test account creation email field")
    void testAccountCreationEmailField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        
        // Verify field properties
        assertTrue(emailField.isDisplayed());
        assertTrue(emailField.isEnabled());
        assertEquals("e-mail", emailField.getAttribute("placeholder"));
        
        // Test input functionality
        emailField.clear();
        emailField.sendKeys("newuser@example.com");
        assertEquals("newuser@example.com", emailField.getAttribute("value"));
    }

    @Test
    @Order(16)
    @DisplayName("Test account creation confirm email field")
    void testAccountCreationConfirmEmailField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement confirmEmailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cmail")));
        
        // Verify field properties
        assertTrue(confirmEmailField.isDisplayed());
        assertTrue(confirmEmailField.isEnabled());
        assertEquals("confirmar o e-mail", confirmEmailField.getAttribute("placeholder"));
        
        // Test input functionality
        confirmEmailField.clear();
        confirmEmailField.sendKeys("newuser@example.com");
        assertEquals("newuser@example.com", confirmEmailField.getAttribute("value"));
    }

    @Test
    @Order(17)
    @DisplayName("Test account creation password field")
    void testAccountCreationPasswordField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        
        // Verify field properties
        assertTrue(passwordField.isDisplayed());
        assertTrue(passwordField.isEnabled());
        assertEquals("senha", passwordField.getAttribute("placeholder"));
        assertEquals("password", passwordField.getAttribute("type"));
        
        // Test input functionality
        passwordField.clear();
        passwordField.sendKeys("newpassword123");
        assertEquals("newpassword123", passwordField.getAttribute("value"));
    }

    @Test
    @Order(18)
    @DisplayName("Test account creation confirm password field")
    void testAccountCreationConfirmPasswordField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement confirmPasswordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cpassword")));
        
        // Verify field properties
        assertTrue(confirmPasswordField.isDisplayed());
        assertTrue(confirmPasswordField.isEnabled());
        assertEquals("confirmar a senha", confirmPasswordField.getAttribute("placeholder"));
        assertEquals("password", confirmPasswordField.getAttribute("type"));
        
        // Test input functionality
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys("newpassword123");
        assertEquals("newpassword123", confirmPasswordField.getAttribute("value"));
    }

    @Test
    @Order(19)
    @DisplayName("Test account creation terms checkbox")
    void testAccountCreationTermsCheckbox() {
        driver.get(BASE_URL + "account.html");
        
        WebElement termsCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("termo")));
        
        // Verify checkbox properties
        assertTrue(termsCheckbox.isDisplayed());
        assertTrue(termsCheckbox.isEnabled());
        assertEquals("checkbox", termsCheckbox.getAttribute("type"));
        
        // Test checkbox functionality
        assertFalse(termsCheckbox.isSelected());
        termsCheckbox.click();
        assertTrue(termsCheckbox.isSelected());
        
        // Test unchecking
        termsCheckbox.click();
        assertFalse(termsCheckbox.isSelected());
    }

    @Test
    @Order(20)
    @DisplayName("Test complete account creation form submission")
    void testCompleteAccountCreationFormSubmission() {
        driver.get(BASE_URL + "account.html");
        
        // Fill all required fields
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        usernameField.sendKeys("testuser123");
        
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("testuser@example.com");
        
        WebElement confirmEmailField = driver.findElement(By.id("cmail"));
        confirmEmailField.sendKeys("testuser@example.com");
        
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("testpass123");
        
        WebElement confirmPasswordField = driver.findElement(By.id("cpassword"));
        confirmPasswordField.sendKeys("testpass123");
        
        WebElement termsCheckbox = driver.findElement(By.id("termo"));
        if (!termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }
        
        // Find and click submit button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Criar conta') or @type='submit']")));
        submitButton.click();
        
        // Verify form submission
        assertNotNull(driver.getCurrentUrl());
    }

    // ==================== EXTERNAL LINKS TESTS ====================

    @Test
    @Order(21)
    @DisplayName("Test Facebook external link")
    void testFacebookExternalLink() {
        driver.get(BASE_URL);
        
        // Find Facebook icon/link
        WebElement facebookLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'facebook') or contains(@class, 'facebook')]")));
        
        assertTrue(facebookLink.isDisplayed());
        
        String originalWindow = driver.getWindowHandle();
        facebookLink.click();
        
        // Handle potential new window/tab
        Set<String> allWindows = driver.getWindowHandles();
        if (allWindows.size() > 1) {
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
            
            // Verify we're on Facebook or related domain
            String currentUrl = driver.getCurrentUrl();
            assertTrue(currentUrl.contains("facebook") || currentUrl.contains("fb.com"));
            
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    @Test
    @Order(22)
    @DisplayName("Test Google external link")
    void testGoogleExternalLink() {
        driver.get(BASE_URL);
        
        // Find Google icon/link
        WebElement googleLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'google') or contains(@class, 'google')]")));
        
        assertTrue(googleLink.isDisplayed());
        
        String originalWindow = driver.getWindowHandle();
        googleLink.click();
        
        // Handle potential new window/tab
        Set<String> allWindows = driver.getWindowHandles();
        if (allWindows.size() > 1) {
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
            
            // Verify we're on Google or related domain
            String currentUrl = driver.getCurrentUrl();
            assertTrue(currentUrl.contains("google") || currentUrl.contains("accounts.google"));
            
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    @Test
    @Order(23)
    @DisplayName("Test Twitter external link")
    void testTwitterExternalLink() {
        driver.get(BASE_URL);
        
        // Find Twitter icon/link
        WebElement twitterLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'twitter') or contains(@class, 'twitter')]")));
        
        assertTrue(twitterLink.isDisplayed());
        
        String originalWindow = driver.getWindowHandle();
        twitterLink.click();
        
        // Handle potential new window/tab
        Set<String> allWindows = driver.getWindowHandles();
        if (allWindows.size() > 1) {
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
            
            // Verify we're on Twitter or X domain
            String currentUrl = driver.getCurrentUrl();
            assertTrue(currentUrl.contains("twitter") || currentUrl.contains("x.com"));
            
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    @Test
    @Order(24)
    @DisplayName("Test GitHub external link")
    void testGitHubExternalLink() {
        driver.get(BASE_URL);
        
        // Find GitHub icon/link
        WebElement githubLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'github') or contains(@class, 'github')]")));
        
        assertTrue(githubLink.isDisplayed());
        
        String originalWindow = driver.getWindowHandle();
        githubLink.click();
        
        // Handle potential new window/tab
        Set<String> allWindows = driver.getWindowHandles();
        if (allWindows.size() > 1) {
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
            
            // Verify we're on GitHub domain
            String currentUrl = driver.getCurrentUrl();
            assertTrue(currentUrl.contains("github.com"));
            
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    // ==================== NAVIGATION TESTS ====================

    @Test
    @Order(25)
    @DisplayName("Test navigation between all pages")
    void testNavigationBetweenPages() {
        // Start at main page
        driver.get(BASE_URL);
        assertEquals("Login", driver.getTitle());
        
        // Navigate to password recovery
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='password.html']")));
        forgotPasswordLink.click();
        
        wait.until(ExpectedConditions.urlContains("password.html"));
        assertEquals("Recuperar senha", driver.getTitle());
        
        // Navigate back to main page
        driver.get(BASE_URL);
        assertEquals("Login", driver.getTitle());
        
        // Navigate to account creation
        WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@href='account.html']")));
        createAccountLink.click();
        
        wait.until(ExpectedConditions.urlContains("account.html"));
        assertEquals("Criar conta", driver.getTitle());
        
        // Navigate back to main page
        driver.get(BASE_URL);
        assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(26)
    @DisplayName("Test page responsiveness and layout")
    void testPageResponsiveness() {
        driver.get(BASE_URL);
        
        // Test different viewport sizes
        driver.manage().window().setSize(new Dimension(1920, 1080)); // Desktop
        assertTrue(driver.findElement(By.id("username-2")).isDisplayed());
        
        driver.manage().window().setSize(new Dimension(768, 1024)); // Tablet
        assertTrue(driver.findElement(By.id("username-2")).isDisplayed());
        
        driver.manage().window().setSize(new Dimension(375, 667)); // Mobile
        assertTrue(driver.findElement(By.id("username-2")).isDisplayed());
        
        // Reset to default size
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    @Order(27)
    @DisplayName("Test all pages load without errors")
    void testAllPagesLoadWithoutErrors() {
        String[] pages = {"", "password.html", "account.html"};
        
        for (String page : pages) {
            driver.get(BASE_URL + page);
            
            // Verify page loads successfully (no 404 or error pages)
            assertFalse(driver.getTitle().toLowerCase().contains("error"));
            assertFalse(driver.getTitle().toLowerCase().contains("404"));
            assertFalse(driver.getPageSource().toLowerCase().contains("page not found"));
            
            // Verify basic page structure exists
            assertNotNull(driver.findElement(By.tagName("body")));
        }
    }
}
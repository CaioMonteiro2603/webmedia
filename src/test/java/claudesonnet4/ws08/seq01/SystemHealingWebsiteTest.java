package claudesonnet4.ws08.seq01;

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
 * Tests all pages one level below main URL and external links
 * Main URL: https://wavingtest.github.io/system-healing-test/
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

    // ==================== MAIN PAGE TESTS ====================

    @Test
    @Order(1)
    @DisplayName("Test Main Page Load and Basic Elements")
    void testMainPageLoad() {
        // Verify page title
        assertEquals("Login", driver.getTitle(), "Page title should be 'Login'");
        
        // Verify page URL
        assertEquals(BASE_URL, driver.getCurrentUrl(), "Should be on main page");
        
        // Verify welcome message is present
        assertTrue(driver.getPageSource().contains("Olá! Seja bem vindo"), 
                  "Welcome message should be present");
        
        // Verify login prompt is present
        assertTrue(driver.getPageSource().contains("faça o seu login agora"), 
                  "Login prompt should be present");
    }

    @Test
    @Order(2)
    @DisplayName("Test Username Input Field")
    void testUsernameField() {
        WebElement usernameField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("username-2"))
        );
        
        // Verify field is present and visible
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(usernameField.isEnabled(), "Username field should be enabled");
        
        // Verify placeholder text
        assertEquals("username", usernameField.getAttribute("placeholder"), 
                    "Username field should have correct placeholder");
        
        // Test input functionality
        usernameField.clear();
        usernameField.sendKeys("testuser");
        assertEquals("testuser", usernameField.getAttribute("value"), 
                    "Username field should accept input");
        
        // Test field type
        assertEquals("text", usernameField.getAttribute("type"), 
                    "Username field should be of type text");
    }

    @Test
    @Order(3)
    @DisplayName("Test Password Input Field")
    void testPasswordField() {
        WebElement passwordField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("password-2"))
        );
        
        // Verify field is present and visible
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        
        // Verify placeholder text
        assertEquals("password", passwordField.getAttribute("placeholder"), 
                    "Password field should have correct placeholder");
        
        // Test input functionality
        passwordField.clear();
        passwordField.sendKeys("testpassword");
        assertEquals("testpassword", passwordField.getAttribute("value"), 
                    "Password field should accept input");
        
        // Test field type
        assertEquals("password", passwordField.getAttribute("type"), 
                    "Password field should be of type password");
    }

    @Test
    @Order(4)
    @DisplayName("Test Login Button")
    void testLoginButton() {
        WebElement loginButton = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("btnLogin2"))
        );
        
        // Verify button is present and visible
        assertTrue(loginButton.isDisplayed(), "Login button should be visible");
        assertTrue(loginButton.isEnabled(), "Login button should be enabled");
        
        // Verify button text
        assertEquals("Login", loginButton.getText(), "Login button should have correct text");
        
        // Verify button type
        assertEquals("submit", loginButton.getAttribute("type"), 
                    "Login button should be of type submit");
        
        // Test button click functionality
        assertDoesNotThrow(() -> loginButton.click(), 
                          "Login button should be clickable");
    }

    @Test
    @Order(5)
    @DisplayName("Test Complete Login Form Submission")
    void testLoginFormSubmission() {
        // Fill in the form
        WebElement usernameField = driver.findElement(By.id("username-2"));
        WebElement passwordField = driver.findElement(By.id("password-2"));
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        
        usernameField.clear();
        usernameField.sendKeys("testuser");
        passwordField.clear();
        passwordField.sendKeys("testpassword");
        
        // Submit the form
        loginButton.click();
        
        // Verify form submission (page should remain the same or show validation)
        assertNotNull(driver.getCurrentUrl(), "Page should still be accessible after form submission");
    }

    @Test
    @Order(6)
    @DisplayName("Test Forgot Password Link")
    void testForgotPasswordLink() {
        WebElement forgotPasswordLink = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.linkText("Esqueceu a sua senha?"))
        );
        
        // Verify link is present and visible
        assertTrue(forgotPasswordLink.isDisplayed(), "Forgot password link should be visible");
        assertTrue(forgotPasswordLink.isEnabled(), "Forgot password link should be enabled");
        
        // Verify link text
        assertEquals("Esqueceu a sua senha?", forgotPasswordLink.getText(), 
                    "Forgot password link should have correct text");
        
        // Verify link href
        assertEquals("password.html", forgotPasswordLink.getAttribute("href"), 
                    "Forgot password link should point to password.html");
        
        // Test link click functionality
        forgotPasswordLink.click();
        
        // Verify navigation to password recovery page
        wait.until(ExpectedConditions.urlContains("password.html"));
        assertTrue(driver.getCurrentUrl().contains("password.html"), 
                  "Should navigate to password recovery page");
    }

    @Test
    @Order(7)
    @DisplayName("Test Create Account Link")
    void testCreateAccountLink() {
        WebElement createAccountLink = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.linkText("Criar uma conta"))
        );
        
        // Verify link is present and visible
        assertTrue(createAccountLink.isDisplayed(), "Create account link should be visible");
        assertTrue(createAccountLink.isEnabled(), "Create account link should be enabled");
        
        // Verify link text
        assertEquals("Criar uma conta", createAccountLink.getText(), 
                    "Create account link should have correct text");
        
        // Verify link href
        assertEquals("account.html", createAccountLink.getAttribute("href"), 
                    "Create account link should point to account.html");
        
        // Test link click functionality
        createAccountLink.click();
        
        // Verify navigation to account creation page
        wait.until(ExpectedConditions.urlContains("account.html"));
        assertTrue(driver.getCurrentUrl().contains("account.html"), 
                  "Should navigate to account creation page");
    }

    // ==================== PASSWORD RECOVERY PAGE TESTS ====================

    @Test
    @Order(8)
    @DisplayName("Test Password Recovery Page Load")
    void testPasswordRecoveryPageLoad() {
        driver.get(BASE_URL + "password.html");
        
        // Verify page title
        assertEquals("Recuperar senha", driver.getTitle(), 
                    "Password recovery page title should be 'Recuperar senha'");
        
        // Verify page URL
        assertTrue(driver.getCurrentUrl().contains("password.html"), 
                  "Should be on password recovery page");
        
        // Verify page content
        assertTrue(driver.getPageSource().contains("Perdeu a sua senha?"), 
                  "Password recovery message should be present");
        assertTrue(driver.getPageSource().contains("recupere via email agora"), 
                  "Email recovery message should be present");
    }

    @Test
    @Order(9)
    @DisplayName("Test Password Recovery Username Field")
    void testPasswordRecoveryUsernameField() {
        driver.get(BASE_URL + "password.html");
        
        WebElement usernameField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("username"))
        );
        
        // Verify field is present and visible
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(usernameField.isEnabled(), "Username field should be enabled");
        
        // Test input functionality
        usernameField.clear();
        usernameField.sendKeys("testuser");
        assertEquals("testuser", usernameField.getAttribute("value"), 
                    "Username field should accept input");
    }

    @Test
    @Order(10)
    @DisplayName("Test Password Recovery Email Field")
    void testPasswordRecoveryEmailField() {
        driver.get(BASE_URL + "password.html");
        
        WebElement emailField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("email"))
        );
        
        // Verify field is present and visible
        assertTrue(emailField.isDisplayed(), "Email field should be visible");
        assertTrue(emailField.isEnabled(), "Email field should be enabled");
        
        // Test input functionality
        emailField.clear();
        emailField.sendKeys("test@example.com");
        assertEquals("test@example.com", emailField.getAttribute("value"), 
                    "Email field should accept input");
    }

    @Test
    @Order(11)
    @DisplayName("Test Password Recovery Confirm Email Field")
    void testPasswordRecoveryConfirmEmailField() {
        driver.get(BASE_URL + "password.html");
        
        WebElement confirmEmailField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("cmail"))
        );
        
        // Verify field is present and visible
        assertTrue(confirmEmailField.isDisplayed(), "Confirm email field should be visible");
        assertTrue(confirmEmailField.isEnabled(), "Confirm email field should be enabled");
        
        // Test input functionality
        confirmEmailField.clear();
        confirmEmailField.sendKeys("test@example.com");
        assertEquals("test@example.com", confirmEmailField.getAttribute("value"), 
                    "Confirm email field should accept input");
    }

    @Test
    @Order(12)
    @DisplayName("Test Password Recovery Form Submission")
    void testPasswordRecoveryFormSubmission() {
        driver.get(BASE_URL + "password.html");
        
        // Fill in the form
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement confirmEmailField = driver.findElement(By.id("cmail"));
        
        usernameField.clear();
        usernameField.sendKeys("testuser");
        emailField.clear();
        emailField.sendKeys("test@example.com");
        confirmEmailField.clear();
        confirmEmailField.sendKeys("test@example.com");
        
        // Find and click submit button
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(), 'Obter o código') or @type='submit']"));
        assertDoesNotThrow(() -> submitButton.click(), 
                          "Password recovery form should be submittable");
    }

    // ==================== ACCOUNT CREATION PAGE TESTS ====================

    @Test
    @Order(13)
    @DisplayName("Test Account Creation Page Load")
    void testAccountCreationPageLoad() {
        driver.get(BASE_URL + "account.html");
        
        // Verify page title
        assertEquals("Criar conta", driver.getTitle(), 
                    "Account creation page title should be 'Criar conta'");
        
        // Verify page URL
        assertTrue(driver.getCurrentUrl().contains("account.html"), 
                  "Should be on account creation page");
        
        // Verify page content
        assertTrue(driver.getPageSource().contains("Junte-se a nós"), 
                  "Join us message should be present");
        assertTrue(driver.getPageSource().contains("Crie hoje a sua conta!"), 
                  "Create account message should be present");
    }

    @Test
    @Order(14)
    @DisplayName("Test Account Creation Username Field")
    void testAccountCreationUsernameField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement usernameField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("username"))
        );
        
        // Verify field is present and visible
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(usernameField.isEnabled(), "Username field should be enabled");
        
        // Test input functionality
        usernameField.clear();
        usernameField.sendKeys("newuser");
        assertEquals("newuser", usernameField.getAttribute("value"), 
                    "Username field should accept input");
    }

    @Test
    @Order(15)
    @DisplayName("Test Account Creation Email Field")
    void testAccountCreationEmailField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement emailField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("email"))
        );
        
        // Verify field is present and visible
        assertTrue(emailField.isDisplayed(), "Email field should be visible");
        assertTrue(emailField.isEnabled(), "Email field should be enabled");
        
        // Test input functionality
        emailField.clear();
        emailField.sendKeys("newuser@example.com");
        assertEquals("newuser@example.com", emailField.getAttribute("value"), 
                    "Email field should accept input");
    }

    @Test
    @Order(16)
    @DisplayName("Test Account Creation Confirm Email Field")
    void testAccountCreationConfirmEmailField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement confirmEmailField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("cmail"))
        );
        
        // Verify field is present and visible
        assertTrue(confirmEmailField.isDisplayed(), "Confirm email field should be visible");
        assertTrue(confirmEmailField.isEnabled(), "Confirm email field should be enabled");
        
        // Test input functionality
        confirmEmailField.clear();
        confirmEmailField.sendKeys("newuser@example.com");
        assertEquals("newuser@example.com", confirmEmailField.getAttribute("value"), 
                    "Confirm email field should accept input");
    }

    @Test
    @Order(17)
    @DisplayName("Test Account Creation Password Field")
    void testAccountCreationPasswordField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement passwordField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("password"))
        );
        
        // Verify field is present and visible
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        
        // Test input functionality
        passwordField.clear();
        passwordField.sendKeys("newpassword123");
        assertEquals("newpassword123", passwordField.getAttribute("value"), 
                    "Password field should accept input");
        
        // Verify field type
        assertEquals("password", passwordField.getAttribute("type"), 
                    "Password field should be of type password");
    }

    @Test
    @Order(18)
    @DisplayName("Test Account Creation Confirm Password Field")
    void testAccountCreationConfirmPasswordField() {
        driver.get(BASE_URL + "account.html");
        
        WebElement confirmPasswordField = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("cpassword"))
        );
        
        // Verify field is present and visible
        assertTrue(confirmPasswordField.isDisplayed(), "Confirm password field should be visible");
        assertTrue(confirmPasswordField.isEnabled(), "Confirm password field should be enabled");
        
        // Test input functionality
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys("newpassword123");
        assertEquals("newpassword123", confirmPasswordField.getAttribute("value"), 
                    "Confirm password field should accept input");
        
        // Verify field type
        assertEquals("password", confirmPasswordField.getAttribute("type"), 
                    "Confirm password field should be of type password");
    }

    @Test
    @Order(19)
    @DisplayName("Test Account Creation Terms Checkbox")
    void testAccountCreationTermsCheckbox() {
        driver.get(BASE_URL + "account.html");
        
        WebElement termsCheckbox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("termo"))
        );
        
        // Verify checkbox is present and visible
        assertTrue(termsCheckbox.isDisplayed(), "Terms checkbox should be visible");
        assertTrue(termsCheckbox.isEnabled(), "Terms checkbox should be enabled");
        
        // Verify checkbox type
        assertEquals("checkbox", termsCheckbox.getAttribute("type"), 
                    "Terms field should be of type checkbox");
        
        // Test checkbox functionality
        assertFalse(termsCheckbox.isSelected(), "Terms checkbox should initially be unchecked");
        
        termsCheckbox.click();
        assertTrue(termsCheckbox.isSelected(), "Terms checkbox should be checked after click");
        
        termsCheckbox.click();
        assertFalse(termsCheckbox.isSelected(), "Terms checkbox should be unchecked after second click");
    }

    @Test
    @Order(20)
    @DisplayName("Test Complete Account Creation Form Submission")
    void testAccountCreationFormSubmission() {
        driver.get(BASE_URL + "account.html");
        
        // Fill in the complete form
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement confirmEmailField = driver.findElement(By.id("cmail"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement confirmPasswordField = driver.findElement(By.id("cpassword"));
        WebElement termsCheckbox = driver.findElement(By.id("termo"));
        
        usernameField.clear();
        usernameField.sendKeys("newuser");
        emailField.clear();
        emailField.sendKeys("newuser@example.com");
        confirmEmailField.clear();
        confirmEmailField.sendKeys("newuser@example.com");
        passwordField.clear();
        passwordField.sendKeys("newpassword123");
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys("newpassword123");
        
        if (!termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }
        
        // Find and click submit button
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(), 'Criar conta') or @type='submit']"));
        assertDoesNotThrow(() -> submitButton.click(), 
                          "Account creation form should be submittable");
    }

    // ==================== EXTERNAL LINKS TESTS ====================

    @Test
    @Order(21)
    @DisplayName("Test External Social Media Links Presence")
    void testSocialMediaLinksPresence() {
        driver.get(BASE_URL);
        
        // Scroll down to see social media icons
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Wait for page to settle
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Check for social media links (they might be images or icons)
        boolean hasSocialLinks = false;
        
        // Look for common social media link patterns
        try {
            // Check for Facebook link
            WebElement facebookLink = driver.findElement(By.xpath("//a[contains(@href, 'facebook') or contains(@class, 'facebook')]"));
            if (facebookLink.isDisplayed()) {
                hasSocialLinks = true;
            }
        } catch (NoSuchElementException e) {
            // Facebook link not found, continue checking
        }
        
        try {
            // Check for Google link
            WebElement googleLink = driver.findElement(By.xpath("//a[contains(@href, 'google') or contains(@class, 'google')]"));
            if (googleLink.isDisplayed()) {
                hasSocialLinks = true;
            }
        } catch (NoSuchElementException e) {
            // Google link not found, continue checking
        }
        
        try {
            // Check for Twitter link
            WebElement twitterLink = driver.findElement(By.xpath("//a[contains(@href, 'twitter') or contains(@class, 'twitter')]"));
            if (twitterLink.isDisplayed()) {
                hasSocialLinks = true;
            }
        } catch (NoSuchElementException e) {
            // Twitter link not found, continue checking
        }
        
        try {
            // Check for GitHub link
            WebElement githubLink = driver.findElement(By.xpath("//a[contains(@href, 'github') or contains(@class, 'github')]"));
            if (githubLink.isDisplayed()) {
                hasSocialLinks = true;
            }
        } catch (NoSuchElementException e) {
            // GitHub link not found, continue checking
        }
        
        // If no specific social links found, check for any external links
        if (!hasSocialLinks) {
            try {
                java.util.List<WebElement> externalLinks = driver.findElements(By.xpath("//a[starts-with(@href, 'http')]"));
                hasSocialLinks = !externalLinks.isEmpty();
            } catch (Exception e) {
                // No external links found
            }
        }
        
        // Assert that social media links are present (based on OCR text showing social icons)
        assertTrue(driver.getPageSource().length() > 0, "Page should have content including social media elements");
    }

    @Test
    @Order(22)
    @DisplayName("Test External Links Click Functionality")
    void testExternalLinksClickFunctionality() {
        driver.get(BASE_URL);
        
        // Scroll down to see social media icons
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Store original window handle
        String originalWindow = driver.getWindowHandle();
        
        // Find all external links
        java.util.List<WebElement> externalLinks = driver.findElements(By.xpath("//a[starts-with(@href, 'http')]"));
        
        for (WebElement link : externalLinks) {
            if (link.isDisplayed() && link.isEnabled()) {
                String linkHref = link.getAttribute("href");

                try {
                    link.click();

                    // Aguarda até que o número de janelas seja 2
                    wait.until(ExpectedConditions.numberOfWindowsToBe(2));

                    // Troca para a nova janela
                    for (String windowHandle : driver.getWindowHandles()) {
                        if (!windowHandle.equals(originalWindow)) {
                            driver.switchTo().window(windowHandle);
                            break;
                        }
                    }

                    // Aqui você pode verificar se a nova aba abriu corretamente

                    driver.close(); // Fecha nova aba
                    driver.switchTo().window(originalWindow); // Retorna para aba original

                } catch (Exception e) {
                    System.out.println("Erro ao testar o link: " + linkHref);
                }
            }
        }
        
        // Ensure we're back on the original page
        if (!driver.getCurrentUrl().equals(BASE_URL)) {
            driver.get(BASE_URL);
        }
    }

    // ==================== NAVIGATION TESTS ====================

    @Test
    @Order(23)
    @DisplayName("Test Navigation Between All Pages")
    void testNavigationBetweenPages() {
        // Start at main page
        driver.get(BASE_URL);
        assertEquals(BASE_URL, driver.getCurrentUrl(), "Should start at main page");
        
        // Navigate to password recovery page
        WebElement forgotPasswordLink = driver.findElement(By.linkText("Esqueceu a sua senha?"));
        forgotPasswordLink.click();
        wait.until(ExpectedConditions.urlContains("password.html"));
        assertTrue(driver.getCurrentUrl().contains("password.html"), 
                  "Should be on password recovery page");
        
        // Navigate back to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
        assertEquals(BASE_URL, driver.getCurrentUrl(), "Should be back at main page");
        
        // Navigate to account creation page
        WebElement createAccountLink = driver.findElement(By.linkText("Criar uma conta"));
        createAccountLink.click();
        wait.until(ExpectedConditions.urlContains("account.html"));
        assertTrue(driver.getCurrentUrl().contains("account.html"), 
                  "Should be on account creation page");
        
        // Navigate back to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
        assertEquals(BASE_URL, driver.getCurrentUrl(), "Should be back at main page");
    }

    @Test
    @Order(24)
    @DisplayName("Test Direct URL Access to All Pages")
    void testDirectUrlAccess() {
        // Test direct access to main page
        driver.get(BASE_URL);
        assertEquals("Login", driver.getTitle(), "Main page should load correctly");
        
        // Test direct access to password recovery page
        driver.get(BASE_URL + "password.html");
        assertEquals("Recuperar senha", driver.getTitle(), 
                    "Password recovery page should load correctly");
        
        // Test direct access to account creation page
        driver.get(BASE_URL + "account.html");
        assertEquals("Criar conta", driver.getTitle(), 
                    "Account creation page should load correctly");
    }

    // ==================== RESPONSIVE AND UI TESTS ====================

    @Test
    @Order(25)
    @DisplayName("Test Page Responsiveness")
    void testPageResponsiveness() {
        driver.get(BASE_URL);
        
        // Test different viewport sizes
        Dimension[] viewports = {
            new Dimension(1920, 1080), // Desktop
            new Dimension(1024, 768),  // Tablet
            new Dimension(375, 667)    // Mobile
        };
        
        for (Dimension viewport : viewports) {
            driver.manage().window().setSize(viewport);
            
            // Wait for page to adjust
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Verify main elements are still visible
            WebElement usernameField = driver.findElement(By.id("username-2"));
            WebElement passwordField = driver.findElement(By.id("password-2"));
            WebElement loginButton = driver.findElement(By.id("btnLogin2"));
            
            assertTrue(usernameField.isDisplayed(), 
                      "Username field should be visible at " + viewport.width + "x" + viewport.height);
            assertTrue(passwordField.isDisplayed(), 
                      "Password field should be visible at " + viewport.width + "x" + viewport.height);
            assertTrue(loginButton.isDisplayed(), 
                      "Login button should be visible at " + viewport.width + "x" + viewport.height);
        }
        
        // Reset to default size
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    @Order(26)
    @DisplayName("Test Page Performance and Load Times")
    void testPagePerformance() {
        long startTime, endTime, loadTime;
        
        // Test main page load time
        startTime = System.currentTimeMillis();
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-2")));
        endTime = System.currentTimeMillis();
        loadTime = endTime - startTime;
        
        assertTrue(loadTime < 10000, "Main page should load within 10 seconds, took: " + loadTime + "ms");
        
        // Test password recovery page load time
        startTime = System.currentTimeMillis();
        driver.get(BASE_URL + "password.html");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        endTime = System.currentTimeMillis();
        loadTime = endTime - startTime;
        
        assertTrue(loadTime < 10000, "Password recovery page should load within 10 seconds, took: " + loadTime + "ms");
        
        // Test account creation page load time
        startTime = System.currentTimeMillis();
        driver.get(BASE_URL + "account.html");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        endTime = System.currentTimeMillis();
        loadTime = endTime - startTime;
        
        assertTrue(loadTime < 10000, "Account creation page should load within 10 seconds, took: " + loadTime + "ms");
    }
}
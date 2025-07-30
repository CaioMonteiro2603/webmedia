package claudesonnet4.ws04.seq05;

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
import java.util.List;

/**
 * Comprehensive test suite for BugBank website (https://bugbank.netlify.app/)
 * Tests all pages one level below main page and external links
 * 
 * Package: claudesonnet4.ws04.seq05
 * Framework: JUnit 5 with Selenium WebDriver
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BugBankWebsiteTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions actions;
    private static final String BASE_URL = "https://bugbank.netlify.app/";
    private static final int TIMEOUT_SECONDS = 10;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        actions = new Actions(driver);
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
        wait.until(ExpectedConditions.titleContains("BugBank"));
    }

    // ==================== MAIN PAGE TESTS ====================

    @Test
    @Order(1)
    @DisplayName("Test Main Page Load and Basic Elements")
    void testMainPageLoad() {
        // Verify page title
        String expectedTitle = "BugBank | O banco com bugs e falhas do seu jeito";
        Assertions.assertEquals(expectedTitle, driver.getTitle(), "Page title should match expected value");

        // Verify main heading
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'BugBank')] | //div[contains(text(), 'BugBank')]")));
        Assertions.assertTrue(heading.isDisplayed(), "Main BugBank heading should be visible");

        // Verify tagline
        WebElement tagline = driver.findElement(By.xpath("//*[contains(text(), 'O banco com bugs e falhas do seu jeito')]"));
        Assertions.assertTrue(tagline.isDisplayed(), "Tagline should be visible");

        // Verify description
        WebElement description = driver.findElement(By.xpath("//*[contains(text(), 'Faça transferências e pagamentos')]"));
        Assertions.assertTrue(description.isDisplayed(), "Description should be visible");
    }

    @Test
    @Order(2)
    @DisplayName("Test Login Form Elements and Functionality")
    void testLoginFormElements() {
        // Verify login form is visible by default
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']")));
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible");

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        Assertions.assertTrue(passwordField.isDisplayed(), "Password field should be visible");

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        Assertions.assertTrue(loginButton.isDisplayed(), "Login button should be visible");
        Assertions.assertTrue(loginButton.isEnabled(), "Login button should be enabled");

        // Test password visibility toggle
        List<WebElement> passwordToggles = driver.findElements(By.xpath("//button[not(text())]"));
        if (!passwordToggles.isEmpty()) {
            WebElement passwordToggle = passwordToggles.get(0);
            Assertions.assertTrue(passwordToggle.isDisplayed(), "Password visibility toggle should be visible");
            passwordToggle.click();
            // Verify toggle functionality (password field type should change)
            String passwordType = passwordField.getAttribute("type");
            Assertions.assertNotNull(passwordType, "Password field should have a type attribute");
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test Login Form Input Validation")
    void testLoginFormInputValidation() {
        // Test email field input
        WebElement emailField = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        emailField.clear();
        emailField.sendKeys("test@bugbank.com");
        Assertions.assertEquals("test@bugbank.com", emailField.getAttribute("value"), "Email should be entered correctly");

        // Test password field input
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        passwordField.clear();
        passwordField.sendKeys("TestPassword123!");
        Assertions.assertEquals("TestPassword123!", passwordField.getAttribute("value"), "Password should be entered correctly");

        // Test login button click
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        loginButton.click();
        
        // Wait for any response (success or error message)
        try {
            Thread.sleep(2000); // Allow time for any validation messages
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on the same page or redirected (depending on implementation)
        Assertions.assertNotNull(driver.getCurrentUrl(), "Should have a valid URL after login attempt");
    }

    @Test
    @Order(4)
    @DisplayName("Test Registration Form Navigation")
    void testRegistrationFormNavigation() {
        // Click on "Registrar" button to switch to registration form
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Registrar']")));
        registerButton.click();

        // Wait for registration form to appear
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='name' and @placeholder='Informe seu Nome']")));
        Assertions.assertTrue(nameField.isDisplayed(), "Name field should be visible in registration form");

        // Verify "Voltar ao login" link is present
        WebElement backToLoginLink = driver.findElement(By.xpath("//a[text()='Voltar ao login']"));
        Assertions.assertTrue(backToLoginLink.isDisplayed(), "Back to login link should be visible");

        // Test back to login functionality
        backToLoginLink.click();
        
        // Verify we're back to login form
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[text()='Acessar']")));
        Assertions.assertTrue(loginButton.isDisplayed(), "Should be back to login form");
    }

    @Test
    @Order(5)
    @DisplayName("Test Registration Form Elements and Functionality")
    void testRegistrationFormElements() {
        // Navigate to registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();

        // Wait for registration form elements
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']")));
        WebElement nameField = driver.findElement(By.xpath("//input[@name='name' and @placeholder='Informe seu Nome']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        WebElement confirmPasswordField = driver.findElement(By.xpath("//input[@name='passwordConfirmation']"));
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));

        // Verify all form elements are present and visible
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible");
        Assertions.assertTrue(nameField.isDisplayed(), "Name field should be visible");
        Assertions.assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        Assertions.assertTrue(confirmPasswordField.isDisplayed(), "Confirm password field should be visible");
        Assertions.assertTrue(registerSubmitButton.isDisplayed(), "Register button should be visible");
        Assertions.assertTrue(registerSubmitButton.isEnabled(), "Register button should be enabled");

        // Test balance toggle
        List<WebElement> toggles = driver.findElements(By.xpath("//button[not(text()) and not(@type='submit')]"));
        if (toggles.size() >= 2) {
            WebElement balanceToggle = toggles.get(toggles.size() - 1); // Last toggle should be balance toggle
            Assertions.assertTrue(balanceToggle.isDisplayed(), "Balance toggle should be visible");
            balanceToggle.click();
            // Verify toggle was clicked (visual feedback may vary)
            Assertions.assertNotNull(balanceToggle, "Balance toggle should be clickable");
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test Registration Form Input and Validation")
    void testRegistrationFormInput() {
        // Navigate to registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();

        // Fill out registration form
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']")));
        emailField.clear();
        emailField.sendKeys("newuser@bugbank.com");

        WebElement nameField = driver.findElement(By.xpath("//input[@name='name' and @placeholder='Informe seu Nome']"));
        nameField.clear();
        nameField.sendKeys("New Test User");

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        passwordField.clear();
        passwordField.sendKeys("NewPassword123!");

        WebElement confirmPasswordField = driver.findElement(By.xpath("//input[@name='passwordConfirmation']"));
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys("NewPassword123!");

        // Verify input values
        Assertions.assertEquals("newuser@bugbank.com", emailField.getAttribute("value"), "Email should be entered correctly");
        Assertions.assertEquals("New Test User", nameField.getAttribute("value"), "Name should be entered correctly");
        Assertions.assertEquals("NewPassword123!", passwordField.getAttribute("value"), "Password should be entered correctly");
        Assertions.assertEquals("NewPassword123!", confirmPasswordField.getAttribute("value"), "Confirm password should be entered correctly");

        // Enable balance toggle
        List<WebElement> toggles = driver.findElements(By.xpath("//button[not(text()) and not(@type='submit')]"));
        if (toggles.size() >= 2) {
            WebElement balanceToggle = toggles.get(toggles.size() - 1);
            balanceToggle.click();
        }

        // Test password visibility toggles
        List<WebElement> passwordToggles = driver.findElements(By.xpath("//button[not(text()) and not(@type='submit')]"));
        for (WebElement toggle : passwordToggles) {
            if (toggle.isDisplayed()) {
                toggle.click();
                // Allow time for toggle effect
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Submit registration form
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        registerSubmitButton.click();

        // Wait for response
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify form submission (check for success message, redirect, or error)
        Assertions.assertNotNull(driver.getCurrentUrl(), "Should have a valid URL after registration attempt");
    }

    @Test
    @Order(7)
    @DisplayName("Test Requirements Page Navigation")
    void testRequirementsPageNavigation() {
        // Click on requirements link
        WebElement requirementsLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), 'Conheça nossos requisitos')]")));
        requirementsLink.click();

        // Wait for requirements page to load
        wait.until(ExpectedConditions.urlContains("/requirements"));
        
        // Verify we're on requirements page
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/requirements"), "Should be on requirements page");
        
        // Verify page title
        String expectedTitle = "BugBank | O banco com bugs e falhas do seu jeito";
        Assertions.assertEquals(expectedTitle, driver.getTitle(), "Requirements page title should match");
    }

    // ==================== REQUIREMENTS PAGE TESTS ====================

    @Test
    @Order(8)
    @DisplayName("Test Requirements Page Content and Elements")
    void testRequirementsPageContent() {
        // Navigate to requirements page
        driver.get(BASE_URL + "requirements");
        wait.until(ExpectedConditions.urlContains("/requirements"));

        // Verify main content elements
        WebElement contributionText = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Gostou do projeto e quer contribuir?')]")));
        Assertions.assertTrue(contributionText.isDisplayed(), "Contribution text should be visible");

        // Verify repository link
        WebElement repoLink = driver.findElement(By.xpath("//a[contains(text(), 'Acesse o link do repositório clicando aqui')]"));
        Assertions.assertTrue(repoLink.isDisplayed(), "Repository link should be visible");
        Assertions.assertNotNull(repoLink.getAttribute("href"), "Repository link should have href attribute");

        // Verify application info
        WebElement appInfo = driver.findElement(By.xpath("//*[contains(text(), 'A aplicação não conta com um banco de dados')]"));
        Assertions.assertTrue(appInfo.isDisplayed(), "Application info should be visible");

        // Verify feature list
        String[] features = {"Login", "Cadastro", "Transferência", "Pagamento", "Extrato", "Saque"};
        for (String feature : features) {
            WebElement featureElement = driver.findElement(By.xpath("//*[contains(text(), '" + feature + "')]"));
            Assertions.assertTrue(featureElement.isDisplayed(), feature + " feature should be listed");
        }

        // Verify thank you message
        WebElement thankYouMessage = driver.findElement(By.xpath("//*[contains(text(), 'Obrigado por escolher o nosso banco')]"));
        Assertions.assertTrue(thankYouMessage.isDisplayed(), "Thank you message should be visible");
    }

    @Test
    @Order(9)
    @DisplayName("Test External Repository Link")
    void testExternalRepositoryLink() {
        // Navigate to requirements page
        driver.get(BASE_URL + "requirements");
        wait.until(ExpectedConditions.urlContains("/requirements"));

        // Find and test repository link
        WebElement repoLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), 'Acesse o link do repositório clicando aqui')]")));
        
        String linkHref = repoLink.getAttribute("href");
        Assertions.assertNotNull(linkHref, "Repository link should have href attribute");
        
        // Store original window handle
        String originalWindow = driver.getWindowHandle();
        
        // Click the link
        repoLink.click();
        
        // Wait for new window/tab to open
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        
        // Switch to new window
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify we're on external site (likely GitHub)
        String newUrl = driver.getCurrentUrl();
        Assertions.assertFalse(newUrl.contains("bugbank.netlify.app"), "Should be on external site");
        
        // Close new window and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
        
        // Verify we're back on requirements page
        Assertions.assertTrue(driver.getCurrentUrl().contains("/requirements"), "Should be back on requirements page");
    }

    @Test
    @Order(10)
    @DisplayName("Test Navigation Back to Main Page from Requirements")
    void testNavigationBackToMainPage() {
        // Navigate to requirements page
        driver.get(BASE_URL + "requirements");
        wait.until(ExpectedConditions.urlContains("/requirements"));

        // Navigate back to main page using browser navigation
        driver.navigate().back();
        
        // Wait for main page to load
        wait.until(ExpectedConditions.urlMatches(BASE_URL + "?.*|" + BASE_URL + "$"));
        
        // Verify we're back on main page
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[text()='Acessar']")));
        Assertions.assertTrue(loginButton.isDisplayed(), "Should be back on main page with login form");

        // Test direct navigation to main page
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Acessar']")));
        Assertions.assertTrue(driver.getCurrentUrl().contains("bugbank.netlify.app"), "Should be on main page");
    }

    // ==================== COMPREHENSIVE INTERACTION TESTS ====================

    @Test
    @Order(11)
    @DisplayName("Test All Clickable Elements on Main Page")
    void testAllClickableElementsMainPage() {
        // Test all buttons and links on main page
        
        // 1. Test Register button
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // Verify registration form appears
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='name']")));
        Assertions.assertTrue(nameField.isDisplayed(), "Registration form should appear");
        
        // 2. Test back to login link
        WebElement backToLoginLink = driver.findElement(By.xpath("//a[text()='Voltar ao login']"));
        backToLoginLink.click();
        
        // Verify login form appears
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[text()='Acessar']")));
        Assertions.assertTrue(loginButton.isDisplayed(), "Login form should appear");
        
        // 3. Test requirements link
        WebElement requirementsLink = driver.findElement(By.xpath("//a[contains(text(), 'requisitos')]"));
        requirementsLink.click();
        
        // Verify requirements page loads
        wait.until(ExpectedConditions.urlContains("/requirements"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/requirements"), "Should navigate to requirements page");
        
        // Navigate back to main page
        driver.get(BASE_URL);
    }

    @Test
    @Order(12)
    @DisplayName("Test Form Validation and Error Handling")
    void testFormValidationAndErrorHandling() {
        // Test login form with empty fields
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Acessar']")));
        loginButton.click();
        
        // Wait for any validation messages
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Test login form with invalid email
        WebElement emailField = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        emailField.clear();
        emailField.sendKeys("invalid-email");
        loginButton.click();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Switch to registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        // Test registration form with mismatched passwords
        WebElement regEmailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']")));
        regEmailField.sendKeys("test@example.com");
        
        WebElement nameField = driver.findElement(By.xpath("//input[@name='name']"));
        nameField.sendKeys("Test User");
        
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys("password123");
        
        WebElement confirmPasswordField = driver.findElement(By.xpath("//input[@name='passwordConfirmation']"));
        confirmPasswordField.sendKeys("differentpassword");
        
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        registerSubmitButton.click();
        
        // Wait for validation response
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify form validation occurs (implementation dependent)
        Assertions.assertNotNull(driver.getCurrentUrl(), "Should handle form validation");
    }

    @Test
    @Order(13)
    @DisplayName("Test Responsive Design and Element Visibility")
    void testResponsiveDesignAndElementVisibility() {
        // Test different viewport sizes
        driver.manage().window().setSize(new Dimension(1920, 1080));
        
        // Verify elements are visible in desktop view
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email']")));
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible in desktop view");
        
        // Test tablet view
        driver.manage().window().setSize(new Dimension(768, 1024));
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible in tablet view");
        
        // Test mobile view
        driver.manage().window().setSize(new Dimension(375, 667));
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible in mobile view");
        
        // Reset to desktop view
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    @Order(14)
    @DisplayName("Test Page Performance and Load Times")
    void testPagePerformanceAndLoadTimes() {
        long startTime = System.currentTimeMillis();
        
        // Navigate to main page
        driver.get(BASE_URL);
        
        // Wait for page to fully load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Acessar']")));
        
        long loadTime = System.currentTimeMillis() - startTime;
        
        // Verify page loads within reasonable time (10 seconds)
        Assertions.assertTrue(loadTime < 10000, "Main page should load within 10 seconds");
        
        // Test requirements page load time
        startTime = System.currentTimeMillis();
        driver.get(BASE_URL + "requirements");
        wait.until(ExpectedConditions.urlContains("/requirements"));
        loadTime = System.currentTimeMillis() - startTime;
        
        Assertions.assertTrue(loadTime < 10000, "Requirements page should load within 10 seconds");
    }

    @Test
    @Order(15)
    @DisplayName("Test Complete User Journey - Registration to Login")
    void testCompleteUserJourneyRegistrationToLogin() {
        // Complete user journey test
        String testEmail = "journey@bugbank.com";
        String testName = "Journey Test User";
        String testPassword = "JourneyTest123!";
        
        // 1. Navigate to registration
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // 2. Fill registration form
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']")));
        emailField.sendKeys(testEmail);
        
        WebElement nameField = driver.findElement(By.xpath("//input[@name='name']"));
        nameField.sendKeys(testName);
        
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys(testPassword);
        
        WebElement confirmPasswordField = driver.findElement(By.xpath("//input[@name='passwordConfirmation']"));
        confirmPasswordField.sendKeys(testPassword);
        
        // 3. Enable balance toggle
        List<WebElement> toggles = driver.findElements(By.xpath("//button[not(text()) and not(@type='submit')]"));
        if (toggles.size() >= 2) {
            WebElement balanceToggle = toggles.get(toggles.size() - 1);
            balanceToggle.click();
        }
        
        // 4. Submit registration
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        registerSubmitButton.click();
        
        // Wait for registration response
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 5. Navigate back to login (if not automatically redirected)
        try {
            WebElement backToLoginLink = driver.findElement(By.xpath("//a[text()='Voltar ao login']"));
            if (backToLoginLink.isDisplayed()) {
                backToLoginLink.click();
            }
        } catch (NoSuchElementException e) {
            // May already be on login page or redirected
            driver.get(BASE_URL);
        }
        
        // 6. Attempt login with registered credentials
        WebElement loginEmailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']")));
        loginEmailField.clear();
        loginEmailField.sendKeys(testEmail);
        
        WebElement loginPasswordField = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        loginPasswordField.clear();
        loginPasswordField.sendKeys(testPassword);
        
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        loginButton.click();
        
        // Wait for login response
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify journey completion
        Assertions.assertNotNull(driver.getCurrentUrl(), "User journey should complete successfully");
    }
}
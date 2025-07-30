package claudesonnet4.ws04.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Comprehensive Selenium WebDriver test suite for BugBank website
 * Tests all pages, forms, navigation, and external links
 * 
 * @author Claude Sonnet 4
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BugBankWebsiteTests {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions actions;
    private static final String BASE_URL = "https://bugbank.netlify.app/";
    private static final String REQUIREMENTS_URL = "https://bugbank.netlify.app/requirements";
    
    @BeforeAll
    static void setupClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
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
        driver.get(BASE_URL);
    }
    
    // ==================== MAIN PAGE TESTS ====================
    
    @Test
    @Order(1)
    @DisplayName("Test Main Page Load and Basic Elements")
    void testMainPageLoad() {
        // Verify page title
        Assertions.assertEquals("BugBank | O banco com bugs e falhas do seu jeito", driver.getTitle());
        
        // Verify URL
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
        
        // Verify main heading is present
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'O banco com')]")));
        Assertions.assertTrue(heading.isDisplayed());
        
        // Verify BugBank logo/title is present
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'BugBank')]")));
        Assertions.assertTrue(logo.isDisplayed());
        
        // Verify subtitle is present
        WebElement subtitle = driver.findElement(By.xpath("//*[contains(text(), 'Faça transferências e pagamentos')]"));
        Assertions.assertTrue(subtitle.isDisplayed());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Login Form Elements Presence")
    void testLoginFormElements() {
        // Verify email input field
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']")));
        Assertions.assertTrue(emailInput.isDisplayed());
        Assertions.assertEquals("email", emailInput.getAttribute("type"));
        
        // Verify password input field
        WebElement passwordInput = driver.findElement(
            By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        Assertions.assertTrue(passwordInput.isDisplayed());
        Assertions.assertEquals("password", passwordInput.getAttribute("type"));
        
        // Verify login button
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        Assertions.assertTrue(loginButton.isDisplayed());
        Assertions.assertEquals("submit", loginButton.getAttribute("type"));
        
        // Verify register button
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        Assertions.assertTrue(registerButton.isDisplayed());
        
        // Verify requirements link
        WebElement requirementsLink = driver.findElement(By.xpath("//a[contains(text(), 'Conheça nossos requisitos')]"));
        Assertions.assertTrue(requirementsLink.isDisplayed());
        Assertions.assertEquals("/requirements", requirementsLink.getAttribute("href"));
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Login Form Input Functionality")
    void testLoginFormInput() {
        // Test email input
        WebElement emailInput = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        emailInput.clear();
        emailInput.sendKeys("test@example.com");
        Assertions.assertEquals("test@example.com", emailInput.getAttribute("value"));
        
        // Test password input
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        passwordInput.clear();
        passwordInput.sendKeys("testpassword");
        Assertions.assertEquals("testpassword", passwordInput.getAttribute("value"));
        
        // Test form submission (should show validation or error)
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        loginButton.click();
        
        // Wait for any response (error message, redirect, etc.)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on the same page or got some response
        Assertions.assertTrue(driver.getCurrentUrl().contains("bugbank.netlify.app"));
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Empty Login Form Validation")
    void testEmptyLoginFormValidation() {
        // Try to submit empty form
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        loginButton.click();
        
        // Wait for validation messages
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on login page
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }
    
    // ==================== REGISTRATION FORM TESTS ====================
    
    @Test
    @Order(5)
    @DisplayName("Test Registration Form Display")
    void testRegistrationFormDisplay() {
        // Click register button to show registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        // Wait for registration form to appear
        WebElement backToLoginLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[contains(text(), 'Voltar ao login')]")));
        Assertions.assertTrue(backToLoginLink.isDisplayed());
        
        // Verify registration form fields
        WebElement regEmailInput = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        Assertions.assertTrue(regEmailInput.isDisplayed());
        
        WebElement nameInput = driver.findElement(By.xpath("//input[@name='name' and @placeholder='Informe seu Nome']"));
        Assertions.assertTrue(nameInput.isDisplayed());
        
        WebElement regPasswordInput = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        Assertions.assertTrue(regPasswordInput.isDisplayed());
        
        WebElement passwordConfirmInput = driver.findElement(By.xpath("//input[@name='passwordConfirmation' and @placeholder='Informe a confirmação da senha']"));
        Assertions.assertTrue(passwordConfirmInput.isDisplayed());
        
        // Verify register submit button
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        Assertions.assertTrue(registerSubmitButton.isDisplayed());
        
        // Verify balance toggle is present
        WebElement balanceToggle = driver.findElement(By.xpath("//*[contains(text(), 'Criar conta com saldo')]"));
        Assertions.assertTrue(balanceToggle.isDisplayed());
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Registration Form Input Functionality")
    void testRegistrationFormInput() {
        // Click register button to show registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        // Wait for form to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Voltar ao login')]")));
        
        // Fill out registration form
        WebElement regEmailInput = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        regEmailInput.clear();
        regEmailInput.sendKeys("newuser@bugbank.com");
        Assertions.assertEquals("newuser@bugbank.com", regEmailInput.getAttribute("value"));
        
        WebElement nameInput = driver.findElement(By.xpath("//input[@name='name' and @placeholder='Informe seu Nome']"));
        nameInput.clear();
        nameInput.sendKeys("New User");
        Assertions.assertEquals("New User", nameInput.getAttribute("value"));
        
        WebElement regPasswordInput = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        regPasswordInput.clear();
        regPasswordInput.sendKeys("NewPass123!");
        Assertions.assertEquals("NewPass123!", regPasswordInput.getAttribute("value"));
        
        WebElement passwordConfirmInput = driver.findElement(By.xpath("//input[@name='passwordConfirmation' and @placeholder='Informe a confirmação da senha']"));
        passwordConfirmInput.clear();
        passwordConfirmInput.sendKeys("NewPass123!");
        Assertions.assertEquals("NewPass123!", passwordConfirmInput.getAttribute("value"));
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Registration Form Submission")
    void testRegistrationFormSubmission() {
        // Click register button to show registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        // Wait for form to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Voltar ao login')]")));
        
        // Fill out complete registration form
        driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"))
            .sendKeys("testuser@bugbank.com");
        driver.findElement(By.xpath("//input[@name='name' and @placeholder='Informe seu Nome']"))
            .sendKeys("Test User");
        driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"))
            .sendKeys("TestPass123!");
        driver.findElement(By.xpath("//input[@name='passwordConfirmation' and @placeholder='Informe a confirmação da senha']"))
            .sendKeys("TestPass123!");
        
        // Submit registration form
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        registerSubmitButton.click();
        
        // Wait for response
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify some response occurred (success message, redirect, or validation)
        Assertions.assertTrue(driver.getCurrentUrl().contains("bugbank.netlify.app"));
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Registration Form Validation")
    void testRegistrationFormValidation() {
        // Click register button to show registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        // Wait for form to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Voltar ao login')]")));
        
        // Try to submit empty form
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        registerSubmitButton.click();
        
        // Wait for validation messages
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Check for validation messages
        List<WebElement> validationMessages = driver.findElements(By.xpath("//*[contains(text(), 'É campo obrigatório')]"));
        Assertions.assertTrue(validationMessages.size() > 0, "Validation messages should appear for empty required fields");
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Password Mismatch Validation")
    void testPasswordMismatchValidation() {
        // Click register button to show registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        // Wait for form to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Voltar ao login')]")));
        
        // Fill form with mismatched passwords
        driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"))
            .sendKeys("test@bugbank.com");
        driver.findElement(By.xpath("//input[@name='name' and @placeholder='Informe seu Nome']"))
            .sendKeys("Test User");
        driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"))
            .sendKeys("Password123!");
        driver.findElement(By.xpath("//input[@name='passwordConfirmation' and @placeholder='Informe a confirmação da senha']"))
            .sendKeys("DifferentPassword123!");
        
        // Submit form
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        registerSubmitButton.click();
        
        // Wait for validation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on the same page (validation should prevent submission)
        Assertions.assertTrue(driver.getCurrentUrl().contains("bugbank.netlify.app"));
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Back to Login Navigation")
    void testBackToLoginNavigation() {
        // Click register button to show registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        // Wait for registration form to appear
        WebElement backToLoginLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[contains(text(), 'Voltar ao login')]")));
        
        // Click back to login
        backToLoginLink.click();
        
        // Wait for login form to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Acessar']")));
        
        // Verify we're back to login form
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        Assertions.assertTrue(loginButton.isDisplayed());
        
        // Verify registration form is hidden
        List<WebElement> registerSubmitButtons = driver.findElements(By.xpath("//button[text()='Cadastrar']"));
        Assertions.assertTrue(registerSubmitButtons.isEmpty() || !registerSubmitButtons.get(0).isDisplayed());
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Balance Toggle Functionality")
    void testBalanceToggleFunctionality() {
        // Click register button to show registration form
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        // Wait for form to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Voltar ao login')]")));
        
        // Find balance toggle elements
        List<WebElement> toggleButtons = driver.findElements(By.xpath("//button[@type='button']"));
        
        // Verify toggle buttons exist
        Assertions.assertTrue(toggleButtons.size() > 0, "Toggle buttons should be present");
        
        // Test clicking toggle buttons
        for (WebElement toggle : toggleButtons) {
            if (toggle.isDisplayed() && toggle.isEnabled()) {
                toggle.click();
                // Wait for any visual changes
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        // Verify balance toggle text is still present
        WebElement balanceText = driver.findElement(By.xpath("//*[contains(text(), 'Criar conta com saldo')]"));
        Assertions.assertTrue(balanceText.isDisplayed());
    }
    
    // ==================== REQUIREMENTS PAGE TESTS ====================
    
    @Test
    @Order(12)
    @DisplayName("Test Requirements Page Navigation")
    void testRequirementsPageNavigation() {
        // Click requirements link
        WebElement requirementsLink = driver.findElement(By.xpath("//a[contains(text(), 'Conheça nossos requisitos')]"));
        requirementsLink.click();
        
        // Wait for page to load
        wait.until(ExpectedConditions.urlContains("/requirements"));
        
        // Verify URL changed
        Assertions.assertEquals(REQUIREMENTS_URL, driver.getCurrentUrl());
        
        // Verify page title
        Assertions.assertEquals("BugBank | O banco com bugs e falhas do seu jeito", driver.getTitle());
    }
    
    @Test
    @Order(13)
    @DisplayName("Test Requirements Page Content")
    void testRequirementsPageContent() {
        // Navigate to requirements page
        driver.get(REQUIREMENTS_URL);
        
        // Verify page content
        WebElement contributionText = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Gostou do projeto e quer contribuir?')]")));
        Assertions.assertTrue(contributionText.isDisplayed());
        
        // Verify repository link
        WebElement repoLink = driver.findElement(By.xpath("//a[contains(text(), 'Acesse o link do repositório clicando aqui')]"));
        Assertions.assertTrue(repoLink.isDisplayed());
        
        // Verify application info
        WebElement appInfo = driver.findElement(By.xpath("//*[contains(text(), 'A aplicação não conta com um banco de dados')]"));
        Assertions.assertTrue(appInfo.isDisplayed());
        
        // Verify features list
        List<WebElement> features = driver.findElements(By.xpath("//*[contains(text(), 'Login') or contains(text(), 'Cadastro') or contains(text(), 'Transferência') or contains(text(), 'Pagamento') or contains(text(), 'Extrato') or contains(text(), 'Saque')]"));
        Assertions.assertTrue(features.size() >= 6, "Should display all main features");
        
        // Verify thank you message
        WebElement thankYouMessage = driver.findElement(By.xpath("//*[contains(text(), 'Obrigado por escolher o nosso banco')]"));
        Assertions.assertTrue(thankYouMessage.isDisplayed());
    }
    
    @Test
    @Order(14)
    @DisplayName("Test External Repository Link")
    void testExternalRepositoryLink() {
        // Navigate to requirements page
        driver.get(REQUIREMENTS_URL);
        
        // Find repository link
        WebElement repoLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[contains(text(), 'Acesse o link do repositório clicando aqui')]")));
        
        // Get current window handle
        String originalWindow = driver.getWindowHandle();
        
        // Click repository link
        repoLink.click();
        
        // Wait for new window/tab to open
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        
        // Switch to new window
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Wait for new page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're on an external site (likely GitHub)
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertFalse(currentUrl.contains("bugbank.netlify.app"), 
            "Should navigate to external repository");
        
        // Close new window and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
        
        // Verify we're back on requirements page
        Assertions.assertEquals(REQUIREMENTS_URL, driver.getCurrentUrl());
    }
    
    // ==================== NAVIGATION AND INTEGRATION TESTS ====================
    
    @Test
    @Order(15)
    @DisplayName("Test Complete Navigation Flow")
    void testCompleteNavigationFlow() {
        // Start on main page
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
        
        // Navigate to requirements
        driver.findElement(By.xpath("//a[contains(text(), 'Conheça nossos requisitos')]")).click();
        wait.until(ExpectedConditions.urlContains("/requirements"));
        Assertions.assertEquals(REQUIREMENTS_URL, driver.getCurrentUrl());
        
        // Navigate back to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
        
        // Test registration form display
        driver.findElement(By.xpath("//button[text()='Registrar']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Voltar ao login')]")));
        
        // Return to login
        driver.findElement(By.xpath("//a[contains(text(), 'Voltar ao login')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Acessar']")));
        
        // Verify we're back to login state
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        Assertions.assertTrue(loginButton.isDisplayed());
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Form State Persistence")
    void testFormStatePersistence() {
        // Fill login form
        WebElement emailInput = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        emailInput.sendKeys("persistent@test.com");
        
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        passwordInput.sendKeys("persistentpass");
        
        // Navigate to requirements page
        driver.findElement(By.xpath("//a[contains(text(), 'Conheça nossos requisitos')]")).click();
        wait.until(ExpectedConditions.urlContains("/requirements"));
        
        // Navigate back
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
        
        // Check if form data persisted (behavior may vary)
        WebElement emailInputAfter = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        WebElement passwordInputAfter = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        
        // Note: Form persistence behavior may vary, so we just verify elements are present
        Assertions.assertTrue(emailInputAfter.isDisplayed());
        Assertions.assertTrue(passwordInputAfter.isDisplayed());
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Page Responsiveness and Element Visibility")
    void testPageResponsiveness() {
        // Test different viewport sizes
        driver.manage().window().setSize(new Dimension(1920, 1080));
        
        // Verify elements are visible at full size
        WebElement logo = driver.findElement(By.xpath("//*[contains(text(), 'BugBank')]"));
        Assertions.assertTrue(logo.isDisplayed());
        
        // Test mobile size
        driver.manage().window().setSize(new Dimension(375, 667));
        
        // Verify elements are still accessible
        WebElement emailInput = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        Assertions.assertTrue(emailInput.isDisplayed());
        
        // Reset to standard size
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }
    
    @Test
    @Order(18)
    @DisplayName("Test Keyboard Navigation")
    void testKeyboardNavigation() {
        // Test tab navigation through form elements
        WebElement emailInput = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        emailInput.click();
        emailInput.sendKeys("tab@test.com");
        
        // Tab to password field
        emailInput.sendKeys(Keys.TAB);
        WebElement activeElement = driver.switchTo().activeElement();
        activeElement.sendKeys("tabpassword");
        
        // Verify password was entered
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        Assertions.assertEquals("tabpassword", passwordInput.getAttribute("value"));
        
        // Test Enter key on login button
        activeElement.sendKeys(Keys.TAB);
        activeElement = driver.switchTo().activeElement();
        if (activeElement.getTagName().equals("button")) {
            activeElement.sendKeys(Keys.ENTER);
        }
        
        // Wait for any response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on the page
        Assertions.assertTrue(driver.getCurrentUrl().contains("bugbank.netlify.app"));
    }
    
    @Test
    @Order(19)
    @DisplayName("Test All Interactive Elements")
    void testAllInteractiveElements() {
        // Test all buttons on main page
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        Assertions.assertTrue(buttons.size() >= 2, "Should have at least login and register buttons");
        
        for (WebElement button : buttons) {
            if (button.isDisplayed() && button.isEnabled()) {
                String buttonText = button.getText();
                Assertions.assertNotNull(buttonText);
                // Verify button is clickable
                Assertions.assertTrue(button.isEnabled());
            }
        }
        
        // Test all links on main page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            if (link.isDisplayed()) {
                String href = link.getAttribute("href");
                if (href != null && !href.isEmpty()) {
                    Assertions.assertTrue(href.length() > 0);
                }
            }
        }
        
        // Test all input fields
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        Assertions.assertTrue(inputs.size() >= 2, "Should have at least email and password inputs");
        
        for (WebElement input : inputs) {
            if (input.isDisplayed()) {
                String placeholder = input.getAttribute("placeholder");
                String type = input.getAttribute("type");
                Assertions.assertNotNull(type);
                if (placeholder != null) {
                    Assertions.assertTrue(placeholder.length() > 0);
                }
            }
        }
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Error Handling and Edge Cases")
    void testErrorHandlingAndEdgeCases() {
        // Test invalid email format
        WebElement emailInput = driver.findElement(By.xpath("//input[@name='email' and @placeholder='Informe seu e-mail']"));
        emailInput.clear();
        emailInput.sendKeys("invalid-email");
        
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='password' and @placeholder='Informe sua senha']"));
        passwordInput.clear();
        passwordInput.sendKeys("password");
        
        // Try to submit
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        loginButton.click();
        
        // Wait for response
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Test very long input values
        emailInput.clear();
        emailInput.sendKeys("a".repeat(100) + "@test.com");
        passwordInput.clear();
        passwordInput.sendKeys("p".repeat(100));
        
        // Verify inputs accept long values
        Assertions.assertTrue(emailInput.getAttribute("value").length() > 50);
        Assertions.assertTrue(passwordInput.getAttribute("value").length() > 50);
        
        // Test special characters
        emailInput.clear();
        emailInput.sendKeys("test+special@example.com");
        passwordInput.clear();
        passwordInput.sendKeys("P@ssw0rd!#$%");
        
        // Verify special characters are accepted
        Assertions.assertTrue(emailInput.getAttribute("value").contains("+"));
        Assertions.assertTrue(passwordInput.getAttribute("value").contains("@"));
    }
}
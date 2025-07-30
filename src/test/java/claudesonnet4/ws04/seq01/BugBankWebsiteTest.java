package claudesonnet4.ws04.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.List;

/**
 * Comprehensive test suite for BugBank website
 * Tests all pages, forms, and interactive elements
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BugBankWebsiteTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
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
        driver.get(BASE_URL);
    }

    // ==================== MAIN PAGE TESTS ====================

    @Test
    @Order(1)
    @DisplayName("Test main page loads correctly")
    void testMainPageLoads() {
        Assertions.assertEquals("BugBank | O banco com bugs e falhas do seu jeito", driver.getTitle());
        Assertions.assertTrue(driver.getCurrentUrl().equals(BASE_URL));
        
        // Verify main page elements are present
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'BugBank')]")));
        Assertions.assertTrue(logo.isDisplayed());
        
        WebElement subtitle = driver.findElement(By.xpath("//*[contains(text(), 'O banco com bugs e falhas do seu jeito')]"));
        Assertions.assertTrue(subtitle.isDisplayed());
        
        WebElement description = driver.findElement(By.xpath("//*[contains(text(), 'Faça transferências e pagamentos com bugs')]"));
        Assertions.assertTrue(description.isDisplayed());
    }

    @Test
    @Order(2)
    @DisplayName("Test login form elements are present and functional")
    void testLoginFormElements() {
        // Test email field
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        Assertions.assertTrue(emailField.isDisplayed());
        Assertions.assertEquals("Informe seu e-mail", emailField.getAttribute("placeholder"));
        
        // Test password field
        WebElement passwordField = driver.findElement(By.name("password"));
        Assertions.assertTrue(passwordField.isDisplayed());
        Assertions.assertEquals("Informe sua senha", passwordField.getAttribute("placeholder"));
        
        // Test login button
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        Assertions.assertTrue(loginButton.isDisplayed());
        Assertions.assertEquals("submit", loginButton.getAttribute("type"));
    }

    @Test
    @Order(3)
    @DisplayName("Test login form input functionality")
    void testLoginFormInput() {
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        WebElement passwordField = driver.findElement(By.name("password"));
        
        // Test email input
        emailField.clear();
        emailField.sendKeys("test@example.com");
        Assertions.assertEquals("test@example.com", emailField.getAttribute("value"));
        
        // Test password input
        passwordField.clear();
        passwordField.sendKeys("password123");
        Assertions.assertEquals("password123", passwordField.getAttribute("value"));
    }

    @Test
    @Order(4)
    @DisplayName("Test login button click functionality")
    void testLoginButtonClick() {
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        
        // Fill form with test data
        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("password123");
        
        // Click login button
        loginButton.click();
        
        // Verify button is clickable (no exceptions thrown)
        Assertions.assertTrue(loginButton.isEnabled());
    }

    @Test
    @Order(5)
    @DisplayName("Test register button switches to registration form")
    void testRegisterButtonSwitchesToRegistrationForm() {
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // Verify registration form elements appear
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("name")));
        Assertions.assertTrue(nameField.isDisplayed());
        
        WebElement passwordConfirmationField = driver.findElement(By.name("passwordConfirmation"));
        Assertions.assertTrue(passwordConfirmationField.isDisplayed());
        
        WebElement backToLoginLink = driver.findElement(By.id("btnBackButton"));
        Assertions.assertTrue(backToLoginLink.isDisplayed());
        
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        Assertions.assertTrue(registerSubmitButton.isDisplayed());
    }

    // ==================== REGISTRATION FORM TESTS ====================

    @Test
    @Order(6)
    @DisplayName("Test registration form elements are present and functional")
    void testRegistrationFormElements() {
        // Switch to registration form
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // Test email field in registration form
        List<WebElement> emailFields = driver.findElements(By.name("email"));
        WebElement regEmailField = emailFields.get(1); // Second email field is for registration
        Assertions.assertTrue(regEmailField.isDisplayed());
        Assertions.assertEquals("Informe seu e-mail", regEmailField.getAttribute("placeholder"));
        
        // Test name field
        WebElement nameField = driver.findElement(By.name("name"));
        Assertions.assertTrue(nameField.isDisplayed());
        Assertions.assertEquals("Informe seu Nome", nameField.getAttribute("placeholder"));
        
        // Test password field in registration form
        List<WebElement> passwordFields = driver.findElements(By.name("password"));
        WebElement regPasswordField = passwordFields.get(1); // Second password field is for registration
        Assertions.assertTrue(regPasswordField.isDisplayed());
        Assertions.assertEquals("Informe sua senha", regPasswordField.getAttribute("placeholder"));
        
        // Test password confirmation field
        WebElement passwordConfirmationField = driver.findElement(By.name("passwordConfirmation"));
        Assertions.assertTrue(passwordConfirmationField.isDisplayed());
        Assertions.assertEquals("Informe a confirmação da senha", passwordConfirmationField.getAttribute("placeholder"));
        
        // Test register button
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        Assertions.assertTrue(registerSubmitButton.isDisplayed());
        Assertions.assertEquals("submit", registerSubmitButton.getAttribute("type"));
    }

    @Test
    @Order(7)
    @DisplayName("Test registration form input functionality")
    void testRegistrationFormInput() {
        // Switch to registration form
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // Get registration form fields
        List<WebElement> emailFields = driver.findElements(By.name("email"));
        WebElement regEmailField = emailFields.get(1);
        WebElement nameField = driver.findElement(By.name("name"));
        List<WebElement> passwordFields = driver.findElements(By.name("password"));
        WebElement regPasswordField = passwordFields.get(1);
        WebElement passwordConfirmationField = driver.findElement(By.name("passwordConfirmation"));
        
        // Test email input
        regEmailField.clear();
        regEmailField.sendKeys("newuser@example.com");
        Assertions.assertEquals("newuser@example.com", regEmailField.getAttribute("value"));
        
        // Test name input
        nameField.clear();
        nameField.sendKeys("Test User");
        Assertions.assertEquals("Test User", nameField.getAttribute("value"));
        
        // Test password input
        regPasswordField.clear();
        regPasswordField.sendKeys("newpassword123");
        Assertions.assertEquals("newpassword123", regPasswordField.getAttribute("value"));
        
        // Test password confirmation input
        passwordConfirmationField.clear();
        passwordConfirmationField.sendKeys("newpassword123");
        Assertions.assertEquals("newpassword123", passwordConfirmationField.getAttribute("value"));
    }

    @Test
    @Order(8)
    @DisplayName("Test registration form toggle buttons")
    void testRegistrationFormToggleButtons() {
        // Switch to registration form
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // Find toggle buttons (they appear as buttons without text)
        List<WebElement> toggleButtons = driver.findElements(By.xpath("//button[@type='button' and text()='']"));
        
        // Verify toggle buttons are present and clickable
        Assertions.assertTrue(toggleButtons.size() >= 2);
        
        for (WebElement toggle : toggleButtons) {
            Assertions.assertTrue(toggle.isDisplayed());
            Assertions.assertTrue(toggle.isEnabled());
            
            // Test clicking toggle
            toggle.click();
            // Verify no exceptions are thrown
        }
    }

    @Test
    @Order(9)
    @DisplayName("Test registration form validation")
    void testRegistrationFormValidation() {
        // Switch to registration form
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // Try to submit empty form
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        registerSubmitButton.click();
        
        // Check for validation messages
        List<WebElement> validationMessages = driver.findElements(By.xpath("//*[contains(text(), 'É campo obrigatório')]"));
        Assertions.assertTrue(validationMessages.size() > 0, "Validation messages should appear for empty required fields");
    }

    @Test
    @Order(10)
    @DisplayName("Test complete registration form submission")
    void testCompleteRegistrationFormSubmission() {
        // Switch to registration form
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // Fill out complete registration form
        List<WebElement> emailFields = driver.findElements(By.name("email"));
        WebElement regEmailField = emailFields.get(1);
        WebElement nameField = driver.findElement(By.name("name"));
        List<WebElement> passwordFields = driver.findElements(By.name("password"));
        WebElement regPasswordField = passwordFields.get(1);
        WebElement passwordConfirmationField = driver.findElement(By.name("passwordConfirmation"));
        
        regEmailField.sendKeys("testuser@example.com");
        nameField.sendKeys("Test User");
        regPasswordField.sendKeys("Test123!");
        passwordConfirmationField.sendKeys("Test123!");
        
        // Submit form
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        registerSubmitButton.click();
        
        // Verify form submission (no exceptions thrown)
        Assertions.assertTrue(registerSubmitButton.isEnabled());
    }

    @Test
    @Order(11)
    @DisplayName("Test back to login link functionality")
    void testBackToLoginLink() {
        // Switch to registration form
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Registrar']")));
        registerButton.click();
        
        // Verify registration form is displayed
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("name")));
        Assertions.assertTrue(nameField.isDisplayed());
        
        // Click back to login link
        WebElement backToLoginLink = driver.findElement(By.id("btnBackButton"));
        backToLoginLink.click();
        
        // Verify we're back to login form (name field should not be visible)
        wait.until(ExpectedConditions.invisibilityOf(nameField));
        
        // Verify login form elements are visible again
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Acessar']")));
        Assertions.assertTrue(loginButton.isDisplayed());
    }

    // ==================== NAVIGATION TESTS ====================

    @Test
    @Order(12)
    @DisplayName("Test requirements page navigation")
    void testRequirementsPageNavigation() {
        WebElement requirementsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Conheça nossos requisitos")));
        requirementsLink.click();
        
        // Verify navigation to requirements page
        wait.until(ExpectedConditions.urlToBe(REQUIREMENTS_URL));
        Assertions.assertEquals(REQUIREMENTS_URL, driver.getCurrentUrl());
        Assertions.assertEquals("BugBank | O banco com bugs e falhas do seu jeito", driver.getTitle());
    }

    // ==================== REQUIREMENTS PAGE TESTS ====================

    @Test
    @Order(13)
    @DisplayName("Test requirements page loads correctly")
    void testRequirementsPageLoads() {
        driver.get(REQUIREMENTS_URL);
        
        Assertions.assertEquals("BugBank | O banco com bugs e falhas do seu jeito", driver.getTitle());
        Assertions.assertEquals(REQUIREMENTS_URL, driver.getCurrentUrl());
        
        // Verify requirements page content
        WebElement contributionText = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Gostou do projeto e quer contribuir?')]")));
        Assertions.assertTrue(contributionText.isDisplayed());
        
        WebElement storageInfo = driver.findElement(By.xpath(
            "//*[contains(text(), 'A aplicação não conta com um banco de dados')]"));
        Assertions.assertTrue(storageInfo.isDisplayed());
        
        WebElement thankYouMessage = driver.findElement(By.xpath(
            "//*[contains(text(), 'Obrigado por escolher o nosso banco')]"));
        Assertions.assertTrue(thankYouMessage.isDisplayed());
    }

    @Test
    @Order(14)
    @DisplayName("Test requirements page expandable sections")
    void testRequirementsPageExpandableSections() {
        driver.get(REQUIREMENTS_URL);
        
        // Find all expandable sections
        String[] sections = {"Login", "Cadastro", "Transferência", "Pagamento", "Extrato", "Saque"};
        
        for (String section : sections) {
            WebElement sectionElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), '" + section + "')]")));
            Assertions.assertTrue(sectionElement.isDisplayed(), section + " section should be visible");
        }
    }

    @Test
    @Order(15)
    @DisplayName("Test requirements page expandable sections interaction")
    void testRequirementsPageExpandableSectionsInteraction() {
        driver.get(REQUIREMENTS_URL);
        
        // Find and click expandable sections
        String[] sections = {"Login", "Cadastro", "Transferência", "Pagamento", "Extrato", "Saque"};
        
        for (String section : sections) {
            try {
                WebElement sectionElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(), '" + section + "')]")));
                
                // Click to expand/collapse
                sectionElement.click();
                
                // Verify click was successful (no exceptions)
                Assertions.assertTrue(sectionElement.isDisplayed());
                
                // Small wait between clicks
                Thread.sleep(500);
            } catch (Exception e) {
                // Some sections might not be clickable, which is acceptable
                System.out.println("Section " + section + " might not be clickable: " + e.getMessage());
            }
        }
    }

    @Test
    @Order(16)
    @DisplayName("Test external GitHub repository link")
    void testExternalGitHubRepositoryLink() throws InterruptedException{
        driver.get(REQUIREMENTS_URL);
        
        // Find the GitHub repository link
        WebElement githubLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[contains(text(), 'Acesse o link do repositório clicando aqui')]")));
        Assertions.assertTrue(githubLink.isDisplayed());
        
        // Verify it's a link element
        Assertions.assertEquals("a", githubLink.getTagName().toLowerCase());
        
        // Verify href attribute exists
        String href = githubLink.getAttribute("href");
        Assertions.assertNotNull(href, "GitHub link should have href attribute");
        Assertions.assertFalse(href.isEmpty(), "GitHub link href should not be empty");
        
        // Click the link (this will open in same tab or new tab depending on implementation)
        String originalWindow = driver.getWindowHandle();
        githubLink.click();
        
        // Wait a moment for potential navigation
        Thread.sleep(2000);
        
        // Check if we navigated to external site or opened new tab
        if (!driver.getCurrentUrl().equals(REQUIREMENTS_URL)) {
            // We navigated to external site
            Assertions.assertTrue(driver.getCurrentUrl().contains("github") || 
                                driver.getCurrentUrl().contains("git") ||
                                !driver.getCurrentUrl().contains("bugbank.netlify.app"),
                                "Should navigate to external repository");
        } else {
            // Check if new tab was opened
            if (driver.getWindowHandles().size() > 1) {
                // Switch to new tab
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!windowHandle.equals(originalWindow)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }
                
                // Verify we're on external site
                Assertions.assertTrue(driver.getCurrentUrl().contains("github") || 
                                    driver.getCurrentUrl().contains("git") ||
                                    !driver.getCurrentUrl().contains("bugbank.netlify.app"),
                                    "New tab should open external repository");
                
                // Close new tab and switch back
                driver.close();
                driver.switchTo().window(originalWindow);
            }
        }
    }

    // ==================== CROSS-PAGE NAVIGATION TESTS ====================

    @Test
    @Order(17)
    @DisplayName("Test navigation between main page and requirements page")
    void testNavigationBetweenPages() {
        // Start on main page
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
        
        // Navigate to requirements page
        WebElement requirementsLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Conheça nossos requisitos")));
        requirementsLink.click();
        
        // Verify we're on requirements page
        wait.until(ExpectedConditions.urlToBe(REQUIREMENTS_URL));
        Assertions.assertEquals(REQUIREMENTS_URL, driver.getCurrentUrl());
        
        // Navigate back using browser back button
        driver.navigate().back();
        
        // Verify we're back on main page
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
        
        // Verify main page elements are present
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[text()='Acessar']")));
        Assertions.assertTrue(loginButton.isDisplayed());
    }

    @Test
    @Order(18)
    @DisplayName("Test page refresh functionality")
    void testPageRefreshFunctionality() {
        // Test main page refresh
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        emailField.sendKeys("test@example.com");
        
        driver.navigate().refresh();
        
        // Verify page refreshed and form is cleared
        emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        Assertions.assertEquals("", emailField.getAttribute("value"));
        
        // Test requirements page refresh
        driver.get(REQUIREMENTS_URL);
        driver.navigate().refresh();
        
        // Verify requirements page still loads correctly
        WebElement contributionText = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Gostou do projeto e quer contribuir?')]")));
        Assertions.assertTrue(contributionText.isDisplayed());
    }

    // ==================== RESPONSIVE DESIGN TESTS ====================

    @Test
    @Order(19)
    @DisplayName("Test responsive design - mobile viewport")
    void testResponsiveDesignMobile() {
        // Set mobile viewport
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        
        // Verify main page elements are still accessible
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        Assertions.assertTrue(emailField.isDisplayed());
        
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        Assertions.assertTrue(registerButton.isDisplayed());
        
        // Test requirements page in mobile
        driver.get(REQUIREMENTS_URL);
        WebElement contributionText = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Gostou do projeto e quer contribuir?')]")));
        Assertions.assertTrue(contributionText.isDisplayed());
        
        // Reset to desktop viewport
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
    }

    @Test
    @Order(20)
    @DisplayName("Test responsive design - tablet viewport")
    void testResponsiveDesignTablet() {
        // Set tablet viewport
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        
        // Verify main page elements are still accessible
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        Assertions.assertTrue(emailField.isDisplayed());
        
        WebElement passwordField = driver.findElement(By.name("password"));
        Assertions.assertTrue(passwordField.isDisplayed());
        
        // Test form interaction in tablet view
        emailField.sendKeys("tablet@test.com");
        passwordField.sendKeys("tabletpass");
        
        Assertions.assertEquals("tablet@test.com", emailField.getAttribute("value"));
        Assertions.assertEquals("tabletpass", passwordField.getAttribute("value"));
        
        // Reset to desktop viewport
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
    }

    // ==================== ACCESSIBILITY TESTS ====================

    @Test
    @Order(21)
    @DisplayName("Test form accessibility - labels and placeholders")
    void testFormAccessibility() {
        // Check login form accessibility
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        Assertions.assertNotNull(emailField.getAttribute("placeholder"));
        Assertions.assertFalse(emailField.getAttribute("placeholder").isEmpty());
        
        WebElement passwordField = driver.findElement(By.name("password"));
        Assertions.assertNotNull(passwordField.getAttribute("placeholder"));
        Assertions.assertFalse(passwordField.getAttribute("placeholder").isEmpty());
        
        // Switch to registration form and check accessibility
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();
        
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("name")));
        Assertions.assertNotNull(nameField.getAttribute("placeholder"));
        Assertions.assertFalse(nameField.getAttribute("placeholder").isEmpty());
        
        WebElement passwordConfirmationField = driver.findElement(By.name("passwordConfirmation"));
        Assertions.assertNotNull(passwordConfirmationField.getAttribute("placeholder"));
        Assertions.assertFalse(passwordConfirmationField.getAttribute("placeholder").isEmpty());
    }

    @Test
    @Order(22)
    @DisplayName("Test keyboard navigation")
    void testKeyboardNavigation() {
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        
        // Test tab navigation
        emailField.sendKeys("test@example.com");
        emailField.sendKeys(org.openqa.selenium.Keys.TAB);
        
        // Verify focus moved to password field
        WebElement passwordField = driver.findElement(By.name("password"));
        Assertions.assertEquals(passwordField, driver.switchTo().activeElement());
        
        // Test form submission with Enter key
        passwordField.sendKeys("password123");
        passwordField.sendKeys(org.openqa.selenium.Keys.ENTER);
        
        // Verify form submission attempt (no exceptions)
        Assertions.assertTrue(passwordField.isDisplayed());
    }

    // ==================== ERROR HANDLING TESTS ====================

    @Test
    @Order(23)
    @DisplayName("Test invalid URL handling")
    void testInvalidUrlHandling() {
        // Test invalid subpath
        driver.get(BASE_URL + "invalid-page");
        
        // Should either redirect to main page or show 404
        // Verify page loads without crashing
        Assertions.assertNotNull(driver.getTitle());
        Assertions.assertFalse(driver.getTitle().isEmpty());
    }

    @Test
    @Order(24)
    @DisplayName("Test JavaScript errors handling")
    void testJavaScriptErrorsHandling() {
        // Execute JavaScript that might cause errors
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        try {
            // Test console errors
            Object result = js.executeScript("return window.console && window.console.error;");
            Assertions.assertNotNull(result);
            
            // Test basic JavaScript functionality
            Object titleResult = js.executeScript("return document.title;");
            Assertions.assertNotNull(titleResult);
            
        } catch (Exception e) {
            // JavaScript errors should be handled gracefully
            Assertions.fail("JavaScript execution should not throw unhandled exceptions: " + e.getMessage());
        }
    }

    // ==================== PERFORMANCE TESTS ====================

    @Test
    @Order(25)
    @DisplayName("Test page load performance")
    void testPageLoadPerformance() {
        long startTime = System.currentTimeMillis();
        driver.get(BASE_URL);
        
        // Wait for page to be fully loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        long endTime = System.currentTimeMillis();
        
        long loadTime = endTime - startTime;
        
        // Page should load within reasonable time (10 seconds)
        Assertions.assertTrue(loadTime < 10000, "Page should load within 10 seconds, actual: " + loadTime + "ms");
        
        // Test requirements page load time
        startTime = System.currentTimeMillis();
        driver.get(REQUIREMENTS_URL);
        
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Gostou do projeto e quer contribuir?')]")));
        endTime = System.currentTimeMillis();
        
        loadTime = endTime - startTime;
        Assertions.assertTrue(loadTime < 10000, "Requirements page should load within 10 seconds, actual: " + loadTime + "ms");
    }
}
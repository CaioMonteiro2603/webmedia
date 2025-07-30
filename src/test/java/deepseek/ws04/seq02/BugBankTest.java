package deepseek.ws04.seq02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BugBankTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://bugbank.netlify.app/";

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    public void beforeEach() {
        driver.get(BASE_URL);
    }

    // Helper methods
    private void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Test cases
    @Test
    public void testLoginPageElements() {
        // Verify all elements on login page are present
        Assertions.assertTrue(isElementPresent(By.cssSelector("input[type='email']")), "Email field should be present");
        Assertions.assertTrue(isElementPresent(By.cssSelector("input[type='password']")), "Password field should be present");
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Acessar')]")), "Login button should be present");
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Registrar')]")), "Register button should be present");
        Assertions.assertTrue(isElementPresent(By.linkText("Conheça nossos requisitos")), "Requirements link should be present");
    }

    @Test
    public void testPasswordVisibilityToggle() {
        // Test password visibility toggle
        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        WebElement toggleButton = driver.findElement(By.cssSelector("input[type='password'] + button"));
        
        // Password should be hidden by default
        Assertions.assertEquals("password", passwordField.getAttribute("type"), "Password should be hidden initially");
        
        // Click toggle button
        toggleButton.click();
        Assertions.assertEquals("text", passwordField.getAttribute("type"), "Password should be visible after toggle");
        
        // Click again to hide
        toggleButton.click();
        Assertions.assertEquals("password", passwordField.getAttribute("type"), "Password should be hidden again");
    }

    @Test
    public void testNavigationToRegistration() {
        // Test navigation to registration form
        click(By.xpath("//button[contains(text(),'Registrar')]"));
        
        // Verify registration form elements
        Assertions.assertTrue(isElementPresent(By.cssSelector("input[name='name']")), "Name field should be present in registration");
        Assertions.assertTrue(isElementPresent(By.cssSelector("input[name='passwordConfirmation']")), "Password confirmation field should be present");
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Cadastrar')]")), "Register button should be present");
        Assertions.assertTrue(isElementPresent(By.linkText("Voltar ao login")), "Back to login link should be present");
    }

    @Test
    public void testRegistrationFormValidation() {
        // Navigate to registration form
        click(By.xpath("//button[contains(text(),'Registrar')]"));
        
        // Try to submit empty form
        click(By.xpath("//button[contains(text(),'Cadastrar')]"));
        
        // Verify validation messages
        Assertions.assertTrue(isElementPresent(By.xpath("//*[contains(text(),'É campo obrigatório')]")), 
            "Validation message should appear for required fields");
    }

    @Test
    public void testSuccessfulRegistration() {
        // Navigate to registration form
        click(By.xpath("//button[contains(text(),'Registrar')]"));
        
        // Fill registration form
        type(By.cssSelector("input[name='email']"), "test" + System.currentTimeMillis() + "@example.com");
        type(By.cssSelector("input[name='name']"), "Test User");
        type(By.cssSelector("input[name='password']"), "Test@123");
        type(By.cssSelector("input[name='passwordConfirmation']"), "Test@123");
        
        // Submit form
        click(By.xpath("//button[contains(text(),'Cadastrar')]"));
        
        // Verify successful registration
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'foi criada com sucesso')]")));
        Assertions.assertTrue(welcomeMessage.isDisplayed(), "Registration success message should be displayed");
    }

    @Test
    public void testPasswordMismatchValidation() {
        // Navigate to registration form
        click(By.xpath("//button[contains(text(),'Registrar')]"));
        
        // Fill form with mismatched passwords
        type(By.cssSelector("input[name='password']"), "Test@123");
        type(By.cssSelector("input[name='passwordConfirmation']"), "Different@123");
        
        // Submit form
        click(By.xpath("//button[contains(text(),'Cadastrar')]"));
        
        // Verify validation message
        Assertions.assertTrue(isElementPresent(By.xpath("//*[contains(text(),'As senhas não coincidem')]")), 
            "Password mismatch validation should appear");
    }

    @Test
    public void testNavigationToRequirementsPage() {
        // Click requirements link
        click(By.linkText("Conheça nossos requisitos"));
        
        // Verify navigation
        wait.until(ExpectedConditions.urlContains("/requirements"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/requirements"), "Should navigate to requirements page");
        
        // Verify back button
        Assertions.assertTrue(isElementPresent(By.id("btnBackButton")), "Back button should be present");
    }

    @Test
    public void testBackNavigationFromRequirements() {
        // Go to requirements page
        click(By.linkText("Conheça nossos requisitos"));
        wait.until(ExpectedConditions.urlContains("/requirements"));
        
        // Click back button
        click(By.id("btnBackButton"));
        
        // Verify back to login page
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl(), "Should return to login page");
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Attempt login with invalid credentials
        type(By.cssSelector("input[type='email']"), "invalid@example.com");
        type(By.cssSelector("input[type='password']"), "wrongpassword");
        click(By.xpath("//button[contains(text(),'Acessar')]"));
        
        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'Usuário ou senha inválido')]")));
        Assertions.assertTrue(errorMessage.isDisplayed(), "Error message should appear for invalid login");
    }

    @Test
    public void testLoginWithValidCredentials() {
        // First register a test user
        click(By.xpath("//button[contains(text(),'Registrar')]"));
        String email = "test" + System.currentTimeMillis() + "@example.com";
        type(By.cssSelector("input[name='email']"), email);
        type(By.cssSelector("input[name='name']"), "Test User");
        type(By.cssSelector("input[name='password']"), "Test@123");
        type(By.cssSelector("input[name='passwordConfirmation']"), "Test@123");
        click(By.xpath("//button[contains(text(),'Cadastrar')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'foi criada com sucesso')]")));
        
        // Now test login
        click(By.id("btnBackButton"));
        type(By.cssSelector("input[type='email']"), email);
        type(By.cssSelector("input[type='password']"), "Test@123");
        click(By.xpath("//button[contains(text(),'Acessar')]"));
        
        // Verify successful login
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'Olá')]")));
        Assertions.assertTrue(welcomeMessage.isDisplayed(), "Welcome message should appear after login");
    }
}
package deepseek.ws08.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SystemHealingTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/system-healing-test/";

    @BeforeEach
    public void setUp() {
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

    @Test
    public void testMainPageElements() {
        // Navigate to main page
        driver.get(BASE_URL);
        
        // Verify page title
        Assertions.assertEquals("Login", driver.getTitle());
        
        // Verify welcome message
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(), 'Ola! Seja bem vindo.')]")));
        Assertions.assertTrue(welcomeMessage.isDisplayed());
        
        // Verify username field
        WebElement usernameField = driver.findElement(By.id("username-2"));
        Assertions.assertTrue(usernameField.isDisplayed());
        Assertions.assertEquals("username", usernameField.getAttribute("placeholder"));
        
        // Verify password field
        WebElement passwordField = driver.findElement(By.id("password-2"));
        Assertions.assertTrue(passwordField.isDisplayed());
        Assertions.assertEquals("password", passwordField.getAttribute("placeholder"));
        
        // Verify login button
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        Assertions.assertTrue(loginButton.isDisplayed());
        Assertions.assertEquals("Login", loginButton.getText());
        
        // Verify forgot password link
        WebElement forgotPasswordLink = driver.findElement(By.linkText("Esqueceu a sua senha?"));
        Assertions.assertTrue(forgotPasswordLink.isDisplayed());
        
        // Verify create account link
        WebElement createAccountLink = driver.findElement(By.linkText("Criar uma conta"));
        Assertions.assertTrue(createAccountLink.isDisplayed());
    }

    @Test
    public void testLoginFunctionality() {
        driver.get(BASE_URL);
        
        // Enter credentials
        WebElement usernameField = driver.findElement(By.id("username-2"));
        usernameField.sendKeys("testuser");
        
        WebElement passwordField = driver.findElement(By.id("password-2"));
        passwordField.sendKeys("testpass");
        
        // Click login button
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        loginButton.click();
        
        // Verify login attempt (assuming it stays on same page for demo)
        // In real scenario, would verify redirect or error message
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testForgotPasswordNavigation() {
        driver.get(BASE_URL);
        
        // Click forgot password link
        WebElement forgotPasswordLink = driver.findElement(By.linkText("Esqueceu a sua senha?"));
        forgotPasswordLink.click();
        
        // Verify navigation to password recovery page
        wait.until(ExpectedConditions.urlContains("password.html"));
        Assertions.assertTrue(driver.getCurrentUrl().endsWith("password.html"));
        
        // Verify page elements
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[type='email']")));
        Assertions.assertTrue(emailField.isDisplayed());
        
        WebElement recoverButton = driver.findElement(By.cssSelector("button[type='submit']"));
        Assertions.assertTrue(recoverButton.isDisplayed());
        
        // Test back navigation
        driver.navigate().back();
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void testCreateAccountNavigation() {
        driver.get(BASE_URL);
        
        // Click create account link
        WebElement createAccountLink = driver.findElement(By.linkText("Criar uma conta"));
        createAccountLink.click();
        
        // Verify navigation to account creation page
        wait.until(ExpectedConditions.urlContains("account.html"));
        Assertions.assertTrue(driver.getCurrentUrl().endsWith("account.html"));
        
        // Verify page elements
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("new-username")));
        Assertions.assertTrue(usernameField.isDisplayed());
        
        WebElement emailField = driver.findElement(By.id("new-email"));
        Assertions.assertTrue(emailField.isDisplayed());
        
        WebElement passwordField = driver.findElement(By.id("new-password"));
        Assertions.assertTrue(passwordField.isDisplayed());
        
        WebElement confirmPasswordField = driver.findElement(By.id("confirm-password"));
        Assertions.assertTrue(confirmPasswordField.isDisplayed());
        
        WebElement createButton = driver.findElement(By.id("btnCreate"));
        Assertions.assertTrue(createButton.isDisplayed());
        
        // Test back navigation
        driver.navigate().back();
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void testAccountCreationForm() {
        driver.get(BASE_URL + "account.html");
        
        // Fill out form
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("new-username")));
        usernameField.sendKeys("newuser");
        
        WebElement emailField = driver.findElement(By.id("new-email"));
        emailField.sendKeys("test@example.com");
        
        WebElement passwordField = driver.findElement(By.id("new-password"));
        passwordField.sendKeys("Test123!");
        
        WebElement confirmPasswordField = driver.findElement(By.id("confirm-password"));
        confirmPasswordField.sendKeys("Test123!");
        
        // Submit form
        WebElement createButton = driver.findElement(By.id("btnCreate"));
        createButton.click();
        
        // Verify form submission (assuming it stays on same page for demo)
        // In real scenario, would verify redirect or success message
        Assertions.assertTrue(driver.getCurrentUrl().endsWith("account.html"));
    }

    @Test
    public void testPasswordRecoveryForm() {
        driver.get(BASE_URL + "password.html");
        
        // Fill out form
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[type='email']")));
        emailField.sendKeys("user@example.com");
        
        // Submit form
        WebElement recoverButton = driver.findElement(By.cssSelector("button[type='submit']"));
        recoverButton.click();
        
        // Verify form submission (assuming it stays on same page for demo)
        // In real scenario, would verify success message
        Assertions.assertTrue(driver.getCurrentUrl().endsWith("password.html"));
    }
}
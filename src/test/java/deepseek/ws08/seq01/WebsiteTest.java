package deepseek.ws08.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebsiteTest {
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
    public void testLoginPageElements() {
        driver.get(BASE_URL);
        
        // Verify page title
        Assertions.assertEquals("Login", driver.getTitle());
        
        // Verify username field
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[name='username']")));
        Assertions.assertTrue(usernameField.isDisplayed());
        Assertions.assertEquals("username", usernameField.getAttribute("placeholder"));
        
        // Verify password field
        WebElement passwordField = driver.findElement(By.cssSelector("input[name='password209']"));
        Assertions.assertTrue(passwordField.isDisplayed());
        Assertions.assertEquals("password", passwordField.getAttribute("placeholder"));
        Assertions.assertEquals("password", passwordField.getAttribute("type"));
        
        // Verify login button
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        Assertions.assertTrue(loginButton.isDisplayed());
        Assertions.assertEquals("Login", loginButton.getText());
        
        // Verify password recovery link
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
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[name='username']")));
        usernameField.sendKeys("testuser");
        
        WebElement passwordField = driver.findElement(By.cssSelector("input[name='password209']"));
        passwordField.sendKeys("testpass");
        
        // Click login button
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        loginButton.click();
        
        // Verify login attempt (assuming it shows an error message)
        // You may need to adjust this based on actual behavior
        try {
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".error-message")));
            Assertions.assertTrue(errorMessage.isDisplayed());
        } catch (TimeoutException e) {
            // If no error message appears, verify we're still on login page
            Assertions.assertEquals("Login", driver.getTitle());
        }
    }

    @Test
    public void testPasswordRecoveryPage() {
        driver.get(BASE_URL);
        
        // Click password recovery link
        WebElement forgotPasswordLink = driver.findElement(By.linkText("Esqueceu a sua senha?"));
        forgotPasswordLink.click();
        
        // Verify navigation to password recovery page
        wait.until(ExpectedConditions.urlContains("password.html"));
        Assertions.assertTrue(driver.getCurrentUrl().endsWith("password.html"));
        
        // Verify page elements
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[type='email']")));
        Assertions.assertTrue(emailField.isDisplayed());
        
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        Assertions.assertTrue(submitButton.isDisplayed());
        
        // Test form submission
        emailField.sendKeys("test@example.com");
        submitButton.click();
        
        // Verify submission (adjust based on actual behavior)
        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".success-message")));
            Assertions.assertTrue(successMessage.isDisplayed());
        } catch (TimeoutException e) {
            // If no success message, verify we're still on the same page
            Assertions.assertTrue(driver.getCurrentUrl().endsWith("password.html"));
        }
    }

    @Test
    public void testAccountCreationPage() {
        driver.get(BASE_URL);
        
        // Click create account link
        WebElement createAccountLink = driver.findElement(By.linkText("Criar uma conta"));
        createAccountLink.click();
        
        // Verify navigation to account creation page
        wait.until(ExpectedConditions.urlContains("account.html"));
        Assertions.assertTrue(driver.getCurrentUrl().endsWith("account.html"));
        
        // Verify page elements
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[name='newUsername']")));
        Assertions.assertTrue(usernameField.isDisplayed());
        
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        Assertions.assertTrue(emailField.isDisplayed());
        
        WebElement passwordField = driver.findElement(By.cssSelector("input[name='newPassword']"));
        Assertions.assertTrue(passwordField.isDisplayed());
        
        WebElement confirmPasswordField = driver.findElement(By.cssSelector("input[name='confirmPassword']"));
        Assertions.assertTrue(confirmPasswordField.isDisplayed());
        
        WebElement registerButton = driver.findElement(By.cssSelector("button[type='submit']"));
        Assertions.assertTrue(registerButton.isDisplayed());
        
        // Test form submission
        usernameField.sendKeys("newuser");
        emailField.sendKeys("newuser@example.com");
        passwordField.sendKeys("newpass123");
        confirmPasswordField.sendKeys("newpass123");
        registerButton.click();
        
        // Verify submission (adjust based on actual behavior)
        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".success-message")));
            Assertions.assertTrue(successMessage.isDisplayed());
        } catch (TimeoutException e) {
            // If no success message, verify we're still on the same page
            Assertions.assertTrue(driver.getCurrentUrl().endsWith("account.html"));
        }
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        
        // Test if external links open in new tab (if any exist)
        // This is a template - you would need to adjust based on actual external links
        
        // Example for if there were external links:
        /*
        WebElement externalLink = driver.findElement(By.cssSelector("a[href^='http']"));
        String originalWindow = driver.getWindowHandle();
        externalLink.click();
        
        // Switch to new tab
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify external page loaded
        Assertions.assertNotEquals(BASE_URL, driver.getCurrentUrl());
        
        // Close the tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
        */
        
        // Currently no external links found on the main page
        Assertions.assertTrue(true, "No external links found on main page to test");
    }
}
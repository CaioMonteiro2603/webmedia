package deepseek.ws08.seq02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SystemHealingTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/system-healing-test/";

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
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
    public void testMainPageLoads() {
        driver.get(BASE_URL);
        Assertions.assertEquals("Login", driver.getTitle());
        
        // Verify welcome message is present
        WebElement welcomeMessage = driver.findElement(By.xpath("//*[contains(text(), 'Ola! Seja bem vindo.')]"));
        Assertions.assertTrue(welcomeMessage.isDisplayed());
    }

    @Test
    public void testLoginFormElements() {
        driver.get(BASE_URL);
        
        // Verify username field
        WebElement usernameField = driver.findElement(By.id("username-2"));
        Assertions.assertTrue(usernameField.isDisplayed());
        Assertions.assertEquals("username", usernameField.getAttribute("placeholder"));
        
        // Verify password field
        WebElement passwordField = driver.findElement(By.id("password-2"));
        Assertions.assertTrue(passwordField.isDisplayed());
        Assertions.assertEquals("password", passwordField.getAttribute("placeholder"));
        Assertions.assertEquals("password", passwordField.getAttribute("type"));
        
        // Verify login button
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        Assertions.assertTrue(loginButton.isDisplayed());
        Assertions.assertEquals("Login", loginButton.getText());
    }

    @Test
    public void testForgotPasswordLink() {
        driver.get(BASE_URL);
        
        // Click forgot password link
        WebElement forgotPasswordLink = driver.findElement(By.linkText("Esqueceu a sua senha?"));
        forgotPasswordLink.click();
        
        // Wait for new page to load
        wait.until(ExpectedConditions.titleContains("Password Recovery"));
        
        // Verify we're on the correct page
        Assertions.assertTrue(driver.getCurrentUrl().contains("password.html"));
        
        // Verify page content
        WebElement recoveryTitle = driver.findElement(By.xpath("//*[contains(text(), 'Recuperação de Senha')]"));
        Assertions.assertTrue(recoveryTitle.isDisplayed());
    }

    @Test
    public void testCreateAccountLink() {
        driver.get(BASE_URL);
        
        // Click create account link
        WebElement createAccountLink = driver.findElement(By.linkText("Criar uma conta"));
        createAccountLink.click();
        
        // Wait for new page to load
        wait.until(ExpectedConditions.titleContains("Create Account"));
        
        // Verify we're on the correct page
        Assertions.assertTrue(driver.getCurrentUrl().contains("account.html"));
        
        // Verify page content
        WebElement createAccountTitle = driver.findElement(By.xpath("//*[contains(text(), 'Criar Nova Conta')]"));
        Assertions.assertTrue(createAccountTitle.isDisplayed());
    }

    @Test
    public void testLoginFormSubmission() {
        driver.get(BASE_URL);
        
        // Fill in login form
        WebElement usernameField = driver.findElement(By.id("username-2"));
        usernameField.sendKeys("testuser");
        
        WebElement passwordField = driver.findElement(By.id("password-2"));
        passwordField.sendKeys("testpass123");
        
        // Submit form
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        loginButton.click();
        
        // Verify login attempt (assuming it stays on same page for this test site)
        // In a real application, we would verify successful login redirect
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        String mainWindow = driver.getWindowHandle();
        
        // Get all links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href != null && !href.startsWith(BASE_URL)) {
                // Open link in new tab
                link.click();
                
                // Switch to new tab
                Set<String> windows = driver.getWindowHandles();
                windows.remove(mainWindow);
                String newWindow = windows.iterator().next();
                driver.switchTo().window(newWindow);
                
                // Verify the page loaded
                Assertions.assertNotEquals("about:blank", driver.getCurrentUrl());
                
                // Close the tab and switch back to main window
                driver.close();
                driver.switchTo().window(mainWindow);
            }
        }
    }

    @Test
    public void testFormValidation() {
        driver.get(BASE_URL);
        
        // Try to submit empty form
        WebElement loginButton = driver.findElement(By.id("btnLogin2"));
        loginButton.click();
        
        // Verify we're still on the same page (form not submitted)
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }
}
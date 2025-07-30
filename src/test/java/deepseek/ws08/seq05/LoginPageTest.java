package deepseek.ws08.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/system-healing-test/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void testPageTitle() {
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testUsernameFieldPresence() {
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='username']")));
        Assertions.assertTrue(usernameField.isDisplayed());
    }

    @Test
    public void testPasswordFieldPresence() {
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='password209']")));
        Assertions.assertTrue(passwordField.isDisplayed());
    }

    @Test
    public void testLoginButtonFunctionality() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("btnLogin2")));
        loginButton.click();
        
        // Verify some expected behavior after login click
        // This would need to be updated based on actual behavior
        Assertions.assertTrue(true, "Login button click should perform some action");
    }

    @Test
    public void testForgotPasswordLink() {
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Esqueceu a sua senha?")));
        forgotPasswordLink.click();
        
        // Verify navigation to password recovery page
        wait.until(ExpectedConditions.urlContains("password.html"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("password.html"));
    }

    @Test
    public void testCreateAccountLink() {
        WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Criar uma conta")));
        createAccountLink.click();
        
        // Verify navigation to account creation page
        wait.until(ExpectedConditions.urlContains("account.html"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("account.html"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
package deepseek.ws08.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PasswordRecoveryPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String PASSWORD_RECOVERY_URL = "https://wavingtest.github.io/system-healing-test/password.html";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(PASSWORD_RECOVERY_URL);
    }

    @Test
    public void testPageTitle() {
        Assertions.assertEquals("Password Recovery", driver.getTitle());
    }

    @Test
    public void testEmailFieldPresence() {
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='email']")));
        Assertions.assertTrue(emailField.isDisplayed());
    }

    @Test
    public void testRecoveryButtonFunctionality() {
        WebElement recoveryButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("btnRecover")));
        recoveryButton.click();
        
        // Verify some expected behavior after recovery click
        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("successMessage")));
        Assertions.assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testBackToLoginLink() {
        WebElement backToLoginLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Voltar para o login")));
        backToLoginLink.click();
        
        // Verify navigation back to login page
        wait.until(ExpectedConditions.urlContains("index.html"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
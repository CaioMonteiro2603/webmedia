package deepseek.ws04.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BugBankTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://bugbank.netlify.app/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testMainPageElementsPresence() {
        // Verify main page title
        Assertions.assertEquals("BugBank | O banco com bugs e falhas do seu jeito", driver.getTitle());

        // Verify presence
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'BugBank')]")));
        Assertions.assertTrue(logo.isDisplayed());

        // Verify login form elements
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        WebElement requirementsLink = driver.findElement(By.linkText("Conheça nossos requisitos"));

        Assertions.assertTrue(emailInput.isDisplayed());
        Assertions.assertTrue(passwordInput.isDisplayed());
        Assertions.assertTrue(loginButton.isDisplayed());
        Assertions.assertTrue(registerButton.isDisplayed());
        Assertions.assertTrue(requirementsLink.isDisplayed());
    }

    @Test
    public void testLoginFunctionality() {
        // Test invalid login
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));

        emailInput.sendKeys("invalid@email.com");
        passwordInput.sendKeys("wrongpassword");
        loginButton.click();

        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'Usuário ou senha inválido')]")));
        Assertions.assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void testRegistrationPageNavigation() {
        // Click on register button
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();

        // Verify registration form elements
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        WebElement nameInput = driver.findElement(By.name("name"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement confirmPasswordInput = driver.findElement(By.name("passwordConfirmation"));
        WebElement registerSubmitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        WebElement backButton = driver.findElement(By.id("btnBackButton"));

        Assertions.assertTrue(emailInput.isDisplayed());
        Assertions.assertTrue(nameInput.isDisplayed());
        Assertions.assertTrue(passwordInput.isDisplayed());
        Assertions.assertTrue(confirmPasswordInput.isDisplayed());
        Assertions.assertTrue(registerSubmitButton.isDisplayed());
        Assertions.assertTrue(backButton.isDisplayed());
    }

    @Test
    public void testRegistrationFunctionality() {
        // Navigate to registration page
        driver.findElement(By.xpath("//button[text()='Registrar']")).click();

        // Fill registration form
        String testEmail = "test" + System.currentTimeMillis() + "@example.com";
        driver.findElement(By.name("email")).sendKeys(testEmail);
        driver.findElement(By.name("name")).sendKeys("Test User");
        driver.findElement(By.name("password")).sendKeys("Test@123");
        driver.findElement(By.name("passwordConfirmation")).sendKeys("Test@123");
        driver.findElement(By.xpath("//button[text()='Cadastrar']")).click();

        // Verify successful registration
        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'foi criada com sucesso')]")));
        Assertions.assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testRequirementsPageNavigation() {
        // Click on requirements link
        WebElement requirementsLink = driver.findElement(By.linkText("Conheça nossos requisitos"));
        requirementsLink.click();

        // Verify requirements page content
        WebElement requirementsTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2[contains(text(), 'Requisitos')]")));
        Assertions.assertTrue(requirementsTitle.isDisplayed());

        // Verify back button functionality
        WebElement backButton = driver.findElement(By.id("btnBackButton"));
        backButton.click();

        // Verify we're back on login page
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[text()='Acessar']")));
        Assertions.assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void testAllLinksOnMainPage() {
        // Get all links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                // Open link in new tab
                ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", href);
                
                // Switch to new tab
                String originalWindow = driver.getWindowHandle();
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!originalWindow.contentEquals(windowHandle)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }

                // Verify page loaded successfully
                Assertions.assertNotEquals("about:blank", driver.getCurrentUrl());
                Assertions.assertFalse(driver.getTitle().isEmpty());

                // Close the tab and switch back
                driver.close();
                driver.switchTo().window(originalWindow);
            }
        }
    }
}
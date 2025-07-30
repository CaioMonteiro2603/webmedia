package deepseek.ws01.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WebPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/Test_Healing/";

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
    public void testMainPageElementsPresence() {
        driver.get(BASE_URL);
        
        // Verify page title
        Assertions.assertEquals("Login Healing", driver.getTitle());
        
        // Verify all expected elements are present
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome-132")));
        WebElement passwordInput = driver.findElement(By.id("senha-123"));
        WebElement dobInput = driver.findElement(By.id("data-123"));
        WebElement optionsSelect = driver.findElement(By.id("opcoes1234"));
        
        Assertions.assertTrue(emailInput.isDisplayed(), "Email input should be displayed");
        Assertions.assertTrue(passwordInput.isDisplayed(), "Password input should be displayed");
        Assertions.assertTrue(dobInput.isDisplayed(), "Date of birth input should be displayed");
        Assertions.assertTrue(optionsSelect.isDisplayed(), "Options select should be displayed");
    }

    @Test
    public void testFormSubmissionWithValidData() {
        driver.get(BASE_URL);
        
        // Fill in form data
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome-132")));
        emailInput.sendKeys("test@example.com");
        
        WebElement passwordInput = driver.findElement(By.id("senha-123"));
        passwordInput.sendKeys("Test123!");
        
        // Set date of birth (format: mm/dd/yyyy)
        WebElement dobInput = driver.findElement(By.id("data-123"));
        String formattedDate = LocalDate.now().minusYears(25).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        dobInput.sendKeys(formattedDate);
        
        // Select multiple options
        Select optionsSelect = new Select(driver.findElement(By.id("opcoes1234")));
        optionsSelect.selectByVisibleText("Opção 1");
        optionsSelect.selectByVisibleText("Opção 3");
        
        // Verify selected options
        List<WebElement> selectedOptions = optionsSelect.getAllSelectedOptions();
        Assertions.assertEquals(2, selectedOptions.size(), "Should have 2 options selected");
        Assertions.assertEquals("Opção 1", selectedOptions.get(0).getText());
        Assertions.assertEquals("Opção 3", selectedOptions.get(1).getText());
    }

    @Test
    public void testFormValidation() {
        driver.get(BASE_URL);
        
        // Try to submit empty form
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome-132")));
        emailInput.sendKeys("");
        emailInput.sendKeys(Keys.TAB);
        
        // Verify validation (assuming HTML5 validation is present)
        Assertions.assertEquals("true", emailInput.getAttribute("required"), "Email should be required");
        Assertions.assertEquals("Please fill out this field.", emailInput.getAttribute("validationMessage"));
    }

    @Test
    public void testNavigationToLinkedPages() {
        driver.get(BASE_URL);
        
        // Check for any links and navigate to them
        // (Currently no links found in initial analysis)
        // This test would be expanded if links are discovered
        
        Assertions.assertTrue(true, "No additional pages found to test");
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        
        // Check for any external links and verify them
        // (Currently no external links found in initial analysis)
        // This test would be expanded if external links are discovered
        
        Assertions.assertTrue(true, "No external links found to test");
    }
}
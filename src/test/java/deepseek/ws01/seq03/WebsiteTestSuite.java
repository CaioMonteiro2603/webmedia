package deepseek.ws01.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WebsiteTestSuite {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "https://wavingtest.github.io/Test_Healing/";

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void testLoginPageElementsPresence() {
        // Verify page title
        Assertions.assertEquals("Login Healing", driver.getTitle());

        // Verify all form elements are present
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome-132")));
        WebElement passwordField = driver.findElement(By.id("senha-123"));
        WebElement dobField = driver.findElement(By.id("data-123"));
        WebElement optionsDropdown = driver.findElement(By.id("opcoes1234"));

        Assertions.assertTrue(emailField.isDisplayed(), "Email field is not displayed");
        Assertions.assertTrue(passwordField.isDisplayed(), "Password field is not displayed");
        Assertions.assertTrue(dobField.isDisplayed(), "Date of Birth field is not displayed");
        Assertions.assertTrue(optionsDropdown.isDisplayed(), "Options dropdown is not displayed");
    }

    @Test
    public void testFormSubmissionWithValidData() {
        // Fill in form fields
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome-132")));
        emailField.sendKeys("test@example.com");

        WebElement passwordField = driver.findElement(By.id("senha-123"));
        passwordField.sendKeys("Test@123");

        // Set date to today's date
        WebElement dobField = driver.findElement(By.id("data-123"));
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        dobField.sendKeys(today);

        // Select an option from dropdown
        Select optionsDropdown = new Select(driver.findElement(By.id("opcoes1234")));
        optionsDropdown.selectByVisibleText("Opção 1");

        // Verify selected option
        Assertions.assertEquals("Opção 1", optionsDropdown.getFirstSelectedOption().getText());
    }

    @Test
    public void testFormValidation() {
        // Try to submit empty form
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome-132")));
        emailField.sendKeys("");
        emailField.sendKeys(Keys.TAB);

        WebElement passwordField = driver.findElement(By.id("senha-123"));
        passwordField.sendKeys("");
        passwordField.sendKeys(Keys.TAB);

        // Verify validation (assuming HTML5 validation is present)
        Assertions.assertEquals("true", emailField.getAttribute("required"));
        Assertions.assertEquals("true", passwordField.getAttribute("required"));
    }

    @Test
    public void testDropdownOptions() {
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("opcoes1234")));
        Select optionsDropdown = new Select(dropdown);

        // Verify all options are present
        Assertions.assertEquals(4, optionsDropdown.getOptions().size());
        Assertions.assertEquals("Opção 1", optionsDropdown.getOptions().get(0).getText());
        Assertions.assertEquals("Opção 2", optionsDropdown.getOptions().get(1).getText());
        Assertions.assertEquals("Opção 3", optionsDropdown.getOptions().get(2).getText());
        Assertions.assertEquals("Opção 4", optionsDropdown.getOptions().get(3).getText());

        // Test selecting each option
        for (int i = 0; i < optionsDropdown.getOptions().size(); i++) {
            optionsDropdown.selectByIndex(i);
            Assertions.assertEquals(optionsDropdown.getOptions().get(i).getText(), 
                                 optionsDropdown.getFirstSelectedOption().getText());
        }
    }

    @Test
    public void testDatePickerFunctionality() {
        WebElement dobField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("data-123")));
        
        // Test with valid date
        String testDate = "12/31/2023";
        dobField.sendKeys(testDate);
        Assertions.assertEquals(testDate, dobField.getAttribute("value"));

        // Test clearing the field
        dobField.clear();
        Assertions.assertEquals("", dobField.getAttribute("value"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
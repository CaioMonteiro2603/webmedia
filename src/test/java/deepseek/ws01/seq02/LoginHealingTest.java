package deepseek.ws01.seq02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoginHealingTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/Test_Healing/";

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void testPageTitle() {
        // Verify page title
        String expectedTitle = "Login Healing";
        String actualTitle = driver.getTitle();
        Assertions.assertEquals(expectedTitle, actualTitle, "Page title doesn't match");
    }

    @Test
    public void testEmailField() {
        // Locate and test email field
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='user-102']")));
        
        // Verify field is present and enabled
        Assertions.assertTrue(emailField.isDisplayed(), "Email field is not displayed");
        Assertions.assertTrue(emailField.isEnabled(), "Email field is not enabled");
        
        // Test input functionality
        String testEmail = "test@example.com";
        emailField.sendKeys(testEmail);
        Assertions.assertEquals(testEmail, emailField.getAttribute("value"), 
            "Email field value doesn't match input");
    }

    @Test
    public void testPasswordField() {
        // Locate and test password field
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='password3']")));
        
        // Verify field is present and enabled
        Assertions.assertTrue(passwordField.isDisplayed(), "Password field is not displayed");
        Assertions.assertTrue(passwordField.isEnabled(), "Password field is not enabled");
        
        // Test input functionality
        String testPassword = "securePassword123";
        passwordField.sendKeys(testPassword);
        Assertions.assertEquals(testPassword, passwordField.getAttribute("value"), 
            "Password field value doesn't match input");
    }

    @Test
    public void testDateOfBirthField() {
        // Locate and test date of birth field
        WebElement dobField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[name='dob']")));
        
        // Verify field is present and enabled
        Assertions.assertTrue(dobField.isDisplayed(), "Date of Birth field is not displayed");
        Assertions.assertTrue(dobField.isEnabled(), "Date of Birth field is not enabled");
        
        // Test date input functionality
        String testDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        dobField.sendKeys(testDate);
        Assertions.assertEquals(testDate, dobField.getAttribute("value"), 
            "Date of Birth field value doesn't match input");
    }

    @Test
    public void testMultiSelectDropdown() {
        // Locate and test multi-select dropdown
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("select[name='opcoes[]']")));
        
        // Verify dropdown is present and enabled
        Assertions.assertTrue(dropdown.isDisplayed(), "Dropdown is not displayed");
        Assertions.assertTrue(dropdown.isEnabled(), "Dropdown is not enabled");
        
        // Create Select object for the dropdown
        Select multiSelect = new Select(dropdown);
        
        // Verify dropdown options
        Assertions.assertEquals(4, multiSelect.getOptions().size(), 
            "Dropdown doesn't have expected number of options");
        
        // Test selecting options
        multiSelect.selectByVisibleText("Opção 1");
        multiSelect.selectByVisibleText("Opção 3");
        
        // Verify selected options
        Assertions.assertEquals(2, multiSelect.getAllSelectedOptions().size(), 
            "Incorrect number of selected options");
    }

    @Test
    public void testFormSubmission() {
        // Test complete form submission
        WebElement emailField = driver.findElement(By.cssSelector("input[name='user-102']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[name='password3']"));
        WebElement dobField = driver.findElement(By.cssSelector("input[name='dob']"));
        WebElement dropdown = driver.findElement(By.cssSelector("select[name='opcoes[]']"));
        
        // Fill out the form
        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("testPassword123");
        dobField.sendKeys("01/01/2000");
        
        Select multiSelect = new Select(dropdown);
        multiSelect.selectByVisibleText("Opção 2");
        multiSelect.selectByVisibleText("Opção 4");
        
        // Submit the form (assuming there's a submit button)
        // Note: The page doesn't show a submit button in the current view
        // If there was one, we would test it like this:
        // WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        // submitButton.click();
        
        // For now, we'll just verify all fields are filled correctly
        Assertions.assertEquals("test@example.com", emailField.getAttribute("value"));
        Assertions.assertEquals("testPassword123", passwordField.getAttribute("value"));
        Assertions.assertEquals("01/01/2000", dobField.getAttribute("value"));
        Assertions.assertEquals(2, multiSelect.getAllSelectedOptions().size());
    }

    @AfterEach
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
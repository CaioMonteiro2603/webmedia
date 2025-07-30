package claudesonnet4.ws01.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Selenium WebDriver test suite for Login Healing website
 * Tests all interactive elements and form functionality
 * 
 * Website URL: https://wavingtest.github.io/Test_Healing/
 * Package: claudesonnet4.ws01.seq03
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginHealingTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/Test_Healing/";
    
    // Element locators
    private static final By EMAIL_FIELD = By.id("nome-132");
    private static final By PASSWORD_FIELD = By.id("senha-123");
    private static final By DATE_FIELD = By.id("data-123");
    private static final By DROPDOWN_FIELD = By.id("opcoes1234");
    
    // Test data
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "testPassword123";
    private static final String TEST_DATE = "01/15/1990";

    @BeforeAll
    static void setUpClass() {
        // Setup Chrome driver with options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode for CI/CD
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeEach
    void setUp() {
        // Navigate to the main page before each test
        driver.get(BASE_URL);
        
        // Wait for page to load completely
        wait.until(ExpectedConditions.titleContains("Login Healing"));
    }

    @Test
    @Order(1)
    @DisplayName("Test page loads successfully and has correct title")
    void testPageLoadsSuccessfully() {
        // Verify page title
        assertEquals("Login Healing", driver.getTitle(), "Page title should be 'Login Healing'");
        
        // Verify URL
        assertEquals(BASE_URL, driver.getCurrentUrl(), "Current URL should match expected URL");
        
        // Verify page contains the main heading
        assertTrue(driver.getPageSource().contains("Cadastro/Login"), 
                  "Page should contain 'Cadastro/Login' heading");
    }

    @Test
    @Order(2)
    @DisplayName("Test all form elements are present and visible")
    void testFormElementsPresence() {
        // Verify email field is present and visible
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
        assertTrue(emailField.isDisplayed(), "Email field should be visible");
        assertTrue(emailField.isEnabled(), "Email field should be enabled");
        assertEquals("text", emailField.getAttribute("type"), "Email field should be of type 'text'");
        assertEquals("user-102", emailField.getAttribute("name"), "Email field should have correct name attribute");

        // Verify password field is present and visible
        WebElement passwordField = driver.findElement(PASSWORD_FIELD);
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        assertEquals("text", passwordField.getAttribute("type"), "Password field should be of type 'text'");
        assertEquals("password3", passwordField.getAttribute("name"), "Password field should have correct name attribute");

        // Verify date field is present and visible
        WebElement dateField = driver.findElement(DATE_FIELD);
        assertTrue(dateField.isDisplayed(), "Date field should be visible");
        assertTrue(dateField.isEnabled(), "Date field should be enabled");
        assertEquals("date", dateField.getAttribute("type"), "Date field should be of type 'date'");
        assertEquals("dob", dateField.getAttribute("name"), "Date field should have correct name attribute");

        // Verify dropdown field is present and visible
        WebElement dropdownField = driver.findElement(DROPDOWN_FIELD);
        assertTrue(dropdownField.isDisplayed(), "Dropdown field should be visible");
        assertTrue(dropdownField.isEnabled(), "Dropdown field should be enabled");
        assertEquals("select", dropdownField.getTagName().toLowerCase(), "Element should be a select dropdown");
        assertEquals("opcoes[]", dropdownField.getAttribute("name"), "Dropdown should have correct name attribute");
    }

    @Test
    @Order(3)
    @DisplayName("Test email field functionality")
    void testEmailFieldFunctionality() {
        WebElement emailField = driver.findElement(EMAIL_FIELD);
        
        // Clear field and enter test email
        emailField.clear();
        emailField.sendKeys(TEST_EMAIL);
        
        // Verify email was entered correctly
        assertEquals(TEST_EMAIL, emailField.getAttribute("value"), 
                    "Email field should contain the entered email");
        
        // Test clearing the field
        emailField.clear();
        assertEquals("", emailField.getAttribute("value"), 
                    "Email field should be empty after clearing");
        
        // Test entering different email formats
        String[] testEmails = {
            "user@domain.com",
            "test.email@example.org",
            "user+tag@domain.co.uk"
        };
        
        for (String email : testEmails) {
            emailField.clear();
            emailField.sendKeys(email);
            assertEquals(email, emailField.getAttribute("value"), 
                        "Email field should accept email: " + email);
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test password field functionality")
    void testPasswordFieldFunctionality() {
        WebElement passwordField = driver.findElement(PASSWORD_FIELD);
        
        // Clear field and enter test password
        passwordField.clear();
        passwordField.sendKeys(TEST_PASSWORD);
        
        // Verify password was entered correctly
        assertEquals(TEST_PASSWORD, passwordField.getAttribute("value"), 
                    "Password field should contain the entered password");
        
        // Test clearing the field
        passwordField.clear();
        assertEquals("", passwordField.getAttribute("value"), 
                    "Password field should be empty after clearing");
        
        // Test entering different password types
        String[] testPasswords = {
            "simplepass",
            "Complex@Pass123",
            "!@#$%^&*()",
            "12345678"
        };
        
        for (String password : testPasswords) {
            passwordField.clear();
            passwordField.sendKeys(password);
            assertEquals(password, passwordField.getAttribute("value"), 
                        "Password field should accept password: " + password);
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test date field functionality")
    void testDateFieldFunctionality() {
        WebElement dateField = driver.findElement(DATE_FIELD);
        
        // Clear field and enter test date
        dateField.clear();
        dateField.sendKeys("01151990"); // Date input format for Selenium
        
        // Verify date was entered (format may vary based on browser)
        String enteredDate = dateField.getAttribute("value");
        assertNotNull(enteredDate, "Date field should contain a value");
        assertFalse(enteredDate.isEmpty(), "Date field should not be empty");
        
        // Test clearing the field
        dateField.clear();
        String clearedValue = dateField.getAttribute("value");
        assertTrue(clearedValue == null || clearedValue.isEmpty(), 
                  "Date field should be empty after clearing");
        
        // Test entering different date formats
        String[] testDates = {
            "12251995",
            "06151985",
            "03101992"
        };
        
        for (String date : testDates) {
            dateField.clear();
            dateField.sendKeys(date);
            String currentValue = dateField.getAttribute("value");
            assertNotNull(currentValue, "Date field should accept date input: " + date);
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test dropdown field functionality and options")
    void testDropdownFieldFunctionality() {
        WebElement dropdownElement = driver.findElement(DROPDOWN_FIELD);
        Select dropdown = new Select(dropdownElement);
        
        // Verify dropdown supports multiple selections
        assertTrue(dropdown.isMultiple(), "Dropdown should support multiple selections");
        
        // Get all available options
        List<WebElement> options = dropdown.getOptions();
        assertEquals(4, options.size(), "Dropdown should have 4 options");
        
        // Verify option texts
        String[] expectedOptions = {"Opção 1", "Opção 2", "Opção 3", "Opção 4"};
        for (int i = 0; i < expectedOptions.length; i++) {
            assertEquals(expectedOptions[i], options.get(i).getText(), 
                        "Option " + (i + 1) + " should have correct text");
        }
        
        // Test selecting individual options
        for (int i = 0; i < options.size(); i++) {
            dropdown.deselectAll(); // Clear previous selections
            dropdown.selectByIndex(i);
            
            List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
            assertEquals(1, selectedOptions.size(), 
                        "Should have exactly one option selected");
            assertEquals(expectedOptions[i], selectedOptions.get(0).getText(), 
                        "Selected option should match expected text");
        }
        
        // Test selecting multiple options
        dropdown.deselectAll();
        dropdown.selectByIndex(0);
        dropdown.selectByIndex(2);
        
        List<WebElement> multipleSelected = dropdown.getAllSelectedOptions();
        assertEquals(2, multipleSelected.size(), 
                    "Should have exactly two options selected");
        assertEquals("Opção 1", multipleSelected.get(0).getText(), 
                    "First selected option should be 'Opção 1'");
        assertEquals("Opção 3", multipleSelected.get(1).getText(), 
                    "Second selected option should be 'Opção 3'");
        
        // Test selecting all options
        dropdown.deselectAll();
        for (int i = 0; i < options.size(); i++) {
            dropdown.selectByIndex(i);
        }
        
        List<WebElement> allSelected = dropdown.getAllSelectedOptions();
        assertEquals(4, allSelected.size(), 
                    "Should have all four options selected");
        
        // Test deselecting options
        dropdown.deselectByIndex(1);
        List<WebElement> afterDeselect = dropdown.getAllSelectedOptions();
        assertEquals(3, afterDeselect.size(), 
                    "Should have three options selected after deselecting one");
        
        // Test deselecting all
        dropdown.deselectAll();
        List<WebElement> noneSelected = dropdown.getAllSelectedOptions();
        assertEquals(0, noneSelected.size(), 
                    "Should have no options selected after deselecting all");
    }

    @Test
    @Order(7)
    @DisplayName("Test complete form filling workflow")
    void testCompleteFormWorkflow() {
        // Fill email field
        WebElement emailField = driver.findElement(EMAIL_FIELD);
        emailField.clear();
        emailField.sendKeys(TEST_EMAIL);
        
        // Fill password field
        WebElement passwordField = driver.findElement(PASSWORD_FIELD);
        passwordField.clear();
        passwordField.sendKeys(TEST_PASSWORD);
        
        // Fill date field
        WebElement dateField = driver.findElement(DATE_FIELD);
        dateField.clear();
        dateField.sendKeys("01151990");
        
        // Select dropdown options
        WebElement dropdownElement = driver.findElement(DROPDOWN_FIELD);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(0);
        dropdown.selectByIndex(2);
        
        // Verify all fields are filled correctly
        assertEquals(TEST_EMAIL, emailField.getAttribute("value"), 
                    "Email should be filled correctly");
        assertEquals(TEST_PASSWORD, passwordField.getAttribute("value"), 
                    "Password should be filled correctly");
        assertNotNull(dateField.getAttribute("value"), 
                     "Date should be filled");
        
        List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
        assertEquals(2, selectedOptions.size(), 
                    "Should have two dropdown options selected");
        assertEquals("Opção 1", selectedOptions.get(0).getText(), 
                    "First selected option should be correct");
        assertEquals("Opção 3", selectedOptions.get(1).getText(), 
                    "Second selected option should be correct");
    }

    @Test
    @Order(8)
    @DisplayName("Test form field validation and edge cases")
    void testFormFieldValidation() {
        // Test email field with edge cases
        WebElement emailField = driver.findElement(EMAIL_FIELD);
        
        // Test very long email
        String longEmail = "a".repeat(100) + "@example.com";
        emailField.clear();
        emailField.sendKeys(longEmail);
        assertEquals(longEmail, emailField.getAttribute("value"), 
                    "Email field should accept long email");
        
        // Test special characters in email
        String specialEmail = "test+special@domain-name.co.uk";
        emailField.clear();
        emailField.sendKeys(specialEmail);
        assertEquals(specialEmail, emailField.getAttribute("value"), 
                    "Email field should accept special characters");
        
        // Test password field with edge cases
        WebElement passwordField = driver.findElement(PASSWORD_FIELD);
        
        // Test very long password
        String longPassword = "a".repeat(200);
        passwordField.clear();
        passwordField.sendKeys(longPassword);
        assertEquals(longPassword, passwordField.getAttribute("value"), 
                    "Password field should accept long password");
        
        // Test password with all special characters
        String specialPassword = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        passwordField.clear();
        passwordField.sendKeys(specialPassword);
        assertEquals(specialPassword, passwordField.getAttribute("value"), 
                    "Password field should accept special characters");
    }

    @Test
    @Order(9)
    @DisplayName("Test page responsiveness and element positioning")
    void testPageResponsiveness() {
        // Test different window sizes
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        
        // Verify all elements are still visible and accessible
        assertTrue(driver.findElement(EMAIL_FIELD).isDisplayed(), 
                  "Email field should be visible at 1920x1080");
        assertTrue(driver.findElement(PASSWORD_FIELD).isDisplayed(), 
                  "Password field should be visible at 1920x1080");
        assertTrue(driver.findElement(DATE_FIELD).isDisplayed(), 
                  "Date field should be visible at 1920x1080");
        assertTrue(driver.findElement(DROPDOWN_FIELD).isDisplayed(), 
                  "Dropdown field should be visible at 1920x1080");
        
        // Test mobile size
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        
        // Verify all elements are still accessible on mobile
        assertTrue(driver.findElement(EMAIL_FIELD).isDisplayed(), 
                  "Email field should be visible on mobile");
        assertTrue(driver.findElement(PASSWORD_FIELD).isDisplayed(), 
                  "Password field should be visible on mobile");
        assertTrue(driver.findElement(DATE_FIELD).isDisplayed(), 
                  "Date field should be visible on mobile");
        assertTrue(driver.findElement(DROPDOWN_FIELD).isDisplayed(), 
                  "Dropdown field should be visible on mobile");
        
        // Reset to default size
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1024, 768));
    }

    @Test
    @Order(10)
    @DisplayName("Test form accessibility and usability")
    void testFormAccessibility() {
        // Test tab navigation through form elements
        WebElement emailField = driver.findElement(EMAIL_FIELD);
        emailField.click();
        
        // Verify email field has focus
        assertEquals(emailField, driver.switchTo().activeElement(), 
                    "Email field should have focus when clicked");
        
        // Test that all form elements can receive focus
        WebElement passwordField = driver.findElement(PASSWORD_FIELD);
        passwordField.click();
        assertEquals(passwordField, driver.switchTo().activeElement(), 
                    "Password field should have focus when clicked");
        
        WebElement dateField = driver.findElement(DATE_FIELD);
        dateField.click();
        assertEquals(dateField, driver.switchTo().activeElement(), 
                    "Date field should have focus when clicked");
        
        WebElement dropdownField = driver.findElement(DROPDOWN_FIELD);
        dropdownField.click();
        assertEquals(dropdownField, driver.switchTo().activeElement(), 
                    "Dropdown field should have focus when clicked");
        
        // Test form labels and accessibility attributes
        assertTrue(driver.getPageSource().contains("Email"), 
                  "Page should contain Email label");
        assertTrue(driver.getPageSource().contains("Password"), 
                  "Page should contain Password label");
        assertTrue(driver.getPageSource().contains("Data de Nascimento"), 
                  "Page should contain Date of Birth label");
        assertTrue(driver.getPageSource().contains("Escolha múltiplas opções"), 
                  "Page should contain dropdown label");
    }

    @AfterEach
    void tearDown() {
        // Clear all form fields after each test
        try {
            driver.findElement(EMAIL_FIELD).clear();
            driver.findElement(PASSWORD_FIELD).clear();
            driver.findElement(DATE_FIELD).clear();
            
            Select dropdown = new Select(driver.findElement(DROPDOWN_FIELD));
            dropdown.deselectAll();
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    @AfterAll
    static void tearDownClass() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
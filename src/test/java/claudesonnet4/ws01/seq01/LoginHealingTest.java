package claudesonnet4.ws01.seq01;

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

/**
 * Comprehensive Selenium WebDriver test suite for Login Healing form
 * Tests all form elements and their interactions
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginHealingTest {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/Test_Healing/";
    
    @BeforeAll
    static void setUpClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
    
    @BeforeEach
    void setUp() {
        driver.get(BASE_URL);
    }
    
    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Test 1: Verify page loads correctly and all elements are present
     */
    @Test
    @Order(1)
    @DisplayName("Test page load and element presence")
    void testPageLoadAndElementPresence() {
        // Verify page title
        Assertions.assertEquals("Login Healing", driver.getTitle(), 
            "Page title should be 'Login Healing'");
        
        // Verify main heading
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'Cadastro/Login')] | //div[contains(text(), 'Cadastro/Login')]")));
        Assertions.assertTrue(heading.isDisplayed(), "Main heading should be visible");
        
        // Verify email field presence
        WebElement emailField = driver.findElement(By.id("nome-132"));
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible");
        Assertions.assertEquals("text", emailField.getAttribute("type"), 
            "Email field should be of type text");
        
        // Verify password field presence
        WebElement passwordField = driver.findElement(By.id("senha-123"));
        Assertions.assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        Assertions.assertEquals("text", passwordField.getAttribute("type"), 
            "Password field should be of type text");
        
        // Verify date field presence
        WebElement dateField = driver.findElement(By.id("data-123"));
        Assertions.assertTrue(dateField.isDisplayed(), "Date field should be visible");
        Assertions.assertEquals("date", dateField.getAttribute("type"), 
            "Date field should be of type date");
        
        // Verify dropdown presence
        WebElement dropdown = driver.findElement(By.id("opcoes1234"));
        Assertions.assertTrue(dropdown.isDisplayed(), "Dropdown should be visible");
        Assertions.assertEquals("select-multiple", dropdown.getAttribute("type"), 
            "Dropdown should be a select element");
    }
    
    /**
     * Test 2: Test email field functionality
     */
    @Test
    @Order(2)
    @DisplayName("Test email field input and validation")
    void testEmailFieldFunctionality() {
        WebElement emailField = driver.findElement(By.id("nome-132"));
        
        // Test valid email input
        String validEmail = "test@example.com";
        emailField.clear();
        emailField.sendKeys(validEmail);
        
        Assertions.assertEquals(validEmail, emailField.getAttribute("value"), 
            "Email field should contain the entered email");
        
        // Test field clearing
        emailField.clear();
        Assertions.assertEquals("", emailField.getAttribute("value"), 
            "Email field should be empty after clearing");
        
        // Test special characters
        String specialEmail = "test+special@domain-name.co.uk";
        emailField.sendKeys(specialEmail);
        Assertions.assertEquals(specialEmail, emailField.getAttribute("value"), 
            "Email field should handle special characters");
    }
    
    /**
     * Test 3: Test password field functionality
     */
    @Test
    @Order(3)
    @DisplayName("Test password field input and validation")
    void testPasswordFieldFunctionality() {
        WebElement passwordField = driver.findElement(By.id("senha-123"));
        
        // Test password input
        String password = "SecurePassword123!";
        passwordField.clear();
        passwordField.sendKeys(password);
        
        Assertions.assertEquals(password, passwordField.getAttribute("value"), 
            "Password field should contain the entered password");
        
        // Test field clearing
        passwordField.clear();
        Assertions.assertEquals("", passwordField.getAttribute("value"), 
            "Password field should be empty after clearing");
        
        // Test special characters in password
        String specialPassword = "P@ssw0rd!#$%";
        passwordField.sendKeys(specialPassword);
        Assertions.assertEquals(specialPassword, passwordField.getAttribute("value"), 
            "Password field should handle special characters");
    }
    
    /**
     * Test 4: Test date field functionality
     */
    @Test
    @Order(4)
    @DisplayName("Test date field input and validation")
    void testDateFieldFunctionality() {
        WebElement dateField = driver.findElement(By.id("data-123"));
        
        // Test valid date input
        String validDate = "1990-05-15";
        dateField.clear();
        dateField.sendKeys(validDate);
        
        Assertions.assertEquals(validDate, dateField.getAttribute("value"), 
            "Date field should contain the entered date");
        
        // Test field clearing
        dateField.clear();
        Assertions.assertEquals("", dateField.getAttribute("value"), 
            "Date field should be empty after clearing");
        
        // Test current date
        String currentDate = "2025-01-15";
        dateField.sendKeys(currentDate);
        Assertions.assertEquals(currentDate, dateField.getAttribute("value"), 
            "Date field should accept current date");
    }
    
    /**
     * Test 5: Test dropdown functionality
     */
    @Test
    @Order(5)
    @DisplayName("Test dropdown selection functionality")
    void testDropdownFunctionality() {
        WebElement dropdownElement = driver.findElement(By.id("opcoes1234"));
        Select dropdown = new Select(dropdownElement);
        
        // Verify dropdown is multiple select
        Assertions.assertTrue(dropdown.isMultiple(), 
            "Dropdown should allow multiple selections");
        
        // Get all options
        List<WebElement> options = dropdown.getOptions();
        Assertions.assertEquals(4, options.size(), 
            "Dropdown should have 4 options");
        
        // Verify option texts
        Assertions.assertEquals("Opção 1", options.get(0).getText(), 
            "First option should be 'Opção 1'");
        Assertions.assertEquals("Opção 2", options.get(1).getText(), 
            "Second option should be 'Opção 2'");
        Assertions.assertEquals("Opção 3", options.get(2).getText(), 
            "Third option should be 'Opção 3'");
        Assertions.assertEquals("Opção 4", options.get(3).getText(), 
            "Fourth option should be 'Opção 4'");
        
        // Test single selection
        dropdown.selectByIndex(0);
        List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
        Assertions.assertEquals(1, selectedOptions.size(), 
            "Should have one selected option");
        Assertions.assertEquals("Opção 1", selectedOptions.get(0).getText(), 
            "Selected option should be 'Opção 1'");
        
        // Test multiple selections
        dropdown.selectByIndex(2);
        selectedOptions = dropdown.getAllSelectedOptions();
        Assertions.assertEquals(2, selectedOptions.size(), 
            "Should have two selected options");
        
        // Test deselection
        dropdown.deselectByIndex(0);
        selectedOptions = dropdown.getAllSelectedOptions();
        Assertions.assertEquals(1, selectedOptions.size(), 
            "Should have one selected option after deselection");
        
        // Test select all options
        dropdown.selectByIndex(1);
        dropdown.selectByIndex(3);
        selectedOptions = dropdown.getAllSelectedOptions();
        Assertions.assertEquals(3, selectedOptions.size(), 
            "Should have three selected options");
        
        // Test deselect all
        dropdown.deselectAll();
        selectedOptions = dropdown.getAllSelectedOptions();
        Assertions.assertEquals(0, selectedOptions.size(), 
            "Should have no selected options after deselect all");
    }
    
    /**
     * Test 6: Test complete form filling scenario
     */
    @Test
    @Order(6)
    @DisplayName("Test complete form filling scenario")
    void testCompleteFormFilling() {
        // Fill email field
        WebElement emailField = driver.findElement(By.id("nome-132"));
        emailField.clear();
        emailField.sendKeys("user@testdomain.com");
        
        // Fill password field
        WebElement passwordField = driver.findElement(By.id("senha-123"));
        passwordField.clear();
        passwordField.sendKeys("TestPassword123");
        
        // Fill date field
        WebElement dateField = driver.findElement(By.id("data-123"));
        dateField.clear();
        dateField.sendKeys("1985-12-25");
        
        // Select multiple options in dropdown
        WebElement dropdownElement = driver.findElement(By.id("opcoes1234"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(0);
        dropdown.selectByIndex(2);
        
        // Verify all fields are filled correctly
        Assertions.assertEquals("user@testdomain.com", emailField.getAttribute("value"), 
            "Email field should contain correct value");
        Assertions.assertEquals("TestPassword123", passwordField.getAttribute("value"), 
            "Password field should contain correct value");
        Assertions.assertEquals("1985-12-25", dateField.getAttribute("value"), 
            "Date field should contain correct value");
        
        List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
        Assertions.assertEquals(2, selectedOptions.size(), 
            "Should have two selected options");
        Assertions.assertEquals("Opção 1", selectedOptions.get(0).getText(), 
            "First selected option should be 'Opção 1'");
        Assertions.assertEquals("Opção 3", selectedOptions.get(1).getText(), 
            "Second selected option should be 'Opção 3'");
    }
    
    /**
     * Test 7: Test form field attributes and properties
     */
    @Test
    @Order(7)
    @DisplayName("Test form field attributes and properties")
    void testFormFieldAttributes() {
        // Test email field attributes
        WebElement emailField = driver.findElement(By.id("nome-132"));
        Assertions.assertEquals("nome-132", emailField.getAttribute("id"), 
            "Email field should have correct id");
        Assertions.assertEquals("user-102", emailField.getAttribute("name"), 
            "Email field should have correct name attribute");
        
        // Test password field attributes
        WebElement passwordField = driver.findElement(By.id("senha-123"));
        Assertions.assertEquals("senha-123", passwordField.getAttribute("id"), 
            "Password field should have correct id");
        Assertions.assertEquals("password3", passwordField.getAttribute("name"), 
            "Password field should have correct name attribute");
        
        // Test date field attributes
        WebElement dateField = driver.findElement(By.id("data-123"));
        Assertions.assertEquals("data-123", dateField.getAttribute("id"), 
            "Date field should have correct id");
        Assertions.assertEquals("dob", dateField.getAttribute("name"), 
            "Date field should have correct name attribute");
        
        // Test dropdown attributes
        WebElement dropdownElement = driver.findElement(By.id("opcoes1234"));
        Assertions.assertEquals("opcoes1234", dropdownElement.getAttribute("id"), 
            "Dropdown should have correct id");
        Assertions.assertEquals("opcoes[]", dropdownElement.getAttribute("name"), 
            "Dropdown should have correct name attribute");
    }
    
    /**
     * Test 8: Test form validation and edge cases
     */
    @Test
    @Order(8)
    @DisplayName("Test form validation and edge cases")
    void testFormValidationAndEdgeCases() {
        // Test empty form submission (if applicable)
        WebElement emailField = driver.findElement(By.id("nome-132"));
        WebElement passwordField = driver.findElement(By.id("senha-123"));
        WebElement dateField = driver.findElement(By.id("data-123"));
        
        // Clear all fields
        emailField.clear();
        passwordField.clear();
        dateField.clear();
        
        // Verify fields are empty
        Assertions.assertEquals("", emailField.getAttribute("value"), 
            "Email field should be empty");
        Assertions.assertEquals("", passwordField.getAttribute("value"), 
            "Password field should be empty");
        Assertions.assertEquals("", dateField.getAttribute("value"), 
            "Date field should be empty");
        
        // Test very long input
        String longText = "a".repeat(1000);
        emailField.sendKeys(longText);
        Assertions.assertTrue(emailField.getAttribute("value").length() > 0, 
            "Email field should accept long input");
        
        // Test special characters
        passwordField.sendKeys("!@#$%^&*()_+-=[]{}|;:,.<>?");
        Assertions.assertTrue(passwordField.getAttribute("value").length() > 0, 
            "Password field should accept special characters");
    }
    
    /**
     * Test 9: Test page responsiveness and element visibility
     */
    @Test
    @Order(9)
    @DisplayName("Test page responsiveness and element visibility")
    void testPageResponsivenessAndVisibility() {
        // Test all elements are visible and enabled
        WebElement emailField = driver.findElement(By.id("nome-132"));
        WebElement passwordField = driver.findElement(By.id("senha-123"));
        WebElement dateField = driver.findElement(By.id("data-123"));
        WebElement dropdownElement = driver.findElement(By.id("opcoes1234"));
        
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible");
        Assertions.assertTrue(emailField.isEnabled(), "Email field should be enabled");
        
        Assertions.assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        Assertions.assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        
        Assertions.assertTrue(dateField.isDisplayed(), "Date field should be visible");
        Assertions.assertTrue(dateField.isEnabled(), "Date field should be enabled");
        
        Assertions.assertTrue(dropdownElement.isDisplayed(), "Dropdown should be visible");
        Assertions.assertTrue(dropdownElement.isEnabled(), "Dropdown should be enabled");
        
        // Test element positioning (all should be in viewport)
        Assertions.assertTrue(emailField.getLocation().getX() >= 0, 
            "Email field should be positioned correctly");
        Assertions.assertTrue(passwordField.getLocation().getX() >= 0, 
            "Password field should be positioned correctly");
        Assertions.assertTrue(dateField.getLocation().getX() >= 0, 
            "Date field should be positioned correctly");
        Assertions.assertTrue(dropdownElement.getLocation().getX() >= 0, 
            "Dropdown should be positioned correctly");
    }
    
    /**
     * Test 10: Test form interaction sequence
     */
    @Test
    @Order(10)
    @DisplayName("Test form interaction sequence")
    void testFormInteractionSequence() {
        // Test tab navigation and form flow
        WebElement emailField = driver.findElement(By.id("nome-132"));
        WebElement passwordField = driver.findElement(By.id("senha-123"));
        WebElement dateField = driver.findElement(By.id("data-123"));
        WebElement dropdownElement = driver.findElement(By.id("opcoes1234"));
        
        // Start with email field
        emailField.click();
        emailField.sendKeys("sequence@test.com");
        
        // Move to password field
        passwordField.click();
        passwordField.sendKeys("SequenceTest123");
        
        // Move to date field
        dateField.click();
        dateField.sendKeys("2000-01-01");
        
        // Move to dropdown
        dropdownElement.click();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(1);
        
        // Verify the sequence worked
        Assertions.assertEquals("sequence@test.com", emailField.getAttribute("value"), 
            "Email should be filled in sequence");
        Assertions.assertEquals("SequenceTest123", passwordField.getAttribute("value"), 
            "Password should be filled in sequence");
        Assertions.assertEquals("2000-01-01", dateField.getAttribute("value"), 
            "Date should be filled in sequence");
        
        List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
        Assertions.assertEquals(1, selectedOptions.size(), 
            "One option should be selected in sequence");
        Assertions.assertEquals("Opção 2", selectedOptions.get(0).getText(), 
            "Second option should be selected");
    }
}
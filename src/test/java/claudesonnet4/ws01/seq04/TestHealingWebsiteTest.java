package claudesonnet4.ws01.seq04;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Comprehensive Selenium WebDriver Test Suite for Test Healing Website
 * Tests all interactive elements, form validations, and page functionality
 * 
 * Website Under Test: https://wavingtest.github.io/Test_Healing/
 * 
 * Test Coverage:
 * - Main page form elements (email, password, date, dropdown)
 * - Form field interactions and validations
 * - Dropdown selection functionality
 * - Page title and content verification
 * - Element presence and visibility tests
 * - Cross-browser compatibility considerations
 * - External link validation (GitHub repository)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestHealingWebsiteTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/Test_Healing/";
    private static final String GITHUB_REPO_URL = "https://github.com/wavingtest/Test_Healing";
    
    // Element locators
    private static final String EMAIL_FIELD_ID = "nome-132";
    private static final String PASSWORD_FIELD_ID = "senha-123";
    private static final String DATE_FIELD_ID = "data-123";
    private static final String DROPDOWN_FIELD_ID = "opcoes1234";
    
    // Test data
    private static final String VALID_EMAIL = "test@example.com";
    private static final String INVALID_EMAIL = "invalid-email";
    private static final String VALID_PASSWORD = "testpassword123";
    private static final String VALID_DATE = "1990-01-15";
    private static final String INVALID_DATE = "invalid-date";

    @BeforeEach
    void setUp() {
        // Configure Chrome options for headless testing
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Test 1: Verify main page loads correctly and contains expected elements
     */
    @Test
    @Order(1)
    @DisplayName("Test Main Page Load and Basic Elements")
    void testMainPageLoadAndBasicElements() {
        // Navigate to main page
        driver.get(BASE_URL);
        
        // Verify page title
        Assertions.assertEquals("Login Healing", driver.getTitle(), 
            "Page title should be 'Login Healing'");
        
        // Verify page URL
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl(), 
            "Current URL should match the base URL");
        
        // Verify main heading is present
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'Cadastro/Login')] | //div[contains(text(), 'Cadastro/Login')] | //*[contains(text(), 'Cadastro/Login')]")));
        Assertions.assertTrue(heading.isDisplayed(), 
            "Main heading 'Cadastro/Login' should be visible");
        
        // Verify all form elements are present
        WebElement emailField = driver.findElement(By.id(EMAIL_FIELD_ID));
        WebElement passwordField = driver.findElement(By.id(PASSWORD_FIELD_ID));
        WebElement dateField = driver.findElement(By.id(DATE_FIELD_ID));
        WebElement dropdownField = driver.findElement(By.id(DROPDOWN_FIELD_ID));
        
        Assertions.assertTrue(emailField.isDisplayed(), "Email field should be visible");
        Assertions.assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        Assertions.assertTrue(dateField.isDisplayed(), "Date field should be visible");
        Assertions.assertTrue(dropdownField.isDisplayed(), "Dropdown field should be visible");
    }

    /**
     * Test 2: Test email field functionality
     */
    @Test
    @Order(2)
    @DisplayName("Test Email Field Functionality")
    void testEmailFieldFunctionality() {
        driver.get(BASE_URL);
        
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id(EMAIL_FIELD_ID)));
        
        // Test email field attributes
        Assertions.assertEquals("user-102", emailField.getAttribute("name"), 
            "Email field should have name attribute 'user-102'");
        Assertions.assertEquals("text", emailField.getAttribute("type"), 
            "Email field should be of type 'text'");
        
        // Test email field input
        emailField.clear();
        emailField.sendKeys(VALID_EMAIL);
        Assertions.assertEquals(VALID_EMAIL, emailField.getAttribute("value"), 
            "Email field should contain the entered email");
        
        // Test email field clearing
        emailField.clear();
        Assertions.assertEquals("", emailField.getAttribute("value"), 
            "Email field should be empty after clearing");
        
        // Test invalid email input
        emailField.sendKeys(INVALID_EMAIL);
        Assertions.assertEquals(INVALID_EMAIL, emailField.getAttribute("value"), 
            "Email field should accept invalid email format");
        
        // Test special characters
        emailField.clear();
        emailField.sendKeys("test+tag@example-domain.co.uk");
        Assertions.assertEquals("test+tag@example-domain.co.uk", emailField.getAttribute("value"), 
            "Email field should accept complex email formats");
    }

    /**
     * Test 3: Test password field functionality
     */
    @Test
    @Order(3)
    @DisplayName("Test Password Field Functionality")
    void testPasswordFieldFunctionality() {
        driver.get(BASE_URL);
        
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id(PASSWORD_FIELD_ID)));
        
        // Test password field attributes
        Assertions.assertEquals("password3", passwordField.getAttribute("name"), 
            "Password field should have name attribute 'password3'");
        Assertions.assertEquals("text", passwordField.getAttribute("type"), 
            "Password field should be of type 'text'");
        
        // Test password field input
        passwordField.clear();
        passwordField.sendKeys(VALID_PASSWORD);
        Assertions.assertEquals(VALID_PASSWORD, passwordField.getAttribute("value"), 
            "Password field should contain the entered password");
        
        // Test password field clearing
        passwordField.clear();
        Assertions.assertEquals("", passwordField.getAttribute("value"), 
            "Password field should be empty after clearing");
        
        // Test special characters in password
        String specialPassword = "P@ssw0rd!#$%";
        passwordField.sendKeys(specialPassword);
        Assertions.assertEquals(specialPassword, passwordField.getAttribute("value"), 
            "Password field should accept special characters");
        
        // Test long password
        String longPassword = "ThisIsAVeryLongPasswordWithMoreThan50CharactersToTestFieldLimits123456789";
        passwordField.clear();
        passwordField.sendKeys(longPassword);
        Assertions.assertFalse(passwordField.getAttribute("value").isEmpty(), 
            "Password field should accept long passwords");
    }

    /**
     * Test 4: Test date field functionality
     */
    @Test
    @Order(4)
    @DisplayName("Test Date Field Functionality")
    void testDateFieldFunctionality() {
        driver.get(BASE_URL);
        
        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(By.id(DATE_FIELD_ID)));
        
        // Test date field attributes
        Assertions.assertEquals("dob", dateField.getAttribute("name"), 
            "Date field should have name attribute 'dob'");
        Assertions.assertEquals("date", dateField.getAttribute("type"), 
            "Date field should be of type 'date'");
        
        // Test valid date input
        dateField.clear();
        dateField.sendKeys(VALID_DATE);
        Assertions.assertEquals(VALID_DATE, dateField.getAttribute("value"), 
            "Date field should contain the entered date");
        
        // Test date field clearing
        dateField.clear();
        Assertions.assertEquals("", dateField.getAttribute("value"), 
            "Date field should be empty after clearing");
        
        // Test different valid date formats
        String[] validDates = {"2000-12-31", "1985-06-15", "2023-01-01"};
        for (String date : validDates) {
            dateField.clear();
            dateField.sendKeys(date);
            Assertions.assertEquals(date, dateField.getAttribute("value"), 
                "Date field should accept valid date: " + date);
        }
        
        // Test future date
        dateField.clear();
        dateField.sendKeys("2030-12-25");
        Assertions.assertEquals("2030-12-25", dateField.getAttribute("value"), 
            "Date field should accept future dates");
    }

    /**
     * Test 5: Test dropdown field functionality
     */
    @Test
    @Order(5)
    @DisplayName("Test Dropdown Field Functionality")
    void testDropdownFieldFunctionality() {
        driver.get(BASE_URL);
        
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(DROPDOWN_FIELD_ID)));
        Select dropdown = new Select(dropdownElement);
        
        // Test dropdown attributes
        Assertions.assertEquals("opcoes[]", dropdownElement.getAttribute("name"), 
            "Dropdown should have name attribute 'opcoes[]'");
        
        // Test dropdown options
        List<WebElement> options = dropdown.getOptions();
        Assertions.assertEquals(4, options.size(), 
            "Dropdown should have exactly 4 options");
        
        // Verify option texts
        String[] expectedOptions = {"Opção 1", "Opção 2", "Opção 3", "Opção 4"};
        for (int i = 0; i < expectedOptions.length; i++) {
            Assertions.assertEquals(expectedOptions[i], options.get(i).getText(), 
                "Option " + (i + 1) + " should have text: " + expectedOptions[i]);
        }
        
        // Test selecting each option
        for (int i = 0; i < options.size(); i++) {
            dropdown.selectByIndex(i);
            WebElement selectedOption = dropdown.getFirstSelectedOption();
            Assertions.assertEquals(expectedOptions[i], selectedOption.getText(), 
                "Selected option should be: " + expectedOptions[i]);
        }
        
        // Test selecting by visible text
        dropdown.selectByVisibleText("Opção 2");
        Assertions.assertEquals("Opção 2", dropdown.getFirstSelectedOption().getText(), 
            "Should be able to select option by visible text");
        
        // Test selecting by value (if values are different from text)
        dropdown.selectByIndex(0);
        Assertions.assertEquals("Opção 1", dropdown.getFirstSelectedOption().getText(), 
            "Should be able to select first option");
    }

    /**
     * Test 6: Test form interaction workflow
     */
    @Test
    @Order(6)
    @DisplayName("Test Complete Form Interaction Workflow")
    void testCompleteFormInteractionWorkflow() {
        driver.get(BASE_URL);
        
        // Fill all form fields in sequence
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id(EMAIL_FIELD_ID)));
        WebElement passwordField = driver.findElement(By.id(PASSWORD_FIELD_ID));
        WebElement dateField = driver.findElement(By.id(DATE_FIELD_ID));
        WebElement dropdownElement = driver.findElement(By.id(DROPDOWN_FIELD_ID));
        
        // Fill email field
        emailField.clear();
        emailField.sendKeys(VALID_EMAIL);
        
        // Fill password field
        passwordField.clear();
        passwordField.sendKeys(VALID_PASSWORD);
        
        // Fill date field
        dateField.clear();
        dateField.sendKeys(VALID_DATE);
        
        // Select dropdown option
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Opção 3");
        
        // Verify all fields are filled correctly
        Assertions.assertEquals(VALID_EMAIL, emailField.getAttribute("value"), 
            "Email field should retain entered value");
        Assertions.assertEquals(VALID_PASSWORD, passwordField.getAttribute("value"), 
            "Password field should retain entered value");
        Assertions.assertEquals(VALID_DATE, dateField.getAttribute("value"), 
            "Date field should retain entered value");
        Assertions.assertEquals("Opção 3", dropdown.getFirstSelectedOption().getText(), 
            "Dropdown should have selected option");
        
        // Test tab navigation between fields
        emailField.sendKeys(Keys.TAB);
        WebElement activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals(passwordField, activeElement, 
            "Tab should move focus to password field");
    }

    /**
     * Test 7: Test static content elements
     */
    @Test
    @Order(7)
    @DisplayName("Test Static Content Elements")
    void testStaticContentElements() {
        driver.get(BASE_URL);
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(EMAIL_FIELD_ID)));
        
        // Test for static text elements
        String pageSource = driver.getPageSource();
        
        Assertions.assertTrue(pageSource.contains("Email"), 
            "Page should contain 'Email' label");
        Assertions.assertTrue(pageSource.contains("Password"), 
            "Page should contain 'Password' label");
        Assertions.assertTrue(pageSource.contains("Data de Nascimento"), 
            "Page should contain 'Data de Nascimento' label");
        Assertions.assertTrue(pageSource.contains("Escolha múltiplas opções"), 
            "Page should contain dropdown label");
        Assertions.assertTrue(pageSource.contains("texto de exemplo"), 
            "Page should contain example text");
        
        // Test for numeric content
        Assertions.assertTrue(pageSource.contains("99"), 
            "Page should contain number '99'");
        Assertions.assertTrue(pageSource.contains("100"), 
            "Page should contain number '100'");
        Assertions.assertTrue(pageSource.contains("120"), 
            "Page should contain number '120'");
    }

    /**
     * Test 8: Test page responsiveness and element visibility
     */
    @Test
    @Order(8)
    @DisplayName("Test Page Responsiveness and Element Visibility")
    void testPageResponsivenessAndElementVisibility() {
        driver.get(BASE_URL);
        
        // Test different window sizes
        Dimension[] testSizes = {
            new Dimension(1920, 1080), // Desktop
            new Dimension(1366, 768),  // Laptop
            new Dimension(768, 1024),  // Tablet
            new Dimension(375, 667)    // Mobile
        };
        
        for (Dimension size : testSizes) {
            driver.manage().window().setSize(size);
            
            // Wait for elements to be present
            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(EMAIL_FIELD_ID)));
            WebElement passwordField = driver.findElement(By.id(PASSWORD_FIELD_ID));
            WebElement dateField = driver.findElement(By.id(DATE_FIELD_ID));
            WebElement dropdownField = driver.findElement(By.id(DROPDOWN_FIELD_ID));
            
            // Verify elements are still visible at different sizes
            Assertions.assertTrue(emailField.isDisplayed(), 
                "Email field should be visible at size: " + size);
            Assertions.assertTrue(passwordField.isDisplayed(), 
                "Password field should be visible at size: " + size);
            Assertions.assertTrue(dateField.isDisplayed(), 
                "Date field should be visible at size: " + size);
            Assertions.assertTrue(dropdownField.isDisplayed(), 
                "Dropdown field should be visible at size: " + size);
        }
    }

    /**
     * Test 9: Test external link to GitHub repository
     */
    @Test
    @Order(9)
    @DisplayName("Test External Link to GitHub Repository")
    void testExternalLinkToGitHubRepository() {
        // Navigate directly to GitHub repository (external link)
        driver.get(GITHUB_REPO_URL);
        
        // Verify GitHub page loads
        wait.until(ExpectedConditions.titleContains("GitHub"));
        
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("github.com"), 
            "Should navigate to GitHub domain");
        Assertions.assertTrue(currentUrl.contains("wavingtest/Test_Healing"), 
            "Should navigate to correct repository");
        
        // Verify repository content
        WebElement repoTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Test_Healing')]")));
        Assertions.assertTrue(repoTitle.isDisplayed(), 
            "Repository title should be visible");
        
        // Check for repository description
        String pageSource = driver.getPageSource();
        Assertions.assertTrue(pageSource.contains("Page to test Healing application") || 
                            pageSource.contains("Test_Healing"), 
            "Page should contain repository description or name");
    }

    /**
     * Test 10: Test form field validation and error handling
     */
    @Test
    @Order(10)
    @DisplayName("Test Form Field Validation and Error Handling")
    void testFormFieldValidationAndErrorHandling() {
        driver.get(BASE_URL);
        
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id(EMAIL_FIELD_ID)));
        WebElement passwordField = driver.findElement(By.id(PASSWORD_FIELD_ID));
        WebElement dateField = driver.findElement(By.id(DATE_FIELD_ID));
        
        // Test empty field behavior
        emailField.clear();
        passwordField.clear();
        dateField.clear();
        
        // Click outside to trigger any validation
        driver.findElement(By.tagName("body")).click();
        
        // Verify fields can be empty (no required validation)
        Assertions.assertEquals("", emailField.getAttribute("value"), 
            "Email field should accept empty value");
        Assertions.assertEquals("", passwordField.getAttribute("value"), 
            "Password field should accept empty value");
        Assertions.assertEquals("", dateField.getAttribute("value"), 
            "Date field should accept empty value");
        
        // Test maximum length inputs
        String longText = "a".repeat(1000);
        emailField.sendKeys(longText);
        passwordField.sendKeys(longText);
        
        // Verify fields handle long input
        Assertions.assertFalse(emailField.getAttribute("value").isEmpty(), 
            "Email field should handle long input");
        Assertions.assertFalse(passwordField.getAttribute("value").isEmpty(), 
            "Password field should handle long input");
    }

    /**
     * Test 11: Test keyboard navigation and accessibility
     */
    @Test
    @Order(11)
    @DisplayName("Test Keyboard Navigation and Accessibility")
    void testKeyboardNavigationAndAccessibility() {
        driver.get(BASE_URL);
        
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id(EMAIL_FIELD_ID)));
        
        // Start with email field
        emailField.click();
        
        // Test tab navigation through all fields
        WebElement activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals(emailField, activeElement, 
            "Email field should be focused initially");
        
        // Tab to password field
        activeElement.sendKeys(Keys.TAB);
        activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals("password3", activeElement.getAttribute("name"), 
            "Password field should be focused after tab");
        
        // Tab to date field
        activeElement.sendKeys(Keys.TAB);
        activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals("dob", activeElement.getAttribute("name"), 
            "Date field should be focused after tab");
        
        // Tab to dropdown field
        activeElement.sendKeys(Keys.TAB);
        activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals("opcoes[]", activeElement.getAttribute("name"), 
            "Dropdown field should be focused after tab");
        
        // Test dropdown keyboard navigation
        activeElement.sendKeys(Keys.ARROW_DOWN);
        Select dropdown = new Select(activeElement);
        String selectedOption = dropdown.getFirstSelectedOption().getText();
        Assertions.assertNotNull(selectedOption, 
            "Dropdown should have a selected option after arrow key");
    }

    /**
     * Test 12: Test page reload and data persistence
     */
    @Test
    @Order(12)
    @DisplayName("Test Page Reload and Data Persistence")
    void testPageReloadAndDataPersistence() {
        driver.get(BASE_URL);
        
        // Fill form fields
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id(EMAIL_FIELD_ID)));
        WebElement passwordField = driver.findElement(By.id(PASSWORD_FIELD_ID));
        WebElement dateField = driver.findElement(By.id(DATE_FIELD_ID));
        WebElement dropdownElement = driver.findElement(By.id(DROPDOWN_FIELD_ID));
        
        emailField.sendKeys(VALID_EMAIL);
        passwordField.sendKeys(VALID_PASSWORD);
        dateField.sendKeys(VALID_DATE);
        
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Opção 2");
        
        // Reload page
        driver.navigate().refresh();
        
        // Wait for page to reload
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(EMAIL_FIELD_ID)));
        
        // Verify form fields are reset after reload
        emailField = driver.findElement(By.id(EMAIL_FIELD_ID));
        passwordField = driver.findElement(By.id(PASSWORD_FIELD_ID));
        dateField = driver.findElement(By.id(DATE_FIELD_ID));
        dropdownElement = driver.findElement(By.id(DROPDOWN_FIELD_ID));
        
        Assertions.assertEquals("", emailField.getAttribute("value"), 
            "Email field should be empty after page reload");
        Assertions.assertEquals("", passwordField.getAttribute("value"), 
            "Password field should be empty after page reload");
        Assertions.assertEquals("", dateField.getAttribute("value"), 
            "Date field should be empty after page reload");
        
        // Verify dropdown returns to default state
        dropdown = new Select(dropdownElement);
        String defaultSelection = dropdown.getFirstSelectedOption().getText();
        Assertions.assertNotNull(defaultSelection, 
            "Dropdown should have a default selection after reload");
    }

    /**
     * Test 13: Test browser back and forward navigation
     */
    @Test
    @Order(13)
    @DisplayName("Test Browser Back and Forward Navigation")
    void testBrowserBackAndForwardNavigation() {
        // Navigate to main page
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(EMAIL_FIELD_ID)));
        
        // Navigate to GitHub repository
        driver.get(GITHUB_REPO_URL);
        wait.until(ExpectedConditions.titleContains("GitHub"));
        
        // Go back to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("Login Healing"));
        
        Assertions.assertEquals("Login Healing", driver.getTitle(), 
            "Should return to main page after back navigation");
        
        // Verify form elements are still present
        WebElement emailField = driver.findElement(By.id(EMAIL_FIELD_ID));
        Assertions.assertTrue(emailField.isDisplayed(), 
            "Email field should be visible after back navigation");
        
        // Go forward to GitHub
        driver.navigate().forward();
        wait.until(ExpectedConditions.titleContains("GitHub"));
        
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("github.com"), 
            "Should return to GitHub after forward navigation");
    }

    /**
     * Test 14: Test multiple dropdown selections (if supported)
     */
    @Test
    @Order(14)
    @DisplayName("Test Multiple Dropdown Selections")
    void testMultipleDropdownSelections() {
        driver.get(BASE_URL);
        
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(DROPDOWN_FIELD_ID)));
        Select dropdown = new Select(dropdownElement);
        
        // Check if multiple selection is supported
        boolean isMultiple = dropdown.isMultiple();
        
        if (isMultiple) {
            // Test multiple selections
            dropdown.selectByVisibleText("Opção 1");
            dropdown.selectByVisibleText("Opção 3");
            
            List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
            Assertions.assertTrue(selectedOptions.size() >= 2, 
                "Should be able to select multiple options");
            
            // Test deselecting options
            dropdown.deselectByVisibleText("Opção 1");
            selectedOptions = dropdown.getAllSelectedOptions();
            Assertions.assertEquals(1, selectedOptions.size(), 
                "Should have one option selected after deselecting one");
            
            // Test deselect all
            dropdown.deselectAll();
            selectedOptions = dropdown.getAllSelectedOptions();
            Assertions.assertEquals(0, selectedOptions.size(), 
                "Should have no options selected after deselect all");
        } else {
            // Test single selection behavior
            dropdown.selectByVisibleText("Opção 1");
            Assertions.assertEquals("Opção 1", dropdown.getFirstSelectedOption().getText(), 
                "Should select Opção 1");
            
            dropdown.selectByVisibleText("Opção 4");
            Assertions.assertEquals("Opção 4", dropdown.getFirstSelectedOption().getText(), 
                "Should change selection to Opção 4");
            
            List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
            Assertions.assertEquals(1, selectedOptions.size(), 
                "Should have only one option selected in single-select mode");
        }
    }

    /**
     * Test 15: Test page performance and load times
     */
    @Test
    @Order(15)
    @DisplayName("Test Page Performance and Load Times")
    void testPagePerformanceAndLoadTimes() {
        long startTime = System.currentTimeMillis();
        
        driver.get(BASE_URL);
        
        // Wait for all elements to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(EMAIL_FIELD_ID)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PASSWORD_FIELD_ID)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(DATE_FIELD_ID)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(DROPDOWN_FIELD_ID)));
        
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        
        // Verify page loads within reasonable time (10 seconds)
        Assertions.assertTrue(loadTime < 10000, 
            "Page should load within 10 seconds. Actual load time: " + loadTime + "ms");
        
        // Verify all elements are interactive
        WebElement emailField = driver.findElement(By.id(EMAIL_FIELD_ID));
        WebElement passwordField = driver.findElement(By.id(PASSWORD_FIELD_ID));
        WebElement dateField = driver.findElement(By.id(DATE_FIELD_ID));
        WebElement dropdownField = driver.findElement(By.id(DROPDOWN_FIELD_ID));
        
        Assertions.assertTrue(emailField.isEnabled(), "Email field should be enabled");
        Assertions.assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        Assertions.assertTrue(dateField.isEnabled(), "Date field should be enabled");
        Assertions.assertTrue(dropdownField.isEnabled(), "Dropdown field should be enabled");
    }
}
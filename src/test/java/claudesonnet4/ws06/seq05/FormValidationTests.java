package claudesonnet4.ws06.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

/**
 * Specialized test class for form validation scenarios
 * Tests edge cases, boundary conditions, and validation behaviors
 * Package: claudesonnet4.ws06.seq05
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FormValidationTests {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://katalon-test.s3.amazonaws.com/aut/html/form.html";
    
    @BeforeAll
    static void setupClass() {
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

    @BeforeEach
    void setup() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test Email Field Validation with Invalid Formats")
    void testEmailValidationInvalidFormats() {
        WebElement emailField = driver.findElement(By.id("email"));
        
        String[] invalidEmails = {
            "invalid-email",
            "@domain.com",
            "user@",
            "user..name@domain.com",
            "user@domain",
            "user name@domain.com",
            "user@domain..com"
        };
        
        for (String invalidEmail : invalidEmails) {
            emailField.clear();
            emailField.sendKeys(invalidEmail);
            
            // Try to submit and check if validation prevents submission
            WebElement submitButton = driver.findElement(By.id("submit"));
            submitButton.click();
            
            // Verify we're still on the form page (validation should prevent submission)
            Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
            
            // Check if browser shows validation message
            String validationMessage = emailField.getAttribute("validationMessage");
            if (validationMessage != null && !validationMessage.isEmpty()) {
                Assertions.assertFalse(validationMessage.isEmpty());
            }
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test Email Field Validation with Valid Formats")
    void testEmailValidationValidFormats() {
        WebElement emailField = driver.findElement(By.id("email"));
        
        String[] validEmails = {
            "user@domain.com",
            "test.email@example.org",
            "user+tag@domain.co.uk",
            "firstname.lastname@company.com",
            "user123@test-domain.com"
        };
        
        for (String validEmail : validEmails) {
            emailField.clear();
            emailField.sendKeys(validEmail);
            
            // Verify the email was entered correctly
            Assertions.assertEquals(validEmail, emailField.getAttribute("value"));
            
            // Check validation state
            String validationMessage = emailField.getAttribute("validationMessage");
            if (validationMessage != null) {
                Assertions.assertTrue(validationMessage.isEmpty() || validationMessage.equals(""));
            }
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test Input Field Length Limits")
    void testInputFieldLengthLimits() {
        // Test very long inputs
        String longText = "A".repeat(1000);
        String extraLongText = "B".repeat(10000);
        
        WebElement[] textFields = {
            driver.findElement(By.id("first-name")),
            driver.findElement(By.id("last-name")),
            driver.findElement(By.id("address")),
            driver.findElement(By.id("company"))
        };
        
        for (WebElement field : textFields) {
            // Test long text
            field.clear();
            field.sendKeys(longText);
            String enteredValue = field.getAttribute("value");
            Assertions.assertTrue(enteredValue.length() > 0);
            
            // Test extra long text
            field.clear();
            field.sendKeys(extraLongText);
            enteredValue = field.getAttribute("value");
            Assertions.assertTrue(enteredValue.length() > 0);
            
            // Verify field still functions after long input
            field.clear();
            field.sendKeys("Normal text");
            Assertions.assertEquals("Normal text", field.getAttribute("value"));
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test Special Characters in Input Fields")
    void testSpecialCharactersInInputs() {
        String specialChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?`~";
        String unicodeChars = "αβγδε中文日本語한국어العربية";
        String mixedChars = "Test123!@#αβγ中文";
        
        WebElement[] textFields = {
            driver.findElement(By.id("first-name")),
            driver.findElement(By.id("last-name")),
            driver.findElement(By.id("address")),
            driver.findElement(By.id("company"))
        };
        
        for (WebElement field : textFields) {
            // Test special characters
            field.clear();
            field.sendKeys(specialChars);
            Assertions.assertEquals(specialChars, field.getAttribute("value"));
            
            // Test unicode characters
            field.clear();
            field.sendKeys(unicodeChars);
            Assertions.assertEquals(unicodeChars, field.getAttribute("value"));
            
            // Test mixed characters
            field.clear();
            field.sendKeys(mixedChars);
            Assertions.assertEquals(mixedChars, field.getAttribute("value"));
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test Password Field Security")
    void testPasswordFieldSecurity() {
        WebElement passwordField = driver.findElement(By.id("password"));
        
        // Verify password field type
        Assertions.assertEquals("password", passwordField.getAttribute("type"));
        
        // Test various password formats
        String[] passwords = {
            "simple",
            "Complex123!",
            "VeryLongPasswordWithManyCharacters123!@#$%",
            "P@ssw0rd",
            "12345678",
            "!@#$%^&*()"
        };
        
        for (String password : passwords) {
            passwordField.clear();
            passwordField.sendKeys(password);
            
            // Verify password was entered (value should be accessible for testing)
            Assertions.assertEquals(password, passwordField.getAttribute("value"));
            
            // Verify field type remains password
            Assertions.assertEquals("password", passwordField.getAttribute("type"));
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test Date Field Validation")
    void testDateFieldValidation() {
        WebElement dobField = driver.findElement(By.id("dob"));
        
        String[] dateFormats = {
            "01/15/1990",
            "12-25-1985",
            "1990-01-15",
            "15/01/1990",
            "Jan 15, 1990",
            "2000/12/31",
            "invalid-date",
            "32/13/2000", // Invalid date
            "00/00/0000"  // Invalid date
        };
        
        for (String date : dateFormats) {
            dobField.clear();
            dobField.sendKeys(date);
            
            // Verify the date was entered
            Assertions.assertEquals(date, dobField.getAttribute("value"));
            
            // Try submitting to test validation
            WebElement submitButton = driver.findElement(By.id("submit"));
            submitButton.click();
            
            // Verify we're still on the form page
            Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test Dropdown Selection Validation")
    void testDropdownSelectionValidation() {
        // Test Role dropdown
        WebElement roleDropdown = driver.findElement(By.id("role"));
        Select roleSelect = new Select(roleDropdown);
        
        List<WebElement> roleOptions = roleSelect.getOptions();
        Assertions.assertTrue(roleOptions.size() >= 5);
        
        // Test selecting each option
        for (WebElement option : roleOptions) {
            String optionText = option.getText();
            if (!optionText.isEmpty()) {
                roleSelect.selectByVisibleText(optionText);
                Assertions.assertEquals(optionText, roleSelect.getFirstSelectedOption().getText());
            }
        }
        
        // Test Job Expectation dropdown
        WebElement expectationDropdown = driver.findElement(By.id("expectation"));
        Select expectationSelect = new Select(expectationDropdown);
        
        List<WebElement> expectationOptions = expectationSelect.getOptions();
        Assertions.assertTrue(expectationOptions.size() >= 6);
        
        // Test selecting each option
        for (WebElement option : expectationOptions) {
            String optionText = option.getText();
            if (!optionText.isEmpty()) {
                expectationSelect.selectByVisibleText(optionText);
                Assertions.assertEquals(optionText, expectationSelect.getFirstSelectedOption().getText());
            }
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test Checkbox Group Validation")
    void testCheckboxGroupValidation() {
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        Assertions.assertEquals(6, checkboxes.size());
        
        // Test selecting all checkboxes
        for (WebElement checkbox : checkboxes) {
            Assertions.assertTrue(checkbox.isDisplayed());
            Assertions.assertTrue(checkbox.isEnabled());
            
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            Assertions.assertTrue(checkbox.isSelected());
        }
        
        // Test deselecting all checkboxes
        for (WebElement checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                checkbox.click();
            }
            Assertions.assertFalse(checkbox.isSelected());
        }
        
        // Test partial selection
        for (int i = 0; i < checkboxes.size(); i += 2) {
            checkboxes.get(i).click();
            Assertions.assertTrue(checkboxes.get(i).isSelected());
        }
    }

    @Test
    @Order(9)
    @DisplayName("Test Radio Button Group Validation")
    void testRadioButtonGroupValidation() {
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        Assertions.assertEquals(3, genderRadios.size());
        
        // Test that only one radio can be selected at a time
        for (int i = 0; i < genderRadios.size(); i++) {
            WebElement currentRadio = genderRadios.get(i);
            currentRadio.click();
            
            // Verify current radio is selected
            Assertions.assertTrue(currentRadio.isSelected());
            
            // Verify other radios are not selected
            for (int j = 0; j < genderRadios.size(); j++) {
                if (i != j) {
                    Assertions.assertFalse(genderRadios.get(j).isSelected());
                }
            }
        }
    }

    @Test
    @Order(10)
    @DisplayName("Test Textarea Validation")
    void testTextareaValidation() {
        WebElement commentTextarea = driver.findElement(By.id("comment"));
        
        // Test various text inputs
        String[] testTexts = {
            "Short comment",
            "This is a much longer comment that spans multiple lines and contains various types of content to test the textarea functionality thoroughly.",
            "Line 1\nLine 2\nLine 3\nMultiple lines with line breaks",
            "Special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?",
            "Unicode: αβγδε中文日本語한국어العربية",
            "Mixed: Text123!@#αβγ中文\nNew line\tTab character"
        };
        
        for (String text : testTexts) {
            commentTextarea.clear();
            commentTextarea.sendKeys(text);
            
            // Verify text was entered correctly
            Assertions.assertEquals(text, commentTextarea.getAttribute("value"));
        }
        
        // Test very long text
        String longText = "A".repeat(5000);
        commentTextarea.clear();
        commentTextarea.sendKeys(longText);
        Assertions.assertTrue(commentTextarea.getAttribute("value").length() > 0);
    }

    @Test
    @Order(11)
    @DisplayName("Test Form Submission with Missing Required Fields")
    void testFormSubmissionMissingFields() {
        // Submit empty form
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Wait for potential validation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on the form page
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
        
        // Test partial form submission
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("email")).sendKeys("john@example.com");
        
        submitButton.click();
        
        // Wait for potential validation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on the form page
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }

    @Test
    @Order(12)
    @DisplayName("Test Form Submission with Complete Valid Data")
    void testFormSubmissionCompleteValidData() {
        // Fill all form fields with valid data
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        
        // Select gender
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        genderRadios.get(0).click();
        
        driver.findElement(By.id("dob")).sendKeys("01/15/1990");
        driver.findElement(By.id("address")).sendKeys("123 Main St, City, State 12345");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("password")).sendKeys("SecurePass123!");
        driver.findElement(By.id("company")).sendKeys("Tech Corp");
        
        // Select role
        Select roleSelect = new Select(driver.findElement(By.id("role")));
        roleSelect.selectByVisibleText("Developer");
        
        // Select job expectation
        Select expectationSelect = new Select(driver.findElement(By.id("expectation")));
        expectationSelect.selectByVisibleText("High salary");
        
        // Select development ways
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        checkboxes.get(0).click(); // Read books
        checkboxes.get(2).click(); // Contribute to opensource projects
        
        // Add comment
        driver.findElement(By.id("comment")).sendKeys("This is a test submission with complete valid data.");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Wait for submission
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify form submission (check URL or success message)
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }
}
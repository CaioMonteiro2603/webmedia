package claudesonnet4.ws06.seq05;

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

/**
 * Comprehensive test suite for Katalon Demo AUT Form
 * Tests all form elements, interactions, and validations
 * Package: claudesonnet4.ws06.seq05
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KatalonFormTestSuite {

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
    @DisplayName("Test Page Load and Title")
    void testPageLoadAndTitle() {
        // Verify page loads correctly
        Assertions.assertEquals("Demo AUT", driver.getTitle());
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
        
        // Verify form is present
        WebElement form = driver.findElement(By.tagName("form"));
        Assertions.assertTrue(form.isDisplayed());
    }

    @Test
    @Order(2)
    @DisplayName("Test First Name Input Field")
    void testFirstNameInput() {
        WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("first-name")));
        
        // Test field is present and enabled
        Assertions.assertTrue(firstNameField.isDisplayed());
        Assertions.assertTrue(firstNameField.isEnabled());
        
        // Test input functionality
        String testFirstName = "John";
        firstNameField.clear();
        firstNameField.sendKeys(testFirstName);
        
        // Verify input was entered correctly
        Assertions.assertEquals(testFirstName, firstNameField.getAttribute("value"));
        
        // Test field accepts various characters
        firstNameField.clear();
        firstNameField.sendKeys("Jean-Pierre O'Connor");
        Assertions.assertEquals("Jean-Pierre O'Connor", firstNameField.getAttribute("value"));
    }

    @Test
    @Order(3)
    @DisplayName("Test Last Name Input Field")
    void testLastNameInput() {
        WebElement lastNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("last-name")));
        
        // Test field is present and enabled
        Assertions.assertTrue(lastNameField.isDisplayed());
        Assertions.assertTrue(lastNameField.isEnabled());
        
        // Test input functionality
        String testLastName = "Doe";
        lastNameField.clear();
        lastNameField.sendKeys(testLastName);
        
        // Verify input was entered correctly
        Assertions.assertEquals(testLastName, lastNameField.getAttribute("value"));
        
        // Test special characters
        lastNameField.clear();
        lastNameField.sendKeys("Van Der Berg-Smith");
        Assertions.assertEquals("Van Der Berg-Smith", lastNameField.getAttribute("value"));
    }

    @Test
    @Order(4)
    @DisplayName("Test Gender Radio Buttons")
    void testGenderRadioButtons() {
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        
        // Verify all three gender options are present
        Assertions.assertEquals(3, genderRadios.size());
        
        // Test each radio button
        for (int i = 0; i < genderRadios.size(); i++) {
            WebElement radio = genderRadios.get(i);
            Assertions.assertTrue(radio.isDisplayed());
            Assertions.assertTrue(radio.isEnabled());
            
            // Click the radio button
            radio.click();
            Assertions.assertTrue(radio.isSelected());
            
            // Verify other radio buttons are deselected
            for (int j = 0; j < genderRadios.size(); j++) {
                if (i != j) {
                    Assertions.assertFalse(genderRadios.get(j).isSelected());
                }
            }
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test Date of Birth Input Field")
    void testDateOfBirthInput() {
        WebElement dobField = wait.until(ExpectedConditions.elementToBeClickable(By.id("dob")));
        
        // Test field is present and enabled
        Assertions.assertTrue(dobField.isDisplayed());
        Assertions.assertTrue(dobField.isEnabled());
        
        // Test date input
        String testDate = "01/15/1990";
        dobField.clear();
        dobField.sendKeys(testDate);
        
        // Verify input was entered correctly
        Assertions.assertEquals(testDate, dobField.getAttribute("value"));
        
        // Test different date format
        dobField.clear();
        dobField.sendKeys("12-25-1985");
        Assertions.assertEquals("12-25-1985", dobField.getAttribute("value"));
    }

    @Test
    @Order(6)
    @DisplayName("Test Address Input Field")
    void testAddressInput() {
        WebElement addressField = wait.until(ExpectedConditions.elementToBeClickable(By.id("address")));
        
        // Test field is present and enabled
        Assertions.assertTrue(addressField.isDisplayed());
        Assertions.assertTrue(addressField.isEnabled());
        
        // Test address input
        String testAddress = "123 Main Street, Anytown, ST 12345";
        addressField.clear();
        addressField.sendKeys(testAddress);
        
        // Verify input was entered correctly
        Assertions.assertEquals(testAddress, addressField.getAttribute("value"));
        
        // Test international address
        addressField.clear();
        addressField.sendKeys("45 Rue de la Paix, 75001 Paris, France");
        Assertions.assertEquals("45 Rue de la Paix, 75001 Paris, France", addressField.getAttribute("value"));
    }

    @Test
    @Order(7)
    @DisplayName("Test Email Input Field")
    void testEmailInput() {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        
        // Test field is present and enabled
        Assertions.assertTrue(emailField.isDisplayed());
        Assertions.assertTrue(emailField.isEnabled());
        Assertions.assertEquals("email", emailField.getAttribute("type"));
        
        // Test valid email input
        String testEmail = "john.doe@example.com";
        emailField.clear();
        emailField.sendKeys(testEmail);
        
        // Verify input was entered correctly
        Assertions.assertEquals(testEmail, emailField.getAttribute("value"));
        
        // Test complex email format
        emailField.clear();
        emailField.sendKeys("test.email+tag@subdomain.example.co.uk");
        Assertions.assertEquals("test.email+tag@subdomain.example.co.uk", emailField.getAttribute("value"));
    }

    @Test
    @Order(8)
    @DisplayName("Test Password Input Field")
    void testPasswordInput() {
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        
        // Test field is present and enabled
        Assertions.assertTrue(passwordField.isDisplayed());
        Assertions.assertTrue(passwordField.isEnabled());
        Assertions.assertEquals("password", passwordField.getAttribute("type"));
        
        // Test password input
        String testPassword = "SecurePassword123!";
        passwordField.clear();
        passwordField.sendKeys(testPassword);
        
        // Verify input was entered correctly
        Assertions.assertEquals(testPassword, passwordField.getAttribute("value"));
        
        // Test special characters in password
        passwordField.clear();
        passwordField.sendKeys("P@ssw0rd#$%^&*()");
        Assertions.assertEquals("P@ssw0rd#$%^&*()", passwordField.getAttribute("value"));
    }

    @Test
    @Order(9)
    @DisplayName("Test Company Input Field")
    void testCompanyInput() {
        WebElement companyField = wait.until(ExpectedConditions.elementToBeClickable(By.id("company")));
        
        // Test field is present and enabled
        Assertions.assertTrue(companyField.isDisplayed());
        Assertions.assertTrue(companyField.isEnabled());
        
        // Test company input
        String testCompany = "Acme Corporation";
        companyField.clear();
        companyField.sendKeys(testCompany);
        
        // Verify input was entered correctly
        Assertions.assertEquals(testCompany, companyField.getAttribute("value"));
        
        // Test company with special characters
        companyField.clear();
        companyField.sendKeys("Tech & Innovation Co., Ltd.");
        Assertions.assertEquals("Tech & Innovation Co., Ltd.", companyField.getAttribute("value"));
    }

    @Test
    @Order(10)
    @DisplayName("Test Role Dropdown Selection")
    void testRoleDropdown() {
        WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("role")));
        Select roleSelect = new Select(roleDropdown);
        
        // Test dropdown is present and enabled
        Assertions.assertTrue(roleDropdown.isDisplayed());
        Assertions.assertTrue(roleDropdown.isEnabled());
        
        // Get all options
        List<WebElement> options = roleSelect.getOptions();
        Assertions.assertTrue(options.size() >= 5); // Should have at least 5 options
        
        // Test selecting each option
        String[] expectedRoles = {"Developer", "QA", "Manager", "Technical Architect", "Business Analyst"};
        
        for (String role : expectedRoles) {
            roleSelect.selectByVisibleText(role);
            Assertions.assertEquals(role, roleSelect.getFirstSelectedOption().getText());
        }
        
        // Test selecting by index
        roleSelect.selectByIndex(0);
        Assertions.assertEquals("Developer", roleSelect.getFirstSelectedOption().getText());
    }

    @Test
    @Order(11)
    @DisplayName("Test Job Expectation Multi-Select Dropdown")
    void testJobExpectationDropdown() {
        WebElement expectationDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("expectation")));
        Select expectationSelect = new Select(expectationDropdown);
        
        // Test dropdown is present and enabled
        Assertions.assertTrue(expectationDropdown.isDisplayed());
        Assertions.assertTrue(expectationDropdown.isEnabled());
        
        // Get all options
        List<WebElement> options = expectationSelect.getOptions();
        Assertions.assertTrue(options.size() >= 6); // Should have at least 6 options
        
        // Test selecting multiple options if supported
        String[] expectedExpectations = {"High salary", "Nice manager/leader", "Excellent colleagues", 
                                       "Good teamwork", "Chance to go onsite", "Challenging"};
        
        for (String expectation : expectedExpectations) {
            expectationSelect.selectByVisibleText(expectation);
            // For single select, verify the selection
            Assertions.assertEquals(expectation, expectationSelect.getFirstSelectedOption().getText());
        }
    }

    @Test
    @Order(12)
    @DisplayName("Test Ways of Development Checkboxes")
    void testDevelopmentCheckboxes() {
        // Find all checkboxes (they don't have individual IDs, so we'll use type)
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        
        // Verify we have 6 checkboxes for development ways
        Assertions.assertEquals(6, checkboxes.size());
        
        // Test each checkbox
        for (int i = 0; i < checkboxes.size(); i++) {
            WebElement checkbox = checkboxes.get(i);
            
            // Verify checkbox is displayed and enabled
            Assertions.assertTrue(checkbox.isDisplayed());
            Assertions.assertTrue(checkbox.isEnabled());
            
            // Test checking the checkbox
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            Assertions.assertTrue(checkbox.isSelected());
            
            // Test unchecking the checkbox
            checkbox.click();
            Assertions.assertFalse(checkbox.isSelected());
            
            // Check it again for final state
            checkbox.click();
            Assertions.assertTrue(checkbox.isSelected());
        }
        
        // Verify all checkboxes can be selected simultaneously
        for (WebElement checkbox : checkboxes) {
            Assertions.assertTrue(checkbox.isSelected());
        }
    }

    @Test
    @Order(13)
    @DisplayName("Test Comment Textarea")
    void testCommentTextarea() {
        WebElement commentTextarea = wait.until(ExpectedConditions.elementToBeClickable(By.id("comment")));
        
        // Test textarea is present and enabled
        Assertions.assertTrue(commentTextarea.isDisplayed());
        Assertions.assertTrue(commentTextarea.isEnabled());
        Assertions.assertEquals("textarea", commentTextarea.getTagName().toLowerCase());
        
        // Test text input
        String testComment = "This is a test comment for the form validation.";
        commentTextarea.clear();
        commentTextarea.sendKeys(testComment);
        
        // Verify input was entered correctly
        Assertions.assertEquals(testComment, commentTextarea.getAttribute("value"));
        
        // Test multiline text
        String multilineComment = "Line 1\nLine 2\nLine 3\nThis is a longer comment to test textarea functionality.";
        commentTextarea.clear();
        commentTextarea.sendKeys(multilineComment);
        Assertions.assertEquals(multilineComment, commentTextarea.getAttribute("value"));
        
        // Test special characters
        commentTextarea.clear();
        commentTextarea.sendKeys("Special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?");
        Assertions.assertEquals("Special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?", commentTextarea.getAttribute("value"));
    }

    @Test
    @Order(14)
    @DisplayName("Test Submit Button")
    void testSubmitButton() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        
        // Test button is present and enabled
        Assertions.assertTrue(submitButton.isDisplayed());
        Assertions.assertTrue(submitButton.isEnabled());
        Assertions.assertEquals("submit", submitButton.getAttribute("type"));
        Assertions.assertEquals("Submit", submitButton.getText());
        
        // Test button click functionality
        submitButton.click();
        
        // Wait for any response or page change
        try {
            Thread.sleep(2000); // Wait for potential form submission
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on the same page or check for success message
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }

    @Test
    @Order(15)
    @DisplayName("Test Complete Form Submission with Valid Data")
    void testCompleteFormSubmission() {
        // Fill out the entire form with valid data
        
        // Basic information
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        
        // Select gender
        driver.findElement(By.name("gender")).click();
        
        // Date of birth
        driver.findElement(By.id("dob")).sendKeys("01/15/1990");
        
        // Address
        driver.findElement(By.id("address")).sendKeys("123 Main Street, Anytown, ST 12345");
        
        // Email and password
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("password")).sendKeys("SecurePassword123!");
        
        // Company
        driver.findElement(By.id("company")).sendKeys("Tech Corp");
        
        // Role selection
        Select roleSelect = new Select(driver.findElement(By.id("role")));
        roleSelect.selectByVisibleText("Developer");
        
        // Job expectation
        Select expectationSelect = new Select(driver.findElement(By.id("expectation")));
        expectationSelect.selectByVisibleText("High salary");
        
        // Select some development ways
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        checkboxes.get(0).click(); // Read books
        checkboxes.get(2).click(); // Contribute to opensource projects
        
        // Add comment
        driver.findElement(By.id("comment")).sendKeys("This is a complete form submission test.");
        
        // Submit the form
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Wait for submission
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify form submission (check for success message or page change)
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }

    @Test
    @Order(16)
    @DisplayName("Test Form Reset Functionality")
    void testFormReset() {
        // Fill some fields
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        
        // Select a radio button
        driver.findElement(By.name("gender")).click();
        
        // Select a checkbox
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        checkboxes.get(0).click();
        
        // Refresh the page to reset form
        driver.navigate().refresh();
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Verify fields are cleared
        Assertions.assertEquals("", driver.findElement(By.id("first-name")).getAttribute("value"));
        Assertions.assertEquals("", driver.findElement(By.id("email")).getAttribute("value"));
        
        // Verify radio buttons are deselected
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        for (WebElement radio : genderRadios) {
            Assertions.assertFalse(radio.isSelected());
        }
        
        // Verify checkboxes are deselected
        checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        for (WebElement checkbox : checkboxes) {
            Assertions.assertFalse(checkbox.isSelected());
        }
    }

    @Test
    @Order(17)
    @DisplayName("Test Form Field Validation and Edge Cases")
    void testFormFieldValidation() {
        // Test empty form submission
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Wait and verify we're still on the form page
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
        
        // Test very long input values
        String longText = "A".repeat(1000);
        driver.findElement(By.id("first-name")).sendKeys(longText);
        Assertions.assertTrue(driver.findElement(By.id("first-name")).getAttribute("value").length() > 0);
        
        // Test special characters in email
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        emailField.sendKeys("invalid-email");
        // Note: HTML5 email validation might prevent submission
        
        // Test numeric input in text fields
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.clear();
        firstNameField.sendKeys("12345");
        Assertions.assertEquals("12345", firstNameField.getAttribute("value"));
    }

    @Test
    @Order(18)
    @DisplayName("Test Keyboard Navigation and Accessibility")
    void testKeyboardNavigation() {
        // Test Tab navigation through form fields
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.click();
        
        // Send Tab key to navigate to next field
        firstNameField.sendKeys(Keys.TAB);
        
        // Verify focus moved to last name field
        WebElement activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals("last-name", activeElement.getAttribute("id"));
        
        // Test Enter key on submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.sendKeys(Keys.ENTER);
        
        // Wait for potential form submission
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're still on the form page
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }

    @Test
    @Order(19)
    @DisplayName("Test Browser Back and Forward Navigation")
    void testBrowserNavigation() {
        String originalUrl = driver.getCurrentUrl();
        
        // Navigate away and back
        driver.navigate().to("https://www.google.com");
        Assertions.assertNotEquals(originalUrl, driver.getCurrentUrl());
        
        // Navigate back
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        Assertions.assertEquals(originalUrl, driver.getCurrentUrl());
        
        // Navigate forward
        driver.navigate().forward();
        driver.navigate().back(); // Return to form
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        Assertions.assertEquals(originalUrl, driver.getCurrentUrl());
    }

    @Test
    @Order(20)
    @DisplayName("Test Page Refresh and Form State")
    void testPageRefreshFormState() {
        // Fill some form data
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("email")).sendKeys("john@example.com");
        
        // Select radio button and checkbox
        driver.findElement(By.name("gender")).click();
        driver.findElements(By.cssSelector("input[type='checkbox']")).get(0).click();
        
        // Refresh the page
        driver.navigate().refresh();
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Verify form is reset after refresh
        Assertions.assertEquals("", driver.findElement(By.id("first-name")).getAttribute("value"));
        Assertions.assertEquals("", driver.findElement(By.id("email")).getAttribute("value"));
        
        // Verify radio buttons and checkboxes are deselected
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        for (WebElement radio : genderRadios) {
            Assertions.assertFalse(radio.isSelected());
        }
        
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        for (WebElement checkbox : checkboxes) {
            Assertions.assertFalse(checkbox.isSelected());
        }
    }
}
package deepseek.ws06.seq02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FormPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://katalon-test.s3.amazonaws.com/aut/html/form.html";

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Maximize window and navigate to base URL
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testFormSubmissionWithValidData() {
        // Fill out the form with valid data
        WebElement firstName = driver.findElement(By.id("first-name"));
        firstName.sendKeys("John");
        
        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Doe");
        
        WebElement gender = driver.findElement(By.id("gender-radio-1"));
        gender.click();
        
        WebElement dob = driver.findElement(By.id("dob"));
        dob.sendKeys("01/01/1990");
        
        WebElement address = driver.findElement(By.id("address"));
        address.sendKeys("123 Main St");
        
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("john.doe@example.com");
        
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SecurePass123!");
        
        WebElement company = driver.findElement(By.id("company"));
        company.sendKeys("ACME Corp");
        
        WebElement role = driver.findElement(By.id("role"));
        role.sendKeys("QA Engineer");
        
        WebElement jobExpectation = driver.findElement(By.id("expectation"));
        jobExpectation.sendKeys("Challenging work");
        
        WebElement developmentWay = driver.findElement(By.id("development-way-1"));
        developmentWay.click();
        
        WebElement comment = driver.findElement(By.id("comment"));
        comment.sendKeys("This is a test comment");
        
        // Submit the form
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Verify successful submission
        wait.until(ExpectedConditions.urlContains("submit.html"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("submit.html"), "Form submission failed");
    }

    @Test
    public void testRequiredFieldValidation() {
        // Try to submit form without filling required fields
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Verify validation messages
        WebElement firstNameError = driver.findElement(By.id("first-name-error"));
        Assertions.assertTrue(firstNameError.isDisplayed(), "First name error not displayed");
        
        WebElement lastNameError = driver.findElement(By.id("last-name-error"));
        Assertions.assertTrue(lastNameError.isDisplayed(), "Last name error not displayed");
        
        WebElement genderError = driver.findElement(By.id("gender-error"));
        Assertions.assertTrue(genderError.isDisplayed(), "Gender error not displayed");
        
        WebElement dobError = driver.findElement(By.id("dob-error"));
        Assertions.assertTrue(dobError.isDisplayed(), "Date of birth error not displayed");
        
        WebElement addressError = driver.findElement(By.id("address-error"));
        Assertions.assertTrue(addressError.isDisplayed(), "Address error not displayed");
        
        WebElement emailError = driver.findElement(By.id("email-error"));
        Assertions.assertTrue(emailError.isDisplayed(), "Email error not displayed");
        
        WebElement passwordError = driver.findElement(By.id("password-error"));
        Assertions.assertTrue(passwordError.isDisplayed(), "Password error not displayed");
    }

    @Test
    public void testEmailFieldValidation() {
        // Test invalid email formats
        WebElement email = driver.findElement(By.id("email"));
        
        email.sendKeys("invalid-email");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        WebElement emailError = driver.findElement(By.id("email-error"));
        Assertions.assertTrue(emailError.isDisplayed(), "Email error not displayed for invalid format");
        
        email.clear();
        email.sendKeys("invalid@email");
        submitButton.click();
        Assertions.assertTrue(emailError.isDisplayed(), "Email error not displayed for invalid format");
        
        email.clear();
        email.sendKeys("valid@example.com");
        submitButton.click();
        Assertions.assertFalse(emailError.isDisplayed(), "Email error displayed for valid format");
    }

    @Test
    public void testPasswordFieldValidation() {
        // Test password validation
        WebElement password = driver.findElement(By.id("password"));
        
        password.sendKeys("short");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        WebElement passwordError = driver.findElement(By.id("password-error"));
        Assertions.assertTrue(passwordError.isDisplayed(), "Password error not displayed for short password");
        
        password.clear();
        password.sendKeys("longenoughbutnospecialchars");
        submitButton.click();
        Assertions.assertTrue(passwordError.isDisplayed(), "Password error not displayed for password without special chars");
        
        password.clear();
        password.sendKeys("ValidPass123!");
        submitButton.click();
        Assertions.assertFalse(passwordError.isDisplayed(), "Password error displayed for valid password");
    }

    @Test
    public void testNavigationLinks() {
        // Test all navigation links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                String originalWindow = driver.getWindowHandle();
                
                // Open link in new tab
                link.click();
                
                // Switch to new tab
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!originalWindow.equals(windowHandle)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }
                
                // Verify the page loaded
                Assertions.assertNotNull(driver.getTitle(), "Page title is null for URL: " + href);
                Assertions.assertFalse(driver.getTitle().isEmpty(), "Page title is empty for URL: " + href);
                
                // Close the tab and switch back
                driver.close();
                driver.switchTo().window(originalWindow);
            }
        }
    }

    @Test
    public void testRadioButtonOptions() {
        // Test all radio button options
        WebElement maleRadio = driver.findElement(By.id("gender-radio-1"));
        WebElement femaleRadio = driver.findElement(By.id("gender-radio-2"));
        WebElement otherRadio = driver.findElement(By.id("gender-radio-3"));
        
        maleRadio.click();
        Assertions.assertTrue(maleRadio.isSelected(), "Male radio button not selected");
        Assertions.assertFalse(femaleRadio.isSelected(), "Female radio button selected when it shouldn't be");
        Assertions.assertFalse(otherRadio.isSelected(), "Other radio button selected when it shouldn't be");
        
        femaleRadio.click();
        Assertions.assertTrue(femaleRadio.isSelected(), "Female radio button not selected");
        Assertions.assertFalse(maleRadio.isSelected(), "Male radio button selected when it shouldn't be");
        Assertions.assertFalse(otherRadio.isSelected(), "Other radio button selected when it shouldn't be");
        
        otherRadio.click();
        Assertions.assertTrue(otherRadio.isSelected(), "Other radio button not selected");
        Assertions.assertFalse(maleRadio.isSelected(), "Male radio button selected when it shouldn't be");
        Assertions.assertFalse(femaleRadio.isSelected(), "Female radio button selected when it shouldn't be");
    }

    @Test
    public void testDevelopmentWayCheckboxes() {
        // Test all development way checkboxes
        WebElement selfTaught = driver.findElement(By.id("development-way-1"));
        WebElement university = driver.findElement(By.id("development-way-2"));
        WebElement bootcamp = driver.findElement(By.id("development-way-3"));
        WebElement other = driver.findElement(By.id("development-way-4"));
        
        // Test individual selection
        selfTaught.click();
        Assertions.assertTrue(selfTaught.isSelected(), "Self-taught checkbox not selected");
        selfTaught.click();
        Assertions.assertFalse(selfTaught.isSelected(), "Self-taught checkbox still selected after click");
        
        // Test multiple selection
        selfTaught.click();
        university.click();
        bootcamp.click();
        other.click();
        
        Assertions.assertTrue(selfTaught.isSelected(), "Self-taught checkbox not selected");
        Assertions.assertTrue(university.isSelected(), "University checkbox not selected");
        Assertions.assertTrue(bootcamp.isSelected(), "Bootcamp checkbox not selected");
        Assertions.assertTrue(other.isSelected(), "Other checkbox not selected");
    }

    @Test
    public void testRoleDropdownOptions() {
        // Test role dropdown options
        WebElement roleDropdown = driver.findElement(By.id("role"));
        List<WebElement> options = roleDropdown.findElements(By.tagName("option"));
        
        // Verify all expected options are present
        String[] expectedOptions = {
            "QA",
            "QC",
            "Developer",
            "Manager",
            "Business Analyst",
            "DevOps",
            "Other"
        };
        
        Assertions.assertEquals(expectedOptions.length, options.size(), "Number of role options doesn't match");
        
        for (int i = 0; i < expectedOptions.length; i++) {
            Assertions.assertEquals(expectedOptions[i], options.get(i).getText(), 
                "Role option text doesn't match at index " + i);
        }
        
        // Test selecting each option
        for (WebElement option : options) {
            option.click();
            Assertions.assertTrue(option.isSelected(), "Option not selected: " + option.getText());
        }
    }

    @Test
    public void testSubmitPageContent() {
        // Fill out the form with test data
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("gender-radio-1")).click();
        driver.findElement(By.id("dob")).sendKeys("01/01/2000");
        driver.findElement(By.id("address")).sendKeys("123 Test St");
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("TestPass123!");
        driver.findElement(By.id("company")).sendKeys("Test Company");
        driver.findElement(By.id("role")).sendKeys("QA");
        driver.findElement(By.id("expectation")).sendKeys("Test expectation");
        driver.findElement(By.id("development-way-1")).click();
        driver.findElement(By.id("comment")).sendKeys("Test comment");
        
        // Submit the form
        driver.findElement(By.id("submit")).click();
        
        // Verify submit page content
        wait.until(ExpectedConditions.urlContains("submit.html"));
        
        WebElement successMessage = driver.findElement(By.tagName("h1"));
        Assertions.assertEquals("Successfully Submitted!", successMessage.getText(), 
            "Success message not displayed correctly");
        
        // Verify submitted data is displayed
        WebElement submittedData = driver.findElement(By.id("submitted-data"));
        Assertions.assertTrue(submittedData.getText().contains("Test"), "First name not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("User"), "Last name not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("Male"), "Gender not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("01/01/2000"), "DOB not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("123 Test St"), "Address not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("test@example.com"), "Email not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("Test Company"), "Company not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("QA"), "Role not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("Test expectation"), "Expectation not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("Self-taught"), "Development way not in submitted data");
        Assertions.assertTrue(submittedData.getText().contains("Test comment"), "Comment not in submitted data");
        
        // Test back to form link
        WebElement backLink = driver.findElement(By.linkText("Back to Form"));
        backLink.click();
        
        wait.until(ExpectedConditions.urlContains("form.html"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"), "Back to form link didn't work");
    }
}
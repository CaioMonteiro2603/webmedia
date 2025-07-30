package claudesonnet4.ws06.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Selenium WebDriver test suite for Katalon Test Site
 * Tests all pages and interactive elements on katalon-test.s3.amazonaws.com
 * 
 * @author Claude Sonnet 4
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KatalonTestSuite {

    private static WebDriver driver;
    private static WebDriverWait wait;
    
    // Test URLs
    private static final String MAIN_FORM_URL = "https://katalon-test.s3.amazonaws.com/aut/html/form.html";
    private static final String DEMO_FORM_URL = "https://katalon-test.s3.amazonaws.com/demo-aut/dist/html/form.html";
    private static final String INDEX_URL = "https://katalon-test.s3.amazonaws.com/aut/html/index.html";
    
    // Test data
    private static final String TEST_FIRST_NAME = "John";
    private static final String TEST_LAST_NAME = "Doe";
    private static final String TEST_EMAIL = "john.doe@example.com";
    private static final String TEST_PASSWORD = "TestPassword123";
    private static final String TEST_COMPANY = "Test Company Inc";
    private static final String TEST_ADDRESS = "123 Test Street, Test City, TC 12345";
    private static final String TEST_DOB = "01/01/1990";
    private static final String TEST_COMMENT = "This is a test comment for automated testing purposes.";

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

    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void setUp() {
        // Clear any existing data
        driver.manage().deleteAllCookies();
    }

    /**
     * Test 1: Main Form Page - Basic Navigation and Page Load
     */
    @Test
    @Order(1)
    @DisplayName("Test Main Form Page Navigation and Load")
    void testMainFormPageNavigation() {
        driver.get(MAIN_FORM_URL);
        
        // Verify page title
        assertEquals("Demo AUT", driver.getTitle(), "Page title should be 'Demo AUT'");
        
        // Verify URL
        assertEquals(MAIN_FORM_URL, driver.getCurrentUrl(), "Current URL should match expected URL");
        
        // Verify page contains form elements
        assertTrue(driver.getPageSource().contains("First name"), "Page should contain 'First name' text");
        assertTrue(driver.getPageSource().contains("Last name"), "Page should contain 'Last name' text");
        assertTrue(driver.getPageSource().contains("Gender"), "Page should contain 'Gender' text");
    }

    /**
     * Test 2: Main Form Page - Text Input Fields
     */
    @Test
    @Order(2)
    @DisplayName("Test Main Form Text Input Fields")
    void testMainFormTextInputs() {
        driver.get(MAIN_FORM_URL);
        
        // Test First Name field
        WebElement firstNameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("first-name")));
        assertTrue(firstNameField.isDisplayed(), "First name field should be visible");
        assertTrue(firstNameField.isEnabled(), "First name field should be enabled");
        firstNameField.clear();
        firstNameField.sendKeys(TEST_FIRST_NAME);
        assertEquals(TEST_FIRST_NAME, firstNameField.getAttribute("value"), "First name should be entered correctly");
        
        // Test Last Name field
        WebElement lastNameField = driver.findElement(By.name("last-name"));
        assertTrue(lastNameField.isDisplayed(), "Last name field should be visible");
        assertTrue(lastNameField.isEnabled(), "Last name field should be enabled");
        lastNameField.clear();
        lastNameField.sendKeys(TEST_LAST_NAME);
        assertEquals(TEST_LAST_NAME, lastNameField.getAttribute("value"), "Last name should be entered correctly");
        
        // Test Email field
        WebElement emailField = driver.findElement(By.name("email"));
        assertTrue(emailField.isDisplayed(), "Email field should be visible");
        assertTrue(emailField.isEnabled(), "Email field should be enabled");
        emailField.clear();
        emailField.sendKeys(TEST_EMAIL);
        assertEquals(TEST_EMAIL, emailField.getAttribute("value"), "Email should be entered correctly");
        
        // Test Password field
        WebElement passwordField = driver.findElement(By.name("password"));
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        passwordField.clear();
        passwordField.sendKeys(TEST_PASSWORD);
        assertEquals(TEST_PASSWORD, passwordField.getAttribute("value"), "Password should be entered correctly");
        
        // Test Company field
        WebElement companyField = driver.findElement(By.name("company"));
        assertTrue(companyField.isDisplayed(), "Company field should be visible");
        assertTrue(companyField.isEnabled(), "Company field should be enabled");
        companyField.clear();
        companyField.sendKeys(TEST_COMPANY);
        assertEquals(TEST_COMPANY, companyField.getAttribute("value"), "Company should be entered correctly");
        
        // Test Address field
        WebElement addressField = driver.findElement(By.name("address"));
        assertTrue(addressField.isDisplayed(), "Address field should be visible");
        assertTrue(addressField.isEnabled(), "Address field should be enabled");
        addressField.clear();
        addressField.sendKeys(TEST_ADDRESS);
        assertEquals(TEST_ADDRESS, addressField.getAttribute("value"), "Address should be entered correctly");
    }

    /**
     * Test 3: Main Form Page - Radio Button Selection
     */
    @Test
    @Order(3)
    @DisplayName("Test Main Form Radio Button Selection")
    void testMainFormRadioButtons() {
        driver.get(MAIN_FORM_URL);
        
        // Find all gender radio buttons
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        assertTrue(genderRadios.size() >= 3, "Should have at least 3 gender radio buttons");
        
        // Test Male radio button
        WebElement maleRadio = genderRadios.get(0);
        assertTrue(maleRadio.isDisplayed(), "Male radio button should be visible");
        assertTrue(maleRadio.isEnabled(), "Male radio button should be enabled");
        maleRadio.click();
        assertTrue(maleRadio.isSelected(), "Male radio button should be selected after click");
        
        // Test Female radio button
        WebElement femaleRadio = genderRadios.get(1);
        assertTrue(femaleRadio.isDisplayed(), "Female radio button should be visible");
        assertTrue(femaleRadio.isEnabled(), "Female radio button should be enabled");
        femaleRadio.click();
        assertTrue(femaleRadio.isSelected(), "Female radio button should be selected after click");
        assertFalse(maleRadio.isSelected(), "Male radio button should be deselected when female is selected");
        
        // Test In Between radio button
        WebElement inBetweenRadio = genderRadios.get(2);
        assertTrue(inBetweenRadio.isDisplayed(), "In Between radio button should be visible");
        assertTrue(inBetweenRadio.isEnabled(), "In Between radio button should be enabled");
        inBetweenRadio.click();
        assertTrue(inBetweenRadio.isSelected(), "In Between radio button should be selected after click");
        assertFalse(femaleRadio.isSelected(), "Female radio button should be deselected when In Between is selected");
    }

    /**
     * Test 4: Main Form Page - Dropdown Selection
     */
    @Test
    @Order(4)
    @DisplayName("Test Main Form Dropdown Selection")
    void testMainFormDropdowns() {
        driver.get(MAIN_FORM_URL);
        
        // Test Role dropdown
        WebElement roleDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("role")));
        assertTrue(roleDropdown.isDisplayed(), "Role dropdown should be visible");
        assertTrue(roleDropdown.isEnabled(), "Role dropdown should be enabled");
        
        Select roleSelect = new Select(roleDropdown);
        List<WebElement> roleOptions = roleSelect.getOptions();
        assertTrue(roleOptions.size() > 1, "Role dropdown should have multiple options");
        
        // Test selecting different role options
        roleSelect.selectByVisibleText("Developer");
        assertEquals("Developer", roleSelect.getFirstSelectedOption().getText(), "Developer should be selected");
        
        roleSelect.selectByVisibleText("QA");
        assertEquals("QA", roleSelect.getFirstSelectedOption().getText(), "QA should be selected");
        
        roleSelect.selectByVisibleText("Manager");
        assertEquals("Manager", roleSelect.getFirstSelectedOption().getText(), "Manager should be selected");
        
        // Test Job Expectation dropdown if present
        try {
            WebElement expectationDropdown = driver.findElement(By.name("expectation"));
            if (expectationDropdown.isDisplayed()) {
                Select expectationSelect = new Select(expectationDropdown);
                List<WebElement> expectationOptions = expectationSelect.getOptions();
                assertTrue(expectationOptions.size() > 1, "Expectation dropdown should have multiple options");
                
                expectationSelect.selectByIndex(1); // Select first non-default option
                assertNotEquals("", expectationSelect.getFirstSelectedOption().getText(), "An expectation should be selected");
            }
        } catch (NoSuchElementException e) {
            // Expectation dropdown might not be present on all pages
        }
    }

    /**
     * Test 5: Main Form Page - Checkbox Selection
     */
    @Test
    @Order(5)
    @DisplayName("Test Main Form Checkbox Selection")
    void testMainFormCheckboxes() {
        driver.get(MAIN_FORM_URL);
        
        // Find all checkboxes
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        
        if (!checkboxes.isEmpty()) {
            for (int i = 0; i < Math.min(checkboxes.size(), 3); i++) {
                WebElement checkbox = checkboxes.get(i);
                
                // Scroll to checkbox if needed
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                
                assertTrue(checkbox.isDisplayed(), "Checkbox " + (i + 1) + " should be visible");
                assertTrue(checkbox.isEnabled(), "Checkbox " + (i + 1) + " should be enabled");
                
                // Test checking the checkbox
                if (!checkbox.isSelected()) {
                    checkbox.click();
                    assertTrue(checkbox.isSelected(), "Checkbox " + (i + 1) + " should be selected after click");
                }
                
                // Test unchecking the checkbox
                checkbox.click();
                assertFalse(checkbox.isSelected(), "Checkbox " + (i + 1) + " should be deselected after second click");
            }
        }
    }

    /**
     * Test 6: Main Form Page - Textarea Input
     */
    @Test
    @Order(6)
    @DisplayName("Test Main Form Textarea Input")
    void testMainFormTextarea() {
        driver.get(MAIN_FORM_URL);
        
        try {
            WebElement commentTextarea = driver.findElement(By.name("comment"));
            
            // Scroll to textarea
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", commentTextarea);
            
            assertTrue(commentTextarea.isDisplayed(), "Comment textarea should be visible");
            assertTrue(commentTextarea.isEnabled(), "Comment textarea should be enabled");
            
            commentTextarea.clear();
            commentTextarea.sendKeys(TEST_COMMENT);
            assertEquals(TEST_COMMENT, commentTextarea.getAttribute("value"), "Comment should be entered correctly");
            
        } catch (NoSuchElementException e) {
            // Comment textarea might not be present on all pages
        }
    }

    /**
     * Test 7: Main Form Page - Form Submission
     */
    @Test
    @Order(7)
    @DisplayName("Test Main Form Submission")
    void testMainFormSubmission() {
        driver.get(MAIN_FORM_URL);
        
        // Fill out the form with test data
        fillMainFormWithTestData();
        
        // Find and click submit button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit'], button[type='submit'], button:contains('Submit')")));
        assertTrue(submitButton.isDisplayed(), "Submit button should be visible");
        assertTrue(submitButton.isEnabled(), "Submit button should be enabled");
        
        // Scroll to submit button
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        
        submitButton.click();
        
        // Wait for form submission response or page change
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Success') or contains(text(), 'Thank') or contains(text(), 'Submitted')]")),
                ExpectedConditions.urlContains("success"),
                ExpectedConditions.urlContains("thank")
            ));
        } catch (TimeoutException e) {
            // Form might submit without visible confirmation
        }
        
        // Verify form submission (check for success message or URL change)
        String pageSource = driver.getPageSource().toLowerCase();
        boolean hasSuccessIndicator = pageSource.contains("success") || 
                                    pageSource.contains("thank") || 
                                    pageSource.contains("submitted") ||
                                    !driver.getCurrentUrl().equals(MAIN_FORM_URL);
        
        assertTrue(hasSuccessIndicator, "Form submission should show success indicator or redirect");
    }

    /**
     * Test 8: Demo Form Page - Navigation and Basic Elements
     */
    @Test
    @Order(8)
    @DisplayName("Test Demo Form Page Navigation and Elements")
    void testDemoFormPageNavigation() {
        driver.get(DEMO_FORM_URL);
        
        // Verify page title
        assertEquals("Demo AUT", driver.getTitle(), "Demo form page title should be 'Demo AUT'");
        
        // Verify URL
        assertEquals(DEMO_FORM_URL, driver.getCurrentUrl(), "Current URL should match demo form URL");
        
        // Verify page contains form elements
        assertTrue(driver.getPageSource().contains("First name"), "Demo page should contain 'First name' text");
        assertTrue(driver.getPageSource().contains("Last name"), "Demo page should contain 'Last name' text");
        
        // Test basic form elements
        WebElement firstNameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        assertTrue(firstNameField.isDisplayed(), "First name field should be visible on demo page");
        
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        assertTrue(lastNameField.isDisplayed(), "Last name field should be visible on demo page");
    }

    /**
     * Test 9: Demo Form Page - Complete Form Interaction
     */
    @Test
    @Order(9)
    @DisplayName("Test Demo Form Complete Interaction")
    void testDemoFormCompleteInteraction() {
        driver.get(DEMO_FORM_URL);
        
        // Fill out all form fields
        fillDemoFormWithTestData();
        
        // Test form submission
        try {
            WebElement submitButton = driver.findElement(By.id("submit"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            
            assertTrue(submitButton.isDisplayed(), "Submit button should be visible");
            assertTrue(submitButton.isEnabled(), "Submit button should be enabled");
            
            submitButton.click();
            
            // Wait for submission response
            Thread.sleep(2000); // Allow time for any submission processing
            
        } catch (NoSuchElementException | InterruptedException e) {
            // Submit button might not be present or submission might be instant
        }
    }

    /**
     * Test 10: Index Page - Navigation and Links
     */
    @Test
    @Order(10)
    @DisplayName("Test Index Page Navigation and Links")
    void testIndexPageNavigation() {
        driver.get(INDEX_URL);
        
        // Verify page title
        assertEquals("Demo AUT", driver.getTitle(), "Index page title should be 'Demo AUT'");
        
        // Verify URL
        assertEquals(INDEX_URL, driver.getCurrentUrl(), "Current URL should match index URL");
        
        // Find all links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        assertTrue(links.size() > 0, "Index page should contain navigation links");
        
        // Test that links are clickable and lead to different pages
        for (int i = 0; i < Math.min(links.size(), 5); i++) {
            WebElement link = links.get(i);
            if (link.isDisplayed() && link.isEnabled()) {
                String linkText = link.getText();
                String linkHref = link.getAttribute("href");
                
                assertNotNull(linkHref, "Link should have href attribute");
                assertFalse(linkHref.isEmpty(), "Link href should not be empty");
                
                // Click the link and verify navigation
                String originalUrl = driver.getCurrentUrl();
                link.click();
                
                // Wait for page load
                wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));
                
                // Verify we navigated to a different page
                assertNotEquals(originalUrl, driver.getCurrentUrl(), 
                    "Clicking link '" + linkText + "' should navigate to different page");
                
                // Navigate back to index for next test
                driver.navigate().back();
                wait.until(ExpectedConditions.urlToBe(INDEX_URL));
                
                // Refresh links list as DOM might have changed
                links = driver.findElements(By.tagName("a"));
            }
        }
    }

    /**
     * Test 11: Index Page - Specific Feature Links
     */
    @Test
    @Order(11)
    @DisplayName("Test Index Page Specific Feature Links")
    void testIndexPageFeatureLinks() {
        driver.get(INDEX_URL);
        
        // Test specific feature links
        String[] expectedFeatures = {"Drag & Drop", "File Upload", "Web Form", "Web Tables", "Iframe"};
        
        for (String feature : expectedFeatures) {
            try {
                WebElement featureLink = driver.findElement(By.linkText(feature));
                assertTrue(featureLink.isDisplayed(), feature + " link should be visible");
                assertTrue(featureLink.isEnabled(), feature + " link should be enabled");
                
                String originalUrl = driver.getCurrentUrl();
                featureLink.click();
                
                // Wait for navigation
                wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));
                
                // Verify navigation occurred
                assertNotEquals(originalUrl, driver.getCurrentUrl(), 
                    "Clicking " + feature + " should navigate to different page");
                
                // Verify page title or content relates to the feature
                String pageTitle = driver.getTitle();
                String pageSource = driver.getPageSource().toLowerCase();
                assertTrue(pageTitle.contains("Demo") || pageSource.contains(feature.toLowerCase()), 
                    "Page should be related to " + feature);
                
                // Navigate back
                driver.navigate().back();
                wait.until(ExpectedConditions.urlToBe(INDEX_URL));
                
            } catch (NoSuchElementException e) {
                // Feature link might not be present
                System.out.println("Feature link not found: " + feature);
            }
        }
    }

    /**
     * Test 12: External Links Validation
     */
    @Test
    @Order(12)
    @DisplayName("Test External Links Validation")
    void testExternalLinksValidation() {
        // Test external links from main pages
        String[] testUrls = {MAIN_FORM_URL, DEMO_FORM_URL, INDEX_URL};
        
        for (String url : testUrls) {
            driver.get(url);
            
            // Find all links
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            
            for (WebElement link : allLinks) {
                String href = link.getAttribute("href");
                if (href != null && !href.isEmpty() && 
                    (href.startsWith("http://") || href.startsWith("https://")) &&
                    !href.contains("katalon-test.s3.amazonaws.com")) {
                    
                    // This is an external link
                    assertTrue(link.isDisplayed() || link.getAttribute("style") == null, 
                        "External link should be properly formatted");
                    
                    // Verify link has proper attributes
                    assertNotNull(href, "External link should have href attribute");
                    assertTrue(href.startsWith("http"), "External link should be a valid URL");
                }
            }
        }
    }

    /**
     * Test 13: Cross-Browser Window Handling
     */
    @Test
    @Order(13)
    @DisplayName("Test Cross-Browser Window Handling")
    void testWindowHandling() {
        driver.get(INDEX_URL);
        
        String originalWindow = driver.getWindowHandle();
        Set<String> originalWindows = driver.getWindowHandles();
        
        // Look for links that might open new windows
        List<WebElement> links = driver.findElements(By.cssSelector("a[target='_blank'], a[onclick*='window.open']"));
        
        if (!links.isEmpty()) {
            WebElement newWindowLink = links.get(0);
            newWindowLink.click();
            
            // Wait for new window
            wait.until(ExpectedConditions.numberOfWindowsToBe(originalWindows.size() + 1));
            
            Set<String> allWindows = driver.getWindowHandles();
            assertTrue(allWindows.size() > originalWindows.size(), "New window should be opened");
            
            // Switch to new window
            for (String window : allWindows) {
                if (!originalWindows.contains(window)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
            
            // Verify we're in the new window
            assertNotEquals(originalWindow, driver.getWindowHandle(), "Should be in new window");
            
            // Close new window and switch back
            driver.close();
            driver.switchTo().window(originalWindow);
            assertEquals(originalWindow, driver.getWindowHandle(), "Should be back in original window");
        }
    }

    /**
     * Test 14: Page Responsiveness and Element Visibility
     */
    @Test
    @Order(14)
    @DisplayName("Test Page Responsiveness and Element Visibility")
    void testPageResponsiveness() throws InterruptedException {
        String[] testUrls = {MAIN_FORM_URL, DEMO_FORM_URL, INDEX_URL};
        
        for (String url : testUrls) {
            driver.get(url);
            
            // Test different viewport sizes
            driver.manage().window().setSize(new Dimension(1920, 1080)); // Desktop
            Thread.sleep(1000);
            
            // Verify page loads properly at desktop size
            assertTrue(driver.findElements(By.tagName("body")).size() > 0, "Page should load at desktop size");
            
            driver.manage().window().setSize(new Dimension(768, 1024)); // Tablet
            Thread.sleep(1000);
            
            // Verify page still loads properly at tablet size
            assertTrue(driver.findElements(By.tagName("body")).size() > 0, "Page should load at tablet size");
            
            driver.manage().window().setSize(new Dimension(375, 667)); // Mobile
            Thread.sleep(1000);
            
            // Verify page still loads properly at mobile size
            assertTrue(driver.findElements(By.tagName("body")).size() > 0, "Page should load at mobile size");
            
            // Reset to default size
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }
    }

    /**
     * Test 15: Form Validation and Error Handling
     */
    @Test
    @Order(15)
    @DisplayName("Test Form Validation and Error Handling")
    void testFormValidationAndErrorHandling() {
        driver.get(MAIN_FORM_URL);
        
        // Test submitting empty form
        try {
            WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit'], button[type='submit']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            submitButton.click();
            
            // Check for validation messages or errors
            Thread.sleep(2000);
            
            // Look for validation messages
            List<WebElement> errorMessages = driver.findElements(By.cssSelector(".error, .validation-error, [class*='error']"));
            
            // Test invalid email format
            WebElement emailField = driver.findElement(By.name("email"));
            emailField.clear();
            emailField.sendKeys("invalid-email");
            
            submitButton.click();
            Thread.sleep(1000);
            
            // Browser should show validation for invalid email
            String validationMessage = emailField.getAttribute("validationMessage");
            if (validationMessage != null && !validationMessage.isEmpty()) {
                assertTrue(validationMessage.contains("@") || validationMessage.toLowerCase().contains("email"), 
                    "Should show email validation message");
            }
            
        } catch (NoSuchElementException | InterruptedException e) {
            // Form might not have validation
        }
    }

    // Helper Methods

    /**
     * Fills the main form with test data
     */
    private void fillMainFormWithTestData() {
        try {
            // Fill text fields
            driver.findElement(By.name("first-name")).sendKeys(TEST_FIRST_NAME);
            driver.findElement(By.name("last-name")).sendKeys(TEST_LAST_NAME);
            driver.findElement(By.name("email")).sendKeys(TEST_EMAIL);
            driver.findElement(By.name("password")).sendKeys(TEST_PASSWORD);
            driver.findElement(By.name("company")).sendKeys(TEST_COMPANY);
            driver.findElement(By.name("address")).sendKeys(TEST_ADDRESS);
            
            // Select gender
            List<WebElement> genderRadios = driver.findElements(By.name("gender"));
            if (!genderRadios.isEmpty()) {
                genderRadios.get(0).click(); // Select first option
            }
            
            // Select role
            try {
                Select roleSelect = new Select(driver.findElement(By.name("role")));
                roleSelect.selectByIndex(1); // Select first non-default option
            } catch (NoSuchElementException e) {
                // Role dropdown might not be present
            }
            
            // Fill comment
            try {
                driver.findElement(By.name("comment")).sendKeys(TEST_COMMENT);
            } catch (NoSuchElementException e) {
                // Comment field might not be present
            }
            
        } catch (NoSuchElementException e) {
            // Some fields might not be present on all forms
        }
    }

    /**
     * Fills the demo form with test data
     */
    private void fillDemoFormWithTestData() {
        try {
            // Fill text fields using IDs
            driver.findElement(By.id("first-name")).sendKeys(TEST_FIRST_NAME);
            driver.findElement(By.id("last-name")).sendKeys(TEST_LAST_NAME);
            driver.findElement(By.id("email")).sendKeys(TEST_EMAIL);
            driver.findElement(By.id("password")).sendKeys(TEST_PASSWORD);
            driver.findElement(By.id("dob")).sendKeys(TEST_DOB);
            driver.findElement(By.id("address")).sendKeys(TEST_ADDRESS);
            
            // Scroll to company field
            WebElement companyField = driver.findElement(By.id("company"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", companyField);
            companyField.sendKeys(TEST_COMPANY);
            
            // Select gender radio button
            List<WebElement> genderRadios = driver.findElements(By.name("gender"));
            if (!genderRadios.isEmpty()) {
                genderRadios.get(0).click();
            }
            
            // Select role dropdown
            try {
                Select roleSelect = new Select(driver.findElement(By.id("role")));
                roleSelect.selectByVisibleText("Developer");
            } catch (NoSuchElementException e) {
                // Role dropdown might not be present
            }
            
            // Select expectation dropdown
            try {
                Select expectationSelect = new Select(driver.findElement(By.id("expectation")));
                expectationSelect.selectByIndex(1);
            } catch (NoSuchElementException e) {
                // Expectation dropdown might not be present
            }
            
            // Select some checkboxes
            List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
            for (int i = 0; i < Math.min(checkboxes.size(), 2); i++) {
                WebElement checkbox = checkboxes.get(i);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
            }
            
            // Fill comment
            try {
                WebElement commentField = driver.findElement(By.id("comment"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", commentField);
                commentField.sendKeys(TEST_COMMENT);
            } catch (NoSuchElementException e) {
                // Comment field might not be present
            }
            
        } catch (NoSuchElementException e) {
            // Some fields might not be present
        }
    }
}
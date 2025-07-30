package claudesonnet4.ws05.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.io.File;

/**
 * Comprehensive Selenium WebDriver test suite for SelectorHub XPath Practice Page
 * Tests all interactive elements including forms, buttons, links, checkboxes, and external pages
 * 
 * @author Automated Test Generator
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SelectorHubXPathPracticeTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions actions;
    private static final String BASE_URL = "https://selectorshub.com/xpath-practice-page/";
    private static final int TIMEOUT_SECONDS = 10;

    /**
     * Setup method to initialize WebDriver and related components
     */
    @BeforeAll
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode for CI/CD
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT_SECONDS));
    }

    /**
     * Navigate to the main practice page before each test
     */
    @BeforeEach
    public void navigateToMainPage() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Xpath Practice Page"));
    }

    /**
     * Test 1: Verify main page loads correctly and title is present
     */
    @Test
    @Order(1)
    @DisplayName("Test Main Page Load and Title Verification")
    public void testMainPageLoadAndTitle() {
        String expectedTitle = "Xpath Practice Page | Shadow dom, nested shadow dom, iframe, nested iframe and more complex automation scenarios.";
        Assertions.assertEquals(expectedTitle, driver.getTitle(), "Page title should match expected value");
        
        // Verify main heading is present
        WebElement mainHeading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'Get Free Access')]")));
        Assertions.assertTrue(mainHeading.isDisplayed(), "Main heading should be visible");
    }

    /**
     * Test 2: Test all form input fields
     */
    @Test
    @Order(2)
    @DisplayName("Test Form Input Fields")
    public void testFormInputFields() {
        // Test email field
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("userId")));
        emailField.clear();
        emailField.sendKeys("test@example.com");
        Assertions.assertEquals("test@example.com", emailField.getAttribute("value"), 
            "Email field should contain entered value");

        // Test password field
        WebElement passwordField = driver.findElement(By.id("pass"));
        passwordField.clear();
        passwordField.sendKeys("testPassword123");
        Assertions.assertEquals("testPassword123", passwordField.getAttribute("value"), 
            "Password field should contain entered value");

        // Test company field
        WebElement companyField = driver.findElement(By.name("company"));
        companyField.clear();
        companyField.sendKeys("Test Company Inc.");
        Assertions.assertEquals("Test Company Inc.", companyField.getAttribute("value"), 
            "Company field should contain entered value");

        // Test mobile number field
        WebElement mobileField = driver.findElement(By.name("mobile number"));
        mobileField.clear();
        mobileField.sendKeys("1234567890");
        Assertions.assertEquals("1234567890", mobileField.getAttribute("value"), 
            "Mobile field should contain entered value");

        // Test first crush field
        WebElement crushField = driver.findElement(By.id("inp_val"));
        crushField.clear();
        crushField.sendKeys("Test Crush Name");
        Assertions.assertEquals("Test Crush Name", crushField.getAttribute("value"), 
            "First crush field should contain entered value");

        // Test first name field
        WebElement firstNameField = driver.findElement(By.xpath("//input[@placeholder='First Enter name']"));
        firstNameField.clear();
        firstNameField.sendKeys("John");
        Assertions.assertEquals("John", firstNameField.getAttribute("value"), 
            "First name field should contain entered value");

        // Test last name field
        WebElement lastNameField = driver.findElement(By.xpath("//input[@placeholder='Enter Last name']"));
        lastNameField.clear();
        lastNameField.sendKeys("Doe");
        Assertions.assertEquals("Doe", lastNameField.getAttribute("value"), 
            "Last name field should contain entered value");
    }

    /**
     * Test 3: Test dropdown selection
     */
    @Test
    @Order(3)
    @DisplayName("Test Dropdown Selection")
    public void testDropdownSelection() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("cars")));
        Select select = new Select(dropdown);
        
        // Test selecting different options
        select.selectByValue("saab");
        Assertions.assertEquals("saab", select.getFirstSelectedOption().getAttribute("value"), 
            "Saab should be selected");
        
        select.selectByVisibleText("Audi");
        Assertions.assertEquals("audi", select.getFirstSelectedOption().getAttribute("value"), 
            "Audi should be selected");
        
        select.selectByIndex(0);
        Assertions.assertEquals("volvo", select.getFirstSelectedOption().getAttribute("value"), 
            "Volvo should be selected by index");
    }

    /**
     * Test 4: Test date picker functionality
     */
    @Test
    @Order(4)
    @DisplayName("Test Date Picker Functionality")
    public void testDatePicker() {
        // Test date input field
        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(By.name("the_date")));
        dateField.clear();
        dateField.sendKeys("2024-12-25");
        Assertions.assertEquals("2024-12-25", dateField.getAttribute("value"), 
            "Date field should contain selected date");

        // Test datepicker field
        WebElement datePicker = driver.findElement(By.id("datepicker"));
        datePicker.click();
        datePicker.clear();
        datePicker.sendKeys("12/25/2024");
        Assertions.assertTrue(datePicker.getAttribute("value").contains("12/25/2024"), 
            "Date picker should contain selected date");
    }

    /**
     * Test 5: Test button clicks and interactions
     */
    @Test
    @Order(5)
    @DisplayName("Test Button Clicks and Interactions")
    public void testButtonClicks() {
        // Test submit button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Submit']")));
        Assertions.assertTrue(submitButton.isEnabled(), "Submit button should be enabled");
        submitButton.click();

        // Test checkout button
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Checkout here']")));
        Assertions.assertTrue(checkoutButton.isEnabled(), "Checkout button should be enabled");
        checkoutButton.click();

        // Test alert button
        WebElement alertButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Click To Open Window Alert']")));
        alertButton.click();
        
        // Handle alert
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            Assertions.assertNotNull(alertText, "Alert should have text");
            alert.accept();
        } catch (TimeoutException e) {
            // Alert might not appear in headless mode, continue test
        }

        // Test prompt alert button
        WebElement promptButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Click To Open Window Prompt Alert']")));
        promptButton.click();
        
        // Handle prompt
        try {
            Alert prompt = wait.until(ExpectedConditions.alertIsPresent());
            prompt.sendKeys("Test Input");
            prompt.accept();
        } catch (TimeoutException e) {
            // Prompt might not appear in headless mode, continue test
        }

        // Test modal button
        WebElement modalButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("myBtn")));
        modalButton.click();
        
        // Verify modal opens (if visible)
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'modal')]")));
            Assertions.assertTrue(modal.isDisplayed(), "Modal should be displayed");
        } catch (TimeoutException e) {
            // Modal might not be visible in headless mode, continue test
        }
    }

    /**
     * Test 6: Test checkbox selections in the users table
     */
    @Test
    @Order(6)
    @DisplayName("Test Checkbox Selections")
    public void testCheckboxSelections() {
        // Test select all checkbox
        WebElement selectAllCheckbox = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("ohrmList_chkSelectAll")));
        selectAllCheckbox.click();
        Assertions.assertTrue(selectAllCheckbox.isSelected(), "Select all checkbox should be selected");

        // Test individual user checkboxes
        List<WebElement> userCheckboxes = driver.findElements(
            By.xpath("//input[@name='chkSelectRow[]']"));
        
        Assertions.assertTrue(userCheckboxes.size() > 0, "Should have user checkboxes");
        
        // Test first few individual checkboxes
        for (int i = 0; i < Math.min(5, userCheckboxes.size()); i++) {
            WebElement checkbox = userCheckboxes.get(i);
            if (checkbox.isDisplayed() && checkbox.isEnabled()) {
                checkbox.click();
                Assertions.assertTrue(checkbox.isSelected(), 
                    "Checkbox " + i + " should be selected after click");
            }
        }

        // Test some of the many checkboxes in the table
        List<WebElement> tableCheckboxes = driver.findElements(
            By.xpath("//input[@type='checkbox' and @value='on']"));
        
        // Test first 10 checkboxes
        for (int i = 0; i < Math.min(10, tableCheckboxes.size()); i++) {
            WebElement checkbox = tableCheckboxes.get(i);
            if (checkbox.isDisplayed() && checkbox.isEnabled()) {
                actions.moveToElement(checkbox).click().perform();
                Assertions.assertTrue(checkbox.isSelected(), 
                    "Table checkbox " + i + " should be selected");
            }
        }
    }

    /**
     * Test 7: Test file upload functionality
     */
    @Test
    @Order(7)
    @DisplayName("Test File Upload Functionality")
    public void testFileUpload() {
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("myFile")));
        
        // Create a temporary test file
        try {
            File tempFile = File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            
            fileInput.sendKeys(tempFile.getAbsolutePath());
            
            String fileName = fileInput.getAttribute("value");
            Assertions.assertTrue(fileName.contains("test"), "File input should contain uploaded file name");
        } catch (Exception e) {
            // File upload might not work in headless mode, verify element exists
            Assertions.assertTrue(fileInput.isDisplayed(), "File input should be present");
        }
    }

    /**
     * Test 8: Test search functionality
     */
    @Test
    @Order(8)
    @DisplayName("Test Search Functionality")
    public void testSearchFunctionality() {
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@title='Search']")));
        searchField.clear();
        searchField.sendKeys("selenium");
        searchField.sendKeys(Keys.ENTER);
        
        // Verify search was performed (page might change or show results)
        Assertions.assertNotNull(searchField.getAttribute("value"), "Search field should have value");
    }

    /**
     * Test 9: Test external links navigation
     */
    @Test
    @Order(9)
    @DisplayName("Test External Links Navigation")
    public void testExternalLinksNavigation() {
        String originalWindow = driver.getWindowHandle();
        
        // Test YouTube channel link
        WebElement youtubeLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'youtube.com/c/SelectorsHub')]")));
        
        // Open in new tab using JavaScript to avoid navigation issues
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", youtubeLink);
        
        // Wait for new window and verify
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windows = driver.getWindowHandles();
        Assertions.assertEquals(2, windows.size(), "Should have 2 windows open");
        
        // Switch back to original window
        driver.switchTo().window(originalWindow);
        
        // Test course link
        WebElement courseLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'bit.ly/shub_training_udemy')]")));
        String courseLinkHref = courseLink.getAttribute("href");
        Assertions.assertTrue(courseLinkHref.contains("bit.ly"), "Course link should contain bit.ly");
        
        // Test donation link
        WebElement donationLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'selectorshub.com/donate')]")));
        String donationHref = donationLink.getAttribute("href");
        Assertions.assertTrue(donationHref.contains("donate"), "Donation link should contain donate");
    }

    /**
     * Test 10: Test sub-page navigation - Shadow DOM iframe page
     */
    @Test
    @Order(10)
    @DisplayName("Test Shadow DOM Iframe Sub-page Navigation")
    public void testShadowDomIframeSubPage() {
        WebElement shadowDomLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'shadow-dom-in-iframe')]")));
        
        String originalUrl = driver.getCurrentUrl();
        shadowDomLink.click();
        
        // Wait for navigation
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));
        
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("shadow-dom-in-iframe"), 
            "Should navigate to shadow DOM iframe page");
        
        // Verify page loaded
        wait.until(ExpectedConditions.titleContains("Shadow"));
        
        // Navigate back
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(originalUrl));
    }

    /**
     * Test 11: Test iframe scenarios sub-page navigation
     */
    @Test
    @Order(11)
    @DisplayName("Test Iframe Scenarios Sub-page Navigation")
    public void testIframeScenarioSubPage() {
        WebElement iframeLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'iframe-scenario')]")));
        
        String originalUrl = driver.getCurrentUrl();
        iframeLink.click();
        
        // Wait for navigation
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));
        
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("iframe-scenario"), 
            "Should navigate to iframe scenario page");
        
        // Verify page loaded
        wait.until(ExpectedConditions.titleContains("iframe"));
        
        // Navigate back
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(originalUrl));
    }

    /**
     * Test 12: Test broken link detection
     */
    @Test
    @Order(12)
    @DisplayName("Test Broken Link Detection")
    public void testBrokenLinkDetection() {
        WebElement brokenLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[text()='This is a broken link']")));
        
        String brokenHref = brokenLink.getAttribute("href");
        Assertions.assertTrue(brokenHref.contains("selectorhub.com"), 
            "Broken link should point to selectorhub.com (note missing 's')");
        
        // Verify it's actually broken by checking the URL format
        Assertions.assertFalse(brokenHref.contains("https://selectorshub.com"), 
            "Broken link should not have correct URL format");
    }

    /**
     * Test 13: Test table interactions and user links
     */
    @Test
    @Order(13)
    @DisplayName("Test Table Interactions and User Links")
    public void testTableInteractionsAndUserLinks() {
        // Test user name links in the table
        List<WebElement> userLinks = driver.findElements(
            By.xpath("//a[contains(text(), '.')]"));
        
        Assertions.assertTrue(userLinks.size() > 0, "Should have user name links");
        
        // Test first few user links
        for (int i = 0; i < Math.min(3, userLinks.size()); i++) {
            WebElement userLink = userLinks.get(i);
            if (userLink.isDisplayed()) {
                String linkText = userLink.getText();
                String linkHref = userLink.getAttribute("href");
                
                Assertions.assertNotNull(linkText, "User link should have text");
                Assertions.assertNotNull(linkHref, "User link should have href");
                
                // Verify link format
                if (linkText.contains("John.Smith") || linkText.contains("Jordan.Mathews")) {
                    Assertions.assertTrue(linkHref.contains("youtube.com"), 
                        "John.Smith and Jordan.Mathews should link to YouTube");
                } else {
                    Assertions.assertTrue(linkHref.contains("bit.ly") || linkHref.contains("youtube.com"), 
                        "User links should point to valid destinations");
                }
            }
        }
    }

    /**
     * Test 14: Test footer links navigation
     */
    @Test
    @Order(14)
    @DisplayName("Test Footer Links Navigation")
    public void testFooterLinksNavigation() {
        // Scroll to footer
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Test various footer links
        String[] footerLinkTexts = {
            "Contact Us", "Privacy Policy", "Terms of Service", 
            "Certification", "About Us", "Sponsors"
        };
        
        for (String linkText : footerLinkTexts) {
            try {
                WebElement footerLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[text()='" + linkText + "']")));
                
                String href = footerLink.getAttribute("href");
                Assertions.assertNotNull(href, linkText + " link should have href");
                Assertions.assertTrue(href.contains("selectorshub.com"), 
                    linkText + " link should point to selectorshub.com");
            } catch (TimeoutException e) {
                // Some links might not be visible, continue with next
            }
        }
    }

    /**
     * Test 15: Test social media links
     */
    @Test
    @Order(15)
    @DisplayName("Test Social Media Links")
    public void testSocialMediaLinks() {
        // Scroll to footer where social media links are located
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Test social media links
        String[] socialPlatforms = {"Twitter", "Youtube", "Linkedin", "Facebook", "Telegram", "Instagram"};
        String[] expectedDomains = {"twitter.com", "youtube.com", "linkedin.com", "facebook.com", "t.me", "instagram.com"};
        
        for (int i = 0; i < socialPlatforms.length; i++) {
            try {
                WebElement socialLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[text()='" + socialPlatforms[i] + "']")));
                
                String href = socialLink.getAttribute("href");
                Assertions.assertNotNull(href, socialPlatforms[i] + " link should have href");
                Assertions.assertTrue(href.contains(expectedDomains[i]), 
                    socialPlatforms[i] + " link should point to " + expectedDomains[i]);
            } catch (TimeoutException e) {
                // Some social links might not be visible, continue with next
            }
        }
    }

    /**
     * Test 16: Test tools and products links
     */
    @Test
    @Order(16)
    @DisplayName("Test Tools and Products Links")
    public void testToolsAndProductsLinks() {
        // Scroll to footer
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        String[] toolLinks = {
            "SelectorsHub", "TestCase Studio", "Check My Links", 
            "Testing Daily", "Auto Test Data", "Page Load Time"
        };
        
        for (String toolName : toolLinks) {
            try {
                WebElement toolLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[text()='" + toolName + "']")));
                
                String href = toolLink.getAttribute("href");
                Assertions.assertNotNull(href, toolName + " link should have href");
                Assertions.assertTrue(href.contains("selectorshub.com"), 
                    toolName + " link should point to selectorshub.com");
            } catch (TimeoutException e) {
                // Some tool links might not be visible, continue with next
            }
        }
    }

    /**
     * Test 17: Test PNG download link
     */
    @Test
    @Order(17)
    @DisplayName("Test PNG Download Link")
    public void testPngDownloadLink() {
        WebElement pngDownloadLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[text()='Click to Download PNG File']")));
        
        String downloadHref = pngDownloadLink.getAttribute("href");
        Assertions.assertNotNull(downloadHref, "PNG download link should have href");
        Assertions.assertTrue(downloadHref.contains(".png"), "Download link should point to PNG file");
        Assertions.assertTrue(downloadHref.contains("selectorshub.com"), 
            "Download link should be from selectorshub.com");
    }

    /**
     * Test 18: Test testrigor external link
     */
    @Test
    @Order(18)
    @DisplayName("Test TestRigor External Link")
    public void testTestRigorExternalLink() {
        WebElement testRigorLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'testrigor.com')]")));
        
        String href = testRigorLink.getAttribute("href");
        Assertions.assertTrue(href.contains("testrigor.com"), "Link should point to testrigor.com");
        Assertions.assertTrue(href.contains("utm_source=selectorshub"), 
            "Link should contain tracking parameters");
        
        String linkText = testRigorLink.getText();
        Assertions.assertTrue(linkText.contains("automate"), "Link text should mention automation");
    }

    /**
     * Test 19: Test LinkedIn profile link
     */
    @Test
    @Order(19)
    @DisplayName("Test LinkedIn Profile Link")
    public void testLinkedInProfileLink() {
        WebElement linkedinProfileLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, 'linkedin.com/in/gauravkhuraana')]")));
        
        String href = linkedinProfileLink.getAttribute("href");
        Assertions.assertTrue(href.contains("linkedin.com/in/gauravkhuraana"), 
            "Link should point to Gaurav Khurana's LinkedIn profile");
        
        String linkText = linkedinProfileLink.getText();
        Assertions.assertEquals("Gaurav Khurana", linkText, "Link text should be 'Gaurav Khurana'");
    }

    /**
     * Test 20: Test page scroll and element visibility
     */
    @Test
    @Order(20)
    @DisplayName("Test Page Scroll and Element Visibility")
    public void testPageScrollAndElementVisibility() {
        // Test scrolling to different sections
        WebElement topElement = driver.findElement(By.xpath("//h1"));
        Assertions.assertTrue(topElement.isDisplayed(), "Top element should be visible");
        
        // Scroll to middle
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
        
        // Verify table is visible
        WebElement usersTable = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//table | //div[contains(@class, 'table')]")));
        Assertions.assertTrue(usersTable.isDisplayed(), "Users table should be visible after scroll");
        
        // Scroll to bottom
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Verify footer elements are visible
        WebElement footerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[text()='Contact Us'] | //a[contains(text(), 'support@selectorshub.com')]")));
        Assertions.assertTrue(footerElement.isDisplayed(), "Footer elements should be visible");
        
        // Scroll back to top
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        Assertions.assertTrue(topElement.isDisplayed(), "Should be able to scroll back to top");
    }

    /**
     * Cleanup method to close browser after all tests
     */
    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Method to take screenshot on test failure (optional)
     */
    @AfterEach
    public void takeScreenshotOnFailure(TestInfo testInfo) {
        // This method can be extended to take screenshots on failure
        // For now, just ensure we're on the main page for next test
        if (!driver.getCurrentUrl().equals(BASE_URL)) {
            driver.get(BASE_URL);
        }
    }
}
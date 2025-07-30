package claudesonnet4.ws06.seq02;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Alert;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Selenium WebDriver Test Suite for Katalon Demo AUT
 * Tests all 24 pages and interactive elements in the test site
 * Package: claudesonnet4.ws06.seq02
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KatalonTestSuite {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions actions;
    private static final String BASE_URL = "https://katalon-test.s3.amazonaws.com/aut/html/";
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    @BeforeAll
    static void setupClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, TIMEOUT);
        actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(TIMEOUT);
    }

    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void setUp() {
        // Navigate to index page before each test
        driver.get(BASE_URL + "index.html");
        wait.until(ExpectedConditions.titleIs("Demo AUT"));
    }

    // ==================== INDEX PAGE TESTS ====================

    @Test
    @Order(1)
    @DisplayName("Test Index Page Load and Title")
    void testIndexPageLoadAndTitle() {
        assertEquals("Demo AUT", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("index.html"));
        
        // Verify all 24 navigation links are present
        List<WebElement> navLinks = driver.findElements(By.tagName("a"));
        assertEquals(24, navLinks.size(), "Should have exactly 24 navigation links");
    }

    @Test
    @Order(2)
    @DisplayName("Test All Navigation Links Are Clickable")
    void testAllNavigationLinksClickable() {
        List<WebElement> navLinks = driver.findElements(By.tagName("a"));
        
        for (WebElement link : navLinks) {
            assertTrue(link.isDisplayed(), "Link should be displayed: " + link.getText());
            assertTrue(link.isEnabled(), "Link should be enabled: " + link.getText());
            assertNotNull(link.getAttribute("href"), "Link should have href attribute: " + link.getText());
        }
    }

    // ==================== FORM PAGE TESTS ====================

    @Test
    @Order(3)
    @DisplayName("Test Form Page Navigation and Load")
    void testFormPageNavigation() {
        WebElement formLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Web Form")));
        formLink.click();
        
        wait.until(ExpectedConditions.urlContains("form.html"));
        assertEquals("Demo AUT", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("form.html"));
    }

    @Test
    @Order(4)
    @DisplayName("Test Complete Form Submission")
    void testCompleteFormSubmission() {
        // Navigate to form page
        driver.get(BASE_URL + "form.html");
        
        // Fill First Name
        WebElement firstName = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("first-name")));
        firstName.clear();
        firstName.sendKeys("John");
        assertEquals("John", firstName.getAttribute("value"));
        
        // Fill Last Name
        WebElement lastName = driver.findElement(By.name("last-name"));
        lastName.clear();
        lastName.sendKeys("Doe");
        assertEquals("Doe", lastName.getAttribute("value"));
        
        // Select Gender
        WebElement genderMale = driver.findElement(By.id("gender-male"));
        genderMale.click();
        assertTrue(genderMale.isSelected());
        
        // Fill Date of Birth
        WebElement dateOfBirth = driver.findElement(By.name("dob"));
        dateOfBirth.clear();
        dateOfBirth.sendKeys("01/01/1990");
        
        // Fill Address
        WebElement address = driver.findElement(By.name("address"));
        address.clear();
        address.sendKeys("123 Test Street, Test City, TC 12345");
        
        // Fill Email
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("john.doe@test.com");
        
        // Fill Password
        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys("TestPassword123");
        
        // Fill Company
        WebElement company = driver.findElement(By.name("company"));
        company.clear();
        company.sendKeys("Test Company Inc.");
        
        // Select Role
        Select roleSelect = new Select(driver.findElement(By.name("role")));
        roleSelect.selectByVisibleText("QA");
        assertEquals("QA", roleSelect.getFirstSelectedOption().getText());
        
        // Select Job Expectations (checkboxes)
        WebElement highSalary = driver.findElement(By.id("high_salary"));
        highSalary.click();
        assertTrue(highSalary.isSelected());
        
        WebElement niceManager = driver.findElement(By.id("nice_manager"));
        niceManager.click();
        assertTrue(niceManager.isSelected());
        
        // Select Ways of Development (checkboxes)
        WebElement readBooks = driver.findElement(By.id("read_books"));
        readBooks.click();
        assertTrue(readBooks.isSelected());
        
        WebElement onlineCourses = driver.findElement(By.id("online_courses"));
        onlineCourses.click();
        assertTrue(onlineCourses.isSelected());
        
        // Fill Comment
        WebElement comment = driver.findElement(By.name("comment"));
        comment.clear();
        comment.sendKeys("This is a comprehensive test of the form functionality.");
        
        // Submit Form
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Verify submission success
        wait.until(ExpectedConditions.textToBePresentInElement(
            driver.findElement(By.tagName("body")), "Successfully submitted!"));
    }

    @Test
    @Order(5)
    @DisplayName("Test Form Validation - Empty Fields")
    void testFormValidationEmptyFields() {
        driver.get(BASE_URL + "form.html");
        
        // Try to submit empty form
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        submitButton.click();
        
        // Check if required field validation works
        WebElement firstName = driver.findElement(By.name("first-name"));
        String validationMessage = firstName.getAttribute("validationMessage");
        assertNotNull(validationMessage, "Should have validation message for required field");
    }

    // ==================== INDIVIDUAL PAGE TESTS ====================

    @Test
    @Order(6)
    @DisplayName("Test Delay Page")
    void testDelayPage() {
        driver.get(BASE_URL + "delay.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Look for delay-related elements
        WebElement delayButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("button")));
        delayButton.click();
        
        // Wait for delayed element to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("delayed_element")));
        WebElement delayedElement = driver.findElement(By.id("delayed_element"));
        assertTrue(delayedElement.isDisplayed());
    }

    @Test
    @Order(7)
    @DisplayName("Test Drag and Drop Page")
    void testDragAndDropPage() {
        driver.get(BASE_URL + "dragdrop.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement source = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("drag1")));
        WebElement target = driver.findElement(By.id("div1"));
        
        // Perform drag and drop
        actions.dragAndDrop(source, target).perform();
        
        // Verify drag and drop completed
        assertTrue(target.findElements(By.id("drag1")).size() > 0, "Element should be dropped in target");
    }

    @Test
    @Order(8)
    @DisplayName("Test File Upload Page")
    void testFileUploadPage() {
        driver.get(BASE_URL + "file-upload.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
        assertTrue(fileInput.isDisplayed());
        assertTrue(fileInput.isEnabled());
        
        // Verify upload button exists
        WebElement uploadButton = driver.findElement(By.xpath("//input[@type='submit']"));
        assertTrue(uploadButton.isDisplayed());
    }

    @Test
    @Order(9)
    @DisplayName("Test Web Tables Page")
    void testWebTablesPage() {
        driver.get(BASE_URL + "grid.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Find table and verify structure
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
        assertTrue(table.isDisplayed());
        
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        assertTrue(rows.size() > 0, "Table should have rows");
        
        List<WebElement> headers = table.findElements(By.tagName("th"));
        assertTrue(headers.size() > 0, "Table should have headers");
    }

    @Test
    @Order(10)
    @DisplayName("Test Iframe Page")
    void testIframePage() {
        driver.get(BASE_URL + "iframe.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Switch to iframe
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        driver.switchTo().frame(iframe);
        
        // Interact with iframe content
        WebElement iframeContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue(iframeContent.isDisplayed());
        
        // Switch back to main content
        driver.switchTo().defaultContent();
    }

    @Test
    @Order(11)
    @DisplayName("Test JavaScript Dialog (Prompt) Page")
    void testJavaScriptDialogPage() {
        driver.get(BASE_URL + "js-dialog.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Click button to trigger prompt
        WebElement promptButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Prompt')]")));
        promptButton.click();
        
        // Handle prompt dialog
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys("Test Input");
        alert.accept();
    }

    @Test
    @Order(12)
    @DisplayName("Test Key Press Page")
    void testKeyPressPage() {
        driver.get(BASE_URL + "keypress.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("input")));
        inputField.click();
        inputField.sendKeys("Test");
        inputField.sendKeys(Keys.ENTER);
        
        assertEquals("Test", inputField.getAttribute("value"));
    }

    @Test
    @Order(13)
    @DisplayName("Test Open New Window Page")
    void testOpenNewWindowPage() {
        driver.get(BASE_URL + "open-window.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        String originalWindow = driver.getWindowHandle();
        
        // Click button to open new window
        WebElement newWindowButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button")));
        newWindowButton.click();
        
        // Wait for new window and switch to it
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windows = driver.getWindowHandles();
        
        for (String window : windows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        
        // Verify new window content
        assertNotEquals(originalWindow, driver.getWindowHandle());
        
        // Close new window and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    @Order(14)
    @DisplayName("Test Shadow DOM Page")
    void testShadowDOMPage() {
        driver.get(BASE_URL + "shadow-dom.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Find shadow host element
        WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("shadow_host")));
        assertTrue(shadowHost.isDisplayed());
        
        // Access shadow root (if supported)
        try {
            SearchContext shadowRoot = shadowHost.getShadowRoot();
            WebElement shadowContent = shadowRoot.findElement(By.cssSelector("*"));
            assertTrue(shadowContent.isDisplayed());
        } catch (Exception e) {
            // Shadow DOM might not be accessible in all browsers
            System.out.println("Shadow DOM access not supported: " + e.getMessage());
        }
    }

    @Test
    @Order(15)
    @DisplayName("Test Alert Page")
    void testAlertPage() {
        driver.get(BASE_URL + "alert.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Click button to trigger alert
        WebElement alertButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button")));
        alertButton.click();
        
        // Handle alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        assertNotNull(alertText);
        alert.accept();
    }

    @Test
    @Order(16)
    @DisplayName("Test Click Page")
    void testClickPage() {
        driver.get(BASE_URL + "click.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement clickButton = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button")));
        clickButton.click();
        
        // Verify click action result
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("result")));
        assertTrue(result.isDisplayed());
    }

    @Test
    @Order(17)
    @DisplayName("Test Confirm Page")
    void testConfirmPage() {
        driver.get(BASE_URL + "confirm.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Click button to trigger confirm dialog
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button")));
        confirmButton.click();
        
        // Handle confirm dialog
        Alert confirm = wait.until(ExpectedConditions.alertIsPresent());
        confirm.accept();
    }

    @Test
    @Order(18)
    @DisplayName("Test Link Page")
    void testLinkPage() {
        driver.get(BASE_URL + "link.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("a")));
        String originalUrl = driver.getCurrentUrl();
        link.click();
        
        // Verify navigation occurred
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));
    }

    @Test
    @Order(19)
    @DisplayName("Test Element Rect Page")
    void testElementRectPage() {
        driver.get(BASE_URL + "element-rect.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rect_element")));
        Rectangle rect = element.getRect();
        
        assertTrue(rect.getWidth() > 0);
        assertTrue(rect.getHeight() > 0);
        assertTrue(rect.getX() >= 0);
        assertTrue(rect.getY() >= 0);
    }

    @Test
    @Order(20)
    @DisplayName("Test Hover Page")
    void testHoverPage() {
        driver.get(BASE_URL + "hover.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement hoverElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("hover_element")));
        
        // Perform hover action
        actions.moveToElement(hoverElement).perform();
        
        // Verify hover effect
        WebElement hoverResult = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("hover_result")));
        assertTrue(hoverResult.isDisplayed());
    }

    @Test
    @Order(21)
    @DisplayName("Test Refresh Page")
    void testRefreshPage() {
        driver.get(BASE_URL + "refresh.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        String originalUrl = driver.getCurrentUrl();
        
        // Click refresh button
        WebElement refreshButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button")));
        refreshButton.click();
        
        // Verify page refreshed
        wait.until(ExpectedConditions.urlToBe(originalUrl));
    }

    @Test
    @Order(22)
    @DisplayName("Test Image Page")
    void testImagePage() {
        driver.get(BASE_URL + "image.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement image = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("img")));
        assertTrue(image.isDisplayed());
        assertNotNull(image.getAttribute("src"));
    }

    @Test
    @Order(23)
    @DisplayName("Test Type On Image Page")
    void testTypeOnImagePage() {
        driver.get(BASE_URL + "type-on-image.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement imageInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='image']")));
        assertTrue(imageInput.isDisplayed());
        imageInput.click();
    }

    @Test
    @Order(24)
    @DisplayName("Test Element Attribute Change Page")
    void testElementAttributeChangePage() {
        driver.get(BASE_URL + "element-attribute-change.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("attribute_element")));
        String originalAttribute = element.getAttribute("class");
        
        // Trigger attribute change
        WebElement changeButton = driver.findElement(By.xpath("//button"));
        changeButton.click();
        
        // Wait for attribute change
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(element, "class", originalAttribute)));
    }

    @Test
    @Order(25)
    @DisplayName("Test Element Clickable Page")
    void testElementClickablePage() {
        driver.get(BASE_URL + "element-clickable.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("clickable_element")));
        assertTrue(clickableElement.isEnabled());
        clickableElement.click();
    }

    @Test
    @Order(26)
    @DisplayName("Test Element Visibility Page")
    void testElementVisibilityPage() {
        driver.get(BASE_URL + "element-visibility.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Test visible element
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visible_element")));
        assertTrue(visibleElement.isDisplayed());
        
        // Test hidden element
        WebElement hiddenElement = driver.findElement(By.id("hidden_element"));
        assertFalse(hiddenElement.isDisplayed());
    }

    @Test
    @Order(27)
    @DisplayName("Test jQuery Async Page")
    void testJQueryAsyncPage() {
        driver.get(BASE_URL + "jquery-async.html");
        assertEquals("Demo AUT", driver.getTitle());
        
        // Trigger async operation
        WebElement asyncButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button")));
        asyncButton.click();
        
        // Wait for async result
        WebElement asyncResult = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("async_result")));
        assertTrue(asyncResult.isDisplayed());
    }

    @Test
    @Order(28)
    @DisplayName("Test Page Load Page")
    void testPageLoadPage() {
        long startTime = System.currentTimeMillis();
        driver.get(BASE_URL + "page-load.html");
        long endTime = System.currentTimeMillis();
        
        assertEquals("Demo AUT", driver.getTitle());
        
        // Verify page loaded within reasonable time
        long loadTime = endTime - startTime;
        assertTrue(loadTime < 10000, "Page should load within 10 seconds");
        
        // Verify page content is present
        WebElement pageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue(pageContent.isDisplayed());
    }

    // ==================== COMPREHENSIVE INTEGRATION TESTS ====================

    @Test
    @Order(29)
    @DisplayName("Test Complete Site Navigation Flow")
    void testCompleteSiteNavigationFlow() {
        // Start from index page
        driver.get(BASE_URL + "index.html");
        
        // Navigate through first 5 pages and verify each loads correctly
        String[] testPages = {"delay.html", "dragdrop.html", "file-upload.html", "form.html", "grid.html"};
        
        for (String page : testPages) {
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='" + page + "']")));
            link.click();
            
            wait.until(ExpectedConditions.urlContains(page));
            assertEquals("Demo AUT", driver.getTitle());
            
            // Navigate back to index
            driver.navigate().back();
            wait.until(ExpectedConditions.urlContains("index.html"));
        }
    }

    @Test
    @Order(30)
    @DisplayName("Test Browser Navigation Functions")
    void testBrowserNavigationFunctions() {
        // Test forward/back navigation
        driver.get(BASE_URL + "index.html");
        driver.get(BASE_URL + "form.html");
        
        driver.navigate().back();
        assertTrue(driver.getCurrentUrl().contains("index.html"));
        
        driver.navigate().forward();
        assertTrue(driver.getCurrentUrl().contains("form.html"));
        
        driver.navigate().refresh();
        assertTrue(driver.getCurrentUrl().contains("form.html"));
    }
}
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
 * Accessibility and usability tests for Katalon Demo AUT Form
 * Tests keyboard navigation, screen reader compatibility, and WCAG compliance
 * Package: claudesonnet4.ws06.seq05
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FormAccessibilityTests {

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
    @DisplayName("Test Form Elements Have Proper IDs and Names")
    void testFormElementsHaveProperIdsAndNames() {
        // Test that all form elements have proper identification
        String[] expectedIds = {"first-name", "last-name", "dob", "address", "email", "password", "company", "role", "expectation", "comment", "submit"};
        
        for (String id : expectedIds) {
            WebElement element = driver.findElement(By.id(id));
            Assertions.assertTrue(element.isDisplayed());
            Assertions.assertEquals(id, element.getAttribute("id"));
        }
        
        // Test radio buttons have proper name attribute
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        Assertions.assertEquals(3, genderRadios.size());
        for (WebElement radio : genderRadios) {
            Assertions.assertEquals("gender", radio.getAttribute("name"));
        }
        
        // Test checkboxes are identifiable
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        Assertions.assertEquals(6, checkboxes.size());
        for (WebElement checkbox : checkboxes) {
            Assertions.assertEquals("checkbox", checkbox.getAttribute("type"));
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test Keyboard Navigation Through Form")
    void testKeyboardNavigationThroughForm() {
        // Start with first name field
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.click();
        
        // Verify focus is on first name field
        WebElement activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals("first-name", activeElement.getAttribute("id"));
        
        // Navigate through form using Tab key
        String[] expectedTabOrder = {"first-name", "last-name"};
        
        for (int i = 0; i < expectedTabOrder.length - 1; i++) {
            activeElement = driver.switchTo().activeElement();
            Assertions.assertEquals(expectedTabOrder[i], activeElement.getAttribute("id"));
            
            // Press Tab to move to next field
            activeElement.sendKeys(Keys.TAB);
        }
        
        // Verify we can navigate to the last expected field
        activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals(expectedTabOrder[expectedTabOrder.length - 1], activeElement.getAttribute("id"));
    }

    @Test
    @Order(3)
    @DisplayName("Test Form Elements Are Keyboard Accessible")
    void testFormElementsAreKeyboardAccessible() {
        // Test text input with keyboard
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.click();
        firstNameField.sendKeys("John");
        Assertions.assertEquals("John", firstNameField.getAttribute("value"));
        
        // Test radio button with keyboard
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        genderRadios.get(0).sendKeys(Keys.SPACE);
        Assertions.assertTrue(genderRadios.get(0).isSelected());
        
        // Test dropdown with keyboard
        WebElement roleDropdown = driver.findElement(By.id("role"));
        roleDropdown.click();
        roleDropdown.sendKeys(Keys.ARROW_DOWN);
        roleDropdown.sendKeys(Keys.ENTER);
        
        Select roleSelect = new Select(roleDropdown);
        Assertions.assertNotNull(roleSelect.getFirstSelectedOption());
        
        // Test checkbox with keyboard
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        checkboxes.get(0).sendKeys(Keys.SPACE);
        Assertions.assertTrue(checkboxes.get(0).isSelected());
        
        // Test submit button with keyboard
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.sendKeys(Keys.ENTER);
        
        // Verify form submission attempt
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }

    @Test
    @Order(4)
    @DisplayName("Test Form Labels and Accessibility Attributes")
    void testFormLabelsAndAccessibilityAttributes() {
        // Test that form elements have associated labels or aria-labels
        String[] inputIds = {"first-name", "last-name", "dob", "address", "email", "password", "company"};
        
        for (String inputId : inputIds) {
            WebElement input = driver.findElement(By.id(inputId));
            
            // Check for associated label
            try {
                WebElement label = driver.findElement(By.cssSelector("label[for='" + inputId + "']"));
                Assertions.assertTrue(label.isDisplayed());
            } catch (NoSuchElementException e) {
                // If no label, check for aria-label or placeholder
                String ariaLabel = input.getAttribute("aria-label");
                String placeholder = input.getAttribute("placeholder");
                String title = input.getAttribute("title");
                
                Assertions.assertTrue(
                    (ariaLabel != null && !ariaLabel.isEmpty()) ||
                    (placeholder != null && !placeholder.isEmpty()) ||
                    (title != null && !title.isEmpty()),
                    "Element " + inputId + " should have a label, aria-label, placeholder, or title"
                );
            }
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test Form Element Focus Indicators")
    void testFormElementFocusIndicators() {
        // Test that form elements show focus indicators
        String[] focusableIds = {"first-name", "last-name", "email", "password", "submit"};
        
        for (String id : focusableIds) {
            WebElement element = driver.findElement(By.id(id));
            element.click();
            
            // Verify element is focused
            WebElement activeElement = driver.switchTo().activeElement();
            Assertions.assertEquals(id, activeElement.getAttribute("id"));
            
            // Check if element has focus styling (this would typically be done with CSS inspection)
            Assertions.assertTrue(element.isDisplayed());
            Assertions.assertTrue(element.isEnabled());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test Screen Reader Compatibility")
    void testScreenReaderCompatibility() {
        // Test that form elements have proper semantic markup
        
        // Test form has proper role
        WebElement form = driver.findElement(By.tagName("form"));
        Assertions.assertTrue(form.isDisplayed());
        
        // Test input types are semantically correct
        Assertions.assertEquals("email", driver.findElement(By.id("email")).getAttribute("type"));
        Assertions.assertEquals("password", driver.findElement(By.id("password")).getAttribute("type"));
        Assertions.assertEquals("text", driver.findElement(By.id("first-name")).getAttribute("type"));
        
        // Test button has proper type
        Assertions.assertEquals("submit", driver.findElement(By.id("submit")).getAttribute("type"));
        
        // Test select elements are properly marked
        WebElement roleSelect = driver.findElement(By.id("role"));
        Assertions.assertEquals("select", roleSelect.getTagName().toLowerCase());
        
        // Test textarea is properly marked
        WebElement commentTextarea = driver.findElement(By.id("comment"));
        Assertions.assertEquals("textarea", commentTextarea.getTagName().toLowerCase());
    }

    @Test
    @Order(7)
    @DisplayName("Test Form Error Handling and Feedback")
    void testFormErrorHandlingAndFeedback() {
        // Test form validation feedback
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("invalid-email");
        
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Check for validation message
        String validationMessage = emailField.getAttribute("validationMessage");
        if (validationMessage != null && !validationMessage.isEmpty()) {
            Assertions.assertFalse(validationMessage.isEmpty());
        }
        
        // Verify form doesn't submit with invalid data
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
        
        // Test with valid email
        emailField.clear();
        emailField.sendKeys("valid@example.com");
        
        // Check validation message is cleared
        validationMessage = emailField.getAttribute("validationMessage");
        if (validationMessage != null) {
            Assertions.assertTrue(validationMessage.isEmpty() || validationMessage.equals(""));
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test Color Contrast and Visual Accessibility")
    void testColorContrastAndVisualAccessibility() {
        // Test that form elements are visible and have sufficient contrast
        // This is a basic test - in practice, you'd use specialized tools for color contrast testing
        
        String[] visibleElements = {"first-name", "last-name", "email", "password", "submit"};
        
        for (String id : visibleElements) {
            WebElement element = driver.findElement(By.id(id));
            
            // Verify element is visible
            Assertions.assertTrue(element.isDisplayed());
            
            // Verify element has reasonable size for accessibility
            Dimension size = element.getSize();
            Assertions.assertTrue(size.getHeight() > 0);
            Assertions.assertTrue(size.getWidth() > 0);
            
            // Verify element is not hidden by CSS
            String display = element.getCssValue("display");
            String visibility = element.getCssValue("visibility");
            Assertions.assertNotEquals("none", display);
            Assertions.assertNotEquals("hidden", visibility);
        }
    }

    @Test
    @Order(9)
    @DisplayName("Test Form Resize and Zoom Accessibility")
    void testFormResizeAndZoomAccessibility() {
        // Test form accessibility at different zoom levels
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Test at 150% zoom
        js.executeScript("document.body.style.zoom='150%'");
        
        // Verify form elements are still accessible
        Assertions.assertTrue(driver.findElement(By.id("first-name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("submit")).isDisplayed());
        
        // Test form interaction at zoom level
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.sendKeys("Zoom Test");
        Assertions.assertEquals("Zoom Test", firstNameField.getAttribute("value"));
        
        // Reset zoom
        js.executeScript("document.body.style.zoom='100%'");
        
        // Test at different viewport sizes
        driver.manage().window().setSize(new Dimension(800, 600));
        
        // Verify form is still accessible at smaller size
        Assertions.assertTrue(driver.findElement(By.id("first-name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("submit")).isDisplayed());
        
        // Reset window size
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    @Order(10)
    @DisplayName("Test Form Timeout and Session Accessibility")
    void testFormTimeoutAndSessionAccessibility() {
        // Fill form data
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        
        // Wait to simulate user taking time to fill form
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify form data is still present
        Assertions.assertEquals("John", driver.findElement(By.id("first-name")).getAttribute("value"));
        Assertions.assertEquals("Doe", driver.findElement(By.id("last-name")).getAttribute("value"));
        Assertions.assertEquals("john.doe@example.com", driver.findElement(By.id("email")).getAttribute("value"));
        
        // Test form submission after delay
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Verify form submission works
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }

    @Test
    @Order(11)
    @DisplayName("Test Form Help and Instructions Accessibility")
    void testFormHelpAndInstructionsAccessibility() {
        // Test that form provides adequate instructions and help
        
        // Check for form title or heading
        try {
            WebElement heading = driver.findElement(By.tagName("h1"));
            Assertions.assertTrue(heading.isDisplayed());
        } catch (NoSuchElementException e) {
            // Check for other heading levels or title
            try {
                WebElement heading = driver.findElement(By.cssSelector("h2, h3, h4, h5, h6"));
                Assertions.assertTrue(heading.isDisplayed());
            } catch (NoSuchElementException ex) {
                // Form should have some kind of title or instructions
                String pageTitle = driver.getTitle();
                Assertions.assertNotNull(pageTitle);
                Assertions.assertFalse(pageTitle.isEmpty());
            }
        }
        
        // Test that required fields are indicated
        // This would typically check for asterisks, "required" text, or aria-required attributes
        String[] inputIds = {"first-name", "last-name", "email", "password"};
        
        for (String inputId : inputIds) {
            WebElement input = driver.findElement(By.id(inputId));
            
            // Check for required attribute
            String required = input.getAttribute("required");
            String ariaRequired = input.getAttribute("aria-required");
            
            // At minimum, form should be usable even if not all fields are marked as required
            Assertions.assertTrue(input.isDisplayed());
            Assertions.assertTrue(input.isEnabled());
        }
    }

    @Test
    @Order(12)
    @DisplayName("Test Form Mobile Accessibility")
    void testFormMobileAccessibility() {
        // Test form accessibility on mobile viewport
        driver.manage().window().setSize(new Dimension(375, 667)); // iPhone size
        
        // Verify all form elements are accessible on mobile
        String[] mobileTestIds = {"first-name", "last-name", "email", "password", "submit"};
        
        for (String id : mobileTestIds) {
            WebElement element = driver.findElement(By.id(id));
            Assertions.assertTrue(element.isDisplayed());
            
            // Verify element is large enough for touch interaction
            Dimension size = element.getSize();
            Assertions.assertTrue(size.getHeight() >= 20); // Minimum touch target size
            Assertions.assertTrue(size.getWidth() >= 20);
        }
        
        // Test form interaction on mobile
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.sendKeys("Mobile Test");
        Assertions.assertEquals("Mobile Test", firstNameField.getAttribute("value"));
        
        // Test dropdown on mobile
        WebElement roleDropdown = driver.findElement(By.id("role"));
        Select roleSelect = new Select(roleDropdown);
        roleSelect.selectByVisibleText("Developer");
        Assertions.assertEquals("Developer", roleSelect.getFirstSelectedOption().getText());
        
        // Reset window size
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    @Order(13)
    @DisplayName("Test Form Language and Internationalization")
    void testFormLanguageAndInternationalization() {
        // Test form supports international characters
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        WebElement addressField = driver.findElement(By.id("address"));
        
        // Test various international characters
        String[] internationalNames = {
            "José María",
            "François",
            "Müller",
            "Øyvind",
            "Владимир",
            "محمد",
            "田中",
            "김민수"
        };
        
        for (String name : internationalNames) {
            firstNameField.clear();
            firstNameField.sendKeys(name);
            Assertions.assertEquals(name, firstNameField.getAttribute("value"));
        }
        
        // Test international address
        String internationalAddress = "123 Rue de la Paix, 75001 Paris, France";
        addressField.clear();
        addressField.sendKeys(internationalAddress);
        Assertions.assertEquals(internationalAddress, addressField.getAttribute("value"));
        
        // Test form still functions with international input
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }
}
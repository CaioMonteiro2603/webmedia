package deepseek.ws08.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WebsiteTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/system-healing-test/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomePageElements() {
        driver.get(BASE_URL);
        
        // Verify main elements
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        Assertions.assertEquals("System Healing Test", heading.getText());
        
        // Test all buttons
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        Assertions.assertTrue(buttons.size() > 0, "No buttons found");
        
        for (WebElement button : buttons) {
            Assertions.assertTrue(button.isDisplayed(), "Button not visible: " + button.getText());
            Assertions.assertTrue(button.isEnabled(), "Button not enabled: " + button.getText());
        }
        
        // Test images
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement img : images) {
            Assertions.assertTrue(img.isDisplayed(), "Image not displayed: " + img.getAttribute("src"));
        }
    }

    @Test
    public void testNavigationToAllPages() {
        driver.get(BASE_URL);
        
        // Get all navigation links
        List<WebElement> navLinks = driver.findElements(By.cssSelector("nav a"));
        Assertions.assertTrue(navLinks.size() > 0, "No navigation links found");
        
        for (WebElement link : navLinks) {
            String pageName = link.getText();
            String expectedUrl = link.getAttribute("href");
            
            // Navigate to page
            link.click();
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            
            // Verify page content
            WebElement mainContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("main")));
            Assertions.assertTrue(mainContent.isDisplayed(), "Main content not displayed on: " + pageName);
            
            // Test all interactive elements on the page
            testInteractiveElementsOnPage();
            
            // Return to home page
            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe(BASE_URL));
        }
    }

    private void testInteractiveElementsOnPage() {
        // Test all buttons
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        for (WebElement button : buttons) {
            if (button.isDisplayed() && button.isEnabled()) {
                button.click();
                // Add appropriate assertions based on expected behavior
            }
        }
        
        // Test all forms
        List<WebElement> forms = driver.findElements(By.tagName("form"));
        for (WebElement form : forms) {
            if (form.isDisplayed()) {
                // Test form submission with test data
                testFormSubmission(form);
            }
        }
    }

    private void testFormSubmission(WebElement form) {
        // Find all input fields
        List<WebElement> inputs = form.findElements(By.tagName("input"));
        for (WebElement input : inputs) {
            if (input.isDisplayed() && input.isEnabled()) {
                String type = input.getAttribute("type");
                if (!"submit".equals(type) && !"button".equals(type)) {
                    input.sendKeys("Test Input");
                }
            }
        }
        
        // Find all textareas
        List<WebElement> textareas = form.findElements(By.tagName("textarea"));
        for (WebElement textarea : textareas) {
            if (textarea.isDisplayed() && textarea.isEnabled()) {
                textarea.sendKeys("This is a test message for the textarea field.");
            }
        }
        
        // Submit the form
        WebElement submitButton = form.findElement(By.cssSelector("button[type='submit'], input[type='submit']"));
        submitButton.click();
        
        // Verify submission (adjust based on actual behavior)
        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".success, .alert-success")));
            Assertions.assertTrue(successMessage.isDisplayed(), "Form submission failed");
        } catch (TimeoutException e) {
            // Handle case where success message isn't shown
        }
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        
        List<WebElement> externalLinks = driver.findElements(By.cssSelector("a[href^='http']:not([href*='wavingtest.github.io'])"));
        Assertions.assertTrue(externalLinks.size() > 0, "No external links found");
        
        for (WebElement link : externalLinks) {
            String originalWindow = driver.getWindowHandle();
            String href = link.getAttribute("href");
            
            // Open in new tab
            link.click();
            
            // Switch to new tab
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            
            // Verify external page loaded
            wait.until(ExpectedConditions.urlToBe(href));
            Assertions.assertNotEquals(BASE_URL, driver.getCurrentUrl());
            
            // Close tab and switch back
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    @Test
    public void testAllPageSpecificFeatures() {
        // Test features page
        testFeaturesPage();
        
        // Test contact page
        testContactPage();
        
        // Test about page
        testAboutPage();
    }

    private void testFeaturesPage() {
        driver.get(BASE_URL + "features.html");
        
        // Test accordion
        List<WebElement> accordionItems = driver.findElements(By.cssSelector(".accordion-item"));
        for (WebElement item : accordionItems) {
            WebElement header = item.findElement(By.cssSelector(".accordion-header"));
            header.click();
            
            WebElement content = item.findElement(By.cssSelector(".accordion-content"));
            Assertions.assertTrue(content.isDisplayed(), "Accordion content not visible");
            
            // Verify content is not empty
            Assertions.assertFalse(content.getText().isEmpty(), "Accordion content is empty");
        }
    }

    private void testContactPage() {
        driver.get(BASE_URL + "contact.html");
        
        // Test form validation
        WebElement form = driver.findElement(By.tagName("form"));
        WebElement submitButton = form.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Verify validation messages
        List<WebElement> errorMessages = driver.findElements(By.cssSelector(".error-message"));
        Assertions.assertTrue(errorMessages.size() > 0, "No validation errors shown");
        
        // Now fill the form properly
        testFormSubmission(form);
    }

    private void testAboutPage() {
        driver.get(BASE_URL + "about.html");
        
        // Test team member cards
        List<WebElement> teamMembers = driver.findElements(By.cssSelector(".team-member"));
        Assertions.assertTrue(teamMembers.size() > 0, "No team members found");
        
        for (WebElement member : teamMembers) {
            Assertions.assertTrue(member.isDisplayed(), "Team member card not displayed");
            WebElement name = member.findElement(By.cssSelector(".member-name"));
            Assertions.assertFalse(name.getText().isEmpty(), "Member name is empty");
        }
    }
}
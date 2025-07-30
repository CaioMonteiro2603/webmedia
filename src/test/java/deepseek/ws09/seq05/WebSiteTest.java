package deepseek.ws09.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WebSiteTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com/index.html";

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
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
    public void testMainPageElements() {
        driver.get(BASE_URL);
        
        // Verify page title
        Assertions.assertEquals("TAT - Thinking About Testing", driver.getTitle());
        
        // Verify header elements
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("header")));
        Assertions.assertTrue(header.isDisplayed());
        
        // Verify navigation links
        testNavigationLinks();
        
        // Verify form elements
        testContactForm();
        
        // Verify footer
        WebElement footer = driver.findElement(By.tagName("footer"));
        Assertions.assertTrue(footer.isDisplayed());
    }

    private void testNavigationLinks() {
        // Get all navigation links
        List<WebElement> navLinks = driver.findElements(By.cssSelector("nav a"));
        Assertions.assertTrue(navLinks.size() > 0, "No navigation links found");
        
        // Test each navigation link
        for (WebElement link : navLinks) {
            String linkText = link.getText();
            String href = link.getAttribute("href");
            
            // Skip mailto links
            if (href.startsWith("mailto:")) continue;
            
            // Click the link
            link.click();
            
            // Verify navigation
            if (!href.contains("github.com")) { // External links handled separately
                wait.until(ExpectedConditions.urlToBe(href));
                Assertions.assertEquals(href, driver.getCurrentUrl());
                
                // Test elements on the new page
                testPageSpecificElements(href);
                
                // Navigate back to main page
                driver.navigate().back();
                wait.until(ExpectedConditions.urlToBe(BASE_URL));
            }
        }
    }

    private void testPageSpecificElements(String url) {
        if (url.contains("index.html")) {
            // Already testing main page in main test
        } else if (url.contains("privacy.html")) {
            testPrivacyPage();
        } else if (url.contains("faq.html")) {
            testFaqPage();
        }
    }

    private void testContactForm() {
        WebElement form = driver.findElement(By.id("contact-form"));
        Assertions.assertTrue(form.isDisplayed(), "Contact form not displayed");
        
        // Test form elements
        WebElement nameInput = driver.findElement(By.name("name"));
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement phoneInput = driver.findElement(By.name("phone"));
        WebElement productSelect = driver.findElement(By.name("product"));
        WebElement messageTextarea = driver.findElement(By.name("message"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        
        // Test input fields
        nameInput.sendKeys("Test User");
        emailInput.sendKeys("test@example.com");
        phoneInput.sendKeys("1234567890");
        
        // Test dropdown
        productSelect.click();
        WebElement option = driver.findElement(By.cssSelector("option[value='consulting']"));
        option.click();
        
        // Test textarea
        messageTextarea.sendKeys("This is a test message");
        
        // Test submit button
        submitButton.click();
        
        // Verify success message
        WebElement successMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success"))
        );
        Assertions.assertTrue(successMessage.isDisplayed(), "Success message not displayed");
    }

    private void testPrivacyPage() {
        WebElement privacyHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1"))
        );
        Assertions.assertEquals("Privacy Policy", privacyHeader.getText());
        
        // Verify content sections
        List<WebElement> sections = driver.findElements(By.cssSelector("section h2"));
        Assertions.assertTrue(sections.size() >= 3, "Expected at least 3 sections in privacy policy");
    }

    private void testFaqPage() {
        WebElement faqHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1"))
        );
        Assertions.assertEquals("FAQ", faqHeader.getText());
        
        // Test FAQ items
        List<WebElement> faqItems = driver.findElements(By.cssSelector(".faq-item"));
        Assertions.assertTrue(faqItems.size() > 0, "No FAQ items found");
        
        // Test expanding/collapsing FAQ items
        for (WebElement item : faqItems) {
            WebElement question = item.findElement(By.cssSelector(".question"));
            question.click();
            
            WebElement answer = item.findElement(By.cssSelector(".answer"));
            wait.until(ExpectedConditions.visibilityOf(answer));
            Assertions.assertTrue(answer.isDisplayed(), "FAQ answer not displayed after click");
            
            // Collapse again
            question.click();
            wait.until(ExpectedConditions.invisibilityOf(answer));
        }
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        
        // Get all external links
        List<WebElement> externalLinks = driver.findElements(By.cssSelector("a[href*='github.com']"));
        Assertions.assertTrue(externalLinks.size() > 0, "No external links found");
        
        // Store main window handle
        String mainWindow = driver.getWindowHandle();
        
        for (WebElement link : externalLinks) {
            String href = link.getAttribute("href");
            
            // Open link in new tab
            link.click();
            
            // Switch to new tab
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(mainWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            
            // Verify external page loaded
            wait.until(ExpectedConditions.urlContains("github.com"));
            Assertions.assertTrue(driver.getCurrentUrl().contains("github.com"), "Not on GitHub page");
            
            // Close tab and switch back to main window
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }
}
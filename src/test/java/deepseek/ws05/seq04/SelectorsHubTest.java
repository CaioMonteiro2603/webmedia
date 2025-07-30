package deepseek.ws05.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SelectorsHubTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://selectorshub.com/xpath-practice-page/";

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
        Assertions.assertEquals("XPath Practice Page - SelectorsHub", driver.getTitle());
        
        // Test header section
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h1[contains(text(),'XPath Practice Page')]")));
        Assertions.assertTrue(header.isDisplayed());
        
        // Test form elements
        testFormElements();
        
        // Test table interactions
        testTableInteractions();
        
        // Test iframe content
        testIframeContent();
        
        // Test shadow DOM elements
        testShadowDomElements();
        
        // Test all links on the page
        testAllLinks();
    }

    private void testFormElements() {
        // Test text input
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("userId")));
        nameInput.sendKeys("Test User");
        Assertions.assertEquals("Test User", nameInput.getAttribute("value"));
        
        // Test email input
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("test@example.com");
        Assertions.assertEquals("test@example.com", emailInput.getAttribute("value"));
        
        // Test password input
        WebElement passwordInput = driver.findElement(By.id("pass"));
        passwordInput.sendKeys("password123");
        Assertions.assertEquals("password123", passwordInput.getAttribute("value"));
        
        // Test textarea
        WebElement textarea = driver.findElement(By.xpath("//textarea[@placeholder='Enter your address']"));
        textarea.sendKeys("123 Test Street");
        Assertions.assertEquals("123 Test Street", textarea.getAttribute("value"));
        
        // Test dropdown
        WebElement companyDropdown = driver.findElement(By.name("company"));
        companyDropdown.click();
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//option[contains(text(),'Select your company')]")));
        Assertions.assertTrue(option.isDisplayed());
        
        // Test radio buttons
        WebElement radioButton = driver.findElement(By.xpath("//input[@value='female']"));
        radioButton.click();
        Assertions.assertTrue(radioButton.isSelected());
        
        // Test checkbox
        WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox' and @value='automation']"));
        checkbox.click();
        Assertions.assertTrue(checkbox.isSelected());
        
        // Test submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        Assertions.assertTrue(submitButton.isEnabled());
    }

    private void testTableInteractions() {
        // Test table presence
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//table[@id='resultTable']")));
        Assertions.assertTrue(table.isDisplayed());
        
        // Test table rows
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        Assertions.assertTrue(rows.size() > 1);
        
        // Test clicking on a table row
        WebElement firstRow = rows.get(1);
        firstRow.click();
        Assertions.assertTrue(firstRow.getAttribute("class").contains("selected"));
    }

    private void testIframeContent() {
        // Switch to iframe
        WebElement iframe = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//iframe[@id='pact']")));
        driver.switchTo().frame(iframe);
        
        // Test iframe content
        WebElement iframeHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h1[contains(text(),'This is sample text inside iframe')]")));
        Assertions.assertTrue(iframeHeading.isDisplayed());
        
        // Switch back to main content
        driver.switchTo().defaultContent();
    }

    private void testShadowDomElements() {
        // Get shadow host
        WebElement shadowHost = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@id='userName']")));
        
        // Get shadow root
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement shadowRoot = (WebElement) js.executeScript(
            "return arguments[0].shadowRoot", shadowHost);
        
        // Test shadow DOM elements
        WebElement shadowInput = shadowRoot.findElement(By.cssSelector("#kils"));
        shadowInput.sendKeys("Shadow DOM Test");
        Assertions.assertEquals("Shadow DOM Test", shadowInput.getAttribute("value"));
        
        WebElement shadowButton = shadowRoot.findElement(By.cssSelector("#app2"));
        shadowButton.click();
        Assertions.assertTrue(shadowButton.isDisplayed());
    }

    private void testAllLinks() {
        // Get all links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Assertions.assertFalse(links.isEmpty());
        
        // Test each link
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty() && href.startsWith("http")) {
                testLinkNavigation(link, href);
            }
        }
    }

    private void testLinkNavigation(WebElement link, String href) {
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
        
        // Verify new page loads
        Assertions.assertNotEquals(BASE_URL, driver.getCurrentUrl());
        
        // Test basic page elements
        try {
            WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.tagName("body")));
            Assertions.assertTrue(body.isDisplayed());
            
            // Add page-specific tests based on URL
            if (driver.getCurrentUrl().contains("selectorshub")) {
                testSelectorsHubSubPage();
            }
        } finally {
            // Close the tab and switch back to original window
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    private void testSelectorsHubSubPage() {
        // Test for common SelectorsHub page elements
        try {
            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//img[contains(@src,'selectorshub')]")));
            Assertions.assertTrue(logo.isDisplayed());
        } catch (TimeoutException e) {
            // Not a SelectorsHub branded page
        }
        
        // Add more subpage-specific tests as needed
    }
}
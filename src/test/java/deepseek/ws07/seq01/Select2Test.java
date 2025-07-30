package deepseek.ws07.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Select2Test {
    private WebDriver driver;
    private WebDriverWait wait;
    private String mainWindowHandle;

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        mainWindowHandle = driver.getWindowHandle();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testMainPageNavigation() {
        // Navigate to main page
        driver.get("https://select2.github.io/select2/");
        
        // Verify page title
        Assertions.assertEquals("Select2 - The jQuery replacement for select boxes", driver.getTitle());
        
        // Verify main navigation links
        verifyNavigationLink("Getting started", "/getting-started/basic-usage");
        verifyNavigationLink("Examples", "/examples");
        verifyNavigationLink("Options", "/options");
        verifyNavigationLink("Themes", "/themes");
        verifyNavigationLink("Documentation", "/documentation");
        verifyNavigationLink("GitHub", "https://github.com/select2/select2");
    }

    @Test
    public void testBasicUsagePage() {
        driver.get("https://select2.github.io/select2/getting-started/basic-usage/");
        
        // Verify page title
        Assertions.assertTrue(driver.getTitle().contains("Basic usage"));
        
        // Test basic select box
        WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-basic-single")));
        selectElement.click();
        
        // Select an option
        WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        option.click();
        
        // Verify selection
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertNotNull(selectedValue.getAttribute("title"));
    }

    @Test
    public void testMultipleSelectPage() {
        driver.get("https://select2.github.io/select2/examples.html");
        
        // Verify page title
        Assertions.assertTrue(driver.getTitle().contains("Examples"));
        
        // Test multiple select
        WebElement multiSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-basic-multiple")));
        multiSelect.click();
        
        // Select multiple options
        List<WebElement> options = driver.findElements(By.cssSelector(".select2-results__option"));
        options.get(0).click();
        options.get(1).click();
        
        // Verify selections
        List<WebElement> selectedItems = driver.findElements(By.cssSelector(".select2-selection__choice"));
        Assertions.assertTrue(selectedItems.size() >= 2);
    }

    @Test
    public void testOptionsPage() {
        driver.get("https://select2.github.io/select2/options.html");
        
        // Verify page title
        Assertions.assertTrue(driver.getTitle().contains("Options"));
        
        // Test disabled select
        WebElement disabledSelect = driver.findElement(By.cssSelector(".js-example-disabled"));
        Assertions.assertFalse(disabledSelect.isEnabled());
        
        // Test placeholder
        WebElement placeholderSelect = driver.findElement(By.cssSelector(".js-example-placeholder-single"));
        placeholderSelect.click();
        WebElement placeholderOption = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        placeholderOption.click();
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertNotEquals("Select a state", selectedValue.getText());
    }

    @Test
    public void testThemesPage() {
        driver.get("https://select2.github.io/select2/themes.html");
        
        // Verify page title
        Assertions.assertTrue(driver.getTitle().contains("Themes"));
        
        // Test classic theme select
        WebElement classicSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-theme-single")));
        classicSelect.click();
        WebElement classicOption = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        classicOption.click();
        
        // Verify selection
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertNotNull(selectedValue.getAttribute("title"));
    }

    @Test
    public void testDocumentationPage() {
        driver.get("https://select2.github.io/select2/documentation.html");
        
        // Verify page title
        Assertions.assertTrue(driver.getTitle().contains("Documentation"));
        
        // Test all documentation links
        List<WebElement> docLinks = driver.findElements(By.cssSelector(".documentation-links a"));
        for (WebElement link : docLinks) {
            String href = link.getAttribute("href");
            if (href.startsWith("http")) {
                verifyExternalLink(link, href);
            }
        }
    }

    @Test
    public void testGitHubLink() {
        driver.get("https://select2.github.io/select2/");
        
        // Click GitHub link
        WebElement githubLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.linkText("GitHub")));
        githubLink.click();
        
        // Switch to new window
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify GitHub page
        wait.until(ExpectedConditions.titleContains("select2"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("github.com/select2/select2"));
        
        // Close the tab and switch back
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }

    private void verifyNavigationLink(String linkText, String expectedUrlPart) {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.linkText(linkText)));
        link.click();
        
        wait.until(ExpectedConditions.urlContains(expectedUrlPart));
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedUrlPart));
        
        // Navigate back to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe("https://select2.github.io/select2/"));
    }

    private void verifyExternalLink(WebElement link, String expectedUrl) {
        String originalWindow = driver.getWindowHandle();
        link.click();
        
        // Wait for new window to open
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        
        // Switch to new window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify URL
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("about:blank")));
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedUrl));
        
        // Close the tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }
}
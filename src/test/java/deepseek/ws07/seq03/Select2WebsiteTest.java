package deepseek.ws07.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Select2WebsiteTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String mainWindowHandle;

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
        // Store main window handle
        mainWindowHandle = driver.getWindowHandle();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testMainPageLoad() {
        driver.get("https://select2.github.io/select2/");
        
        // Verify page title
        Assertions.assertEquals("Select2 - The jQuery replacement for select boxes", driver.getTitle());
        
        // Verify main elements are present
        Assertions.assertTrue(driver.findElement(By.cssSelector(".navbar-brand")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".jumbotron")).isDisplayed());
    }

    @Test
    public void testNavigationLinks() {
        driver.get("https://select2.github.io/select2/");
        
        // Test Documentation link
        WebElement docsLink = driver.findElement(By.linkText("Documentation"));
        docsLink.click();
        wait.until(ExpectedConditions.titleContains("Documentation"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/docs.html"));
        
        // Test Examples link
        WebElement examplesLink = driver.findElement(By.linkText("Examples"));
        examplesLink.click();
        wait.until(ExpectedConditions.titleContains("Examples"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/examples.html"));
    }

    @Test
    public void testBasicExample() {
        driver.get("https://select2.github.io/select2/examples.html");
        
        // Find and click Basic Example link
        WebElement basicExampleLink = driver.findElement(By.linkText("Basic Example"));
        basicExampleLink.click();
        wait.until(ExpectedConditions.urlContains("#basics"));
        
        // Test the select box
        WebElement selectBox = driver.findElement(By.id("e1"));
        selectBox.click();
        
        // Verify dropdown appears
        WebElement dropdown = driver.findElement(By.cssSelector(".select2-drop"));
        Assertions.assertTrue(dropdown.isDisplayed());
        
        // Select an option
        WebElement option = driver.findElement(By.cssSelector(".select2-results li:nth-child(2)"));
        option.click();
        
        // Verify selection
        WebElement selectedOption = driver.findElement(By.cssSelector(".select2-chosen"));
        Assertions.assertTrue(selectedOption.getText().contains("Alaska"));
    }

    @Test
    public void testMultipleSelect() {
        driver.get("https://select2.github.io/select2/examples.html");
        
        // Find and click Multiple Select link
        WebElement multipleSelectLink = driver.findElement(By.linkText("Multiple Select"));
        multipleSelectLink.click();
        wait.until(ExpectedConditions.urlContains("#multi"));
        
        // Test the multiple select box
        WebElement selectBox = driver.findElement(By.id("e2"));
        selectBox.click();
        
        // Select multiple options
        List<WebElement> options = driver.findElements(By.cssSelector(".select2-results li"));
        options.get(1).click();
        options.get(3).click();
        
        // Verify selections
        List<WebElement> selectedItems = driver.findElements(By.cssSelector(".select2-search-choice"));
        Assertions.assertEquals(2, selectedItems.size());
    }

    @Test
    public void testLoadingRemoteData() {
        driver.get("https://select2.github.io/select2/examples.html");
        
        // Find and click Loading Remote Data link
        WebElement remoteDataLink = driver.findElement(By.linkText("Loading Remote Data"));
        remoteDataLink.click();
        wait.until(ExpectedConditions.urlContains("#remote"));
        
        // Test the remote data select box
        WebElement selectBox = driver.findElement(By.id("e6"));
        selectBox.click();
        
        // Type search term
        WebElement searchField = driver.findElement(By.cssSelector(".select2-input"));
        searchField.sendKeys("test");
        
        // Wait for results
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".select2-results li")));
        
        // Verify results
        List<WebElement> results = driver.findElements(By.cssSelector(".select2-results li"));
        Assertions.assertTrue(results.size() > 0);
    }

    @Test
    public void testExternalLinks() {
        driver.get("https://select2.github.io/select2/");
        
        // Find GitHub link
        WebElement githubLink = driver.findElement(By.linkText("GitHub"));
        
        // Click and verify new tab opens
        githubLink.click();
        
        // Switch to new window
        Set<String> windowHandles = driver.getWindowHandles();
        windowHandles.remove(mainWindowHandle);
        String newWindowHandle = windowHandles.iterator().next();
        driver.switchTo().window(newWindowHandle);
        
        // Verify GitHub page
        wait.until(ExpectedConditions.titleContains("GitHub"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("github.com/select2/select2"));
        
        // Close tab and switch back
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }

    @Test
    public void testDocumentationPages() {
        driver.get("https://select2.github.io/select2/docs.html");
        
        // Test all documentation sections
        List<WebElement> docLinks = driver.findElements(By.cssSelector(".sidebar-nav li a"));
        
        for (WebElement link : docLinks) {
            String linkText = link.getText();
            if (!linkText.isEmpty()) {
                link.click();
                wait.until(ExpectedConditions.urlContains(link.getAttribute("href")));
                
                // Verify content is displayed
                WebElement content = driver.findElement(By.cssSelector(".container .content"));
                Assertions.assertTrue(content.isDisplayed());
                
                // Go back to docs page
                driver.navigate().back();
                wait.until(ExpectedConditions.titleContains("Documentation"));
            }
        }
    }
}
package deepseek.ws07.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class DocumentationTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String DOCUMENTATION_URL = "https://select2.github.io/select2/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(DOCUMENTATION_URL);
        
        // Navigate to Documentation page
        WebElement documentationLink = driver.findElement(By.linkText("Documentation"));
        documentationLink.click();
        wait.until(ExpectedConditions.titleContains("Documentation"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPageLoadsSuccessfully() {
        Assertions.assertEquals("Documentation - Select2", driver.getTitle());
    }

    @Test
    public void testCoreOptionsSection() {
        WebElement coreOptionsHeader = driver.findElement(By.id("core-options"));
        Assertions.assertTrue(coreOptionsHeader.isDisplayed());
        
        // Test dropdown example
        WebElement dropdownExample = driver.findElement(By.cssSelector(".js-example-basic-single"));
        dropdownExample.click();
        
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        option.click();
        
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertNotNull(selectedValue.getAttribute("title"));
    }

    @Test
    public void testDropdownSection() {
        WebElement dropdownHeader = driver.findElement(By.id("dropdown"));
        Assertions.assertTrue(dropdownHeader.isDisplayed());
        
        // Test minimum input length example
        WebElement minInput = driver.findElement(By.cssSelector(".js-example-min-input"));
        minInput.click();
        
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-search__field")));
        searchBox.sendKeys("Alaska");
        
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//li[contains(@class, 'select2-results__option') and contains(text(), 'Alaska')]")));
        option.click();
        
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertEquals("Alaska", selectedValue.getAttribute("title"));
    }

    @Test
    public void testEventsSection() {
        // Scroll to events section
        WebElement eventsHeader = driver.findElement(By.id("events"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", eventsHeader);
        
        // Test event example
        WebElement eventSelect = driver.findElement(By.cssSelector(".js-example-events"));
        eventSelect.click();
        
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        option.click();
        
        WebElement eventOutput = driver.findElement(By.id("event-output"));
        Assertions.assertTrue(eventOutput.getText().contains("change"));
    }

    @Test
    public void testSidebarNavigation() {
        // Test clicking on sidebar links
        List<WebElement> sidebarLinks = driver.findElements(By.cssSelector(".sidebar-nav a"));
        Assertions.assertTrue(sidebarLinks.size() > 0);
        
        for (WebElement link : sidebarLinks) {
            String href = link.getAttribute("href");
            if (href != null && !href.contains("#")) {
                String originalTitle = driver.getTitle();
                link.click();
                wait.until(ExpectedConditions.not(ExpectedConditions.titleIs(originalTitle)));
                driver.navigate().back();
                wait.until(ExpectedConditions.titleIs(originalTitle));
            }
        }
    }
}
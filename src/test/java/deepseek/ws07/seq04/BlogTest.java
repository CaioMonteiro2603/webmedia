package deepseek.ws07.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BlogTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BLOG_URL = "https://select2.github.io/select2/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BLOG_URL);
        
        // Navigate to Blog page
        WebElement blogLink = driver.findElement(By.linkText("Blog"));
        blogLink.click();
        wait.until(ExpectedConditions.titleContains("Blog"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPageLoadsSuccessfully() {
        Assertions.assertEquals("Blog - Select2", driver.getTitle());
    }

    @Test
    public void testBlogPostNavigation() {
        List<WebElement> blogPosts = driver.findElements(By.cssSelector(".post-title a"));
        Assertions.assertTrue(blogPosts.size() > 0);
        
        // Test clicking on the first blog post
        String firstPostTitle = blogPosts.get(0).getText();
        blogPosts.get(0).click();
        wait.until(ExpectedConditions.titleContains(firstPostTitle));
        
        // Verify post content exists
        WebElement postContent = driver.findElement(By.cssSelector(".post-content"));
        Assertions.assertTrue(postContent.isDisplayed());
        
        // Go back to blog listing
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("Blog"));
    }

    @Test
    public void testPagination() {
        // Check if pagination exists
        List<WebElement> paginationLinks = driver.findElements(By.cssSelector(".pagination a"));
        if (paginationLinks.size() > 0) {
            // Test clicking on next page
            String firstPageTitle = driver.getTitle();
            paginationLinks.get(0).click();
            wait.until(ExpectedConditions.not(ExpectedConditions.titleIs(firstPageTitle)));
            
            // Verify we're on a different page
            Assertions.assertNotEquals(firstPageTitle, driver.getTitle());
        }
    }

    @Test
    public void testSearchFunctionality() {
        // Find search input if exists
        List<WebElement> searchInputs = driver.findElements(By.cssSelector(".search-field"));
        if (searchInputs.size() > 0) {
            WebElement searchInput = searchInputs.get(0);
            searchInput.sendKeys("release");
            searchInput.sendKeys(Keys.RETURN);
            
            // Wait for search results
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".search-results")));
            
            // Verify search results
            List<WebElement> results = driver.findElements(By.cssSelector(".search-result"));
            Assertions.assertTrue(results.size() > 0);
        }
    }

    @Test
    public void testExternalLinks() {
        List<WebElement> externalLinks = driver.findElements(By.cssSelector("a[href^='http']:not([href*='select2.github.io'])"));
        if (externalLinks.size() > 0) {
            // Test first external link
            String originalWindow = driver.getWindowHandle();
            externalLinks.get(0).click();
            
            // Wait for new window
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            
            // Switch to new window
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            
            // Verify external page loaded
            Assertions.assertNotEquals("Blog - Select2", driver.getTitle());
            
            // Close external window and switch back
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }
}
package deepseek.ws07.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class GettingStartedTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String GETTING_STARTED_URL = "https://select2.github.io/select2/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(GETTING_STARTED_URL);
        
        // Navigate to Getting Started page
        WebElement gettingStartedLink = driver.findElement(By.linkText("Getting started"));
        gettingStartedLink.click();
        wait.until(ExpectedConditions.titleContains("Getting started"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPageLoadsSuccessfully() {
        Assertions.assertEquals("Getting started - Select2", driver.getTitle());
    }

    @Test
    public void testInstallationSection() {
        WebElement installationHeader = driver.findElement(By.id("install"));
        Assertions.assertTrue(installationHeader.isDisplayed());
        
        // Test npm installation code block
        WebElement npmCodeBlock = driver.findElement(By.cssSelector("pre.highlight:nth-child(4)"));
        Assertions.assertTrue(npmCodeBlock.getText().contains("npm install select2"));
    }

    @Test
    public void testUsageSection() {
        WebElement usageHeader = driver.findElement(By.id("usage"));
        Assertions.assertTrue(usageHeader.isDisplayed());
        
        // Test jQuery include code block
        WebElement jqueryCodeBlock = driver.findElement(By.cssSelector("pre.highlight:nth-child(8)"));
        Assertions.assertTrue(jqueryCodeBlock.getText().contains("$('select').select2()"));
    }

    @Test
    public void testBackToTopLink() {
        // Scroll down
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        
        // Click back to top link
        WebElement backToTop = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".back-to-top")));
        backToTop.click();
        
        // Verify we're back at top
        Long scrollPosition = (Long)((JavascriptExecutor)driver).executeScript("return window.pageYOffset;");
        Assertions.assertTrue(scrollPosition < 100);
    }
}
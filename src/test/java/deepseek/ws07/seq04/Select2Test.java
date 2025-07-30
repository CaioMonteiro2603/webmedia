package deepseek.ws07.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Select2Test {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://select2.github.io/select2/";

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
    public void testMainPageLoadsSuccessfully() {
        driver.get(BASE_URL);
        Assertions.assertEquals("Select2 - The jQuery replacement for select boxes", driver.getTitle());
    }

    @Test
    public void testNavigationLinks() {
        driver.get(BASE_URL);
        
        // Test Getting Started link
        WebElement gettingStartedLink = driver.findElement(By.linkText("Getting started"));
        gettingStartedLink.click();
        wait.until(ExpectedConditions.titleContains("Getting started"));
        Assertions.assertTrue(driver.getTitle().contains("Getting started"));
        driver.navigate().back();
        
        // Test Documentation link
        WebElement documentationLink = driver.findElement(By.linkText("Documentation"));
        documentationLink.click();
        wait.until(ExpectedConditions.titleContains("Documentation"));
        Assertions.assertTrue(driver.getTitle().contains("Documentation"));
        driver.navigate().back();
        
        // Test Examples link
        WebElement examplesLink = driver.findElement(By.linkText("Examples"));
        examplesLink.click();
        wait.until(ExpectedConditions.titleContains("Examples"));
        Assertions.assertTrue(driver.getTitle().contains("Examples"));
        driver.navigate().back();
        
        // Test Blog link
        WebElement blogLink = driver.findElement(By.linkText("Blog"));
        blogLink.click();
        wait.until(ExpectedConditions.titleContains("Blog"));
        Assertions.assertTrue(driver.getTitle().contains("Blog"));
        driver.navigate().back();
        
        // Test GitHub link (external)
        WebElement githubLink = driver.findElement(By.linkText("GitHub"));
        githubLink.click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        
        // Switch to new window
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        Assertions.assertTrue(driver.getCurrentUrl().contains("github.com/select2/select2"));
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    public void testBasicSelectFunctionality() {
        driver.get(BASE_URL);
        
        // Find and click the first select box
        WebElement selectElement = driver.findElement(By.cssSelector(".js-example-basic-single"));
        selectElement.click();
        
        // Wait for dropdown to appear and select an option
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        option.click();
        
        // Verify selection was made
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertNotNull(selectedValue.getAttribute("title"));
    }

    @Test
    public void testMultipleSelectFunctionality() {
        driver.get(BASE_URL);
        
        // Find and click the multiple select box
        WebElement selectElement = driver.findElement(By.cssSelector(".js-example-basic-multiple"));
        selectElement.click();
        
        // Select first option
        WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option:nth-child(1)")));
        firstOption.click();
        
        // Select second option
        WebElement secondOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option:nth-child(2)")));
        secondOption.click();
        
        // Verify selections were made
        List<WebElement> selectedValues = driver.findElements(By.cssSelector(".select2-selection__choice"));
        Assertions.assertEquals(2, selectedValues.size());
    }

    @Test
    public void testSearchFunctionality() {
        driver.get(BASE_URL);
        
        // Find and click the select box with search
        WebElement selectElement = driver.findElement(By.cssSelector(".js-example-basic-single"));
        selectElement.click();
        
        // Type in search box
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-search__field")));
        searchBox.sendKeys("Alaska");
        
        // Select the matching option
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//li[contains(@class, 'select2-results__option') and contains(text(), 'Alaska')]")));
        option.click();
        
        // Verify selection was made
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertEquals("Alaska", selectedValue.getAttribute("title"));
    }
}
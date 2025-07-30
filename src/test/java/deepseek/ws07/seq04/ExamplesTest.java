package deepseek.ws07.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ExamplesTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String EXAMPLES_URL = "https://select2.github.io/select2/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(EXAMPLES_URL);
        
        // Navigate to Examples page
        WebElement examplesLink = driver.findElement(By.linkText("Examples"));
        examplesLink.click();
        wait.until(ExpectedConditions.titleContains("Examples"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPageLoadsSuccessfully() {
        Assertions.assertEquals("Examples - Select2", driver.getTitle());
    }

    @Test
    public void testBasicExamples() {
        // Test basic single select
        WebElement basicSingle = driver.findElement(By.cssSelector(".js-example-basic-single"));
        basicSingle.click();
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        option.click();
        Assertions.assertNotNull(basicSingle.getAttribute("value"));

        // Test basic multiple select
        WebElement basicMultiple = driver.findElement(By.cssSelector(".js-example-basic-multiple"));
        basicMultiple.click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.cssSelector(".select2-results__option")));
        options.get(0).click();
        options.get(1).click();
        List<WebElement> selectedValues = driver.findElements(By.cssSelector(".select2-selection__choice"));
        Assertions.assertEquals(2, selectedValues.size());
    }

    @Test
    public void testLoadingRemoteData() {
        // Scroll to loading remote data section
        WebElement remoteDataSection = driver.findElement(By.id("loading-remote-data"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", remoteDataSection);
        
        // Test movie search example
        WebElement movieSearch = driver.findElement(By.cssSelector(".js-example-data-ajax"));
        movieSearch.click();
        
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-search__field")));
        searchBox.sendKeys("star wars");
        
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option")));
        result.click();
        
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertTrue(selectedValue.getText().contains("Star Wars"));
    }

    @Test
    public void testCustomTemplates() {
        // Scroll to custom templates section
        WebElement templatesSection = driver.findElement(By.id("templating"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", templatesSection);
        
        // Test custom template example
        WebElement templateSelect = driver.findElement(By.cssSelector(".js-example-templating"));
        templateSelect.click();
        
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        option.click();
        
        WebElement selectedValue = driver.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertTrue(selectedValue.getText().contains("Custom template"));
    }

    @Test
    public void testMultipleDropdowns() {
        // Test that all example dropdowns are present
        List<WebElement> exampleSelects = driver.findElements(By.cssSelector(".js-example-"));
        Assertions.assertTrue(exampleSelects.size() >= 5);
        
        // Test interaction with each dropdown
        for (WebElement select : exampleSelects) {
            select.click();
            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".select2-results__option")));
            option.click();
            Assertions.assertNotNull(select.getAttribute("value"));
        }
    }
}
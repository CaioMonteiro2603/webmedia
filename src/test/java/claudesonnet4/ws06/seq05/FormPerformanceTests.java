package claudesonnet4.ws06.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Performance and load testing for Katalon Demo AUT Form
 * Tests form responsiveness, load times, and performance under stress
 * Package: claudesonnet4.ws06.seq05
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FormPerformanceTests {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://katalon-test.s3.amazonaws.com/aut/html/form.html";
    
    @BeforeAll
    static void setupClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeEach
    void setup() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test Page Load Performance")
    void testPageLoadPerformance() {
        long startTime = System.currentTimeMillis();
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        
        // Assert page loads within 5 seconds
        Assertions.assertTrue(loadTime < 5000, "Page load time should be less than 5 seconds, actual: " + loadTime + "ms");
        
        // Verify all critical elements are loaded
        Assertions.assertTrue(driver.findElement(By.id("first-name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("submit")).isDisplayed());
        
        System.out.println("Page load time: " + loadTime + "ms");
    }

    @Test
    @Order(2)
    @DisplayName("Test Form Element Response Time")
    void testFormElementResponseTime() {
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        
        // Test input response time
        long startTime = System.currentTimeMillis();
        firstNameField.sendKeys("Performance Test");
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        // Assert input response is immediate (less than 100ms)
        Assertions.assertTrue(responseTime < 100, "Input response time should be less than 100ms, actual: " + responseTime + "ms");
        Assertions.assertEquals("Performance Test", firstNameField.getAttribute("value"));
        
        System.out.println("Input response time: " + responseTime + "ms");
    }

    @Test
    @Order(3)
    @DisplayName("Test Dropdown Performance")
    void testDropdownPerformance() {
        WebElement roleDropdown = driver.findElement(By.id("role"));
        Select roleSelect = new Select(roleDropdown);
        
        // Test dropdown selection performance
        long startTime = System.currentTimeMillis();
        roleSelect.selectByVisibleText("Developer");
        long endTime = System.currentTimeMillis();
        long selectionTime = endTime - startTime;
        
        // Assert dropdown selection is fast (less than 200ms)
        Assertions.assertTrue(selectionTime < 200, "Dropdown selection time should be less than 200ms, actual: " + selectionTime + "ms");
        Assertions.assertEquals("Developer", roleSelect.getFirstSelectedOption().getText());
        
        System.out.println("Dropdown selection time: " + selectionTime + "ms");
    }

    @Test
    @Order(4)
    @DisplayName("Test Multiple Element Interaction Performance")
    void testMultipleElementInteractionPerformance() {
        long startTime = System.currentTimeMillis();
        
        // Fill multiple form fields rapidly
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("password")).sendKeys("SecurePass123!");
        driver.findElement(By.id("company")).sendKeys("Tech Corp");
        driver.findElement(By.id("address")).sendKeys("123 Main St");
        driver.findElement(By.id("dob")).sendKeys("01/15/1990");
        
        // Select radio button
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        genderRadios.get(0).click();
        
        // Select dropdown options
        Select roleSelect = new Select(driver.findElement(By.id("role")));
        roleSelect.selectByVisibleText("Developer");
        
        Select expectationSelect = new Select(driver.findElement(By.id("expectation")));
        expectationSelect.selectByVisibleText("High salary");
        
        // Select checkboxes
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        checkboxes.get(0).click();
        checkboxes.get(2).click();
        
        // Add comment
        driver.findElement(By.id("comment")).sendKeys("Performance test comment");
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        // Assert all interactions complete within 2 seconds
        Assertions.assertTrue(totalTime < 2000, "Multiple element interaction should complete within 2 seconds, actual: " + totalTime + "ms");
        
        // Verify all values were set correctly
        Assertions.assertEquals("John", driver.findElement(By.id("first-name")).getAttribute("value"));
        Assertions.assertEquals("Doe", driver.findElement(By.id("last-name")).getAttribute("value"));
        Assertions.assertEquals("john.doe@example.com", driver.findElement(By.id("email")).getAttribute("value"));
        Assertions.assertTrue(genderRadios.get(0).isSelected());
        Assertions.assertEquals("Developer", roleSelect.getFirstSelectedOption().getText());
        Assertions.assertTrue(checkboxes.get(0).isSelected());
        
        System.out.println("Multiple element interaction time: " + totalTime + "ms");
    }

    @Test
    @Order(5)
    @DisplayName("Test Form Submission Performance")
    void testFormSubmissionPerformance() {
        // Fill form with valid data
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("password")).sendKeys("SecurePass123!");
        
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        genderRadios.get(0).click();
        
        Select roleSelect = new Select(driver.findElement(By.id("role")));
        roleSelect.selectByVisibleText("Developer");
        
        // Test form submission performance
        long startTime = System.currentTimeMillis();
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Wait for submission to complete
        try {
            Thread.sleep(1000); // Allow time for submission
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        long submissionTime = endTime - startTime;
        
        // Assert form submission completes within 3 seconds
        Assertions.assertTrue(submissionTime < 3000, "Form submission should complete within 3 seconds, actual: " + submissionTime + "ms");
        
        System.out.println("Form submission time: " + submissionTime + "ms");
    }

    @Test
    @Order(6)
    @DisplayName("Test Repeated Form Interactions Performance")
    void testRepeatedFormInteractionsPerformance() {
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        
        long totalTime = 0;
        int iterations = 10;
        
        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();
            
            firstNameField.clear();
            firstNameField.sendKeys("Test" + i);
            
            long endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
        }
        
        long averageTime = totalTime / iterations;
        
        // Assert average interaction time is reasonable
        Assertions.assertTrue(averageTime < 50, "Average repeated interaction time should be less than 50ms, actual: " + averageTime + "ms");
        
        System.out.println("Average repeated interaction time: " + averageTime + "ms");
    }

    @Test
    @Order(7)
    @DisplayName("Test Large Text Input Performance")
    void testLargeTextInputPerformance() {
        WebElement commentTextarea = driver.findElement(By.id("comment"));
        
        // Create large text input
        StringBuilder largeText = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeText.append("This is line ").append(i).append(" of a very long comment. ");
        }
        
        long startTime = System.currentTimeMillis();
        commentTextarea.sendKeys(largeText.toString());
        long endTime = System.currentTimeMillis();
        long inputTime = endTime - startTime;
        
        // Assert large text input completes within 5 seconds
        Assertions.assertTrue(inputTime < 5000, "Large text input should complete within 5 seconds, actual: " + inputTime + "ms");
        
        // Verify text was entered correctly
        String enteredText = commentTextarea.getAttribute("value");
        Assertions.assertTrue(enteredText.length() > 0);
        Assertions.assertTrue(enteredText.contains("This is line 0"));
        Assertions.assertTrue(enteredText.contains("This is line 999"));
        
        System.out.println("Large text input time: " + inputTime + "ms");
    }

    @Test
    @Order(8)
    @DisplayName("Test Memory Usage During Form Interaction")
    void testMemoryUsageDuringFormInteraction() {
        Runtime runtime = Runtime.getRuntime();
        
        // Get initial memory usage
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Perform extensive form interactions
        for (int i = 0; i < 50; i++) {
            driver.findElement(By.id("first-name")).clear();
            driver.findElement(By.id("first-name")).sendKeys("Test" + i);
            
            driver.findElement(By.id("last-name")).clear();
            driver.findElement(By.id("last-name")).sendKeys("User" + i);
            
            Select roleSelect = new Select(driver.findElement(By.id("role")));
            roleSelect.selectByIndex(i % 5);
            
            List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
            if (i % 2 == 0) {
                checkboxes.get(i % checkboxes.size()).click();
            }
        }
        
        // Get final memory usage
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = finalMemory - initialMemory;
        
        // Assert memory increase is reasonable (less than 50MB)
        Assertions.assertTrue(memoryIncrease < 50 * 1024 * 1024, "Memory increase should be less than 50MB, actual: " + (memoryIncrease / 1024 / 1024) + "MB");
        
        System.out.println("Memory increase during form interaction: " + (memoryIncrease / 1024 / 1024) + "MB");
    }

    @Test
    @Order(9)
    @DisplayName("Test Page Refresh Performance")
    void testPageRefreshPerformance() {
        // Fill some form data
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        
        // Test page refresh performance
        long startTime = System.currentTimeMillis();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        long endTime = System.currentTimeMillis();
        long refreshTime = endTime - startTime;
        
        // Assert page refresh completes within 3 seconds
        Assertions.assertTrue(refreshTime < 3000, "Page refresh should complete within 3 seconds, actual: " + refreshTime + "ms");
        
        // Verify form is reset after refresh
        Assertions.assertEquals("", driver.findElement(By.id("first-name")).getAttribute("value"));
        Assertions.assertEquals("", driver.findElement(By.id("email")).getAttribute("value"));
        
        System.out.println("Page refresh time: " + refreshTime + "ms");
    }

    @Test
    @Order(10)
    @DisplayName("Test Concurrent Element Access Performance")
    void testConcurrentElementAccessPerformance() {
        long startTime = System.currentTimeMillis();
        
        // Access multiple elements simultaneously
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("submit"));
        
        // Verify all elements are accessible
        Assertions.assertTrue(firstNameField.isDisplayed());
        Assertions.assertTrue(lastNameField.isDisplayed());
        Assertions.assertTrue(emailField.isDisplayed());
        Assertions.assertTrue(passwordField.isDisplayed());
        Assertions.assertTrue(submitButton.isDisplayed());
        
        long endTime = System.currentTimeMillis();
        long accessTime = endTime - startTime;
        
        // Assert concurrent element access is fast
        Assertions.assertTrue(accessTime < 500, "Concurrent element access should complete within 500ms, actual: " + accessTime + "ms");
        
        System.out.println("Concurrent element access time: " + accessTime + "ms");
    }

    @Test
    @Order(11)
    @DisplayName("Test Form Validation Performance")
    void testFormValidationPerformance() {
        // Test validation performance with invalid data
        long startTime = System.currentTimeMillis();
        
        driver.findElement(By.id("email")).sendKeys("invalid-email");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Wait for validation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        long validationTime = endTime - startTime;
        
        // Assert validation completes quickly
        Assertions.assertTrue(validationTime < 1000, "Form validation should complete within 1 second, actual: " + validationTime + "ms");
        
        // Verify we're still on the form page (validation prevented submission)
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
        
        System.out.println("Form validation time: " + validationTime + "ms");
    }

    @Test
    @Order(12)
    @DisplayName("Test Browser Navigation Performance")
    void testBrowserNavigationPerformance() {
        String originalUrl = driver.getCurrentUrl();
        
        // Test navigation away and back
        long startTime = System.currentTimeMillis();
        driver.navigate().to("https://www.google.com");
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        long endTime = System.currentTimeMillis();
        long navigationTime = endTime - startTime;
        
        // Assert navigation completes within 10 seconds
        Assertions.assertTrue(navigationTime < 10000, "Browser navigation should complete within 10 seconds, actual: " + navigationTime + "ms");
        Assertions.assertEquals(originalUrl, driver.getCurrentUrl());
        
        System.out.println("Browser navigation time: " + navigationTime + "ms");
    }
}
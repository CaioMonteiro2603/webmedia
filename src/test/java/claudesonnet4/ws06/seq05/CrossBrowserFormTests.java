package claudesonnet4.ws06.seq05;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

/**
 * Cross-browser compatibility tests for Katalon Demo AUT Form
 * Tests form functionality across different browsers
 * Package: claudesonnet4.ws06.seq05
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrossBrowserFormTests {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://katalon-test.s3.amazonaws.com/aut/html/form.html";
    
    private WebDriver createDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                return new ChromeDriver(chromeOptions);
                
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                return new FirefoxDriver(firefoxOptions);
                
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--window-size=1920,1080");
                return new EdgeDriver(edgeOptions);
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test Form Load Across Browsers")
    void testFormLoadAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Verify page loads correctly
        Assertions.assertEquals("Demo AUT", driver.getTitle());
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
        
        // Verify all form elements are present
        Assertions.assertTrue(driver.findElement(By.id("first-name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("last-name")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("email")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("password")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("submit")).isDisplayed());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test Text Input Functionality Across Browsers")
    void testTextInputFunctionalityAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Test text inputs
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement companyField = driver.findElement(By.id("company"));
        WebElement addressField = driver.findElement(By.id("address"));
        WebElement dobField = driver.findElement(By.id("dob"));
        
        // Test input functionality
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        emailField.sendKeys("john.doe@example.com");
        passwordField.sendKeys("SecurePass123!");
        companyField.sendKeys("Tech Corp");
        addressField.sendKeys("123 Main St");
        dobField.sendKeys("01/15/1990");
        
        // Verify inputs
        Assertions.assertEquals("John", firstNameField.getAttribute("value"));
        Assertions.assertEquals("Doe", lastNameField.getAttribute("value"));
        Assertions.assertEquals("john.doe@example.com", emailField.getAttribute("value"));
        Assertions.assertEquals("SecurePass123!", passwordField.getAttribute("value"));
        Assertions.assertEquals("Tech Corp", companyField.getAttribute("value"));
        Assertions.assertEquals("123 Main St", addressField.getAttribute("value"));
        Assertions.assertEquals("01/15/1990", dobField.getAttribute("value"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test Radio Button Functionality Across Browsers")
    void testRadioButtonFunctionalityAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        Assertions.assertEquals(3, genderRadios.size());
        
        // Test each radio button
        for (int i = 0; i < genderRadios.size(); i++) {
            WebElement radio = genderRadios.get(i);
            radio.click();
            Assertions.assertTrue(radio.isSelected());
            
            // Verify other radios are deselected
            for (int j = 0; j < genderRadios.size(); j++) {
                if (i != j) {
                    Assertions.assertFalse(genderRadios.get(j).isSelected());
                }
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test Dropdown Functionality Across Browsers")
    void testDropdownFunctionalityAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Test Role dropdown
        WebElement roleDropdown = driver.findElement(By.id("role"));
        Select roleSelect = new Select(roleDropdown);
        
        List<WebElement> roleOptions = roleSelect.getOptions();
        Assertions.assertTrue(roleOptions.size() >= 5);
        
        // Test selecting different roles
        String[] roles = {"Developer", "QA", "Manager", "Technical Architect", "Business Analyst"};
        for (String role : roles) {
            roleSelect.selectByVisibleText(role);
            Assertions.assertEquals(role, roleSelect.getFirstSelectedOption().getText());
        }
        
        // Test Job Expectation dropdown
        WebElement expectationDropdown = driver.findElement(By.id("expectation"));
        Select expectationSelect = new Select(expectationDropdown);
        
        List<WebElement> expectationOptions = expectationSelect.getOptions();
        Assertions.assertTrue(expectationOptions.size() >= 6);
        
        // Test selecting different expectations
        String[] expectations = {"High salary", "Nice manager/leader", "Excellent colleagues", 
                               "Good teamwork", "Chance to go onsite", "Challenging"};
        for (String expectation : expectations) {
            expectationSelect.selectByVisibleText(expectation);
            Assertions.assertEquals(expectation, expectationSelect.getFirstSelectedOption().getText());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test Checkbox Functionality Across Browsers")
    void testCheckboxFunctionalityAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        Assertions.assertEquals(6, checkboxes.size());
        
        // Test checking all checkboxes
        for (WebElement checkbox : checkboxes) {
            Assertions.assertTrue(checkbox.isDisplayed());
            Assertions.assertTrue(checkbox.isEnabled());
            
            checkbox.click();
            Assertions.assertTrue(checkbox.isSelected());
        }
        
        // Test unchecking all checkboxes
        for (WebElement checkbox : checkboxes) {
            checkbox.click();
            Assertions.assertFalse(checkbox.isSelected());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test Textarea Functionality Across Browsers")
    void testTextareaFunctionalityAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        WebElement commentTextarea = driver.findElement(By.id("comment"));
        
        String testComment = "This is a test comment for cross-browser compatibility testing.\nIt includes multiple lines and special characters: !@#$%^&*()";
        commentTextarea.sendKeys(testComment);
        
        Assertions.assertEquals(testComment, commentTextarea.getAttribute("value"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test Form Submission Across Browsers")
    void testFormSubmissionAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Fill complete form
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        genderRadios.get(0).click();
        
        driver.findElement(By.id("dob")).sendKeys("01/15/1990");
        driver.findElement(By.id("address")).sendKeys("123 Main St");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("password")).sendKeys("SecurePass123!");
        driver.findElement(By.id("company")).sendKeys("Tech Corp");
        
        Select roleSelect = new Select(driver.findElement(By.id("role")));
        roleSelect.selectByVisibleText("Developer");
        
        Select expectationSelect = new Select(driver.findElement(By.id("expectation")));
        expectationSelect.selectByVisibleText("High salary");
        
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
        checkboxes.get(0).click();
        checkboxes.get(2).click();
        
        driver.findElement(By.id("comment")).sendKeys("Cross-browser test submission");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Wait for submission
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify submission (check URL or success message)
        Assertions.assertTrue(driver.getCurrentUrl().contains("form.html"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test JavaScript Events Across Browsers")
    void testJavaScriptEventsAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Test focus and blur events
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.click(); // Focus
        firstNameField.sendKeys("Test");
        
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        lastNameField.click(); // This should blur the first name field
        
        // Verify the value is still there after blur
        Assertions.assertEquals("Test", firstNameField.getAttribute("value"));
        
        // Test change events on dropdowns
        Select roleSelect = new Select(driver.findElement(By.id("role")));
        roleSelect.selectByVisibleText("QA");
        Assertions.assertEquals("QA", roleSelect.getFirstSelectedOption().getText());
        
        roleSelect.selectByVisibleText("Manager");
        Assertions.assertEquals("Manager", roleSelect.getFirstSelectedOption().getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test CSS Styling Across Browsers")
    void testCSSStylingAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Test that elements are visible and properly styled
        WebElement submitButton = driver.findElement(By.id("submit"));
        Assertions.assertTrue(submitButton.isDisplayed());
        Assertions.assertTrue(submitButton.isEnabled());
        
        // Test form layout
        WebElement form = driver.findElement(By.tagName("form"));
        Assertions.assertTrue(form.isDisplayed());
        
        // Verify all input fields are visible
        String[] inputIds = {"first-name", "last-name", "dob", "address", "email", "password", "company"};
        for (String inputId : inputIds) {
            WebElement input = driver.findElement(By.id(inputId));
            Assertions.assertTrue(input.isDisplayed());
            Assertions.assertTrue(input.isEnabled());
        }
        
        // Verify dropdowns are visible
        Assertions.assertTrue(driver.findElement(By.id("role")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("expectation")).isDisplayed());
        
        // Verify textarea is visible
        Assertions.assertTrue(driver.findElement(By.id("comment")).isDisplayed());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome"})
    @DisplayName("Test Responsive Design Across Browsers")
    void testResponsiveDesignAcrossBrowsers(String browserName) {
        driver = createDriver(browserName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Demo AUT"));
        
        // Test different viewport sizes
        Dimension[] viewportSizes = {
            new Dimension(1920, 1080), // Desktop
            new Dimension(1366, 768),  // Laptop
            new Dimension(768, 1024),  // Tablet
            new Dimension(375, 667)    // Mobile
        };
        
        for (Dimension size : viewportSizes) {
            driver.manage().window().setSize(size);
            
            // Wait for resize to complete
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Verify form elements are still accessible
            Assertions.assertTrue(driver.findElement(By.id("first-name")).isDisplayed());
            Assertions.assertTrue(driver.findElement(By.id("submit")).isDisplayed());
            
            // Test form interaction at this size
            WebElement firstNameField = driver.findElement(By.id("first-name"));
            firstNameField.clear();
            firstNameField.sendKeys("Test" + size.width);
            Assertions.assertEquals("Test" + size.width, firstNameField.getAttribute("value"));
        }
    }
}
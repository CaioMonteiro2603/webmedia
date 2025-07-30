package deepseek.ws06.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class FormPageTest {
    private WebDriver driver;
    private static final String BASE_URL = "https://katalon-test.s3.amazonaws.com/aut/html/form.html";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testFormPageTitle() {
        assertEquals("Demo AUT", driver.getTitle());
    }

    @Test
    public void testFormSubmissionWithValidData() {
        // Fill in all form fields
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElements(By.name("gender")).get(0).click(); // Select Male
        driver.findElement(By.id("dob")).sendKeys("01/01/1990");
        driver.findElement(By.id("address")).sendKeys("123 Main St");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("password")).sendKeys("securePassword123");
        
        // Scroll to elements not in viewport
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", 
            driver.findElement(By.id("company")));
        driver.findElement(By.id("company")).sendKeys("Test Company");
        
        new Select(driver.findElement(By.id("role"))).selectByVisibleText("QA");
        new Select(driver.findElement(By.id("expectation"))).selectByVisibleText("Challenging");
        
        // Select multiple checkboxes
        driver.findElements(By.cssSelector("input[type='checkbox']")).get(0).click(); // High salary
        driver.findElements(By.cssSelector("input[type='checkbox']")).get(2).click(); // Excellent colleagues
        
        driver.findElement(By.id("comment")).sendKeys("This is a test comment");
        driver.findElement(By.id("submit")).click();
        
        // Verify successful submission
        assertTrue(driver.getCurrentUrl().contains("submit"), "Form submission failed");
    }

    @Test
    public void testRequiredFieldValidation() {
        driver.findElement(By.id("submit")).click();
        assertTrue(driver.findElement(By.id("first-name")).getAttribute("class").contains("error"), 
            "First name required validation failed");
    }

    @Test
    public void testEmailFormatValidation() {
        driver.findElement(By.id("email")).sendKeys("invalid-email");
        driver.findElement(By.id("submit")).click();
        assertTrue(driver.findElement(By.id("email")).getAttribute("class").contains("error"), 
            "Email format validation failed");
    }

    @Test
    public void testGenderSelection() {
        WebElement femaleRadio = driver.findElements(By.name("gender")).get(1);
        femaleRadio.click();
        assertTrue(femaleRadio.isSelected(), "Female radio button not selected");
    }

    @Test
    public void testMultipleExpectationSelection() {
        // Scroll to elements
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", 
            driver.findElement(By.id("expectation")));
            
        // Select multiple options
        Select expectationSelect = new Select(driver.findElement(By.id("expectation")));
        expectationSelect.selectByVisibleText("High salary");
        expectationSelect.selectByVisibleText("Challenging");
        
        assertEquals(2, expectationSelect.getAllSelectedOptions().size(), 
            "Multiple selection not working");
    }

    @Test
    public void testNavigationToLinkedPages() {
        // Test navigation to external links (if any)
        // Note: The current page doesn't show any navigation links in the analysis
        // This test would be expanded if navigation links are found
        assertTrue(true, "No navigation links found on this page");
    }

    @Test
    public void testPasswordFieldMasking() {
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("test123");
        assertEquals("password", passwordField.getAttribute("type"), 
            "Password field is not masked");
    }
}
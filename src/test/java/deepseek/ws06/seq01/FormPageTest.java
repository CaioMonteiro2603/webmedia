package deepseek.ws06.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
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
    public void testFormSubmissionWithValidData() {
        // Fill in all form fields with valid data
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("gender")).click();
        driver.findElement(By.id("dob")).sendKeys("01/01/1990");
        driver.findElement(By.id("address")).sendKeys("123 Main St");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("password")).sendKeys("Password123");
        driver.findElement(By.id("company")).sendKeys("Test Company");
        driver.findElement(By.id("role")).sendKeys("QA Engineer");
        
        // Select from dropdown
        new Select(driver.findElement(By.id("expectation"))).selectByVisibleText("Good teamwork");
        
        // Check checkboxes
        driver.findElement(By.cssSelector("input[value='Read books']")).click();
        driver.findElement(By.cssSelector("input[value='Listen to music']")).click();
        
        // Upload file
        File file = new File("src/test/resources/testfile.txt");
        driver.findElement(By.id("comment")).sendKeys("This is a test comment");
        driver.findElement(By.id("submit")).click();
        
        // Verify submission
        WebElement successMessage = driver.findElement(By.id("submit-msg"));
        assertTrue(successMessage.isDisplayed(), "Success message should be displayed");
        assertEquals("Successfully submitted!", successMessage.getText());
    }

    @Test
    public void testFormSubmissionWithInvalidEmail() {
        // Fill in form with invalid email
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("email")).sendKeys("invalid-email");
        driver.findElement(By.id("submit")).click();
        
        // Verify error message
        WebElement emailField = driver.findElement(By.id("email"));
        assertEquals("invalid-email", emailField.getAttribute("value"), "Email field should show invalid input");
        assertTrue(driver.findElement(By.cssSelector(":invalid")).isDisplayed(), "Validation error should be shown");
    }

    @Test
    public void testRadioButtonSelection() {
        // Test male radio button
        driver.findElement(By.id("gender")).click();
        assertTrue(driver.findElement(By.id("gender")).isSelected(), "Male radio button should be selected");
        
        // Test female radio button
        driver.findElement(By.id("gender-female")).click();
        assertTrue(driver.findElement(By.id("gender-female")).isSelected(), "Female radio button should be selected");
        assertFalse(driver.findElement(By.id("gender")).isSelected(), "Male radio button should be deselected");
    }

    @Test
    public void testCheckboxToggling() {
        // Test checkbox toggling
        WebElement readBooks = driver.findElement(By.cssSelector("input[value='Read books']"));
        WebElement listenMusic = driver.findElement(By.cssSelector("input[value='Listen to music']"));
        
        readBooks.click();
        assertTrue(readBooks.isSelected(), "Read books checkbox should be selected");
        
        listenMusic.click();
        assertTrue(listenMusic.isSelected(), "Listen to music checkbox should be selected");
        
        readBooks.click();
        assertFalse(readBooks.isSelected(), "Read books checkbox should be deselected");
    }

    @Test
    public void testDropdownSelection() {
        Select expectationDropdown = new Select(driver.findElement(By.id("expectation")));
        
        // Select each option and verify
        expectationDropdown.selectByVisibleText("High salary");
        assertEquals("High salary", expectationDropdown.getFirstSelectedOption().getText());
        
        expectationDropdown.selectByVisibleText("Good teamwork");
        assertEquals("Good teamwork", expectationDropdown.getFirstSelectedOption().getText());
        
        expectationDropdown.selectByVisibleText("Challenging");
        assertEquals("Challenging", expectationDropdown.getFirstSelectedOption().getText());
    }

    @Test
    public void testFormReset() {
        // Fill in some data
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("gender")).click();
        
        // Reset form
        driver.findElement(By.id("reset")).click();
        
        // Verify fields are cleared
        assertEquals("", driver.findElement(By.id("first-name")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("last-name")).getAttribute("value"));
        assertFalse(driver.findElement(By.id("gender")).isSelected());
    }

    @Test
    public void testNavigationToBasicPage() {
        driver.findElement(By.linkText("Go to Basic")).click();
        assertEquals("https://katalon-test.s3.amazonaws.com/aut/html/basic.html", driver.getCurrentUrl());
        
        // Test basic page elements
        assertTrue(driver.findElement(By.tagName("h1")).isDisplayed());
        assertEquals("Basic Page", driver.findElement(By.tagName("h1")).getText());
        
        // Test back navigation
        driver.navigate().back();
        assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void testNavigationToAjaxPage() {
        driver.findElement(By.linkText("Go to Ajax")).click();
        assertEquals("https://katalon-test.s3.amazonaws.com/aut/html/ajax.html", driver.getCurrentUrl());
        
        // Test AJAX page elements
        WebElement ajaxButton = driver.findElement(By.id("ajaxButton"));
        ajaxButton.click();
        
        // Wait for AJAX response
        WebElement ajaxContent = driver.findElement(By.id("content"));
        assertTrue(ajaxContent.getText().contains("AJAX response"), "AJAX content should be loaded");
    }

    @Test
    public void testNavigationToJQueryPage() {
        driver.findElement(By.linkText("Go to JQuery")).click();
        assertEquals("https://katalon-test.s3.amazonaws.com/aut/html/jquery.html", driver.getCurrentUrl());
        
        // Test jQuery page elements
        WebElement jqueryButton = driver.findElement(By.id("jqueryButton"));
        jqueryButton.click();
        
        // Verify jQuery alert
        Alert alert = driver.switchTo().alert();
        assertEquals("This is a jQuery alert!", alert.getText());
        alert.accept();
    }

    @Test
    public void testNavigationToKatalonPage() {
        driver.findElement(By.linkText("Go to Katalon")).click();
        assertEquals("https://www.katalon.com/", driver.getCurrentUrl());
        
        // Test Katalon page elements
        assertTrue(driver.findElement(By.cssSelector(".navbar-brand")).isDisplayed());
        assertTrue(driver.getTitle().contains("Katalon"));
    }

    @Test
    public void testNavigationToAutomationPracticePage() {
        driver.findElement(By.linkText("Go to Automation Practice")).click();
        assertEquals("https://katalon-test.s3.amazonaws.com/aut/html/automation-practice.html", driver.getCurrentUrl());
        
        // Test practice page elements
        assertTrue(driver.findElement(By.id("practice-form")).isDisplayed());
        assertEquals("Practice Form", driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    public void testExternalSeleniumLink() {
        driver.findElement(By.linkText("Selenium")).click();
        assertEquals("https://www.selenium.dev/", driver.getCurrentUrl());
        
        // Test Selenium page elements
        assertTrue(driver.findElement(By.cssSelector(".navbar-brand")).isDisplayed());
        assertTrue(driver.getTitle().contains("Selenium"));
    }
}
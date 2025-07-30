package deepseek.ws06.seq03;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class FormPageTest {
    private WebDriver driver;
    private String baseUrl = "https://katalon-test.s3.amazonaws.com/aut/html/form.html";

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @Test
    public void testPageTitle() {
        assertEquals("A Simple Form", driver.getTitle());
    }

    @Test
    public void testFirstNameInput() {
        WebElement firstName = driver.findElement(By.id("first-name"));
        firstName.sendKeys("John");
        assertEquals("John", firstName.getAttribute("value"));
    }

    @Test
    public void testLastNameInput() {
        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Doe");
        assertEquals("Doe", lastName.getAttribute("value"));
    }

    @Test
    public void testGenderRadioButtons() {
        WebElement maleRadio = driver.findElement(By.id("genderMale"));
        WebElement femaleRadio = driver.findElement(By.id("genderFemale"));

        // Test male radio button
        maleRadio.click();
        assertTrue(maleRadio.isSelected());
        assertFalse(femaleRadio.isSelected());

        // Test female radio button
        femaleRadio.click();
        assertTrue(femaleRadio.isSelected());
        assertFalse(maleRadio.isSelected());
    }

    @Test
    public void testDateOfBirthInput() {
        WebElement dob = driver.findElement(By.id("dob"));
        dob.sendKeys("01/01/1990");
        assertEquals("01/01/1990", dob.getAttribute("value"));
    }

    @Test
    public void testAddressInput() {
        WebElement address = driver.findElement(By.id("address"));
        address.sendKeys("123 Main St");
        assertEquals("123 Main St", address.getAttribute("value"));
    }

    @Test
    public void testEmailInput() {
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@example.com");
        assertEquals("test@example.com", email.getAttribute("value"));
    }

    @Test
    public void testPasswordInput() {
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secure123");
        assertEquals("secure123", password.getAttribute("value"));
    }

    @Test
    public void testCompanyInput() {
        WebElement company = driver.findElement(By.id("company"));
        company.sendKeys("Test Company");
        assertEquals("Test Company", company.getAttribute("value"));
    }

    @Test
    public void testRoleDropdown() {
        Select roleDropdown = new Select(driver.findElement(By.id("role")));
        
        // Test selecting QA option
        roleDropdown.selectByVisibleText("QA");
        assertEquals("QA", roleDropdown.getFirstSelectedOption().getText());
        
        // Test selecting Developer option
        roleDropdown.selectByVisibleText("Developer");
        assertEquals("Developer", roleDropdown.getFirstSelectedOption().getText());
    }

    @Test
    public void testJobExpectationCheckboxes() {
        WebElement highSalary = driver.findElement(By.id("expectation-high-salary"));
        WebElement goodTeam = driver.findElement(By.id("expectation-good-team"));
        WebElement challenging = driver.findElement(By.id("expectation-challenging"));

        // Test individual checkboxes
        highSalary.click();
        assertTrue(highSalary.isSelected());
        
        goodTeam.click();
        assertTrue(goodTeam.isSelected());
        
        challenging.click();
        assertTrue(challenging.isSelected());

        // Test unchecking
        highSalary.click();
        assertFalse(highSalary.isSelected());
    }

    @Test
    public void testFileUpload() {
        WebElement fileInput = driver.findElement(By.id("file"));
        File file = new File("testfile.txt");
        fileInput.sendKeys(file.getAbsolutePath());
        assertTrue(fileInput.getAttribute("value").contains("testfile.txt"));
    }

    @Test
    public void testCommentTextarea() {
        WebElement comment = driver.findElement(By.id("comment"));
        comment.sendKeys("This is a test comment");
        assertEquals("This is a test comment", comment.getAttribute("value"));
    }

    @Test
    public void testSubmitButton() {
        // Fill out required fields
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("genderMale")).click();
        driver.findElement(By.id("dob")).sendKeys("01/01/1990");
        driver.findElement(By.id("address")).sendKeys("123 Main St");
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("secure123");
        driver.findElement(By.id("company")).sendKeys("Test Company");
        
        // Submit the form
        driver.findElement(By.id("submit")).click();
        
        // Verify submission (assuming successful submission shows a message)
        WebElement successMessage = driver.findElement(By.id("submit-msg"));
        assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testNavigationToGoogle() {
        // Click on the Google link
        driver.findElement(By.linkText("Go to Google")).click();
        
        // Switch to new tab
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify we're on Google
        assertTrue(driver.getTitle().contains("Google"));
        
        // Close the Google tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    public void testNavigationToDemoPage() {
        // Click on the Demo Page link
        driver.findElement(By.linkText("Go to Demo Page")).click();
        
        // Verify we're on the demo page
        assertEquals("Demo Page", driver.getTitle());
        
        // Test elements on demo page
        WebElement header = driver.findElement(By.tagName("h1"));
        assertEquals("This is a demo page", header.getText());
        
        // Go back to form page
        driver.navigate().back();
        assertEquals("A Simple Form", driver.getTitle());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
package deepseek.ws06.seq04;

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
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPageTitle() {
        assertEquals("Demo AUT", driver.getTitle());
    }

    @Test
    public void testFormElementsPresence() {
        assertTrue(driver.findElement(By.id("first-name")).isDisplayed());
        assertTrue(driver.findElement(By.id("last-name")).isDisplayed());
        assertTrue(driver.findElement(By.id("dob")).isDisplayed());
        assertTrue(driver.findElement(By.id("address")).isDisplayed());
        assertTrue(driver.findElement(By.id("email")).isDisplayed());
        assertTrue(driver.findElement(By.id("password")).isDisplayed());
        assertTrue(driver.findElement(By.id("company")).isDisplayed());
        assertTrue(driver.findElement(By.id("role")).isDisplayed());
        assertTrue(driver.findElement(By.id("expectation")).isDisplayed());
        assertTrue(driver.findElement(By.id("comment")).isDisplayed());
        assertTrue(driver.findElement(By.id("submit")).isDisplayed());
    }

    @Test
    public void testTextInputFields() {
        WebElement firstName = driver.findElement(By.id("first-name"));
        firstName.sendKeys("John");
        assertEquals("John", firstName.getAttribute("value"));

        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Doe");
        assertEquals("Doe", lastName.getAttribute("value"));

        WebElement address = driver.findElement(By.id("address"));
        address.sendKeys("123 Main St");
        assertEquals("123 Main St", address.getAttribute("value"));
    }

    @Test
    public void testRadioButtons() {
        WebElement maleRadio = driver.findElements(By.name("gender")).get(0);
        WebElement femaleRadio = driver.findElements(By.name("gender")).get(1);
        WebElement inBetweenRadio = driver.findElements(By.name("gender")).get(2);

        assertFalse(maleRadio.isSelected());
        assertFalse(femaleRadio.isSelected());
        assertFalse(inBetweenRadio.isSelected());

        maleRadio.click();
        assertTrue(maleRadio.isSelected());
        assertFalse(femaleRadio.isSelected());
        assertFalse(inBetweenRadio.isSelected());

        femaleRadio.click();
        assertFalse(maleRadio.isSelected());
        assertTrue(femaleRadio.isSelected());
        assertFalse(inBetweenRadio.isSelected());
    }

    @Test
    public void testDateInput() {
        WebElement dob = driver.findElement(By.id("dob"));
        dob.sendKeys("01/01/1990");
        assertEquals("01/01/1990", dob.getAttribute("value"));
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
        password.sendKeys("securePassword123");
        assertEquals("password", password.getAttribute("type"));
        assertEquals("securePassword123", password.getAttribute("value"));
    }

    @Test
    public void testDropdownSelections() {
        Select roleDropdown = new Select(driver.findElement(By.id("role")));
        roleDropdown.selectByVisibleText("QA");
        assertEquals("QA", roleDropdown.getFirstSelectedOption().getText());

        Select expectationDropdown = new Select(driver.findElement(By.id("expectation")));
        expectationDropdown.selectByVisibleText("Challenging");
        assertEquals("Challenging", expectationDropdown.getFirstSelectedOption().getText());
    }

    @Test
    public void testCheckboxes() {
        WebElement developmentCheckbox = driver.findElements(By.cssSelector("input[type='checkbox']")).get(0);
        WebElement marketingCheckbox = driver.findElements(By.cssSelector("input[type='checkbox']")).get(1);
        WebElement accountingCheckbox = driver.findElements(By.cssSelector("input[type='checkbox']")).get(2);
        WebElement adminCheckbox = driver.findElements(By.cssSelector("input[type='checkbox']")).get(3);
        WebElement otherCheckbox = driver.findElements(By.cssSelector("input[type='checkbox']")).get(4);

        developmentCheckbox.click();
        assertTrue(developmentCheckbox.isSelected());

        marketingCheckbox.click();
        assertTrue(marketingCheckbox.isSelected());

        // Test unchecking
        developmentCheckbox.click();
        assertFalse(developmentCheckbox.isSelected());
    }

    @Test
    public void testTextArea() {
        WebElement comment = driver.findElement(By.id("comment"));
        comment.sendKeys("This is a test comment");
        assertEquals("This is a test comment", comment.getAttribute("value"));
    }

    @Test
    public void testFormSubmission() {
        // Fill out the form
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElements(By.name("gender")).get(0).click();
        driver.findElement(By.id("dob")).sendKeys("01/01/1990");
        driver.findElement(By.id("address")).sendKeys("123 Test St");
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("company")).sendKeys("Test Company");
        
        new Select(driver.findElement(By.id("role"))).selectByVisibleText("QA");
        new Select(driver.findElement(By.id("expectation"))).selectByVisibleText("Challenging");
        
        driver.findElements(By.cssSelector("input[type='checkbox']")).get(0).click();
        driver.findElement(By.id("comment")).sendKeys("Test comment");

        // Submit the form
        driver.findElement(By.id("submit")).click();

        // Verify submission (assuming it redirects or shows success message)
        // This would need to be updated based on actual submission behavior
        assertNotEquals(BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void testRequiredFieldValidation() {
        driver.findElement(By.id("submit")).click();
        
        // Verify validation messages (implementation depends on actual validation)
        // This is a placeholder - actual validation would need to be implemented
        // based on how the form handles validation
        assertTrue(driver.findElement(By.id("first-name")).getAttribute("class").contains("error") ||
                 driver.findElement(By.id("first-name")).getAttribute("required") != null);
    }
}
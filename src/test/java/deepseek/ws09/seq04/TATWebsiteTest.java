package deepseek.ws09.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TATWebsiteTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com/index.html";

    @BeforeAll
    public static void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    public void beforeEach() {
        driver.get(BASE_URL);
    }

    @Test
    public void testMainPageElements() {
        // Verify title
        Assertions.assertEquals("TAT - Test Automation Training", driver.getTitle());

        // Verify header
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("body > div.container > h1")));
        Assertions.assertEquals("TAT - Test Automation Training", header.getText());

        // Verify subtitle
        WebElement subtitle = driver.findElement(By.cssSelector("body > div.container > p"));
        Assertions.assertEquals("This is a sample page for test automation training.", subtitle.getText());

        // Verify all links are present
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Assertions.assertEquals(4, links.size());
    }

    @Test
    public void testFormPageNavigation() {
        // Click on Form link
        WebElement formLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("body > div.container > ul > li:nth-child(1) > a")));
        formLink.click();

        // Verify form page title
        Assertions.assertEquals("TAT - Form", driver.getTitle());

        // Verify form elements
        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement phone = driver.findElement(By.id("phone"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        Assertions.assertTrue(firstName.isDisplayed());
        Assertions.assertTrue(lastName.isDisplayed());
        Assertions.assertTrue(email.isDisplayed());
        Assertions.assertTrue(phone.isDisplayed());
        Assertions.assertTrue(submitButton.isDisplayed());
    }

    @Test
    public void testFormSubmission() {
        // Navigate to form page
        driver.get(BASE_URL.replace("index.html", "form.html"));

        // Fill out form
        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        firstName.sendKeys("John");

        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Doe");

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("john.doe@example.com");

        WebElement phone = driver.findElement(By.id("phone"));
        phone.sendKeys("1234567890");

        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Verify success message
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".success")));
        Assertions.assertEquals("Form submitted successfully!", successMessage.getText());
    }

    @Test
    public void testListPageNavigation() {
        // Click on List link
        WebElement listLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("body > div.container > ul > li:nth-child(2) > a")));
        listLink.click();

        // Verify list page title
        Assertions.assertEquals("TAT - List", driver.getTitle());

        // Verify list items
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.cssSelector("body > div.container > ul > li")));
        Assertions.assertEquals(5, items.size());
    }

    @Test
    public void testExternalLinks() {
        // Click on Google link
        WebElement googleLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("body > div.container > ul > li:nth-child(3) > a")));
        googleLink.click();

        // Switch to new tab
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Verify Google page
        Assertions.assertTrue(driver.getTitle().contains("Google"));
        driver.close();
        driver.switchTo().window(originalWindow);

        // Click on GitHub link
        WebElement githubLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("body > div.container > ul > li:nth-child(4) > a")));
        githubLink.click();

        // Switch to new tab
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Verify GitHub page
        Assertions.assertTrue(driver.getTitle().contains("GitHub"));
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    public void testListItemsContent() {
        // Navigate to list page
        driver.get(BASE_URL.replace("index.html", "list.html"));

        // Verify list items content
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.cssSelector("body > div.container > ul > li")));

        String[] expectedItems = {
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5"
        };

        for (int i = 0; i < items.size(); i++) {
            Assertions.assertEquals(expectedItems[i], items.get(i).getText());
        }
    }

    @Test
    public void testFormValidation() {
        // Navigate to form page
        driver.get(BASE_URL.replace("index.html", "form.html"));

        // Try to submit empty form
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[type='submit']")));
        submitButton.click();

        // Verify validation messages
        WebElement firstNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("first-name-error")));
        Assertions.assertEquals("First name is required", firstNameError.getText());

        WebElement lastNameError = driver.findElement(By.id("last-name-error"));
        Assertions.assertEquals("Last name is required", lastNameError.getText());

        WebElement emailError = driver.findElement(By.id("email-error"));
        Assertions.assertEquals("Email is required", emailError.getText());

        WebElement phoneError = driver.findElement(By.id("phone-error"));
        Assertions.assertEquals("Phone is required", phoneError.getText());
    }

    @Test
    public void testEmailValidation() {
        // Navigate to form page
        driver.get(BASE_URL.replace("index.html", "form.html"));

        // Enter invalid email
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        email.sendKeys("invalid-email");

        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Verify email validation message
        WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("email-error")));
        Assertions.assertEquals("Please enter a valid email", emailError.getText());
    }

    @Test
    public void testPhoneValidation() {
        // Navigate to form page
        driver.get(BASE_URL.replace("index.html", "form.html"));

        // Enter invalid phone
        WebElement phone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
        phone.sendKeys("abc");

        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Verify phone validation message
        WebElement phoneError = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("phone-error")));
        Assertions.assertEquals("Please enter a valid phone number", phoneError.getText());
    }
}
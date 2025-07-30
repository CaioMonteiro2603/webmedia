package deepseek.ws05.seq02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SelectorsHubTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String mainWindowHandle;

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
        // Navigate to main page
        driver.get("https://selectorshub.com/xpath-practice-page/");
        mainWindowHandle = driver.getWindowHandle();
    }

    @AfterEach
    public void tearDown() {
        // Close all windows except main window
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle).close();
            }
        }
        
        // Switch back to main window and close driver
        driver.switchTo().window(mainWindowHandle);
        driver.quit();
    }

    @Test
    public void testPageTitle() {
        String expectedTitle = "Xpath Practice Page | Shadow dom, nested shadow dom, iframe, nested iframe and more complex automation scenarios.";
        Assertions.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testFormElements() {
        // Test email field
        WebElement emailField = driver.findElement(By.id("userId"));
        emailField.sendKeys("test@example.com");
        Assertions.assertEquals("test@example.com", emailField.getAttribute("value"));

        // Test password field
        WebElement passwordField = driver.findElement(By.id("pass"));
        passwordField.sendKeys("password123");
        Assertions.assertEquals("password123", passwordField.getAttribute("value"));

        // Test company field
        WebElement companyField = driver.findElement(By.name("company"));
        companyField.sendKeys("Test Company");
        Assertions.assertEquals("Test Company", companyField.getAttribute("value"));

        // Test mobile number field
        WebElement mobileField = driver.findElement(By.name("mobile number"));
        mobileField.sendKeys("1234567890");
        Assertions.assertEquals("1234567890", mobileField.getAttribute("value"));
    }

    @Test
    public void testDropdownSelection() {
        WebElement dropdown = driver.findElement(By.id("cars"));
        dropdown.click();
        
        // Select Audi from dropdown
        driver.findElement(By.xpath("//select[@id='cars']/option[text()='Audi']")).click();
        Assertions.assertEquals("Audi", dropdown.getAttribute("value"));
    }

    @Test
    public void testDatePicker() {
        WebElement datePicker = driver.findElement(By.id("datepicker"));
        datePicker.sendKeys("01/01/2025");
        Assertions.assertEquals("01/01/2025", datePicker.getAttribute("value"));
    }

    @Test
    public void testCheckoutButton() {
        WebElement checkoutButton = driver.findElement(By.xpath("//button[contains(text(),'Checkout here')]"));
        checkoutButton.click();
        
        // Verify alert is present
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(alert);
        alert.accept();
    }

    @Test
    public void testSubmitButton() {
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        submitButton.click();
        
        // Verify alert is present
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(alert);
        alert.accept();
    }

    @Test
    public void testFirstCrushField() {
        // Scroll to element
        WebElement crushField = driver.findElement(By.id("inp_val"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", crushField);
        
        crushField.sendKeys("My First Crush");
        Assertions.assertEquals("My First Crush", crushField.getAttribute("value"));
    }

    @Test
    public void testTableCheckboxes() {
        // Scroll to table
        WebElement table = driver.findElement(By.xpath("//table[@id='ohrmList_chkSelectRecord_1']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", table);
        
        // Test select all checkbox
        WebElement selectAll = driver.findElement(By.id("ohrmList_chkSelectAll"));
        selectAll.click();
        Assertions.assertTrue(selectAll.isSelected());
        
        // Test individual checkboxes
        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[contains(@id,'ohrmList_chkSelectRecord_')]"));
        for (WebElement checkbox : checkboxes) {
            Assertions.assertTrue(checkbox.isSelected());
        }
    }

    @Test
    public void testModalWindow() {
        // Scroll to modal button
        WebElement modalButton = driver.findElement(By.id("myBtn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", modalButton);
        
        // Open modal
        modalButton.click();
        
        // Verify modal is displayed
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModal")));
        Assertions.assertTrue(modal.isDisplayed());
        
        // Close modal
        driver.findElement(By.xpath("//span[text()='×']")).click();
        wait.until(ExpectedConditions.invisibilityOf(modal));
    }

    @Test
    public void testFileUpload() {
        // Scroll to file upload
        WebElement fileUpload = driver.findElement(By.id("myFile"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fileUpload);
        
        // Upload a file (path needs to be adjusted based on actual test file)
        fileUpload.sendKeys("/path/to/testfile.txt");
        Assertions.assertTrue(fileUpload.getAttribute("value").contains("testfile.txt"));
    }

    @Test
    public void testAlertButtons() {
        // Test alert button
        WebElement alertButton = driver.findElement(By.xpath("//button[contains(text(),'Click To Open Window Alert')]"));
        alertButton.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(alert);
        alert.accept();
        
        // Test prompt button
        WebElement promptButton = driver.findElement(By.xpath("//button[contains(text(),'Click To Open Window Prompt Alert')]"));
        promptButton.click();
        Alert prompt = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(prompt);
        prompt.sendKeys("Test Input");
        prompt.accept();
    }

    @Test
    public void testBrokenLink() {
        WebElement brokenLink = driver.findElement(By.xpath("//a[text()='This is a broken link']"));
        brokenLink.click();
        
        // Verify page not found
        wait.until(ExpectedConditions.titleContains("404"));
        Assertions.assertTrue(driver.getTitle().contains("404"));
    }

    @Test
    public void testIframeLink() {
        WebElement iframeLink = driver.findElement(By.xpath("//a[contains(text(),'Click here to practice iframe and nested iframe scenarios')]"));
        iframeLink.click();
        
        // Verify new page loads
        wait.until(ExpectedConditions.titleContains("Iframe"));
        Assertions.assertTrue(driver.getTitle().contains("Iframe"));
    }

    @Test
    public void testShadowDomLink() {
        WebElement shadowDomLink = driver.findElement(By.xpath("//a[contains(text(),'Click to practice shadow dom inside iframe scenario')]"));
        shadowDomLink.click();
        
        // Verify new page loads
        wait.until(ExpectedConditions.titleContains("Shadow DOM"));
        Assertions.assertTrue(driver.getTitle().contains("Shadow DOM"));
    }

    @Test
    public void testDownloadLink() {
        WebElement downloadLink = driver.findElement(By.xpath("//a[text()='DownLoad Link']"));
        downloadLink.click();
        
        // Verify new tab opens (actual download verification would require additional setup)
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Assertions.assertEquals(2, driver.getWindowHandles().size());
    }

    @Test
    public void testYoutubeChannelLink() {
        WebElement youtubeLink = driver.findElement(By.xpath("//a[contains(text(),'SelectorsHub Youtube Channel')]"));
        youtubeLink.click();
        
        // Switch to new tab and verify URL
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        Assertions.assertTrue(driver.getCurrentUrl().contains("youtube.com"));
    }

    @Test
    public void testCourseLink() {
        WebElement courseLink = driver.findElement(By.xpath("//a[text()='Course Link']"));
        courseLink.click();
        
        // Switch to new tab and verify URL
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        Assertions.assertTrue(driver.getCurrentUrl().contains("udemy.com"));
    }

    @Test
    public void testDonationLink() {
        WebElement donationLink = driver.findElement(By.xpath("//a[contains(text(),'Consider a small Donation')]"));
        donationLink.click();
        
        // Verify new page loads
        wait.until(ExpectedConditions.titleContains("Donate"));
        Assertions.assertTrue(driver.getTitle().contains("Donate"));
    }

    @Test
    public void testSocialMediaLinks() {
        // Test Twitter link
        testSocialLink("Twitter", "twitter.com");
        
        // Test YouTube link
        testSocialLink("Youtube", "youtube.com");
        
        // Test LinkedIn link
        testSocialLink("Linkedin", "linkedin.com");
        
        // Test Facebook link
        testSocialLink("Facebook", "facebook.com");
        
        // Test Telegram link
        testSocialLink("Telegram", "t.me");
        
        // Test Instagram link
        testSocialLink("Instagram", "instagram.com");
    }

    private void testSocialLink(String linkText, String expectedDomain) {
        // Scroll to footer
        WebElement footer = driver.findElement(By.tagName("footer"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", footer);
        
        // Click social link
        WebElement link = driver.findElement(By.xpath("//a[text()='" + linkText + "']"));
        link.click();
        
        // Switch to new tab and verify URL
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedDomain));
        
        // Close tab and switch back to main window
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }
}
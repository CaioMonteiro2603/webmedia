package deepseek.ws05.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SelectorsHubTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://selectorshub.com/xpath-practice-page/";

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
    public void testMainPageLoad() {
        driver.get(BASE_URL);
        Assertions.assertEquals("XPath Practice Page | SelectorsHub", driver.getTitle());
    }

    @Test
    public void testFormSubmission() {
        driver.get(BASE_URL);
        
        // Fill out the form
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@id='userId']")));
        nameInput.sendKeys("Test User");
        
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='email']"));
        emailInput.sendKeys("test@example.com");
        
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='pass']"));
        passwordInput.sendKeys("TestPassword123");
        
        WebElement companyInput = driver.findElement(By.xpath("//input[@name='company']"));
        companyInput.sendKeys("Test Company");
        
        WebElement mobileInput = driver.findElement(By.xpath("//input[@name='mobile number']"));
        mobileInput.sendKeys("1234567890");
        
        // Submit the form
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
        submitButton.click();
        
        // Verify submission (assuming there's a success message)
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(),'Submission successful')]")));
        Assertions.assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testCheckboxSelection() {
        driver.get(BASE_URL);
        
        // Select check boxes
        WebElement checkbox1 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@name='chkboxone']")));
        checkbox1.click();
        Assertions.assertTrue(checkbox1.isSelected());
        
        WebElement checkbox2 = driver.findElement(By.xpath("//input[@name='chkboxtwo']"));
        checkbox2.click();
        Assertions.assertTrue(checkbox2.isSelected());
    }

    @Test
    public void testRadioButtonSelection() {
        driver.get(BASE_URL);
        
        // Select radio buttons
        WebElement radio1 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@value='option1']")));
        radio1.click();
        Assertions.assertTrue(radio1.isSelected());
        
        WebElement radio2 = driver.findElement(By.xpath("//input[@value='option2']"));
        radio2.click();
        Assertions.assertTrue(radio2.isSelected());
        Assertions.assertFalse(radio1.isSelected());
    }

    @Test
    public void testDropdownSelection() {
        driver.get(BASE_URL);
        
        // Select from drop down
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//select[@name='cars']")));
        dropdown.click();
        
        WebElement option = driver.findElement(By.xpath("//select[@name='cars']/option[text()='Audi']"));
        option.click();
        Assertions.assertTrue(option.isSelected());
    }

    @Test
    public void testTableInteraction() {
        driver.get(BASE_URL);
        
        // Verify table content
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//table[@id='tablepress-1']")));
        Assertions.assertTrue(table.isDisplayed());
        
        // Click on a table row
        WebElement firstRow = driver.findElement(By.xpath("//table[@id='tablepress-1']/tbody/tr[1]"));
        firstRow.click();
        
        // Verify row selection (assuming some visual change occurs)
        String rowClass = firstRow.getAttribute("class");
        Assertions.assertTrue(rowClass.contains("selected") || !rowClass.isEmpty());
    }

    @Test
    public void testNavigationToIframePage() {
        driver.get(BASE_URL);
        
        // Click on i frame link
        WebElement iframeLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(),'iFrame practice page')]")));
        iframeLink.click();
        
        // Verify navigation
        wait.until(ExpectedConditions.titleContains("iFrame Practice Page"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("iframe-practice-page"));
        
        // Test i frame interaction
        driver.switchTo().frame("iframeResult");
        WebElement iframeButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(),'Click Me')]")));
        iframeButton.click();
        
        // Verify button click action
        WebElement iframeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[contains(text(),'Button was clicked')]")));
        Assertions.assertTrue(iframeMessage.isDisplayed());
        
        // Switch back to main content
        driver.switchTo().defaultContent();
    }

    @Test
    public void testNavigationToShadowDOMPage() {
        driver.get(BASE_URL);
        
        // Click on Shadow DOM link
        WebElement shadowDomLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(),'Shadow DOM')]")));
        shadowDomLink.click();
        
        // Verify navigation
        wait.until(ExpectedConditions.titleContains("Shadow DOM"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("shadow-dom"));
        
        // Test Shadow DOM interaction
        WebElement shadowHost = driver.findElement(By.cssSelector("#userName"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        
        WebElement shadowInput = shadowRoot.findElement(By.cssSelector("#kils"));
        shadowInput.sendKeys("Test Input");
        Assertions.assertEquals("Test Input", shadowInput.getAttribute("value"));
    }

    @Test
    public void testNavigationToUploadPage() {
        driver.get(BASE_URL);
        
        // Click on upload link
        WebElement uploadLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(),'Upload File')]")));
        uploadLink.click();
        
        // Verify navigation
        wait.until(ExpectedConditions.titleContains("Upload File"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("file-upload"));
        
        // Test file upload (note: actual file upload requires a real file path)
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@type='file']")));
        fileInput.sendKeys(System.getProperty("user.dir") + "/testfile.txt");
        
        WebElement uploadButton = driver.findElement(By.xpath("//button[contains(text(),'Upload')]"));
        uploadButton.click();
        
        // Verify upload (assuming success message appears)
        WebElement uploadMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(),'File uploaded successfully')]")));
        Assertions.assertTrue(uploadMessage.isDisplayed());
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        
        // Get all external links
        List<WebElement> externalLinks = driver.findElements(By.xpath("//a[contains(@href,'http') and not(contains(@href,'selectorshub'))]"));
        
        for (WebElement link : externalLinks) {
            // String href = link.getAttribute("href");
            String originalWindow = driver.getWindowHandle();
            
            // Open link in new tab
            link.click();
            
            // Switch to new tab
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            
            // Verify the page loaded
            Assertions.assertFalse(driver.getTitle().isEmpty());
            
            // Close the tab and switch back
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }
}
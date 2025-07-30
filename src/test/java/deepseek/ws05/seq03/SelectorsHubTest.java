package deepseek.ws05.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
        
        // Navigate to the main page
        driver.get("https://selectorshub.com/xpath-practice-page/");
        mainWindowHandle = driver.getWindowHandle();
    }

    @AfterEach
    public void tearDown() {
        // Close all browser windows
        driver.quit();
    }

    @Test
    public void testPageTitle() {
        String expectedTitle = "XPath Practice Page | SelectorsHub";
        Assertions.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testFormElements() {
        // Test text inputs
        WebElement nameInput = driver.findElement(By.id("userId"));
        nameInput.sendKeys("Test User");
        Assertions.assertEquals("Test User", nameInput.getAttribute("value"));

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("test@example.com");
        Assertions.assertEquals("test@example.com", emailInput.getAttribute("value"));

        WebElement passwordInput = driver.findElement(By.id("pass"));
        passwordInput.sendKeys("password123");
        Assertions.assertEquals("password123", passwordInput.getAttribute("value"));

        // Test textarea
        WebElement textarea = driver.findElement(By.id("textarea"));
        textarea.sendKeys("This is a test comment");
        Assertions.assertEquals("This is a test comment", textarea.getAttribute("value"));
    }

    @Test
    public void testDropdowns() {
        // Test single select dropdown
        Select companySelect = new Select(driver.findElement(By.name("company")));
        companySelect.selectByVisibleText("Microsoft");
        Assertions.assertEquals("Microsoft", companySelect.getFirstSelectedOption().getText());

        // Test multi-select dropdown
        Select carsSelect = new Select(driver.findElement(By.name("cars")));
        carsSelect.selectByVisibleText("Saab");
        carsSelect.selectByVisibleText("Opel");
        List<WebElement> selectedOptions = carsSelect.getAllSelectedOptions();
        Assertions.assertEquals(2, selectedOptions.size());
        Assertions.assertTrue(selectedOptions.stream().anyMatch(option -> option.getText().equals("Saab")));
        Assertions.assertTrue(selectedOptions.stream().anyMatch(option -> option.getText().equals("Opel")));
    }

    @Test
    public void testCheckboxes() {
        // Test checkboxes
        WebElement bikeCheckbox = driver.findElement(By.id("bike"));
        bikeCheckbox.click();
        Assertions.assertTrue(bikeCheckbox.isSelected());

        WebElement carCheckbox = driver.findElement(By.id("car"));
        carCheckbox.click();
        Assertions.assertTrue(carCheckbox.isSelected());

        // Verify both are checked
        Assertions.assertTrue(bikeCheckbox.isSelected());
        Assertions.assertTrue(carCheckbox.isSelected());
    }

    @Test
    public void testRadioButtons() {
        // Test radio buttons
        WebElement maleRadio = driver.findElement(By.id("male"));
        maleRadio.click();
        Assertions.assertTrue(maleRadio.isSelected());

        WebElement femaleRadio = driver.findElement(By.id("female"));
        Assertions.assertFalse(femaleRadio.isSelected());

        // Switch to female
        femaleRadio.click();
        Assertions.assertTrue(femaleRadio.isSelected());
        Assertions.assertFalse(maleRadio.isSelected());
    }

    @Test
    public void testButtons() {
        // Test submit button
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        submitButton.click();
        
        // Verify alert appears
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(alert);
        alert.accept();
    }

    @Test
    public void testShadowDOMPage() {
        // Navigate to Shadow DOM page
        WebElement shadowDomLink = driver.findElement(By.linkText("Shadow DOM"));
        shadowDomLink.click();
        
        // Wait for page to load
        wait.until(ExpectedConditions.titleContains("Shadow DOM"));

        // Interact with Shadow DOM elements
        WebElement shadowHost = driver.findElement(By.id("userName"));
        WebElement shadowRoot = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].shadowRoot", shadowHost);
        
        WebElement shadowInput = shadowRoot.findElement(By.cssSelector("#kils"));
        shadowInput.sendKeys("Shadow DOM Test");
        Assertions.assertEquals("Shadow DOM Test", shadowInput.getAttribute("value"));

        // Return to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("XPath Practice Page"));
    }

    @Test
    public void testIframePage() {
        // Navigate to iframe page
        WebElement iframeLink = driver.findElement(By.linkText("iFrame"));
        iframeLink.click();
        
        // Wait for page to load
        wait.until(ExpectedConditions.titleContains("iFrame"));

        // Switch to iframe and interact with elements
        WebElement iframe = driver.findElement(By.id("pact"));
        driver.switchTo().frame(iframe);
        
        WebElement iframeInput = driver.findElement(By.id("inp"));
        iframeInput.sendKeys("Iframe Test");
        Assertions.assertEquals("Iframe Test", iframeInput.getAttribute("value"));

        // Switch back to main content
        driver.switchTo().defaultContent();
        
        // Return to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("XPath Practice Page"));
    }

    @Test
    public void testSvgPage() {
        // Navigate to SVG page
        WebElement svgLink = driver.findElement(By.linkText("SVG"));
        svgLink.click();
        
        // Wait for page to load
        wait.until(ExpectedConditions.titleContains("SVG"));

        // Interact with SVG elements
        WebElement svgElement = driver.findElement(By.xpath("//*[local-name()='svg']"));
        Assertions.assertTrue(svgElement.isDisplayed());

        // Return to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("XPath Practice Page"));
    }

    @Test
    public void testExternalLinks() {
        // Test YouTube link
        WebElement youtubeLink = driver.findElement(By.linkText("YouTube"));
        youtubeLink.click();
        
        // Switch to new tab
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        switchToNewWindow();
        
        // Verify YouTube page
        wait.until(ExpectedConditions.titleContains("YouTube"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("youtube.com"));
        
        // Close YouTube tab and switch back
        driver.close();
        driver.switchTo().window(mainWindowHandle);

        // Test Google link
        WebElement googleLink = driver.findElement(By.linkText("Google"));
        googleLink.click();
        
        // Switch to new tab
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        switchToNewWindow();
        
        // Verify Google page
        wait.until(ExpectedConditions.titleContains("Google"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("google.com"));
        
        // Close Google tab and switch back
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }

    private void switchToNewWindow() {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }
}
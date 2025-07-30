package deepseek.ws07.seq02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Select2DemoTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        
        // Maximize window and navigate to Select2 demo page
        driver.manage().window().maximize();
        driver.get("https://select2.github.io/select2/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testBasicSingleSelect() {
        // Test basic single select functionality
        WebElement basicSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-basic-single")));
        
        // Click to open dropdown
        basicSelect.click();
        
        // Verify dropdown is visible
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-dropdown")));
        Assertions.assertTrue(dropdown.isDisplayed(), "Dropdown should be visible");
        
        // Select an option
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'Alaska')]")));
        option.click();
        
        // Verify selection
        WebElement selectedValue = driver.findElement(By.cssSelector(".js-example-basic-single .select2-selection__rendered"));
        Assertions.assertTrue(selectedValue.getText().contains("Alaska"), "Selected value should be Alaska");
    }

    @Test
    public void testMultipleSelect() {
        // Test multiple select functionality
        WebElement multiSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-basic-multiple")));
        
        // Click to open dropdown
        multiSelect.click();
        
        // Select first option
        WebElement option1 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'Alabama')]")));
        option1.click();
        
        // Select second option
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'California')]")));
        option2.click();
        
        // Verify selections
        List<WebElement> selectedValues = driver.findElements(By.cssSelector(".js-example-basic-multiple .select2-selection__choice"));
        Assertions.assertEquals(2, selectedValues.size(), "Should have 2 selected options");
        Assertions.assertTrue(selectedValues.get(0).getText().contains("Alabama"), "First selection should be Alabama");
        Assertions.assertTrue(selectedValues.get(1).getText().contains("California"), "Second selection should be California");
        
        // Test removing a selection
        WebElement removeButton = selectedValues.get(0).findElement(By.cssSelector(".select2-selection__choice__remove"));
        removeButton.click();
        
        // Verify removal
        selectedValues = driver.findElements(By.cssSelector(".js-example-basic-multiple .select2-selection__choice"));
        Assertions.assertEquals(1, selectedValues.size(), "Should have 1 selected option after removal");
    }

    @Test
    public void testDisabledSelect() {
        // Test disabled select functionality
        WebElement disabledSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-disabled")));
        
        // Verify select is disabled
        Assertions.assertFalse(disabledSelect.isEnabled(), "Select should be disabled");
    }

    @Test
    public void testLoadingRemoteData() {
        // Test loading remote data functionality
        WebElement remoteSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-data-example-ajax")));
        
        // Click to open dropdown
        remoteSelect.click();
        
        // Search for a term
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-search__field")));
        searchInput.sendKeys("test");
        
        // Verify loading indicator appears
        WebElement loadingIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option--loading")));
        Assertions.assertTrue(loadingIndicator.isDisplayed(), "Loading indicator should be visible");
        
        // Wait for results to load (simplified for demo)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verify results are displayed
        List<WebElement> results = driver.findElements(By.cssSelector(".select2-results__option"));
        Assertions.assertTrue(results.size() > 0, "Should display search results");
    }

    @Test
    public void testCustomTemplates() {
        // Test custom templates functionality
        WebElement templateSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-templates")));
        
        // Click to open dropdown
        templateSelect.click();
        
        // Verify custom template is displayed
        WebElement customOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-results__option--highlighted")));
        Assertions.assertTrue(customOption.isDisplayed(), "Custom template option should be visible");
        
        // Select an option
        customOption.click();
        
        // Verify selection
        WebElement selectedValue = driver.findElement(By.cssSelector(".js-example-templates .select2-selection__rendered"));
        Assertions.assertTrue(selectedValue.getText().contains("Custom template"), "Selected value should be from custom template");
    }

    @Test
    public void testMultipleDropdowns() {
        // Test multiple dropdowns functionality
        WebElement multiDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-basic-multiple-limit")));
        
        // Click to open dropdown
        multiDropdown.click();
        
        // Select first option
        WebElement option1 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'Alabama')]")));
        option1.click();
        
        // Try to select more than limit (3)
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'Alaska')]")));
        option2.click();
        
        WebElement option3 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'Arizona')]")));
        option3.click();
        
        WebElement option4 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'Arkansas')]")));
        option4.click();
        
        // Verify only 3 selections allowed
        List<WebElement> selectedValues = driver.findElements(By.cssSelector(".js-example-basic-multiple-limit .select2-selection__choice"));
        Assertions.assertEquals(3, selectedValues.size(), "Should only allow 3 selections");
    }

    @Test
    public void testExternalLinks() {
        // Test external links on the page
        WebElement githubLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.linkText("GitHub repository")));
        
        // Click GitHub link
        String originalWindow = driver.getWindowHandle();
        githubLink.click();
        
        // Switch to new window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify GitHub page loaded
        wait.until(ExpectedConditions.urlContains("github.com/select2/select2"));
        Assertions.assertTrue(driver.getTitle().contains("select2"), "Should be on GitHub page");
        
        // Close tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    public void testPlaceholderText() {
        // Test placeholder text functionality
        WebElement placeholderSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-placeholder-single")));
        
        // Verify placeholder text
        WebElement placeholder = placeholderSelect.findElement(By.cssSelector(".select2-selection__placeholder"));
        Assertions.assertEquals("Select a state", placeholder.getText(), "Placeholder text should match");
        
        // Select an option
        placeholderSelect.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'Alaska')]")));
        option.click();
        
        // Verify placeholder is replaced
        WebElement selectedValue = placeholderSelect.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertNotEquals("Select a state", selectedValue.getText(), "Placeholder should be replaced");
    }

    @Test
    public void testDisabledOptions() {
        // Test disabled options functionality
        WebElement disabledOptionsSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-disabled-results")));
        
        // Click to open dropdown
        disabledOptionsSelect.click();
        
        // Try to select disabled option
        WebElement disabledOption = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//li[contains(@class,'disabled') and contains(text(),'Wyoming')]")));
        
        // Verify option is disabled
        Assertions.assertTrue(disabledOption.getAttribute("class").contains("disabled"), "Option should be disabled");
        
        // Try to click (should not work)
        try {
            disabledOption.click();
            Assertions.fail("Should not be able to click disabled option");
        } catch (ElementNotInteractableException e) {
            // Expected behavior
        }
    }

    @Test
    public void testRightToLeftSupport() {
        // Test RTL support functionality
        WebElement rtlSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".js-example-rtl")));
        
        // Click to open dropdown
        rtlSelect.click();
        
        // Verify RTL styling
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".select2-dropdown")));
        Assertions.assertTrue(dropdown.getAttribute("dir").equals("rtl"), "Dropdown should be RTL");
        
        // Select an option
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'Alaska')]")));
        option.click();
        
        // Verify selection
        WebElement selectedValue = rtlSelect.findElement(By.cssSelector(".select2-selection__rendered"));
        Assertions.assertTrue(selectedValue.getText().contains("Alaska"), "Selected value should be Alaska");
    }
}
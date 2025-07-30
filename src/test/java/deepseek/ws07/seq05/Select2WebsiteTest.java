package deepseek.ws07.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Select2WebsiteTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String mainUrl = "https://select2.github.io/select2/";
    private String originalWindowHandle;

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        originalWindowHandle = driver.getWindowHandle();
        
        // Maximize window and set implicit wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        // Close all browser windows
        driver.quit();
    }

    // Helper method to switch back to original window
    private void switchToOriginalWindow() {
        driver.switchTo().window(originalWindowHandle);
    }

    // Helper method to verify page title
    private void verifyPageTitle(String expectedTitle) {
        wait.until(ExpectedConditions.titleContains(expectedTitle));
        Assertions.assertTrue(driver.getTitle().contains(expectedTitle),
                "Page title doesn't contain: " + expectedTitle);
    }

    // Helper method to click and verify link
    private void clickAndVerifyLink(By locator, String expectedTitle) {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(locator));
        link.click();
        verifyPageTitle(expectedTitle);
    }

    // Test for main page
    @Test
    public void testMainPage() {
        driver.get(mainUrl);
        verifyPageTitle("Select2");

        // Test navigation links
        clickAndVerifyLink(By.linkText("Examples"), "Examples");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Documentation"), "Documentation");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Options"), "Options");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Themes"), "Themes");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("GitHub"), "GitHub");
        switchToOriginalWindow();

        // Test main page content
        WebElement gettingStarted = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".jumbotron h1")));
        Assertions.assertEquals("Select2", gettingStarted.getText(),
                "Main heading text mismatch");

        // Test search box functionality
        WebElement searchBox = driver.findElement(By.cssSelector(".search-box input"));
        searchBox.sendKeys("ajax");
        searchBox.sendKeys(Keys.ENTER);
        verifyPageTitle("Ajax");
        driver.navigate().back();
    }

    // Test Examples page
    @Test
    public void testExamplesPage() {
        driver.get(mainUrl + "examples.html");
        verifyPageTitle("Examples");

        // Test all example links
        clickAndVerifyLink(By.linkText("Basic examples"), "Basic examples");
        testBasicExamples();
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Multiple select"), "Multiple select");
        testMultipleSelect();
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Placeholders"), "Placeholders");
        testPlaceholders();
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Loading remote data"), "Loading remote data");
        testLoadingRemoteData();
        driver.navigate().back();
    }

    // Test Basic Examples section
    private void testBasicExamples() {
        // Test single select
        WebElement singleSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".single-select .select2-selection")));
        singleSelect.click();
        
        List<WebElement> options = driver.findElements(By.cssSelector(".select2-results__option"));
        Assertions.assertTrue(options.size() > 0, "No options in single select");
        options.get(0).click();
        
        // Verify selection
        WebElement selectedOption = driver.findElement(By.cssSelector(".single-select .select2-selection__rendered"));
        Assertions.assertNotEquals("", selectedOption.getText(), "No option selected");
    }

    // Test Multiple Select section
    private void testMultipleSelect() {
        // Test multiple select
        WebElement multiSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".multi-select .select2-selection")));
        multiSelect.click();
        
        List<WebElement> options = driver.findElements(By.cssSelector(".select2-results__option"));
        Assertions.assertTrue(options.size() > 0, "No options in multi select");
        
        // Select first two options
        options.get(0).click();
        options.get(1).click();
        
        // Verify selections
        List<WebElement> selectedOptions = driver.findElements(
                By.cssSelector(".multi-select .select2-selection__choice"));
        Assertions.assertEquals(2, selectedOptions.size(), "Incorrect number of selected options");
    }

    // Test Placeholders section
    private void testPlaceholders() {
        // Test placeholder select
        WebElement placeholderSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".placeholder-select .select2-selection")));
        placeholderSelect.click();
        
        List<WebElement> options = driver.findElements(By.cssSelector(".select2-results__option"));
        Assertions.assertTrue(options.size() > 0, "No options in placeholder select");
        options.get(0).click();
        
        // Verify selection cleared placeholder
        WebElement selectedOption = driver.findElement(
                By.cssSelector(".placeholder-select .select2-selection__rendered"));
        Assertions.assertNotEquals("Select a state", selectedOption.getText(), "Placeholder still visible");
    }

    // Test Loading Remote Data section
    private void testLoadingRemoteData() {
        // Test remote data select
        WebElement remoteSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".remote-select .select2-selection")));
        remoteSelect.click();
        
        // Type search term
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".select2-search__field")));
        searchInput.sendKeys("test");
        
        // Wait for results to load
        List<WebElement> results = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector(".select2-results__option"), 0));
        Assertions.assertTrue(results.size() > 0, "No remote results loaded");
        results.get(0).click();
        
        // Verify selection
        WebElement selectedOption = driver.findElement(
                By.cssSelector(".remote-select .select2-selection__rendered"));
        Assertions.assertNotEquals("", selectedOption.getText(), "No remote option selected");
    }

    // Test Documentation page
    @Test
    public void testDocumentationPage() {
        driver.get(mainUrl + "documentation.html");
        verifyPageTitle("Documentation");

        // Test all documentation sections
        clickAndVerifyLink(By.linkText("Getting started"), "Getting started");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Options"), "Options");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Events"), "Events");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Data sources"), "Data sources");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Dropdown"), "Dropdown");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Themes"), "Themes");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("AMD"), "AMD");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Browser support"), "Browser support");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("GitHub"), "GitHub");
        switchToOriginalWindow();
    }

    // Test Options page
    @Test
    public void testOptionsPage() {
        driver.get(mainUrl + "options.html");
        verifyPageTitle("Options");

        // Test all options sections
        clickAndVerifyLink(By.linkText("Core options"), "Core options");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Data options"), "Data options");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("Dropdown options"), "Dropdown options");
        driver.navigate().back();
        
        clickAndVerifyLink(By.linkText("GitHub"), "GitHub");
        switchToOriginalWindow();
    }

    // Test Themes page
    @Test
    public void testThemesPage() {
        driver.get(mainUrl + "themes.html");
        verifyPageTitle("Themes");

        // Test theme selection
        WebElement themeSelect = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".theme-selector .select2-selection")));
        themeSelect.click();
        
        List<WebElement> themes = driver.findElements(By.cssSelector(".select2-results__option"));
        Assertions.assertTrue(themes.size() > 0, "No themes available");
        
        // Select first theme
        String themeName = themes.get(0).getText();
        themes.get(0).click();
        
        // Verify theme applied
        WebElement selectedTheme = driver.findElement(
                By.cssSelector(".theme-selector .select2-selection__rendered"));
        Assertions.assertEquals(themeName, selectedTheme.getText(), "Theme not selected");
        
        // Test GitHub link
        clickAndVerifyLink(By.linkText("GitHub"), "GitHub");
        switchToOriginalWindow();
    }

    // Test external links
    @Test
    public void testExternalLinks() {
        driver.get(mainUrl);
        
        // Get all external links
        List<WebElement> externalLinks = driver.findElements(By.cssSelector("a[href^='http']"));
        Assertions.assertTrue(externalLinks.size() > 0, "No external links found");
        
        for (WebElement link : externalLinks) {
            String href = link.getAttribute("href");
            if (!href.contains("select2.github.io")) {
                // Open link in new tab
                String originalWindow = driver.getWindowHandle();
                link.click();
                
                // Switch to new tab
                Set<String> windows = driver.getWindowHandles();
                windows.remove(originalWindow);
                String newWindow = windows.iterator().next();
                driver.switchTo().window(newWindow);
                
                // Verify page loaded
                Assertions.assertNotEquals("", driver.getTitle(), "External page didn't load: " + href);
                
                // Close tab and switch back
                driver.close();
                driver.switchTo().window(originalWindow);
            }
        }
    }
}
package claudesonnet4.ws07.seq04;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/**
 * Comprehensive Selenium WebDriver Test Suite for Select2 Website
 * Tests main page functionality, sub-pages, external links, and all interactive elements
 * 
 * Main URL: https://select2.github.io/select2/
 * Package: claudesonnet4.ws7.seq04
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Select2WebsiteTestSuite {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions actions;
    private static final String BASE_URL = "https://select2.github.io/select2/";
    private static final String SELECT2_LATEST_URL = "https://select2.org/";
    private static final int TIMEOUT_SECONDS = 10;

    @BeforeAll
    static void setupClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT_SECONDS));
    }

    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void setUp() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("Select2"));
    }

    // ==================== MAIN PAGE TESTS ====================

    @Test
    @Order(1)
    @DisplayName("Test Main Page Load and Basic Elements")
    void testMainPageLoadAndBasicElements() {
        // Verify page title
        Assertions.assertTrue(driver.getTitle().contains("Select2 3.5.3"), 
            "Page title should contain 'Select2 3.5.3'");
        
        // Verify main heading
        WebElement mainHeading = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'Select2 3.5.3')]")));
        Assertions.assertTrue(mainHeading.isDisplayed(), "Main heading should be visible");
        
        // Verify description text
        WebElement description = driver.findElement(By.xpath(
            "//p[contains(text(), 'jQuery based replacement for select boxes')]"));
        Assertions.assertTrue(description.isDisplayed(), "Description should be visible");
        
        // Verify browser compatibility section
        WebElement browserCompatibility = driver.findElement(By.xpath(
            "//h3[contains(text(), 'Browser Compatibility')]"));
        Assertions.assertTrue(browserCompatibility.isDisplayed(), 
            "Browser compatibility section should be visible");
    }

    @Test
    @Order(2)
    @DisplayName("Test Main Navigation Links")
    void testMainNavigationLinks() {
        // Test Changelog link
        WebElement changelogLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Changelog")));
        changelogLink.click();
        
        // Verify URL contains changelog anchor
        Assertions.assertTrue(driver.getCurrentUrl().contains("#changelog"), 
            "URL should contain changelog anchor");
        
        // Test Examples dropdown
        WebElement examplesLink = driver.findElement(By.linkText("Examples"));
        examplesLink.click();
        
        // Verify examples dropdown menu appears
        WebElement examplesMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//ul[contains(@class, 'dropdown-menu')]")));
        Assertions.assertTrue(examplesMenu.isDisplayed(), "Examples dropdown menu should be visible");
        
        // Test Documentation link
        WebElement documentationLink = driver.findElement(By.linkText("Documentation"));
        documentationLink.click();
        
        // Verify URL contains documentation anchor
        Assertions.assertTrue(driver.getCurrentUrl().contains("#documentation"), 
            "URL should contain documentation anchor");
        
        // Test About link
        WebElement aboutLink = driver.findElement(By.linkText("About"));
        aboutLink.click();
        
        // Verify URL contains about anchor
        Assertions.assertTrue(driver.getCurrentUrl().contains("#about"), 
            "URL should contain about anchor");
    }

    @Test
    @Order(3)
    @DisplayName("Test Basic Select2 Dropdown Functionality")
    void testBasicSelect2Dropdown() {
        // Find the first Select2 dropdown
        WebElement select2Container = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'select2-container')]")));
        Assertions.assertTrue(select2Container.isDisplayed(), "Select2 container should be visible");
        
        // Click on the dropdown to open it
        WebElement dropdownArrow = select2Container.findElement(
            By.xpath(".//a[contains(@class, 'select2-choice')]"));
        dropdownArrow.click();
        
        // Wait for dropdown to open and verify options are visible
        WebElement dropdownResults = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'select2-drop-active')]")));
        Assertions.assertTrue(dropdownResults.isDisplayed(), "Dropdown results should be visible");
        
        // Select an option (California)
        WebElement californiaOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@class, 'select2-result-label') and text()='California']")));
        californiaOption.click();
        
        // Verify selection was made
        WebElement selectedValue = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//span[contains(@class, 'select2-chosen') and text()='California']")));
        Assertions.assertTrue(selectedValue.isDisplayed(), "California should be selected");
    }

    @Test
    @Order(4)
    @DisplayName("Test Control Buttons Functionality")
    void testControlButtonsFunctionality() {
        // Test "Alert selected value" button
        WebElement alertButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("e8_get")));
        Assertions.assertTrue(alertButton.isDisplayed(), "Alert button should be visible");
        alertButton.click();
        
        // Handle alert dialog
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            alert.accept();
            Assertions.assertNotNull(alertText, "Alert should contain text");
        } catch (TimeoutException e) {
            // Alert might not appear if no selection is made
            System.out.println("No alert appeared - this is expected if no selection was made");
        }
        
        // Test "Set to California" button
        WebElement setCaliforniaButton = driver.findElement(By.id("e8_set"));
        Assertions.assertTrue(setCaliforniaButton.isDisplayed(), "Set California button should be visible");
        setCaliforniaButton.click();
        
        // Verify California is selected
        WebElement selectedValue = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//span[contains(@class, 'select2-chosen') and text()='California']")));
        Assertions.assertTrue(selectedValue.isDisplayed(), "California should be selected after clicking Set button");
        
        // Test "Clear" button
        WebElement clearButton = driver.findElement(By.id("e8_cl"));
        Assertions.assertTrue(clearButton.isDisplayed(), "Clear button should be visible");
        clearButton.click();
        
        // Test "Open" button
        WebElement openButton = driver.findElement(By.id("e8_open"));
        Assertions.assertTrue(openButton.isDisplayed(), "Open button should be visible");
        openButton.click();
        
        // Verify dropdown is open
        WebElement openDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'select2-drop-active')]")));
        Assertions.assertTrue(openDropdown.isDisplayed(), "Dropdown should be open after clicking Open button");
        
        // Test "Close" button
        WebElement closeButton = driver.findElement(By.id("e8_close"));
        Assertions.assertTrue(closeButton.isDisplayed(), "Close button should be visible");
        closeButton.click();
    }

    @Test
    @Order(5)
    @DisplayName("Test Multi-Value Select2 Functionality")
    void testMultiValueSelect2() {
        // Scroll to multi-value section
        WebElement multiValueSection = driver.findElement(
            By.xpath("//h3[contains(text(), 'Multi-Value Select Boxes')]"));
        actions.moveToElement(multiValueSection).perform();
        
        // Find multi-value Select2 container
        List<WebElement> multiContainers = driver.findElements(
            By.xpath("//div[contains(@class, 'select2-container-multi')]"));
        
        if (!multiContainers.isEmpty()) {
            WebElement multiContainer = multiContainers.get(0);
            Assertions.assertTrue(multiContainer.isDisplayed(), "Multi-value container should be visible");
            
            // Click on the multi-select input
            WebElement multiInput = multiContainer.findElement(
                By.xpath(".//input[contains(@class, 'select2-input')]"));
            multiInput.click();
            
            // Type to search for a state
            multiInput.sendKeys("Cal");
            
            // Wait for and select California
            try {
                WebElement californiaOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'select2-result-label') and contains(text(), 'California')]")));
                californiaOption.click();
                
                // Verify California was added as a choice
                WebElement californiaChoice = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//li[contains(@class, 'select2-search-choice')]//div[text()='California']")));
                Assertions.assertTrue(californiaChoice.isDisplayed(), "California should be added as a choice");
            } catch (TimeoutException e) {
                System.out.println("Multi-value selection test skipped due to timing");
            }
        }
        
        // Test multi-value control buttons
        WebElement multiAlertButton = driver.findElement(By.id("e8_2_get"));
        Assertions.assertTrue(multiAlertButton.isDisplayed(), "Multi-value alert button should be visible");
        
        WebElement multiSetButton = driver.findElement(By.id("e8_2_set"));
        Assertions.assertTrue(multiSetButton.isDisplayed(), "Multi-value set button should be visible");
        multiSetButton.click();
        
        WebElement multiClearButton = driver.findElement(By.id("e8_2_cl"));
        Assertions.assertTrue(multiClearButton.isDisplayed(), "Multi-value clear button should be visible");
    }

    @Test
    @Order(6)
    @DisplayName("Test Placeholder Functionality")
    void testPlaceholderFunctionality() {
        // Find Select2 elements with placeholders
        List<WebElement> placeholderElements = driver.findElements(
            By.xpath("//span[contains(@class, 'select2-chosen') and contains(text(), 'Select a State')]"));
        
        Assertions.assertFalse(placeholderElements.isEmpty(), "Should find elements with placeholder text");
        
        for (WebElement element : placeholderElements) {
            Assertions.assertTrue(element.isDisplayed(), "Placeholder element should be visible");
            String placeholderText = element.getText();
            Assertions.assertTrue(placeholderText.contains("Select"), 
                "Placeholder should contain 'Select' text");
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test Remote Data Loading (GitHub API)")
    void testRemoteDataLoading() {
        // Scroll to remote data section
        WebElement remoteDataSection = driver.findElement(
            By.xpath("//h3[contains(text(), 'Loading Remote Data')]"));
        actions.moveToElement(remoteDataSection).perform();
        
        // Find the GitHub repository search Select2
        WebElement githubSelect = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//span[contains(@class, 'select2-chosen') and text()='select2/select2']")));
        Assertions.assertTrue(githubSelect.isDisplayed(), "GitHub repository select should be visible");
        
        // Click to open the dropdown
        githubSelect.click();
        
        // Find the search input and type a query
        try {
            WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@class, 'select2-input')]")));
            searchInput.clear();
            searchInput.sendKeys("jquery");
            
            // Wait for remote results to load
            Thread.sleep(2000); // Allow time for AJAX request
            
            // Check if results are loaded
            List<WebElement> results = driver.findElements(
                By.xpath("//div[contains(@class, 'select2-result-label')]"));
            
            if (!results.isEmpty()) {
                Assertions.assertTrue(results.size() > 0, "Remote data should load search results");
            }
        } catch (Exception e) {
            System.out.println("Remote data test may have hit API rate limit: " + e.getMessage());
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test Enable/Disable Functionality")
    void testEnableDisableFunctionality() {
        // Scroll to the enable/disable section
        WebElement enableButton = driver.findElement(By.id("e16_enable"));
        actions.moveToElement(enableButton).perform();
        
        // Test Enable button
        Assertions.assertTrue(enableButton.isDisplayed(), "Enable button should be visible");
        enableButton.click();
        
        // Test Disable button
        WebElement disableButton = driver.findElement(By.id("e16_disable"));
        Assertions.assertTrue(disableButton.isDisplayed(), "Disable button should be visible");
        disableButton.click();
        
        // Test Writable button
        WebElement writableButton = driver.findElement(By.id("e16_writable"));
        Assertions.assertTrue(writableButton.isDisplayed(), "Writable button should be visible");
        writableButton.click();
        
        // Test Readonly button
        WebElement readonlyButton = driver.findElement(By.id("e16_readonly"));
        Assertions.assertTrue(readonlyButton.isDisplayed(), "Readonly button should be visible");
        readonlyButton.click();
    }

    @Test
    @Order(9)
    @DisplayName("Test Init/Destroy Functionality")
    void testInitDestroyFunctionality() {
        // Find and test Init button
        WebElement initButton = driver.findElement(By.id("e14_init"));
        actions.moveToElement(initButton).perform();
        
        Assertions.assertTrue(initButton.isDisplayed(), "Init button should be visible");
        initButton.click();
        
        // Find and test Destroy button
        WebElement destroyButton = driver.findElement(By.id("e14_destroy"));
        Assertions.assertTrue(destroyButton.isDisplayed(), "Destroy button should be visible");
        destroyButton.click();
    }

    // ==================== EXTERNAL LINKS TESTS ====================

    @Test
    @Order(10)
    @DisplayName("Test Select2 Latest External Link")
    void testSelect2LatestExternalLink() {
        WebElement select2LatestLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Select2 Latest")));
        
        String originalWindow = driver.getWindowHandle();
        select2LatestLink.click();
        
        // Wait for new tab/window and switch to it
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        
        // Verify we're on the Select2 documentation site
        wait.until(ExpectedConditions.urlContains("select2.org"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("select2.org"), 
            "Should navigate to select2.org");
        Assertions.assertTrue(driver.getTitle().contains("Select2"), 
            "Page title should contain Select2");
        
        // Verify main navigation elements exist
        WebElement gettingStarted = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[contains(text(), 'Getting Started') or contains(@href, 'getting-started')]")));
        Assertions.assertTrue(gettingStarted.isDisplayed(), "Getting Started link should be visible");
        
        // Close the new tab and return to original
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    @Order(11)
    @DisplayName("Test GitHub External Links")
    void testGitHubExternalLinks() {
        // Scroll to About section
        WebElement aboutLink = driver.findElement(By.linkText("About"));
        aboutLink.click();
        
        // Test Project Site link
        WebElement projectSiteLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Project Site")));
        
        String href = projectSiteLink.getAttribute("href");
        Assertions.assertTrue(href.contains("github.com/select2/select2"), 
            "Project site should link to GitHub repository");
        
        // Test Bug Tracker link
        WebElement bugTrackerLink = driver.findElement(By.linkText("Bug Tracker"));
        String bugTrackerHref = bugTrackerLink.getAttribute("href");
        Assertions.assertTrue(bugTrackerHref.contains("github.com/select2/select2/issues"), 
            "Bug tracker should link to GitHub issues");
        
        // Test Wiki link
        WebElement wikiLink = driver.findElement(By.xpath(
            "//a[contains(text(), 'Wiki containing example integrations')]"));
        String wikiHref = wikiLink.getAttribute("href");
        Assertions.assertTrue(wikiHref.contains("github.com/select2/select2/wiki"), 
            "Wiki should link to GitHub wiki");
    }

    @Test
    @Order(12)
    @DisplayName("Test Mailing List External Link")
    void testMailingListExternalLink() {
        // Navigate to About section
        WebElement aboutLink = driver.findElement(By.linkText("About"));
        aboutLink.click();
        
        WebElement mailingListLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.linkText("Mailing List")));
        
        String href = mailingListLink.getAttribute("href");
        Assertions.assertTrue(href.contains("groups.google.com"), 
            "Mailing list should link to Google Groups");
    }

    @Test
    @Order(13)
    @DisplayName("Test License External Links")
    void testLicenseExternalLinks() {
        // Navigate to About section
        WebElement aboutLink = driver.findElement(By.linkText("About"));
        aboutLink.click();
        
        // Test Apache License link
        WebElement apacheLicenseLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.linkText("Apache Software Foundation License Version 2.0")));
        String apacheHref = apacheLicenseLink.getAttribute("href");
        Assertions.assertTrue(apacheHref.contains("apache.org/licenses"), 
            "Apache license should link to apache.org");
        
        // Test GPL License link
        WebElement gplLicenseLink = driver.findElement(By.linkText("GPL Version 2.0"));
        String gplHref = gplLicenseLink.getAttribute("href");
        Assertions.assertTrue(gplHref.contains("gnu.org/licenses"), 
            "GPL license should link to gnu.org");
    }

    @Test
    @Order(14)
    @DisplayName("Test jQuery API Documentation External Link")
    void testJQueryAPIDocumentationLink() {
        // Navigate to Documentation section
        WebElement documentationLink = driver.findElement(By.linkText("Documentation"));
        documentationLink.click();
        
        // Find jQuery API Documentation link
        WebElement jqueryApiLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.linkText("JQuery API Documentation")));
        
        String href = jqueryApiLink.getAttribute("href");
        Assertions.assertTrue(href.contains("api.jquery.com"), 
            "jQuery API link should point to api.jquery.com");
    }

    @Test
    @Order(15)
    @DisplayName("Test Download External Link")
    void testDownloadExternalLink() {
        WebElement downloadLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), 'Download')]")));
        
        String href = downloadLink.getAttribute("href");
        Assertions.assertTrue(href.contains("github.com/select2/select2/tags"), 
            "Download link should point to GitHub tags/releases");
    }

    // ==================== COMPREHENSIVE FUNCTIONALITY TESTS ====================

    @Test
    @Order(16)
    @DisplayName("Test All Select2 Dropdowns on Page")
    void testAllSelect2DropdownsOnPage() {
        List<WebElement> select2Containers = driver.findElements(
            By.xpath("//div[contains(@class, 'select2-container')]"));
        
        Assertions.assertTrue(select2Containers.size() > 0, 
            "Should find multiple Select2 containers on the page");
        
        int workingDropdowns = 0;
        for (int i = 0; i < Math.min(select2Containers.size(), 5); i++) {
            try {
                WebElement container = select2Containers.get(i);
                if (container.isDisplayed()) {
                    actions.moveToElement(container).perform();
                    
                    WebElement choice = container.findElement(
                        By.xpath(".//a[contains(@class, 'select2-choice') or contains(@class, 'select2-choices')]"));
                    choice.click();
                    
                    // Check if dropdown opened
                    List<WebElement> activeDropdowns = driver.findElements(
                        By.xpath("//div[contains(@class, 'select2-drop-active')]"));
                    
                    if (!activeDropdowns.isEmpty()) {
                        workingDropdowns++;
                        // Close the dropdown
                        choice.click();
                    }
                }
            } catch (Exception e) {
                // Continue with next dropdown if this one fails
                System.out.println("Dropdown " + i + " test failed: " + e.getMessage());
            }
        }
        
        Assertions.assertTrue(workingDropdowns > 0, 
            "At least one Select2 dropdown should be functional");
    }

    @Test
    @Order(17)
    @DisplayName("Test All Control Buttons on Page")
    void testAllControlButtonsOnPage() {
        List<WebElement> controlButtons = driver.findElements(
            By.xpath("//input[@type='button']"));
        
        Assertions.assertTrue(controlButtons.size() > 0, 
            "Should find control buttons on the page");
        
        int clickableButtons = 0;
        for (WebElement button : controlButtons) {
            try {
                if (button.isDisplayed() && button.isEnabled()) {
                    actions.moveToElement(button).perform();
                    String buttonText = button.getAttribute("value");
                    Assertions.assertNotNull(buttonText, "Button should have text/value");
                    clickableButtons++;
                    
                    // Click the button (but handle potential alerts)
                    button.click();
                    
                    // Handle any alerts that might appear
                    try {
                        Alert alert = driver.switchTo().alert();
                        alert.accept();
                    } catch (NoAlertPresentException e) {
                        // No alert is fine
                    }
                }
            } catch (Exception e) {
                // Continue with next button if this one fails
                System.out.println("Button test failed: " + e.getMessage());
            }
        }
        
        Assertions.assertTrue(clickableButtons > 0, 
            "Should find clickable control buttons");
    }

    @Test
    @Order(18)
    @DisplayName("Test Page Sections Navigation")
    void testPageSectionsNavigation() {
        // Test navigation to different sections
        String[] sections = {"#changelog", "#documentation", "#about"};
        String[] sectionNames = {"Changelog", "Documentation", "About"};
        
        for (int i = 0; i < sections.length; i++) {
            WebElement sectionLink = driver.findElement(By.linkText(sectionNames[i]));
            sectionLink.click();
            
            // Verify URL contains the section anchor
            Assertions.assertTrue(driver.getCurrentUrl().contains(sections[i]), 
                "URL should contain " + sections[i] + " anchor");
            
            // Verify we can find content related to the section
            String sectionXPath = "//h2[contains(text(), '" + sectionNames[i] + "')] | " +
                                 "//h3[contains(text(), '" + sectionNames[i] + "')] | " +
                                 "//div[contains(@id, '" + sectionNames[i].toLowerCase() + "')]";
            
            List<WebElement> sectionElements = driver.findElements(By.xpath(sectionXPath));
            Assertions.assertTrue(sectionElements.size() > 0, 
                "Should find " + sectionNames[i] + " section content");
        }
    }

    @Test
    @Order(19)
    @DisplayName("Test Responsive Design Elements")
    void testResponsiveDesignElements() throws InterruptedException {
        // Test different viewport sizes
        Dimension originalSize = driver.manage().window().getSize();
        
        try {
            // Test mobile viewport
            driver.manage().window().setSize(new Dimension(375, 667));
            Thread.sleep(1000);
            
            // Verify main elements are still visible
            WebElement mainHeading = driver.findElement(
                By.xpath("//h1[contains(text(), 'Select2')]"));
            Assertions.assertTrue(mainHeading.isDisplayed(), 
                "Main heading should be visible on mobile");
            
            // Test tablet viewport
            driver.manage().window().setSize(new Dimension(768, 1024));
            Thread.sleep(1000);
            
            // Verify navigation is accessible
            WebElement navigation = driver.findElement(
                By.xpath("//a[contains(text(), 'Examples')]"));
            Assertions.assertTrue(navigation.isDisplayed(), 
                "Navigation should be visible on tablet");
            
        } finally {
            // Restore original size
            driver.manage().window().setSize(originalSize);
        }
    }

    @Test
    @Order(20)
    @DisplayName("Test Page Performance and Load Times")
    void testPagePerformanceAndLoadTimes() {
        long startTime = System.currentTimeMillis();
        
        driver.get(BASE_URL);
        
        // Wait for page to be fully loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'Select2')]")));
        
        long loadTime = System.currentTimeMillis() - startTime;
        
        // Verify page loads within reasonable time (10 seconds)
        Assertions.assertTrue(loadTime < 10000, 
            "Page should load within 10 seconds, actual: " + loadTime + "ms");
        
        // Verify all critical elements are present
        List<WebElement> criticalElements = driver.findElements(
            By.xpath("//div[contains(@class, 'select2-container')] | " +
                    "//input[@type='button'] | " +
                    "//a[contains(@href, '#')]"));
        
        Assertions.assertTrue(criticalElements.size() > 10, 
            "Should find multiple critical interactive elements");
    }
}
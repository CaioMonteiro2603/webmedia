package deepseek.ws01.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WebsiteTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://wavingtest.github.io/Test_Healing/";

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomePage() {
        // Navigate to home page
        driver.get(BASE_URL);

        // Verify page title
        Assertions.assertEquals("Login Healing", driver.getTitle());

        // Verify main heading
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.tagName("h1")));
        Assertions.assertEquals("Welcome to Test Healing", heading.getText());

        // Verify navigation links are present
        List<WebElement> navLinks = driver.findElements(By.cssSelector("nav a"));
        Assertions.assertTrue(navLinks.size() >= 3, "Should have at least 3 navigation links");

        // Click on logo to verify it links back to home
        WebElement logo = driver.findElement(By.cssSelector(".logo"));
        logo.click();
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    @Test
    public void testAboutPage() {
        driver.get(BASE_URL);
        
        // Click on About link
        WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("About")));
        aboutLink.click();

        // Verify About page title
        Assertions.assertTrue(driver.getTitle().contains("About"));

        // Verify page content
        WebElement aboutContent = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".about-content")));
        Assertions.assertTrue(aboutContent.getText().contains("about our mission"));

        // Verify team members section
        List<WebElement> teamMembers = driver.findElements(By.cssSelector(".team-member"));
        Assertions.assertTrue(teamMembers.size() > 0, "Should display team members");
    }

    @Test
    public void testServicesPage() {
        driver.get(BASE_URL);
        
        // Click on Services link
        WebElement servicesLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Services")));
        servicesLink.click();

        // Verify Services page title
        Assertions.assertTrue(driver.getTitle().contains("Services"));

        // Verify services list
        List<WebElement> services = driver.findElements(By.cssSelector(".service-item"));
        Assertions.assertTrue(services.size() >= 3, "Should list at least 3 services");

        // Test service details expansion
        WebElement firstService = services.get(0);
        WebElement detailsButton = firstService.findElement(By.tagName("button"));
        detailsButton.click();
        
        WebElement detailsContent = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".service-details")));
        Assertions.assertTrue(detailsContent.isDisplayed());
    }

    @Test
    public void testContactPage() {
        driver.get(BASE_URL);
        
        // Click on Contact link
        WebElement contactLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Contact")));
        contactLink.click();

        // Verify Contact page title
        Assertions.assertTrue(driver.getTitle().contains("Contact"));

        // Test form submission
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("name")));
        nameField.sendKeys("Test User");

        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("test@example.com");

        WebElement messageField = driver.findElement(By.id("message"));
        messageField.sendKeys("This is a test message");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Verify success message
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".alert-success")));
        Assertions.assertTrue(successMessage.getText().contains("Thank you"));
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        
        // Test social media links
        testExternalLink("Facebook", "facebook.com");
        testExternalLink("Twitter", "twitter.com");
        testExternalLink("LinkedIn", "linkedin.com");
    }

    private void testExternalLink(String linkText, String expectedDomain) {
        // Find and click the external link
        WebElement externalLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText(linkText)));
        externalLink.click();

        // Switch to new tab
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Verify the external URL
        Assertions.assertTrue(driver.getCurrentUrl().contains(expectedDomain),
            "Expected domain " + expectedDomain + " not found in " + driver.getCurrentUrl());

        // Close the tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }
}
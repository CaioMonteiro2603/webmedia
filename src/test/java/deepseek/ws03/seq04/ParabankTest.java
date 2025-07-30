package deepseek.ws03.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ParabankTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://parabank.parasoft.com/parabank/index.htm";

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
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

    @Test
    public void testHomePageElements() {
        driver.get(BASE_URL);
        
        // Verify page title
        Assertions.assertEquals("ParaBank | Welcome | Online Banking", driver.getTitle());
        
        // Verify logo is displayed
        WebElement logo = driver.findElement(By.xpath("//img[@title='ParaBank']"));
        Assertions.assertTrue(logo.isDisplayed());
        
        // Verify main navigation links
        String[] navLinks = {"Home", "About Us", "Services", "Products", "Locations", "Admin Page"};
        for (String linkText : navLinks) {
            WebElement link = driver.findElement(By.linkText(linkText));
            Assertions.assertTrue(link.isDisplayed());
        }
        
        // Verify customer login section
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));
        
        Assertions.assertTrue(usernameField.isDisplayed());
        Assertions.assertTrue(passwordField.isDisplayed());
        Assertions.assertTrue(loginButton.isDisplayed());
        
        // Verify footer links
        String[] footerLinks = {"About Us", "Services", "Products", "Locations", "Forum", "Site Map", "Contact Us"};
        for (String linkText : footerLinks) {
            WebElement link = driver.findElement(By.linkText(linkText));
            Assertions.assertTrue(link.isDisplayed());
        }
    }

    @Test
    public void testAboutUsPage() {
        driver.get(BASE_URL);
        WebElement aboutUsLink = driver.findElement(By.linkText("About Us"));
        aboutUsLink.click();
        
        wait.until(ExpectedConditions.titleContains("About Us"));
        Assertions.assertEquals("ParaBank | About Us", driver.getTitle());
        
        // Verify page content
        WebElement header = driver.findElement(By.tagName("h1"));
        Assertions.assertEquals("ParaSoft Demo Website", header.getText());
        
        WebElement content = driver.findElement(By.id("rightPanel"));
        Assertions.assertTrue(content.getText().contains("ParaBank is a demo site"));
    }

    @Test
    public void testServicesPage() {
        driver.get(BASE_URL);
        WebElement servicesLink = driver.findElement(By.linkText("Services"));
        servicesLink.click();
        
        wait.until(ExpectedConditions.titleContains("Services"));
        Assertions.assertEquals("ParaBank | Services", driver.getTitle());
        
        // Verify services listed
        String[] services = {"Checking Accounts", "Loans", "Mortgages", "Credit Cards", "Investment Services"};
        for (String service : services) {
            WebElement serviceElement = driver.findElement(By.xpath("//h2[contains(text(),'" + service + "')]"));
            Assertions.assertTrue(serviceElement.isDisplayed());
        }
    }

    @Test
    public void testProductsPage() {
        driver.get(BASE_URL);
        WebElement productsLink = driver.findElement(By.linkText("Products"));
        productsLink.click();
        
        wait.until(ExpectedConditions.titleContains("Products"));
        Assertions.assertEquals("ParaBank | Products", driver.getTitle());
        
        // Verify products listed
        WebElement productsHeader = driver.findElement(By.xpath("//h1[contains(text(),'Products')]"));
        Assertions.assertTrue(productsHeader.isDisplayed());
        
        // Verify external links
        testExternalLinksOnPage();
    }

    @Test
    public void testLocationsPage() {
        driver.get(BASE_URL);
        WebElement locationsLink = driver.findElement(By.linkText("Locations"));
        locationsLink.click();
        
        wait.until(ExpectedConditions.titleContains("Locations"));
        Assertions.assertEquals("ParaBank | Locations", driver.getTitle());
        
        // Verify map is displayed
        WebElement map = driver.findElement(By.id("map"));
        Assertions.assertTrue(map.isDisplayed());
    }

    @Test
    public void testAdminPage() {
        driver.get(BASE_URL);
        WebElement adminLink = driver.findElement(By.linkText("Admin Page"));
        adminLink.click();
        
        wait.until(ExpectedConditions.titleContains("Administration"));
        Assertions.assertEquals("ParaBank | Administration", driver.getTitle());
        
        // Verify admin form elements
        WebElement cleanButton = driver.findElement(By.xpath("//button[contains(text(),'Clean')]"));
        WebElement initializeButton = driver.findElement(By.xpath("//button[contains(text(),'Initialize')]"));
        
        Assertions.assertTrue(cleanButton.isDisplayed());
        Assertions.assertTrue(initializeButton.isDisplayed());
    }

    @Test
    public void testCustomerLogin() {
        driver.get(BASE_URL);
        
        // Enter credentials and login
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));
        
        usernameField.sendKeys("john");
        passwordField.sendKeys("demo");
        loginButton.click();
        
        // Verify successful login
        wait.until(ExpectedConditions.titleContains("Accounts Overview"));
        Assertions.assertEquals("ParaBank | Accounts Overview", driver.getTitle());
        
        // Verify welcome message
        WebElement welcomeMessage = driver.findElement(By.className("smallText"));
        Assertions.assertTrue(welcomeMessage.getText().contains("Welcome"));
        
        // Logout
        WebElement logoutLink = driver.findElement(By.linkText("Log Out"));
        logoutLink.click();
        
        wait.until(ExpectedConditions.titleContains("Online Banking"));
        Assertions.assertEquals("ParaBank | Welcome | Online Banking", driver.getTitle());
    }

    @Test
    public void testForgotLoginInfo() {
        driver.get(BASE_URL);
        WebElement forgotLoginLink = driver.findElement(By.linkText("Forgot login info?"));
        forgotLoginLink.click();
        
        wait.until(ExpectedConditions.titleContains("Customer Lookup"));
        Assertions.assertEquals("ParaBank | Customer Lookup", driver.getTitle());
        
        // Verify form elements
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        WebElement addressField = driver.findElement(By.id("address.street"));
        WebElement cityField = driver.findElement(By.id("address.city"));
        WebElement stateField = driver.findElement(By.id("address.state"));
        WebElement zipCodeField = driver.findElement(By.id("address.zipCode"));
        WebElement ssnField = driver.findElement(By.id("ssn"));
        WebElement findLoginButton = driver.findElement(By.xpath("//input[@value='Find My Login Info']"));
        
        Assertions.assertTrue(firstNameField.isDisplayed());
        Assertions.assertTrue(lastNameField.isDisplayed());
        Assertions.assertTrue(addressField.isDisplayed());
        Assertions.assertTrue(cityField.isDisplayed());
        Assertions.assertTrue(stateField.isDisplayed());
        Assertions.assertTrue(zipCodeField.isDisplayed());
        Assertions.assertTrue(ssnField.isDisplayed());
        Assertions.assertTrue(findLoginButton.isDisplayed());
        
        // Test form submission
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Smith");
        addressField.sendKeys("123 Main St");
        cityField.sendKeys("Beverly Hills");
        stateField.sendKeys("CA");
        zipCodeField.sendKeys("90210");
        ssnField.sendKeys("123-45-6789");
        findLoginButton.click();
        
        // Verify results
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rightPanel")));
        WebElement results = driver.findElement(By.id("rightPanel"));
        Assertions.assertTrue(results.getText().contains("Your login information was located successfully"));
    }

    @Test
    public void testRegisterNewCustomer() {
        driver.get(BASE_URL);
        WebElement registerLink = driver.findElement(By.linkText("Register"));
        registerLink.click();
        
        wait.until(ExpectedConditions.titleContains("Register"));
        Assertions.assertEquals("ParaBank | Register for Free Online Account Access", driver.getTitle());
        
        // Fill out registration form
        WebElement firstNameField = driver.findElement(By.id("customer.firstName"));
        WebElement lastNameField = driver.findElement(By.id("customer.lastName"));
        WebElement addressField = driver.findElement(By.id("customer.address.street"));
        WebElement cityField = driver.findElement(By.id("customer.address.city"));
        WebElement stateField = driver.findElement(By.id("customer.address.state"));
        WebElement zipCodeField = driver.findElement(By.id("customer.address.zipCode"));
        WebElement phoneField = driver.findElement(By.id("customer.phoneNumber"));
        WebElement ssnField = driver.findElement(By.id("customer.ssn"));
        WebElement usernameField = driver.findElement(By.id("customer.username"));
        WebElement passwordField = driver.findElement(By.id("customer.password"));
        WebElement confirmPasswordField = driver.findElement(By.id("repeatedPassword"));
        WebElement registerButton = driver.findElement(By.xpath("//input[@value='Register']"));
        
        // Generate unique username
        String username = "user" + System.currentTimeMillis();
        
        firstNameField.sendKeys("Test");
        lastNameField.sendKeys("User");
        addressField.sendKeys("123 Test St");
        cityField.sendKeys("Testville");
        stateField.sendKeys("TS");
        zipCodeField.sendKeys("12345");
        phoneField.sendKeys("555-123-4567");
        ssnField.sendKeys("123-45-6789");
        usernameField.sendKeys(username);
        passwordField.sendKeys("password");
        confirmPasswordField.sendKeys("password");
        registerButton.click();
        
        // Verify successful registration
        wait.until(ExpectedConditions.titleContains("Account Created"));
        Assertions.assertEquals("ParaBank | Account Created", driver.getTitle());
        
        WebElement successMessage = driver.findElement(By.id("rightPanel"));
        Assertions.assertTrue(successMessage.getText().contains("Your account was created successfully"));
    }

    private void testExternalLinksOnPage() {
        // Find all links on the current page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href != null && !href.contains("parabank.parasoft.com")) {
                // This is an external link
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
                
                // Verify the external page loads
                Assertions.assertNotNull(driver.getTitle());
                
                // Close the tab and switch back
                driver.close();
                driver.switchTo().window(originalWindow);
            }
        }
    }
}
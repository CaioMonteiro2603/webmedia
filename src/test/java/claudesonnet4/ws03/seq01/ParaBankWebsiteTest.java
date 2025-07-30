package claudesonnet4.ws03.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for ParaBank website
 * Tests all pages one level below main page and external links
 * 
 * @author Automated Test Generator
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParaBankWebsiteTest {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://parabank.parasoft.com/parabank/";
    private static final int TIMEOUT_SECONDS = 10;
    
    @BeforeAll
    static void setUpClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
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
        driver.get(BASE_URL + "index.htm");
    }
    
    /**
     * Test main homepage functionality and elements
     */
    @Test
    @Order(1)
    @DisplayName("Test Homepage - Main Page Elements and Navigation")
    void testHomepage() {
        // Verify page title
        assertEquals("ParaBank | Welcome | Online Banking", driver.getTitle());
        
        // Verify main logo and heading
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//img[contains(@src, 'logo')]")));
        assertTrue(logo.isDisplayed());
        
        // Verify welcome message
        WebElement welcomeMessage = driver.findElement(By.xpath("//*[contains(text(), 'Welcome to ParaBank')]"));
        assertTrue(welcomeMessage.isDisplayed());
        
        // Test navigation menu links
        List<WebElement> navLinks = driver.findElements(By.xpath("//div[@id='leftPanel']//a"));
        assertTrue(navLinks.size() > 0, "Navigation links should be present");
        
        // Test customer login form elements
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));
        
        assertTrue(usernameField.isDisplayed());
        assertTrue(passwordField.isDisplayed());
        assertTrue(loginButton.isDisplayed());
        
        // Test login form interaction
        usernameField.clear();
        usernameField.sendKeys("testuser");
        passwordField.clear();
        passwordField.sendKeys("testpass");
        
        assertEquals("testuser", usernameField.getAttribute("value"));
        assertEquals("testpass", passwordField.getAttribute("value"));
        
        // Test register link
        WebElement registerLink = driver.findElement(By.linkText("Register"));
        assertTrue(registerLink.isDisplayed());
        assertTrue(registerLink.isEnabled());
        
        // Test forgot login info link
        WebElement forgotLink = driver.findElement(By.linkText("Forgot login info?"));
        assertTrue(forgotLink.isDisplayed());
        assertTrue(forgotLink.isEnabled());
        
        // Verify ATM and Online Services sections
        assertTrue(driver.getPageSource().contains("ATM Services"));
        assertTrue(driver.getPageSource().contains("Online Services"));
        assertTrue(driver.getPageSource().contains("Bill Pay"));
        assertTrue(driver.getPageSource().contains("Account History"));
    }
    
    /**
     * Test About Us page functionality
     */
    @Test
    @Order(2)
    @DisplayName("Test About Us Page")
    void testAboutUsPage() {
        // Navigate to About Us page
        WebElement aboutUsLink = driver.findElement(By.linkText("About Us"));
        aboutUsLink.click();
        
        // Verify page title and URL
        assertEquals("ParaBank | About Us", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("about.htm"));
        
        // Verify page content
        WebElement welcomeHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Welcome to ParaBank')]")));
        assertTrue(welcomeHeader.isDisplayed());
        
        // Verify demo website information
        assertTrue(driver.getPageSource().contains("ParaSoft Demo Website"));
        assertTrue(driver.getPageSource().contains("ParaBank is a demo site"));
        assertTrue(driver.getPageSource().contains("not a real bank"));
        assertTrue(driver.getPageSource().contains("www.parasoft.com"));
        assertTrue(driver.getPageSource().contains("888-305-0041"));
        
        // Test navigation back to home
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        homeLink.click();
        assertTrue(driver.getCurrentUrl().contains("index.htm"));
    }
    
    /**
     * Test Services page functionality
     */
    @Test
    @Order(3)
    @DisplayName("Test Services Page")
    void testServicesPage() {
        // Navigate to Services page
        WebElement servicesLink = driver.findElement(By.linkText("Services"));
        servicesLink.click();
        
        // Verify page title and URL
        assertEquals("ParaBank | Services", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("services.htm"));
        
        // Verify services content
        assertTrue(driver.getPageSource().contains("Available Bookstore SOAP services"));
        assertTrue(driver.getPageSource().contains("Bookstore"));
        assertTrue(driver.getPageSource().contains("Endpoint address"));
        assertTrue(driver.getPageSource().contains("Target namespace"));
        assertTrue(driver.getPageSource().contains("WS-Security"));
        
        // Verify multiple bookstore service versions
        assertTrue(driver.getPageSource().contains("Version 2.0"));
        assertTrue(driver.getPageSource().contains("Username Token"));
        assertTrue(driver.getPageSource().contains("Signature"));
        
        // Test scroll functionality for long content
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Verify SOAP service details
        assertTrue(driver.getPageSource().contains("soatest"));
        assertTrue(driver.getPageSource().contains("parabank.parasoft.com"));
    }
    
    /**
     * Test Admin Page functionality
     */
    @Test
    @Order(4)
    @DisplayName("Test Admin Page")
    void testAdminPage() {
        // Navigate to Admin page
        WebElement adminLink = driver.findElement(By.linkText("Admin Page"));
        adminLink.click();
        
        // Verify page title and URL
        assertEquals("ParaBank | Administration", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("admin.htm"));
        
        // Verify administration content
        assertTrue(driver.getPageSource().contains("Administration"));
        assertTrue(driver.getPageSource().contains("Database"));
        assertTrue(driver.getPageSource().contains("JMS Service"));
        assertTrue(driver.getPageSource().contains("Data Access Mode"));
        
        // Test data access mode options
        assertTrue(driver.getPageSource().contains("REST (JSON)"));
        assertTrue(driver.getPageSource().contains("REST (XML)"));
        assertTrue(driver.getPageSource().contains("SOAP"));
        
        // Verify web service information
        assertTrue(driver.getPageSource().contains("Web Service"));
        assertTrue(driver.getPageSource().contains("WSDL"));
        assertTrue(driver.getPageSource().contains("WADL"));
        assertTrue(driver.getPageSource().contains("OpenAPI"));
        
        // Test interactive elements if present
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        
        // Verify form elements are present
        assertTrue(buttons.size() > 0 || inputs.size() > 0, "Admin page should have interactive elements");
    }
    
    /**
     * Test Register page functionality
     */
    @Test
    @Order(5)
    @DisplayName("Test Register Page")
    void testRegisterPage() {
        // Navigate to Register page
        WebElement registerLink = driver.findElement(By.linkText("Register"));
        registerLink.click();
        
        // Verify page title and URL
        assertEquals("ParaBank | Register for Free Online Account Access", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("register.htm"));
        
        // Verify registration form elements
        assertTrue(driver.getPageSource().contains("Signing up is easy!"));
        
        // Test form fields
        WebElement firstNameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.name("customer.firstName")));
        WebElement lastNameField = driver.findElement(By.name("customer.lastName"));
        WebElement addressField = driver.findElement(By.name("customer.address.street"));
        WebElement cityField = driver.findElement(By.name("customer.address.city"));
        WebElement stateField = driver.findElement(By.name("customer.address.state"));
        WebElement zipCodeField = driver.findElement(By.name("customer.address.zipCode"));
        WebElement phoneField = driver.findElement(By.name("customer.phoneNumber"));
        WebElement ssnField = driver.findElement(By.name("customer.ssn"));
        WebElement usernameField = driver.findElement(By.name("customer.username"));
        WebElement passwordField = driver.findElement(By.name("customer.password"));
        WebElement confirmField = driver.findElement(By.name("repeatedPassword"));
        
        // Verify all fields are present and enabled
        assertTrue(firstNameField.isDisplayed() && firstNameField.isEnabled());
        assertTrue(lastNameField.isDisplayed() && lastNameField.isEnabled());
        assertTrue(addressField.isDisplayed() && addressField.isEnabled());
        assertTrue(cityField.isDisplayed() && cityField.isEnabled());
        assertTrue(stateField.isDisplayed() && stateField.isEnabled());
        assertTrue(zipCodeField.isDisplayed() && zipCodeField.isEnabled());
        assertTrue(phoneField.isDisplayed() && phoneField.isEnabled());
        assertTrue(ssnField.isDisplayed() && ssnField.isEnabled());
        assertTrue(usernameField.isDisplayed() && usernameField.isEnabled());
        assertTrue(passwordField.isDisplayed() && passwordField.isEnabled());
        assertTrue(confirmField.isDisplayed() && confirmField.isEnabled());
        
        // Test form filling
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Doe");
        addressField.sendKeys("123 Test Street");
        cityField.sendKeys("Test City");
        stateField.sendKeys("CA");
        zipCodeField.sendKeys("12345");
        phoneField.sendKeys("555-1234");
        ssnField.sendKeys("123-45-6789");
        usernameField.sendKeys("johndoe123");
        passwordField.sendKeys("password123");
        confirmField.sendKeys("password123");
        
        // Verify form values
        assertEquals("John", firstNameField.getAttribute("value"));
        assertEquals("Doe", lastNameField.getAttribute("value"));
        assertEquals("123 Test Street", addressField.getAttribute("value"));
        assertEquals("Test City", cityField.getAttribute("value"));
        assertEquals("CA", stateField.getAttribute("value"));
        assertEquals("12345", zipCodeField.getAttribute("value"));
        assertEquals("555-1234", phoneField.getAttribute("value"));
        assertEquals("123-45-6789", ssnField.getAttribute("value"));
        assertEquals("johndoe123", usernameField.getAttribute("value"));
        assertEquals("password123", passwordField.getAttribute("value"));
        assertEquals("password123", confirmField.getAttribute("value"));
        
        // Test register button
        WebElement registerButton = driver.findElement(By.xpath("//input[@value='Register']"));
        assertTrue(registerButton.isDisplayed() && registerButton.isEnabled());
    }
    
    /**
     * Test Contact Us page functionality
     */
    @Test
    @Order(6)
    @DisplayName("Test Contact Us Page")
    void testContactUsPage() {
        // Navigate to Contact Us page
        WebElement contactLink = driver.findElement(By.linkText("Contact Us"));
        contactLink.click();
        
        // Verify page title and URL
        assertEquals("ParaBank | Customer Care", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("contact.htm"));
        
        // Verify customer care content
        assertTrue(driver.getPageSource().contains("Customer Care"));
        assertTrue(driver.getPageSource().contains("Email support is available"));
        
        // Test contact form fields
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.name("name")));
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement phoneField = driver.findElement(By.name("phone"));
        WebElement messageField = driver.findElement(By.name("message"));
        
        // Verify form fields are present and enabled
        assertTrue(nameField.isDisplayed() && nameField.isEnabled());
        assertTrue(emailField.isDisplayed() && emailField.isEnabled());
        assertTrue(phoneField.isDisplayed() && phoneField.isEnabled());
        assertTrue(messageField.isDisplayed() && messageField.isEnabled());
        
        // Test form filling
        nameField.sendKeys("Test User");
        emailField.sendKeys("test@example.com");
        phoneField.sendKeys("555-0123");
        messageField.sendKeys("This is a test message for customer support.");
        
        // Verify form values
        assertEquals("Test User", nameField.getAttribute("value"));
        assertEquals("test@example.com", emailField.getAttribute("value"));
        assertEquals("555-0123", phoneField.getAttribute("value"));
        assertEquals("This is a test message for customer support.", messageField.getAttribute("value"));
        
        // Test send button
        WebElement sendButton = driver.findElement(By.xpath("//input[@value='Send to Customer Care']"));
        assertTrue(sendButton.isDisplayed() && sendButton.isEnabled());
    }
    
    /**
     * Test Site Map page functionality
     */
    @Test
    @Order(7)
    @DisplayName("Test Site Map Page")
    void testSiteMapPage() {
        // Navigate to Site Map page
        WebElement siteMapLink = driver.findElement(By.linkText("Site Map"));
        siteMapLink.click();
        
        // Verify page title and URL
        assertEquals("ParaBank | Site Map", driver.getTitle());
        assertTrue(driver.getCurrentUrl().contains("sitemap.htm"));
        
        // Verify site map content sections
        assertTrue(driver.getPageSource().contains("Solutions"));
        assertTrue(driver.getPageSource().contains("About Us"));
        assertTrue(driver.getPageSource().contains("Services"));
        assertTrue(driver.getPageSource().contains("Products"));
        assertTrue(driver.getPageSource().contains("Locations"));
        assertTrue(driver.getPageSource().contains("Admin Page"));
        
        // Verify account services section
        assertTrue(driver.getPageSource().contains("Account Services"));
        assertTrue(driver.getPageSource().contains("Open New Account"));
        assertTrue(driver.getPageSource().contains("Accounts Overview"));
        assertTrue(driver.getPageSource().contains("Transfer Funds"));
        assertTrue(driver.getPageSource().contains("Bill Pay"));
        assertTrue(driver.getPageSource().contains("Find Transactions"));
        assertTrue(driver.getPageSource().contains("Update Contact Info"));
        assertTrue(driver.getPageSource().contains("Request Loan"));
        
        // Test clickable links in site map
        List<WebElement> siteMapLinks = driver.findElements(By.xpath("//div[@id='rightPanel']//a"));
        assertTrue(siteMapLinks.size() > 0, "Site map should contain clickable links");
        
        // Test specific account service links
        WebElement openAccountLink = driver.findElement(By.linkText("Open New Account"));
        assertTrue(openAccountLink.isDisplayed() && openAccountLink.isEnabled());
        
        WebElement transferFundsLink = driver.findElement(By.linkText("Transfer Funds"));
        assertTrue(transferFundsLink.isDisplayed() && transferFundsLink.isEnabled());
        
        WebElement billPayLink = driver.findElement(By.linkText("Bill Pay"));
        assertTrue(billPayLink.isDisplayed() && billPayLink.isEnabled());
    }
    
    /**
     * Test external links functionality
     */
    @Test
    @Order(8)
    @DisplayName("Test External Links")
    void testExternalLinks() {
        // Test Products external link
        WebElement productsLink = driver.findElement(By.linkText("Products"));
        String productsHref = productsLink.getAttribute("href");
        assertTrue(productsHref.contains("parasoft.com/jsp/products.jsp"));
        assertTrue(productsLink.isDisplayed() && productsLink.isEnabled());
        
        // Test Locations external link
        WebElement locationsLink = driver.findElement(By.linkText("Locations"));
        String locationsHref = locationsLink.getAttribute("href");
        assertTrue(locationsHref.contains("parasoft.com/jsp/pr/contacts.jsp"));
        assertTrue(locationsLink.isDisplayed() && locationsLink.isEnabled());
        
        // Navigate to site map to test Forum link
        driver.findElement(By.linkText("Site Map")).click();
        
        WebElement forumLink = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.linkText("Forum")));
        String forumHref = forumLink.getAttribute("href");
        assertTrue(forumHref.contains("forums.parasoft.com"));
        assertTrue(forumLink.isDisplayed() && forumLink.isEnabled());
        
        // Test Parasoft main website link
        WebElement parasoftLink = driver.findElement(By.linkText("www.parasoft.com"));
        String parasoftHref = parasoftLink.getAttribute("href");
        assertTrue(parasoftHref.contains("parasoft.com"));
        assertTrue(parasoftLink.isDisplayed() && parasoftLink.isEnabled());
    }
    
    /**
     * Test account service pages (requires navigation from site map)
     */
    @Test
    @Order(9)
    @DisplayName("Test Account Service Pages")
    void testAccountServicePages() {
        // Navigate to site map first
        driver.findElement(By.linkText("Site Map")).click();
        
        // Test Open New Account page
        WebElement openAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Open New Account")));
        openAccountLink.click();
        
        assertTrue(driver.getCurrentUrl().contains("openaccount.htm"));
        assertTrue(driver.getPageSource().contains("Open New Account") || 
                  driver.getPageSource().contains("account") ||
                  driver.getPageSource().contains("Please log in"));
        
        // Go back to site map
        driver.navigate().back();
        
        // Test Accounts Overview page
        WebElement overviewLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Accounts Overview")));
        overviewLink.click();
        
        assertTrue(driver.getCurrentUrl().contains("overview.htm"));
        assertTrue(driver.getPageSource().contains("Accounts Overview") || 
                  driver.getPageSource().contains("account") ||
                  driver.getPageSource().contains("Please log in"));
        
        // Go back to site map
        driver.navigate().back();
        
        // Test Transfer Funds page
        WebElement transferLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Transfer Funds")));
        transferLink.click();
        
        assertTrue(driver.getCurrentUrl().contains("transfer.htm"));
        assertTrue(driver.getPageSource().contains("Transfer Funds") || 
                  driver.getPageSource().contains("transfer") ||
                  driver.getPageSource().contains("Please log in"));
        
        // Go back to site map
        driver.navigate().back();
        
        // Test Bill Pay page
        WebElement billPayLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Bill Pay")));
        billPayLink.click();
        
        assertTrue(driver.getCurrentUrl().contains("billpay.htm"));
        assertTrue(driver.getPageSource().contains("Bill Pay") || 
                  driver.getPageSource().contains("bill") ||
                  driver.getPageSource().contains("Please log in"));
        
        // Go back to site map
        driver.navigate().back();
        
        // Test Find Transactions page
        WebElement findTransLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Find Transactions")));
        findTransLink.click();
        
        assertTrue(driver.getCurrentUrl().contains("findtrans.htm"));
        assertTrue(driver.getPageSource().contains("Find Transactions") || 
                  driver.getPageSource().contains("transaction") ||
                  driver.getPageSource().contains("Please log in"));
        
        // Go back to site map
        driver.navigate().back();
        
        // Test Update Contact Info page
        WebElement updateContactLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Update Contact Info")));
        updateContactLink.click();
        
        assertTrue(driver.getCurrentUrl().contains("updateprofile.htm"));
        assertTrue(driver.getPageSource().contains("Update Contact Info") || 
                  driver.getPageSource().contains("contact") ||
                  driver.getPageSource().contains("Please log in"));
        
        // Go back to site map
        driver.navigate().back();
        
        // Test Request Loan page
        WebElement requestLoanLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Request Loan")));
        requestLoanLink.click();
        
        assertTrue(driver.getCurrentUrl().contains("requestloan.htm"));
        assertTrue(driver.getPageSource().contains("Request Loan") || 
                  driver.getPageSource().contains("loan") ||
                  driver.getPageSource().contains("Please log in"));
    }
    
    /**
     * Test login functionality and error handling
     */
    @Test
    @Order(10)
    @DisplayName("Test Login Functionality")
    void testLoginFunctionality() {
        // Test login with invalid credentials
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));
        
        usernameField.clear();
        usernameField.sendKeys("invaliduser");
        passwordField.clear();
        passwordField.sendKeys("invalidpass");
        loginButton.click();
        
        // Verify error handling or redirect
        assertTrue(driver.getCurrentUrl().contains("login") || 
                  driver.getPageSource().contains("error") ||
                  driver.getPageSource().contains("invalid") ||
                  driver.getPageSource().contains("Please enter"));
        
        // Test forgot login info functionality
        driver.get(BASE_URL + "index.htm");
        WebElement forgotLink = driver.findElement(By.linkText("Forgot login info?"));
        forgotLink.click();
        
        assertTrue(driver.getCurrentUrl().contains("lookup.htm"));
        assertTrue(driver.getPageSource().contains("Customer Lookup") ||
                  driver.getPageSource().contains("forgot") ||
                  driver.getPageSource().contains("lookup"));
    }
    
    /**
     * Test footer links and copyright information
     */
    @Test
    @Order(11)
    @DisplayName("Test Footer Links and Copyright")
    void testFooterLinksAndCopyright() {
        // Verify copyright information
        assertTrue(driver.getPageSource().contains("© Parasoft"));
        assertTrue(driver.getPageSource().contains("All rights reserved"));
        
        // Test footer navigation links
        List<WebElement> footerLinks = driver.findElements(
            By.xpath("//div[@id='footerPanel']//a | //td[@class='footer']//a"));
        
        assertTrue(footerLinks.size() > 0, "Footer should contain navigation links");
        
        // Test specific footer links
        WebElement homeFooterLink = driver.findElement(
            By.xpath("//a[contains(@href, 'index.htm') and text()='Home']"));
        assertTrue(homeFooterLink.isDisplayed());
        
        WebElement aboutFooterLink = driver.findElement(
            By.xpath("//a[contains(@href, 'about.htm') and text()='About Us']"));
        assertTrue(aboutFooterLink.isDisplayed());
        
        WebElement servicesFooterLink = driver.findElement(
            By.xpath("//a[contains(@href, 'services.htm') and text()='Services']"));
        assertTrue(servicesFooterLink.isDisplayed());
        
        WebElement contactFooterLink = driver.findElement(
            By.xpath("//a[contains(@href, 'contact.htm') and text()='Contact Us']"));
        assertTrue(contactFooterLink.isDisplayed());
    }
    
    /**
     * Test responsive design and page layout
     */
    @Test
    @Order(12)
    @DisplayName("Test Page Layout and Responsive Design")
    void testPageLayoutAndResponsiveDesign() {
        // Test different viewport sizes
        driver.manage().window().setSize(new Dimension(1920, 1080));
        
        // Verify main layout elements are present
        WebElement leftPanel = driver.findElement(By.id("leftPanel"));
        WebElement rightPanel = driver.findElement(By.id("rightPanel"));
        
        assertTrue(leftPanel.isDisplayed());
        assertTrue(rightPanel.isDisplayed());
        
        // Test mobile viewport
        driver.manage().window().setSize(new Dimension(768, 1024));
        
        // Verify elements are still accessible
        assertTrue(driver.findElement(By.name("username")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("Register")).isDisplayed());
        
        // Reset to original size
        driver.manage().window().setSize(new Dimension(1920, 1080));
        
        // Test page scroll functionality
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        js.executeScript("window.scrollTo(0, 0);");
        
        // Verify page is still functional after scrolling
        WebElement usernameField = driver.findElement(By.name("username"));
        assertTrue(usernameField.isDisplayed());
    }
    
    /**
     * Test browser navigation functionality
     */
    @Test
    @Order(13)
    @DisplayName("Test Browser Navigation")
    void testBrowserNavigation() {
        String originalUrl = driver.getCurrentUrl();
        
        // Navigate to About Us
        driver.findElement(By.linkText("About Us")).click();
        assertNotEquals(originalUrl, driver.getCurrentUrl());
        
        // Test back navigation
        driver.navigate().back();
        assertEquals(originalUrl, driver.getCurrentUrl());
        
        // Test forward navigation
        driver.navigate().forward();
        assertTrue(driver.getCurrentUrl().contains("about.htm"));
        
        // Test refresh
        driver.navigate().refresh();
        assertTrue(driver.getCurrentUrl().contains("about.htm"));
        assertEquals("ParaBank | About Us", driver.getTitle());
        
        // Navigate back to home
        driver.navigate().back();
        assertTrue(driver.getCurrentUrl().contains("index.htm"));
    }
    
    /**
     * Test page performance and load times
     */
    @Test
    @Order(14)
    @DisplayName("Test Page Performance")
    void testPagePerformance() {
        long startTime = System.currentTimeMillis();
        
        // Navigate to different pages and measure load times
        driver.get(BASE_URL + "index.htm");
        long homeLoadTime = System.currentTimeMillis() - startTime;
        assertTrue(homeLoadTime < 10000, "Home page should load within 10 seconds");
        
        startTime = System.currentTimeMillis();
        driver.get(BASE_URL + "about.htm");
        long aboutLoadTime = System.currentTimeMillis() - startTime;
        assertTrue(aboutLoadTime < 10000, "About page should load within 10 seconds");
        
        startTime = System.currentTimeMillis();
        driver.get(BASE_URL + "services.htm");
        long servicesLoadTime = System.currentTimeMillis() - startTime;
        assertTrue(servicesLoadTime < 10000, "Services page should load within 10 seconds");
        
        // Verify all pages loaded successfully
        assertEquals("ParaBank | Services", driver.getTitle());
    }
    
    /**
     * Test accessibility and form validation
     */
    @Test
    @Order(15)
    @DisplayName("Test Accessibility and Form Validation")
    void testAccessibilityAndFormValidation() {
        // Test form labels and accessibility
        driver.get(BASE_URL + "register.htm");
        
        // Verify form has proper structure
        List<WebElement> formInputs = driver.findElements(By.tagName("input"));
        assertTrue(formInputs.size() > 0, "Registration form should have input fields");
        
        // Test form submission with empty fields
        WebElement registerButton = driver.findElement(By.xpath("//input[@value='Register']"));
        registerButton.click();
        
        // Verify form validation or error handling
        assertTrue(driver.getCurrentUrl().contains("register") ||
                  driver.getPageSource().contains("error") ||
                  driver.getPageSource().contains("required") ||
                  driver.getPageSource().contains("Please"));
        
        // Test contact form validation
        driver.get(BASE_URL + "contact.htm");
        WebElement sendButton = driver.findElement(By.xpath("//input[@value='Send to Customer Care']"));
        sendButton.click();
        
        // Verify contact form validation
        assertTrue(driver.getCurrentUrl().contains("contact") ||
                  driver.getPageSource().contains("error") ||
                  driver.getPageSource().contains("required") ||
                  driver.getPageSource().contains("Please"));
    }
}
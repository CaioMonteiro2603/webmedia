package deepseek.ws04.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BugBankTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String originalWindow;

    @BeforeEach
    public void setUp() {
        // Initialize ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        originalWindow = driver.getWindowHandle();
        
        // Maximize window
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        // Close all browser windows
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomePageElements() {
        // Navigate to home page
        driver.get("https://bugbank.netlify.app/");
        
        // Verify page title
        Assertions.assertEquals("BugBank", driver.getTitle());
        
        // Verify main elements are present
        Assertions.assertTrue(driver.findElement(By.cssSelector(".home__logo")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".home__title")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".home__subtitle")).isDisplayed());
        
        // Verify buttons are present and clickable
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".home__buttonRegister")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".home__buttonLogin")));
        
        Assertions.assertTrue(registerButton.isDisplayed());
        Assertions.assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void testRegistrationProcess() {
        driver.get("https://bugbank.netlify.app/");
        
        // Click register button
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".home__buttonRegister")));
        registerButton.click();
        
        // Verify registration page
        wait.until(ExpectedConditions.urlContains("/register"));
        Assertions.assertTrue(driver.findElement(By.cssSelector(".card__register")).isDisplayed());
        
        // Fill registration form
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("passwordConfirmation"));
        WebElement createAccountButton = driver.findElement(By.cssSelector(".style__ContainerButton-sc-1wsixal-0.ihZxkY.button__child"));
        
        // Generate unique email for test
        String email = "testuser" + System.currentTimeMillis() + "@example.com";
        
        emailField.sendKeys(email);
        nameField.sendKeys("Test User");
        passwordField.sendKeys("Test@123");
        confirmPasswordField.sendKeys("Test@123");
        
        // Click create account button
        createAccountButton.click();
        
        // Verify successful registration
        WebElement modalTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal__title")));
        Assertions.assertEquals("foi criada com sucesso", modalTitle.getText());
        
        // Close modal
        WebElement closeButton = driver.findElement(By.cssSelector(".style__ContainerButton-sc-1wsixal-0.ihZxkY.button__child"));
        closeButton.click();
    }

    @Test
    public void testLoginProcess() {
        // First register a test user
        testRegistrationProcess();
        
        // Navigate to login page
        driver.get("https://bugbank.netlify.app/");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".home__buttonLogin")));
        loginButton.click();
        
        // Verify login page
        wait.until(ExpectedConditions.urlContains("/login"));
        Assertions.assertTrue(driver.findElement(By.cssSelector(".card__login")).isDisplayed());
        
        // Fill login form
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginSubmitButton = driver.findElement(By.cssSelector(".style__ContainerButton-sc-1wsixal-0.ihZxkY.button__child"));
        
        emailField.sendKeys("testuser@example.com");
        passwordField.sendKeys("Test@123");
        
        // Click login button
        loginSubmitButton.click();
        
        // Verify successful login
        wait.until(ExpectedConditions.urlContains("/account"));
        Assertions.assertTrue(driver.findElement(By.cssSelector(".home__title")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".home__subtitle")).isDisplayed());
    }

    @Test
    public void testAccountDashboard() {
        // Login first
        testLoginProcess();
        
        // Verify dashboard elements
        Assertions.assertTrue(driver.findElement(By.cssSelector(".account__balance")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".account__number")).isDisplayed());
        
        // Verify menu buttons
        List<WebElement> menuButtons = driver.findElements(By.cssSelector(".home__button"));
        Assertions.assertEquals(3, menuButtons.size());
        
        // Verify each button is clickable
        for (WebElement button : menuButtons) {
            Assertions.assertTrue(button.isDisplayed());
            Assertions.assertTrue(button.isEnabled());
        }
    }

    @Test
    public void testTransferMoney() {
        // Login first
        testLoginProcess();
        
        // Click transfer button
        WebElement transferButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Transferência')]")));
        transferButton.click();
        
        // Verify transfer page
        wait.until(ExpectedConditions.urlContains("/transfer"));
        Assertions.assertTrue(driver.findElement(By.cssSelector(".transfer__form")).isDisplayed());
        
        // Fill transfer form
        WebElement accountNumberField = driver.findElement(By.name("accountNumber"));
        WebElement amountField = driver.findElement(By.name("amount"));
        WebElement descriptionField = driver.findElement(By.name("description"));
        WebElement transferSubmitButton = driver.findElement(By.cssSelector(".style__ContainerButton-sc-1wsixal-0.ihZxkY.button__child"));
        
        accountNumberField.sendKeys("12345");
        amountField.sendKeys("100");
        descriptionField.sendKeys("Test transfer");
        
        // Click transfer button
        transferSubmitButton.click();
        
        // Verify transfer confirmation
        WebElement modalTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal__title")));
        Assertions.assertTrue(modalTitle.getText().contains("sucesso"));
        
        // Close modal
        WebElement closeButton = driver.findElement(By.cssSelector(".style__ContainerButton-sc-1wsixal-0.ihZxkY.button__child"));
        closeButton.click();
    }

    @Test
    public void testViewTransactions() {
        // Login first
        testLoginProcess();
        
        // Click transactions button
        WebElement transactionsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Extrato')]")));
        transactionsButton.click();
        
        // Verify transactions page
        wait.until(ExpectedConditions.urlContains("/transactions"));
        Assertions.assertTrue(driver.findElement(By.cssSelector(".transactions__container")).isDisplayed());
        
        // Verify transactions table
        WebElement transactionsTable = driver.findElement(By.cssSelector(".transactions__table"));
        Assertions.assertTrue(transactionsTable.isDisplayed());
    }

    @Test
    public void testExternalLinks() {
        driver.get("https://bugbank.netlify.app/");
        
        // Find all external links
        List<WebElement> externalLinks = driver.findElements(By.cssSelector("a[href^='http']"));
        
        for (WebElement link : externalLinks) {
            String href = link.getAttribute("href");
            if (!href.contains("bugbank.netlify.app")) {
                // Open link in new tab
                link.click();
                
                // Wait for new tab to open
                wait.until(ExpectedConditions.numberOfWindowsToBe(2));
                
                // Switch to new tab
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!originalWindow.equals(windowHandle)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }
                
                // Verify new tab opened
                Assertions.assertNotEquals(originalWindow, driver.getWindowHandle());
                
                // Verify page loaded
                Assertions.assertTrue(driver.getTitle().length() > 0);
                
                // Close tab and switch back
                driver.close();
                driver.switchTo().window(originalWindow);
            }
        }
    }

    @Test
    public void testLogout() {
        // Login first
        testLoginProcess();
        
        // Click logout button
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Sair')]")));
        logoutButton.click();
        
        // Verify logout
        wait.until(ExpectedConditions.urlContains("/login"));
        Assertions.assertTrue(driver.findElement(By.cssSelector(".card__login")).isDisplayed());
    }
}
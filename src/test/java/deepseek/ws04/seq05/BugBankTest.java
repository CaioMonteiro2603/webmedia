package deepseek.ws04.seq05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BugBankTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://bugbank.netlify.app/";
    
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    // Helper methods
    private void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
    private void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }
    
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    @Test
    public void testHomePageElements() {
        // Verify main page elements
        Assertions.assertTrue(isElementPresent(By.xpath("//h1[contains(text(),'BugBank')]")), 
            "BugBank logo should be present");
        Assertions.assertTrue(isElementPresent(By.xpath("//h2[contains(text(),'O banco com bugs e falhas do seu jeito')]")), 
            "Subtitle should be present");
        
        // Verify login form elements
        Assertions.assertTrue(isElementPresent(By.cssSelector("input[placeholder='E-mail']")), 
            "Email field should be present");
        Assertions.assertTrue(isElementPresent(By.cssSelector("input[placeholder='Senha']")), 
            "Password field should be present");
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Acessar')]")), 
            "Login button should be present");
        
        // Verify registration button
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Registrar')]")), 
            "Register button should be present");
    }

    @Test
    public void testRegistrationFlow() {
        // Click register button
        click(By.xpath("//button[contains(text(),'Registrar')]"));
        
        // Fill registration form
        type(By.cssSelector("input[placeholder='Informe seu e-mail']"), "testuser@example.com");
        type(By.cssSelector("input[placeholder='Informe seu nome']"), "Test User");
        type(By.cssSelector("input[placeholder='Informe sua senha']"), "Test@123");
        type(By.cssSelector("input[placeholder='Informe a confirmação da senha']"), "Test@123");
        
        // Click create account with balance
        click(By.xpath("//label[contains(text(),'Criar conta com saldo')]/../div"));
        click(By.xpath("//button[contains(text(),'Cadastrar')]"));
        
        // Verify account creation
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(),'foi criada com sucesso')]")));
        Assertions.assertTrue(modal.isDisplayed(), "Account creation success modal should appear");
        
        // Close modal
        click(By.xpath("//button[contains(text(),'Fechar')]"));
    }

    @Test
    public void testLoginFlow() {
        // First register a test account
        testRegistrationFlow();
        
        // Fill login form
        type(By.cssSelector("input[placeholder='E-mail']"), "testuser@example.com");
        type(By.cssSelector("input[placeholder='Senha']"), "Test@123");
        
        // Click login button
        click(By.xpath("//button[contains(text(),'Acessar')]"));
        
        // Verify dashboard appears after login
        Assertions.assertTrue(isElementPresent(By.xpath("//p[contains(text(),'Bem vindo')]")), 
            "Welcome message should appear after login");
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Sair')]")), 
            "Logout button should be present");
    }

    @Test
    public void testDashboardFunctionality() {
        // Login first
        testLoginFlow();
        
        // Verify account balance display
        Assertions.assertTrue(isElementPresent(By.xpath("//div[contains(@class, 'home__ContainerBalance-sc')]")), 
            "Balance container should be visible");
        
        // Verify transfer button
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Transferência')]")), 
            "Transfer button should be present");
        
        // Verify statement button
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Extrato')]")), 
            "Statement button should be present");
    }

    @Test
    public void testTransferFunctionality() {
        // Login first
        testLoginFlow();
        
        // Create a second account for transfer
        click(By.xpath("//button[contains(text(),'Sair')]"));
        testRegistrationFlow();
        String accountNumber = driver.findElement(By.xpath("//div[contains(text(),'Número:')]/p")).getText();
        click(By.xpath("//button[contains(text(),'Fechar')]"));
        
        // Login with first account
        type(By.cssSelector("input[placeholder='E-mail']"), "testuser@example.com");
        type(By.cssSelector("input[placeholder='Senha']"), "Test@123");
        click(By.xpath("//button[contains(text(),'Acessar')]"));
        
        // Open transfer modal
        click(By.xpath("//button[contains(text(),'Transferência')]"));
        
        // Fill transfer form
        type(By.cssSelector("input[placeholder='Número da conta']"), accountNumber.split(": ")[1]);
        type(By.cssSelector("input[placeholder='Digite o valor']"), "50");
        type(By.cssSelector("input[placeholder='Descrição']"), "Test transfer");
        click(By.xpath("//button[contains(text(),'Transferir agora')]"));
        
        // Verify transfer success
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[contains(text(),'Transferencia realizada com sucesso')]")));
        Assertions.assertTrue(successMessage.isDisplayed(), "Transfer success message should appear");
    }

    @Test
    public void testStatementFunctionality() {
        // Login first
        testLoginFlow();
        
        // Make a transfer to generate statement
        testTransferFunctionality();
        
        // Open statement
        click(By.xpath("//button[contains(text(),'Extrato')]"));
        
        // Verify statement entries
        List<WebElement> statementEntries = driver.findElements(By.xpath("//div[contains(@class, 'home__ContainerList-sc')]/div"));
        Assertions.assertTrue(statementEntries.size() > 0, "Statement should contain entries");
        
        // Verify statement filters
        Assertions.assertTrue(isElementPresent(By.xpath("//input[@type='date']")), 
            "Date filter should be present");
        Assertions.assertTrue(isElementPresent(By.xpath("//button[contains(text(),'Filtrar')]")), 
            "Filter button should be present");
    }

    @Test
    public void testExternalLinks() {
        // Verify Facebook link
        WebElement facebookLink = driver.findElement(By.xpath("//a[contains(@href,'facebook.com')]"));
        Assertions.assertTrue(facebookLink.isDisplayed(), "Facebook link should be present");
        
        // Verify Instagram link
        WebElement instagramLink = driver.findElement(By.xpath("//a[contains(@href,'instagram.com')]"));
        Assertions.assertTrue(instagramLink.isDisplayed(), "Instagram link should be present");
        
        // Verify LinkedIn link
        WebElement linkedinLink = driver.findElement(By.xpath("//a[contains(@href,'linkedin.com')]"));
        Assertions.assertTrue(linkedinLink.isDisplayed(), "LinkedIn link should be present");
        
        // Note: Actual navigation to external sites would require additional handling
    }
}
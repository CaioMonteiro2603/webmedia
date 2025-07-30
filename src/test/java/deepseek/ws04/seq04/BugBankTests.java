package deepseek.ws04.seq04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class BugBankTests {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://bugbank.netlify.app/";

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    public void resetApp() {
        driver.get(BASE_URL);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginPageElements() {
        // Verify page title
        assertEquals("BugBank | O banco com bugs e falhas do seu jeito", driver.getTitle());

        // Verify email field
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='email']")));
        assertEquals("Informe seu e-mail", emailField.getAttribute("placeholder"));

        // Verify password field
        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        assertEquals("Informe sua senha", passwordField.getAttribute("placeholder"));

        // Verify login button
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        assertTrue(loginButton.isDisplayed());

        // Verify register button
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        assertTrue(registerButton.isDisplayed());

        // Verify requirements link
        WebElement requirementsLink = driver.findElement(By.linkText("Conheça nossos requisitos"));
        assertTrue(requirementsLink.isDisplayed());
    }

    @Test
    public void testSuccessfulRegistration() {
        // Click register button
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();

        // Fill registration form
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='email']")));
        emailField.sendKeys("test" + System.currentTimeMillis() + "@example.com");

        WebElement nameField = driver.findElement(By.cssSelector("input[type='name']"));
        nameField.sendKeys("Test User");

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys("Test@123");

        WebElement confirmPasswordField = driver.findElement(
            By.cssSelector("input[placeholder='Informe a confirmação da senha']"));
        confirmPasswordField.sendKeys("Test@123");

        // Click register button
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        submitButton.click();

        // Verify registration success
        WebElement modalTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h2[contains(text(), 'foi criada com sucesso')]")));
        assertTrue(modalTitle.isDisplayed());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Enter invalid credentials
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='email']")));
        emailField.sendKeys("invalid@example.com");

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys("wrongpassword");

        // Click login button
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        loginButton.click();

        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'Usuário ou senha inválido')]")));
        assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void testRequirementsPageNavigation() {
        // Click requirements link
        WebElement requirementsLink = driver.findElement(By.linkText("Conheça nossos requisitos"));
        requirementsLink.click();

        // Verify requirements page
        WebElement requirementsTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h1[contains(text(), 'Requisitos')]")));
        assertTrue(requirementsTitle.isDisplayed());

        // Verify back button
        WebElement backButton = driver.findElement(By.xpath("//a[text()='Voltar ao login']"));
        assertTrue(backButton.isDisplayed());
    }

    @Test
    public void testAccountDashboardAfterLogin() {
        // Register a new user
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();

        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "Test@123";

        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='email']")));
        emailField.sendKeys(email);

        WebElement nameField = driver.findElement(By.cssSelector("input[type='name']"));
        nameField.sendKeys("Test User");

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = driver.findElement(
            By.cssSelector("input[placeholder='Informe a confirmação da senha']"));
        confirmPasswordField.sendKeys(password);

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        submitButton.click();

        // Close registration modal
        WebElement closeButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[text()='Fechar']")));
        closeButton.click();

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        // Login with new user
        emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='email']")));
        emailField.sendKeys(email);

        passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys(password);

        loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        loginButton.click();

        // Verify dashboard elements
        WebElement welcomeMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'Olá')]")));
        assertTrue(welcomeMessage.isDisplayed());

        WebElement balanceElement = driver.findElement(By.id("textBalance"));
        assertTrue(balanceElement.isDisplayed());

        WebElement transferButton = driver.findElement(By.id("btn-TRANSFERÊNCIA"));
        assertTrue(transferButton.isDisplayed());

        WebElement paymentButton = driver.findElement(By.id("btn-PAGAMENTOS"));
        assertTrue(paymentButton.isDisplayed());

        WebElement withdrawButton = driver.findElement(By.id("btn-SAQUE"));
        assertTrue(withdrawButton.isDisplayed());

        WebElement statementButton = driver.findElement(By.id("btn-EXTRATO"));
        assertTrue(statementButton.isDisplayed());
    }

    @Test
    public void testTransferBetweenAccounts() {
        // First register and login two users
        // User 1
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();

        String email1 = "user1" + System.currentTimeMillis() + "@example.com";
        String password = "Test@123";

        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='email']")));
        emailField.sendKeys(email1);

        WebElement nameField = driver.findElement(By.cssSelector("input[type='name']"));
        nameField.sendKeys("User One");

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = driver.findElement(
            By.cssSelector("input[placeholder='Informe a confirmação da senha']"));
        confirmPasswordField.sendKeys(password);

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        submitButton.click();

        WebElement accountElement = wait.until(ExpectedConditions.presenceOfElementLocated(
        	    By.xpath("//p[contains(text(), 'Conta digital')]")));

        	String accountNumber1 = accountElement.getText().replaceAll("[^0-9]", "");

        WebElement closeButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[text()='Fechar']")));
        closeButton.click();

        // User 2
        registerButton = driver.findElement(By.xpath("//button[text()='Registrar']"));
        registerButton.click();

        String email2 = "user2" + System.currentTimeMillis() + "@example.com";

        emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='email']")));
        emailField.sendKeys(email2);

        nameField = driver.findElement(By.cssSelector("input[type='name']"));
        nameField.sendKeys("User Two");

        passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys(password);

        confirmPasswordField = driver.findElement(
            By.cssSelector("input[placeholder='Informe a confirmação da senha']"));
        confirmPasswordField.sendKeys(password);

        submitButton = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        submitButton.click();

        WebElement accountElement2 = wait.until(ExpectedConditions.presenceOfElementLocated(
        	    By.xpath("//p[contains(text(), 'Conta digital')]")));

        	String accountNumber2 = accountElement2.getText().replaceAll("[^0-9]", "");


        closeButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[text()='Fechar']")));
        closeButton.click();

        // Login with User 1
        emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='email']")));
        emailField.sendKeys(email1);

        passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Acessar']"));
        loginButton.click();

        // Perform transfer
        WebElement transferButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("btn-TRANSFERÊNCIA")));
        transferButton.click();

        WebElement accountNumberField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[placeholder='Informe o número da conta']")));
        accountNumberField.sendKeys(accountNumber2);

        WebElement amountField = driver.findElement(By.cssSelector("input[placeholder='Informe o valor da transferência']"));
        amountField.sendKeys("100");

        WebElement descriptionField = driver.findElement(By.cssSelector("input[placeholder='Informe uma descrição']"));
        descriptionField.sendKeys("Test transfer");

        WebElement transferSubmitButton = driver.findElement(By.xpath("//button[text()='Transferir agora']"));
        transferSubmitButton.click();

        // Verify transfer success
        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'Transferencia realizada com sucesso')]")));
        assertTrue(successMessage.isDisplayed());
    }
}
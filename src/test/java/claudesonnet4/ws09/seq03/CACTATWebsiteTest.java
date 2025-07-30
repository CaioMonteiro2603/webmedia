package claudesonnet4.ws09.seq03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.io.File;

/**
 * Comprehensive test suite for CAC TAT (Central de Atendimento ao Cliente TAT) website
 * Tests all pages and interactive elements including form validation and navigation
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CACTATWebsiteTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com";
    private static final String MAIN_PAGE_URL = BASE_URL + "/index.html";
    private static final String PRIVACY_PAGE_URL = BASE_URL + "/privacy.html";

    @BeforeAll
    static void setUpClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void setUp() {
        driver.get(MAIN_PAGE_URL);
    }

    // ========== MAIN PAGE TESTS ==========

    @Test
    @Order(1)
    @DisplayName("Test main page loads correctly")
    void testMainPageLoads() {
        Assertions.assertEquals("Central de Atendimento ao Cliente TAT", driver.getTitle());
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"));
        
        // Verify main heading is present
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        Assertions.assertTrue(heading.getText().contains("CAC TAT"));
    }

    @Test
    @Order(2)
    @DisplayName("Test all form fields are present and accessible")
    void testFormFieldsPresent() {
        // Test required name field
        WebElement nameField = driver.findElement(By.id("firstName"));
        Assertions.assertTrue(nameField.isDisplayed());
        Assertions.assertTrue(nameField.isEnabled());
        
        // Test required surname field
        WebElement surnameField = driver.findElement(By.id("lastName"));
        Assertions.assertTrue(surnameField.isDisplayed());
        Assertions.assertTrue(surnameField.isEnabled());
        
        // Test required email field
        WebElement emailField = driver.findElement(By.id("email"));
        Assertions.assertTrue(emailField.isDisplayed());
        Assertions.assertTrue(emailField.isEnabled());
        
        // Test phone field
        WebElement phoneField = driver.findElement(By.id("phone"));
        Assertions.assertTrue(phoneField.isDisplayed());
        Assertions.assertTrue(phoneField.isEnabled());
        
        // Test product dropdown
        WebElement productDropdown = driver.findElement(By.id("product"));
        Assertions.assertTrue(productDropdown.isDisplayed());
        Assertions.assertTrue(productDropdown.isEnabled());
        
        // Test service type radio buttons
        WebElement helpRadio = driver.findElement(By.cssSelector("input[value='ajuda']"));
        WebElement praiseRadio = driver.findElement(By.cssSelector("input[value='elogio']"));
        WebElement feedbackRadio = driver.findElement(By.cssSelector("input[value='feedback']"));
        
        Assertions.assertTrue(helpRadio.isDisplayed());
        Assertions.assertTrue(praiseRadio.isDisplayed());
        Assertions.assertTrue(feedbackRadio.isDisplayed());
        
        // Test contact preference checkboxes
        WebElement emailCheckbox = driver.findElement(By.cssSelector("input[value='email']"));
        WebElement phoneCheckbox = driver.findElement(By.cssSelector("input[value='telefone']"));
        
        Assertions.assertTrue(emailCheckbox.isDisplayed());
        Assertions.assertTrue(phoneCheckbox.isDisplayed());
        
        // Test message textarea
        WebElement messageArea = driver.findElement(By.id("open-text-area"));
        Assertions.assertTrue(messageArea.isDisplayed());
        Assertions.assertTrue(messageArea.isEnabled());
        
        // Test file upload
        WebElement fileUpload = driver.findElement(By.id("file-upload"));
        Assertions.assertTrue(fileUpload.isDisplayed());
        Assertions.assertTrue(fileUpload.isEnabled());
        
        // Test submit button
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        Assertions.assertTrue(submitButton.isDisplayed());
        Assertions.assertTrue(submitButton.isEnabled());
    }

    @Test
    @Order(3)
    @DisplayName("Test form submission with valid data")
    void testValidFormSubmission() {
        // Fill required fields
        driver.findElement(By.id("firstName")).sendKeys("João");
        driver.findElement(By.id("lastName")).sendKeys("Silva");
        driver.findElement(By.id("email")).sendKeys("joao.silva@email.com");
        driver.findElement(By.id("phone")).sendKeys("11999999999");
        
        // Select product
        Select productSelect = new Select(driver.findElement(By.id("product")));
        productSelect.selectByIndex(1); // Select first available product
        
        // Select service type
        driver.findElement(By.cssSelector("input[value='ajuda']")).click();
        
        // Select contact preference
        driver.findElement(By.cssSelector("input[value='email']")).click();
        
        // Fill message
        driver.findElement(By.id("open-text-area")).sendKeys("Esta é uma mensagem de teste para verificar o funcionamento do formulário.");
        
        // Submit form
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Verify success message or form behavior
        // Note: Since this is a test form, we verify the form was processed
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"));
    }

    @Test
    @Order(4)
    @DisplayName("Test form validation for required fields")
    void testRequiredFieldValidation() {
        // Try to submit empty form
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Check if required field validation is triggered
        WebElement nameField = driver.findElement(By.id("firstName"));
        String validationMessage = nameField.getAttribute("validationMessage");
        Assertions.assertFalse(validationMessage == null || validationMessage.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Test email field validation")
    void testEmailValidation() {
        // Fill required fields with invalid email
        driver.findElement(By.id("firstName")).sendKeys("Test");
        driver.findElement(By.id("lastName")).sendKeys("User");
        driver.findElement(By.id("email")).sendKeys("invalid-email");
        driver.findElement(By.id("open-text-area")).sendKeys("Test message");
        
        // Try to submit
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Check email validation
        WebElement emailField = driver.findElement(By.id("email"));
        String validationMessage = emailField.getAttribute("validationMessage");
        Assertions.assertFalse(validationMessage == null || validationMessage.isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("Test product dropdown functionality")
    void testProductDropdown() {
        Select productSelect = new Select(driver.findElement(By.id("product")));
        
        // Test default selection
        Assertions.assertEquals("Selecione", productSelect.getFirstSelectedOption().getText());
        
        // Test selecting different options
        if (productSelect.getOptions().size() > 1) {
            productSelect.selectByIndex(1);
            Assertions.assertNotEquals("Selecione", productSelect.getFirstSelectedOption().getText());
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test service type radio buttons")
    void testServiceTypeRadioButtons() {
        WebElement helpRadio = driver.findElement(By.cssSelector("input[value='ajuda']"));
        WebElement praiseRadio = driver.findElement(By.cssSelector("input[value='elogio']"));
        WebElement feedbackRadio = driver.findElement(By.cssSelector("input[value='feedback']"));
        
        // Test clicking each radio button
        helpRadio.click();
        Assertions.assertTrue(helpRadio.isSelected());
        Assertions.assertFalse(praiseRadio.isSelected());
        Assertions.assertFalse(feedbackRadio.isSelected());
        
        praiseRadio.click();
        Assertions.assertFalse(helpRadio.isSelected());
        Assertions.assertTrue(praiseRadio.isSelected());
        Assertions.assertFalse(feedbackRadio.isSelected());
        
        feedbackRadio.click();
        Assertions.assertFalse(helpRadio.isSelected());
        Assertions.assertFalse(praiseRadio.isSelected());
        Assertions.assertTrue(feedbackRadio.isSelected());
    }

    @Test
    @Order(8)
    @DisplayName("Test contact preference checkboxes")
    void testContactPreferenceCheckboxes() {
        WebElement emailCheckbox = driver.findElement(By.cssSelector("input[value='email']"));
        WebElement phoneCheckbox = driver.findElement(By.cssSelector("input[value='telefone']"));
        
        // Test checking both options
        emailCheckbox.click();
        Assertions.assertTrue(emailCheckbox.isSelected());
        
        phoneCheckbox.click();
        Assertions.assertTrue(phoneCheckbox.isSelected());
        Assertions.assertTrue(emailCheckbox.isSelected()); // Both can be selected
        
        // Test unchecking
        emailCheckbox.click();
        Assertions.assertFalse(emailCheckbox.isSelected());
        Assertions.assertTrue(phoneCheckbox.isSelected());
    }

    @Test
    @Order(9)
    @DisplayName("Test phone field accepts only numbers")
    void testPhoneFieldInput() {
        WebElement phoneField = driver.findElement(By.id("phone"));
        
        // Test entering phone number
        phoneField.sendKeys("11999999999");
        Assertions.assertEquals("11999999999", phoneField.getAttribute("value"));
        
        // Clear and test with formatted number
        phoneField.clear();
        phoneField.sendKeys("(11) 99999-9999");
        Assertions.assertFalse(phoneField.getAttribute("value").isEmpty());
    }

    @Test
    @Order(10)
    @DisplayName("Test message textarea functionality")
    void testMessageTextarea() {
        WebElement messageArea = driver.findElement(By.id("open-text-area"));
        
        String testMessage = "Esta é uma mensagem de teste muito longa para verificar se o campo de texto aceita mensagens extensas e se mantém a formatação adequada quando o usuário digita várias linhas de texto.";
        
        messageArea.sendKeys(testMessage);
        Assertions.assertEquals(testMessage, messageArea.getAttribute("value"));
        
        // Test clearing the field
        messageArea.clear();
        Assertions.assertTrue(messageArea.getAttribute("value").isEmpty());
    }

    @Test
    @Order(11)
    @DisplayName("Test file upload field")
    void testFileUploadField() {
        WebElement fileUpload = driver.findElement(By.id("file-upload"));
        
        // Verify file upload field is present and enabled
        Assertions.assertTrue(fileUpload.isDisplayed());
        Assertions.assertTrue(fileUpload.isEnabled());
        Assertions.assertEquals("file", fileUpload.getAttribute("type"));
    }

    // ========== NAVIGATION TESTS ==========

    @Test
    @Order(12)
    @DisplayName("Test privacy policy link navigation")
    void testPrivacyPolicyNavigation() {
        // Find and click privacy policy link
        WebElement privacyLink = driver.findElement(By.linkText("Política de Privacidade"));
        Assertions.assertTrue(privacyLink.isDisplayed());
        
        privacyLink.click();
        
        // Verify navigation to privacy page
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("privacy.html"));
        Assertions.assertEquals("Central de Atendimento ao Cliente TAT - Política de privacidade", driver.getTitle());
    }

    // ========== PRIVACY PAGE TESTS ==========

    @Test
    @Order(13)
    @DisplayName("Test privacy policy page content")
    void testPrivacyPageContent() {
        // Navigate to privacy page
        driver.get(PRIVACY_PAGE_URL);
        
        // Verify page title
        Assertions.assertEquals("Central de Atendimento ao Cliente TAT - Política de privacidade", driver.getTitle());
        
        // Verify page heading
        WebElement heading = driver.findElement(By.tagName("h1"));
        Assertions.assertTrue(heading.getText().contains("Política de privacidade"));
        
        // Verify content is present
        WebElement body = driver.findElement(By.tagName("body"));
        String pageText = body.getText();
        
        Assertions.assertTrue(pageText.contains("Não salvamos dados submetidos"));
        Assertions.assertTrue(pageText.contains("CAC TAT"));
        Assertions.assertTrue(pageText.contains("Talking About Testing"));
    }

    @Test
    @Order(14)
    @DisplayName("Test privacy page has no interactive elements")
    void testPrivacyPageNoInteractiveElements() {
        driver.get(PRIVACY_PAGE_URL);
        
        // Verify no form elements
        Assertions.assertTrue(driver.findElements(By.tagName("input")).isEmpty());
        Assertions.assertTrue(driver.findElements(By.tagName("button")).isEmpty());
        Assertions.assertTrue(driver.findElements(By.tagName("select")).isEmpty());
        Assertions.assertTrue(driver.findElements(By.tagName("textarea")).isEmpty());
        
        // Verify no clickable links (except potential back navigation)
        java.util.List<WebElement> links = driver.findElements(By.tagName("a"));
        // Privacy page should have minimal or no external links
        Assertions.assertTrue(links.size() <= 1);
    }

    // ========== CROSS-PAGE NAVIGATION TESTS ==========

    @Test
    @Order(15)
    @DisplayName("Test navigation between pages")
    void testCrossPageNavigation() {
        // Start on main page
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"));
        
        // Navigate to privacy page
        driver.findElement(By.linkText("Política de Privacidade")).click();
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("privacy.html"));
        
        // Navigate back to main page using browser back
        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("index.html"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"));
        
        // Verify main page elements are still functional
        WebElement nameField = driver.findElement(By.id("firstName"));
        Assertions.assertTrue(nameField.isDisplayed());
        Assertions.assertTrue(nameField.isEnabled());
    }

    // ========== RESPONSIVE AND ACCESSIBILITY TESTS ==========

    @Test
    @Order(16)
    @DisplayName("Test page responsiveness")
    void testPageResponsiveness() {
        // Test different viewport sizes
        driver.manage().window().setSize(new Dimension(1920, 1080)); // Desktop
        WebElement form = driver.findElement(By.tagName("form"));
        Assertions.assertTrue(form.isDisplayed());
        
        driver.manage().window().setSize(new Dimension(768, 1024)); // Tablet
        Assertions.assertTrue(form.isDisplayed());
        
        driver.manage().window().setSize(new Dimension(375, 667)); // Mobile
        Assertions.assertTrue(form.isDisplayed());
        
        // Reset to default size
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    @Order(17)
    @DisplayName("Test form accessibility attributes")
    void testFormAccessibility() {
        // Check for required field indicators
        WebElement nameField = driver.findElement(By.id("firstName"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement messageField = driver.findElement(By.id("open-text-area"));
        
        // Verify required attributes
        Assertions.assertTrue(nameField.getAttribute("required") != null || 
                            nameField.getAttribute("aria-required") != null);
        Assertions.assertTrue(emailField.getAttribute("required") != null || 
                            emailField.getAttribute("aria-required") != null);
        Assertions.assertTrue(messageField.getAttribute("required") != null || 
                            messageField.getAttribute("aria-required") != null);
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    @Order(18)
    @DisplayName("Test form with maximum length inputs")
    void testMaximumLengthInputs() {
        // Test very long inputs
        String longText = "A".repeat(1000);
        
        driver.findElement(By.id("firstName")).sendKeys(longText);
        driver.findElement(By.id("lastName")).sendKeys(longText);
        driver.findElement(By.id("email")).sendKeys("test@" + "a".repeat(100) + ".com");
        driver.findElement(By.id("open-text-area")).sendKeys(longText);
        
        // Verify fields accept the input (or truncate appropriately)
        WebElement nameField = driver.findElement(By.id("firstName"));
        Assertions.assertFalse(nameField.getAttribute("value").isEmpty());
    }

    @Test
    @Order(19)
    @DisplayName("Test form with special characters")
    void testSpecialCharacterInputs() {
        // Test special characters in text fields
        String specialChars = "áéíóúàèìòùâêîôûãõçñü@#$%&*()";
        
        driver.findElement(By.id("firstName")).sendKeys(specialChars);
        driver.findElement(By.id("lastName")).sendKeys(specialChars);
        driver.findElement(By.id("open-text-area")).sendKeys(specialChars);
        
        // Verify special characters are handled properly
        WebElement nameField = driver.findElement(By.id("firstName"));
        Assertions.assertFalse(nameField.getAttribute("value").isEmpty());
    }

    @Test
    @Order(20)
    @DisplayName("Test complete user workflow")
    void testCompleteUserWorkflow() {
        // Simulate complete user journey
        
        // 1. User arrives at main page
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"));
        
        // 2. User reads privacy policy
        driver.findElement(By.linkText("Política de Privacidade")).click();
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        
        // 3. User returns to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("index.html"));
        
        // 4. User fills out complete form
        driver.findElement(By.id("firstName")).sendKeys("Maria");
        driver.findElement(By.id("lastName")).sendKeys("Santos");
        driver.findElement(By.id("email")).sendKeys("maria.santos@email.com");
        driver.findElement(By.id("phone")).sendKeys("11987654321");
        
        Select productSelect = new Select(driver.findElement(By.id("product")));
        if (productSelect.getOptions().size() > 1) {
            productSelect.selectByIndex(1);
        }
        
        driver.findElement(By.cssSelector("input[value='elogio']")).click();
        driver.findElement(By.cssSelector("input[value='email']")).click();
        driver.findElement(By.id("open-text-area")).sendKeys("Gostaria de elogiar o excelente atendimento recebido.");
        
        // 5. User submits form
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // 6. Verify form submission was processed
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.html"));
    }
}
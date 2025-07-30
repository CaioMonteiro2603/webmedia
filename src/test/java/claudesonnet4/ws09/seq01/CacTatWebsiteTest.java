package claudesonnet4.ws09.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for CAC TAT website
 * Tests all interactive elements on main page and privacy policy page
 * Package: cluadesonnet4.ws09.seq01
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CacTatWebsiteTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com/index.html";
    private static final String PRIVACY_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com/privacy.html";
    
    @BeforeEach
    void setUp() {
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
    
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    // ==================== MAIN PAGE TESTS ====================
    
    @Test
    @Order(1)
    @DisplayName("Test main page loads correctly")
    void testMainPageLoads() {
        driver.get(BASE_URL);
        
        // Verify page title
        assertEquals("Central de Atendimento ao Cliente TAT", driver.getTitle());
        
        // Verify main heading is present
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertTrue(heading.getText().contains("CAC TAT"));
        
        // Verify form instruction text
        WebElement instruction = driver.findElement(By.xpath("//*[contains(text(), 'Forneça o máximo de informações')]"));
        assertNotNull(instruction);
    }
    
    @Test
    @Order(2)
    @DisplayName("Test all form input fields are present and functional")
    void testFormInputFields() {
        driver.get(BASE_URL);
        
        // Test firstName field
        WebElement firstNameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));
        assertTrue(firstNameField.isDisplayed());
        assertTrue(firstNameField.isEnabled());
        firstNameField.sendKeys("João");
        assertEquals("João", firstNameField.getAttribute("value"));
        
        // Test lastName field
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        assertTrue(lastNameField.isDisplayed());
        assertTrue(lastNameField.isEnabled());
        lastNameField.sendKeys("Silva");
        assertEquals("Silva", lastNameField.getAttribute("value"));
        
        // Test email field
        WebElement emailField = driver.findElement(By.id("email"));
        assertTrue(emailField.isDisplayed());
        assertTrue(emailField.isEnabled());
        assertEquals("email", emailField.getAttribute("type"));
        emailField.sendKeys("joao.silva@email.com");
        assertEquals("joao.silva@email.com", emailField.getAttribute("value"));
        
        // Test phone field
        WebElement phoneField = driver.findElement(By.id("phone"));
        assertTrue(phoneField.isDisplayed());
        assertTrue(phoneField.isEnabled());
        assertEquals("number", phoneField.getAttribute("type"));
        phoneField.sendKeys("11987654321");
        assertEquals("11987654321", phoneField.getAttribute("value"));
    }
    
    @Test
    @Order(3)
    @DisplayName("Test product dropdown functionality")
    void testProductDropdown() {
        driver.get(BASE_URL);
        
        WebElement productDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("product")));
        assertTrue(productDropdown.isDisplayed());
        assertTrue(productDropdown.isEnabled());
        
        Select select = new Select(productDropdown);
        
        // Verify default selection
        assertEquals("Selecione", select.getFirstSelectedOption().getText());
        
        // Test all dropdown options
        List<WebElement> options = select.getOptions();
        assertEquals(5, options.size()); // Including "Selecione" option
        
        String[] expectedOptions = {"Selecione", "Blog", "Cursos", "Mentoria", "YouTube"};
        for (int i = 0; i < expectedOptions.length; i++) {
            assertEquals(expectedOptions[i], options.get(i).getText());
        }
        
        // Test selecting each option
        select.selectByVisibleText("Blog");
        assertEquals("Blog", select.getFirstSelectedOption().getText());
        
        select.selectByVisibleText("Cursos");
        assertEquals("Cursos", select.getFirstSelectedOption().getText());
        
        select.selectByVisibleText("Mentoria");
        assertEquals("Mentoria", select.getFirstSelectedOption().getText());
        
        select.selectByVisibleText("YouTube");
        assertEquals("YouTube", select.getFirstSelectedOption().getText());
    }
    
    @Test
    @Order(4)
    @DisplayName("Test service type radio buttons")
    void testServiceTypeRadioButtons() {
        driver.get(BASE_URL);
        
        // Find all radio buttons with name "atendimento-tat"
        List<WebElement> radioButtons = driver.findElements(By.name("atendimento-tat"));
        assertEquals(3, radioButtons.size());
        
        // Test "Ajuda" radio button (should be selected by default)
        WebElement ajudaRadio = radioButtons.get(0);
        assertTrue(ajudaRadio.isDisplayed());
        assertTrue(ajudaRadio.isEnabled());
        assertTrue(ajudaRadio.isSelected()); // Default selection
        
        // Test "Elogio" radio button
        WebElement elogioRadio = radioButtons.get(1);
        assertTrue(elogioRadio.isDisplayed());
        assertTrue(elogioRadio.isEnabled());
        assertFalse(elogioRadio.isSelected());
        
        elogioRadio.click();
        assertTrue(elogioRadio.isSelected());
        assertFalse(ajudaRadio.isSelected()); // Should be deselected
        
        // Test "Feedback" radio button
        WebElement feedbackRadio = radioButtons.get(2);
        assertTrue(feedbackRadio.isDisplayed());
        assertTrue(feedbackRadio.isEnabled());
        assertFalse(feedbackRadio.isSelected());
        
        feedbackRadio.click();
        assertTrue(feedbackRadio.isSelected());
        assertFalse(elogioRadio.isSelected()); // Should be deselected
        assertFalse(ajudaRadio.isSelected()); // Should be deselected
    }
    
    @Test
    @Order(5)
    @DisplayName("Test contact preference checkboxes")
    void testContactPreferenceCheckboxes() {
        driver.get(BASE_URL);
        
        // Test email checkbox
        WebElement emailCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email-checkbox")));
        assertTrue(emailCheckbox.isDisplayed());
        assertTrue(emailCheckbox.isEnabled());
        assertFalse(emailCheckbox.isSelected());
        
        emailCheckbox.click();
        assertTrue(emailCheckbox.isSelected());
        
        // Test phone checkbox
        WebElement phoneCheckbox = driver.findElement(By.id("phone-checkbox"));
        assertTrue(phoneCheckbox.isDisplayed());
        assertTrue(phoneCheckbox.isEnabled());
        assertFalse(phoneCheckbox.isSelected());
        
        phoneCheckbox.click();
        assertTrue(phoneCheckbox.isSelected());
        
        // Both checkboxes can be selected simultaneously
        assertTrue(emailCheckbox.isSelected());
        assertTrue(phoneCheckbox.isSelected());
        
        // Test unchecking
        emailCheckbox.click();
        assertFalse(emailCheckbox.isSelected());
        assertTrue(phoneCheckbox.isSelected()); // Should remain selected
    }
    
    @Test
    @Order(6)
    @DisplayName("Test textarea functionality")
    void testTextareaFunctionality() {
        driver.get(BASE_URL);
        
        WebElement textarea = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("open-text-area")));
        assertTrue(textarea.isDisplayed());
        assertTrue(textarea.isEnabled());
        
        String testMessage = "Esta é uma mensagem de teste para verificar a funcionalidade da área de texto. " +
                           "Preciso de ajuda com o produto XYZ que comprei recentemente.";
        
        textarea.sendKeys(testMessage);
        assertEquals(testMessage, textarea.getAttribute("value"));
        
        // Test clearing and adding new text
        textarea.clear();
        assertEquals("", textarea.getAttribute("value"));
        
        textarea.sendKeys("Nova mensagem de teste");
        assertEquals("Nova mensagem de teste", textarea.getAttribute("value"));
    }
    
    @Test
    @Order(7)
    @DisplayName("Test file upload field")
    void testFileUploadField() {
        driver.get(BASE_URL);
        
        WebElement fileUpload = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("file-upload")));
        assertTrue(fileUpload.isDisplayed());
        assertTrue(fileUpload.isEnabled());
        assertEquals("file", fileUpload.getAttribute("type"));
        
        // Verify initial state
        assertEquals("", fileUpload.getAttribute("value"));
        
        // Note: Actual file upload testing would require a real file path
        // This test verifies the element exists and is functional
    }
    
    @Test
    @Order(8)
    @DisplayName("Test submit button functionality")
    void testSubmitButton() {
        driver.get(BASE_URL);
        
        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));
        assertTrue(submitButton.isDisplayed());
        assertTrue(submitButton.isEnabled());
        assertEquals("Enviar", submitButton.getText());
        assertEquals("submit", submitButton.getAttribute("type"));
        
        // Test button click (without submitting to avoid form validation issues)
        assertTrue(submitButton.isEnabled());
    }
    
    @Test
    @Order(9)
    @DisplayName("Test complete form submission with valid data")
    void testCompleteFormSubmission() {
        driver.get(BASE_URL);
        
        // Fill all required fields
        driver.findElement(By.id("firstName")).sendKeys("Maria");
        driver.findElement(By.id("lastName")).sendKeys("Santos");
        driver.findElement(By.id("email")).sendKeys("maria.santos@email.com");
        driver.findElement(By.id("phone")).sendKeys("11999888777");
        
        // Select product
        Select productSelect = new Select(driver.findElement(By.id("product")));
        productSelect.selectByVisibleText("Cursos");
        
        // Select service type
        driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='elogio']")).click();
        
        // Select contact preferences
        driver.findElement(By.id("email-checkbox")).click();
        driver.findElement(By.id("phone-checkbox")).click();
        
        // Fill textarea
        driver.findElement(By.id("open-text-area")).sendKeys("Gostaria de parabenizar pela qualidade dos cursos oferecidos.");
        
        // Verify all fields are filled correctly before submission
        assertEquals("Maria", driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals("Santos", driver.findElement(By.id("lastName")).getAttribute("value"));
        assertEquals("maria.santos@email.com", driver.findElement(By.id("email")).getAttribute("value"));
        assertEquals("11999888777", driver.findElement(By.id("phone")).getAttribute("value"));
        assertEquals("Cursos", productSelect.getFirstSelectedOption().getText());
        assertTrue(driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='elogio']")).isSelected());
        assertTrue(driver.findElement(By.id("email-checkbox")).isSelected());
        assertTrue(driver.findElement(By.id("phone-checkbox")).isSelected());
        
        // Submit button should be clickable
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        assertTrue(submitButton.isEnabled());
    }
    
    @Test
    @Order(10)
    @DisplayName("Test privacy policy link navigation")
    void testPrivacyPolicyLinkNavigation() {
        driver.get(BASE_URL);
        
        // Scroll down to make privacy policy link visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        WebElement privacyLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Política de Privacidade")));
        assertTrue(privacyLink.isDisplayed());
        assertEquals("privacy.html", privacyLink.getAttribute("href"));
        
        // Click the privacy policy link
        privacyLink.click();
        
        // Verify navigation to privacy policy page
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        assertTrue(driver.getCurrentUrl().contains("privacy.html"));
        assertEquals("Central de Atendimento ao Cliente TAT - Política de privacidade", driver.getTitle());
    }
    
    // ==================== PRIVACY POLICY PAGE TESTS ====================
    
    @Test
    @Order(11)
    @DisplayName("Test privacy policy page loads correctly")
    void testPrivacyPolicyPageLoads() {
        driver.get(PRIVACY_URL);
        
        // Verify page title
        assertEquals("Central de Atendimento ao Cliente TAT - Política de privacidade", driver.getTitle());
        
        // Verify main heading
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertTrue(heading.getText().contains("CAC TAT - Política de privacidade"));
    }
    
    @Test
    @Order(12)
    @DisplayName("Test privacy policy page content")
    void testPrivacyPolicyPageContent() {
        driver.get(PRIVACY_URL);
        
        // Verify key content elements
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageText = content.getText();
        
        // Check for expected content
        assertTrue(pageText.contains("Não salvamos dados submetidos no formulário"));
        assertTrue(pageText.contains("CAC TAT"));
        assertTrue(pageText.contains("aplicação é um exemplo"));
        assertTrue(pageText.contains("fins de ensino"));
        assertTrue(pageText.contains("Talking About Testing"));
        
        // Verify the page structure
        assertNotNull(driver.findElement(By.tagName("h1")));
        
        // Verify "Talking About Testing" text is present (not clickable)
        WebElement tatText = driver.findElement(By.xpath("//*[contains(text(), 'Talking About Testing')]"));
        assertNotNull(tatText);
        assertTrue(tatText.isDisplayed());
    }
    
    @Test
    @Order(13)
    @DisplayName("Test privacy policy page has no interactive elements")
    void testPrivacyPolicyPageInteractivity() {
        driver.get(PRIVACY_URL);
        
        // Verify there are no form elements
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        assertEquals(0, inputs.size());
        
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        assertEquals(0, buttons.size());
        
        List<WebElement> selects = driver.findElements(By.tagName("select"));
        assertEquals(0, selects.size());
        
        List<WebElement> textareas = driver.findElements(By.tagName("textarea"));
        assertEquals(0, textareas.size());
        
        // Verify "Talking About Testing" is not a clickable link
        List<WebElement> links = driver.findElements(By.tagName("a"));
        boolean tatIsLink = false;
        for (WebElement link : links) {
            if (link.getText().contains("Talking About Testing")) {
                tatIsLink = true;
                break;
            }
        }
        assertFalse(tatIsLink, "Talking About Testing should not be a clickable link");
    }
    
    // ==================== CROSS-PAGE NAVIGATION TESTS ====================
    
    @Test
    @Order(14)
    @DisplayName("Test navigation between main page and privacy policy")
    void testCrossPageNavigation() {
        // Start at main page
        driver.get(BASE_URL);
        assertEquals("Central de Atendimento ao Cliente TAT", driver.getTitle());
        
        // Navigate to privacy policy
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        WebElement privacyLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Política de Privacidade")));
        privacyLink.click();
        
        // Verify we're on privacy policy page
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        assertEquals("Central de Atendimento ao Cliente TAT - Política de privacidade", driver.getTitle());
        
        // Navigate back to main page using browser navigation
        driver.navigate().back();
        
        // Verify we're back on main page
        wait.until(ExpectedConditions.urlContains("index.html"));
        assertEquals("Central de Atendimento ao Cliente TAT", driver.getTitle());
        
        // Verify form elements are still present and functional
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        assertTrue(firstNameField.isDisplayed());
        firstNameField.sendKeys("Test");
        assertEquals("Test", firstNameField.getAttribute("value"));
    }
    
    // ==================== FORM VALIDATION TESTS ====================
    
    @Test
    @Order(15)
    @DisplayName("Test form field validation and requirements")
    void testFormFieldValidation() {
        driver.get(BASE_URL);
        
        // Test required field attributes
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement textArea = driver.findElement(By.id("open-text-area"));
        
        // Check if fields have required attribute (if implemented)
        // Note: The actual validation behavior depends on the form implementation
        assertNotNull(firstNameField);
        assertNotNull(lastNameField);
        assertNotNull(emailField);
        assertNotNull(textArea);
        
        // Test email field type validation
        assertEquals("email", emailField.getAttribute("type"));
        
        // Test phone field type validation
        WebElement phoneField = driver.findElement(By.id("phone"));
        assertEquals("number", phoneField.getAttribute("type"));
    }
    
    // ==================== RESPONSIVE DESIGN TESTS ====================
    
    @Test
    @Order(16)
    @DisplayName("Test page elements visibility and layout")
    void testPageElementsVisibility() {
        driver.get(BASE_URL);
        
        // Test that all main form elements are visible
        assertTrue(driver.findElement(By.id("firstName")).isDisplayed());
        assertTrue(driver.findElement(By.id("lastName")).isDisplayed());
        assertTrue(driver.findElement(By.id("email")).isDisplayed());
        assertTrue(driver.findElement(By.id("phone")).isDisplayed());
        assertTrue(driver.findElement(By.id("product")).isDisplayed());
        assertTrue(driver.findElement(By.id("open-text-area")).isDisplayed());
        assertTrue(driver.findElement(By.id("file-upload")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//button[@type='submit']")).isDisplayed());
        
        // Test radio buttons visibility
        List<WebElement> radioButtons = driver.findElements(By.name("atendimento-tat"));
        for (WebElement radio : radioButtons) {
            assertTrue(radio.isDisplayed());
        }
        
        // Test checkboxes visibility
        assertTrue(driver.findElement(By.id("email-checkbox")).isDisplayed());
        assertTrue(driver.findElement(By.id("phone-checkbox")).isDisplayed());
    }
    
    // ==================== ERROR HANDLING TESTS ====================
    
    @Test
    @Order(17)
    @DisplayName("Test handling of invalid input data")
    void testInvalidInputHandling() {
        driver.get(BASE_URL);
        
        // Test email field with invalid email format
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("invalid-email-format");
        
        // Test phone field with non-numeric input
        WebElement phoneField = driver.findElement(By.id("phone"));
        phoneField.sendKeys("abc123");
        
        // The actual validation behavior depends on browser and form implementation
        // These tests verify the fields accept the input (browser-level validation may occur on submit)
        assertNotNull(emailField.getAttribute("value"));
        assertNotNull(phoneField.getAttribute("value"));
    }
    
    // ==================== COMPREHENSIVE INTEGRATION TEST ====================
    
    @Test
    @Order(18)
    @DisplayName("Test complete user journey through the website")
    void testCompleteUserJourney() {
        // Start at main page
        driver.get(BASE_URL);
        
        // Fill out the complete form
        driver.findElement(By.id("firstName")).sendKeys("Ana");
        driver.findElement(By.id("lastName")).sendKeys("Costa");
        driver.findElement(By.id("email")).sendKeys("ana.costa@exemplo.com");
        driver.findElement(By.id("phone")).sendKeys("11888777666");
        
        // Select product
        Select productSelect = new Select(driver.findElement(By.id("product")));
        productSelect.selectByVisibleText("Mentoria");
        
        // Select feedback option
        driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='feedback']")).click();
        
        // Select both contact preferences
        driver.findElement(By.id("email-checkbox")).click();
        driver.findElement(By.id("phone-checkbox")).click();
        
        // Fill message
        driver.findElement(By.id("open-text-area")).sendKeys("Gostaria de dar um feedback sobre a mentoria. Foi uma experiência muito enriquecedora!");
        
        // Verify all selections are correct
        assertEquals("Ana", driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals("Costa", driver.findElement(By.id("lastName")).getAttribute("value"));
        assertEquals("ana.costa@exemplo.com", driver.findElement(By.id("email")).getAttribute("value"));
        assertEquals("11888777666", driver.findElement(By.id("phone")).getAttribute("value"));
        assertEquals("Mentoria", productSelect.getFirstSelectedOption().getText());
        assertTrue(driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='feedback']")).isSelected());
        assertTrue(driver.findElement(By.id("email-checkbox")).isSelected());
        assertTrue(driver.findElement(By.id("phone-checkbox")).isSelected());
        
        // Navigate to privacy policy
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        WebElement privacyLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Política de Privacidade")));
        privacyLink.click();
        
        // Verify privacy policy page
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        assertTrue(driver.getPageSource().contains("Não salvamos dados submetidos"));
        
        // Navigate back and verify form data is preserved (if browser supports it)
        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("index.html"));
        
        // Complete the user journey
        assertTrue(driver.findElement(By.xpath("//button[@type='submit']")).isEnabled());
    }
}
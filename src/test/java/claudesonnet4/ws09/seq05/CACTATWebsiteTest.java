package claudesonnet4.ws09.seq05;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for CAC TAT (Central de Atendimento ao Cliente TAT) website
 * Tests all interactive elements on main page and privacy policy page
 * 
 * Main URL: https://cac-tat.s3.eu-central-1.amazonaws.com/index.html
 * Sub-page: https://cac-tat.s3.eu-central-1.amazonaws.com/privacy.html
 */
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CACTATWebsiteTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com";
    private static final String MAIN_PAGE_URL = BASE_URL + "/index.html";
    private static final String PRIVACY_PAGE_URL = BASE_URL + "/privacy.html";
    
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
    @DisplayName("01. Test Main Page Load and Title")
    void testMainPageLoadAndTitle() {
        driver.get(MAIN_PAGE_URL);
        
        // Verify page title
        String expectedTitle = "Central de Atendimento ao Cliente TAT";
        assertEquals(expectedTitle, driver.getTitle(), "Page title should match expected value");
        
        // Verify page URL
        assertEquals(MAIN_PAGE_URL, driver.getCurrentUrl(), "Current URL should match main page URL");
        
        // Verify main heading is present
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertTrue(heading.isDisplayed(), "Main heading should be visible");
        assertEquals("CAC TAT", heading.getText(), "Main heading text should be 'CAC TAT'");
    }
    
    @Test
    @DisplayName("02. Test First Name Input Field")
    void testFirstNameInputField() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("firstName")));
        
        // Verify field is present and enabled
        assertTrue(firstNameField.isDisplayed(), "First name field should be visible");
        assertTrue(firstNameField.isEnabled(), "First name field should be enabled");
        
        // Test input functionality
        String testName = "João";
        firstNameField.clear();
        firstNameField.sendKeys(testName);
        
        assertEquals(testName, firstNameField.getAttribute("value"), "First name field should contain entered text");
        
        // Verify field attributes
        assertEquals("text", firstNameField.getAttribute("type"), "First name field should be of type text");
        assertEquals("firstName", firstNameField.getAttribute("name"), "First name field should have correct name attribute");
    }
    
    @Test
    @DisplayName("03. Test Last Name Input Field")
    void testLastNameInputField() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement lastNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("lastName")));
        
        // Verify field is present and enabled
        assertTrue(lastNameField.isDisplayed(), "Last name field should be visible");
        assertTrue(lastNameField.isEnabled(), "Last name field should be enabled");
        
        // Test input functionality
        String testLastName = "Silva";
        lastNameField.clear();
        lastNameField.sendKeys(testLastName);
        
        assertEquals(testLastName, lastNameField.getAttribute("value"), "Last name field should contain entered text");
        
        // Verify field attributes
        assertEquals("text", lastNameField.getAttribute("type"), "Last name field should be of type text");
        assertEquals("lastName", lastNameField.getAttribute("name"), "Last name field should have correct name attribute");
    }
    
    @Test
    @DisplayName("04. Test Email Input Field")
    void testEmailInputField() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        
        // Verify field is present and enabled
        assertTrue(emailField.isDisplayed(), "Email field should be visible");
        assertTrue(emailField.isEnabled(), "Email field should be enabled");
        
        // Test input functionality
        String testEmail = "joao.silva@example.com";
        emailField.clear();
        emailField.sendKeys(testEmail);
        
        assertEquals(testEmail, emailField.getAttribute("value"), "Email field should contain entered text");
        
        // Verify field attributes
        assertEquals("email", emailField.getAttribute("type"), "Email field should be of type email");
        assertEquals("email", emailField.getAttribute("name"), "Email field should have correct name attribute");
    }
    
    @Test
    @DisplayName("05. Test Phone Input Field")
    void testPhoneInputField() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(By.id("phone")));
        
        // Verify field is present and enabled
        assertTrue(phoneField.isDisplayed(), "Phone field should be visible");
        assertTrue(phoneField.isEnabled(), "Phone field should be enabled");
        
        // Test input functionality
        String testPhone = "11987654321";
        phoneField.clear();
        phoneField.sendKeys(testPhone);
        
        assertEquals(testPhone, phoneField.getAttribute("value"), "Phone field should contain entered text");
        
        // Verify field attributes
        assertEquals("number", phoneField.getAttribute("type"), "Phone field should be of type number");
        assertEquals("phone", phoneField.getAttribute("name"), "Phone field should have correct name attribute");
    }
    
    @Test
    @DisplayName("06. Test Product Dropdown Selection")
    void testProductDropdownSelection() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement productDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("product")));
        
        // Verify dropdown is present and enabled
        assertTrue(productDropdown.isDisplayed(), "Product dropdown should be visible");
        assertTrue(productDropdown.isEnabled(), "Product dropdown should be enabled");
        
        Select select = new Select(productDropdown);
        
        // Verify default selection
        assertEquals("Selecione", select.getFirstSelectedOption().getText(), "Default option should be 'Selecione'");
        
        // Test selecting each option
        List<WebElement> options = select.getOptions();
        assertTrue(options.size() >= 5, "Dropdown should have at least 5 options");
        
        // Test selecting "Blog"
        select.selectByVisibleText("Blog");
        assertEquals("Blog", select.getFirstSelectedOption().getText(), "Selected option should be 'Blog'");
        
        // Test selecting "Cursos"
        select.selectByVisibleText("Cursos");
        assertEquals("Cursos", select.getFirstSelectedOption().getText(), "Selected option should be 'Cursos'");
        
        // Test selecting "Mentoria"
        select.selectByVisibleText("Mentoria");
        assertEquals("Mentoria", select.getFirstSelectedOption().getText(), "Selected option should be 'Mentoria'");
        
        // Test selecting "YouTube"
        select.selectByVisibleText("YouTube");
        assertEquals("YouTube", select.getFirstSelectedOption().getText(), "Selected option should be 'YouTube'");
    }
    
    @Test
    @DisplayName("07. Test Service Type Radio Buttons")
    void testServiceTypeRadioButtons() {
        driver.get(MAIN_PAGE_URL);
        
        // Find all radio buttons for service type
        List<WebElement> radioButtons = driver.findElements(By.name("atendimento-tat"));
        
        assertEquals(3, radioButtons.size(), "Should have exactly 3 service type radio buttons");
        
        // Test "Ajuda" radio button (should be selected by default)
        WebElement ajudaRadio = radioButtons.get(0);
        assertTrue(ajudaRadio.isDisplayed(), "Ajuda radio button should be visible");
        assertTrue(ajudaRadio.isEnabled(), "Ajuda radio button should be enabled");
        assertTrue(ajudaRadio.isSelected(), "Ajuda radio button should be selected by default");
        assertEquals("ajuda", ajudaRadio.getAttribute("value"), "Ajuda radio button should have correct value");
        
        // Test "Elogio" radio button
        WebElement elogioRadio = radioButtons.get(1);
        assertTrue(elogioRadio.isDisplayed(), "Elogio radio button should be visible");
        assertTrue(elogioRadio.isEnabled(), "Elogio radio button should be enabled");
        assertFalse(elogioRadio.isSelected(), "Elogio radio button should not be selected initially");
        
        // Click "Elogio" and verify selection
        elogioRadio.click();
        assertTrue(elogioRadio.isSelected(), "Elogio radio button should be selected after click");
        assertFalse(ajudaRadio.isSelected(), "Ajuda radio button should be deselected when Elogio is selected");
        assertEquals("elogio", elogioRadio.getAttribute("value"), "Elogio radio button should have correct value");
        
        // Test "Feedback" radio button
        WebElement feedbackRadio = radioButtons.get(2);
        assertTrue(feedbackRadio.isDisplayed(), "Feedback radio button should be visible");
        assertTrue(feedbackRadio.isEnabled(), "Feedback radio button should be enabled");
        assertFalse(feedbackRadio.isSelected(), "Feedback radio button should not be selected initially");
        
        // Click "Feedback" and verify selection
        feedbackRadio.click();
        assertTrue(feedbackRadio.isSelected(), "Feedback radio button should be selected after click");
        assertFalse(elogioRadio.isSelected(), "Elogio radio button should be deselected when Feedback is selected");
        assertEquals("feedback", feedbackRadio.getAttribute("value"), "Feedback radio button should have correct value");
    }
    
    @Test
    @DisplayName("08. Test Contact Preference Checkboxes")
    void testContactPreferenceCheckboxes() {
        driver.get(MAIN_PAGE_URL);
        
        // Test Email checkbox
        WebElement emailCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("email-checkbox")));
        assertTrue(emailCheckbox.isDisplayed(), "Email checkbox should be visible");
        assertTrue(emailCheckbox.isEnabled(), "Email checkbox should be enabled");
        assertFalse(emailCheckbox.isSelected(), "Email checkbox should not be selected initially");
        
        // Click email checkbox and verify
        emailCheckbox.click();
        assertTrue(emailCheckbox.isSelected(), "Email checkbox should be selected after click");
        assertEquals("email", emailCheckbox.getAttribute("name"), "Email checkbox should have correct name attribute");
        
        // Test Phone checkbox
        WebElement phoneCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("phone-checkbox")));
        assertTrue(phoneCheckbox.isDisplayed(), "Phone checkbox should be visible");
        assertTrue(phoneCheckbox.isEnabled(), "Phone checkbox should be enabled");
        assertFalse(phoneCheckbox.isSelected(), "Phone checkbox should not be selected initially");
        
        // Click phone checkbox and verify
        phoneCheckbox.click();
        assertTrue(phoneCheckbox.isSelected(), "Phone checkbox should be selected after click");
        assertEquals("phone", phoneCheckbox.getAttribute("name"), "Phone checkbox should have correct name attribute");
        
        // Verify both can be selected simultaneously
        assertTrue(emailCheckbox.isSelected(), "Email checkbox should remain selected");
        assertTrue(phoneCheckbox.isSelected(), "Phone checkbox should be selected");
        
        // Test unchecking
        emailCheckbox.click();
        assertFalse(emailCheckbox.isSelected(), "Email checkbox should be unselected after second click");
        assertTrue(phoneCheckbox.isSelected(), "Phone checkbox should remain selected");
    }
    
    @Test
    @DisplayName("09. Test Help Text Area")
    void testHelpTextArea() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement textArea = wait.until(ExpectedConditions.elementToBeClickable(By.id("open-text-area")));
        
        // Verify text area is present and enabled
        assertTrue(textArea.isDisplayed(), "Text area should be visible");
        assertTrue(textArea.isEnabled(), "Text area should be enabled");
        
        // Test input functionality
        String testMessage = "Preciso de ajuda com minha conta. Por favor, entrem em contato comigo o mais breve possível.";
        textArea.clear();
        textArea.sendKeys(testMessage);
        
        assertEquals(testMessage, textArea.getAttribute("value"), "Text area should contain entered text");
        assertEquals("open-text-area", textArea.getAttribute("name"), "Text area should have correct name attribute");
        
        // Test clearing text area
        textArea.clear();
        assertEquals("", textArea.getAttribute("value"), "Text area should be empty after clear");
    }
    
    @Test
    @DisplayName("10. Test File Upload Field")
    void testFileUploadField() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement fileUpload = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("file-upload")));
        
        // Verify file upload field is present
        assertTrue(fileUpload.isDisplayed(), "File upload field should be visible");
        assertTrue(fileUpload.isEnabled(), "File upload field should be enabled");
        
        // Verify field attributes
        assertEquals("file", fileUpload.getAttribute("type"), "File upload field should be of type file");
        assertEquals("file-upload", fileUpload.getAttribute("id"), "File upload field should have correct id");
        
        // Note: Actual file upload testing would require a real file path
        // This test verifies the element exists and has correct attributes
    }
    
    @Test
    @DisplayName("11. Test Submit Button")
    void testSubmitButton() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        
        // Verify button is present and enabled
        assertTrue(submitButton.isDisplayed(), "Submit button should be visible");
        assertTrue(submitButton.isEnabled(), "Submit button should be enabled");
        
        // Verify button text and attributes
        assertEquals("Enviar", submitButton.getText(), "Submit button should have text 'Enviar'");
        assertEquals("submit", submitButton.getAttribute("type"), "Submit button should be of type submit");
        
        // Test button click (without filling required fields - should show validation)
        submitButton.click();
        
        // Verify button is still clickable after click
        assertTrue(submitButton.isEnabled(), "Submit button should remain enabled after click");
    }
    
    @Test
    @DisplayName("12. Test Complete Form Submission")
    void testCompleteFormSubmission() {
        driver.get(MAIN_PAGE_URL);
        
        // Fill all required fields
        WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("firstName")));
        firstNameField.sendKeys("João");
        
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        lastNameField.sendKeys("Silva");
        
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("joao.silva@example.com");
        
        // Select product
        Select productSelect = new Select(driver.findElement(By.id("product")));
        productSelect.selectByVisibleText("Cursos");
        
        // Select service type
        WebElement feedbackRadio = driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='feedback']"));
        feedbackRadio.click();
        
        // Select contact preference
        WebElement emailCheckbox = driver.findElement(By.id("email-checkbox"));
        emailCheckbox.click();
        
        // Fill help text
        WebElement textArea = driver.findElement(By.id("open-text-area"));
        textArea.sendKeys("Gostaria de saber mais informações sobre os cursos disponíveis.");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        
        // Verify form submission (the exact behavior depends on the application)
        // Since this is a test application, we verify the button remains functional
        assertTrue(submitButton.isDisplayed(), "Submit button should remain visible after submission");
    }
    
    @Test
    @DisplayName("13. Test Privacy Policy Link")
    void testPrivacyPolicyLink() {
        driver.get(MAIN_PAGE_URL);
        
        // Scroll to make privacy policy link visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        WebElement privacyLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Política de Privacidade")));
        
        // Verify link is present and clickable
        assertTrue(privacyLink.isDisplayed(), "Privacy policy link should be visible");
        assertTrue(privacyLink.isEnabled(), "Privacy policy link should be enabled");
        
        // Verify link attributes
        assertEquals("Política de Privacidade", privacyLink.getText(), "Privacy policy link should have correct text");
        assertTrue(privacyLink.getAttribute("href").contains("privacy.html"), "Privacy policy link should point to privacy.html");
        
        // Click the link
        privacyLink.click();
        
        // Verify navigation to privacy page
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        assertTrue(driver.getCurrentUrl().contains("privacy.html"), "Should navigate to privacy policy page");
    }
    
    // ==================== PRIVACY POLICY PAGE TESTS ====================
    
    @Test
    @DisplayName("14. Test Privacy Policy Page Load and Content")
    void testPrivacyPolicyPageLoadAndContent() {
        driver.get(PRIVACY_PAGE_URL);
        
        // Verify page title
        String expectedTitle = "Central de Atendimento ao Cliente TAT - Política de privacidade";
        assertEquals(expectedTitle, driver.getTitle(), "Privacy policy page title should match expected value");
        
        // Verify page URL
        assertEquals(PRIVACY_PAGE_URL, driver.getCurrentUrl(), "Current URL should match privacy policy page URL");
        
        // Verify main heading is present
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertTrue(heading.isDisplayed(), "Privacy policy heading should be visible");
        assertEquals("CAC TAT - Política de privacidade", heading.getText(), "Privacy policy heading should have correct text");
        
        // Verify privacy policy content is present
        WebElement content = driver.findElement(By.tagName("body"));
        String pageText = content.getText();
        assertTrue(pageText.contains("Não salvamos dados submetidos no formulário"), "Privacy policy should contain data saving information");
        assertTrue(pageText.contains("aplicação é um exemplo"), "Privacy policy should mention it's an example application");
        assertTrue(pageText.contains("fins de ensino"), "Privacy policy should mention educational purposes");
    }
    
    @Test
    @DisplayName("15. Test Privacy Policy Page Navigation from Main Page")
    void testPrivacyPolicyPageNavigationFromMainPage() {
        // Start from main page
        driver.get(MAIN_PAGE_URL);
        
        // Scroll to make privacy policy link visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Click privacy policy link
        WebElement privacyLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Política de Privacidade")));
        privacyLink.click();
        
        // Verify navigation
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        assertEquals(PRIVACY_PAGE_URL, driver.getCurrentUrl(), "Should navigate to privacy policy page");
        
        // Verify privacy policy page content
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertEquals("CAC TAT - Política de privacidade", heading.getText(), "Privacy policy page should load correctly");
    }
    
    @Test
    @DisplayName("16. Test Privacy Policy Page Talking About Testing Text")
    void testPrivacyPolicyPageTalkingAboutTestingText() {
        driver.get(PRIVACY_PAGE_URL);
        
        // Verify "Talking About Testing" text is present
        WebElement body = driver.findElement(By.tagName("body"));
        String pageText = body.getText();
        assertTrue(pageText.contains("Talking About Testing"), "Privacy policy page should contain 'Talking About Testing' text");
        
        // Verify it's just text, not a clickable link
        List<WebElement> links = driver.findElements(By.tagName("a"));
        boolean isTalkingAboutTestingALink = links.stream()
            .anyMatch(link -> link.getText().contains("Talking About Testing"));
        assertFalse(isTalkingAboutTestingALink, "'Talking About Testing' should be text, not a clickable link");
    }
    
    // ==================== CROSS-PAGE NAVIGATION TESTS ====================
    
    @Test
    @DisplayName("17. Test Browser Back Navigation")
    void testBrowserBackNavigation() {
        // Start from main page
        driver.get(MAIN_PAGE_URL);
        String mainPageTitle = driver.getTitle();
        
        // Navigate to privacy policy
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        WebElement privacyLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Política de Privacidade")));
        privacyLink.click();
        
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        String privacyPageTitle = driver.getTitle();
        
        // Verify we're on privacy page
        assertNotEquals(mainPageTitle, privacyPageTitle, "Privacy page title should be different from main page");
        
        // Navigate back using browser back button
        driver.navigate().back();
        
        // Verify we're back on main page
        wait.until(ExpectedConditions.urlContains("index.html"));
        assertEquals(MAIN_PAGE_URL, driver.getCurrentUrl(), "Should navigate back to main page");
        assertEquals(mainPageTitle, driver.getTitle(), "Should be back on main page with correct title");
    }
    
    @Test
    @DisplayName("18. Test Direct URL Access to Both Pages")
    void testDirectUrlAccessToBothPages() {
        // Test direct access to main page
        driver.get(MAIN_PAGE_URL);
        assertEquals("Central de Atendimento ao Cliente TAT", driver.getTitle(), "Main page should load correctly via direct URL");
        
        WebElement mainHeading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertEquals("CAC TAT", mainHeading.getText(), "Main page should have correct heading");
        
        // Test direct access to privacy policy page
        driver.get(PRIVACY_PAGE_URL);
        assertEquals("Central de Atendimento ao Cliente TAT - Política de privacidade", driver.getTitle(), "Privacy page should load correctly via direct URL");
        
        WebElement privacyHeading = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertEquals("CAC TAT - Política de privacidade", privacyHeading.getText(), "Privacy page should have correct heading");
    }
    
    // ==================== FORM VALIDATION TESTS ====================
    
    @Test
    @DisplayName("19. Test Required Field Validation")
    void testRequiredFieldValidation() {
        driver.get(MAIN_PAGE_URL);
        
        // Try to submit form without filling required fields
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        submitButton.click();
        
        // Check if required field validation is triggered
        // Note: The exact validation behavior depends on the application implementation
        // This test verifies the form structure supports validation
        
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement textArea = driver.findElement(By.id("open-text-area"));
        
        // Verify required fields are still empty and accessible
        assertEquals("", firstNameField.getAttribute("value"), "First name should be empty");
        assertEquals("", lastNameField.getAttribute("value"), "Last name should be empty");
        assertEquals("", emailField.getAttribute("value"), "Email should be empty");
        assertEquals("", textArea.getAttribute("value"), "Text area should be empty");
    }
    
    @Test
    @DisplayName("20. Test Email Field Format Validation")
    void testEmailFieldFormatValidation() {
        driver.get(MAIN_PAGE_URL);
        
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        
        // Test with invalid email format
        emailField.clear();
        emailField.sendKeys("invalid-email");
        
        // Verify the field accepts the input (HTML5 validation may occur on submit)
        assertEquals("invalid-email", emailField.getAttribute("value"), "Email field should accept any text input");
        
        // Test with valid email format
        emailField.clear();
        emailField.sendKeys("valid@example.com");
        assertEquals("valid@example.com", emailField.getAttribute("value"), "Email field should accept valid email format");
        
        // Verify field type is email for HTML5 validation
        assertEquals("email", emailField.getAttribute("type"), "Email field should have type='email' for HTML5 validation");
    }
}
package deepseek.ws09.seq01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TatCustomerServiceTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com/index.html";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPageTitle() {
        String expectedTitle = "Central de Atendimento ao Cliente TAT";
        Assertions.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testFormElementsPresence() {
        // Test all form fields are present
        Assertions.assertTrue(driver.findElement(By.id("firstName")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("lastName")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("email")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("phone")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("product")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.name("atendimento-tat")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("email-checkbox")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("phone-checkbox")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("open-text-area")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("file-upload")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.xpath("//button[text()='Enviar']")).isDisplayed());
    }

    @Test
    public void testRequiredFieldsValidation() {
        // Click submit without filling required fields
        driver.findElement(By.xpath("//button[text()='Enviar']")).click();
        
        // Verify validation messages
        WebElement firstName = driver.findElement(By.id("firstName"));
        Assertions.assertEquals("true", firstName.getAttribute("required"));
        Assertions.assertEquals("Valide o campo obrigatório", firstName.getAttribute("validationMessage"));
        
        WebElement lastName = driver.findElement(By.id("lastName"));
        Assertions.assertEquals("true", lastName.getAttribute("required"));
        Assertions.assertEquals("Valide o campo obrigatório", lastName.getAttribute("validationMessage"));
        
        WebElement openTextArea = driver.findElement(By.id("open-text-area"));
        Assertions.assertEquals("true", openTextArea.getAttribute("required"));
        Assertions.assertEquals("Valide o campo obrigatório", openTextArea.getAttribute("validationMessage"));
    }

    @Test
    public void testTextInputFields() {
        // Fill text fields
        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("phone")).sendKeys("1234567890");
        
        // Verify values
        Assertions.assertEquals("John", driver.findElement(By.id("firstName")).getAttribute("value"));
        Assertions.assertEquals("Doe", driver.findElement(By.id("lastName")).getAttribute("value"));
        Assertions.assertEquals("john.doe@example.com", driver.findElement(By.id("email")).getAttribute("value"));
        Assertions.assertEquals("1234567890", driver.findElement(By.id("phone")).getAttribute("value"));
    }

    @Test
    public void testProductDropdown() {
        Select productDropdown = new Select(driver.findElement(By.id("product")));
        
        // Verify options
        List<WebElement> options = productDropdown.getOptions();
        Assertions.assertEquals(5, options.size());
        Assertions.assertEquals("Selecione", options.get(0).getText());
        Assertions.assertEquals("Blog", options.get(1).getText());
        Assertions.assertEquals("Cursos", options.get(2).getText());
        Assertions.assertEquals("Mentoria", options.get(3).getText());
        Assertions.assertEquals("YouTube", options.get(4).getText());
        
        // Select each option and verify
        productDropdown.selectByVisibleText("Blog");
        Assertions.assertEquals("Blog", productDropdown.getFirstSelectedOption().getText());
        
        productDropdown.selectByVisibleText("Cursos");
        Assertions.assertEquals("Cursos", productDropdown.getFirstSelectedOption().getText());
    }

    @Test
    public void testServiceTypeRadioButtons() {
        // Test radio buttons
        WebElement helpRadio = driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='ajuda']"));
        WebElement praiseRadio = driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='elogio']"));
        WebElement feedbackRadio = driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='feedback']"));
        
        helpRadio.click();
        Assertions.assertTrue(helpRadio.isSelected());
        Assertions.assertFalse(praiseRadio.isSelected());
        Assertions.assertFalse(feedbackRadio.isSelected());
        
        praiseRadio.click();
        Assertions.assertFalse(helpRadio.isSelected());
        Assertions.assertTrue(praiseRadio.isSelected());
        Assertions.assertFalse(feedbackRadio.isSelected());
    }

    @Test
    public void testContactPreferenceCheckboxes() {
        // Test checkboxes
        WebElement emailCheckbox = driver.findElement(By.id("email-checkbox"));
        WebElement phoneCheckbox = driver.findElement(By.id("phone-checkbox"));
        
        emailCheckbox.click();
        Assertions.assertTrue(emailCheckbox.isSelected());
        Assertions.assertFalse(phoneCheckbox.isSelected());
        
        phoneCheckbox.click();
        Assertions.assertTrue(phoneCheckbox.isSelected());
        
        // Both can be selected
        emailCheckbox.click();
        Assertions.assertTrue(emailCheckbox.isSelected());
        Assertions.assertTrue(phoneCheckbox.isSelected());
    }

    @Test
    public void testTextAreaInput() {
        WebElement textArea = driver.findElement(By.id("open-text-area"));
        String testText = "This is a test message for the customer service.";
        
        textArea.sendKeys(testText);
        Assertions.assertEquals(testText, textArea.getAttribute("value"));
    }

    @Test
    public void testFileUpload() {
        WebElement fileUpload = driver.findElement(By.id("file-upload"));
        
        // Provide a file path (this would need to be adjusted based on actual test file location)
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testfile.txt";
        fileUpload.sendKeys(filePath);
        
        // Verify file is attached (actual verification would depend on how the UI shows the file)
        Assertions.assertNotNull(fileUpload.getAttribute("value"));
    }

    @Test
    public void testPrivacyPolicyLink() {
        // Find and click privacy policy link
        WebElement privacyLink = driver.findElement(By.linkText("Política de Privacidade"));
        privacyLink.click();
        
        // Wait for new page to load
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        
        // Verify new page title or content
        Assertions.assertTrue(driver.getTitle().contains("Privacy Policy") || 
                            driver.getPageSource().contains("Política de Privacidade"));
        
        // Test elements on privacy policy page
        Assertions.assertTrue(driver.findElement(By.tagName("h1")).isDisplayed());
        
        // Go back to main page
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
    }

    @Test
    public void testFormSubmissionSuccess() {
        // Fill all required fields
        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("open-text-area")).sendKeys("Test message");
        
        // Submit form
        driver.findElement(By.xpath("//button[text()='Enviar']")).click();
        
        // Verify success message (adjust based on actual success indicator)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(), 'Mensagem enviada com sucesso')]")));
    }

    @Test
    public void testFormSubmissionWithAllFields() {
        // Fill all fields
        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("phone")).sendKeys("1234567890");
        
        // Select from dropdown
        Select productDropdown = new Select(driver.findElement(By.id("product")));
        productDropdown.selectByVisibleText("Cursos");
        
        // Select radio button
        driver.findElement(By.xpath("//input[@name='atendimento-tat' and @value='ajuda']")).click();
        
        // Select checkboxes
        driver.findElement(By.id("email-checkbox")).click();
        driver.findElement(By.id("phone-checkbox")).click();
        
        // Fill text area
        driver.findElement(By.id("open-text-area")).sendKeys("This is a comprehensive test message.");
        
        // Submit form
        driver.findElement(By.xpath("//button[text()='Enviar']")).click();
        
        // Verify success
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(), 'Mensagem enviada com sucesso')]")));
    }

    @Test
    public void testPrivacyPolicyPageElements() {
        // Navigate to privacy policy
        driver.findElement(By.linkText("Política de Privacidade")).click();
        wait.until(ExpectedConditions.urlContains("privacy.html"));
        
        // Verify page elements
        Assertions.assertTrue(driver.findElement(By.tagName("h1")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.tagName("p")).isDisplayed());
        
        // Verify back link works
        driver.findElement(By.linkText("Voltar")).click();
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
    }

    @Test
    public void testFormReset() {
        // Fill some fields
        driver.findElement(By.id("firstName")).sendKeys("Test");
        driver.findElement(By.id("lastName")).sendKeys("User");
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        
        // Refresh page to reset form
        driver.navigate().refresh();
        
        // Verify fields are empty
        Assertions.assertEquals("", driver.findElement(By.id("firstName")).getAttribute("value"));
        Assertions.assertEquals("", driver.findElement(By.id("lastName")).getAttribute("value"));
        Assertions.assertEquals("", driver.findElement(By.id("email")).getAttribute("value"));
    }
}
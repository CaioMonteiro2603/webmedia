package deepseek.ws09.seq02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class CACTATTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com/index.html";
    private static final String PRIVACY_URL = "https://cac-tat.s3.eu-central-1.amazonaws.com/privacy.html";

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
    public void testFormElementsPresence() {
        // Verify all form elements are present
        Assertions.assertTrue(driver.findElement(By.id("firstName")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("lastName")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("email")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("phone")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("product")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector("input[name='atendimento-tat'][value='ajuda']")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector("input[name='atendimento-tat'][value='elogio']")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector("input[name='atendimento-tat'][value='feedback']")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("email-checkbox")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("phone-checkbox")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("open-text-area")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.id("file-upload")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector("button[type='submit']")).isDisplayed());
    }

    @Test
    public void testFormSubmissionWithValidData() {
        // Fill in form fields
        driver.findElement(By.id("firstName")).sendKeys("Test");
        driver.findElement(By.id("lastName")).sendKeys("User");
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("phone")).sendKeys("11999999999");
        
        // Select from dropdown
        Select productSelect = new Select(driver.findElement(By.id("product")));
        productSelect.selectByVisibleText("Cursos");
        
        // Select radio button
        driver.findElement(By.cssSelector("input[name='atendimento-tat'][value='ajuda']")).click();
        
        // Select checkboxes
        driver.findElement(By.id("email-checkbox")).click();
        driver.findElement(By.id("phone-checkbox")).click();
        
        // Fill text area
        driver.findElement(By.id("open-text-area")).sendKeys("This is a test message");
        
        // Submit form
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Verify success message
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success")));
        Assertions.assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testRequiredFieldValidation() {
        // Try to submit without filling required fields
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Verify validation messages
        Assertions.assertEquals("É obrigatório", driver.findElement(By.id("firstName")).getAttribute("validationMessage"));
        Assertions.assertEquals("É obrigatório", driver.findElement(By.id("lastName")).getAttribute("validationMessage"));
        Assertions.assertEquals("É obrigatório", driver.findElement(By.id("open-text-area")).getAttribute("validationMessage"));
    }

    @Test
    public void testRadioButtonSelection() {
        // Test all radio button options
        WebElement ajudaRadio = driver.findElement(By.cssSelector("input[name='atendimento-tat'][value='ajuda']"));
        WebElement elogioRadio = driver.findElement(By.cssSelector("input[name='atendimento-tat'][value='elogio']"));
        WebElement feedbackRadio = driver.findElement(By.cssSelector("input[name='atendimento-tat'][value='feedback']"));
        
        ajudaRadio.click();
        Assertions.assertTrue(ajudaRadio.isSelected());
        Assertions.assertFalse(elogioRadio.isSelected());
        Assertions.assertFalse(feedbackRadio.isSelected());
        
        elogioRadio.click();
        Assertions.assertFalse(ajudaRadio.isSelected());
        Assertions.assertTrue(elogioRadio.isSelected());
        Assertions.assertFalse(feedbackRadio.isSelected());
        
        feedbackRadio.click();
        Assertions.assertFalse(ajudaRadio.isSelected());
        Assertions.assertFalse(elogioRadio.isSelected());
        Assertions.assertTrue(feedbackRadio.isSelected());
    }

    @Test
    public void testCheckboxSelection() {
        // Test checkbox functionality
        WebElement emailCheckbox = driver.findElement(By.id("email-checkbox"));
        WebElement phoneCheckbox = driver.findElement(By.id("phone-checkbox"));
        
        emailCheckbox.click();
        Assertions.assertTrue(emailCheckbox.isSelected());
        Assertions.assertFalse(phoneCheckbox.isSelected());
        
        phoneCheckbox.click();
        Assertions.assertTrue(emailCheckbox.isSelected());
        Assertions.assertTrue(phoneCheckbox.isSelected());
        
        emailCheckbox.click();
        Assertions.assertFalse(emailCheckbox.isSelected());
        Assertions.assertTrue(phoneCheckbox.isSelected());
    }

    @Test
    public void testFileUpload() throws Exception {
        // Create a temporary file for testing upload
        File testFile = File.createTempFile("test", ".txt");
        testFile.deleteOnExit();
        
        // Upload the file
        WebElement fileInput = driver.findElement(By.id("file-upload"));
        fileInput.sendKeys(testFile.getAbsolutePath());
        
        // Verify file was selected
        String fileName = fileInput.getAttribute("value").replace("C:\\fakepath\\", "");
        Assertions.assertEquals(testFile.getName(), fileName);
    }

    @Test
    public void testNavigationToPrivacyPolicy() {
        // Find and click privacy policy link
        WebElement privacyLink = driver.findElement(By.linkText("Política de Privacidade"));
        privacyLink.click();
        
        // Verify navigation to privacy page
        wait.until(ExpectedConditions.urlToBe(PRIVACY_URL));
        Assertions.assertEquals(PRIVACY_URL, driver.getCurrentUrl());
        
        // Verify privacy page content
        WebElement privacyTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        Assertions.assertEquals("Política de privacidade", privacyTitle.getText());
    }

    @Test
    public void testProductDropdownOptions() {
        // Test all dropdown options
        Select productSelect = new Select(driver.findElement(By.id("product")));
        
        Assertions.assertEquals(4, productSelect.getOptions().size());
        Assertions.assertEquals("Selecione", productSelect.getOptions().get(0).getText());
        Assertions.assertEquals("Blog", productSelect.getOptions().get(1).getText());
        Assertions.assertEquals("Cursos", productSelect.getOptions().get(2).getText());
        Assertions.assertEquals("Mentoria", productSelect.getOptions().get(3).getText());
        
        // Test selection
        productSelect.selectByVisibleText("Cursos");
        Assertions.assertEquals("Cursos", productSelect.getFirstSelectedOption().getText());
    }
}
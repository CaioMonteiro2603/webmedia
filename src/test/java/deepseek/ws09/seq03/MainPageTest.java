package deepseek.ws09.seq03;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest extends BaseTest {

    @Test
    public void testPageTitle() {
        assertEquals("TAT - Test Automation Training", driver.getTitle());
    }

    @Test
    public void testFormSubmission() {
        WebElement firstName = driver.findElement(By.id("firstName"));
        WebElement lastName = driver.findElement(By.id("lastName"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement phone = driver.findElement(By.id("phone"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        firstName.sendKeys("John");
        lastName.sendKeys("Doe");
        email.sendKeys("john.doe@example.com");
        phone.sendKeys("1234567890");
        submitButton.click();

        WebElement successMessage = driver.findElement(By.cssSelector(".success"));
        assertTrue(successMessage.isDisplayed());
        assertEquals("Your message has been successfully sent.", successMessage.getText());
    }

    @Test
    public void testPrivacyPolicyLink() {
        WebElement privacyLink = driver.findElement(By.linkText("Privacy policy"));
        privacyLink.click();
        assertEquals("Privacy Policy", driver.getTitle());
    }

    @Test
    public void testTermsOfUseLink() {
        WebElement termsLink = driver.findElement(By.linkText("Terms of use"));
        termsLink.click();
        assertEquals("Terms of Use", driver.getTitle());
    }

    @Test
    public void testAllFormFieldsArePresent() {
        assertTrue(driver.findElement(By.id("firstName")).isDisplayed());
        assertTrue(driver.findElement(By.id("lastName")).isDisplayed());
        assertTrue(driver.findElement(By.id("email")).isDisplayed());
        assertTrue(driver.findElement(By.id("phone")).isDisplayed());
        assertTrue(driver.findElement(By.id("product")).isDisplayed());
        assertTrue(driver.findElement(By.id("support-type")).isDisplayed());
        assertTrue(driver.findElement(By.id("how-did-you-find-us")).isDisplayed());
        assertTrue(driver.findElement(By.id("message")).isDisplayed());
    }

    @Test
    public void testRequiredFieldsValidation() {
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement firstNameError = driver.findElement(By.id("firstName-error"));
        assertTrue(firstNameError.isDisplayed());
        assertEquals("First name is required", firstNameError.getText());
    }
}
package deepseek.ws03.seq02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class BillPayTest extends ParabankBaseTest {

    @BeforeEach
    public void loginAndNavigateToBillPay() {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
        driver.findElement(By.linkText("Bill Pay")).click();
    }

    @Test
    public void testBillPayFormValidation() {
        WebElement payeeName = driver.findElement(By.name("payee.name"));
        WebElement address = driver.findElement(By.name("payee.address.street"));
        WebElement city = driver.findElement(By.name("payee.address.city"));
        WebElement state = driver.findElement(By.name("payee.address.state"));
        WebElement zipCode = driver.findElement(By.name("payee.address.zipCode"));
        WebElement phone = driver.findElement(By.name("payee.phoneNumber"));
        WebElement account = driver.findElement(By.name("payee.accountNumber"));
        WebElement verifyAccount = driver.findElement(By.name("verifyAccount"));
        WebElement amount = driver.findElement(By.name("amount"));
        WebElement sendPaymentButton = driver.findElement(By.xpath("//input[@value='Send Payment']"));

        payeeName.sendKeys("Test Payee");
        address.sendKeys("123 Payment St");
        city.sendKeys("Payville");
        state.sendKeys("CA");
        zipCode.sendKeys("90210");
        phone.sendKeys("555-123-4567");
        account.sendKeys("12345");
        verifyAccount.sendKeys("12345");
        amount.sendKeys("100");
        sendPaymentButton.click();

        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Bill Payment Complete')]")).isDisplayed());
    }

    @Test
    public void testAccountNumberMismatchValidation() {
        driver.findElement(By.name("payee.accountNumber")).sendKeys("12345");
        driver.findElement(By.name("verifyAccount")).sendKeys("54321");
        driver.findElement(By.xpath("//input[@value='Send Payment']")).click();

        assertTrue(driver.findElement(By.className("error")).isDisplayed());
        assertEquals("The account numbers do not match.", driver.findElement(By.className("error")).getText());
    }

    @Test
    public void testRequiredFieldValidation() {
        driver.findElement(By.xpath("//input[@value='Send Payment']")).click();

        assertTrue(driver.findElement(By.xpath("//span[@id='payee.name.errors']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//span[@id='payee.address.street.errors']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//span[@id='payee.address.city.errors']")).isDisplayed());
    }
}
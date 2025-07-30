package deepseek.ws03.seq03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServicesTest extends BaseTest {
    
    @BeforeEach
    public void login() {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
    }

    @Test
    public void testAccountOverview() {
        driver.findElement(By.linkText("Accounts Overview")).click();
        assertEquals("ParaBank | Accounts Overview", driver.getTitle());
        
        WebElement accountsTable = driver.findElement(By.id("accountTable"));
        assertTrue(accountsTable.isDisplayed());
    }

    @Test
    public void testTransferFunds() {
        driver.findElement(By.linkText("Transfer Funds")).click();
        assertEquals("ParaBank | Transfer Funds", driver.getTitle());
        
        Select fromAccount = new Select(driver.findElement(By.id("fromAccountId")));
        Select toAccount = new Select(driver.findElement(By.id("toAccountId")));
        WebElement amountField = driver.findElement(By.id("amount"));
        WebElement transferButton = driver.findElement(By.xpath("//input[@value='Transfer']"));
        
        assertTrue(fromAccount.getOptions().size() > 0);
        assertTrue(toAccount.getOptions().size() > 0);
        assertTrue(amountField.isDisplayed());
        assertTrue(transferButton.isEnabled());
        
        // Perform a transfer
        fromAccount.selectByIndex(0);
        toAccount.selectByIndex(1);
        amountField.sendKeys("100");
        transferButton.click();
        
        WebElement transferComplete = driver.findElement(By.xpath("//h1[contains(text(), 'Transfer Complete!')]"));
        assertTrue(transferComplete.isDisplayed());
    }

    @Test
    public void testBillPay() {
        driver.findElement(By.linkText("Bill Pay")).click();
        assertEquals("ParaBank | Bill Pay", driver.getTitle());
        
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
        
        // Fill out the form
        payeeName.sendKeys("Test Payee");
        address.sendKeys("123 Test St");
        city.sendKeys("Test City");
        state.sendKeys("CA");
        zipCode.sendKeys("90210");
        phone.sendKeys("555-123-4567");
        account.sendKeys("12345");
        verifyAccount.sendKeys("12345");
        amount.sendKeys("50");
        sendPaymentButton.click();
        
        WebElement paymentComplete = driver.findElement(By.xpath("//h1[contains(text(), 'Bill Payment Complete')]"));
        assertTrue(paymentComplete.isDisplayed());
    }

    @Test
    public void testAccountActivity() {
        driver.findElement(By.linkText("Accounts Overview")).click();
        WebElement accountLink = driver.findElement(By.xpath("//a[contains(@href, 'activity.htm')]"));
        accountLink.click();
        
        assertEquals("ParaBank | Account Activity", driver.getTitle());
        WebElement transactionsTable = driver.findElement(By.id("transactionTable"));
        assertTrue(transactionsTable.isDisplayed());
    }
}
package deepseek.ws03.seq05;
import org.openqa.selenium.support.ui.Select;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        WebElement accountsTable = driver.findElement(By.id("accountTable"));
        assertTrue(accountsTable.isDisplayed());
    }

    @Test
    public void testTransferFunds() {
        driver.findElement(By.linkText("Transfer Funds")).click();
        
        // Fill out transfer form
        WebElement amountField = driver.findElement(By.id("amount"));
        amountField.sendKeys("100");
        
        Select fromAccount = new Select(driver.findElement(By.id("fromAccountId")));
        fromAccount.selectByIndex(0);
        
        Select toAccount = new Select(driver.findElement(By.id("toAccountId")));
        toAccount.selectByIndex(1);
        
        driver.findElement(By.xpath("//input[@value='Transfer']")).click();
        
        // Verify transfer success
        WebElement successMessage = driver.findElement(By.xpath("//h1[contains(text(), 'Transfer Complete!')]"));
        assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testBillPay() {
        driver.findElement(By.linkText("Bill Pay")).click();
        
        // Fill out bill payment form
        driver.findElement(By.name("payee.name")).sendKeys("Test Payee");
        driver.findElement(By.name("payee.address.street")).sendKeys("123 Bill St");
        driver.findElement(By.name("payee.address.city")).sendKeys("Bill City");
        driver.findElement(By.name("payee.address.state")).sendKeys("CA");
        driver.findElement(By.name("payee.address.zipCode")).sendKeys("90210");
        driver.findElement(By.name("payee.phoneNumber")).sendKeys("555-987-6543");
        driver.findElement(By.name("payee.accountNumber")).sendKeys("12345");
        driver.findElement(By.name("verifyAccount")).sendKeys("12345");
        driver.findElement(By.name("amount")).sendKeys("50");
        
        Select fromAccount = new Select(driver.findElement(By.name("fromAccountId")));
        fromAccount.selectByIndex(0);
        
        driver.findElement(By.xpath("//input[@value='Send Payment']")).click();
        
        // Verify payment success
        WebElement successMessage = driver.findElement(By.xpath("//h1[contains(text(), 'Bill Payment Complete')]"));
        assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void testFindTransactions() {
        driver.findElement(By.linkText("Find Transactions")).click();
        
        // Search by amount
        driver.findElement(By.id("criteria.amount")).sendKeys("100");
        driver.findElement(By.xpath("//button[contains(text(), 'Find Transactions')]")).click();
        
        // Verify results
        WebElement resultsTable = driver.findElement(By.id("transactionTable"));
        assertTrue(resultsTable.isDisplayed());
    }

    @Test
    public void testUpdateContactInfo() {
        driver.findElement(By.linkText("Update Contact Info")).click();
        
        // Update phone number
        WebElement phoneField = driver.findElement(By.id("customer.phoneNumber"));
        phoneField.clear();
        phoneField.sendKeys("555-555-5555");
        
        driver.findElement(By.xpath("//input[@value='Update Profile']")).click();
        
        // Verify update success
        WebElement successMessage = driver.findElement(By.xpath("//h1[contains(text(), 'Profile Updated')]"));
        assertTrue(successMessage.isDisplayed());
    }
}
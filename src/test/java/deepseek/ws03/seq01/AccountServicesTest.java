package deepseek.ws03.seq01;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import static org.junit.jupiter.api.Assertions.*;

public class AccountServicesTest extends BaseTest {

    @Test
    public void testOpenNewAccount() {
        // Login first
        driver.get(BASE_URL);
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[value='Log In']")).click();

        // Navigate to Open New Account
        WebElement openNewAccount = driver.findElement(By.linkText("Open New Account"));
        openNewAccount.click();
        assertEquals("ParaBank | Open Account", driver.getTitle());

        // Select account type and existing account
        Select typeSelect = new Select(driver.findElement(By.id("type")));
        typeSelect.selectByVisibleText("SAVINGS");

        Select fromAccountSelect = new Select(driver.findElement(By.id("fromAccountId")));
        fromAccountSelect.selectByIndex(0);

        // Open account
        WebElement openAccountButton = driver.findElement(By.cssSelector("input[value='Open New Account']"));
        openAccountButton.click();

        // Verify account opened
        assertTrue(driver.findElement(By.id("newAccountId")).isDisplayed());
        assertTrue(driver.getPageSource().contains("Congratulations, your account is now open."));

        // Logout
        driver.findElement(By.linkText("Log Out")).click();
    }

    @Test
    public void testAccountsOverview() {
        // Login first
        driver.get(BASE_URL);
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[value='Log In']")).click();

        // Verify accounts overview
        assertTrue(driver.findElement(By.cssSelector("h1.title")).getText().contains("Accounts Overview"));
        assertTrue(driver.findElements(By.cssSelector("table#accountTable tbody tr")).size() > 0);

        // Click on account link
        WebElement accountLink = driver.findElement(By.cssSelector("table#accountTable tbody tr td a"));
        accountLink.click();
        assertTrue(driver.getTitle().contains("Account Activity"));

        // Logout
        driver.findElement(By.linkText("Log Out")).click();
    }

    @Test
    public void testTransferFunds() {
        // Login first
        driver.get(BASE_URL);
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[value='Log In']")).click();

        // Navigate to Transfer Funds
        WebElement transferFunds = driver.findElement(By.linkText("Transfer Funds"));
        transferFunds.click();
        assertEquals("ParaBank | Transfer Funds", driver.getTitle());

        // Get initial balances
        String fromAccountId = driver.findElement(By.id("fromAccountId")).getAttribute("value");
        String toAccountId = driver.findElement(By.id("toAccountId")).getAttribute("value");

        // Enter transfer amount
        WebElement amount = driver.findElement(By.id("amount"));
        amount.sendKeys("100");

        // Transfer funds
        WebElement transferButton = driver.findElement(By.cssSelector("input[value='Transfer']"));
        transferButton.click();

        // Verify transfer
        assertTrue(driver.getPageSource().contains("Transfer Complete!"));
        assertTrue(driver.getPageSource().contains("$100.00 has been transferred from account #" + fromAccountId + " to account #" + toAccountId));

        // Logout
        driver.findElement(By.linkText("Log Out")).click();
    }

    @Test
    public void testBillPay() {
        // Login first
        driver.get(BASE_URL);
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[value='Log In']")).click();

        // Navigate to Bill Pay
        WebElement billPay = driver.findElement(By.linkText("Bill Pay"));
        billPay.click();
        assertEquals("ParaBank | Bill Pay", driver.getTitle());

        // Fill bill payment form
        driver.findElement(By.name("payee.name")).sendKeys("Test Payee");
        driver.findElement(By.name("payee.address.street")).sendKeys("123 Test St");
        driver.findElement(By.name("payee.address.city")).sendKeys("Test City");
        driver.findElement(By.name("payee.address.state")).sendKeys("CA");
        driver.findElement(By.name("payee.address.zipCode")).sendKeys("90210");
        driver.findElement(By.name("payee.phoneNumber")).sendKeys("555-123-4567");
        driver.findElement(By.name("payee.accountNumber")).sendKeys("12345");
        driver.findElement(By.name("verifyAccount")).sendKeys("12345");
        driver.findElement(By.name("amount")).sendKeys("50");

        // Select from account
        Select fromAccountSelect = new Select(driver.findElement(By.name("fromAccountId")));
        fromAccountSelect.selectByIndex(0);

        // Submit payment
        WebElement sendPaymentButton = driver.findElement(By.cssSelector("input[value='Send Payment']"));
        sendPaymentButton.click();

        // Verify payment
        assertTrue(driver.getPageSource().contains("Bill Payment Complete"));
        assertTrue(driver.getPageSource().contains("$50.00"));

        // Logout
        driver.findElement(By.linkText("Log Out")).click();
    }
}
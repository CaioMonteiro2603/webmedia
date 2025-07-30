package deepseek.ws03.seq02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.*;

public class AccountOverviewTest extends ParabankBaseTest {

    @BeforeEach
    public void login() {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
    }

    @Test
    public void testAccountOverviewDisplay() {
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Accounts Overview')]")).isDisplayed());
        assertTrue(driver.findElement(By.id("accountTable")).isDisplayed());
    }

    @Test
    public void testAccountDetailsLink() {
        driver.findElement(By.xpath("//a[contains(text(),'Account')]")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Account Details')]")).isDisplayed());
    }

    @Test
    public void testTransferFundsLink() {
        driver.findElement(By.linkText("Transfer Funds")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Transfer Funds')]")).isDisplayed());
    }

    @Test
    public void testBillPayLink() {
        driver.findElement(By.linkText("Bill Pay")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Bill Pay Service')]")).isDisplayed());
    }

    @Test
    public void testFindTransactionsLink() {
        driver.findElement(By.linkText("Find Transactions")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Find Transactions')]")).isDisplayed());
    }

    @Test
    public void testUpdateContactInfoLink() {
        driver.findElement(By.linkText("Update Contact Info")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Update Profile')]")).isDisplayed());
    }
}
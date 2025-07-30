package deepseek.ws03.seq02;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest extends ParabankBaseTest {

    @Test
    public void testPageTitle() {
        assertEquals("ParaBank | Welcome | Online Banking", driver.getTitle());
    }

    @Test
    public void testLoginWithValidCredentials() {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
        
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Accounts Overview')]")).isDisplayed());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        driver.findElement(By.name("username")).sendKeys("invalid");
        driver.findElement(By.name("password")).sendKeys("invalid");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
        
        assertTrue(driver.findElement(By.xpath("//p[contains(text(),'The username and password could not be verified.')]")).isDisplayed());
    }

    @Test
    public void testRegisterLink() {
        driver.findElement(By.linkText("Register")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Signing up is easy!')]")).isDisplayed());
    }

    @Test
    public void testAboutUsLink() {
        driver.findElement(By.linkText("About Us")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'ParaSoft Demo Website')]")).isDisplayed());
    }

    @Test
    public void testServicesLink() {
        driver.findElement(By.linkText("Services")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Available Bookstore SOAP services')]")).isDisplayed());
    }

    @Test
    public void testAdminPageLink() {
        driver.findElement(By.linkText("Admin Page")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Administration')]")).isDisplayed());
    }
}
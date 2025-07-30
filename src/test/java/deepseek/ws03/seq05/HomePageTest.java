package deepseek.ws03.seq05;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest extends BaseTest {

    @Test
    public void testPageTitle() {
        String expectedTitle = "ParaBank | Welcome | Online Banking";
        assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testLoginFormExists() {
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));
        
        assertTrue(usernameField.isDisplayed());
        assertTrue(passwordField.isDisplayed());
        assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void testRegisterLink() {
        WebElement registerLink = driver.findElement(By.linkText("Register"));
        registerLink.click();
        assertEquals("ParaBank | Register for Free Online Account Access", driver.getTitle());
    }

    @Test
    public void testAboutUsLink() {
        WebElement aboutUsLink = driver.findElement(By.linkText("About Us"));
        aboutUsLink.click();
        assertEquals("ParaBank | About Us", driver.getTitle());
    }

    @Test
    public void testServicesLink() {
        WebElement servicesLink = driver.findElement(By.linkText("Services"));
        servicesLink.click();
        assertEquals("ParaBank | Services", driver.getTitle());
    }

    @Test
    public void testAdminPageLink() {
        WebElement adminPageLink = driver.findElement(By.linkText("Admin Page"));
        adminPageLink.click();
        assertEquals("ParaBank | Administration", driver.getTitle());
    }

    @Test
    public void testForgotLoginInfoLink() {
        WebElement forgotLoginLink = driver.findElement(By.linkText("Forgot login info?"));
        forgotLoginLink.click();
        assertEquals("ParaBank | Customer Lookup", driver.getTitle());
    }
}
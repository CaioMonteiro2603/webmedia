package deepseek.ws03.seq03;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest extends BaseTest {

    @Test
    public void testPageTitle() {
        assertEquals("ParaBank | Welcome | Online Banking", driver.getTitle());
    }

    @Test
    public void testHeaderElements() {
        WebElement header = driver.findElement(By.className("title"));
        assertTrue(header.getText().contains("Experience the difference"));
        
        WebElement logo = driver.findElement(By.xpath("//img[@title='ParaBank']"));
        assertTrue(logo.isDisplayed());
    }

    @Test
    public void testNavigationLinks() {
        String[] linkTexts = {"Home", "About Us", "Services", "Products", "Locations", "Admin Page"};
        
        for (String linkText : linkTexts) {
            WebElement link = driver.findElement(By.linkText(linkText));
            assertTrue(link.isDisplayed());
            assertTrue(link.isEnabled());
        }
    }

    @Test
    public void testLoginFormElements() {
        WebElement usernameField = driver.findElement(By.name("username"));
        assertTrue(usernameField.isDisplayed());
        
        WebElement passwordField = driver.findElement(By.name("password"));
        assertTrue(passwordField.isDisplayed());
        
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));
        assertTrue(loginButton.isDisplayed());
        assertTrue(loginButton.isEnabled());
    }

    @Test
    public void testRegistrationLink() {
        WebElement registerLink = driver.findElement(By.linkText("Register"));
        assertTrue(registerLink.isDisplayed());
        assertTrue(registerLink.isEnabled());
        
        registerLink.click();
        assertEquals("ParaBank | Register for Free Online Account Access", driver.getTitle());
    }
}
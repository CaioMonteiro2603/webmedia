package deepseek.ws03.seq01;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest extends BaseTest {

    @Test
    public void testHomePageLoads() {
        driver.get(BASE_URL);
        assertEquals("ParaBank | Welcome | Online Banking", driver.getTitle());
        assertTrue(driver.findElement(By.cssSelector("img[title='ParaBank']")).isDisplayed());
    }

    @Test
    public void testMainMenuLinks() {
        driver.get(BASE_URL);
        
        // Test About Us link
        WebElement aboutUs = driver.findElement(By.linkText("About Us"));
        aboutUs.click();
        assertEquals("ParaBank | About Us", driver.getTitle());
        driver.navigate().back();

        // Test Services link
        WebElement services = driver.findElement(By.linkText("Services"));
        services.click();
        assertEquals("ParaBank | Services", driver.getTitle());
        driver.navigate().back();

        // Test Products link
        WebElement products = driver.findElement(By.linkText("Products"));
        products.click();
        assertEquals("ParaBank | Products", driver.getTitle());
        driver.navigate().back();

        // Test Locations link
        WebElement locations = driver.findElement(By.linkText("Locations"));
        locations.click();
        assertEquals("ParaBank | Locations", driver.getTitle());
        driver.navigate().back();

        // Test Admin Page link
        WebElement adminPage = driver.findElement(By.linkText("Admin Page"));
        adminPage.click();
        assertEquals("ParaBank | Administration", driver.getTitle());
    }

    @Test
    public void testFooterLinks() {
        driver.get(BASE_URL);
        
        // Test Home link in footer
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        homeLink.click();
        assertEquals("ParaBank | Welcome | Online Banking", driver.getTitle());

        // Test About Us link in footer
        WebElement aboutUs = driver.findElement(By.linkText("About Us"));
        aboutUs.click();
        assertEquals("ParaBank | About Us", driver.getTitle());
        driver.navigate().back();

        // Test Services link in footer
        WebElement services = driver.findElement(By.linkText("Services"));
        services.click();
        assertEquals("ParaBank | Services", driver.getTitle());
        driver.navigate().back();

        // Test Products link in footer
        WebElement products = driver.findElement(By.linkText("Products"));
        products.click();
        assertEquals("ParaBank | Products", driver.getTitle());
        driver.navigate().back();

        // Test Locations link in footer
        WebElement locations = driver.findElement(By.linkText("Locations"));
        locations.click();
        assertEquals("ParaBank | Locations", driver.getTitle());
        driver.navigate().back();

        // Test Forum link in footer
        WebElement forum = driver.findElement(By.linkText("Forum"));
        forum.click();
        // Forum opens in new tab, so we need to switch
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        assertTrue(driver.getCurrentUrl().contains("forums.parasoft.com"));
        driver.close();
        driver.switchTo().window(originalWindow);

        // Test Site Map link in footer
        WebElement siteMap = driver.findElement(By.linkText("Site Map"));
        siteMap.click();
        assertEquals("ParaBank | Site Map", driver.getTitle());
        driver.navigate().back();

        // Test Contact Us link in footer
        WebElement contactUs = driver.findElement(By.linkText("Contact Us"));
        contactUs.click();
        assertEquals("ParaBank | Customer Care", driver.getTitle());
    }

    @Test
    public void testLoginFunctionality() {
        driver.get(BASE_URL);
        
        // Test successful login
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("input[value='Log In']"));

        username.sendKeys("john");
        password.sendKeys("demo");
        loginButton.click();

        assertEquals("ParaBank | Accounts Overview", driver.getTitle());
        
        // Logout
        WebElement logout = driver.findElement(By.linkText("Log Out"));
        logout.click();
        assertEquals("ParaBank | Welcome | Online Banking", driver.getTitle());
    }

    @Test
    public void testRegistrationLink() {
        driver.get(BASE_URL);
        WebElement registerLink = driver.findElement(By.linkText("Register"));
        registerLink.click();
        assertEquals("ParaBank | Register for Free Online Account Access", driver.getTitle());
    }
}
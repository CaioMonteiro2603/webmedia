package deepseek.ws03.seq01;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class AdminPageTest extends BaseTest {

    @Test
    public void testDatabaseInitialization() {
        driver.get(BASE_URL);
        
        // Navigate to Admin Page
        WebElement adminPage = driver.findElement(By.linkText("Admin Page"));
        adminPage.click();
        assertEquals("ParaBank | Administration", driver.getTitle());

        // Initialize database
        WebElement initializeButton = driver.findElement(By.cssSelector("input[value='Initialize']"));
        initializeButton.click();

        // Verify initialization
        assertTrue(driver.getPageSource().contains("Database Initialized"));
    }

    @Test
    public void testCleanDatabase() {
        driver.get(BASE_URL);
        
        // Navigate to Admin Page
        WebElement adminPage = driver.findElement(By.linkText("Admin Page"));
        adminPage.click();
        assertEquals("ParaBank | Administration", driver.getTitle());

        // Clean database
        WebElement cleanButton = driver.findElement(By.cssSelector("input[value='Clean']"));
        cleanButton.click();

        // Verify cleaning
        assertTrue(driver.getPageSource().contains("Database Cleaned"));
    }
}
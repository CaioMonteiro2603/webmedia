package deepseek.ws03.seq01;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class ExternalLinksTest extends BaseTest {

    @Test
    public void testAboutUsExternalLinks() {
        driver.get(BASE_URL);
        
        // Navigate to About Us
        WebElement aboutUs = driver.findElement(By.linkText("About Us"));
        aboutUs.click();
        assertEquals("ParaBank | About Us", driver.getTitle());

        // Test Parasoft link
        WebElement parasoftLink = driver.findElement(By.linkText("www.parasoft.com"));
        parasoftLink.click();
        
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        assertTrue(driver.getCurrentUrl().contains("parasoft.com"));
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    public void testServicesExternalLinks() {
        driver.get(BASE_URL);
        
        // Navigate to Services
        WebElement services = driver.findElement(By.linkText("Services"));
        services.click();
        assertEquals("ParaBank | Services", driver.getTitle());

        // Test SOAtest link
        WebElement soatestLink = driver.findElement(By.linkText("Parasoft SOAtest"));
        soatestLink.click();
        
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        assertTrue(driver.getCurrentUrl().contains("parasoft.com/products/soatest"));
        driver.close();
        driver.switchTo().window(originalWindow);

        // Test Virtualize link
        WebElement virtualizeLink = driver.findElement(By.linkText("Parasoft Virtualize"));
        virtualizeLink.click();
        
        originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        assertTrue(driver.getCurrentUrl().contains("parasoft.com/products/virtualize"));
        driver.close();
        driver.switchTo().window(originalWindow);
    }
}
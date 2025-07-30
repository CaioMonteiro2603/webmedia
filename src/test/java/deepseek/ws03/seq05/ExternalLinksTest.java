package deepseek.ws03.seq05;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.*;

public class ExternalLinksTest extends BaseTest {

    @Test
    public void testParasoftLink() {
        String originalWindow = driver.getWindowHandle();
        
        // Click on Parasoft link
        driver.findElement(By.linkText("www.parasoft.com")).click();
        
        // Switch to new window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify new window URL
        assertTrue(driver.getCurrentUrl().contains("parasoft.com"));
        
        // Close the new window and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    public void testPrivacyPolicyLink() {
        String originalWindow = driver.getWindowHandle();
        
        // Click on Privacy Policy link
        driver.findElement(By.linkText("Privacy Policy")).click();
        
        // Switch to new window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Verify new window content
        assertTrue(driver.getPageSource().contains("Privacy Policy"));
        
        // Close the new window and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }
}
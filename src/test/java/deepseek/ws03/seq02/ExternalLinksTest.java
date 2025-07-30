package deepseek.ws03.seq02;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class ExternalLinksTest extends ParabankBaseTest {

    @Test
    public void testParasoftLink() {
        String mainWindow = driver.getWindowHandle();
        driver.findElement(By.linkText("www.parasoft.com")).click();
        
        // Switch to new window
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        
        assertTrue(driver.getCurrentUrl().contains("parasoft.com"));
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    @Test
    public void testForgotLoginInfoLink() {
        driver.findElement(By.linkText("Forgot login info?")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Customer Lookup')]")).isDisplayed());
    }

    @Test
    public void testSiteMapLink() {
        driver.findElement(By.linkText("Site Map")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Site Map')]")).isDisplayed());
    }

    @Test
    public void testContactUsLink() {
        driver.findElement(By.linkText("Contact Us")).click();
        assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Customer Care')]")).isDisplayed());
    }
}
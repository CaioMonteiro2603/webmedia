package deepseek.ws09.seq03;

import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.*;

public class ExternalLinksTest extends BaseTest {

    @Test
    public void testFacebookLink() {
        WebElement facebookLink = driver.findElement(By.cssSelector("a[href*='facebook.com']"));
        facebookLink.click();
        assertTrue(driver.getCurrentUrl().contains("facebook.com"));
    }

    @Test
    public void testTwitterLink() {
        WebElement twitterLink = driver.findElement(By.cssSelector("a[href*='twitter.com']"));
        twitterLink.click();
        assertTrue(driver.getCurrentUrl().contains("twitter.com"));
    }

    @Test
    public void testInstagramLink() {
        WebElement instagramLink = driver.findElement(By.cssSelector("a[href*='instagram.com']"));
        instagramLink.click();
        assertTrue(driver.getCurrentUrl().contains("instagram.com"));
    }
}
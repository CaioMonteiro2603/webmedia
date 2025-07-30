package deepseek.ws01.seq04;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest extends BaseTest {

    @Test
    public void testPageTitle() {
        driver.get(BASE_URL);
        assertEquals("Test Healing", driver.getTitle());
    }

    @Test
    public void testNavigationLinks() {
        driver.get(BASE_URL);
        
        // Test Home link
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        assertTrue(homeLink.isDisplayed());
        homeLink.click();
        assertEquals(BASE_URL, driver.getCurrentUrl());

        // Test About link
        WebElement aboutLink = driver.findElement(By.linkText("About"));
        assertTrue(aboutLink.isDisplayed());
        aboutLink.click();
        assertEquals(BASE_URL + "about.html", driver.getCurrentUrl());
        driver.navigate().back();

        // Test Services link
        WebElement servicesLink = driver.findElement(By.linkText("Services"));
        assertTrue(servicesLink.isDisplayed());
        servicesLink.click();
        assertEquals(BASE_URL + "services.html", driver.getCurrentUrl());
        driver.navigate().back();

        // Test Contact link
        WebElement contactLink = driver.findElement(By.linkText("Contact"));
        assertTrue(contactLink.isDisplayed());
        contactLink.click();
        assertEquals(BASE_URL + "contact.html", driver.getCurrentUrl());
    }

    @Test
    public void testExternalLinks() {
        driver.get(BASE_URL);
        
        // Test GitHub link
        WebElement githubLink = driver.findElement(By.cssSelector("a[href*='github.com']"));
        assertTrue(githubLink.isDisplayed());
        githubLink.click();
        assertTrue(driver.getCurrentUrl().contains("github.com"));
        driver.navigate().back();

        // Test Twitter link
        WebElement twitterLink = driver.findElement(By.cssSelector("a[href*='twitter.com']"));
        assertTrue(twitterLink.isDisplayed());
        twitterLink.click();
        assertTrue(driver.getCurrentUrl().contains("twitter.com"));
    }

    @Test
    public void testCallToActionButton() {
        driver.get(BASE_URL);
        WebElement ctaButton = driver.findElement(By.cssSelector(".btn-primary"));
        assertTrue(ctaButton.isDisplayed());
        ctaButton.click();
        assertEquals(BASE_URL + "contact.html", driver.getCurrentUrl());
    }
}
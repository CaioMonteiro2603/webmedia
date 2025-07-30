package deepseek.ws05.seq05;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

public class XPathTutorialPageTest extends BaseTest {

    @Override
    public void setUp() {
        super.setUp();
        driver.get("https://selectorshub.com/xpath-tutorial/");
    }

    @Test
    public void testPageTitle() {
        assertEquals("XPath Tutorial - SelectorsHub", driver.getTitle());
    }

    @Test
    public void testNavigationMenu() {
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        assertTrue(homeLink.isDisplayed());
        homeLink.click();
        assertEquals("SelectorsHub - UI element selector and generator tool", driver.getTitle());
        driver.navigate().back();

        WebElement xpathLink = driver.findElement(By.linkText("XPath"));
        assertTrue(xpathLink.isDisplayed());
        xpathLink.click();
        assertTrue(driver.getCurrentUrl().contains("xpath-tutorial"));
    }

    @Test
    public void testTutorialSections() {
        WebElement introSection = driver.findElement(By.id("introduction"));
        assertTrue(introSection.isDisplayed());

        WebElement syntaxSection = driver.findElement(By.id("syntax"));
        assertTrue(syntaxSection.isDisplayed());

        WebElement examplesSection = driver.findElement(By.id("examples"));
        assertTrue(examplesSection.isDisplayed());
    }

    @Test
    public void testCodeExamples() {
        WebElement exampleCode = driver.findElement(By.xpath("//pre[contains(text(),'//div[@class=\"example\"]')]"));
        assertTrue(exampleCode.isDisplayed());
    }

    @Test
    public void testExternalResources() {
        WebElement w3schoolsLink = driver.findElement(By.partialLinkText("W3Schools"));
        assertTrue(w3schoolsLink.isDisplayed());
        w3schoolsLink.click();
        assertTrue(driver.getCurrentUrl().contains("w3schools.com"));
        driver.navigate().back();
    }
}
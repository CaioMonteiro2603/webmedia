package deepseek.ws05.seq05;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

public class XPathPracticePageTest extends BaseTest {

    @Test
    public void testPageTitle() {
        assertEquals("XPath Practice Page - SelectorsHub", driver.getTitle());
    }

    @Test
    public void testUserEmailInput() {
        WebElement emailInput = driver.findElement(By.id("userId"));
        emailInput.sendKeys("test@example.com");
        assertEquals("test@example.com", emailInput.getAttribute("value"));
    }

    @Test
    public void testPasswordInput() {
        WebElement passwordInput = driver.findElement(By.id("pass"));
        passwordInput.sendKeys("test123");
        assertEquals("test123", passwordInput.getAttribute("value"));
    }

    @Test
    public void testCompanyInput() {
        WebElement companyInput = driver.findElement(By.name("company"));
        companyInput.sendKeys("Test Company");
        assertEquals("Test Company", companyInput.getAttribute("value"));
    }

    @Test
    public void testMobileNumberInput() {
        WebElement mobileInput = driver.findElement(By.name("mobile number"));
        mobileInput.sendKeys("1234567890");
        assertEquals("1234567890", mobileInput.getAttribute("value"));
    }

    @Test
    public void testSubmitButton() {
        WebElement submitBtn = driver.findElement(By.xpath("//input[@value='Submit']"));
        assertTrue(submitBtn.isDisplayed());
        assertTrue(submitBtn.isEnabled());
        submitBtn.click();
    }

    @Test
    public void testGenderRadioButtons() {
        WebElement maleRadio = driver.findElement(By.xpath("//input[@value='male']"));
        WebElement femaleRadio = driver.findElement(By.xpath("//input[@value='female']"));
        
        assertFalse(maleRadio.isSelected());
        assertFalse(femaleRadio.isSelected());
        
        maleRadio.click();
        assertTrue(maleRadio.isSelected());
        assertFalse(femaleRadio.isSelected());
        
        femaleRadio.click();
        assertFalse(maleRadio.isSelected());
        assertTrue(femaleRadio.isSelected());
    }

    @Test
    public void testCountryDropdown() {
        Select countryDropdown = new Select(driver.findElement(By.name("country")));
        assertEquals(3, countryDropdown.getOptions().size());
        
        countryDropdown.selectByVisibleText("India");
        assertEquals("India", countryDropdown.getFirstSelectedOption().getText());
        
        countryDropdown.selectByVisibleText("United States");
        assertEquals("United States", countryDropdown.getFirstSelectedOption().getText());
    }

    @Test
    public void testCheckboxes() {
        WebElement bikeCheckbox = driver.findElement(By.xpath("//input[@value='Bike']"));
        WebElement carCheckbox = driver.findElement(By.xpath("//input[@value='Car']"));
        
        assertFalse(bikeCheckbox.isSelected());
        assertFalse(carCheckbox.isSelected());
        
        bikeCheckbox.click();
        assertTrue(bikeCheckbox.isSelected());
        assertFalse(carCheckbox.isSelected());
        
        carCheckbox.click();
        assertTrue(bikeCheckbox.isSelected());
        assertTrue(carCheckbox.isSelected());
    }

    @Test
    public void testIframeElements() {
        // Switch to iframe
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='iframe1']")));
        
        WebElement iframeInput = driver.findElement(By.id("inp_val"));
        iframeInput.sendKeys("Iframe Test");
        assertEquals("Iframe Test", iframeInput.getAttribute("value"));
        
        // Switch back to main content
        driver.switchTo().defaultContent();
    }

    @Test
    public void testShadowDOMElements() {
        // This requires special handling for shadow DOM
        WebElement shadowHost = driver.findElement(By.cssSelector("#userName"));
        WebElement shadowRoot = (WebElement) ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("return arguments[0].shadowRoot", shadowHost);
        
        WebElement shadowInput = shadowRoot.findElement(By.cssSelector("#kils"));
        shadowInput.sendKeys("Shadow DOM Test");
        assertEquals("Shadow DOM Test", shadowInput.getAttribute("value"));
    }

    @Test
    public void testNavigationLinks() {
        // Test links to other pages
        WebElement xpathTutorialLink = driver.findElement(By.linkText("XPath Tutorial"));
        assertTrue(xpathTutorialLink.isDisplayed());
        xpathTutorialLink.click();
        assertEquals("XPath Tutorial - SelectorsHub", driver.getTitle());
        driver.navigate().back();
        
        WebElement cssSelectorLink = driver.findElement(By.linkText("CSS Selector"));
        assertTrue(cssSelectorLink.isDisplayed());
        cssSelectorLink.click();
        assertEquals("CSS Selector - SelectorsHub", driver.getTitle());
        driver.navigate().back();
    }

    @Test
    public void testExternalLinks() {
        // Test external links
        WebElement youtubeLink = driver.findElement(By.linkText("Youtube"));
        assertTrue(youtubeLink.isDisplayed());
        youtubeLink.click();
        assertTrue(driver.getCurrentUrl().contains("youtube.com"));
        driver.navigate().back();
        
        WebElement twitterLink = driver.findElement(By.linkText("Twitter"));
        assertTrue(twitterLink.isDisplayed());
        twitterLink.click();
        assertTrue(driver.getCurrentUrl().contains("twitter.com"));
        driver.navigate().back();
    }
}
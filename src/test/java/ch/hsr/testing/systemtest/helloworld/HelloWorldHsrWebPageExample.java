/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.helloworld;

import ch.hsr.testing.systemtest.weekenddiscount.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * This class is a first example for running an automated system test against a website.
 */
public class HelloWorldHsrWebPageExample implements Constants {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        // Create an instance of the driver of your choice
        //driver = new HtmlUnitDriver();

        System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void searchForMAS() {
        // And now use this to visit the duckduckgo page
        driver.get("https://www.ost.ch/de/");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("tx_solr[q]"));

        // Enter something to search for
        element.sendKeys("MAS SE");

        // Now submit the form. WebDriver will find the form for us from the
        // element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
        Assertions.assertThat(driver.getPageSource()).contains("MAS Software Engineering");
    }


    @Test
    public void lookupPhoneNumber() {
        // TODO: Implement this
        // 1. Jump to "Kontakt"-Page
        // 2. Assert that the page title contains "Kontakt"
        // 3. Assert that the phone number '+41 58 257 41 11' is found on the page
        org.junit.jupiter.api.Assertions.fail("Implement Testcase");
    }
}

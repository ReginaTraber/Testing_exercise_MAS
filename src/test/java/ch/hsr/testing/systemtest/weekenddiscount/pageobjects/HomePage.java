package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Page {

    private static final Log LOG = LogFactory.getLog(HomePage.class);

    By metaDescriptionLocator = By.xpath("//meta[@name='description']");
    By shownProductsLocator = By.className("product-list-item");

    public HomePage(WebDriver driver) {
        super(driver);
        String content = driver.findElement(metaDescriptionLocator)
                .getAttribute("content");
        if (!(content.equals("Broadleaf Demo Store"))) {
            throw new IllegalStateException("This is not the home page");
        }
        LOG.debug("HomePage created successfully");
    }

    public static HomePage navigateTo(WebDriver driver) {
        driver.get(BASE_URL);
        return PageFactory.initElements(driver, HomePage.class);
    }

    public int getNofProductsShown() {
        return driver.findElements(shownProductsLocator).size();

    }


}

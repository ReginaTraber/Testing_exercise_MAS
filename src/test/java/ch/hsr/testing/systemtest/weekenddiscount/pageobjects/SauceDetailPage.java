package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SauceDetailPage extends Page {

    private static final Log LOG = LogFactory.getLog(SauceDetailPage.class);

    private By addToCartButtonLocator = By.xpath("//button[contains(@class,'js-addToCart')]");

    public SauceDetailPage(WebDriver driver) {
        super(driver);
        LOG.debug("HotSauces Detail Page created successfully");
    }

    public void buySauce() {
        driver.findElement(addToCartButtonLocator).click();
    }

}

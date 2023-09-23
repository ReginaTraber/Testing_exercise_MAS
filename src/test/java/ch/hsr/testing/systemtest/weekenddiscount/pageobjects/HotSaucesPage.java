package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HotSaucesPage extends Page {

    private static final Log LOG = LogFactory.getLog(HotSaucesPage.class);

    private By habaneroHotSauceLocator = By.xpath("//a[contains(@href,'day_of_the_dead_habanero_hot_sauce')]");

    public HotSaucesPage(WebDriver driver) {
        super(driver);
        if (!(driver.getTitle().startsWith("Hot Sauces"))) {
            throw new IllegalStateException("This is not the hot sauces page");
        }
        LOG.debug("HotSauces Page created successfully");
    }

    public SauceDetailPage sauceDayOfTheDeadHabaneroDetails() {
        driver.findElement(habaneroHotSauceLocator).click();
        return PageFactory.initElements(driver, SauceDetailPage.class);

    }
}






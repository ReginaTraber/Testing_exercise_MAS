/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.tests;

import ch.hsr.testing.systemtest.weekenddiscount.Constants;
import ch.hsr.testing.systemtest.weekenddiscount.extension.ScreenshotOnFailureExtension;
import ch.hsr.testing.systemtest.weekenddiscount.extension.WebDriverKeeper;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.HomePage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.HotSaucesPage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.SauceDetailPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.concurrent.TimeUnit;

/**
 * The Class HeatClinicSmokeTests. This class implements some smoke tests that
 * might be used as test entry criteria for further tests.
 */
@ExtendWith(ScreenshotOnFailureExtension.class)
public class WeekendDiscountSmokeTests implements Constants {

    private static final Log LOG = LogFactory
            .getLog(WeekendDiscountSmokeTests.class);

    private WebDriver driver;

    @BeforeEach
    public void setup() {

        System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebDriverKeeper.getInstance().setDriver(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testLoadHomePage() {

        // get the home page
        HomePage homePage = HomePage.navigateTo(driver);

        // and make sure some sauces are show
        Assertions.assertTrue(
                homePage.getNofProductsShown() > 0, "Some products should be shown");
    }

    @Test
    public void testAddItemToCart() {

        // get the home page
        HomePage homePage = HomePage.navigateTo(driver);

        // make sure that cart is empty at the beginning
        MatcherAssert.assertThat(homePage.getNofObjectsInCart(), Matchers.is(0));

        // go to the sauces
        HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

        // and pick one
        SauceDetailPage saucePage = hotSaucesPage.sauceDayOfTheDeadHabaneroDetails();

        // now buy this one
        saucePage.buySauce();

        // jump back to the home page
        homePage = HomePage.navigateTo(driver);

        // and check that there is 1 product in the cart.
        MatcherAssert.assertThat(homePage.getNofObjectsInCart(), Matchers.is(1));
    }

}

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
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.CartPage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.HomePage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.HotSaucesPage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.SauceDetailPage;
import ch.hsr.testing.systemtest.weekenddiscount.util.DBUtil;
import ch.hsr.testing.systemtest.weekenddiscount.util.DateFactory;
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

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The Class HeatClinicAcceptanceTests. In this class the acceptance Tests for
 * the weekend discount features are implemented.
 */
@ExtendWith(ScreenshotOnFailureExtension.class)
public class WeekendDiscountAcceptanceTests implements Constants {

    private static final Log LOG = LogFactory
            .getLog(WeekendDiscountAcceptanceTests.class);

    public ScreenshotOnFailureExtension screenshot = new ScreenshotOnFailureExtension();

    private WebDriver driver;

    /**
     * Tipp: The ending of the url gives the name that can be used
     * E.g. https://localhost:8443/hot-sauces/hoppin_hot_sauce => hrefSauce = hoppin_hot_sauce
     */
    private static final String hrefSauce = "day_of_the_dead_scotch_bonnet_sauce";

    /**
     * Discount allowed during the weekend in integer %
     */

    @BeforeEach
    public void setup() {
        //Disclaimer: I had to change how to receive the chromedriver, as it would not work for me
        //Changed it to directly interact with the newest chromedriver (in Zip under Assets) and my installed Chrome Browser
        //String chromeDriver = "C:\\SeleniumTesting\\chromedriver.exe";
        //System.setProperty("webdriver.chrome.driver", chromeDriver);

        //TODO: Change here to your chrome driver
        // System.setProperty("webdriver.chrome.driver", "C:\\repos" + "\\Testing_exercise_MAS\\chromedriver.exe");
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
    public void testWeekendDiscountEnabled() {

        Date within4thWeekend = DateFactory.createDate(2023, 10, 28, 0, 0, 0);
        DBUtil.setTestTime(within4thWeekend);

        //Add desired sauce to check on applied weekend
        CartPage cartPage = addSauceToCart(hrefSauce);
        //Calculates discount provided (savings/subtotal)

        //Rounding to 3.50 seemed okay from 6.99 (50% => 3.495)
        Assertions.assertTrue(checkIfPriceCorrect(cartPage, "3.50") && cartPage.isWeekendDiscountApplied());
    }

    @Test
    public void testWeekendDiscountDisabled() {

        Date after4thWeekend = DateFactory.createDate(2018, 6, 25, 0, 0, 0);
        DBUtil.setTestTime(after4thWeekend);

        //Check if weekend discount is not applied
        CartPage cartPage = addSauceToCart(hrefSauce);
        //Check for zero, as it is possible that there is no promotion defined, but still applied and also check that no promotion is displayed/applied
        Assertions.assertTrue(checkIfPriceCorrect(cartPage, "0.00") && !cartPage.isWeekendDiscountApplied());
    }

    /**
     * Add a sauce to the card that has been given by the hrefSauce.
     *
     * @param hrefTargetedSauce the sauce defined in hrefSauce value: {@value hrefSauce}
     * @return Return object CartPage
     */
    private CartPage addSauceToCart(String hrefTargetedSauce) {
        HomePage homePage = HomePage.navigateTo(driver);

        // make sure that cart is empty at the beginning
        MatcherAssert.assertThat(homePage.getNofObjectsInCart(), Matchers.is(0));

        // go to the sauces
        HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

        // and pick the requested one
        SauceDetailPage sauceDetailPage = hotSaucesPage.dynamicSauceDetailPage(hrefTargetedSauce);

        //Try to buy sauce and go to cart to check for discount
        sauceDetailPage.buySauce();
        CartPage cartPage = sauceDetailPage.goToCart();

        //Get list of amounts to calculate (as understood, this is applied when "paying")
        cartPage.initPricesDisplayed();

        return cartPage;
    }

    /**
     * Calculates the discount given by the saving and subtotal and checks if it is in range
     *
     * @param cartPage Cart Page that has information about savings
     * @return Discount calculated from the extracted values
     */
    private boolean checkIfPriceCorrect(CartPage cartPage, String expectedPriceInSavings) {
        return expectedPriceInSavings.equals(cartPage.getSavings());
    }
}

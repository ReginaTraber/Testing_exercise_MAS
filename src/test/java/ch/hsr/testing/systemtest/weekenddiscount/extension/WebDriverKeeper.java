/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.extension;

import org.openqa.selenium.WebDriver;

/**
 * The Class WebDriverKeeper. Singleton for keeping the selenium webdriver to
 * use it in the Statement for getting the screenshot and the page source code.
 */
public class WebDriverKeeper {

    /**
     * The instance.
     */
    private static WebDriverKeeper instance;

    /**
     * Instantiates a new web driver keeper.
     */
    private WebDriverKeeper() {
    }

    /**
     * Gets the single instance of WebDriverKeeper.
     *
     * @return single instance of WebDriverKeeper
     */
    public static WebDriverKeeper getInstance() {
        if (instance == null) {
            instance = new WebDriverKeeper();
        }
        return instance;
    }

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

}

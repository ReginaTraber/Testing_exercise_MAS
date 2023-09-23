package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends Page {

    private static final Log LOG = LogFactory.getLog(HomePage.class);


    @FindBy(id = "cart_products")
    private WebElement productsInCartTable;

    public CartPage(WebDriver driver) {
        super(driver);

        if (!(driver.getPageSource().contains("checkout"))) {
            throw new IllegalStateException("This is not the cart page");
        }
        LOG.debug("CartPage created successfully");
    }
}

package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends Page {

    private static final Log LOG = LogFactory.getLog(HomePage.class);
    private String subtotal;
    private String savings;
    private boolean isWeekendDiscountApplied = false;

    /**
     * Check if ANYWHERE this class is shown. Enabled discount testing fails, if this class suddenly not available anymore.
     * In that particular case, new selector has to be defined
     */
    private static final By SelectorPromotionTitle = By.className("promotion-applied");
    @FindBy(id = "cart_products")
    private WebElement productsInCartTable;

    public CartPage(WebDriver driver) {
        super(driver);

        if (!(driver.getPageSource().contains("checkout"))) {
            throw new IllegalStateException("This is not the cart page");
        }

        LOG.debug("CartPage created successfully");
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public boolean isWeekendDiscountApplied() {
        return isWeekendDiscountApplied;
    }

    public void setWeekendDiscountApplied(boolean weekendDiscountApplied) {
        isWeekendDiscountApplied = weekendDiscountApplied;
    }

    /**
     * Sets the price received from the table in the Summary of the cart.
     */
    public void initPricesDisplayed() {
        //Gets the price displayed at Subtotal in Cart Summary
        setSubtotal(getPriceFromPage("Subtotal"));
        //Gets the calculated Total Savings displayed in Cart Summary
        setSavings(getPriceFromPage("Total Savings"));
        setWeekendDiscountApplied(getIfWeekendDiscountApplied());
    }

    private String getPriceFromPage(String desiredPriceElement) {
        String selector =
                "//div/span[text() = '" + desiredPriceElement + "']/following-sibling::span[contains(@class,'pull-right')]";
        By priceExtracted = By.xpath(selector);

        /*Clean numbers from anything that could ANYTHING other than a number or a delimiter
         * Allowed delimiters extracted from the different languages on the page: , and .
         * */
        String rawPrice = driver.findElement(priceExtracted).getText();
        StringBuilder sb = new StringBuilder();
        rawPrice.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> Character.isDigit(c) || c == '.' || c == ',')
                .forEach(sb::append);

        return sb.toString();
    }

    private boolean getIfWeekendDiscountApplied() {
        //If anything has a class tagged as promotion
        List<WebElement> tagsPromotion = driver.findElements(SelectorPromotionTitle);

        return tagsPromotion.stream().count() > 0;
    }
}

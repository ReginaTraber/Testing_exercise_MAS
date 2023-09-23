package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import ch.hsr.testing.systemtest.weekenddiscount.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Page implements Constants {

    WebDriver driver;

    By logoLocator = By.xpath("//a[contains(text(),'The Heat Clinic Home')]");
    By cartLinkLocator = By.xpath("//a[i[text()='shopping_cart']]");
    By navigatorLocator = By.xpath("//div[@id='left-nav']");
    By openCartPageLocator = By.xpath("//a[contains(@class,'goto-full-cart')]");
    By cartBadgeLocator = By.className("cart-count-badge");

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage clickLogo() {
        driver.findElement(logoLocator).click();
        return PageFactory.initElements(driver, HomePage.class);

    }

    public HotSaucesPage jumpToHotSauces() {
        WebElement navigation = driver.findElement(navigatorLocator);
        navigation.findElement(By.partialLinkText("HOT")).click();
        return PageFactory.initElements(driver, HotSaucesPage.class);
    }

    public CartPage goToCart() {
        driver.findElement(cartLinkLocator).click();
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(openCartPageLocator));

        WebElement fullCartButton = driver.findElement(openCartPageLocator);
        fullCartButton.click();
        return PageFactory.initElements(driver, CartPage.class);
    }

    public int getNofObjectsInCart() {
        driver.get(driver.getCurrentUrl());
        List<WebElement> badge = driver.findElements(cartBadgeLocator);
        if (badge.size() > 0) {
            return Integer.parseInt(badge.get(0).getText());
        } else {
            return 0;
        }
    }


}

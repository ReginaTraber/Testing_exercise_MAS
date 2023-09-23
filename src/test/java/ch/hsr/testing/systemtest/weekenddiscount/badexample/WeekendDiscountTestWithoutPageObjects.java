/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.badexample;

import ch.hsr.testing.systemtest.weekenddiscount.Constants;
import ch.hsr.testing.systemtest.weekenddiscount.util.DBUtil;
import ch.hsr.testing.systemtest.weekenddiscount.util.DateFactory;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;

/**
 * The Class WeekendDiscountTestWithoutPageObjects. This is the bad example how
 * the tests would look like without the page object pattern: a messy bunch of
 * implementation details spread over all tests, impossible to maintain
 */
public class WeekendDiscountTestWithoutPageObjects implements Constants {

	private WebDriver driver;

	@BeforeEach
	public void setup() {

		System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		DBUtil.setTestTime(DateFactory.createDate(2019, 6, 23, 0, 0, 0));
	}

	@Test
	public void testAddToCart() {
		driver.get(BASE_URL);

		List<WebElement> nofItemsInCart = driver.findElements(By.className("cart-count-badge"));
		MatcherAssert.assertThat("Should be 0 items in cart", nofItemsInCart.size(), is(0));

		WebElement navigation = driver.findElement(By.xpath("//div[@id='left-nav']"));
		navigation.findElement(By.partialLinkText("HOT")).click();

		driver.findElement(By.xpath("//a[contains(@href,'day_of_the_dead_habanero_hot_sauce')]")).click();

		driver.findElement(By.xpath("//button[contains(@class,'js-addToCart')]")).click();

		WebElement itemsInCart = driver.findElement(By.className("cart-count-badge"));
		MatcherAssert.assertThat("Should be 1 items in cart", itemsInCart.getText(), is("1"));
	}
}

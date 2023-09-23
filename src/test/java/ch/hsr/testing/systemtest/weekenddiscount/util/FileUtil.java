/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The Class FileUtil. Helps to persist screenshot and page source code in case
 * of failure. Is used by the ScreenshotOnFailureExtension and -Statement
 */

public class FileUtil {

    private static final Log LOG = LogFactory.getLog(FileUtil.class);

    private static final String TEMP_DIR = "temp";
    private static final String SCREENSHOT_PREFIX = "screenshot_";
    private static final String PAGESOURCE_PREFIX = "pagesource_";
    private static final String SCREENSHOT_EXTENSION = ".png";
    private static final String PAGESOURCE_EXTENSION = ".html";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyyMMdd_HHmmss");

    public static void writePageSourceToFile(String testcase, WebDriver driver)
            throws IOException {
        String pageSource = driver.getPageSource();
        // Create file
        File destination = new File(TEMP_DIR + File.separator + testcase + "_"
                + PAGESOURCE_PREFIX + DATE_FORMAT.format(new Date())
                + PAGESOURCE_EXTENSION);
        FileUtils.writeStringToFile(destination, pageSource, UTF_8);
        LOG.info("PageSource saved to " + destination.getAbsolutePath());
    }

    public static void saveScreenshotToFile(String testcase, WebDriver driver)
            throws IOException {
        File screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
        File destination = new File(TEMP_DIR + File.separator + testcase + "_"
                + SCREENSHOT_PREFIX + DATE_FORMAT.format(new Date())
                + SCREENSHOT_EXTENSION);
        FileUtils.copyFile(screenshot, destination);
        LOG.info("Screenshot saved to " + destination.getAbsolutePath());
    }
}

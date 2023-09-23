/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.extension;

import ch.hsr.testing.systemtest.weekenddiscount.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

/**
 * The Class ScreenshotOnFailureExtension. Used for adding steps at execution time of
 * the junit framework in order to write log output and create snapshot of html
 * and a screenshot in case of failure.
 */
public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    private static final Log LOG = LogFactory
            .getLog(ScreenshotOnFailureExtension.class);


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Optional<Throwable> testException = context.getExecutionException();
        if (testException.isPresent()) {
            String classMethodName = context.getTestClass().get().getSimpleName() + "_" +context.getDisplayName();
            LOG.info("Test failed: " + classMethodName);
            WebDriver driver = WebDriverKeeper.getInstance().getDriver();

            FileUtil.saveScreenshotToFile(classMethodName, driver);
            FileUtil.writePageSourceToFile(classMethodName, driver);
        }

    }
}
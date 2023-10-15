package ch.hsr.testing.unittest.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestExecutionTimeLoggingExtension implements BeforeEachCallback, AfterEachCallback {

    private long startTime;

    @Override
    public void afterEach(ExtensionContext context) {
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Test duration for " + context.getDisplayName() + ": " + duration + "ms");

    }

    @Override
    public void beforeEach(ExtensionContext context) {
        startTime = System.currentTimeMillis();

    }


}

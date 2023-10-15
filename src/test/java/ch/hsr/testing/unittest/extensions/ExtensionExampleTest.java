package ch.hsr.testing.unittest.extensions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({TestExecutionTimeLoggingExtension.class})
class ExtensionExampleTest {

    @Test
    public void test() {
        Assertions.assertTrue(true);
    }


    @Test
    public void test2() throws InterruptedException {
        Thread.sleep(1000);
        Assertions.assertTrue(true);
    }
}


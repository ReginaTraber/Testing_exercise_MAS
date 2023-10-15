package ch.hsr.testing.unittest.parametrized;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static ch.hsr.testing.unittest.parametrized.PrimeChecker.isPrime;

public class PrimeCheckerTest {

    @ParameterizedTest(name = "Test {index} => input:{0} => isPrime?:{1}")
    @MethodSource("createTestInput")
    void testPrimeCheckerParametrized(int input, boolean isPrime) {
        boolean actual = PrimeChecker.isPrime(input);
        MatcherAssert.assertThat(actual, Matchers.is(isPrime));
    }


    private static Stream<Arguments> createTestInput() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, true),
                Arguments.of(4, false),
                Arguments.of(9, false),
                Arguments.of(22, false),
                Arguments.of(23, true),
                Arguments.of(144, false),
                Arguments.of(7187, true)
        );
    }


    private final Map<Integer, Boolean> valuesToBeTested = Map.of(
            1, true,
            2, true,
            3, true,
            4, false,
            5, true,
            9, false
    );

    @TestFactory
    public Stream<DynamicTest> dynamicTestsForEmployeeWorkflows() {
        return valuesToBeTested.keySet().stream()
                .map(value -> DynamicTest.dynamicTest("Number " + value + " is prime? " + valuesToBeTested.get(value),
                        () -> org.junit.jupiter.api.Assertions.assertEquals(PrimeChecker.isPrime(value), valuesToBeTested.get(value))));
    }

    public static Stream<Arguments> testcases() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, true),
                Arguments.of(4, false)
        );
    }

    @ParameterizedTest(name = "{index}: {0} is a prime number: {1}")
    @MethodSource("testcases")
    void testPrimeChecker1(int input, boolean expectedOutput) {

        // Act
        boolean result = isPrime(input);

        // Assert
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(expectedOutput);
    }

    @ParameterizedTest(name = "{index}")
    @CsvFileSource(resources = "testcases2.csv", numLinesToSkip = 1)
    void testPrimeChecker2(int input, boolean expectedOutput) {

        // Act
        boolean result = isPrime(input);

        // Assert
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(expectedOutput);
    }
}
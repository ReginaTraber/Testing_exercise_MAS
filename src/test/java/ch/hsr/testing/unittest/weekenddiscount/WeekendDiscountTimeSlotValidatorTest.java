package ch.hsr.testing.unittest.weekenddiscount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WeekendDiscountTimeSlotValidatorTest {

    WeekendDiscountTimeSlotValidator validator;

    @BeforeEach
    void setUp() {
        validator = new WeekendDiscountTimeSlotValidator();
    }

    /**
     * Setup a testDate for the exception test
     */
    LocalDateTime testDate = LocalDateTime.of(2023, 2, 25, 12, 0);

    /**
     * Checks an exception is thrown, if
     * isAuthorizedForDiscount gets called without initialising the validator
     */
    @Test
    void testNotInitialized() {
        IllegalWeekendNumberException exception = assertThrows(IllegalWeekendNumberException.class, () -> {
            validator.isAuthorizedForDiscount(LocalDateTime.now());
        });

        String expectedErrorMessage = "WeekendDiscountTimeSlotValidator has not been initialized correctly!";
        assertEquals(expectedErrorMessage, exception.getMessage(), "Expected " +
                "custom exception message for not initialized number of weekends");
    }


    /**
     * Checks an exception is thrown, if the initialized weekend number is
     * bigger than the number of weekends in the month we're trying to get the
     * discount in. February 2023 doesn't have a 5th weekend.
     */
    @Test
    void testExceptionForTooManyWeekends() {
        validator.initializeWithWeekendNumber(5);

        IllegalWeekendNumberException exception = assertThrows(IllegalWeekendNumberException.class, () -> {
            validator.isAuthorizedForDiscount(testDate);
        });

        String expectedErrorMessage = "Invalid weekend number: 5";
        assertEquals(expectedErrorMessage, exception.getMessage(),
                "Expected custom exception message for too many weekends");
    }

    /**
     * Checks an exception is thrown, if the initialized weekend number is 0.
     */
    @Test
    void testExceptionForZeroAsWeekendNumber() {
        validator.initializeWithWeekendNumber(0);
        IllegalWeekendNumberException exception = assertThrows(IllegalWeekendNumberException.class, () -> {
            validator.isAuthorizedForDiscount(testDate);
        });

        String expectedErrorMessage = "Invalid weekend number: 0";
        assertEquals(expectedErrorMessage, exception.getMessage(),
                "Expected custom exception message for zero weekendNumber");
    }

    /**
     * Checks an exception is thrown, if the initialized weekend number is
     * negative.
     */
    @Test
    void testExceptionForNegativeWeekendNumber() {
        validator.initializeWithWeekendNumber(-1);
        IllegalWeekendNumberException exception = assertThrows(IllegalWeekendNumberException.class, () -> {
            validator.isAuthorizedForDiscount(testDate);
        });

        String expectedErrorMessage = "Invalid weekend number: -1";
        assertEquals(expectedErrorMessage, exception.getMessage(),
                "Expected custom exception message for negative weekendNumber");
    }

    /**
     * Various checks to loop through possible cases
     */
    @ParameterizedTest
    @CsvFileSource(resources = "weekendDiscountTimeSlotValidatorTestCases" +
            ".csv", numLinesToSkip = 1)
    void testWithCsvInput(int weekendNumber, int year, int month, int day,
                          int hour, int minute, boolean expectedResult, String explanation)
            throws IllegalWeekendNumberException {
        validator.initializeWithWeekendNumber(weekendNumber);
        LocalDateTime testDate = LocalDateTime.of(year, month, day, hour, minute);
        assertEquals(expectedResult, validator.isAuthorizedForDiscount(testDate));
    }
}

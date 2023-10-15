package ch.hsr.testing.unittest.weekenddiscount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDateTime;

public class WeekendDiscountTimeSlotValidatorTest {
    
    @ParameterizedTest(name = "Test {index}: weekendNumber: {0} for {1} throws " +
            "exception")
    @CsvFileSource(resources = "throwIllegalWeekendNumberExceptionTestcases.csv", numLinesToSkip = 1)
    public void testInitializeWithWeekendNumberThrowsException(int weekendNumber,
                                                               LocalDateTime date) {
        // Arrange
        WeekendDiscountTimeSlotValidator validator = new WeekendDiscountTimeSlotValidator();
        validator.initializeWithWeekendNumber(weekendNumber);

        // Act + Assert
        Assertions.assertThatThrownBy(() -> {
                    validator.isAuthorizedForDiscount(date);
                })
                .isInstanceOf(IllegalWeekendNumberException.class);
    }

    @ParameterizedTest(name = "Test {index}: weekendNumber: {0}, date {1} is " +
            "discounted: {2}")
    @CsvFileSource(resources =
            "weekendDiscountTestcasesValidInitializationTestCases.csv", numLinesToSkip = 1)
    public void testIsAuthorizedForDiscount(int weekendNumber,
                                            LocalDateTime date,
                                            boolean expectedOutput) throws IllegalWeekendNumberException {
        // Arrange
        WeekendDiscountTimeSlotValidator validator = new WeekendDiscountTimeSlotValidator();
        validator.initializeWithWeekendNumber(weekendNumber);

        // Act
        boolean actualOutput = validator.isAuthorizedForDiscount(date);

        // Assert
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}
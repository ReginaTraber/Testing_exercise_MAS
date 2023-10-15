package ch.hsr.testing.unittest.weekenddiscount;

/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Regina Traber, regina.traber@ost.ch
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

public class WeekendDiscountTimeSlotValidator {

    public static final List<DayOfWeek> WEEKEND_DAYS = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    public static final int NOF_WEEKDAYS = 7;

    private Integer weekendNumber;

    public void initializeWithWeekendNumber(int weekendNumber) {
        this.weekendNumber = weekendNumber;
    }

    /**
     * Checks whether a date is within the nth weekend (Saturday 00:00 to Sunday
     * 23:59) of the month. The number n has to be given to the instance beforehand
     * using the initializeWithWeekendNumber Method.
     *
     * @param now the point in time for which the decision should be made whether weekend discount is applied or not
     * @return
     * @throws IllegalWeekendNumberException if weekend number is not set
     *                                       or if the weekend number is higher than the number of weekends in this month
     */

    // correction: Check if the weekend number is higher than the number of
    // weekends in this month and that the weekend number > 1
    public boolean isAuthorizedForDiscount(LocalDateTime now) throws IllegalWeekendNumberException {

        // correction: adding a check for .weekendNumber < 1 to avoid
        // negative numbers  and 0
        if (this.weekendNumber == null || this.weekendNumber < 1) {
            throw new IllegalWeekendNumberException("WeekendDiscountTimeSlotValidator has not been initialized correctly!");
        }
        Integer firstSaturdayInMonth = null;
        for (int i = 1; i <= YearMonth.from(now).lengthOfMonth() && firstSaturdayInMonth == null; i++) {
            if (LocalDate.of(now.getYear(), now.getMonth(), i).getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                firstSaturdayInMonth = i;
            }
        }
        // correction: adding check that Weekend number is not higher
        // than the number of full weekends in this month
        int dayOfMonthOfDiscountSaturday =
                firstSaturdayInMonth + ((weekendNumber - 1) * NOF_WEEKDAYS);
        if (dayOfMonthOfDiscountSaturday + 1 > YearMonth.from(now).lengthOfMonth()) {
            throw new IllegalWeekendNumberException("Weekend number is higher than the number of weekends in this month!");
        } else {
            if (WEEKEND_DAYS.contains(now.getDayOfWeek())) {
                LocalDate beginningOfDiscountWeekend = LocalDate.of(
                        now.getYear(),
                        now.getMonth(),
                        dayOfMonthOfDiscountSaturday);
                // correction: Check if the day is in the discount weekend
                if (now.getDayOfMonth() == beginningOfDiscountWeekend.getDayOfMonth() ||
                        now.getDayOfMonth() == beginningOfDiscountWeekend.getDayOfMonth() + 1) {
                    return true;
                }
            }
            return false;
        }
    }

}

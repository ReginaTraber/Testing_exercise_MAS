package ch.hsr.testing.unittest.weekenddiscount;

/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * original: Thomas Briner, thomas.briner@gmail.com
 * changes: Surendiran Sithamparam, Regina Traber
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
     * Correction of Comment
     * Checks whether a date is within the nth weekend (Saturday 00:00 to Sunday
     * 23:59) of the month. A discount weekend is a valid discount weekend
     * also if only Saturday is in the current month. In this case the
     * discount starts from Saturday 00:00 to Saturday 23:59.
     * The number n has to be given to the instance
     * beforehand using the initializeWithWeekendNumber Method.
     *
     * @param now the point in time for which the decision should be made whether weekend discount is applied or not
     * @return
     * @throws IllegalWeekendNumberException Correction of Comment: if
     *                                       weekend number is not set
     *                                       or if the weekend number is
     *                                       not a valid number of weekends in
     *                                       this month
     */
    public boolean isAuthorizedForDiscount(LocalDateTime now) throws IllegalWeekendNumberException {
        // make sure a weekend number has been set already
        if (this.weekendNumber == null) {
            throw new IllegalWeekendNumberException(
                    "WeekendDiscountTimeSlotValidator has not been initialized correctly!");
        }
        //Correction: Throws exception if the weekend number is not valid for the current month
        if (!isValidWeekendNumberForCurrentMonth(now)) {
            throw new IllegalWeekendNumberException("Invalid weekend number: " + weekendNumber);
        }
        DayOfWeek test = now.getDayOfWeek();
        if (WEEKEND_DAYS.contains(now.getDayOfWeek())) {
            LocalDate beginningOfDiscountWeekend = LocalDate.of(
                    now.getYear(),
                    now.getMonth(),
                    getDayOfMonthOfDiscountSaturday(now));
            // Correction: Correcting logic error; for a discount day the
            // day has to be in the discount weekend
            return now.getDayOfMonth() == beginningOfDiscountWeekend.getDayOfMonth() ||
                    now.getDayOfMonth() == beginningOfDiscountWeekend.getDayOfMonth() + 1;
        }
        return false;
    }


    private Integer getDayOfMonthFirstSaturdayInMonth(LocalDateTime now) {
        Integer firstSaturdayInMonth = null;
        // Correction: Extracted method and adapted the loop bound since
        // it will anyway not be executed more than the amount of days in a week
        for (int i = 1; i <= NOF_WEEKDAYS && firstSaturdayInMonth == null; i++) {
            if (LocalDate.of(now.getYear(), now.getMonth(), i).getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                firstSaturdayInMonth = i;
            }
        }
        return firstSaturdayInMonth;
    }

    private Integer getDayOfMonthOfDiscountSaturday(LocalDateTime now) {
        return getDayOfMonthFirstSaturdayInMonth(now) + (weekendNumber - 1) * NOF_WEEKDAYS;
    }

    // Correction: Extracted method and added a check if the weekend number
    // is a possible weekend number for the current month
    private boolean isValidWeekendNumberForCurrentMonth(LocalDateTime now) {
        if (weekendNumber <= 0) {
            return false;
        }
        return getDayOfMonthOfDiscountSaturday(now) <= YearMonth.from(now).lengthOfMonth();
    }
}

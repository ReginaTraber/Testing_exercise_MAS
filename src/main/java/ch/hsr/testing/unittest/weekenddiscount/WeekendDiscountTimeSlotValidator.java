package ch.hsr.testing.unittest.weekenddiscount;

/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * @throws IllegalWeekendNumberException
     *             if weekend number is not set
     *             or if the weekend number is higher than the number of weekends in this month
     */
    public boolean isAuthorizedForDiscount(LocalDateTime now) throws IllegalWeekendNumberException {
        // make sure a weekend number has been set already
        if (this.weekendNumber == null) {
            throw new IllegalWeekendNumberException("WeekendDiscountTimeSlotValidator has not been initialized correctly!");
        } else {

            if (WEEKEND_DAYS.contains(now.getDayOfWeek())) {
                Integer firstSaturdayInMonth = null;
                for (int i = 1; i < now.getMonth().maxLength() && firstSaturdayInMonth == null; i++) {
                    if (LocalDate.of(now.getYear(), now.getMonth(), i).getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                        firstSaturdayInMonth = i;
                    }
                }
                LocalDate beginningOfDiscountWeekend = LocalDate.of(
                        now.getYear(),
                        now.getMonth(),
                        firstSaturdayInMonth + (weekendNumber - 1) * NOF_WEEKDAYS);
                if (now.getDayOfMonth() >= beginningOfDiscountWeekend.getDayOfMonth() ||
                        now.getDayOfMonth() >= beginningOfDiscountWeekend.getDayOfMonth() + 1) {
                    return true;
                }
            }
            return false;
        }
    }

}

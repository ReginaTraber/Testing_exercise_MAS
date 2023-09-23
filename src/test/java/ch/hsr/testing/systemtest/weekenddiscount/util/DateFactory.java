/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for creating dates
 *
 * @author Thomas Briner, thomas.briner@gmail.com
 */
public class DateFactory {

    /**
     * Utility Method for creating a java.api.Date-
     *
     * @param year   The desired year, e.g. 2015
     * @param month  The desired month, e.g. 6 for June
     * @param day    The desired day, e.g. 30
     * @param hour   The desired hour, 0-23
     * @param minute The desired minute
     * @param second The desired second
     * @return The created date
     */
    public static Date createDate(int year, int month, int day, int hour,
                                  int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);

        // Be careful: Month starts with 0
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        return calendar.getTime();

    }

}

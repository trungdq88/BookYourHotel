package com.library.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Huynh Quang Thao
 */
public class Helper {

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Date getTomorow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);  // Add one day
        return calendar.getTime();
    }
    public static Date getNextDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);  // Add one day
        return calendar.getTime();
    }
    public static String NormalizeString(String s) {
        s = s.toLowerCase().trim().replaceAll("\\s+", " ");
        s = s.replaceAll("\\.", "");
        s = AccentRemoval.removeAccent(s);
        return s;
    }
}

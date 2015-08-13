package com.example.alekszilagyi.moraviandailytexts.utilities;

import java.util.GregorianCalendar;

/**
 * Created by alekszilagyi on 3/21/15.
 */
public class TimeUtility
{
    private TimeUtility()
    {}

    public static int getLongTimeHour(long time)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        return cal.get(GregorianCalendar.HOUR_OF_DAY);
    }

    public static int getLongTimeMinute(long time)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        return cal.get(GregorianCalendar.MINUTE);
    }
}

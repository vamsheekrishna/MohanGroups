package com.example.mybusinesstracker.utilities;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static final String DD_MMM_YYYY = "dd MMM yyyy";
    public static final String MMM_YYYY = "MMM yyyy";
    public static final String DD_MMM = "dd MMM";
    public static final String HH_MM_SS = "hh:mm:ss a";
    public static final String DD_MMM_YYYY_HH_MM_SS = DD_MMM_YYYY+" "+HH_MM_SS;

    public static Date getStartOfDay(Calendar calendar) {
        //Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Calendar calendar) {
        //Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }
    public static String getStringFromDate(Calendar calendar, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(calendar.getTime());
    }
    public static String getStringFromDate(Long time, String pattern) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return simpleDateFormat.format(calendar.getTime());
    }
}

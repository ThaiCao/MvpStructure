package com.mvp.structure.utils;

import android.app.AlarmManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by thai.cao on 8/1/2019.
 */
public class DateUtil {

    /**
     * Get the number of milliseconds in today's zero, zero, and zero seconds
     */
    public static long getDayStartTimeMillis() {
        return System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
    }


    /**
     * Get the number of milliseconds today at 23:59:59
     */
    public static long getDayEndTimeMillis() {
        return getDayStartTimeMillis() + 24 * 60 * 60 * 1000 - 1;
    }

    /**
     * Time string in yyyy-MM-dd HH:mm:ss format
     * Convert to timestamp
     */
    public static long timeToTimestamp(String dateTime) {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = -1;
        try {
            time = format2.parse(dateTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * Get the number of days between the current date and this Monday
     * @return
     */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // Getting today is the first day of the week, Sunday is the first day, and Tuesday is the second day...
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * Get the number of milliseconds in zero, zero, and zero on the first day of the week
     */
    public static long getWeekStartTimeMillis() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return timeToTimestamp(format.format(currentDate.getTime()) + " 00:00:00");
    }

    /**
     * Get the number of milliseconds at 23:59:59 on the last day of the week
     */
    public static long getWeekEndTimeMillis() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return timeToTimestamp(format.format(currentDate.getTime()) + " 23:59:59");
    }

    /**
     * Get the number of milliseconds in zero, zero, and zero seconds on the first day of the month
     */
    public static long getMondayStartTimeMillis() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return timeToTimestamp(format.format(cal.getTime()) + " 00:00:00");
    }

    /**
     * Get the number of milliseconds at 23:59:59 on the last day of the month
     */
    public static long getMondayEndTimeMillis() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return timeToTimestamp(format.format(cal.getTime()) + " 23:59:59");
    }

    /**
     * Time friendly display
     * just -%s minutes ago -%s hours ago - yesterday - the day before -%s days ago
     */
    public static String formatSomeAgo(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Calendar mCurrentDate = Calendar.getInstance();
        long crim = mCurrentDate.getTimeInMillis(); // current
        long trim = calendar.getTimeInMillis(); // target
        long diff = crim - trim;

        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH);
        int day = mCurrentDate.get(Calendar.DATE);

        if (diff < 60 * 1000) {
            return "just";
        }
        if (diff >= 60 * 1000 && diff < AlarmManager.INTERVAL_HOUR) {
            return String.format("%s minutes ago", diff / 60 / 1000);
        }
        mCurrentDate.set(year, month, day, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return String.format("%s hours ago", diff / AlarmManager.INTERVAL_HOUR);
        }
        mCurrentDate.set(year, month, day - 1, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "yesterday";
        }
        mCurrentDate.set(year, month, day - 2, 0, 0, 0);
        if (trim >= mCurrentDate.getTimeInMillis()) {
            return "The day before yesterday";
        }
        if (diff < AlarmManager.INTERVAL_DAY * 30) {
            return String.format("%s days ago", diff / AlarmManager.INTERVAL_DAY);
        }
        if (diff < AlarmManager.INTERVAL_DAY * 30 * 12) {
            return String.format("%s month ago", diff / (AlarmManager.INTERVAL_DAY * 30));
        }
        return String.format("%s years ago", mCurrentDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR));
    }

}

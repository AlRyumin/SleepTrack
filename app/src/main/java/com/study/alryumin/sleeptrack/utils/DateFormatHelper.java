package com.study.alryumin.sleeptrack.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateFormatHelper {
    public static String getDateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(date);
    }

    public static String getTimeDateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - yyyy.MM.dd");
        return dateFormat.format(date);
    }

    public static String getDateDayMonthFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        return dateFormat.format(date);
    }

    public static String getTimeFormat(Long time) {
        String sleepTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)));

        return sleepTime;
    }

    public static String getTimeDoubleFormat(Long time) {

        String sleepTime = String.format("%02d.%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)));

//        String sleepTime = "" + ((float) TimeUnit.MILLISECONDS.toMinutes(time) / 60);

        return sleepTime;
    }

    public static String getTimeFormat(Long time, boolean withSeconds) {
        String sleepTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));

        return sleepTime;
    }

    public static String getTimeFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        return dateFormat.format(date);
    }

    public static long timeToMilliseconds(int hour, int minute) {
        long lHour = hour * 60 * 60 * 1000;
        long lMinute = minute * 60 * 1000;

        return lHour + lMinute;
    }

    public static int getHour(String time) {
        try {
            String[] times = time.split(":");
            int hour = Integer.parseInt(times[0].trim());
            return hour;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getMinute(String time) {
        try {
            Log.d("getM", time);
            String[] times = time.split(":");
            for(String t : times){
                Log.d("timesdf:", t);
            }
            int minute = Integer.parseInt(times[1].trim());
            return minute;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getHour(Float time) {
        String timeString = Float.toString(time);
        try {
            String[] times = timeString.split("\\.");
            return Integer.parseInt(times[0].trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getMinute(Float time) {
        String timeString = Float.toString(time);
        try {
            String[] times = timeString.split("\\.");
            return Integer.parseInt(times[1].trim());
        } catch (Exception e) {
            return 0;
        }
    }

}

package com.study.alryumin.sleeptrack.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateFormatHelper {
    public static String getDateFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - yyyy.MM.dd");
        return dateFormat.format( date );
    }

    public static String getTimeFormat(Long time){
        String sleepTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)));

        return sleepTime;
    }

    public static String getTimeFormat(Long time, boolean withSeconds){
        String sleepTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));

        return sleepTime;
    }

}

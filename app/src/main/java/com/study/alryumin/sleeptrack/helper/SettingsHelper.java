package com.study.alryumin.sleeptrack.helper;

public class SettingsHelper {
    public static long timeToMilliseconds(int hour, int minute){
        long lHour = hour * 60 * 60 * 1000;
        long lMinute = minute * 60 * 1000;

        return lHour + lMinute;
    }

    public static int hourFromMilliseconds(long milliseconds){
        return (int) (milliseconds / 1000 / 60 / 60);
    }

    public static int minuteFromMilliseconds(long milliseconds){
        return (int) ((milliseconds / 1000 / 60) % 60);
    }
}

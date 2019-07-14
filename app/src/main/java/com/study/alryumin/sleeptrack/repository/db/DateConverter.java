package com.study.alryumin.sleeptrack.repository.db;

import androidx.room.TypeConverter;

import java.sql.Time;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Time toTime(Long time){
        return time == null ? null: new Time(time);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Long fromTime(Time time){
        return time == null ? null : time.getTime();
    }
}

package com.study.alryumin.sleeptrack.helper;

import android.util.Log;

import com.study.alryumin.sleeptrack.model.Target;
import com.study.alryumin.sleeptrack.repository.room.TargetDao;
import com.study.alryumin.sleeptrack.view.App;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class TargetHelper {
    private static TargetHelper instance;
    private Target target;
    private TargetDao targetDao;
    private DatabaseHelper database;

    private TargetHelper() {
        database = App.getInstance().getDatabase();
        targetDao = database.getTargetDao();
        target = targetDao.getLast();

        if(null == target){
            target = new Target();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(0));
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);

            Time time = new Time(calendar.getTimeInMillis());

            target.setStartAt(time);

            calendar.set(Calendar.HOUR,7);

            target.setFinishAt(new Time(calendar.getTimeInMillis()));

            target.setSleepTime(target.getFinishAt().getTime() - target.getStartAt().getTime());

            targetDao.add(target);
        }
    }

    public static TargetHelper getInstance(){
        if(null == instance){
            instance = new TargetHelper();
        }

        return instance;
    }

    public Target getTarget(){
        return target;
    }

    public void setTarget(Target target){
        targetDao.deleteAll();
        targetDao.add(target);
    }
}

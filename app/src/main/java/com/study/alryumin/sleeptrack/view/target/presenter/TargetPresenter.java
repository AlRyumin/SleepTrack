package com.study.alryumin.sleeptrack.view.target.presenter;

import android.util.Log;

import com.study.alryumin.sleeptrack.helper.TargetHelper;
import com.study.alryumin.sleeptrack.model.Target;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;
import com.study.alryumin.sleeptrack.view.target.contract.TargetContract;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class TargetPresenter implements TargetContract.Presenter {
    private static TargetPresenter instance;
    TargetHelper targetHelper;
    Target target, targetClone;

    private TargetPresenter(){
        targetHelper = TargetHelper.getInstance();
        target = targetHelper.getTarget();
        targetClone = new Target();
        replaceTarget(target, targetClone);
    }

    public static TargetPresenter getInstance(){
        if(null == instance){
            instance = new TargetPresenter();
        }

        return instance;
    }

    public Time getStartAt(){
        return targetClone.getStartAt();
    }

    public Time getFinishAt(){
        return targetClone.getFinishAt();
    }

    public Long getSleepTime(){
        return targetClone.getSleepTime();
    }

    public void setStartAt(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(0));
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);

        targetClone.setStartAt(new Time(calendar.getTimeInMillis()));
        countSleepTime();
    }

    public void setFinishAt(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(0));
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);

        targetClone.setFinishAt(new Time(calendar.getTimeInMillis()));
        countSleepTime();
    }

    public void setSleepTime(int hour, int minute){
        long sleepTime = DateFormatHelper.timeToMilliseconds(hour, minute);

        targetClone.setSleepTime(sleepTime);
        targetClone.setFinishAt(new Time(targetClone.getStartAt().getTime() + sleepTime));
    }

    public void resetTarget(){
        replaceTarget(target, targetClone);
    }

    public void saveTarget(){
        replaceTarget(targetClone, target);
        targetHelper.setTarget(targetClone);
    }

    public void countSleepTime(){
        Long time = targetClone.getFinishAt().getTime() - targetClone.getStartAt().getTime();

        if(time < 0l){
            Long dayNight = 24l * 60 * 60 * 1000;
            time = dayNight - (time * -1l);
        }

        targetClone.setSleepTime(time);
    }

    public void replaceTarget(Target target, Target target2){
        target2.setStartAt(target.getStartAt());
        target2.setFinishAt(target.getFinishAt());
        target2.setSleepTime(target.getSleepTime());
    }
}

package com.study.alryumin.sleeptrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class SleepTime {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private Date startAt, finishAt;
    private long sleepTime;

    public long getId() {
        return id;
    }

    public Date getStartAt() {
        return startAt;
    }

    public Date getFinishAt() {
        return finishAt;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public void setFinishAt(Date finishAt) {
        this.finishAt = finishAt;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getSleepTimeFormat(){
        String sleepTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(getSleepTime()),
                TimeUnit.MILLISECONDS.toMinutes(getSleepTime()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(getSleepTime())),
                TimeUnit.MILLISECONDS.toSeconds(getSleepTime()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getSleepTime())));

        return sleepTime;
    }

    public String toString(){
        return "Start: " + getStartAt() + " '\n Finish: " + getFinishAt() + " '\n Sleep: " + getSleepTimeFormat();
    }
}

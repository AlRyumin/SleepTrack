package com.study.alryumin.sleeptrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
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
        if(sleepTime == 0l){
            sleepTime = getFinishAt().getTime() - getStartAt().getTime();
        }
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

    public void copy(SleepTime sleepTime){
        sleepTime.setStartAt(this.getStartAt());
        sleepTime.setFinishAt(this.getFinishAt());
        sleepTime.setSleepTime(this.getSleepTime());
    }

    public String toString(){
        return "Start: " + getStartAt() + " '\n Finish: " + getFinishAt() + " '\n Sleep: " + getSleepTime();
    }
}

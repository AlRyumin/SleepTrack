package com.study.alryumin.sleeptrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class SleepTime {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private Date startAt, finishAt;
    private int sleepTime;

    public long getId() {
        return id;
    }

    public Date getStartAt() {
        return startAt;
    }

    public Date getFinishAt() {
        return finishAt;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public void setFinishAt(Date finishAt) {
        this.finishAt = finishAt;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }
}

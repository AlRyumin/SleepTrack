package com.study.alryumin.sleeptrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity
public class Target implements Cloneable{
    @PrimaryKey(autoGenerate = true)
    public long id;
    private Time startAt, finishAt;
    private long sleepTime;

    public long getId() { return id; }

    public Time getStartAt() { return startAt; }

    public Time getFinishAt() { return finishAt; }

    public long getSleepTime() { return sleepTime; }

    public void setStartAt(Time startAt) {
        this.startAt = startAt;
    }

    public void setFinishAt(Time finishAt) { this.finishAt = finishAt; }

    public void setSleepTime(long sleepTime) { this.sleepTime = sleepTime; }

    public String toString(){
        return "id: " + id + " start: " + startAt + " finish: " + finishAt + " sleep: " + sleepTime;
    }
}

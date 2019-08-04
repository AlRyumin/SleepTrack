package com.study.alryumin.sleeptrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AppSettings {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long minSleepTime;

    public long getId() {
        return id;
    }

    public Long getMinSleepTime() {
        if(minSleepTime == 0l){
            minSleepTime = 14400l * 1000;//4 hours in milliseconds;
        }
        return minSleepTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMinSleepTime(Long minSleepTime) {
        this.minSleepTime = minSleepTime;
    }
}

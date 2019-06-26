package com.study.alryumin.sleeptrack.model;

public class AppSettings {
    public Long minSleepTime;

    public AppSettings() {}

    public AppSettings(Long minSleepTime) {
        this.minSleepTime = minSleepTime;
    }


    public Long getMinSleepTime() {
        if(minSleepTime == null){
            minSleepTime = 14400l * 1000;//4 hours in milliseconds;
        }
        return minSleepTime;
    }

    public void setMinSleepTime(Long minSleepTime) {
        this.minSleepTime = minSleepTime;
    }
}

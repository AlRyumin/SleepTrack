package com.study.alryumin.sleeptrack.model;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class ActivityTrack {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public int processId;
    private String name;
    private Date startAt, finishAt;

    public ActivityTrack(String name, Date startAt) {
        this.name = name;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public int getProcessId() {
        return processId;
    }

    public String getName() {
        return name;
    }

    public Date getStartAt() {
        return startAt;
    }

    public Date getFinishAt() {
        return finishAt;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public void setFinishAt(Date finishAt) {
        this.finishAt = finishAt;
    }

    @NonNull
    @Override
    public String toString() {
        try {
            if (getFinishAt() != null) {
                return " Started at: " + getStartAt().toString() + "\n"
                        + " Finished at: " + getFinishAt().toString();
            } else {
                return " Started at: " + getStartAt().toString() + "\n"
                        + " Finished at: null";
            }
        } catch (Exception e) {
            Log.d("ToStringException",e.getMessage());
            return super.toString();
        }
    }

    public long getActivityTime() {
        return this.getFinishAt().getTime() - this.getStartAt().getTime();
    }
}

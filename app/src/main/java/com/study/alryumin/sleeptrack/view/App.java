package com.study.alryumin.sleeptrack.view;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;

public class App extends Application {

    private static App instance;
    private DatabaseHelper db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "activityTrack")
                .allowMainThreadQueries()
                .build();
    }

    public DatabaseHelper getDatabase() {
        return db;
    }
}
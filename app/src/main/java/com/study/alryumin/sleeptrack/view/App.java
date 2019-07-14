package com.study.alryumin.sleeptrack.view;

import android.app.Application;

import androidx.room.Room;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.repository.db.DatabaseMigration;

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
                .addMigrations(DatabaseMigration.MIGRATION_1_2)
                .addMigrations(DatabaseMigration.MIGRATION_2_3)
                .build();
    }

    public DatabaseHelper getDatabase() {
        return db;
    }
}
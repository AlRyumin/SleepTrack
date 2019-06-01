package com.study.alryumin.sleeptrack.helper;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.repository.db.DateConverter;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;
import com.study.alryumin.sleeptrack.repository.room.SleepTimeDao;

@Database(entities = { ActivityTrack.class, SleepTime.class }, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract ActivityTrackDao getActivityTrackDao();
    public abstract SleepTimeDao getSleepTimeDao();

}

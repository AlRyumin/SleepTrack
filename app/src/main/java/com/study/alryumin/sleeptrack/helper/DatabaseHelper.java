package com.study.alryumin.sleeptrack.helper;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.model.AppSettings;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.model.Target;
import com.study.alryumin.sleeptrack.repository.db.DateConverter;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;
import com.study.alryumin.sleeptrack.repository.room.AppSettingsDao;
import com.study.alryumin.sleeptrack.repository.room.SleepTimeDao;
import com.study.alryumin.sleeptrack.repository.room.TargetDao;

@Database(entities = { ActivityTrack.class, SleepTime.class, Target.class, AppSettings.class }, version = 4, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract ActivityTrackDao getActivityTrackDao();
    public abstract SleepTimeDao getSleepTimeDao();
    public abstract TargetDao getTargetDao();
    public abstract AppSettingsDao getAppSettingsDao();

}

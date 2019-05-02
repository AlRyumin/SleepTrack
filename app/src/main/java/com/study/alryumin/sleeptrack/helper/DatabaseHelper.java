package com.study.alryumin.sleeptrack.helper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.repository.db.DateConverter;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;

@Database(entities = { ActivityTrack.class }, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract ActivityTrackDao getActivityTrackDao();

}

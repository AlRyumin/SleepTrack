package com.study.alryumin.sleeptrack.repository.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.study.alryumin.sleeptrack.model.ActivityTrack;

import java.util.List;

@Dao
public interface ActivityTrackDao {
    @Query("SELECT * FROM activityTrack")
    List<ActivityTrack> getAll();

    @Query("SELECT * FROM activityTrack WHERE id = (SELECT MAX(id)  FROM activityTrack)")
    ActivityTrack getLast();

    @Query("SELECT * FROM activityTrack WHERE id = :id")
    ActivityTrack getById(int id);

    @Insert
    void add(ActivityTrack activityTrack);

    @Update
    void update(ActivityTrack activityTrack);

    @Delete
    void delete(ActivityTrack activityTrack);
}

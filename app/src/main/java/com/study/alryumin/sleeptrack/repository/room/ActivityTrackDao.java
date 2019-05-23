package com.study.alryumin.sleeptrack.repository.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM activityTrack WHERE startAt >= :startAt AND finishAt <= :finishAt")
    List<ActivityTrack> getByDate(Long startAt, Long finishAt);

    @Insert
    void add(ActivityTrack activityTrack);

    @Update
    void update(ActivityTrack activityTrack);

    @Delete
    void delete(ActivityTrack activityTrack);
}

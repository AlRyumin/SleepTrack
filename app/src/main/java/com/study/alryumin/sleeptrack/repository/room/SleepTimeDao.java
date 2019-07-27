package com.study.alryumin.sleeptrack.repository.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.study.alryumin.sleeptrack.model.SleepTime;

import java.util.List;

@Dao
public interface SleepTimeDao {
    @Query("SELECT * FROM sleepTime")
    List<SleepTime> getAll();

    @Query("SELECT * FROM sleepTime ORDER BY finishAt")
    List<SleepTime> getAllByDate();

    @Query("SELECT * FROM sleepTime WHERE id = (SELECT MAX(id)  FROM sleepTime)")
    SleepTime getLast();

    @Query("SELECT * FROM sleepTime WHERE id = :id")
    SleepTime getById(long id);

    @Query("SELECT * FROM sleepTime WHERE startAt >= :startAt AND finishAt <= :finishAt")
    List<SleepTime> getByDate(Long startAt, Long finishAt);

    @Insert
    void add(SleepTime sleepTime);

    @Update
    void update(SleepTime sleepTime);

    @Delete
    void delete(SleepTime sleepTime);

    @Query("DELETE FROM sleepTime WHERE 1")
    void deleteAll();
}

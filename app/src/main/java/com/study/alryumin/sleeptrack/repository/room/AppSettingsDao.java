package com.study.alryumin.sleeptrack.repository.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.study.alryumin.sleeptrack.model.AppSettings;

@Dao
public interface AppSettingsDao {
    @Query("SELECT * FROM appSettings WHERE 1 LIMIT 1")
    AppSettings getLast();

    @Insert
    void add(AppSettings appSettings);

    @Update
    void update(AppSettings appSettings);

    @Delete
    void delete(AppSettings appSettings);

    @Query("DELETE FROM appSettings WHERE 1")
    void deleteAll();
}

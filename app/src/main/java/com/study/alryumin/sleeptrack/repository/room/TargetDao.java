package com.study.alryumin.sleeptrack.repository.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.study.alryumin.sleeptrack.model.Target;

import java.util.List;

@Dao
public interface TargetDao {
    @Query("SELECT * FROM target")
    List<Target> getAll();

    @Query("SELECT * FROM Target WHERE id = (SELECT MAX(id)  FROM target)")
    Target getLast();

    @Query("SELECT * FROM target WHERE id = :id")
    Target getById(int id);

    @Insert
    void add(Target target);

    @Update
    void update(Target target);

    @Delete
    void delete(Target target);

    @Query("DELETE FROM target WHERE 1")
    void deleteAll();
}

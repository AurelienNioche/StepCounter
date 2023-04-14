package com.aureliennioche.stepcounterplugin;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface StepDao {
    @Query("SELECT * FROM steprecord ORDER BY date DESC")
    List<StepRecord> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStepRecord(StepRecord stepRecord);

    @Query("DELETE FROM steprecord")
    void nukeTable();
}

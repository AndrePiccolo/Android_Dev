package com.example.myhealthhelper;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDAO {

    @Query("SELECT * FROM exercise_data")
    List<ExerciseData> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addExcerciseData(ExerciseData exercise);
}

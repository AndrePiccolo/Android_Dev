package com.example.myhealthhelper;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ExerciseData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExerciseDAO exerciseDao();
}

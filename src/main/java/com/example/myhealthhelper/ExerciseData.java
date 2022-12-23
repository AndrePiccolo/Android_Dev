package com.example.myhealthhelper;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_data")
public class ExerciseData {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "total_time")
    String time;
    @ColumnInfo(name = "total_distance")
    String distance;
    @ColumnInfo(name = "total_calories")
    String calories;
    @ColumnInfo(name = "total_steps")
    String steps;
    @ColumnInfo(name = "total_walking")
    String walking;
    @ColumnInfo(name = "total_running")
    String running;
    @ColumnInfo(name = "date")
    String date;

    ExerciseData(String time, String distance, String calories, String steps,
                 String walking, String running, String date)
    {
        this.time = time;
        this.distance = distance;
        this.calories = calories;
        this.steps = steps;
        this.walking = walking;
        this.running = running;
        this.date = date;
    }
}

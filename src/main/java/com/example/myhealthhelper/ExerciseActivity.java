package com.example.myhealthhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity {

    Button btnStart, btnStop, btnFinish, btnRun, btnWalk;
    TextView  txtCurrentSteps, txtCurrentKM, txtCurrentCalories;
    Chronometer chrCurrentTime, chrWalking, chrRunning;
    long lastPauseWalking, lastPauseRunning, lastPauseTime;
    boolean initApp = true;
    boolean isWalking = true;
    Integer steps = 0;
    static final Double WALKING_STEPS = 1200.0;
    static final Double CALORIES_PER_KM = 60.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ExerciseDB").allowMainThreadQueries().build();

        btnStart = findViewById(R.id.btnRestart);
        btnStop = findViewById(R.id.btnStop);
        btnFinish = findViewById(R.id.btnFinish);
        btnRun = findViewById(R.id.btnRunning);
        btnWalk = findViewById(R.id.btnWalking);

        txtCurrentSteps = findViewById(R.id.txtCurrentSteps);
        txtCurrentKM = findViewById(R.id.txtCurrentKM);
        txtCurrentCalories = findViewById(R.id.txtCalories);

        chrCurrentTime = findViewById(R.id.chrCurrentTime);
        chrWalking = findViewById(R.id.chrWalkingTime);
        chrRunning = findViewById(R.id.crhRunningTime);

        chrCurrentTime.setBase(SystemClock.elapsedRealtime());
        chrWalking.setBase(SystemClock.elapsedRealtime());

        chrCurrentTime.start();
        chrWalking.start();
        btnWalk.setEnabled(false);
        btnStart.setEnabled(false);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
                String today = df.format(c);

                ExerciseData includeData = new ExerciseData(
                        chrCurrentTime.getText().toString(),
                        txtCurrentKM.getText().toString(),
                        txtCurrentCalories.getText().toString(),
                        txtCurrentSteps.getText().toString(),
                        chrWalking.getText().toString(),
                        chrRunning.getText().toString(),
                        today
                );

                ExerciseDAO exerciseDao = db.exerciseDao();
                exerciseDao.addExcerciseData(includeData);

                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home);
            }
        });

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWalking = false;
                btnWalk.setEnabled(true);
                btnRun.setEnabled(false);
                if(initApp){
                    chrRunning.setBase(SystemClock.elapsedRealtime());
                    initApp = false;
                }else{
                    chrRunning.setBase(chrRunning.getBase() + SystemClock.elapsedRealtime() - lastPauseRunning);
                }
                chrRunning.start();
                lastPauseWalking = SystemClock.elapsedRealtime();
                chrWalking.stop();
            }
        });

        btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWalking = true;
                btnWalk.setEnabled(false);
                btnRun.setEnabled(true);
                lastPauseRunning = SystemClock.elapsedRealtime();
                chrRunning.stop();
                chrWalking.setBase(chrWalking.getBase() + SystemClock.elapsedRealtime() - lastPauseWalking);
                chrWalking.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWalking){
                    btnRun.setEnabled(false);
                    lastPauseWalking = SystemClock.elapsedRealtime();
                    chrWalking.stop();
                }else{
                    btnWalk.setEnabled(false);
                    lastPauseRunning = SystemClock.elapsedRealtime();
                    chrRunning.stop();
                }

                lastPauseTime = SystemClock.elapsedRealtime();
                chrCurrentTime.stop();
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWalking){
                    btnRun.setEnabled(true);
                    chrWalking.setBase(chrWalking.getBase() + SystemClock.elapsedRealtime() - lastPauseWalking);
                    chrWalking.start();
                }else{
                    btnWalk.setEnabled(true);
                    chrRunning.setBase(chrRunning.getBase() + SystemClock.elapsedRealtime() - lastPauseRunning);
                    chrRunning.start();
                }
                chrCurrentTime.setBase(chrCurrentTime.getBase() + SystemClock.elapsedRealtime() - lastPauseTime);
                chrCurrentTime.start();
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
            }
        });

        chrCurrentTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(isWalking){
                    steps++;
                }else{
                    steps = steps + 2;
                }
                txtCurrentSteps.setText((steps).toString());
                txtCurrentKM.setText(String.format("%.3f",steps/WALKING_STEPS));
                txtCurrentCalories.setText(
                        String.format("%.2f",((steps/WALKING_STEPS) * CALORIES_PER_KM)));
            }
        });
    }
}
package com.example.myhealthhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.Collections;
import java.util.List;

public class AchievementActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ExerciseDB").allowMainThreadQueries().build();

        navigationView = findViewById(R.id.nav_view_achiev);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.achieveLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ExerciseDAO exerciseDao = db.exerciseDao();
        List<ExerciseData> exerciseDataList = exerciseDao.getAll();

        Collections.reverse(exerciseDataList);

        CustomAdapter customAdapter = new CustomAdapter(exerciseDataList);
        recyclerView = findViewById(R.id.rvItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home);
                break;
            case R.id.nav_achievement:
                break;
            case R.id.nav_profile:
                Intent profile = new Intent(getApplicationContext(), MainActivity.class);
                profile.putExtra("update", "update");
                startActivity(profile);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
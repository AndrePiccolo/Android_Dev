package com.example.myhealthhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button btnStart;
    TextView txtHomeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sh = getSharedPreferences("ProfileSharedPref", MODE_PRIVATE);

        btnStart = findViewById(R.id.btnStart);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtHomeName = findViewById(R.id.txtHomeName);
        txtHomeName.setText("Welcome " + sh.getString("user_name", "User") + "!");

        drawerLayout = findViewById(R.id.homeLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exercise = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(exercise);
            }
        });
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
                break;
            case R.id.nav_achievement:
                Intent achievement = new Intent(getApplicationContext(), AchievementActivity.class);
                startActivity(achievement);
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
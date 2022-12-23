package com.example.myhealthhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner exerciseActive;
    Button btnRegister;
    TextView txtName, txtHeight, txtWeight, txtGoalSteps;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    DatePicker dob;
    String activeOption;
    Integer activeOptionPosition;
    String sexOption;
    String fromUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sh = getSharedPreferences("ProfileSharedPref", MODE_PRIVATE);

        exerciseActive = findViewById(R.id.spExercise);
        btnRegister = findViewById(R.id.btnRegister);
        txtName = findViewById(R.id.txtName);
        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        txtGoalSteps = findViewById(R.id.txtGoalSteps);
        radioGroup = findViewById(R.id.rgSex);
        dob = findViewById(R.id.dpDob);

        txtName.setText(sh.getString("user_name", ""));
        if(getIntent().getExtras() != null){
            fromUpdate = getIntent().getExtras().getString("update");
            if(!txtName.getText().toString().isEmpty() && fromUpdate.isEmpty()){
                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home);
            }
        }else{
            if(!txtName.getText().toString().isEmpty()){
                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home);
            }
        }

        txtHeight.setText(sh.getString("user_height", ""));
        txtWeight.setText(sh.getString("user_weight", ""));
        txtGoalSteps.setText(sh.getString("user_goal", ""));
        dob.updateDate(sh.getInt("user_dob_year", 2022),
                sh.getInt("user_dob_month", 10),
                sh.getInt("user_dob_day", 10));

        sexOption = sh.getString("user_sex", "Male");
        if("male".equalsIgnoreCase(sexOption)){
            selectedRadioButton = findViewById(R.id.rbMale);
        }else if("female".equalsIgnoreCase(sexOption)){
            selectedRadioButton = findViewById(R.id.rbFemale);
        }else{
            selectedRadioButton = findViewById(R.id.rbOther);
        }
        selectedRadioButton.setChecked(true);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    selectedRadioButton = findViewById(selectedRadioButtonId);
                } else {
                    selectedRadioButton = findViewById(R.id.rbMale);
                }
                sexOption = selectedRadioButton.getText().toString();

                saveInformationSharedPreference(sh, txtName.getText().toString(), activeOptionPosition,
                        sexOption, dob.getYear(),dob.getDayOfMonth(), dob.getMonth(),
                        txtHeight.getText().toString(), txtWeight.getText().toString(),
                        txtGoalSteps.getText().toString());

                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercise_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseActive.setAdapter(adapter);

        exerciseActive.setSelection(sh.getInt("user_active", 0));

        exerciseActive.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        activeOption = getResources().getStringArray(R.array.exercise_array)[position];
        activeOptionPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void saveInformationSharedPreference(SharedPreferences sh, String userName, int userActive,
                                                 String userSex, int userDOBYear, int userDOBMonth,
                                                 int userDOBDay, String userHeight, String userWeight,
                                                 String userGoal){
        SharedPreferences.Editor editor = sh.edit();

        editor.putString("user_name", userName);
        editor.putInt("user_active", userActive);
        editor.putString("user_sex", userSex);
        editor.putInt("user_dob_year", userDOBYear);
        editor.putInt("user_dob_month", userDOBMonth);
        editor.putInt("user_dob_day", userDOBDay);
        editor.putString("user_height", userHeight);
        editor.putString("user_weight", userWeight);
        editor.putString("user_goal", userGoal);

        editor.commit();
    }
}
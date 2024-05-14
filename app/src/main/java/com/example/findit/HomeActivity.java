package com.example.findit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        String dynamicString = "Home";
        toolbarTitle.setText(dynamicString);

        Button changeNumberButton = findViewById(R.id.btnChangeNum);
        changeNumberButton.setOnClickListener(v -> {
            // Your code here
            startActivity(new Intent(HomeActivity.this, AddPhoneNumberActivity.class));
        });

        SwitchMaterial locationSwitch = findViewById(R.id.switchLocation);
        locationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Your code here
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isChecked){
                // The switch is ON
                editor.putBoolean("location", true);
                editor.apply();

            }else{
                // The switch is OFF
                editor.putBoolean("location", false);
                editor.apply();
            }
        });

        SwitchMaterial ringSwitch = findViewById(R.id.switchRing);
        ringSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Your code here
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isChecked){
                // The switch is ON
                editor.putBoolean("ring", true);
                editor.apply();
            }else{
                // The switch is OFF
                editor.putBoolean("ring", false);
                editor.apply();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
        boolean isRingOn = sharedPreferences.getBoolean("ring", false);
        boolean isLocationOn = sharedPreferences.getBoolean("location", false);
        ringSwitch.setChecked(isRingOn);
        locationSwitch.setChecked(isLocationOn);

    }
}
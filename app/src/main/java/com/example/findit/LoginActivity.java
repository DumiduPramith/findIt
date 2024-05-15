package com.example.findit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edUsername = findViewById(R.id.editTextRegEmail);
        EditText edPassword = findViewById(R.id.editTextRegPassword);
        Button regBtn = findViewById(R.id.btnRegister);
        TextView registerUser = findViewById(R.id.textViewRegister);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
        if (sharedPreferences.contains("username")) {
            edUsername.setText(sharedPreferences.getString("username", ""));
        }

        regBtn.setOnClickListener(v -> {
            String username = edUsername.getText().toString();
            String password = edPassword.getText().toString();
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext(), "findit", null, 1);
            if (username.length() == 0 || password.length() == 0) {
                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                if (dbHelper.login(username, password) == 1) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("username", username);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    if (sharedPreferences.contains("phone")) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(LoginActivity.this, AddPhoneNumberActivity.class));
                    }
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerUser.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext(), "findit", null, 1);
            if (db.checkRegistered()) {
                Toast.makeText(getApplicationContext(), "Already Registered", Toast.LENGTH_SHORT).show();
            } else {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}
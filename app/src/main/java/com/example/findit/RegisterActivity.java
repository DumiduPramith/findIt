package com.example.findit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText edUsername = findViewById(R.id.editTextRegusername);
        EditText edEmail = findViewById(R.id.editTextRegEmail);
        EditText edPassword = findViewById(R.id.editTextRegPassword);
        EditText edConfirmPassword = findViewById(R.id.editTextRegconfPassword);
        Button regBtn = findViewById(R.id.btnRegister);
        TextView existingUser = findViewById(R.id.textViewRegister);

        regBtn.setOnClickListener(v -> {
            String username = edUsername.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            String confPassword = edConfirmPassword.getText().toString();

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext(), "findit", null, 1);

            if (username.length() == 0 || email.length() == 0 || password.length() == 0 || confPassword.length() == 0)
                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            else if (!email.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confPassword))
                Toast.makeText(getApplicationContext(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
            else {
                dbHelper.register(username, email, password);
                Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        existingUser.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

    }
}
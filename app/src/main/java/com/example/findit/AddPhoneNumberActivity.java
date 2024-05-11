package com.example.findit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddPhoneNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        String dynamicString = "Add Phone Number";
        toolbarTitle.setText(dynamicString);

        EditText phoneNumberEditText = findViewById(R.id.editTextPhoneNumber);
        Button saveButton = findViewById(R.id.btnSave);

        saveButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.isEmpty() || phoneNumber.length() != 10){
                Toast.makeText(getApplicationContext(),"Phone Number not valid",Toast.LENGTH_SHORT).show();

            }else{
                // Your code here
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("phone", phoneNumber);
                editor.apply();
                Toast.makeText(getApplicationContext(),"Phone Number saved",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddPhoneNumberActivity.this, HomeActivity.class));
                finish();
            }
            // Your code here
        });

    }
}
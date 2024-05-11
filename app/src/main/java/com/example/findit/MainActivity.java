package com.example.findit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION_CODE);
        }else{
            this.runActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                this.runActivity();
            } else {
                // Permission denied
                Toast.makeText(this, "This app needs SMS permission to work properly", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        }
    }

    public void runActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your code here
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                if (sharedPreferences.contains("phone")) {
                    // The key exists in SharedPreferences
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    // The key does not exist in SharedPreferences
                    startActivity(new Intent(MainActivity.this, AddPhoneNumberActivity.class));
                }
                finish();
            }
        }, 3000);
    }
}
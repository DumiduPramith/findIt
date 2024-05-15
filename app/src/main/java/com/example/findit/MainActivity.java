package com.example.findit;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int SMS_RECEIVE_PERMISSION_CODE = 0;
    private static final int SMS_SEND_PERMISSION_CODE = 1;
    private static final int LOCATION_PERMISSION_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }

        // Request RECEIVE_SMS permission
        requestPermission(Manifest.permission.RECEIVE_SMS, SMS_RECEIVE_PERMISSION_CODE);

        // Request SEND_SMS permission
//        requestPermission(Manifest.permission.SEND_SMS, SMS_SEND_PERMISSION_CODE);

        // Request ACCESS_FINE_LOCATION permission
//        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE);
    }

    private void requestPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        } else {
            handlePermissionGranted(requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handlePermissionGranted(requestCode);
        } else {
            Toast.makeText(this, "Need to Grant " + getPermissionName(requestCode), Toast.LENGTH_LONG).show();
            new Handler().postDelayed(() -> finish(), 2000);
        }
    }

    private String getPermissionName(int requestCode) {
        switch (requestCode) {
            case SMS_RECEIVE_PERMISSION_CODE:
                return "RECEIVE_SMS";
            case SMS_SEND_PERMISSION_CODE:
                return "SEND_SMS";
            case LOCATION_PERMISSION_CODE:
                return "ACCESS LOCATION";
            default:
                return "Unknown";
        }
    }

    private void handlePermissionGranted(int requestCode) {
        switch (requestCode) {
            case SMS_RECEIVE_PERMISSION_CODE:
                // After RECEIVE_SMS permission is granted, request SEND_SMS permission
                requestPermission(Manifest.permission.SEND_SMS, SMS_SEND_PERMISSION_CODE);
                break;
            case SMS_SEND_PERMISSION_CODE:
                // After SEND_SMS permission is granted, request ACCESS_FINE_LOCATION permission
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE);
                break;
            case LOCATION_PERMISSION_CODE:
                // After all permissions are granted, run the activity
                runActivity();
                break;
        }
    }

    public void runActivity() {
        new Handler().postDelayed(() -> {
//            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
//            if (sharedPreferences.contains("phone")) {
//                startActivity(new Intent(MainActivity.this, HomeActivity.class));
//            } else {
//                startActivity(new Intent(MainActivity.this, AddPhoneNumberActivity.class));
//            }
//            finish();
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }, 3000);
    }
}
package Core;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context.checkCallingOrSelfPermission("android.permission.BROADCAST_SMS") == PackageManager.PERMISSION_GRANTED) {
            // Your existing code
            if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    String messageBody = smsMessage.getMessageBody();
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    if (messageBody.toLowerCase().contains("track")) {
                        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                            if (locationManager != null) {
                                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                                    @Override
                                    public void onLocationChanged(@NonNull Location location) {
                                        // get the latitude and longitude
                                        double latitude = location.getLatitude();
                                        double longitude = location.getLongitude();

                                        // Create a message with the latitude and longitude
                                        String message = "http://maps.google.com/maps?q=" + latitude + "," + longitude;

                                        // Create a Data object with the sender and message
                                        Data data = new Data.Builder()
                                                .putString("sender", sender)
                                                .putString("message", message)
                                                .build();

                                        // Create a OneTimeWorkRequest with the Data object
                                        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                                                .setInputData(data)
                                                .build();

                                        // Enqueue the work request
                                        WorkManager.getInstance(context).enqueue(workRequest);

                                        // Stop requesting location updates
                                        locationManager.removeUpdates(this);
                                    }

                                    @Override
                                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                                    @Override
                                    public void onProviderEnabled(String provider) {}

                                    @Override
                                    public void onProviderDisabled(String provider) {}
                                }, null);
                            }
                        }
                    } else if (messageBody.toLowerCase().contains("ring")) {
                        Toast.makeText(context, "Ringing the phone", Toast.LENGTH_LONG).show();
                        try {
                            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            if (audioManager != null && notificationManager != null && notificationManager.isNotificationPolicyAccessGranted()) {
                                Toast.makeText(context, "Ringer mode is set to vibrate", Toast.LENGTH_LONG).show();
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
                            }

                            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                            Ringtone ringtone = RingtoneManager.getRingtone(context, ringtoneUri);
                            ringtone.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            // Log a warning or throw a SecurityException
        }

    }
}
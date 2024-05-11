package Core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                String sender = smsMessage.getDisplayOriginatingAddress();
                // Do what you want with the message
                if (messageBody.toLowerCase().contains("track")) {
                    // Do what you want with the message
                    Data data = new Data.Builder()
                            .putString("sender", sender)
                            .putString("message", "Location is being tracked")
                            .build();

                    OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                            .setInputData(data)
                            .build();

                    WorkManager.getInstance(context).enqueue(workRequest);
                }
            }
        }
    }
}
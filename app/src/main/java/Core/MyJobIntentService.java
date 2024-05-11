package Core;

import android.content.Context;
import android.content.Intent;
import androidx.core.app.JobIntentService;

public class MyJobIntentService extends JobIntentService {
    private static final int JOB_ID = 0;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        String sender = intent.getStringExtra("sender");
        String message = intent.getStringExtra("message");
        SmsSend smsSend = new SmsSend();
        smsSend.sendMessage(sender, message);
    }
}
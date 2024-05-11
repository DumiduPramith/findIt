package Core;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    private static final String TAG = "MyWorker";
    public MyWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        Log.d(TAG, "doWork called");
        Data inputData = getInputData();
        String sender = inputData.getString("sender");
        String message = inputData.getString("message");
        SmsSend smsSend = new SmsSend();
        smsSend.sendMessage(sender, message);
        Log.d(TAG, "Message sent to: " + sender);
        return Result.success();
    }
}
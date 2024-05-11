package Core;

import android.telephony.SmsManager;

public class SmsSend {
    public void sendMessage(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}

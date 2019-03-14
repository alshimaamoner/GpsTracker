package com.route.gpstrackerg2.OneSignal;

import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 3/7/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class MyNotificationsRecievedHandler implements OneSignal.NotificationReceivedHandler {


    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
        String customKey;

        if (data != null) {
            customKey = data.optString("type", null);
            if (customKey != null)
                Log.e("OneSignalExample", "type set with value: " + customKey);
        }
    }
}

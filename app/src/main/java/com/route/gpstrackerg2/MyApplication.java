package com.route.gpstrackerg2;

import android.app.Application;

import com.onesignal.OneSignal;
import com.route.gpstrackerg2.OneSignal.MyNotificationOpenedHandler;
import com.route.gpstrackerg2.OneSignal.MyNotificationsRecievedHandler;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 3/7/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .setNotificationReceivedHandler(new MyNotificationsRecievedHandler())
                .init();

    }
}

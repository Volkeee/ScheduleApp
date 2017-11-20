package com.ifntuog.volkeee.schedule;

import android.app.Application;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by volkeee on 10/9/17.
 */

public class ScheduleIFNTUOG extends Application {

    public ScheduleIFNTUOG() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Toast.makeText(getApplicationContext(), "FB INITIALIZED", Toast.LENGTH_LONG).show();
    }
}

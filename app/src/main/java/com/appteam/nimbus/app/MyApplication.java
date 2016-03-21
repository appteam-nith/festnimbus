package com.appteam.nimbus.app;

import android.app.Application;
import android.content.Context;

import com.appteam.nimbus.helper.MyPreferenceManager;


public class MyApplication extends Application {
    private static MyApplication myApplication ;
    private MyPreferenceManager pref;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }
    public static synchronized MyApplication getInstance() {
        return myApplication;
    }
    public static Context getAppContext(){
        return myApplication.getApplicationContext();
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }
}

package com.appteam.nimbus;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {
    private static MyApplication myApplication=null;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }
    public static Context getAppContext(){
        return myApplication.getApplicationContext();
    }
}

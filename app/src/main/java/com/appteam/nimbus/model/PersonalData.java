package com.appteam.nimbus.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lenovo on 1/23/2016.
 */
public class PersonalData {
    private static final String TOKEN = "token";
    private static final String TOKEN_GCM = "token gcm";
    private static final String EMAIL = "email";
    private static final String ROLLNO = "rollno";
    private static final String PHONENO = "phone";
    private final String STATUS = "STATUS";
    private Context context;
    private SharedPreferences.Editor editor = null;

    public PersonalData(Context context) {
        this.context = context;
        editor = getEditor();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    public void SaveData(boolean status) {
        editor.putBoolean(STATUS, status);
        editor.commit();
    }

    public void SaveToken(String token) {
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public void SaveDetail(String email, String rollno, String phoneno) {
        editor.putString(EMAIL, email);
        editor.putString(ROLLNO, rollno);
        editor.putString(PHONENO, phoneno);
        editor.commit();
    }

    public boolean getStatus() {
        return getSharedPreferences().getBoolean(STATUS, false);

    }

    public String getToken() {
        return getSharedPreferences().getString(TOKEN, "no token");
    }

    public void SaveGCMToken(String token) {
        editor.putString(TOKEN_GCM, token);
        editor.commit();
    }

    public String getPHONENO() {
        return getSharedPreferences().getString(PHONENO, "");
    }

    public String getEMAIL() {
        return getSharedPreferences().getString(EMAIL, "");
    }

    public String getROLLNO() {
        return getSharedPreferences().getString(ROLLNO, "");
    }

    public String getGCCMtoken() {
        return getSharedPreferences().getString(TOKEN_GCM, "no token");
    }
}

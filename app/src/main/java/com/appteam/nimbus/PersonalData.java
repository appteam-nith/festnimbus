package com.appteam.nimbus;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lenovo on 1/23/2016.
 */
public class PersonalData {
    private  final String NODATA ="N/A" ;
    private  final String NAME = "name";
    private  final String EMAIL = "email";
    private  final String URL = "url";
    private  final String STATUS="STATUS";
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

    public void SaveData(boolean status,String name, String email, String url) {
        editor.putBoolean(STATUS,status);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(URL, url);
        editor.commit();
    }

    public String getNAME() {
        return getSharedPreferences().getString(NAME,NODATA);
    }

    public String getEMAIL() {
        return getSharedPreferences().getString(EMAIL,NODATA);
    }

    public String getURL() {
        return getSharedPreferences().getString(URL,NODATA);
    }
    public boolean getStatus(){
        return getSharedPreferences().getBoolean(STATUS,false);
    }
}

package com.appteam.nimbus.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

private static final String DB_NAME = "mydb";
private static final int DB_VERSION = 1;

public static final String TABLE_NAME = "Notification";
public static final String COL_NAME = "Message";
private static final String STRING_CREATE = "CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        +COL_NAME+" TEXT);";

public MyDbHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
}

@Override
public void onCreate(SQLiteDatabase db) {
    db.execSQL(STRING_CREATE);
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    onCreate(db);
}
    public void insertData(String data){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_NAME,data);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
    }
    public ArrayList<String> readData(){
        ArrayList<String> list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
              list.add(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            }
            while (cursor.moveToNext());
        }
        return list;
    }
}


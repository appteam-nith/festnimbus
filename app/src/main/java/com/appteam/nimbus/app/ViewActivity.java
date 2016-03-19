package com.appteam.nimbus.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appteam.nimbus.R;
import com.appteam.nimbus.adapters.NotificationAdapter;

import java.util.ArrayList;

public class ViewActivity extends Activity {


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.notification);
   RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view_notification);
    MyDbHelper dbHelper=new MyDbHelper(this);
    ArrayList<String> list=dbHelper.readData();
    NotificationAdapter adapter=new NotificationAdapter(list);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
}

}
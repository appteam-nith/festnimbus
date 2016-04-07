package com.appteam.nimbus.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.appteam.nimbus.R;
import com.appteam.nimbus.adapters.NotificationAdapter;
import com.appteam.nimbus.model.NotificationItem;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Notifications");

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view_notification);
        MyDbHelper dbHelper=new MyDbHelper(this);
        ArrayList<NotificationItem> list=dbHelper.readData();
        NotificationAdapter adapter=new NotificationAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
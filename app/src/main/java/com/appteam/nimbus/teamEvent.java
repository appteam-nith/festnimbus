package com.appteam.nimbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class teamEvent extends AppCompatActivity {
    private static final String KEY_NAME_TEAM = "Detail";
    private RecyclerView recyclerView;
    private adapter_department adapter;
    private ArrayList<Item_department> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = setData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_department);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new adapter_department(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i=new Intent(teamEvent.this, Event.class);
                i.putExtra(KEY_NAME_TEAM,list.get(position).name);
                startActivity(i);
            }
        }));
    }
    private ArrayList<Item_department> setData() {
        ArrayList<Item_department> itemlist = new ArrayList<>();
        itemlist.add(new Item_department("ECE", R.drawable.placeholder_image));
        itemlist.add(new Item_department("ELECTRICAL", R.drawable.placeholder_image));
        itemlist.add(new Item_department("CIVIL", R.drawable.placeholder_image));
        itemlist.add(new Item_department("MECHANICAL", R.drawable.placeholder_image));
        itemlist.add(new Item_department("CSE", R.drawable.placeholder_image));
        itemlist.add(new Item_department("CHEMICAL", R.drawable.placeholder_image));
        return itemlist;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.close_main, R.anim.close_next);
    }
}

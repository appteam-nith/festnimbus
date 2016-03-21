package com.appteam.nimbus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.appteam.nimbus.R;
import com.appteam.nimbus.adapters.EventsAdapter;
import com.appteam.nimbus.model.EventClass;
import com.appteam.nimbus.model.TeamClass;

import java.util.ArrayList;

/**
 * Created by sukhbir on 21/2/16.
 */
public class EventActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    EventsAdapter adapter;
    private ArrayList<TeamClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        // uses same layout as that of core team

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView= (RecyclerView) findViewById(R.id.list_core_team);
        list=new ArrayList<>();
        TeamClass team1=new TeamClass();
        team1.setTeamname("Coming Soon");
        EventClass event1=new EventClass("56cb1c03ed77fec80e88ef71","Coming Soon","A Nice Event","");
        EventClass event2=new EventClass("56cb1c03ed77fec80e88ef71","Coming Soon","A Nice Event","");

        ArrayList<EventClass> events=new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event1);
        events.add(event2);
        events.add(event1);
        events.add(event2);
        events.add(event1);
        events.add(event2);

        team1.setEvents(events);

        list.add(team1);
        list.add(team1);
        list.add(team1);
        list.add(team1);
        list.add(team1);
        list.add(team1);
        list.add(team1);
        list.add(team1);

        adapter=new EventsAdapter(list,EventActivity.this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.close_main, R.anim.close_next);
    }
}

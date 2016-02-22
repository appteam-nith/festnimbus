package com.appteam.nimbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Event extends AppCompatActivity {
    private static final String KEY_NAME_TEAM ="Detail" ;
    private static final String TITLE ="asdf" ;
    private String team_name;
    private  event_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        if(savedInstanceState==null) {
            if (intent != null) {
                if (intent.hasExtra(KEY_NAME_TEAM))
                    team_name = intent.getStringExtra(KEY_NAME_TEAM).toLowerCase();
                    doWork(team_name);
            }
        }
        else {
            team_name=savedInstanceState.getString(TITLE);
            doWork(team_name);
        }

    }

private  void doWork(final String team_name){
    getSupportActionBar().setTitle(team_name.toUpperCase());
    View view=findViewById(R.id.content_event);
    TextView title= (TextView) view.findViewById(R.id.title_team_event);
    title.setText(team_name.toUpperCase());

    RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.event_recyclerview);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    int id=getResources().getIdentifier(team_name+"_event","array",this.getPackageName());
    final String list[]=getResources().getStringArray(id);
    adapter=new event_adapter(this,list);
    recyclerView.setAdapter(adapter);
    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(Event.this, EventDetail.class);
            intent.putExtra(KEY_NAME_TEAM,list[position]);
            startActivity(intent);
        }
    }));
}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE,team_name);
    }
}

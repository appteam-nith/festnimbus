package com.appteam.nimbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class EventDetail extends AppCompatActivity {

    private static final String KEY_NAME_TEAM ="Detail" ;
    private static final String TITLE ="tii" ;
    private String team_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState==null){
            Intent i=getIntent();
            if(i!=null){
                if(i.hasExtra(KEY_NAME_TEAM)){
                    team_name=i.getStringExtra(KEY_NAME_TEAM).toLowerCase();
                    getSupportActionBar().setTitle(team_name.toUpperCase());
                   doWork(team_name);
                }
            }
        }
        else{
            team_name=savedInstanceState.getString(TITLE);
            getSupportActionBar().setTitle(team_name.toUpperCase());
           doWork(team_name);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE,team_name);
    }
    private void doWork(final String team_name){
        View teamdetail = findViewById(R.id.team_detail);
        TextView description = (TextView) teamdetail.findViewById(R.id.team_detail_description);
        int id = getResources().getIdentifier(team_name.replace(" ","").toUpperCase()+"_event", "string",EventDetail.this.getPackageName());
        String content = getResources().getString(id);
        description.setText(content);

        View contact_detail = findViewById(R.id.contact_detail);
        TextView contact_description = (TextView) contact_detail.findViewById(R.id.contact_detail_description);
        int id1 = getResources().getIdentifier(team_name.replace(" ","").toUpperCase()+ "_contact", "string", EventDetail.this.getPackageName());
        String content_contact = getResources().getString(id1);
        contact_description.setText(content_contact);

    }
}

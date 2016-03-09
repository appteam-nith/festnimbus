package com.appteam.nimbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by sukhbir on 9/3/16.
 */
public class HackathonActivity extends AppCompatActivity {

    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hackathon);
        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // setTitle("HACKATHON");

    }

}

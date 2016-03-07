package com.appteam.nimbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appteam.nimbus.adapters.CoreTeamAdapter;
import com.appteam.nimbus.model.ItemCoreTeam;

import java.util.ArrayList;

/**
 * Created by sukhbir on 7/3/16.
 */
public class CoreTeamActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CoreTeamAdapter adapter;
    private ArrayList<ItemCoreTeam> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_team);
        recyclerView= (RecyclerView) findViewById(R.id.list_core_team);
        list=new ArrayList<>();
        list.add(new ItemCoreTeam("Prof.Rajnish Srivastava","Director",R.drawable.person_icon));
        list.add(new ItemCoreTeam("Dr. Sushil Chauhan", "Dean Student Welfare", R.drawable.person_icon));
        list.add(new ItemCoreTeam("Dr. Surender Soni", "Faculty Co-ordinator", R.drawable.person_icon));
        list.add(new ItemCoreTeam("Abhishek Vijay","Secretary Hill'ffair",R.drawable.person_icon));
        list.add(new ItemCoreTeam("Vishal Agarwal","Club Secretary Hill'ffair",R.drawable.person_icon));
        list.add(new ItemCoreTeam("Arun Muraleedharan","J-sec",R.drawable.person_icon));
        list.add(new ItemCoreTeam("Varun Kalra","J-sec",R.drawable.person_icon));
        list.add(new ItemCoreTeam("Srinath K","J-sec",R.drawable.person_icon));
        list.add(new ItemCoreTeam("Prikshit Tekta","J-sec",R.drawable.person_icon));
        list.add(new ItemCoreTeam("Shivangini Singh","J-sec",R.drawable.person_icon));
        list.add(new ItemCoreTeam("Prakash Haritwal","J-sec",R.drawable.person_icon));
        adapter=new CoreTeamAdapter(list,CoreTeamActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.close_main, R.anim.close_next);
    }
}

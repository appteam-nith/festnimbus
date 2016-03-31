package com.appteam.nimbus.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appteam.nimbus.R;
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
private String BASE_URL="https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/nimbusteam/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_team);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Core Team");

        recyclerView= (RecyclerView) findViewById(R.id.list_core_team);
        list=new ArrayList<>();
        list.add(new ItemCoreTeam("Dr. Rajnish Shrivastava","Director",BASE_URL+"rajnish.png"));
        list.add(new ItemCoreTeam("Dr. Raman Parti", "Dean Student Welfare",BASE_URL+"raman_parti.png"));
        list.add(new ItemCoreTeam("Dr. Surender Soni", "Faculty Coordinator",BASE_URL+"surender_soni.png"));
        list.add(new ItemCoreTeam("Ms. Venu Shree", "Faculty Co-Coordinator",BASE_URL+"venushree.png"));
        list.add(new ItemCoreTeam("Samriti Attrey","Student Secretary",BASE_URL+"" + "samriti_attrey.png"));
        list.add(new ItemCoreTeam("Vishal Agarwal","Club Secretary",BASE_URL+"vishal_agarwal.png"));
        list.add(new ItemCoreTeam("Sachin Patial","Finance & Treasury Secretary",BASE_URL+"sachin_patial.png"));
        list.add(new ItemCoreTeam("Parul Thakur","Public Relations Secretary",BASE_URL+"parul_thakur.png"));
        list.add(new ItemCoreTeam("Vinay Kumar","Hospitality & Accomodation Secretary",BASE_URL+"vinay_kumar.png"));
        list.add(new ItemCoreTeam("Manish Kandoria","Registration & Transportation Secretary",BASE_URL+"manish_kandoria.png"));
        list.add(new ItemCoreTeam("Shubhinder Singh","Creative Head",BASE_URL+"shubhinder_singh.png"));
        list.add(new ItemCoreTeam("Prateek Bandhu","Graphics Head",BASE_URL+"praxie.png"));
        list.add(new ItemCoreTeam("Prikshit Tekta","Web Head",BASE_URL+"prikshit_tekta.png"));
        list.add(new ItemCoreTeam("Sahil Badyal","Promotional & Marketing Secretary (App Team)",BASE_URL+"sahil_badyal.png"));
        list.add(new ItemCoreTeam("Shobit Kansal","Event Schedule Manager",BASE_URL+"shobit_kansal.png"));
        list.add(new ItemCoreTeam("Shubham Garg","Event Schedule Manager",BASE_URL+"shubham_garg.png"));
        list.add(new ItemCoreTeam("Srinath K","Event Quality Manager",BASE_URL+"srinath_k.png"));
        list.add(new ItemCoreTeam("Mohit Sharma","Event Quality Manager",BASE_URL+"mohit_sharma.png"));
        list.add(new ItemCoreTeam("Satya Prakash","Design & Decoration Secretary",BASE_URL+"satya_prakash.png"));
        list.add(new ItemCoreTeam("Ankush Sharma","Discipline Secretary",BASE_URL+"ankush_sharma.png"));
        list.add(new ItemCoreTeam("Kumud Jindal","Discipline Joint Secretary (Girls)",BASE_URL+"kumud_jindal.png"));
        list.add(new ItemCoreTeam("Rishabh kumar","Discipline Joint Secretary (Boys)",BASE_URL+"rishabh_kumar.png"));
        list.add(new ItemCoreTeam("Aditya Mittal","Environment Secretary",BASE_URL+"aditya_mittal.png"));
        list.add(new ItemCoreTeam("Siddharth Sood","Environment Joint Secretary",BASE_URL+"siddharth_sood.png"));
        list.add(new ItemCoreTeam("Pulkit","Human Values & Ethics Secretary",BASE_URL+"pulkit.png"));
        list.add(new ItemCoreTeam("Arinav Jain","Human Values & Ethics Joint Secretary",BASE_URL+"arinav_jain.png"));
        adapter=new CoreTeamAdapter(list,CoreTeamActivity.this);
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

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(this) != null) {

                finish();
                overridePendingTransition(R.anim.close_main, R.anim.close_next);

			}
			return true;
		default:
		return super.onOptionsItemSelected(item);
		}
	}
}

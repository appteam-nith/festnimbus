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
private String BASE_URL="https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/nimbusteam/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_team);
        recyclerView= (RecyclerView) findViewById(R.id.list_core_team);
        list=new ArrayList<>();
        list.add(new ItemCoreTeam("Dr. Rajnish Shrivastava","Director",BASE_URL+"rajnish.png"));
        list.add(new ItemCoreTeam("Dr. Raman Parti", "Dean Student Welfare",BASE_URL+"raman_parti.png"));
        list.add(new ItemCoreTeam("Dr. Surender Soni", "Faculty Coordinator",BASE_URL+"surender_soni.png"));
        list.add(new ItemCoreTeam("Ms. Venu Shree", "Faculty Co-Coordinator",BASE_URL+"venushree.png"));
        list.add(new ItemCoreTeam("Samriti Attrey","Secretary Nimbus",BASE_URL+"" + "samriti_attrey.png"));
        list.add(new ItemCoreTeam("Vishal Agarwal","Club Secretary",BASE_URL+"vishal_agarwal.png"));
        list.add(new ItemCoreTeam("Sachin Patial","J-sec",BASE_URL+"sachin_patial.png"));
        list.add(new ItemCoreTeam("Parul Thakur","J-sec",BASE_URL+"parul_thakur.png"));
        list.add(new ItemCoreTeam("Vinay Kumar","J-sec",BASE_URL+"vinay_kumar.png"));
        list.add(new ItemCoreTeam("Manish Kandoria","J-sec",BASE_URL+"manish_kandoria.png"));
        list.add(new ItemCoreTeam("Shubhinder Singh","J-sec",BASE_URL+"shubhinder_singh.png"));
        list.add(new ItemCoreTeam("Prateek Bandhu","J-sec",BASE_URL+"praxie.png"));
        list.add(new ItemCoreTeam("Prikshit Tekta","J-sec",BASE_URL+"prikshit_tekta.png"));
        list.add(new ItemCoreTeam("Sahil Badyal","J-sec",BASE_URL+"sahil_badyal.png"));
        list.add(new ItemCoreTeam("Shobit Kansal","J-sec",BASE_URL+"shobit_kansal.png"));
        list.add(new ItemCoreTeam("Shubham Garg","J-sec",BASE_URL+"shubham_garg.png"));
        list.add(new ItemCoreTeam("Srinath K","J-sec",BASE_URL+"srinath_k.png"));
        list.add(new ItemCoreTeam("Mohit Sharma","J-sec",BASE_URL+"mohit_sharma.png"));
        list.add(new ItemCoreTeam("Satya Prakash","J-sec",BASE_URL+"satya_prakash.png"));
        list.add(new ItemCoreTeam("Ankush Sharma","J-sec",BASE_URL+"ankush_sharma.png"));
        list.add(new ItemCoreTeam("Kumud Jindal","J-sec",BASE_URL+"kumud_jindal.png"));
        list.add(new ItemCoreTeam("Rishabh kumar","J-sec",BASE_URL+"rishabh_kumar.png"));
        list.add(new ItemCoreTeam("Aditya Mittal","J-sec",BASE_URL+"aditya_mittal.png"));
        list.add(new ItemCoreTeam("Siddharth Sood","J-sec",BASE_URL+"siddharth_sood.png"));
        list.add(new ItemCoreTeam("Pulkit","J-sec",BASE_URL+"pulkit.png"));
        list.add(new ItemCoreTeam("Arinav Jain","J-sec",BASE_URL+"arinav_jain.png"));
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
}

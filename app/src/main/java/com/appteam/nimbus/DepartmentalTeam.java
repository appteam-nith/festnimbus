package com.appteam.nimbus;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DepartmentalTeam extends AppCompatActivity {

MyAdapter adapter;
    private String BASE_URL="https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/teams/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departmental_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager_team);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);
        adapter=new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setPageTransformer(false,new ReaderViewPageTransformer(ReaderViewPageTransformer.TransformType.FLOW));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.close_main, R.anim.close_next);
    }

    public  class MyAdapter extends FragmentStatePagerAdapter{

        String ClubName[]={"CHelix","DesignOCrats",".eXe","Hermetica","Medextrous","Ojas","Vibhav","Vitruvians"};
        String LOGO[]={BASE_URL+"chelix.png",BASE_URL+"designocrats.png",BASE_URL+"exe.png",BASE_URL+"hermatica.png",BASE_URL+"medextrous.png",BASE_URL+"ojas.png",BASE_URL+"vibhav.png",BASE_URL+"vitruvians.png"};
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DepartmentFragment.newInstance(ClubName[position],getDetail(ClubName[position]),LOGO[position],getTeamDetail(ClubName[position]),getContactDetail(ClubName[position]));
        }

        @Override
        public int getCount() {
            return ClubName.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ClubName[position];
        }
    }
private String getDetail(String Clubname){
    int id = getResources().getIdentifier(Clubname, "string", MyApplication.getAppContext().getPackageName());
    String content = getResources().getString(id);
    return content;
}
    private String getTeamDetail(String Clubname){
        int id = getResources().getIdentifier(Clubname+"_team_detail", "string", MyApplication.getAppContext().getPackageName());
        String content = getResources().getString(id);
        return content;
    }
    private String getContactDetail(String Clubname){
        int id = getResources().getIdentifier(Clubname+"_contact_detail", "string", MyApplication.getAppContext().getPackageName());
        String content = getResources().getString(id);
        return content;
    }
}

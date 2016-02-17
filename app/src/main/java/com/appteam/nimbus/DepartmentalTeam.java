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
        String ClubName[]={"CHelix","Ojas","Medextrous","Vibhav",".eXe","DesignOCrats","Hermetica"};
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DepartmentFragment.newInstance(ClubName[position],getDetail(ClubName[position]),0);
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
}

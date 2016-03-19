package com.appteam.nimbus;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SponserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager_sponser);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }

public  class MyAdapter extends FragmentStatePagerAdapter{
private String sponser_type[]={"Educational Partner","Electronics Partner","Food Partner","Sponsors"};
    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SponserFragment.newInstance(sponser_type[position]);
    }

    @Override
    public int getCount() {
        return sponser_type.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sponser_type[position];
    }
}
}

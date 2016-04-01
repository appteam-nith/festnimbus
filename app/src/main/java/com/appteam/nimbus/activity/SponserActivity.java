package com.appteam.nimbus.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appteam.nimbus.R;
import com.appteam.nimbus.fragments.SponserFragment;

public class SponserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sponsors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager_sponser);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

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

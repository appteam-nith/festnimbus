package com.appteam.nimbus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class homeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private PersonalData personalData;
private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personalData=new PersonalData(this);
        if(personalData.getStatus()==false){
            Intent i=new Intent(homeActivity.this,Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        imageLoader=MySingleton.getInstance(MyApplication.getAppContext()).getImageLoader();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setDetail(navigationView);

        findViewById(R.id.department).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,DepartmentalTeam.class));
                overridePendingTransition(R.anim.open_next,R.anim.open_main);
            }
        });
        findViewById(R.id.event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,teamEvent.class));
                overridePendingTransition(R.anim.open_next,R.anim.open_main);
            }
        });
    }

    private void setDetail(NavigationView navigationView) {
        View v=navigationView.getHeaderView(0);
        TextView textEmail= (TextView) v.findViewById(R.id.textView);
        textEmail.setText(personalData.getEMAIL());
        TextView textName= (TextView) v.findViewById(R.id.name_text);
        ImageView image= (ImageView) v.findViewById(R.id.imageView);
        textName.setText(personalData.getNAME());
        String url=personalData.getURL();
        loadimage(url.substring(0,url.length()-2)+""+(int)Utils.convertDpToPixel(50f,MyApplication.getAppContext()),image);

    }

    private void loadimage(String url, final ImageView image) {
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                setRounded(response.getBitmap(),image);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void setRounded(Bitmap bitmap,ImageView image) {
        RoundedBitmapDrawable bitmapDrawable= RoundedBitmapDrawableFactory.create(homeActivity.this.getResources(),bitmap);
        bitmapDrawable.setCircular(true);
        image.setImageDrawable(bitmapDrawable);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

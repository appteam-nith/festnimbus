package com.appteam.nimbus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.appteam.nimbus.app.ViewActivity;

public class homeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private PersonalData personalData;
private ImageLoader imageLoader;
    private  static final String SHOW_OPTION="show";
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.department).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this, DepartmentalTeam.class));
                overridePendingTransition(R.anim.open_next, R.anim.open_main);
            }
        });

        findViewById(R.id.coreteam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,CoreTeamActivity.class));
                overridePendingTransition(R.anim.open_next, R.anim.open_main);
            }
        });

        findViewById(R.id.event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,EventActivity.class));
                overridePendingTransition(R.anim.open_next, R.anim.open_main);
            }
        });
        findViewById(R.id.welcome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browser=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.festnimbus.com"));
                startActivity(browser);
            }
        });
        findViewById(R.id.sponsors).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,SponserActivity.class));
                overridePendingTransition(R.anim.open_next, R.anim.open_main);
            }
        });
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
        if (id == R.id.action_logout) {
            personalData.SaveData(false);

            Intent launch_logout=new Intent(homeActivity.this,Login.class);
            launch_logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launch_logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launch_logout.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(launch_logout);
            finish();

            return true;
        }
        else if(id==R.id.action_leaderboard){
            startActivity(new Intent(homeActivity.this,Leaderboard.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_to_profile) {
            // Handle the camera action
            Intent i=new Intent(homeActivity.this,Profile.class);
            startActivity(i);

        }else if(id==R.id.aboutus_nav){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.app_name)));
            builder.setMessage(getResources().getText(R.string.aboutus_text));
            builder.setPositiveButton("OK", null);
            builder.setIcon(R.mipmap.nimbus_icon);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }else if(id==R.id.hackathon_nav){
            Intent i=new Intent(homeActivity.this,HackathonActivity.class);
            startActivity(i);

        }else if(id==R.id.feedback_nav){
            /*Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            String email_arr[]={"appteam.nith@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, email_arr);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Reporting A Bug/Feedback");
            intent.putExtra(Intent.EXTRA_TEXT, "Hello, Appteam \nI want to report a bug/give feedback corresponding to the app Nimbus 2k16.\n.....\n\n-Your name");
            startActivity(Intent.createChooser(intent,"Send Email"));*/


            Intent intent=new Intent(Intent.ACTION_SENDTO);
            String uriText="mailto:"+ Uri.encode("appteam.nith@gmail.com")+"?subject="+
                    Uri.encode("Reporting A Bug/Feedback")  + "&body="+ Uri.encode("Hello, Appteam \nI want to report a bug/give feedback corresponding to the app Nimbus 2k16.\n.....\n\n-Your name");

            Uri uri=Uri.parse(uriText);
            intent.setData(uri);
            startActivity(Intent.createChooser(intent,"Send Email"));
        }else if(id==R.id.opensourcelicenses_nav){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.open_source_licenses)));
            builder.setMessage(getResources().getText(R.string.licenses_text));
            builder.setPositiveButton("OK", null);
            //builder.setIcon(R.mipmap.nimbus_icon);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }else if(id==R.id.contributors_nav){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.contributors)));
            builder.setMessage(getResources().getText(R.string.contributors_text));
            builder.setPositiveButton("OK", null);
            //builder.setIcon(R.mipmap.nimbus_icon);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }
        else if(id==R.id.notifications){
            startActivity(new Intent(homeActivity.this, ViewActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}


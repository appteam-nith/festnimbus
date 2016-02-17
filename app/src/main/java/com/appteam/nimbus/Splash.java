package com.appteam.nimbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.appteam.nimbus.app.Config;
import com.appteam.nimbus.gcm.GcmIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH=1200;
    private static final String DEFAULT_CHECK="show-on-startup";
    private SharedPreferences preferences;
    private String TAG = Splash.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
private PersonalData personalData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

personalData=new PersonalData(this);
        preferences=getPreferences(MODE_PRIVATE);

        final Boolean isDefaultChecked=preferences.getBoolean(DEFAULT_CHECK,true);

        setContentView(R.layout.splash1);

        Resources res = getResources();
        final ImageView image1 = (ImageView) findViewById(R.id.dqqefdf);
        final ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        final ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        final ImageView image4 = (ImageView) findViewById(R.id.imageView4);
        final ImageView image5 = (ImageView) findViewById(R.id.imageView5);
        final ImageView image6 = (ImageView) findViewById(R.id.imageView6);
        final ImageView image7 = (ImageView) findViewById(R.id.imageView7);
        final ImageView image8 = (ImageView) findViewById(R.id.imageView8);
        final ImageView image9 = (ImageView) findViewById(R.id.imageView10);
        final ImageView image10 = (ImageView) findViewById(R.id.imageView12);

        final int newColor = res.getColor(R.color.new_color);
        image1.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image2.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image3.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image4.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image5.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image6.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image7.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image8.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image9.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        image10.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);

        image1.setRotation(27);
        image5.setRotation(24);
        image8.setRotation(40);
        image4.setRotation(-50);
        image6.setRotation(10);
        image7.setRotation(20);
        image3.setRotation(-10);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    String token = intent.getStringExtra("token");
                    Log.e(TAG, "GCM Registration Token: " + token);


                } else if (intent.getAction().equals(Config.SENT_TOKEN_TO_SERVER)) {
                    // gcm registration id is stored in our server's MySQL

                    //Toast.makeText(getApplicationContext(), "GCM registration token is stored in server!", Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    //Toast.makeText(getApplicationContext(), "Push notification is received!", Toast.LENGTH_LONG).show();
                }
            }
        };

        if (checkPlayServices()) {
            if(personalData.getGCCMtoken().equals("no token")){
                Log.d("check","check");
                registerGCM();
                      }

        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;

                if (isDefaultChecked == false) {
                    i = new Intent(Splash.this, Login.class);
                } else {
                    i = new Intent(Splash.this, AboutNimbusSplash.class);
                }

                startActivity(i);
                finish();

                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
    private void registerGCM() {

                Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}

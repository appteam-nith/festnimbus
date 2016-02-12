package com.appteam.nimbus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH=1200;
    private static final String DEFAULT_CHECK="show-on-startup";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
}

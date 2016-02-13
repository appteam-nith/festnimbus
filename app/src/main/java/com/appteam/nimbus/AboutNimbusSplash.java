package com.appteam.nimbus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

public class AboutNimbusSplash extends AppCompatActivity{

    private static final String DEFAULT_CHECK="show-on-startup";

    private ImageButton next;
    private CheckBox showByDefault;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash2);

        preferences=getPreferences(MODE_PRIVATE);

        Boolean isDefaultChecked=preferences.getBoolean(DEFAULT_CHECK,true);

        showByDefault=(CheckBox)findViewById(R.id.show_by_default);
        showByDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(DEFAULT_CHECK, isChecked);
                editor.commit();

            }
        });

        showByDefault.setSelected(isDefaultChecked);

        if(isDefaultChecked==false){
            Intent i;
            if(new PersonalData(AboutNimbusSplash.this).getStatus())
                i=new Intent(AboutNimbusSplash.this,homeActivity.class);
            else
             i = new Intent(AboutNimbusSplash.this, Login.class);
            startActivity(i);
            finish();

            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        next=(ImageButton)findViewById(R.id.splash_next_imagebutton);
        next.setRotation(-90);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(new PersonalData(AboutNimbusSplash.this).getStatus())
                    i=new Intent(AboutNimbusSplash.this,homeActivity.class);
                else
                    i = new Intent(AboutNimbusSplash.this, Login.class);
                startActivity(i);
                finish();

                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

    }

}
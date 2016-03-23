package com.appteam.nimbus.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appteam.nimbus.R;
import com.appteam.nimbus.app.MyApplication;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.model.EventClass;
import com.appteam.nimbus.model.PersonalData;
import com.appteam.nimbus.singleton.MySingleton;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sukhbir on 23/3/16.
 */
public class EventRegisterActivity extends AppCompatActivity {

    EventClass event;
    LoadToast loadToast;
    TextView text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hackathon);
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        event=(EventClass)getIntent().getSerializableExtra("eventPassed");

        collapsingToolbarLayout.setTitle(event.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadToast=new LoadToast(EventRegisterActivity.this);

        loadToast.setText("LOADING");
        loadToast.setTranslationY((int) Utils.convertDpToPixel(20, EventRegisterActivity.this));
        text=(TextView)findViewById(R.id.description);
        String toshow="";
        toshow="by "+event.getTeamname()+" ( "+event.getDname()+" )\n\n"+event.getTimeline()+"\n\n";
        toshow+=event.getShort_des()+"\n\nRules:-\n\n";

        for(int i=0;i<event.getRules().size();i++){
            toshow+=""+(i+1)+". "+event.getRules().get(i)+"\n\n";
        }

        toshow+="Contact Number: "+event.getContact();

        text.setText(toshow);

        findViewById(R.id.hackathon_register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest(event);
                loadToast.show();
            }
        });

    }

    private void getRequest(final EventClass current_event) {

        Log.v("Sending registration Req", "for event " + current_event.getName());

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.PUT,getURL(current_event.getName()), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE-Registration", response.toString());

                try {
                    String status=response.getString("status");
                    loadToast.success();

                        Toast.makeText(EventRegisterActivity.this,status,Toast.LENGTH_LONG).show();



                } catch (JSONException e) {
                    e.printStackTrace();
                    loadToast.error();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadToast.error();
                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("Authorization","bearer "+new PersonalData(EventRegisterActivity.this).getToken());
                return map;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }

    private String getURL(String eventName) {
        return "https://festnimbus.herokuapp.com/api/user/"+eventName;
    }


}

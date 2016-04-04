package com.appteam.nimbus.activity;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sukhbir on 23/3/16.
 */
public class EventRegisterActivity extends AppCompatActivity {

    EventClass event;
    LoadToast loadToast;
    TextView text;
    ImageView error_image;
    private static String KEY_STRING="key string message";
    private static String KEY_EVENT="key event";
    private static String KEY_SHOW_BUTTON="register button";
    boolean current_event_registered=false;
    private Button register_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hackathon);
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        register_button=(Button)findViewById(R.id.hackathon_register_button);

        loadToast=new LoadToast(EventRegisterActivity.this);
        loadToast.setText("LOADING");
        loadToast.setTranslationY((int) Utils.convertDpToPixel(20, EventRegisterActivity.this));
        text=(TextView)findViewById(R.id.description);
        error_image=(ImageView)findViewById(R.id.error_image);
        error_image.setVisibility(View.GONE);
        final int newColor = getResources().getColor(R.color.new_color);
        error_image.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            ImageView logo_image= (ImageView) findViewById(R.id.bgheader);
            logo_image.setTransitionName(getString(R.string.transition_name));
        }

        String toshow="";

        getRegisteredEventRequest(); // for getting array of registered events
        register_button.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            toshow = (String) savedInstanceState.getString(KEY_STRING);
            event=(EventClass) savedInstanceState.getSerializable(KEY_EVENT);
            current_event_registered=(Boolean)savedInstanceState.getSerializable(KEY_SHOW_BUTTON);
            if(current_event_registered==true)
            {register_button.setText("Unregister");}
            else{register_button.setText("Register");}

            if(toshow.contains("Details about event is currently unavailable.")){
                register_button.setVisibility(View.GONE);
                error_image.setVisibility(View.VISIBLE);
            }else{
                register_button.setVisibility(View.VISIBLE);
            }
        }else{
            toshow+="Please wait...\n\n\n";
            event=(EventClass)getIntent().getSerializableExtra("eventPassed");
            register_button.setVisibility(View.GONE);

            loadToast.show();

            getRequest();

        }

        collapsingToolbarLayout.setTitle(event.getName());

        text.setText(toshow);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRegistrationRequest(event);
                loadToast.show();
            }
        });
    }

    private void getRegistrationRequest(final EventClass current_event) {

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.PUT,getURLForRegistration(current_event.getName()), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE-Registration", response.toString());

                try {
                    String status=response.getString("status");
                    loadToast.success();

                    Toast.makeText(EventRegisterActivity.this,status,Toast.LENGTH_LONG).show();

                    if(response.has("validity")){
                        int v=response.getInt("validity");
                        if(v==1){
                            register_button.setText("Unregister");
                            current_event_registered=true;
                        }else{
                            register_button.setText("Register");
                            current_event_registered=false;
                        }
                    }

                    register_button.setVisibility(View.VISIBLE);

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

    private String getURLForRegistration(String eventName) {
        try {
            URI uri=new URI("https","festnimbus.herokuapp.com","/api/user/"+eventName,null);
            Log.d("url",uri.toASCIIString());
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void getRequest() {

        Log.v("Sending request", "for team " + event.getTeamname()+"/"+event.getName());

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(getURL(event.getTeamname(),event.getName()), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE", response.toString());

                loadToast.success();

                try {
                    String status=response.getString("status");
                    if(status.equals("Success!")){
                        JSONArray data=response.getJSONArray("data");

                        if(data.length()==0){
                            text.setText("Details about event is currently unavailable.\n\n");

                            error_image.setVisibility(View.VISIBLE);
                        }else{
                            for(int j=0;j<data.length();j++) {
                                JSONObject dataValue = (JSONObject) data.get(j);

                                if(dataValue.has("_id")){
                                    event.setId(dataValue.getString("_id"));
                                    Log.v("eventcard-respose", "id found");
                                }

                                if(dataValue.has("Ename")){
                                    event.setName(dataValue.getString("Ename"));
                                    Log.v("eventcard-respose", "ename found");
                                }

                                if(dataValue.has("Dname")){
                                    event.setDname(dataValue.getString("Dname"));
                                    Log.v("eventcard-respose", "dname found");
                                }else{
                                    event.setDname("NA");
                                }

                                if(dataValue.has("Tname")){
                                    event.setTeamname(dataValue.getString("Tname"));
                                    Log.v("eventcard-respose", "tname found");
                                }else{
                                    event.setTeamname("NA");
                                }

                                if(dataValue.has("shortD")){
                                    event.setShort_des(dataValue.getString("shortD"));
                                    Log.v("eventcard-respose", "shortD found");
                                }else{
                                    event.setShort_des("Currently Not Available");
                                }

                                if(dataValue.has("longD")){
                                    event.setLong_des(dataValue.getString("longD"));
                                    Log.v("eventcard-respose", "longD found");
                                }else{
                                    event.setLong_des(" ");
                                }

                                if(dataValue.has("Contact")){
                                    event.setContact(dataValue.getString("Contact"));
                                    //Log.v("eventcard-respose", "longD found");
                                }else{
                                    event.setContact("NA");
                                }

                                if(dataValue.has("timeline")){
                                    event.setTimeline(dataValue.getString("timeline"));
                                    //Log.v("eventcard-respose", "longD found");
                                }else{
                                    event.setTimeline("NA");
                                }

                                if(dataValue.has("__v")){
                                    event.set__v(dataValue.getString("__v"));
                                    //Log.v("eventcard-respose", "longD found");
                                }

                                if(dataValue.has("rules")){
                                    JSONArray rulesResponse=dataValue.getJSONArray("rules");

                                    ArrayList<String> ruleslist=new ArrayList<>();

                                    for(int k=0;k<rulesResponse.length();k++){
                                        ruleslist.add(rulesResponse.get(k).toString());
                                    }

                                    if(rulesResponse.length()==0){
                                        ruleslist.add("NA");
                                    }

                                    event.setRules(ruleslist);
                                }
                            }

                            String toshow="";
                            if(!event.getTeamname().equals("NA")){
                                toshow+="by "+event.getTeamname();
                            }

                            if(!event.getDname().equals("NA")){
                                toshow+=" ( "+event.getDname()+" )";
                            }
                            toshow+="\n\n";

                            if(!event.getTimeline().equals("NA")){
                                toshow+=event.getTimeline()+"\n\n";
                            }

                            if(!event.getShort_des().equals("NA")){
                                toshow+=event.getShort_des()+"\n\n";
                            }

                            if(event.getRules()!=null){

                                toshow+="Rules:-\n\n";

                                if(!event.getRules().get(0).equals("NA")){
                                    for(int i=0;i<event.getRules().size();i++){

                                        toshow+=""+(i+1)+". "+event.getRules().get(i)+"\n\n";
                                    }
                                }else{
                                    toshow+="NA\n\n";
                                }
                            }

                            if(!event.getContact().equals("NA")){
                                toshow+="Contact Number: "+event.getContact();
                            }

                            text.setText(toshow);
                            register_button.setText("Checking Status...");
                            register_button.setVisibility(View.VISIBLE);
                        }

                        loadToast.success();

                    }

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

                text.setText("Details about event is currently unavailable.");
                error_image.setVisibility(View.VISIBLE);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }

    private String getURL(String teamName,String eventName) {
        try {
            URI uri=new URI("https","festnimbus.herokuapp.com","/api/teams/"+teamName+"/"+eventName,null);
      Log.d("url",uri.toASCIIString());
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
           return "";
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_STRING, text.getText().toString());
        savedInstanceState.putSerializable(KEY_EVENT, event);
        savedInstanceState.putSerializable(KEY_SHOW_BUTTON, current_event_registered);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {

                    finish();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void getRegisteredEventRequest() {

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(getRegisteredEventsURL(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("profile response", response.toString());
                try {
                    JSONObject data=response.getJSONObject("data");
                    String status=response.getString("status");

                    if(status.equals("User Profile Info")){
                        JSONArray arr=new JSONArray();

                        if(data.has("events_register")){
                            arr=data.getJSONArray("events_register");

                            register_button.setText("Register");

                            for(int i=0;i<arr.length();i++){
                                Log.v("%&#$",arr.getString(i));

                                if(event.getName().equals(arr.getString(i))){
                                    current_event_registered=true;
                                    register_button.setText("Unregister");
                                    register_button.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private String getRegisteredEventsURL() {
        return "https://festnimbus.herokuapp.com/api/user/profile";
    }

}

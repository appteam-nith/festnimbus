package com.appteam.nimbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    TextView name_textview,username_textview,phone_textview,silverCoins_textview,goldCoins_textview,
            collegeRank_textview,eventsWithRank_textview;

    private static final String KEY_INDEX = "profileSaveOnRotation";
    private LoadToast loadToast;
    private Button logout_profile;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            user = (User) savedInstanceState.getSerializable(KEY_INDEX);
        }

        name_textview=(TextView)findViewById(R.id.name_profile);
        username_textview=(TextView)findViewById(R.id.username_profile);
        phone_textview=(TextView)findViewById(R.id.phone_profile);
        silverCoins_textview=(TextView)findViewById(R.id.silverCoins_profile);
        goldCoins_textview=(TextView)findViewById(R.id.goldCoins_profile);
        collegeRank_textview=(TextView)findViewById(R.id.collegeRank_profile);
        eventsWithRank_textview=(TextView)findViewById(R.id.eventNamesWithRank_profile);
        logout_profile=(Button)findViewById(R.id.logout_profilepage);

        logout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalData personalData=new PersonalData(Profile.this);
                personalData.SaveData(false);

                Intent launch_logout=new Intent(Profile.this,Login.class);
                launch_logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launch_logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launch_logout.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(launch_logout);
                finish();
            }
        });

        if(user==null){
        getRequest();
        }else{

            Log.v("Note","user retrieved from saveInstance");

            if(user.getEmail()!=null){
                name_textview.setText(user.getEmail());
            }

            if(user.getRoll()!=null){
                username_textview.setText(user.getRoll());
            }

            if(user.getCollegeRank()!=0){
               collegeRank_textview.setText(user.getCollegeRank());
            }

            if(user.getEventRank()!=null){
                eventsWithRank_textview.setText(user.getEventRank());
            }

            if(user.getGoldCoins()!=0){
                goldCoins_textview.setText(user.getGoldCoins());
            }

            if(user.getSilverCoins()!=0){
                silverCoins_textview.setText(user.getSilverCoins());
            }

            if(user.getPhone()!=null){
                phone_textview.setText(user.getPhone());
            }

        }
    }

    private void getRequest() {

        loadToast=new LoadToast(Profile.this);
        loadToast.setText("LOADING");
        loadToast.setTranslationY((int) Utils.convertDpToPixel(20,Profile.this));
        loadToast.show();

      final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(getURL(), new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
              Log.d("RESPONSE-PROFILE",response.toString());
              try {
                  loadToast.success();

                  JSONObject data=response.getJSONObject("data");
                  String status=response.getString("status");

                  if(status.equals("OK")){
                      String email=data.getString("email");
                      user=new User();
                      user.setEmail(email);

                      String roll=null;

                      if(data.has("rollno")){
                          if(!data.isNull("rollno")){
                              roll=data.getString("rollno");
                              username_textview.setText(roll);
                              user.setRoll(roll);
                          }
                      }

                      name_textview.setText(email);
                  }

              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
                loadToast.error();
          }
      }){
          @Override
          public Map<String, String> getHeaders() throws AuthFailureError {
              Map<String,String> map=new HashMap<>();
              map.put("Authorization","bearer "+new PersonalData(Profile.this).getToken());
              return map;
          }
      };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(KEY_INDEX, user);
    }

    private String getURL() {
        return "https://festnimbus.herokuapp.com/api/user/me";
    }
}

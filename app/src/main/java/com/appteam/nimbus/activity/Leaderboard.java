package com.appteam.nimbus.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appteam.nimbus.helper.Connection;
import com.appteam.nimbus.singleton.MySingleton;
import com.appteam.nimbus.model.PersonalData;
import com.appteam.nimbus.R;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.adapters.LeaderboardAdapter;
import com.appteam.nimbus.app.MyApplication;
import com.appteam.nimbus.model.LeaderboardItem;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Leaderboard extends AppCompatActivity {
    private static final String LIST = "List";
    private static final String STATUS = "status";
    private RecyclerView recyclerView;
    private ArrayList<LeaderboardItem> list;
    private LoadToast loadToast;
    private PersonalData personalData;
    private Connection connection;
    private boolean reverse;
    private String status;
    private LeaderboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        connection = new Connection(this);
        personalData = new PersonalData(this);

        loadToast = new LoadToast(this);
        loadToast.setText("Loading");
        loadToast.setTranslationY((int) Utils.convertDpToPixel(70, Leaderboard.this));

        recyclerView = (RecyclerView) findViewById(R.id.list_leaderboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeaderboardAdapter();
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList(LIST);
            adapter.setStatus(savedInstanceState.getString(STATUS));
            if(list!=null){
                adapter.refresh(list);
            }


        } else {
            if (connection.isInternet()) {
                sendRequest(getUrl("Gold"));
                loadToast.show();
            } else {
                Toast.makeText(Leaderboard.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connection.isInternet()) {
                    if (!reverse) {
                        sendRequest(getUrl("Silver"));
                        loadToast.show();
                        reverse = true;
                        fab.setImageResource(R.drawable.currency_dollar_yellow);
                    } else {
                        sendRequest(getUrl("Gold"));
                        loadToast.show();
                        reverse = false;
                        fab.setImageResource(R.drawable.currency_dollar);
                    }
                } else {
                    Toast.makeText(Leaderboard.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendRequest(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadToast.success();
                list = parseResponse(response);
                adapter.refresh(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadToast.error();
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    if (networkResponse.statusCode == 401) {
                        Toast.makeText(Leaderboard.this, "INVALID ACCESS", Toast.LENGTH_SHORT).show();
                    }
                    if (error instanceof TimeoutError) {
                        Toast.makeText(Leaderboard.this, "TIME OUT ERROR", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(Leaderboard.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "bearer " + personalData.getToken());
                return map;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }

    private ArrayList<LeaderboardItem> parseResponse(JSONObject response) {
        ArrayList<LeaderboardItem> list = new ArrayList<>();
        try {
            JSONArray array = response.getJSONArray("data");
            status = response.getString("status");
            adapter.setStatus(status);
            for (int i = 0; i <=array.length()-1; i++) {
                String email = "", silver_coin = "", gold_coin = "", rollno = "", name= "";
                int mobile = 0;
                String event_register[] = {};
                JSONObject profile = array.getJSONObject(i);
                if (contain(profile, "email")) {
                    email = profile.getString("email");
                }
                if (contain(profile, "name")) {
                    name = profile.getString("name");
                }
                if (contain(profile, "silver_coins")) {
                    silver_coin = profile.getString("silver_coins");
                }
                if (contain(profile, "gold_coins")) {
                    gold_coin = profile.getString("gold_coins");
                }
                if (contain(profile, "rollno")) {
                    rollno = profile.getString("rollno");
                }
                if (contain(profile, "mobile")) {
                    mobile = profile.getInt("mobile");
                }
                if (contain(profile, "events_register")) {
                }
                list.add(new LeaderboardItem(email,name, silver_coin, gold_coin, rollno, event_register));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getUrl(String Coin) {
        if (Coin.equalsIgnoreCase("Gold")) {
            return "https://festnimbus.herokuapp.com/api/user/glb";
        } else {
            return "https://festnimbus.herokuapp.com/api/user/slb";
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST, list);
        outState.putString(STATUS, status);
    }

    private Boolean contain(JSONObject object, String name) {
        return object.has(name) && (!object.isNull(name));
    }
}

package com.appteam.nimbus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appteam.nimbus.R;
import com.appteam.nimbus.adapters.EventsAdapter;
import com.appteam.nimbus.app.MyApplication;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.model.EventClass;
import com.appteam.nimbus.model.TeamClass;
import com.appteam.nimbus.singleton.MySingleton;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sukhbir on 21/2/16.
 */
public class EventActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    EventsAdapter adapter;
    LoadToast loadToast;

    String teamNameArray[]={"EXE","EXE","APPTEAM"};  //jUST cHANGE tHE ARRAY VALUES FOR MORE REQUESTS

    private ArrayList<TeamClass> list;
    int globalResponseCount=0;
    public static String KEY_EVENTS_ARRAY="events array";
    public static String KEY_CHECK="response success";
    boolean responseRecievedSuccessfull=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);    // uses same layout as that of core team
        globalResponseCount=0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView= (RecyclerView) findViewById(R.id.list_core_team);

        int flag=0; // 0 => array not available create new, otherwise 1=> array saved in instance state

        if (savedInstanceState != null) {
            list = (ArrayList) savedInstanceState.getSerializable(KEY_EVENTS_ARRAY);
            responseRecievedSuccessfull=(Boolean)savedInstanceState.getBoolean(KEY_CHECK);
            flag=1;
        }else{
            list=new ArrayList<>();
        }

        if(flag==0 || !responseRecievedSuccessfull){

            loadToast=new LoadToast(EventActivity.this);

            loadToast.setText("LOADING");
            loadToast.setTranslationY((int) Utils.convertDpToPixel(70, EventActivity.this));

            loadToast.show();

            for(int j=0;j<teamNameArray.length;j++){
                getRequest(teamNameArray[j]);
            }

        }else {

            loadToast=new LoadToast(EventActivity.this);

            loadToast.setText("LOADING");
            loadToast.setTranslationY((int) Utils.convertDpToPixel(70, EventActivity.this));

            loadToast.show();

            globalResponseCount=teamNameArray.length;
            attachAdapter();
        }

    }

    private void attachAdapter(){
        if(globalResponseCount>=teamNameArray.length){
            loadToast.success();
            adapter=new EventsAdapter(list,EventActivity.this);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    private void getRequest(final String teamName) {

        Log.v("Sending request", "for team "+teamName);

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(getURL(teamName), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE-EventActivity", response.toString());
                try {
                    String status=response.getString("status");
                    JSONArray data=response.getJSONArray("data");

                    TeamClass team1=new TeamClass();
                    for(int j=0;j<data.length();j++){
                        JSONObject dataValue= (JSONObject) data.get(j);
                        if(dataValue.has("_id")){
                            team1.set_id(dataValue.getString("_id"));
                            Log.v("respose","id found");
                        }

                        if(dataValue.has("teamName")){
                            team1.setTeamname(dataValue.getString("teamName"));
                            Log.v("respose", "team name found");
                        }

                        if(dataValue.has("__v")){
                            team1.set__v(dataValue.getString("__v"));
                            Log.v("respose", "__v found");
                        }

                        if(dataValue.has("events")){
                            //team1.setEvents(dataValue.getJSONArray("events").);
                            JSONArray eventsArray=dataValue.getJSONArray("events");

                            ArrayList <EventClass> events1=new ArrayList<>();

                            for(int k=0;k<eventsArray.length();k++){
                                EventClass e1=new EventClass();
                                e1.setTeamname(teamName);
                                e1.setName(eventsArray.get(k).toString());
                                Log.v("respose", "event found: "+eventsArray.get(k).toString());
                                events1.add(e1);
                            }

                            if(eventsArray.length()==0){
                                EventClass event1=new EventClass("56cb1c03ed77fec80e88ef71","Coming Soon","A Nice Event","");
                                EventClass event2=new EventClass("56cb1c03ed77fec80e88ef71","Coming Soon","A Nice Event","");
                                event1.setTeamname("None");
                                event2.setTeamname("None");
                                events1.add(event1);
                                events1.add(event2);
                                events1.add(event1);
                                events1.add(event2);
                                events1.add(event1);
                                events1.add(event2);
                            }

                            team1.setEvents(events1);
                        }
                    }

                    if(data.length()==0){
                        ArrayList <EventClass> events1=new ArrayList<>();
                            EventClass event1=new EventClass("56cb1c03ed77fec80e88ef71","Coming Soon","A Nice Event","");
                            EventClass event2=new EventClass("56cb1c03ed77fec80e88ef71","Coming Soon","A Nice Event","");
                            event1.setTeamname("None");
                            event2.setTeamname("None");
                            events1.add(event1);
                            events1.add(event2);
                            events1.add(event1);
                            events1.add(event2);
                            events1.add(event1);
                            events1.add(event2);
                        team1.setEvents(events1);

                        team1.setTeamname(teamName);
                    }

                    list.add(team1);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                responseRecievedSuccessfull=true;
                globalResponseCount++;
                attachAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                globalResponseCount++;
                responseRecievedSuccessfull=false;
                //attachAdapter();
                loadToast.error();

                list.clear();

                TeamClass team1=new TeamClass();
                team1.setTeamname("Coming Soon");
                EventClass event1=new EventClass("56cb1c03ed77fec80e88ef71","Coming Soon","A Nice Event","");
                EventClass event2=new EventClass("56cb1c03ed77fec80e88ef71","Coming Soon","A Nice Event","");
                event1.setTeamname("None");
                event2.setTeamname("None");

                ArrayList<EventClass> events=new ArrayList<>();
                events.add(event1);
                events.add(event2);
                events.add(event1);
                events.add(event2);
                events.add(event1);
                events.add(event2);
                team1.setEvents(events);

                list.add(team1);
                list.add(team1);
                list.add(team1);
                list.add(team1);

                adapter=new EventsAdapter(list,EventActivity.this);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(EventActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }

    private String getURL(String teamName) {
        return "https://festnimbus.herokuapp.com/api/teams/"+teamName;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(KEY_EVENTS_ARRAY, list);
        savedInstanceState.putBoolean(KEY_CHECK,responseRecievedSuccessfull);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.close_main, R.anim.close_next);
    }
}

package com.appteam.nimbus.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appteam.nimbus.R;
import com.appteam.nimbus.activity.EventRegisterActivity;
import com.appteam.nimbus.app.MyApplication;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.model.EventClass;
import com.appteam.nimbus.singleton.MySingleton;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sukhbir on 14/3/16.
 */
public class NestedEventListAdapter extends RecyclerView.Adapter<NestedEventListAdapter.ViewHolder> {

    Context context;
    ArrayList<EventClass> list;
    LoadToast loadToast;

    public NestedEventListAdapter(ArrayList<EventClass> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Log.v("Nested called","true");
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_event,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final EventClass current_event=list.get(i);    //event Under Processing

        viewHolder.eventname.setText(current_event.getName());
        viewHolder.event_image.setImageResource(R.drawable.placeholder_sidemenu);
        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,current_event.getName(),Toast.LENGTH_SHORT).show();

                loadToast=new LoadToast(context);

                loadToast.setText("LOADING");
                loadToast.setTranslationY((int) Utils.convertDpToPixel(70, context));

                if(!current_event.getName().equals("Coming Soon")){
                    loadToast.show();
                    getRequest(current_event);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView eventname;
        ImageView event_image;
        CardView root;

        public ViewHolder(View v) {
            super(v);
            root=(CardView)v.findViewById(R.id.root_event_cardview);
            eventname= (TextView) v.findViewById(R.id.event_name_row_event);
            event_image= (ImageView) v.findViewById(R.id.event_pic_row_event);
        }
    }

    private void getRequest(final EventClass current_event) {

        Log.v("Sending request", "for team " + current_event.getTeamname()+"/"+current_event.getName());

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(getURL(current_event.getTeamname(),current_event.getName()), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE-EventCard", response.toString());

                loadToast.success();

                try {
                    String status=response.getString("status");
                    if(status.equals("Success!")){
                        JSONArray data=response.getJSONArray("data");

                        for(int j=0;j<data.length();j++) {
                            JSONObject dataValue = (JSONObject) data.get(j);

                            if(dataValue.has("_id")){
                                current_event.setId(dataValue.getString("_id"));
                                Log.v("eventcard-respose", "id found");
                            }

                            if(dataValue.has("Ename")){
                                current_event.setName(dataValue.getString("Ename"));
                                Log.v("eventcard-respose", "ename found");
                            }

                            if(dataValue.has("Dname")){
                                current_event.setDname(dataValue.getString("Dname"));
                                Log.v("eventcard-respose", "dname found");
                            }

                            if(dataValue.has("Tname")){
                                current_event.setTeamname(dataValue.getString("Tname"));
                                Log.v("eventcard-respose", "tname found");
                            }

                            if(dataValue.has("shortD")){
                                current_event.setShort_des(dataValue.getString("shortD"));
                                Log.v("eventcard-respose", "shortD found");
                            }

                            if(dataValue.has("shortD")){
                                current_event.setShort_des(dataValue.getString("shortD"));
                                Log.v("eventcard-respose", "shortD found");
                            }

                            if(dataValue.has("longD")){
                                current_event.setLong_des(dataValue.getString("longD"));
                                Log.v("eventcard-respose", "longD found");
                            }

                            if(dataValue.has("Contact")){
                                current_event.setContact(dataValue.getString("Contact"));
                                //Log.v("eventcard-respose", "longD found");
                            }

                            if(dataValue.has("timeline")){
                                current_event.setTimeline(dataValue.getString("timeline"));
                                //Log.v("eventcard-respose", "longD found");
                            }

                            if(dataValue.has("__v")){
                                current_event.set__v(dataValue.getString("__v"));
                                //Log.v("eventcard-respose", "longD found");
                            }

                            if(dataValue.has("rules")){
                                JSONArray rulesResponse=dataValue.getJSONArray("rules");

                                ArrayList <String> ruleslist=new ArrayList<>();

                                for(int k=0;k<rulesResponse.length();k++){
                                    ruleslist.add(rulesResponse.get(k).toString());
                                }

                                if(rulesResponse.length()==0){
                                   ruleslist.add("NA");
                                }

                                current_event.setRules(ruleslist);
                            }
                        }

                        Intent i=new Intent(context,EventRegisterActivity.class);
                        i.putExtra("eventPassed",current_event);
                        context.startActivity(i);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadToast.error();
               error.printStackTrace();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }

    private String getURL(String teamName,String eventName) {
        return "https://festnimbus.herokuapp.com/api/teams/"+teamName+"/"+eventName;
    }

}


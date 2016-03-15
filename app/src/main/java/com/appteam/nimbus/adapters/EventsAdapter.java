package com.appteam.nimbus.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appteam.nimbus.R;
import com.appteam.nimbus.model.EventClass;
import com.appteam.nimbus.model.TeamClass;

import java.util.ArrayList;

/**
 * Created by sukhbir on 14/3/16.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    Context context;
    ArrayList<TeamClass> list;

    public EventsAdapter(ArrayList<TeamClass> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_events,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

            viewHolder.teamname.setText(list.get(i).getTeamname());

            ArrayList<EventClass> event_list=list.get(i).getEvents();

            Log.v("Events list item",event_list.get(0).getName());

            // list.add(new ItemCoreTeam("Prof.Rajnish Srivastava","Director",R.drawable.person_icon));
            NestedEventListAdapter adapter1=new NestedEventListAdapter(event_list,context);
            viewHolder.horizontal_recycler.setAdapter(adapter1);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.horizontal_recycler.setLayoutManager(linearLayoutManager);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView teamname;
        RecyclerView horizontal_recycler;

        public ViewHolder(View v) {
            super(v);
            teamname= (TextView) v.findViewById(R.id.team_name_event);
            horizontal_recycler= (RecyclerView) v.findViewById(R.id.recycler_horizontal_events);
        }
    }

}

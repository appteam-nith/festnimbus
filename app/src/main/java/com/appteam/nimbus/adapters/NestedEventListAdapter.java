package com.appteam.nimbus.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appteam.nimbus.R;
import com.appteam.nimbus.activity.EventRegisterActivity;
import com.appteam.nimbus.model.EventClass;

import net.steamcrafted.loadtoast.LoadToast;

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
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        final EventClass current_event=list.get(i);    //event Under Processing
        viewHolder.eventname.setText(current_event.getName());
        viewHolder.event_image.setImageResource(R.drawable.placeholder_sidemenu);
        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!current_event.getName().equals("Coming Soon")) {
                    Intent i = new Intent(context, EventRegisterActivity.class);
                    i.putExtra("eventPassed", current_event);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        viewHolder.root.setTransitionName(context.getString(R.string.transition_name));
                        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,viewHolder.root,context.getString(R.string.transition_name));
                        context.startActivity(i,optionsCompat.toBundle());
                    }
                   else {
                        context.startActivity(i);
                    }


                }else{
                    Toast.makeText(context, current_event.getName(), Toast.LENGTH_SHORT).show();
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

}


package com.appteam.nimbus.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appteam.nimbus.R;
import com.appteam.nimbus.model.ItemCoreTeam;

import java.util.ArrayList;

/**
 * Created by sukhbir on 7/3/16.
 */
public class CoreTeamAdapter extends RecyclerView.Adapter<CoreTeamAdapter.ViewHolder> {

    Context context;
    ArrayList<ItemCoreTeam> list;
    
    public CoreTeamAdapter(ArrayList<ItemCoreTeam> list,Context context) {
        this.list = list;
        this.context=context;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.core_team_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if(list.get(i).getdesignation()==null&&list.get(i).getname()==null){
            viewHolder.designation.setVisibility(View.GONE);
            viewHolder.name.setVisibility(View.GONE);
        }
        else{
            viewHolder.name.setText(list.get(i).getname());
            viewHolder.designation.setText(list.get(i).getdesignation());}
        viewHolder.imageView.setImageResource(list.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name,designation;
        ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            name= (TextView) v.findViewById(R.id.text_name);
            designation= (TextView) v.findViewById(R.id.text_designation);
            imageView= (ImageView) v.findViewById(R.id.imageView_core_team);
        }
    }

}

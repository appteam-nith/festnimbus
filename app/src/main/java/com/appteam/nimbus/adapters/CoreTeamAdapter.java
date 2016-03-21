package com.appteam.nimbus.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.appteam.nimbus.singleton.MySingleton;
import com.appteam.nimbus.R;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.model.ItemCoreTeam;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by sukhbir on 7/3/16.
 */
public class CoreTeamAdapter extends RecyclerView.Adapter<CoreTeamAdapter.ViewHolder> {

    Context context;
    ArrayList<ItemCoreTeam> list;
    private ImageLoader imageLoader;
    public CoreTeamAdapter(ArrayList<ItemCoreTeam> list,Context context) {
        this.list = list;
        this.context=context;
        imageLoader= MySingleton.getInstance(context).getImageLoader();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.core_team_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if(!list.get(i).name.isEmpty()&&list.get(i).name.length()!=0){
            viewHolder.name.setText(list.get(i).name);
        }
        if(!list.get(i).designation.isEmpty()&&list.get(i).designation.length()!=0){
            viewHolder.designation.setText(list.get(i).designation);
        }
        if(!list.get(i).url.isEmpty()&&list.get(i).url.length()!=0){
            Glide.with(context).load(list.get(i).url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.person_icon).error(R.mipmap.nimbus_icon).transform(new Utils.CircleTransform(context)).into(viewHolder.imageView);
        }
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

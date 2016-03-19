package com.appteam.nimbus.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appteam.nimbus.R;
import com.appteam.nimbus.model.SponsorItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class SponserAdapter extends RecyclerView.Adapter<SponserAdapter.ViewHolder> {
private Context context;
    private ArrayList<SponsorItem> list=new ArrayList<>();

    public SponserAdapter(Context context, ArrayList<SponsorItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.core_team_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.none.setVisibility(View.GONE);
        if(!list.get(position).name.isEmpty()&&list.get(position).name.length()!=0){
            holder.name.setText(list.get(position).name);
        }
        if(!list.get(position).name.isEmpty()&&list.get(position).name.length()!=0){
            Glide.with(context).load(list.get(position).image_url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.person_icon).error(R.mipmap.nimbus_icon).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,none;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.text_name);
            none= (TextView) itemView.findViewById(R.id.text_designation);
            imageView= (ImageView) itemView.findViewById(R.id.imageView_core_team);
        }
    }
}

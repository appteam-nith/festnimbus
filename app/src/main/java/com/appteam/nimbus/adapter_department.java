package com.appteam.nimbus;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_department extends RecyclerView.Adapter<adapter_department.ViewHolder> {

    private Context context;
    private ArrayList<Item_department> list;

    public adapter_department(Context context, ArrayList<Item_department> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.department,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
   holder.name.setText(list.get(position).name);
   holder.imageView.setImageResource(list.get(position).image_resource);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.name_department);
            imageView= (ImageView) itemView.findViewById(R.id.image_department);
        }
    }
}

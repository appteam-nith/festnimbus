package com.appteam.nimbus.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appteam.nimbus.R;
import com.appteam.nimbus.model.NotificationItem;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private ArrayList<NotificationItem> list;

    public NotificationAdapter(ArrayList<NotificationItem> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(list.get(position).Message!=null){
            if(!list.get(position).Message.isEmpty()&&list.get(position).Message.length()!=0)
                holder.textView.setText(list.get(position).Message);
            else {
                holder.textView.setVisibility(View.GONE);
            }
        }else{
            holder.textView.setVisibility(View.GONE);
        }

        if(!list.get(position).title.isEmpty()&&list.get(position).title.length()!=0)
            holder.title.setText(list.get(position).title);
        else {
            holder.title.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
TextView textView,title;
        public ViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.text_message);
            title= (TextView) itemView.findViewById(R.id.text_title);
        }
    }
}

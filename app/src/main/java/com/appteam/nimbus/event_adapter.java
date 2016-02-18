package com.appteam.nimbus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class event_adapter extends RecyclerView.Adapter<event_adapter.viewHolder> {
    private String list[];
    private Context context;

    public event_adapter(Context context, String  list[]) {
        this.context = context;
        this.list = list;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_event,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
      holder.textView.setText(list[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public viewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.textView_team_event);
        }
    }
}

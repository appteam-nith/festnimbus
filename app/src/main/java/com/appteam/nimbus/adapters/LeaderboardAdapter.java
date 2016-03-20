package com.appteam.nimbus.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appteam.nimbus.R;
import com.appteam.nimbus.model.LeaderboardItem;

import java.util.ArrayList;


public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.viewHolder> {

    private ArrayList<LeaderboardItem> list = new ArrayList<>();
    private String Status;
private  boolean isclick=false;
    public void setStatus(String status) {
        Status = status;
    }

    public void refresh(ArrayList<LeaderboardItem> list) {
        this.list = list;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_leaderboard, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {
        int pos=position;
        int Ranking=++pos;
        holder.rank.setText(Ranking+".");

        if (!list.get(position).email.isEmpty() && list.get(position).email.length() != 0) {
            holder.email.setText(list.get(position).email);
        }
        if (list.get(position).mobile != 0) {
            holder.mobile.setText(""+list.get(position).mobile);
        }
        if (list.get(position).events_register.length != 0) {
            String eventarray[] = list.get(position).events_register;
            String eventString = "Event Register \n";
            for (int i = 0; i < eventarray.length; i++)
                eventString += eventarray[i] + "\n";
            holder.event.setText(eventString);
        }
        if(Status!=null){
            if (Status.equals("Sorted according to silver coins")) {
                if (!list.get(position).gold_coins.isEmpty() && list.get(position).gold_coins.length() != 0) {
                    holder.coin2.setText(list.get(position).gold_coins);
                    holder.gold_image.setImageResource(R.drawable.currency_dollar);
                }
                if (!list.get(position).silver_coins.isEmpty() && list.get(position).silver_coins.length() != 0) {
                    holder.coin1.setText(list.get(position).silver_coins);
                    holder.silver_image.setImageResource(R.drawable.currency_dollar_yellow);
                }
            }
            else {
                if (!list.get(position).gold_coins.isEmpty() && list.get(position).gold_coins.length() != 0) {
                    holder.coin1.setText(list.get(position).gold_coins);
                    holder.gold_image.setImageResource(R.drawable.currency_dollar_yellow);
                }
                if (!list.get(position).silver_coins.isEmpty() && list.get(position).silver_coins.length() != 0) {
                    holder.coin2.setText(list.get(position).silver_coins);
                    holder.silver_image.setImageResource(R.drawable.currency_dollar);
                }
            }
        }

        if (!list.get(position).rollno.isEmpty() && list.get(position).rollno.length() != 0) {
            holder.rollno.setText(list.get(position).rollno);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isclick){
                    holder.linear.setVisibility(View.VISIBLE);
                    holder.border.setVisibility(View.VISIBLE);
                    holder.rollno.setVisibility(View.VISIBLE);
                    holder.event.setVisibility(View.VISIBLE);
                    isclick=true;
                }
                else {
                    holder.linear.setVisibility(View.GONE);
                    holder.border.setVisibility(View.GONE);
                    holder.rollno.setVisibility(View.GONE);
                    holder.event.setVisibility(View.GONE);
                    isclick=false;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView rank, email, coin1, coin2, mobile, event,rollno;
        ImageView gold_image, silver_image;
         ViewGroup linear;
        View border;
        public viewHolder(View itemView) {
            super(itemView);
            border=itemView.findViewById(R.id.line_leaderboard);
            linear= (ViewGroup) itemView.findViewById(R.id.linear_data);
            rollno= (TextView) itemView.findViewById(R.id.textview_rollno);
            rank = (TextView) itemView.findViewById(R.id.textview_no);
            email = (TextView) itemView.findViewById(R.id.textview_email);
            coin1 = (TextView) itemView.findViewById(R.id.textview_coin);
            coin2 = (TextView) itemView.findViewById(R.id.textview_other_coin);
            mobile = (TextView) itemView.findViewById(R.id.textview_mobile);
            event = (TextView) itemView.findViewById(R.id.textview_event_register);
            gold_image = (ImageView) itemView.findViewById(R.id.imageView_coin_image);
            silver_image = (ImageView) itemView.findViewById(R.id.imageView_coin_other_image);
        }
    }
}

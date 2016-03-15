package com.appteam.nimbus.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.appteam.nimbus.MySingleton;
import com.appteam.nimbus.R;
import com.appteam.nimbus.Utils;
import com.appteam.nimbus.Waiting;
import com.appteam.nimbus.model.ItemCoreTeam;

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
            if (Utils.check(list.get(i).url.substring(61))) {
                new Waiting.GetImage(list.get(i).url.substring(61), viewHolder.imageView).execute();
            } else{
                load_image(list.get(i).url, viewHolder);
                Log.d("NOT FOUND","NO FILE FOUND "+list.get(i).url);
            }

        }
    }
    private void load_image(  final String url_photo, final ViewHolder viewHolder) {
        imageLoader.get(url_photo, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap d = imageContainer.getBitmap();
                if(d!=null){
                   // int nh = (int) ( d.getHeight() * (100.0 / d.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(d, 100,100, true);
                    viewHolder.imageView.setImageBitmap(scaled);
                    new Waiting.SaveImage(d, url_photo.substring(61)).execute();
                }
                else {
                    Log.d("Error","Error while downloading "+url_photo);
                }

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
               // volleyError.printStackTrace();
                viewHolder.imageView.setImageResource(R.drawable.person_icon);
            }
        });
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

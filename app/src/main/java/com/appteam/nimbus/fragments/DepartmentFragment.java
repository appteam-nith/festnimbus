package com.appteam.nimbus.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appteam.nimbus.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends Fragment {


    private static final String NAME ="Department Name" ;
    private static final String DETAIl="detail";
    private static final String IMAGE_ID = "image";
    private static final String TEAM_DETAIl="teamdetail";
    private static final String CONTACT_DETAIl="contactdetail";

    private  String USER_NAME,USER_DETAIL,USER_IMAGE_ID,USER_TEAM_DETAIL,USER_CONTACT_DETAIL;
    public DepartmentFragment() {
        // Required empty public constructor
    }
public  static Fragment newInstance(String departmentname,String detail,String url,String teamDetail,String contactDetail ){
    DepartmentFragment fragment=new DepartmentFragment();
    Bundle bundle=new Bundle();
    bundle.putString(NAME,departmentname);
    bundle.putString(DETAIl,detail);
    bundle.putString(TEAM_DETAIl,teamDetail);
    bundle.putString(CONTACT_DETAIl,contactDetail);
    bundle.putString(IMAGE_ID,url);
    fragment.setArguments(bundle);
    return  fragment;
}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        USER_NAME=getArguments().getString(NAME,"USERNAME");
        USER_DETAIL=getArguments().getString(DETAIl,"USER DETAIl");
        USER_IMAGE_ID=getArguments().getString(IMAGE_ID,"");
        USER_TEAM_DETAIL=getArguments().getString(TEAM_DETAIl,"Team Detail");
        USER_CONTACT_DETAIL=getArguments().getString(CONTACT_DETAIl,"Contact Detail");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_department, container, false);
        TextView name_textView= (TextView) v.findViewById(R.id.team_name_department);
        final TextView content_textView= (TextView) v.findViewById(R.id.team_detail_department);
        ImageView imageView= (ImageView) v.findViewById(R.id.image_department);
        name_textView.setText(USER_NAME);
        content_textView.setText(USER_DETAIL);
        final View team_detail=v.findViewById(R.id.department_team_detail);
        final View contact_detail=v.findViewById(R.id.department_contact_detail);
        TextView textView_team_detail= (TextView) team_detail.findViewById(R.id.team_detail_description);
        TextView textView_contact_detail= (TextView) contact_detail.findViewById(R.id.contact_detail_description);
        final Button more_detail= (Button) v.findViewById(R.id.more_detail_department);
       more_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content_textView.setVisibility(View.GONE);
                more_detail.setVisibility(View.GONE);
                team_detail.setVisibility(View.VISIBLE);
                contact_detail.setVisibility(View.VISIBLE);
            }
        });
        textView_contact_detail.setText(USER_CONTACT_DETAIL);
        textView_team_detail.setText(USER_TEAM_DETAIL);
        Log.d("url",USER_IMAGE_ID);
        Glide.with(getActivity()).load(USER_IMAGE_ID).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.nimbus4).into(imageView);
        return v;
    }

}

package com.appteam.nimbus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends Fragment {


    private static final String NAME ="Department Name" ;
    private static final String DETAIl="detail";
    private static final String IMAGE_ID = "image";

    private  String USER_NAME,USER_DETAIL,USER_IMAGE_ID;
    public DepartmentFragment() {
        // Required empty public constructor
    }
public  static Fragment newInstance(String departmentname,String detail,int image_id ){
    DepartmentFragment fragment=new DepartmentFragment();
    Bundle bundle=new Bundle();
    bundle.putString(NAME,departmentname);
    bundle.putString(DETAIl,detail);
    bundle.putInt(IMAGE_ID,image_id);
    fragment.setArguments(bundle);
    return  fragment;
}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        USER_NAME=getArguments().getString(NAME,"USERNAME");
        USER_DETAIL=getArguments().getString(DETAIl,"USER DETAIl");
        USER_IMAGE_ID=getArguments().getString(USER_IMAGE_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_department, container, false);
        TextView name_textView= (TextView) v.findViewById(R.id.team_name_department);
        TextView content_textView= (TextView) v.findViewById(R.id.team_detail_department);
        ImageView imageView= (ImageView) v.findViewById(R.id.image_department);
        name_textView.setText(USER_NAME);
        content_textView.setText(USER_DETAIL);
        return v;
    }

}

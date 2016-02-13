package com.appteam.nimbus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends Fragment {


    private static final String NAME ="Department Name" ;
    private static final String DETAIl="detail";
    private static final String IMAGE_ID = "image";

    public DepartmentFragment() {
        // Required empty public constructor
    }
public  static Fragment newInstance(String departmentname,String detail,int image_id ){
    DepartmentFragment fragment=new DepartmentFragment();
    Bundle bundle=new Bundle();
    bundle.putString(NAME,departmentname);
    bundle.putString(DETAIl,detail);
    bundle.putInt(IMAGE_ID,image_id);
    return  fragment;
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_department, container, false);
    }

}

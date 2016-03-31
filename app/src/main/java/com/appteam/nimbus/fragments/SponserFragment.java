package com.appteam.nimbus.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam.nimbus.R;
import com.appteam.nimbus.adapters.SponserAdapter;
import com.appteam.nimbus.model.SponsorItem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SponserFragment extends Fragment {

    private String BASE_URL="https://s3-ap-southeast-1.amazonaws.com/nimbus2k16/sponsers/";
    private static final String TYPE ="type" ;
    private RecyclerView recyclerView;
    private String type;

    public SponserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getArguments().getString(TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sponser, container, false);
        recyclerView= (RecyclerView) v.findViewById(R.id.recyclerview_sponsor);
        ArrayList<SponsorItem> list=getList();
        SponserAdapter adapter=new SponserAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v ;
    }
    public static Fragment newInstance(String type){
        SponserFragment fragment=new SponserFragment();
        Bundle bundle=new Bundle();
        bundle.putString(TYPE,type);
        fragment.setArguments(bundle);
        return fragment;
    }
    private ArrayList<SponsorItem> getList(){
        ArrayList<SponsorItem> list=new ArrayList<>();
        if(type!=null){
            switch(type){
                case "Educational Partner":
                    list.add(new SponsorItem("Projectineer Noida",BASE_URL+"11.png"));
                    list.add(new SponsorItem("All Innovative Technology Mohali",BASE_URL+"13.png"));
                    list.add(new SponsorItem("Aptron Solutions Pvt. Ltd. Noida",BASE_URL+"12.png"));
                    break;
                case "Electronics Partner":
                    list.add(new SponsorItem("Zebronics",BASE_URL+"15.png"));
                    break;
                case "Food Partner":
                    list.add(new SponsorItem("Delhi 6 Zayka",BASE_URL+"delhi6.png"));
                    break;
                case "Sponsors":
                    list.add(new SponsorItem("Honda (Maxim Honda)",BASE_URL+"01.jpg"));
                    list.add(new SponsorItem("Renault (Mandi)",BASE_URL+"02.png"));
                    list.add(new SponsorItem("Tata Motors (Hamirpur)",BASE_URL+"03.png"));
                    list.add(new SponsorItem("Mahindra 2-wheeler (Narendra Motors)",BASE_URL+"04.png"));
                    list.add(new SponsorItem("Made Easy Delhi",BASE_URL+"05.png"));
                    list.add(new SponsorItem("ACE Engineering Academy",BASE_URL+"06.png"));
                    list.add(new SponsorItem("App4pc",BASE_URL+"07.png"));
                    list.add(new SponsorItem("Ebay",BASE_URL+"08.png"));
                    list.add(new SponsorItem("Archies",BASE_URL+"14.png"));
                    list.add(new SponsorItem("Antariksh Mall Hamirpur",BASE_URL+"antariksh.png"));
                    list.add(new SponsorItem("AV Pizzas Hamirpur",BASE_URL+"avpizza.png"));
                    list.add(new SponsorItem("Tata Sky",BASE_URL+"09.png"));
                    list.add(new SponsorItem("Cadd Center (Hamirpur)",BASE_URL+"10.png"));
                    break;
            }
        }
        return list;
    }

}

package com.example.identity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class allowed_websites extends Fragment {
    RecyclerView subjectList;
    allowed_website_adapter usera;
    List<allowed_web_info> pr1;

    public allowed_websites() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_allowed_websites, container, false);


        try {
//            RecyclerView subjectList;

            subjectList = (RecyclerView) view.findViewById(R.id.rec2);
            final FragmentActivity c = getActivity();
            RecyclerView.LayoutManager m1LayoutManager = new LinearLayoutManager(c);
            subjectList.setLayoutManager(m1LayoutManager);
            // subjectList.setItemAnimator(new DefaultItemAnimator());
            List<allowed_web_info> subjects = new ArrayList<allowed_web_info>();
         //  String s1[]={"data","name"};


                subjects.add(new allowed_web_info("google.com","name email"));
            subjects.add(new allowed_web_info("amazon.com","pan card mobile"));



//            subjects.add(new User_details("email", "value", "m1"));

            usera = new allowed_website_adapter(subjects, getContext());
            subjectList.setAdapter(usera);
            usera.setOnItemClickListner(new allowed_website_adapter.OnItemClickListner() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getContext(),""+position,Toast.LENGTH_LONG).show();
                }
            });

        }catch (Exception e)
        {
            Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
        }
        return view;
    }

}

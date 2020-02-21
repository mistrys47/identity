package com.example.identity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class user_details_card extends Fragment {

    RecyclerView subjectList;
    useradapter usera;
    List<User_details> pr1;
    public user_details_card() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_user_details_card, container, false);
//        pr1=new ArrayList<User_details>();
//        recyclerView=(RecyclerView)getView().findViewById(R.id.rec1);
//        recyclerView.setHasFixedSize(true);
//
        try {
//            RecyclerView subjectList;

            subjectList = (RecyclerView) view.findViewById(R.id.rec1);
            final FragmentActivity c = getActivity();
            RecyclerView.LayoutManager m1LayoutManager = new LinearLayoutManager(c);
            subjectList.setLayoutManager(m1LayoutManager);
            // subjectList.setItemAnimator(new DefaultItemAnimator());
            List<User_details> subjects = new ArrayList<User_details>();
            database db = new database(getActivity());
            SQLiteDatabase db1 = db.getWritableDatabase();
            Cursor cm = db.getuserdetails(db1);
            while (cm.moveToNext()){
                String x=cm.getString(2);
                final String s = cm.getString(0);
                final String s1 = cm.getString(1);

                    subjects.add(new User_details(s, s1, x));


            }

//            subjects.add(new User_details("email", "value", "m1"));

            usera = new useradapter(subjects, getContext());
            subjectList.setAdapter(usera);

        }catch (Exception e)
        {
            Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
        }
        return view;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


}

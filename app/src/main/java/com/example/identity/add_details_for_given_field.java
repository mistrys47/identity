package com.example.identity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;





public class add_details_for_given_field extends Fragment {


    public add_details_for_given_field() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle args = getArguments();


        String m1 = getArguments().getString("field");

       // Toast.makeText(getContext(),"Hi"+m1,Toast.LENGTH_LONG).show();
        final View view= inflater.inflate(R.layout.fragment_add_details_for_given_field, container, false);

        return view;

    }

    @Override
    public  void onResume() {
        try {
            //   ((Main2Activity) getActivity()).getSupportActionBar().setTitle("hi");
        } catch (Exception e) {
            Log.e("action bar error::", String.valueOf(e));
        }
        super.onResume();
    }

}

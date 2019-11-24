package com.example.identity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;





public class m1 extends Fragment {


    public m1() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_m1, container, false);

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

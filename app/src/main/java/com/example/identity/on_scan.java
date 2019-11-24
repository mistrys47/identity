package com.example.identity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;





public class on_scan extends Fragment {



    public on_scan() {
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

        super.onResume();
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        Toast.makeText(getContext(),args.toString(),Toast.LENGTH_LONG).show();
        database db = new database(getActivity());
        SQLiteDatabase db1 = db.getWritableDatabase();


    }

}

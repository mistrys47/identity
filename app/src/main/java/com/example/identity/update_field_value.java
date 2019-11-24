package com.example.identity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;





public class update_field_value extends Fragment {
EditText e1;
TextView t1;

    public update_field_value() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view= inflater.inflate(R.layout.fragment_update_field_value, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        //Toast.makeText(getActivity(),mBundle.getString("value"),Toast.LENGTH_LONG).show();
         t1 = view.findViewById(R.id.t1);
         e1 = view.findViewById(R.id.e1);
        t1.setText(mBundle.getString("field"));
        e1.setHint(mBundle.getString("value"));
        Button b=view.findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"hiiii",Toast.LENGTH_LONG).show();
                try{
                String s = e1.getText().toString();
                String s1 = t1.getText().toString();
                database db = new database(getActivity());
                SQLiteDatabase db1 = db.getWritableDatabase();
                db.updatevalue(db1,s1,s);
                userdetails frag = new userdetails();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl1,frag).addToBackStack(null).commit();}
                catch (Exception e)
                {
                    Toast.makeText(getActivity(),e+"",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}

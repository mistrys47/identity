package com.example.identity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


public class on_scan extends Fragment {

TextView t1;
LinearLayout l1,l2,l3,l4;

    public on_scan() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_on_scan, container, false);

        return view;

    }

    @Override
    public  void onResume() {

        super.onResume();
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        t1=(TextView)getView().findViewById(R.id.t1);
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        l1=(LinearLayout)getView().findViewById(R.id.l1);

        String strtext = getArguments().getString("user");
        Toast.makeText(getContext(),args.toString(),Toast.LENGTH_LONG).show();
        database db = new database(getActivity());
        SQLiteDatabase db1 = db.getWritableDatabase();
        Gson gson = new Gson();
        qr_code_data json = gson.fromJson(strtext,qr_code_data.class);
        t1.setText(t1.getText().toString()+" "+json.name+json.url);

        qr_info_data all_info[]=json.fields;
        String s="";

        for(int i=0;i<all_info.length;i++) {
            // all_info[i].field;
            int exist = db.checkfield(db1, all_info[i].field);
            if (exist == 1) {


//field

                LinearLayout f = new LinearLayout(getActivity());
                f.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        100));
                f.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 3));
                f1.setOrientation(LinearLayout.VERTICAL);

                TextView t1 = new TextView(getActivity());
                t1.setText(all_info[i].field);
                t1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                t1.setPadding(10, 0, 0, 0);
                t1.setTextSize(15);

                t1.setTypeface(null, Typeface.BOLD);

                f1.addView(t1);
                f.addView(f1);


                //value
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 4));
                f1.setOrientation(LinearLayout.VERTICAL);
                t1 = new TextView(getActivity());

                String val = db.getvalue(db1, all_info[i].field);
                t1.setText(val);
                t1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                t1.setPadding(10, 0, 0, 0);
                t1.setTextSize(15);
                t1.setTypeface(null, Typeface.BOLD);
                //t1.setGravity(Gravity.CENTER);
                f1.addView(t1);
                f.addView(f1);


                //required
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 2));
                f1.setOrientation(LinearLayout.VERTICAL);
                t1 = new TextView(getActivity());
                if(all_info[i].required) {
                    t1.setText("True");
                }
                else
                {
                    t1.setText("False");
                }
                t1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                t1.setPadding(10, 0, 0, 0);
                t1.setTextSize(15);
                t1.setTypeface(null, Typeface.BOLD);
                //t1.setGravity(Gravity.CENTER);
                f1.addView(t1);
                f.addView(f1);

                //checkbox
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 2));
                f1.setOrientation(LinearLayout.VERTICAL);
                CheckBox c1 = new CheckBox(getActivity());
                c1.setChecked(true);
                c1.setGravity(Gravity.CENTER);
                c1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                f1.addView(c1);
                f.addView(f1);
                l1.addView(f);


            }
        }
        for(int i=0;i<all_info.length;i++)
        {
            // all_info[i].field;
            int exist=db.checkfield(db1,all_info[i].field);
        if(exist==-1)
            {
                LinearLayout f = new LinearLayout(getActivity());
                f.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        100));
                f.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,3));
                f1.setOrientation(LinearLayout.VERTICAL);

                TextView t1 = new TextView(getActivity());
                t1.setText(all_info[i].field);
                t1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                t1.setPadding(10, 0, 0, 0);
                t1.setTextSize(15);

                t1.setTypeface(null, Typeface.BOLD);

                f1.addView(t1);
                f.addView(f1);
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,4));
                f1.setOrientation(LinearLayout.VERTICAL);
                Button b1 = new Button(getActivity());
                b1.setText("Add Details");
                b1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.MATCH_PARENT));
                b1.setGravity(Gravity.CENTER);
                b1.setBackgroundColor(Color.TRANSPARENT);
                final String sm=all_info[i].field;

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        add_details_for_given_field frag = new add_details_for_given_field();
                        Bundle b = new Bundle();
                        b.putString("field", sm);

                        frag.setArguments(b);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fl1,frag).addToBackStack(null).commit();
                    }
                });

                f1.addView(b1);
                f.addView(f1);
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f1.setOrientation(LinearLayout.VERTICAL);
                t1 = new TextView(getActivity());
                if(all_info[i].required) {
                    t1.setText("True");
                }
                else
                {
                    t1.setText("False");
                }
                t1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                t1.setPadding(10, 0, 0, 0);
                t1.setTextSize(15);
                t1.setTypeface(null, Typeface.BOLD);
                //t1.setGravity(Gravity.CENTER);
                f1.addView(t1);
                f.addView(f1);
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f1.setOrientation(LinearLayout.VERTICAL);
                CheckBox c1 = new CheckBox(getActivity());
                c1.setChecked(false);
                c1.setEnabled(false);
                c1.setGravity(Gravity.CENTER);
                c1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                f1.addView(c1);
                f.addView(f1);
                l1.addView(f);
            }}
        for(int i=0;i<all_info.length;i++)
        {
            // all_info[i].field;
            int exist=db.checkfield(db1,all_info[i].field);
            if(exist==0) {
                LinearLayout f = new LinearLayout(getActivity());
                f.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int)(40*getResources().getDisplayMetrics().density)));
                f.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,3));
                f1.setOrientation(LinearLayout.VERTICAL);

                TextView t1 = new TextView(getActivity());
                t1.setText(all_info[i].field);
                t1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                t1.setPadding(10, 0, 0, 0);
                t1.setTextSize(15);

                t1.setTypeface(null, Typeface.BOLD);

                f1.addView(t1);
                f.addView(f1);
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,4));
                f1.setOrientation(LinearLayout.VERTICAL);
                Button b1 = new Button(getActivity());
                b1.setText("Verification Pending");
                b1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.MATCH_PARENT));
                b1.setGravity(Gravity.CENTER);
                b1.setBackgroundColor(Color.TRANSPARENT);
                final String sm=all_info[i].field;

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userdetails frag = new userdetails();
                        Bundle b = new Bundle();
                        b.putString("field", sm);

                        frag.setArguments(b);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fl1,frag).addToBackStack(null).commit();
                    }
                });

                f1.addView(b1);
                f.addView(f1);
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f1.setOrientation(LinearLayout.VERTICAL);
                t1 = new TextView(getActivity());
                if(all_info[i].required) {
                    t1.setText("True");
                }
                else
                {
                    t1.setText("False");
                }
                t1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                t1.setPadding(10, 0, 0, 0);
                t1.setTextSize(15);
                t1.setTypeface(null, Typeface.BOLD);
                //t1.setGravity(Gravity.CENTER);
                f1.addView(t1);
                f.addView(f1);
                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f1.setOrientation(LinearLayout.VERTICAL);
                CheckBox c1 = new CheckBox(getActivity());
                c1.setChecked(false);
                c1.setEnabled(false);
                c1.setGravity(Gravity.CENTER);
                c1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                f1.addView(c1);
                f.addView(f1);
                l1.addView(f);
            }
          //  c1.setText();

        }

       // Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();

    }

}

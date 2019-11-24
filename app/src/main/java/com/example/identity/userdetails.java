package com.example.identity;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class userdetails extends Fragment {

    public userdetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userdetails, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database db = new database(getActivity());
        SQLiteDatabase db1 = db.getWritableDatabase();
        LinearLayout ll11 = view.findViewById(R.id.l11);
        LinearLayout ll12 = view.findViewById(R.id.l12);
        LinearLayout ll21 = view.findViewById(R.id.l21);
        LinearLayout ll22 = view.findViewById(R.id.l22);
        float scale = getResources().getDisplayMetrics().density;
        int leftpadding = (int) (5*scale + 0.5f);
        int height = (int) (40*scale + 0.5f);
        boolean flag1=false,flag2=false;
        Cursor c = db.getuserdetails(db1);
        while (c.moveToNext()) {
            LinearLayout l1=new LinearLayout(getActivity());
            l1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    height));
            l1.setOrientation(LinearLayout.HORIZONTAL);
            String x=c.getString(2);
            if (x.equals("false")) {
                //field1
                l1.setWeightSum(9);
                LinearLayout f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f1.setOrientation(LinearLayout.VERTICAL);

                TextView t1 = new TextView(getActivity());
                t1.setText(c.getString(0));
                t1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
                t1.setPadding(leftpadding, 0, 0, 0);
                t1.setTextSize(15);
                t1.setGravity(Gravity.CENTER_VERTICAL);
                t1.setTypeface(null, Typeface.BOLD);
                t1.setTextColor(Color.BLUE);
                f1.addView(t1);

                //field2
                LinearLayout f2 = new LinearLayout(getActivity());
                f2.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,3));
                f2.setOrientation(LinearLayout.VERTICAL);

                TextView t2 = new TextView(getActivity());
                t2.setText(c.getString(1));
                t2.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
                t2.setGravity(Gravity.CENTER_VERTICAL);
                t2.setPadding(leftpadding, 0, 0, 0);
                t2.setTextSize(15);
                f2.addView(t2);

                //field3
                LinearLayout f3 = new LinearLayout(getActivity());
                f3.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f3.setOrientation(LinearLayout.VERTICAL);
                TextView t3 = new TextView(getActivity());
                t3.setText(c.getString(3));
                t3.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
                t3.setGravity(Gravity.CENTER_VERTICAL);
                t3.setPadding(leftpadding, 0, 0, 0);
                t3.setTextSize(15);
                f3.addView(t3);

                //field4
                LinearLayout f4 = new LinearLayout(getActivity());
                f4.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f4.setOrientation(LinearLayout.VERTICAL);
                Button b1 = new Button(getActivity());
                b1.setText("Update");
                b1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT));
                b1.setGravity(Gravity.CENTER);
                b1.setBackgroundColor(Color.TRANSPARENT);
                final String s = c.getString(0);
                final String s1 = c.getString(1);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        update_field_value frag = new update_field_value();
                        Bundle b = new Bundle();
                        b.putString("field", s);
                        b.putString("value",s1);
                        frag.setArguments(b);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fl1,frag).addToBackStack(null).commit();
                    }
                });
                if(!s.equals("email") && !s.equals("mobile"))
                f4.addView(b1);

                l1.addView(f1);
                l1.addView(f2);
                l1.addView(f3);
                l1.addView(f4);
                ll12.addView(l1);
                flag1=true;
            }
            else
            {
                //field1
                l1.setWeightSum(7);
                LinearLayout f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f1.setOrientation(LinearLayout.VERTICAL);
                TextView t1 = new TextView(getActivity());
                t1.setText(c.getString(0));
                t1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
                t1.setPadding(leftpadding, 0, 0, 0);
                t1.setGravity(Gravity.CENTER_VERTICAL);
                t1.setTextSize(15);
                t1.setTypeface(null, Typeface.BOLD);
                t1.setTextColor(Color.BLUE);
                f1.addView(t1);

                //field2
                LinearLayout f2 = new LinearLayout(getActivity());
                f2.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,3));
                f2.setOrientation(LinearLayout.VERTICAL);
                TextView t2 = new TextView(getActivity());
                t2.setText(c.getString(1));
                t2.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
                t2.setPadding(leftpadding, 0, 0, 0);
                t2.setGravity(Gravity.CENTER_VERTICAL);
                t2.setTextSize(15);
                f2.addView(t2);

                //field4
                LinearLayout f4 = new LinearLayout(getActivity());
                f4.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,2));
                f4.setOrientation(LinearLayout.VERTICAL);
                Button b1 = new Button(getActivity());
                b1.setText("Update");
                b1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.MATCH_PARENT));
                b1.setGravity(Gravity.CENTER);
                b1.setBackgroundColor(Color.TRANSPARENT);
                final String s=c.getString(0);
                final String s1=c.getString(1);

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        update_field_value frag = new update_field_value();
                        Bundle b = new Bundle();
                        b.putString("field", s);
                        b.putString("value",s1);
                        frag.setArguments(b);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fl1,frag).addToBackStack(null).commit();
                    }
                });
                if(!s.equals("email") && !s.equals("mobile"))
                f4.addView(b1);

                l1.addView(f1);
                l1.addView(f2);
                l1.addView(f4);
                ll22.addView(l1);
                flag2=true;
            }
        }
        if(flag1==false)
        {

            ll11.removeAllViews();
            TextView t1=new TextView(getActivity());
            t1.setText("No items");
            t1.setGravity(Gravity.CENTER);
            t1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
            t1.setTextSize(15);
            ll11.addView(t1);
        }
        if(flag2==false)
        {
            ll22.removeAllViews();
            TextView t1=new TextView(getActivity());
            t1.setText("No items");
            t1.setGravity(Gravity.CENTER);
            t1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
            t1.setTextSize(15);
            ll21.addView(t1);
        }
    }

}

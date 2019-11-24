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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        //Toast.makeText(getContext(),"hii",Toast.LENGTH_LONG).show();

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
        LinearLayout ll11 = view.findViewById(R.id.ll11);
        LinearLayout ll12 = view.findViewById(R.id.ll12);
        LinearLayout ll13 = view.findViewById(R.id.ll13);
        LinearLayout ll21 = view.findViewById(R.id.ll21);
        LinearLayout ll22 = view.findViewById(R.id.ll22);
        float scale = getResources().getDisplayMetrics().density;
        int leftpadding = (int) (5*scale + 0.5f);
        boolean flag1=false,flag2=false;
        Cursor c = db.getuserdetails(db1);
        while (c.moveToNext()) {
            if (c.getString(2) == "false") {
                TextView t1 = new TextView(getActivity());
                t1.setText(c.getString(0));
                t1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
                t1.setPadding(leftpadding, 0, 0, 0);
                t1.setTextSize(15);
                t1.setTypeface(null, Typeface.BOLD);
                t1.setTextColor(Color.BLUE);
                ll11.addView(t1);

                TextView t2 = new TextView(getActivity());
                t2.setText(c.getString(1));
                t2.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
                t2.setPadding(leftpadding, 0, 0, 0);
                t2.setTextSize(15);
                ll12.addView(t2);

                TextView t3 = new TextView(getActivity());
                t3.setText(c.getString(3));
                t3.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
                t3.setPadding(leftpadding, 0, 0, 0);
                t3.setTextSize(15);
                ll13.addView(t3);
                flag1=true;
            }
            else
            {
                TextView t1 = new TextView(getActivity());
                t1.setText(c.getString(0));
                t1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
                t1.setPadding(leftpadding, 0, 0, 0);
                t1.setTextSize(15);
                t1.setTypeface(null, Typeface.BOLD);
                t1.setTextColor(Color.BLUE);
                ll21.addView(t1);

                TextView t2 = new TextView(getActivity());
                t2.setText(c.getString(1));
                t2.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
                t2.setPadding(leftpadding, 0, 0, 0);
                t2.setTextSize(15);
                ll22.addView(t2);
                flag2=true;
            }
        }
        if(flag1==false)
        {
            LinearLayout l1=view.findViewById(R.id.l1);
            l1.removeAllViews();
            TextView t1=new TextView(getActivity());
            t1.setText("No items");
            t1.setGravity(Gravity.CENTER);
            t1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
            t1.setTextSize(15);
            l1.addView(t1);
        }
        if(flag2==false)
        {
            LinearLayout l2=view.findViewById(R.id.l2);
            l2.removeAllViews();
            TextView t1=new TextView(getActivity());
            t1.setText("No items");
            t1.setGravity(Gravity.CENTER);
            t1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
            t1.setTextSize(15);
            l2.addView(t1);
        }
    }
}

package com.example.identity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class on_scan extends Fragment {

TextView t1;
LinearLayout l1,l2,l3,l4;
String URL1;
String data1;
String b4,emailmmm,emailkey;
    Boolean bm1;
    qr_code_data json;
    String check1111;
    String veri;
JSONObject jj1,jj2,jo ;
Integer cnt=0,cnt1=0;
    public on_scan() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view= inflater.inflate(R.layout.fragment_on_scan, container, false);

        return view;

    }

    @Override
    public  void onResume() {

        super.onResume();
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cnt=0;cnt1=0;
        t1=(TextView)getView().findViewById(R.id.t1);

        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        l1=(LinearLayout)getView().findViewById(R.id.l1);
        jo=new JSONObject();
        jj1=new JSONObject();
        jj2=new JSONObject();
        String strtext = getArguments().getString("user");

        //database connection
        database db = new database(getActivity());
        SQLiteDatabase db1 = db.getWritableDatabase();
        Gson gson = new Gson();
        json = gson.fromJson(strtext,qr_code_data.class);
        t1.setText(t1.getText().toString()+" "+json.url);
        bm1=db.check_serviceprovider(db1,json.url);
        URL1=json.url;


        emailmmm = db.getvalue(db1, "email");
        emailkey=db.gettransaction_id(db1,"email");

        try {
            JSONObject om = new JSONObject();

            om.put("key", emailkey);
            om.put("value", emailmmm);
            jj1.put("email",om);
            jj2.put("email",om);
            jj2.put("signup","false");
            jj1.put("signup","true");

        }catch (Exception e)
        {
            Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
        }

        qr_info_data all_info[]=json.fields;
        String s="";
        int cm11=all_info.length;
        for(int i=0;i<all_info.length;i++) {
            int exist = db.checkfield(db1, all_info[i].field.toLowerCase());
            JSONObject jj=new JSONObject();
            if (exist == 1) {
                cm11--;
                if(all_info[i].required){
                    cnt++;
                    cnt1++;
                }
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

                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 4));
                f1.setOrientation(LinearLayout.VERTICAL);
                t1 = new TextView(getActivity());

                String val = db.getvalue(db1, all_info[i].field.toLowerCase());
                String val1 = db.gettransaction_id(db1,all_info[i].field.toLowerCase());
                Toast.makeText(getContext(),""+val1,Toast.LENGTH_LONG).show();
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
                    veri="true";
                    try {

                        jj.put("value", val);
                        jj.put("key",val1);
                        if(!all_info[i].field.toLowerCase().equals("email"))
                        jj1.put(all_info[i].field.toLowerCase(),jj);
                        Toast.makeText(getContext(),jj1.toString(),Toast.LENGTH_LONG).show();
                    }catch (Exception e)
                    {
                        Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    veri="false";
                    t1.setText("False");
                }
                t1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                t1.setPadding(10, 0, 0, 0);
                t1.setTextSize(15);
                t1.setTypeface(null, Typeface.BOLD);

                f1.addView(t1);
                f.addView(f1);


                f1 = new LinearLayout(getActivity());
                f1.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 2));
                f1.setOrientation(LinearLayout.VERTICAL);
                final CheckBox c1 = new CheckBox(getActivity());
                final String ve=veri;
                c1.setChecked(true);
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                        if (!c1.isChecked() && ve.equals("true"))
                        {
                            cnt1--;
                            if((Button)getView().findViewWithTag("select_button")!=null){
                            Button b1=(Button)getView().findViewWithTag("select_button");
                            ViewGroup layout = (ViewGroup) b1.getParent();
                            if(null!=layout)
                                layout.removeView(b1);

                        }}
                        else if(c1.isChecked() && ve.equals("true")){
                            cnt1++;
                            if(cnt==cnt1) {
                                try {
                                    fn();
                                }catch (Exception e)
                                {
                                    Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
                    }}

                });
                c1.setGravity(Gravity.CENTER);
                c1.setId(i);
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
            int exist=db.checkfield(db1,all_info[i].field.toLowerCase());
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
                final String sm=all_info[i].field.toLowerCase();

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        add_user_details frag = new add_user_details();
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
                    cm11--;
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

            int exist=db.checkfield(db1,all_info[i].field.toLowerCase());
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
                b1.setText("Verification");
                b1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.MATCH_PARENT));
                b1.setGravity(Gravity.CENTER);
                b1.setBackgroundColor(Color.TRANSPARENT);
                final String sm=all_info[i].field.toLowerCase();

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user_details_card frag = new user_details_card();
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
                    cm11--;
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

        }
        if(cm11==0)
        {
            Button c1 = new Button(getActivity());
            // c1.setChecked(true);
            c1.setTag("select_button");
            c1.setGravity(Gravity.CENTER);
            c1.setText("Allow");
            c1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    100));

            final String m1=json.sid;
            JSONObject jj3;
           if(bm1)
           {
               jj3=jj2;
           }
           else
           {
               jj3=jj1;
           }
            final JSONObject jj4=jj3;
          // Toast.makeText(getContext(),jj2.toString(),Toast.LENGTH_LONG).show();

            c1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                      //  Toast.makeText(getContext(), ""+jj4.toString(), Toast.LENGTH_LONG).show();
                         new on_scan.AsyncLogin().execute(jj4.toString(),m1);
                }
            });
            l1.addView(c1);
            Button c2 = new Button(getActivity());
            // c1.setChecked(true);
            c2.setTag("select_button");
            c2.setGravity(Gravity.CENTER);
            c2.setText("Deny");
            c2.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    100));
            c2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();

                    //  Toast.makeText(getContext(), ""+jj4.toString(), Toast.LENGTH_LONG).show();

                }
            });
            l1.addView(c2);
        }

       if(bm1)
       {
           already_signedup();
       }

    }
    public void fn()
    {
        if((Button)getView().findViewWithTag("select_button")==null){
        Button c1 = new Button(getActivity());

        c1.setTag("select_button");
        c1.setGravity(Gravity.CENTER);
        c1.setText("Submit");
        c1.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                100));


                JSONObject jj3;
                if (bm1) {
                    jj3 = jj2;
                } else {
                    jj3 = jj1;
                }
                final JSONObject jj4=jj3;

        final String m1=json.sid;


       // final String b3=b2;
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new on_scan.AsyncLogin().execute(jj4.toString(),m1);
            }
        });

        l1.addView(c1);
            Button c2 = new Button(getActivity());
            // c1.setChecked(true);
            c2.setTag("select_button");
            c2.setGravity(Gravity.CENTER);
            c2.setText("Deny");
            c2.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    100));
            c2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();

                    //  Toast.makeText(getContext(), ""+jj4.toString(), Toast.LENGTH_LONG).show();

                }
            });
            l1.addView(c2);
        }
    }
    public void already_signedup()
    {
        final String m1=json.sid;
        JSONObject jj3 ;
        if(bm1)
        {
            jj3=jj2;
        }
        else
        {
            jj3=jj1;
        }
        final JSONObject jj4=jj3;
      //  Toast.makeText(getContext(),jj4.toString(),Toast.LENGTH_LONG).show();
        new on_scan.AsyncLogin().execute(jj4.toString(),m1);

    }
    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                JSONObject jo1 = new JSONObject();

                jo1.put( "sid", params[1]);
                data1=params[0];
                jo1.put( "data", params[0]);
                //jo1.put("signup", true);
                check1111=jo1.toString();
                server a=new server();
                String name=a.getServer_name();
                String urll=name+"/service_provider/user/tempdata";
                RequestBody body = RequestBody.create( jo1.toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url(urll)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {
           // res1 = result;
            Toast.makeText(getContext(),""+result,Toast.LENGTH_LONG).show();
            try{
            boolean isFound = result.indexOf("true") !=-1? true: false;
            //Toast.makeText(getContext(),"gsdgh"+result,Toast.LENGTH_LONG).show();
            final database db = new database(getActivity());
            final SQLiteDatabase db1 = db.getWritableDatabase();

            if(isFound)
            {
                if(!bm1)
                {

                     boolean m1=db.insert2(db1,URL1,data1);

                   if(m1) {
                        Toast.makeText(getContext(), "Signed Up" , Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Signed In",Toast.LENGTH_LONG).show();
                }

            }}
            catch (Exception e)
            {
                Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
            }
            pdLoading.dismiss();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();



        }
    }

}

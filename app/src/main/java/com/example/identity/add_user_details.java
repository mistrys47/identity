package com.example.identity;


import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class add_user_details extends Fragment {

    Spinner s1;
    LinearLayout l1;
    EditText e1;
    Button add,remove,submit;
    int cnt = 0;
    String res1;
    Boolean done=false;
    LinearLayout l;
    LinearLayout lx,ly;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    public add_user_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user_details, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final database db = new database(getActivity());
        final SQLiteDatabase db1 = db.getWritableDatabase();
        try {
            new add_user_details.AsyncLogin().execute();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),"err"+e,Toast.LENGTH_LONG).show();
        }


        l1 = getView().findViewById(R.id.linear1);
        add = getView().findViewById(R.id.add);
        remove = getView().findViewById(R.id.remove);
        submit = getView().findViewById(R.id.submit);




        final ArrayList<String> spinnerarray1 = spinnerArray;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt < spinnerarray1.size()) {
                    l = new LinearLayout(getActivity());
                    l.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    l.setOrientation(LinearLayout.HORIZONTAL);

                    l.setId(cnt);

                    lx = new LinearLayout(getActivity());
                    lx.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
                    lx.setOrientation(LinearLayout.VERTICAL);

                    s1 = new Spinner(getActivity());
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerarray1);
                    s1.setAdapter(spinnerArrayAdapter);
                    s1.setLayoutParams(new Spinner.LayoutParams(Spinner.LayoutParams.MATCH_PARENT, Spinner.LayoutParams.WRAP_CONTENT));
                    s1.setTag(cnt);
                    lx.addView(s1);

                    l.addView(lx);
                    ly = new LinearLayout(getActivity());
                    ly.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
                    ly.setOrientation(LinearLayout.VERTICAL);
                    e1 = new EditText(getActivity());
                    e1.setTag("i" + cnt);
                    e1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    ly.addView(e1);
                    l.addView(ly);
                    l1.addView(l);
                    cnt++;
                }
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt>0)
                {

                    LinearLayout lw = getView().findViewById(cnt-1);
                    if(lw!=null) {
                        cnt--;
                        l1.removeView(lw);
                    }
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Integer> items = new HashMap<>();
                for(int i=0;i<cnt;i++){
                    EditText e = getView().findViewWithTag("i"+i);
                    Spinner spinner = getView().findViewWithTag(i);
                    if(e.getText().toString().equals(""))
                    {
                        e.setError("Required");
                        return;
                    }
                    String item = spinner.getSelectedItem().toString();
                    if(items.containsKey(item)) {
                        ((TextView)spinner.getSelectedView()).setError("Message");
                        return;
                    }
                    else
                    items.put(item,1);
                }
                for(int i=0;i<cnt;i++)
                {
                    EditText e = getView().findViewWithTag("i"+i);
                    Spinner spinner = getView().findViewWithTag(i);
                    String item = spinner.getSelectedItem().toString().toLowerCase();
                    String value = e.getText().toString();
                    db.insert1(db1,item,value,"admin","false");

                    //decide where to send user after this is done
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl1,new userdetails()).addToBackStack(null).commit();
                }
            }
        });

    }
    void fn()
    {

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

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

                Request request = new Request.Builder()
                        .url("https://uidserver.herokuapp.com/service_provider/fields")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .build();



                Response response = client.newCall(request).execute();
                //objm=response.tostring();
                // Toast.makeText(email_input.this,"hello"+response,Toast.LENGTH_LONG).show();
            //    done=true;
                return response.body().string();
            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {
            res1=result;
            final database db = new database(getActivity());
            final SQLiteDatabase db1 = db.getWritableDatabase();
            String[] a1 = res1.split("\\[");
            String[] a2=a1[1].split("\\]");
            String[] a3=a2[0].split(",");

            String m1="";
            for(String a : a3) {
                int x = db.checkfield(db1,a.substring(1,a.length()-1).toLowerCase());
                if(x==1 || x==0 )
                    continue;
                spinnerArray.add(a.substring(1,a.length()-1));
            }
            cnt = 0;
            try
            {
                LinearLayout parent = new LinearLayout(getActivity());
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout ll1 = new LinearLayout(getActivity());
                ll1.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,2));
                ll1.setOrientation(LinearLayout.VERTICAL);

                s1 = new Spinner(getActivity());
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                s1.setAdapter(spinnerArrayAdapter);
                s1.setLayoutParams(new Spinner.LayoutParams(Spinner.LayoutParams.MATCH_PARENT, Spinner.LayoutParams.WRAP_CONTENT));
                s1.setTag(cnt);
                ll1.addView(s1);

                parent.addView(ll1);
                LinearLayout ll2 = new LinearLayout(getActivity());
                ll2.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,3));
                ll2.setOrientation(LinearLayout.VERTICAL);
                e1 = new EditText(getActivity());
                e1.setTag("i" + cnt);
                e1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                ll2.addView(e1);
                parent.addView(ll2);
                l1.addView(parent);
                cnt++;
            }
            catch (Exception e)
            {
                Toast.makeText(getActivity(),e+"",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            Toast.makeText(getContext(),"hello"+m1,Toast.LENGTH_LONG).show();
            //this method will be running on UI thread
            pdLoading.dismiss();
        }
    }

}

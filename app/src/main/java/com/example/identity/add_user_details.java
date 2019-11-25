package com.example.identity;


import android.database.sqlite.SQLiteDatabase;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class add_user_details extends Fragment {

    Spinner s1;
    LinearLayout l1;
    EditText e1;
    Button add,remove,submit;
    int cnt = 0;
    LinearLayout l;
    LinearLayout lx,ly;

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
        l1 = getView().findViewById(R.id.linear1);
        add = getView().findViewById(R.id.add);
        remove = getView().findViewById(R.id.remove);
        submit = getView().findViewById(R.id.submit);
        JSONArray arr = new JSONArray();
        arr.put("name");
        arr.put("email");
        arr.put("mobile");
        arr.put("aadhar");

        JSONArray jsonArray1;
        final ArrayList<String> spinnerArray = new ArrayList<String>();
        JSONObject obj1 = new JSONObject();
        try
        {
            cnt = 0;
            obj1.put("fields", arr);
            jsonArray1 = obj1.getJSONArray("fields");
            for(int i = 0; i < jsonArray1.length(); i++) {
                int x = db.checkfield(db1,jsonArray1.getString(i));
                if(x==1 || x==0 )
                    continue;
                spinnerArray.add(jsonArray1.getString(i));
            }

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
        if(spinnerArray.size()==0)
        {
            //decide where to send user after this is done
            Toast.makeText(getActivity(),"No new item to get enrolled",Toast.LENGTH_LONG).show();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,new userdetails()).addToBackStack(null).commit();
        }
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
                    submit.setText(submit.getText().toString()+e.getText().toString());
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
                    String item = spinner.getSelectedItem().toString();
                    String value = e.getText().toString();
                    db.insert1(db1,item,value,"admin","false");

                    //decide where to send user after this is done
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl1,new userdetails()).addToBackStack(null).commit();
                }
            }
        });

    }

}

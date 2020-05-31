package com.example.identity;

import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class add_user_details extends Fragment {

    Spinner s1;
    LinearLayout l1;
    EditText e1,e2;
    List<String> lm1;
    Button submit;
    int cnt = 0;
    String res1;
    String it11,it22,it33;
    String verifier_url,verifier_name;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    ArrayList<Integer> expiry_index = new ArrayList<Integer>();
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    public add_user_details() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_user_details, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final database db = new database(getActivity());
        lm1=new ArrayList<String>();
        final SQLiteDatabase db1 = db.getWritableDatabase();
        try
        {
            new add_user_details.AsyncLogin().execute();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),"err"+e,Toast.LENGTH_LONG).show();
        }
         date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };
        l1 = getView().findViewById(R.id.linear1);
        submit = getView().findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e1.getText().toString().equals("")) {
                    e1.setError("Required");
                    return;
                }
                int pos = s1.getSelectedItemPosition();
                if(expiry_index.contains(pos))
                if (e2.getText().toString().equals("")) {
                    e2.setError("Required");
                    return;
                }
                String item = s1.getSelectedItem().toString().toLowerCase();
                String value = e1.getText().toString();
                String expiry_date = e2.getText().toString();
                if (e2.isEnabled()) {
                    try {
                        if (!item.toLowerCase().equals("email")) {
                            fn(item, value, expiry_date);
                        } else {
                            db.insert1(db1, item, "", value, "admin", "true", "");
                        }
                    } catch (Exception e1) {
                        Toast.makeText(getContext(), "First Item Clicked" + e1, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    fn(item,value,"");
                }
            }


        });

    }
    void fn(String item,String value, String expiry_date)
    {
        new add_user_details.AsyncVerifier().execute();
        it11=item;
        it22=value;
        it33=expiry_date;
    }

    private class AsyncAdding extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tGetting Verifier list...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                JSONObject jo1 = new JSONObject();
                jo1.put( "type", params[0]);
                jo1.put( "value", params[1]);
                if(params[2].equals(""))
                    jo1.put("reqExpiry",false);
                else
                    jo1.put("reqExpiry",true);
                jo1.put("expiry",params[2]);
                jo1.put( "email", params[3]);
                String s = verifier_url + "add";
                RequestBody body = RequestBody.create( jo1.toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url(s)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            catch (Exception e)
            {
                return "error"+e;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            database db = new database(getActivity());
            SQLiteDatabase db1 = db.getWritableDatabase();
            db.insert1(db1,it11,result,it22,verifier_name,"false",it33);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();
            pdLoading.dismiss();
        }
    }

    private class AsyncVerifier extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tGetting Verifier list...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                server a=new server();
                String name=a.getServer_name();
                String urll=name+"/service_provider/verifiers";
                Request request = new Request.Builder()
                        .url(urll)
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            catch (Exception e)
            {
                return "error"+e;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jo1 = new JSONObject(result);
                String sm= jo1.getString("verifiers");
                JSONArray jo2=new JSONArray(sm);
                List<verifier_info> a = new ArrayList<verifier_info>();
                for(int i=0;i<jo2.length();i++)
                {
                    JSONObject jm2=new JSONObject(jo2.getString(i));
                    lm1.add(jm2.getString("name"));
                    a.add(new verifier_info(jm2.getString("name"),jm2.getString("url")));
                }
                final List<verifier_info> b=a;
                List<String> listItems = lm1;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose a verifier");
                final CharSequence[] values = listItems.toArray(new CharSequence[listItems.size()]);
                AlertDialog dialog;
                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        verifier_url=b.get(item).getUrl1();
                        verifier_name=b.get(item).getName1();
                        database db = new database(getActivity());
                        SQLiteDatabase db1 = db.getWritableDatabase();
                        String em1 = db.getvalue(db1, "email");
                        new add_user_details.AsyncAdding().execute(it11,it22,it33,em1);
                        dialog.dismiss();
                    }
                });
                dialog= builder.create();
                dialog.show();
            }
            catch (Exception e)
            {
                Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
            }
            pdLoading.dismiss();
        }
    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tGetting Fields...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                server a=new server();
                String name=a.getServer_name();
                String urll=name+"/service_provider/fields";
                Request request = new Request.Builder()
                        .url(urll)
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            catch (Exception e)
            {
                return "error"+e;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            res1=result;
            final database db = new database(getActivity());
            final SQLiteDatabase db1 = db.getWritableDatabase();
            boolean flag = false;
            int no = 0;
            try {
                //res1="[{\"name\":\"aadhar\",\"reqExpiry\":true},{\"name\":\"pan\",\"reqExpiry\":false}]";
                JSONObject jj = new JSONObject(res1);

                //Toast.makeText(getContext(),res1,Toast.LENGTH_LONG).show();
                JSONArray arr = jj.getJSONArray("fields");
                for(int i=0;i<arr.length();i++)
                {
                    JSONObject obj = arr.getJSONObject(i);
                    //Toast.makeText(getContext(),obj.getString("name")+""+obj.getBoolean("reqExpiry"),Toast.LENGTH_LONG).show();
                    int x = db.checkfield(db1, obj.getString("name").toLowerCase());
                    if(x == 1 || x == 0)
                        continue;
                    if(obj.getBoolean("reqExpiry")==true)
                    {
                        expiry_index.add(no);
                    }
                    spinnerArray.add(obj.getString("name"));
                    no++;
                    flag=true;
                }
            }
            catch (Exception e)
            {
                Toast.makeText(getContext(),"No fields...!!!",Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();
            }
            if(!flag)
            {
                Toast.makeText(getContext(),"No fields...!!!",Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();
            }
            cnt = 0;
            try
            {
                final LinearLayout parent = new LinearLayout(getActivity());
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.VERTICAL);

                LinearLayout ll1 = new LinearLayout(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(60, 0, 60, 0);
                ll1.setLayoutParams(layoutParams);
                ll1.setOrientation(LinearLayout.VERTICAL);
                s1 = new Spinner(getActivity());
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                s1.setAdapter(spinnerArrayAdapter);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.CENTER;
                s1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                s1.setLayoutParams(params);
                s1.setTag(cnt);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        if(expiry_index.contains(position))
                        {
                            EditText e = (EditText) parent.findViewWithTag("exp0");
                            e.setVisibility(View.VISIBLE);
                            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.US);
                            String todate= dateFormat.format(currentdate());
                            e.setText(todate);
                        }
                        else
                        {
                            EditText e = (EditText) parent.findViewWithTag("exp0");
                            e.setText("");
                            e.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });
                ll1.addView(s1);
                parent.addView(ll1);

                LinearLayout ll2 = new LinearLayout(getActivity());
                ll2.setLayoutParams(layoutParams);
                ll2.setOrientation(LinearLayout.VERTICAL);
                ll2.setWeightSum(4);
                e1 = new EditText(getActivity());
                e1.setTag("i" + cnt);
                e1.setHint(R.string.value);
                e1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,2));
                ll2.addView(e1);



                e2 = new EditText(getActivity());
                e2.setTag("exp" + cnt);
                e2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,2));
                e2.setClickable(true);
                e2.setFocusable(false);
                e2.setHint(R.string.expiry);
                e2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(getContext(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });
                e2.setEnabled(true);
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.US);
                String todate= dateFormat.format(currentdate());
                e2.setText(todate);
                if(expiry_index.contains(0))
                    e2.setVisibility(View.GONE);
                else
                    e2.setVisibility(View.VISIBLE);
                ll2.addView(e2);
                parent.addView(ll2);

                l1.addView(parent);
                cnt++;
            }
            catch (Exception e)
            {
                Toast.makeText(getActivity(),e+"",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            pdLoading.dismiss();
        }
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        e2.setText(sdf.format(myCalendar.getTime()));
        //Toast.makeText(getContext(),sdf.format(myCalendar.getTime()),Toast.LENGTH_LONG).show();
    }
    private Date currentdate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }
}

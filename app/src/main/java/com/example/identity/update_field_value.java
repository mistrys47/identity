package com.example.identity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class update_field_value extends Fragment {

    EditText value,expiry;
    String value1,expiry1;
    String verified_value,verified_expiry,id;
    String old_verifier_url;
    int verified;
    String field_name,email;
    Spinner spinner;
    Button submit;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    ArrayList<String> values = new ArrayList<String>();
    ArrayList<String> expiry_dates = new ArrayList<String>();
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    List<String> lm1;
    String verifier_url,verifier_name;
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
        try {
            lm1=new ArrayList<String>();
            spinner = view.findViewById(R.id.spinner);
            value = view.findViewById(R.id.value);
            expiry = view.findViewById(R.id.expiry);
            submit = view.findViewById(R.id.submit);
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
            final database db = new database(getActivity());
            final SQLiteDatabase db1 = db.getWritableDatabase();
            Cursor c = db.getfields(db1);
            while (c.moveToNext()) {
                if(c.getString(0).equals("email") || c.getString(0).equals("mobile"))
                    continue;
                spinnerArray.add(c.getString(0));
                values.add(c.getString(1));
                if (c.getString(2).equals("")) {
                    expiry_dates.add("-1");
                } else
                    expiry_dates.add(c.getString(2));
            }
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            spinner.setAdapter(spinnerArrayAdapter);
            try {
                value.setText(values.get(0));
                if (expiry_dates.get(0) == "-1") {
                    expiry.setVisibility(View.GONE);
                } else {
                    expiry.setVisibility(View.VISIBLE);
                    expiry.setText(expiry_dates.get(0));
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "No Fields", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();
            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                      value.setText(values.get(i));
                      if (expiry_dates.get(i) == "-1") {
                          expiry.setVisibility(View.GONE);
                      } else {
                          expiry.setVisibility(View.VISIBLE);
                          expiry.setText(expiry_dates.get(i));
                      }
                  }
                  @Override
                  public void onNothingSelected(AdapterView<?> adapterView) {

                  }
            });
            expiry.setClickable(true);
            expiry.setFocusable(false);
            expiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    field_name = spinnerArray.get(spinner.getSelectedItemPosition());
                    value1 = value.getText().toString();
                    expiry1 = expiry.getText().toString();
                    verified_value = values.get(spinner.getSelectedItemPosition());
                    verified_expiry = expiry_dates.get(spinner.getSelectedItemPosition());
                    if(verified_expiry.equals("-1"))
                    {
                        verified_expiry = "";
                        expiry1 = "";
                    }
                    if(value1.equals(verified_value) && expiry1.equals(verified_expiry))
                    {
                        Toast.makeText(getContext(),"No changes made",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        new update_field_value.AsyncVerifier().execute();
                    }
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
        }
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        expiry.setText(sdf.format(myCalendar.getTime()));
        //Toast.makeText(getContext(),sdf.format(myCalendar.getTime()),Toast.LENGTH_LONG).show();
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
                //Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();
                String sm= jo1.getString("verifiers");
                JSONArray jo2=new JSONArray(sm);
                List<verifier_info> a = new ArrayList<verifier_info>();
                for(int i=0;i<jo2.length();i++)
                {
                    JSONObject jm2=new JSONObject(jo2.getString(i));
                    lm1.add(jm2.getString("name"));
                    //Toast.makeText(getContext(),jm2.getString("name")+jm2.getString("url"),Toast.LENGTH_LONG).show();
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
                        try {
                            verifier_url = b.get(item).getUrl1();
                            verifier_name = b.get(item).getName1();
                            database db = new database(getActivity());
                            SQLiteDatabase db1 = db.getWritableDatabase();
                            String key = db.getkey(db1, field_name);
                            email = db.getvalue(db1, "email");
                            id = db.getverifier_url(db1,field_name);
                            old_verifier_url = getoldurl(id);
                            id = getid(id);
                            verified = db.checkfield(db1,field_name);
                            //Toast.makeText(getContext(),id+":"+old_verifier_url,Toast.LENGTH_LONG).show();
                            new update_field_value.Asyncupdate().execute(value1, expiry1, key, email, field_name);
                            dialog.dismiss();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
                        }
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
    private class Asyncupdate extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tSending Update Request");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                JSONObject jo1 = new JSONObject();
                jo1.put( "value", params[0]);
                jo1.put( "expiry", params[1]);
                jo1.put("key",params[2]);
                jo1.put("email",params[3]);
                jo1.put("type",params[4]);
                jo1.put("id",id);
                String s;
                if(verified!=0)
                    s = verifier_url+ "update";
                else
                    s = old_verifier_url + "remove";
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
            if(verified==0)
            {
                pdLoading.dismiss();
                new update_field_value.Asyncadd().execute(field_name,value1,expiry1,email);
            }
            else{
                Boolean b, c;
                String last_verified_value;
                database db = new database(getActivity());
                SQLiteDatabase db1 = db.getWritableDatabase();
                int verified = db.checkfield(db1, field_name);
                if (verified_expiry == "") {
                    last_verified_value = verified_value + "#";
                    if (verified == 1)
                        c = db.update_db1(db1, "last_verified_value", last_verified_value, field_name);
                    c = db.update_db1(db1, "value", value1, field_name);
                    c = c & db.update_db1(db1,"verified_by",verifier_name,field_name);
                    c = c & db.update_db1(db1, "verified", "updated", field_name);
                    c = c & db.update_db1(db1, "verifier_url", result, field_name);
                } else {
                    last_verified_value = verified_value + "#" + verified_expiry;
                    if (verified == 1)
                        c = db.update_db1(db1, "last_verified_value", last_verified_value, field_name);
                    c = db.update_db1(db1, "value", value1, field_name);
                    c = c & db.update_db1(db1,"verified_by",verifier_name,field_name);
                    c = c & db.update_db1(db1, "expiry_date", expiry1, field_name);
                    c = c & db.update_db1(db1, "verified", "updated", field_name);
                    c = c & db.update_db1(db1, "verifier_url", result, field_name);
                }
                if (c)
                    Toast.makeText(getContext(), "Successful Update", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl1, new user_details_card()).addToBackStack(null).commit();
                pdLoading.dismiss();
            }
        }
    }
    private class Asyncadd extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tSending Update Request");
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
                String s = verifier_url+"add";
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

                Boolean b, c;
                String last_verified_value;
                database db = new database(getActivity());
                SQLiteDatabase db1 = db.getWritableDatabase();
                int verified = db.checkfield(db1, field_name);
                if (verified_expiry == "") {
                    last_verified_value = verified_value + "#";
                    if (verified == 1)
                        c = db.update_db1(db1, "last_verified_value", last_verified_value, field_name);
                    c = db.update_db1(db1, "value", value1, field_name);
                    c = c & db.update_db1(db1,"verified_by",verifier_name,field_name);
                    if(verified != 0)
                        c = c & db.update_db1(db1, "verified", "updated", field_name);
                    else
                        c = c & db.update_db1(db1, "verified", "false", field_name);
                    c = c & db.update_db1(db1, "verifier_url", result, field_name);
                } else {
                    last_verified_value = verified_value + "#" + verified_expiry;
                    if (verified == 1)
                        c = db.update_db1(db1, "last_verified_value", last_verified_value, field_name);
                    c = db.update_db1(db1, "value", value1, field_name);
                    c = c & db.update_db1(db1,"verified_by",verifier_name,field_name);
                    c = c & db.update_db1(db1, "expiry_date", expiry1, field_name);
                    if(verified != 0)
                        c = c & db.update_db1(db1, "verified", "updated", field_name);
                    else
                        c = c & db.update_db1(db1, "verified", "false", field_name);
                    c = c & db.update_db1(db1, "verifier_url", result, field_name);
                }
                if (c)
                    Toast.makeText(getContext(), "Successful update", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl1, new user_details_card()).addToBackStack(null).commit();
                pdLoading.dismiss();
        }
    }
    public String getid(String s)
    {
        int k = s.indexOf('=');
        return s.substring(k+1);
    }
    public String getoldurl(String s)
    {
        int k = s.indexOf("/status");
        return s.substring(0,k)+"/";
    }
}

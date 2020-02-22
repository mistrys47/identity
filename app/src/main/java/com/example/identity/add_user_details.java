package com.example.identity;

import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
    List<String> lm1;
    Button add,remove,submit;
    int cnt = 0;
    String res1;
    Boolean done=false;
    Boolean done1=false;
    LinearLayout l;
    String it11,it22;
    String verifier_url,verifier_name;
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
        lm1=new ArrayList<String>();
        final SQLiteDatabase db1 = db.getWritableDatabase();
        try {
            new add_user_details.AsyncLogin().execute();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),"err"+e,Toast.LENGTH_LONG).show();
        }


        l1 = getView().findViewById(R.id.linear1);

        submit = getView().findViewById(R.id.submit);


        final ArrayList<String> spinnerarray1 = spinnerArray;

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

                    EditText e = getView().findViewWithTag("i"+0);
                    Spinner spinner = getView().findViewWithTag(0);
                    String item = spinner.getSelectedItem().toString().toLowerCase();
                    String value = e.getText().toString();
                   // Toast.makeText()

                    try {
                        if(!item.toLowerCase().equals("email")) {
                            fn(item, value);

                        }else
                        {

                            db.insert1(db1,item,"",value,"admin","true");
                        }
                    }
                    catch (Exception e1)
                    {
                        Toast.makeText(getContext(), "First Item Clicked"+e1, Toast.LENGTH_LONG).show();
                    }
                    //decide where to send user after this is done

                }


        });

    }
    void fn(String item,String value)
    {

       // lm1.add("hello");
        new add_user_details.AsyncVerifier().execute();


        it11=item;
        it22=value;



    }




    private class AsyncAdding extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tGetting Verifier list...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();


                JSONObject jo1 = new JSONObject();
                jo1.put( "type", params[0]);
                jo1.put( "value", params[1]);
                jo1.put( "email", params[2]);
                //jo1.put("signup", true);
                String s=verifier_url+"add";
                RequestBody body = RequestBody.create( jo1.toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url("https://a75f66f6.ngrok.io/details/add")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                Response response = client.newCall(request).execute();
             /*   MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

                Request request = new Request.Builder()
                        .url(s)
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .build();



                Response response = client.newCall(request).execute();
*/
                return response.body().string();

            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();
            database db = new database(getActivity());
            SQLiteDatabase db1 = db.getWritableDatabase();
            db.insert1(db1,it11,result,it22,verifier_name,"false");
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

            //this method will be running on UI thread
            pdLoading.setMessage("\tGetting Verifier list...");
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
                        .url("https://uidserver.herokuapp.com/service_provider/verifiers")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
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
            try {
                JSONObject jo1 = new JSONObject(result);
               String sm= jo1.getString("verifiers");
                Toast.makeText(getContext(),""+sm,Toast.LENGTH_LONG).show();
                JSONArray jo2=new JSONArray(sm);
                List<verifier_info> a = new ArrayList<verifier_info>();
               // List< verifier_info> a=new verifier_info[100];
                for(int i=0;i<jo2.length();i++)
                {

                    JSONObject jm2=new JSONObject(jo2.getString(i));
                    lm1.add(jm2.getString("name"));
                    a.add(new verifier_info(jm2.getString("name"),jm2.getString("url")));
                  //  a[i].setName1(jm2.getString("name"));
                    //  a[i].setUrl1(jm2.getString("url"));
                    Toast.makeText(getContext(),""+jm2.getString("name"),Toast.LENGTH_LONG).show();
                   // lm1.add(jo2.getString(i));
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
                        Toast.makeText(getContext(),verifier_url,Toast.LENGTH_LONG).show();

                        database db = new database(getActivity());
                        SQLiteDatabase db1 = db.getWritableDatabase();
                        String em1 = db.getvalue(db1, "email");
                        new add_user_details.AsyncAdding().execute(it11,it22,em1);
                        dialog.dismiss();
                    }
                });
                dialog= builder.create();
                dialog.show();



            }catch (Exception e)
            {
                Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
            }
          //  done1=true;

          //  Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();
            pdLoading.dismiss();
        }
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
            boolean flag=false;
            String m1="";
            for(String a : a3) {
                int x = db.checkfield(db1,a.substring(1,a.length()-1).toLowerCase());
                if(x==1 || x==0 )
                    continue;
                spinnerArray.add(a.substring(1,a.length()-1));
                flag=true;
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

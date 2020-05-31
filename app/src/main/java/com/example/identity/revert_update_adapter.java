package com.example.identity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class revert_update_adapter extends RecyclerView.Adapter<revert_update_adapter.MyViewHolder> {

    private List<update_details> updateDetails;
    private Context c;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView field_name,value,expiry_date;
        public LinearLayout ll1;
        String old_verifier_url;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.field_name = (TextView) itemView.findViewById(R.id.field_name);
            this.value = (TextView) itemView.findViewById(R.id.value);
            this.expiry_date = (TextView) itemView.findViewById(R.id.expiry_date);
            this.ll1 = (LinearLayout) itemView.findViewById(R.id.linearlayout);
        }
    }
    public revert_update_adapter(List<update_details> updateDetails,Context c) {
        this.updateDetails = updateDetails;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.revert_update_card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        update_details update_details1 = updateDetails.get(position);
        holder.field_name.setText(update_details1.getField_name());
        holder.value.setText(update_details1.getValue());
        holder.expiry_date.setText(update_details1.getExpiry_date());
        holder.ll1.setTag(update_details1.getField_name());
        holder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View m1=view;
                new AlertDialog.Builder(c)
                        .setTitle("Warning")
                        .setMessage("Do you want to revert update ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                AppCompatActivity activity = (AppCompatActivity) m1.getContext();
                                database db = new database(activity);
                                SQLiteDatabase db1 = db.getWritableDatabase();
                                String id = db.getverifier_url(db1,(String) view.getTag());
                                String old_verifier_url = getoldurl(id);
                                id = getid(id);
                                Boolean b = change((String) view.getTag(),activity);
                                new revert_update_adapter.Asyncupdate().execute(id,old_verifier_url);

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return updateDetails.size();
    }
    public boolean change(String s,AppCompatActivity a){
        database db = new database(a);
        SQLiteDatabase db1 = db.getWritableDatabase();
        String whole = db.getlast_verified_value(db1,s);
        String value = whole.substring(0,whole.indexOf('#'));
        String expiry;
        if(whole.length()==value.length()+1)
            expiry = "";
        else
            expiry = whole.substring(whole.indexOf('#')+1);
        //Toast.makeText(c,value+"..."+expiry,Toast.LENGTH_LONG).show();
        Boolean b;
        b = db.update_db1(db1,"last_verified_value","",s);
        b = b & db.update_db1(db1,"value",value,s);
        b = b & db.update_db1(db1,"expiry_date",expiry,s);
        b = b & db.update_db1(db1,"verified","true",s);
        return b;
    }
    private class Asyncupdate extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(c);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tSending revert Request");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                JSONObject jo1 = new JSONObject();
                jo1.put("id",params[0]);
                String s = params[1] + "remove";
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
            Toast.makeText(c,"Reverted back ",Toast.LENGTH_LONG).show();
            Fragment myFragment = new user_details_card();
            pdLoading.dismiss();
            AppCompatActivity activity = (AppCompatActivity) c;
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl1, myFragment).addToBackStack(null).commit();
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


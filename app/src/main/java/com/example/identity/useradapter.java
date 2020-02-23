package com.example.identity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.LinearGradient;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class useradapter extends RecyclerView.Adapter<useradapter.MyViewHolder> {
    private Context context;
    public String im,old_url,old_id;
    public List<String> lm1;
    private OnItemClickListner mlistner;
    public String verifier_url,verifier_name,old_verifier_url,old_verifier_name;
    public interface OnItemClickListner{
        void onItemClick(int position);
    }
    public  void setOnItemClickListner(OnItemClickListner listner)
    {
        mlistner=listner;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView i1;
        public TextView field,info,totalHeld;
        public LinearLayout ll1;


        public MyViewHolder(final View itemView, final OnItemClickListner listner) {
            super(itemView);
            lm1=new ArrayList<String>();
            this.i1 = (ImageView) itemView.findViewById(R.id.bb1);
            this.field = (TextView) itemView.findViewById(R.id.bb2);
            this.ll1=(LinearLayout)itemView.findViewById(R.id.ll1);
            this.info = (TextView) itemView.findViewById(R.id.bb3);

            this.i1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(i1.getTag()!="verified") {
                        context=itemView.getContext();
                        try {
                        //    Toast.makeText(context,""+view.getId(),Toast.LENGTH_LONG).show();
                            database db = new database(context);
                            im=i1.getTag().toString();
                            SQLiteDatabase db1 = db.getWritableDatabase();
                            String url1 = db.geturl1(db1, i1.getTag().toString());
                            new useradapter.AsyncCheck().execute(url1);
                           // Toast.makeText(itemView.getContext(), url1 + " ", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(itemView.getContext(), e + ""+i1.getTag(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            this.ll1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(i1.getTag()!="verified") {
                        context=itemView.getContext();
                        try {
                         //   Toast.makeText(context, "" + view.getId(), Toast.LENGTH_LONG).show();
                            database db = new database(context);
                            im = i1.getTag().toString();
                            try{
                                new useradapter.AsyncVerifier().execute();
                                //Toast.makeText(context,"calling verifier",Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(context,e+"",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(itemView.getContext(), e + "", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                });
        }

    }
    private class AsyncCheck extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(context);
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
                        .url(params[0])
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .build();



                Response response = client.newCall(request).execute();
                if(response.code()==200)
                return response.body().string();
                else
                    return "Not Found";

            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context,""+result,Toast.LENGTH_LONG).show();
            boolean isFound = result.indexOf("Not Found") !=-1? true: false;

            if(!isFound)
            {
                database db = new database(context);
                SQLiteDatabase db1 = db.getWritableDatabase();
                db.update1(db1,im,"true");
                db.update2(db1,im,result);
            }
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();
            // res1 = result;
            // Toast.makeText(context,"background",Toast.LENGTH_LONG).show();
            pdLoading.dismiss();
        }
    }
    private class AsyncVerifier extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(mContext);


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

            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {
            try
            {
                JSONObject jo1 = new JSONObject(result);
                String sm= jo1.getString("verifiers");
                JSONArray jo2=new JSONArray(sm);
                List<verifier_info> a = new ArrayList<verifier_info>();
                database db = new database(context);
                SQLiteDatabase db1 = db.getWritableDatabase();
                old_verifier_name = db.getverifier_name(db1,im);
                lm1 = new ArrayList<String>();
                for(int i=0;i<jo2.length();i++)
                {
                    JSONObject jm2=new JSONObject(jo2.getString(i));
                    lm1.add(jm2.getString("name"));
                    a.add(new verifier_info(jm2.getString("name"),jm2.getString("url")));

                    if(old_verifier_name.equals(jm2.getString("name")))
                    {
                        old_verifier_url = jm2.getString("url");
                    }
                }
                final List<verifier_info> b=a;
                List<String> listItems = lm1;
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose a verifier");
                final CharSequence[] values = listItems.toArray(new CharSequence[listItems.size()]);
                AlertDialog dialog;
                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        //Toast.makeText(mContext,""+item,Toast.LENGTH_LONG).show();
                        verifier_url = b.get(item).getUrl1();
                        verifier_name = b.get(item).getName1();
                        try {
                            //Toast.makeText(context,"calling add",Toast.LENGTH_LONG).show();
                            new useradapter.AsyncAdd().execute();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(context,e+"",Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog= builder.create();
                dialog.show();

            }catch (Exception e)
            {
                Toast.makeText(mContext,""+e,Toast.LENGTH_LONG).show();
            }

            pdLoading.dismiss();
        }
    }
    private class AsyncAdd extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(context);
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
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            JSONObject jo1 = new JSONObject();
            try {
                database db = new database(context);
                SQLiteDatabase db1 = db.getWritableDatabase();
                String email = db.getvalue(db1, "email");
                String value = db.getvalue(db1, im);
                jo1.put("type", im);
                jo1.put("value", value);
                jo1.put("email", email);
                String s = verifier_url + "add";
                RequestBody body = RequestBody.create(jo1.toString(), okhttp3.MediaType.parse("application/json; charset=utf-8"));
                //use s var
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
                return e+"";
            }

        }
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
            database db = new database(context);
            SQLiteDatabase db1 = db.getWritableDatabase();
            old_url = db.getverifier_url(db1,im);
            int i = old_url.indexOf("=");
            old_id = old_url.substring(i+1);
            //Toast.makeText(context,old_url+old_id,Toast.LENGTH_LONG).show();
            boolean x12=db.update(db1,"verified_by",verifier_name,im);
            boolean x11=db.update(db1,"verifier_url",result,im);
            if(x11 )
            {
                if(x12)
                {
                    Toast.makeText(context,"db update",Toast.LENGTH_LONG).show();
                }
            }
            //Toast.makeText(context,"post adding",Toast.LENGTH_LONG).show();
            try {
                new useradapter.AsyncRemove().execute();
            }
            catch (Exception e)
            {
                Toast.makeText(context,e+"",Toast.LENGTH_LONG).show();
            }
            pdLoading.dismiss();
        }
    }
    private class AsyncRemove extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(context);
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
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            JSONObject jo1 = new JSONObject();
            try
            {
                String s=old_verifier_url+"remove";
                jo1.put("id",old_id);
                RequestBody body = RequestBody.create( jo1.toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));

                //use s var
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
                return e+"";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            boolean isFound = result.indexOf("Success") !=-1? true: false;
            //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
            if(isFound)
            {
                Toast.makeText(context,"updated",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context,"Error removing previous verifier",Toast.LENGTH_LONG).show();
            }
            pdLoading.dismiss();
        }
    }
    private List<User_details> categoriesList;
    private Context mContext;
    public useradapter(List<User_details> moviesList) {
        this.categoriesList = moviesList;
    }
    @Override
    public useradapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_card, parent, false);

        return new useradapter.MyViewHolder(itemView,mlistner);}

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        User_details  tempobj=categoriesList.get(position);
        if(tempobj.getVerified().equals("false")) {
            holder.i1.setImageResource(R.drawable.wrong);
            holder.i1.setTag(tempobj.getfields());
        }
        else
        {
            holder.i1.setImageResource(R.drawable.tick);
            holder.i1.setTag("verified");
        }
        holder.field.setText((tempobj.getfields().toUpperCase() ));
        holder.info.setText((tempobj.getInfo()));
    }



    public useradapter(List<User_details> categories, Context context) {
        this.mContext = context;
        this.categoriesList = categories;
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


}

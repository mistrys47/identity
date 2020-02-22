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
    public String im;
    public List<String> lm1;
private OnItemClickListner mlistner;
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
                            Toast.makeText(context,""+view.getId(),Toast.LENGTH_LONG).show();
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
                            Toast.makeText(context, "" + view.getId(), Toast.LENGTH_LONG).show();
                            database db = new database(context);
                            im = i1.getTag().toString();
                            SQLiteDatabase db1 = db.getWritableDatabase();
                            String url1 = db.geturl1(db1, i1.getTag().toString());
                            new useradapter.AsyncVerifier().execute(url1);
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
              //  Toast.makeText(getContext(),""+sm,Toast.LENGTH_LONG).show();
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
                  //  Toast.makeText(getContext(),""+jm2.getString("name"),Toast.LENGTH_LONG).show();
                    // lm1.add(jo2.getString(i));
                }

                final List<verifier_info> b=a;
                List<String> listItems = lm1;
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose a verifier");
                final CharSequence[] values = listItems.toArray(new CharSequence[listItems.size()]);
                AlertDialog dialog;
                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                       Toast.makeText(mContext,""+item,Toast.LENGTH_LONG).show();

                      //  new add_user_details.AsyncAdding().execute(it11,it22,em1);
                        dialog.dismiss();
                    }
                });
                dialog= builder.create();
                dialog.show();



            }catch (Exception e)
            {
                Toast.makeText(mContext,""+e,Toast.LENGTH_LONG).show();
            }
            //  done1=true;

            //  Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();
            pdLoading.dismiss();
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

                return response.body().string();

            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {
            boolean isFound = result.indexOf("true") !=-1? true: false;
            if(isFound)
            {
                database db = new database(context);

                SQLiteDatabase db1 = db.getWritableDatabase();
                db.update1(db1,im,"true");

            }
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,new user_details_card()).addToBackStack(null).commit();
            // res1 = result;
            // Toast.makeText(context,"background",Toast.LENGTH_LONG).show();
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

        //holder.subname.setText(tempobj.getSubname());
        holder.field.setText((tempobj.getfields().toUpperCase() ));

        holder.info.setText((tempobj.getInfo()));

     //   holder.totalHeld.setText(Integer.toString(tempobj.getTotalheld()));
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

package com.example.identity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;
import java.util.zip.Inflater;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class useradapter extends RecyclerView.Adapter<useradapter.MyViewHolder> {
    private Context context;
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


        public MyViewHolder(final View itemView, final OnItemClickListner listner) {
            super(itemView);
           this.i1 = (ImageView) itemView.findViewById(R.id.bb1);
            this.field = (TextView) itemView.findViewById(R.id.bb2);
            this.info = (TextView) itemView.findViewById(R.id.bb3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(i1.getTag()!="verified") {
                        Toast.makeText(itemView.getContext(), i1.getTag() + "", Toast.LENGTH_LONG).show();
                        context=itemView.getContext();
                        new useradapter.AsyncLogin().execute();
                        //FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                        //fragmentManager.beginTransaction().replace(R.id.fl1,new update_field_value()).addToBackStack(null).commit();
                    }
                }
            });


        }

    }
    private class AsyncLogin extends AsyncTask<String, String, String>
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


                //Thread.sleep(1000);

                return "";
            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {
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

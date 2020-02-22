package com.example.identity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;
import java.util.zip.Inflater;

public class allowed_website_adapter extends RecyclerView.Adapter<allowed_website_adapter.MyViewHolder> {
    private OnItemClickListner mlistner;
    public interface OnItemClickListner{
        void onItemClick(int position);
    }
    public  void setOnItemClickListner(OnItemClickListner listner)
    {
        mlistner=listner;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

      //  public ImageView i1;
        public TextView field;
        public LinearLayout ll;


        public MyViewHolder(View itemView, final OnItemClickListner listner) {
            super(itemView);
           // this.i1 = (ImageView) itemView.findViewById(R.id.bb1);
            this.field = (TextView) itemView.findViewById(R.id.d1);
            this.ll = (LinearLayout) itemView.findViewById(R.id.ll2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listner.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
    private List<allowed_web_info> categoriesList;
    private Context mContext;
    public allowed_website_adapter(List<allowed_web_info> moviesList) {
        this.categoriesList = moviesList;
    }
    @Override
    public allowed_website_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.website_details, parent, false);

        return new allowed_website_adapter.MyViewHolder(itemView,mlistner);}

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        allowed_web_info  tempobj=categoriesList.get(position);


        //holder.subname.setText(tempobj.getSubname());
        holder.field.setText((tempobj.getWebsite_name() ));
        String x = tempobj.getRequested_details();
        try {
            JSONObject jo1 = new JSONObject(x);
            for(int i=0;i<jo1.names().length();i++)
            {
                String s1 = jo1.names().getString(i);
                String s2 = jo1.getString(s1);
                if(s1.equals("signup"))
                    continue;
                TextView t1 = new TextView(mContext);
                t1.setText(s1+" : "+s2);
                holder.ll.addView(t1);
            }
        }catch (Exception e)
        {
            Toast.makeText(mContext,e+"",Toast.LENGTH_LONG).show();
        }
    }



    public allowed_website_adapter(List<allowed_web_info> categories, Context context) {
        this.mContext = context;

        this.categoriesList = categories;
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

}

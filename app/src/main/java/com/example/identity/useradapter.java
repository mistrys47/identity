package com.example.identity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

public class useradapter extends RecyclerView.Adapter<useradapter.MyViewHolder> {
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
            i1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(i1.getTag()!="verified")
                    Toast.makeText(itemView.getContext(),i1.getTag()+"",Toast.LENGTH_LONG).show();

                }
            });


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

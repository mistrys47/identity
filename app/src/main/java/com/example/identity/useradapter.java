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
/*
public class useradapter extends RecyclerView.Adapter<useradapter.userviewholder> {
    private Context mctx;
    private List<User_details> userlist;

    public useradapter(Context mctx, List<User_details> userlist) {
        this.mctx = mctx;
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public userviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        try {
            LayoutInflater inflater = LayoutInflater.from(mctx);
            View v = inflater.inflate(R.layout.details_card, null);
            userviewholder holder = new userviewholder(v);
            return holder;
        } catch (Exception e) {
            Toast.makeText(mctx, "" + e, Toast.LENGTH_LONG).show();
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull userviewholder userviewholder, int i) {
       try {
           User_details u1 = userlist.get(i);
           userviewholder.field.setText(u1.getfields());
       }catch (Exception e)
       {
           Toast.makeText(mctx,""+e,Toast.LENGTH_LONG).show();
       }

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    class userviewholder extends RecyclerView.ViewHolder{
       public ImageButton i1;
       public TextView field;
        public TextView info;
        public userviewholder(@NonNull View itemView) {
            super(itemView);
            try {
                i1 = (ImageButton) itemView.findViewById(R.id.button1);
                field = (TextView) itemView.findViewById(R.id.field);
                info = (TextView) itemView.findViewById(R.id.info);
            }
            catch (Exception e)
            {
                Toast.makeText(mctx,""+e,Toast.LENGTH_LONG).show();
            }
        }
    }
}
*/
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


        public MyViewHolder(View itemView, final OnItemClickListner listner) {
            super(itemView);
           this.i1 = (ImageView) itemView.findViewById(R.id.bb1);
            this.field = (TextView) itemView.findViewById(R.id.bb2);
            this.info = (TextView) itemView.findViewById(R.id.bb3);
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



        }
        else
        {
            holder.i1.setImageResource(R.drawable.tick);
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

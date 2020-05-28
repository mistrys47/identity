package com.example.identity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class revert_update_adapter extends RecyclerView.Adapter<revert_update_adapter.MyViewHolder> {

    private List<update_details> updateDetails;
    private Context c;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView field_name,value,expiry_date;
        public LinearLayout ll1;

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
                                Boolean b = change((String) view.getTag(),activity);
                                Fragment myFragment = new user_details_card();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl1, myFragment).addToBackStack(null).commit();

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
        Toast.makeText(c,value+"..."+expiry,Toast.LENGTH_LONG).show();
        Boolean b;
        b = db.update_db1(db1,"last_verified_value","",s);
        b = b & db.update_db1(db1,"value",value,s);
        b = b & db.update_db1(db1,"expiry_date",expiry,s);
        b = b & db.update_db1(db1,"verified","true",s);
        return b;
    }

}


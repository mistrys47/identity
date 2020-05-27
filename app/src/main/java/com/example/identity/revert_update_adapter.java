package com.example.identity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
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
    }

    @Override
    public int getItemCount() {
        return updateDetails.size();
    }


}


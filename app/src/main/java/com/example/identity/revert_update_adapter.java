package com.example.identity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class revert_update_adapter extends RecyclerView.Adapter<revert_update_adapter.MyViewHolder> {

    private List<update_details> updateDetails;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView field_name,value,expiry_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.field_name = (TextView) itemView.findViewById(R.id.field_name);
            this.value = (TextView) itemView.findViewById(R.id.value);
            this.expiry_date = (TextView) itemView.findViewById(R.id.expiry_date);
        }
    }
    public revert_update_adapter(List<update_details> updateDetails) {
        this.updateDetails = updateDetails;
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
    }

    @Override
    public int getItemCount() {
        return updateDetails.size();
    }
}


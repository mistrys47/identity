package com.example.identity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class revert_update extends Fragment {

    private List<update_details> update_detailsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private revert_update_adapter revert_update_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            View inflate = inflater.inflate(R.layout.fragment_revert_update, container, false);
            return inflate;
        }
        catch (Exception e){
            Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
            View v=null;
            return v;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_revert_update);
            revert_update_adapter = new revert_update_adapter(update_detailsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(revert_update_adapter);
            database db = new database(getActivity());
            SQLiteDatabase db1;
            db1 = db.getWritableDatabase();
            Cursor c = db.getrevertfields(db1);
            Toast.makeText(getContext(), c.getCount(), Toast.LENGTH_LONG).show();
            while (c.moveToNext()) {
                update_detailsList.add(new update_details(c.getString(0), c.getString(1), c.getString(2)));
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
        }
    }
}

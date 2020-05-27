package com.example.identity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class update_field_value extends Fragment {

    EditText value,expiry;
    Spinner spinner;
    Button submit;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    ArrayList<String> values = new ArrayList<String>();
    ArrayList<String> expiry_dates = new ArrayList<String>();
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    public update_field_value() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view= inflater.inflate(R.layout.fragment_update_field_value, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            spinner = view.findViewById(R.id.spinner);
            value = view.findViewById(R.id.value);
            expiry = view.findViewById(R.id.expiry);
            submit = view.findViewById(R.id.submit);
            date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };
            final database db = new database(getActivity());
            final SQLiteDatabase db1 = db.getWritableDatabase();
            Cursor c = db.getfields(db1);
            while (c.moveToNext()) {
                spinnerArray.add(c.getString(0));
                values.add(c.getString(1));

                if (c.getString(2).equals("")) {
                    //Toast.makeText(getContext(),c.getString(2)+":",Toast.LENGTH_LONG).show();
                    expiry_dates.add("-1");
                } else
                    expiry_dates.add(c.getString(2));
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            spinner.setAdapter(spinnerArrayAdapter);
            try {
                value.setText(values.get(0));
                if (expiry_dates.get(0) == "-1") {
                    expiry.setVisibility(View.GONE);
                } else {
                    expiry.setVisibility(View.VISIBLE);
                    expiry.setText(expiry_dates.get(0));
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "No Fields", Toast.LENGTH_LONG).show();
            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                      value.setText(values.get(i));
                      if (expiry_dates.get(i) == "-1") {
                          expiry.setVisibility(View.GONE);
                      } else {
                          expiry.setVisibility(View.VISIBLE);
                          expiry.setText(expiry_dates.get(i));
                      }
                  }
                  @Override
                  public void onNothingSelected(AdapterView<?> adapterView) {

                  }
            });
            expiry.setClickable(true);
            expiry.setFocusable(false);
            expiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean b, c;
                    String last_verified_value;
                    if (expiry_dates.get(spinner.getSelectedItemPosition()) == "-1") {
                        last_verified_value = values.get(spinner.getSelectedItemPosition()) + "#";
                        b = db.update1(db1, "last_verified_value", last_verified_value);
                        c = db.update1(db1, "value", value.getText().toString());
                    } else {
                        last_verified_value = values.get(spinner.getSelectedItemPosition()) + "#" + expiry_dates.get(spinner.getSelectedItemPosition());
                        b = db.update1(db1, "last_verified_value",last_verified_value );
                        c = db.update1(db1, "value", value.getText().toString());
                        c = c & db.update1(db1, "expiry_date", expiry.getText().toString());
                    }
                    if (b)
                        Toast.makeText(getContext(), "Successful Updatation in last_verified value" + last_verified_value, Toast.LENGTH_LONG).show();
                    if (c)
                        Toast.makeText(getContext(), "Successful Updatation in new value"+db.getlast_verified_value(db1,spinnerArray.get(spinner.getSelectedItemPosition())), Toast.LENGTH_LONG).show();

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
        }
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        expiry.setText(sdf.format(myCalendar.getTime()));
        //Toast.makeText(getContext(),sdf.format(myCalendar.getTime()),Toast.LENGTH_LONG).show();
    }
}

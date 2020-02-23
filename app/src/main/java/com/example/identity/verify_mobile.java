package com.example.identity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class verify_mobile extends AppCompatActivity {
    EditText l1,l2,l3,l4;
    Button b;
    String m4,c;
    database db=new database(this);
    SQLiteDatabase db1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_mobile);
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.number_background));}
        db1 = db.getWritableDatabase();
        m4= getIntent().getStringExtra("number");
        Toast.makeText(verify_mobile.this,""+m4,Toast.LENGTH_LONG).show();
        Toast.makeText(this,m4,Toast.LENGTH_LONG).show();
        l1=(EditText)findViewById(R.id.letter1);
        l2=(EditText)findViewById(R.id.letter2);
        l3=(EditText)findViewById(R.id.letter3);
        l4=(EditText)findViewById(R.id.letter4);
        b=(Button)findViewById(R.id.verify_email);


        l2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(l2.getText().length()==0){
                        l1.getText().clear();
                        l1.requestFocus();}
                    // Toast.makeText(verify_email.this,"backspace 2",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        l3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(l3.getText().length()==0){
                        l2.getText().clear();
                        l2.requestFocus();}
                    //     Toast.makeText(verify_email.this,"backspace 3",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        l4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction()==KeyEvent.ACTION_DOWN) {
                    if(l4.getText().toString().length()==0){
                        // Toast.makeText(verify_email.this,l4.getText(),Toast.LENGTH_LONG).show();
                        l3.getText().clear();
                        l3.requestFocus();

                    }
                    else{

                        l4.getText().clear();
                    }
                    //     Toast.makeText(verify_email.this,"backspace 4",Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });

        l1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(l1.getText().length()==1)
                {
                    l2.requestFocus();
                }

            }
        });
        l2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(l2.getText().length()==1)
                {
                    l3.requestFocus();
                }
            }
        });
        l3.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(l3.getText().length()==1)
                {
                    l4.requestFocus();
                }

            }
        });
        l4.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //m4=s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(l4.getText().length()==1)
                {
                    b.requestFocus();
                }
            }
        });


    }
    public void go(View view)
    {
        if(l1.getText().length()==1 && l2.getText().length()==1 && l3.getText().length()==1 && l4.getText().length()==1 )
        {
            String s=l1.getText().toString()+l2.getText().toString()+l3.getText().toString()+l4.getText().toString();

            new verify_mobile.AsyncLogin().execute(m4,s);
        }




    }
    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(verify_mobile.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tSending...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

                JSONObject jo1 = new JSONObject();
                jo1.put("mobile",params[0]);
                jo1.put("otp",params[1]);c= jo1.toString();

                RequestBody body = RequestBody.create( jo1.toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                server a=new server();
                String name=a.getServer_name();
                String urll=name+"/verify/mobile/check";
                Request request = new Request.Builder()
                        .url(urll)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();

                Response response = client.newCall(request).execute();
                // Toast.makeText(email_input.this,"hello"+response,Toast.LENGTH_LONG).show();
                return response.body().string();
            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(verify_mobile.this,"hello"+result,Toast.LENGTH_LONG).show();
            //this method will be running on UI thread
            String s="";
            try {
                JSONObject jo1 = new JSONObject(result);
                s=jo1.getString("key");
              }
              catch (Exception e)
            {

            }

           // boolean isFound = result.indexOf("true") !=-1? true: false;
            if (!s.equals("")) {
                Intent intent=new Intent(verify_mobile.this,home.class);
                boolean x=db.insert11(db1,"mobile","",m4,"admin","true",s);
                if(x==true)
                {
                    Toast.makeText(verify_mobile.this,"success",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(verify_mobile.this,"error occured",Toast.LENGTH_LONG).show();
                }
                startActivity(intent);
                pdLoading.dismiss();
            }
            else{
               // Toast.makeText(verify_mobile.this,"some error occured"+c,Toast.LENGTH_LONG).show();
                pdLoading.dismiss();
            }
        }
    }
}

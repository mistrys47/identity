package com.example.identity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class email_input extends AppCompatActivity {
    EditText email;
    String em1;
    int sent=0;
    database db=new database(this);
    SQLiteDatabase db1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_input);
        email=(EditText)findViewById(R.id.letter1);
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.number_background));}
        db1 = db.getWritableDatabase();
        boolean x1=db.checkemail(db1);

        boolean x2=db.checkmobile(db1);
        if(x1==true && x2==true)
        {
            Intent intent=new Intent(email_input.this,home.class);
            startActivity(intent);
        }
        else if(x1==true)
        {
            Intent intent=new Intent(email_input.this,mobile_number.class);
            startActivity(intent);
        }
        else if(x2==true)
        {
            db.deletetable(db1);
        }
    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public void go(View view)
    {
        final String email_input=email.getText().toString();
        em1=email_input;
        if(isValid(email_input)){
            new email_input.AsyncLogin().execute(email_input);
           // Toast.makeText(this,"hi",Toast.LENGTH_LONG).show();
        }
        else{
            email.setError("Enter email in correct format");
        }


    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(email_input.this);


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
                RequestBody body = RequestBody.create( "email="+params[0],mediaType);
                Request request = new Request.Builder()
                        .url("https://uidserver.herokuapp.com/verify/email/otp")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                Response response = client.newCall(request).execute();

                 //Toast.makeText(email_input.this,"hello",Toast.LENGTH_LONG).show();
                return response.body().string();
            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
           // Toast.makeText(email_input.this,result,Toast.LENGTH_LONG).show();
         //   pdLoading.dismiss();
            boolean isFound = result.indexOf("true") !=-1? true: false;
            // Toast.makeText(email_input.this,"hello"+result,Toast.LENGTH_LONG).show();
            if (isFound ) {
                Intent newact1=new Intent(email_input.this,verify_email.class);
                newact1.putExtra("email_id", em1);
              //  Toast.makeText(email_input.this,"hello"+result,Toast.LENGTH_LONG).show();
               startActivity(newact1);
                pdLoading.dismiss();
            }
            else{
                Toast.makeText(email_input.this,"some error occured",Toast.LENGTH_LONG).show();

                pdLoading.dismiss();
            }

        }
    }

}


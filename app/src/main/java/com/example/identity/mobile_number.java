package com.example.identity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class mobile_number extends AppCompatActivity {
CheckBox c;
EditText num;
    String num1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_number);
        c=(CheckBox)findViewById(R.id.checkbox);
        num=(EditText)findViewById(R.id.letter1);
        c.setOnClickListener(checked);
        if(Build.VERSION.SDK_INT>=21){
        Window window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.number_background));}
    }
    private View.OnClickListener checked = new View.OnClickListener() {
        public void onClick(View v) {
          if(c.isChecked())
          {
              c.setError(null);
          }
        }
    };
    public static boolean isValid(String number)

    {
        if(number.length()!=10)
            return false;

        for(int i=0;i<number.length();i++)
        {
            if(number.charAt(i)<='9' && number.charAt(i)>='0')
            {
                continue;
            }
            else
            {
                return false;
            }
        }
        return true;
    }
    public void go(View view)
    {
        //Toast.makeText(this,"CALLED",Toast.LENGTH_LONG).show();
        if(c.isChecked())
        {
           num1=num.getText().toString();
        if(isValid(num1)){

            new mobile_number.AsyncLogin().execute(num1);
        }
        else{
            num.setError("Enter number in correct format");
        }
        }
        else{
            c.setError("Accept the terms and condition");
        }


    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(mobile_number.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
                RequestBody body = RequestBody.create( "mobile="+params[0],mediaType);
                server a=new server();
                String name=a.getServer_name();
                String urll=name+"/verify/mobile/otp";
                Request request = new Request.Builder()
                        .url(urll)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                Response response = client.newCall(request).execute();
                //Toast.makeText(mobile_number.this,response.toString(),Toast.LENGTH_LONG).show();
               return response.body().string();
            }catch (Exception e)
            {
                return "error"+e;
            }

        }
        @Override
        protected void onPostExecute(String result) {
            boolean isFound = result.indexOf("true") !=-1? true: false;
            // Toast.makeText(email_input.this,"hello"+result,Toast.LENGTH_LONG).show();
            if (isFound ) {
                Intent newact1=new Intent(mobile_number.this,verify_mobile.class);
                newact1.putExtra("number", num1);
                startActivity(newact1);
                pdLoading.dismiss();}
            else{
              //  Toast.makeText(mobile_number.this,"hello"+result,Toast.LENGTH_LONG).show();
             //   Toast.makeText(mobile_number.this,"some error occured",Toast.LENGTH_LONG).show();

                pdLoading.dismiss();
            }

        }
    }
}

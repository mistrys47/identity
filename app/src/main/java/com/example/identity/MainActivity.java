package com.example.identity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
Button verify_email;
EditText email_id;
TextView t1,t2,t3,t4;
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verify_email=(Button)findViewById(R.id.email_verify);
       // email_id=(EditText)findViewById(R.id.email_id);
        verify_email.setOnClickListener(verify_email_process);
        //t1=(TextView)findViewById(R.id.t1);
        //t2=(TextView)findViewById(R.id.t2);
        //t3=(TextView)findViewById(R.id.t3);
        //t4=(TextView)findViewById(R.id.t4);
        qrScan = new IntentIntegrator(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
             //       t2.setText(obj.getString("name"));
               //     t4.setText(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private View.OnClickListener verify_email_process = new View.OnClickListener() {
        public void onClick(View v) {
            qrScan.initiateScan();
            Toast.makeText(v.getContext(),"hello",Toast.LENGTH_LONG).show();
        }
    };
    public void go(View v)
    {
        Intent newact1=new Intent(MainActivity.this,email_input.class);
        startActivity(newact1);
    }
    public void go1(View v)
    {
        Intent newact1=new Intent(MainActivity.this,home.class);
        try{startActivity(newact1);}
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,e+"",Toast.LENGTH_LONG).show();
        }
    }

}

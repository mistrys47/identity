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

        qrScan = new IntentIntegrator(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {

                try {

                    JSONObject obj = new JSONObject(result.getContents());

                } catch (JSONException e) {
                    e.printStackTrace();

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

package com.example.identity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Button scan_qr;
String data1;
Boolean done;
FrameLayout f1;

    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        done=false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        f1=(FrameLayout)findViewById(R.id.fl1);
        scan_qr=(Button)findViewById(R.id.Scan);
        // email_id=(EditText)findViewById(R.id.email_id);
        scan_qr.setOnClickListener(findtext);
        qrScan = new IntentIntegrator(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                    data1=result.getContents();
                    done=true;
                   Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                 //   Toast.makeText(this,"tried to change",Toast.LENGTH_LONG).show();
//                   on_scan frag = new on_scan();
//                    Bundle b = new Bundle();
//                    b.putString("user", "steve");
//                    frag.setArguments(b);
//
//                    FragmentManager fragmentManager=getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.fl1,frag).addToBackStack(null).commit();

//call("hello");


                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(this, result.getContents()+e, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void call(String data)
    {
        Toast.makeText(this,"tried to change",Toast.LENGTH_LONG).show();
        on_scan frag = new on_scan();
        Bundle b = new Bundle();
        b.putString("user", data);
        frag.setArguments(b);

        FragmentManager fragmentManager=getSupportFragmentManager();
        f1.removeAllViews();
        fragmentManager.beginTransaction().replace(R.id.fl1,frag).addToBackStack(null).commit();

    }
    private View.OnClickListener findtext = new View.OnClickListener() {
        public void onClick(View v) {
          //  qrScan.initiateScan();
            //data.getStr
           new home.AsyncLogin().execute();
        //    String str_result= new RunInBackGround().execute();
        //    while(!done)
      //   call(data1);
            Toast.makeText(v.getContext(),"hello",Toast.LENGTH_LONG).show();
        }
    };

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
      //  ProgressDialog pdLoading = new ProgressDialog(verify_mobile.this);


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(String... params) {

            qrScan.initiateScan();
            while(!done);
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
           // if(done)
            call(data1);
        }
    }
            @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,(Fragment) new userdetails()).addToBackStack(null).commit();
        } else if (id == R.id.nav_gallery) {

            on_scan frag = new on_scan();
            Bundle b = new Bundle();
            b.putString("user", "steve");
            frag.setArguments(b);

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,frag).addToBackStack(null).commit();


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

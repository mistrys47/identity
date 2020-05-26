package com.example.identity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton scan_qr;
    String data1;
    Integer Storagepermession=1;
    Boolean done,m;
    Integer exp_day,exp_mon,exp_yr,current_day,current_mon,current_yr;
    FrameLayout f1;
    database db=new database(this);
    SQLiteDatabase db1;

    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        done=false;
        m=checkpermissionstat();
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.number_background));}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        f1=(FrameLayout)findViewById(R.id.fl1);
        scan_qr=(ImageButton)findViewById(R.id.Scan);
        check_for_expiry_fields();
  //      db1 = db.getWritableDatabase();
//        boolean x=db.insert11(db1,"email","","guptashubham1798@gmail.com","admin","true","key1","");
//
        //boolean x33=db.insert11(db1,"mobile","","9284959664","admin","true","key3","");
        boolean x1=db.checkemail(db1);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl1,(Fragment) new user_details_card()).addToBackStack(null).commit();

        boolean x2=db.checkmobile(db1);
        if(x1==true && x2==true)
        {
            try {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView t1 = (TextView) headerView.findViewById(R.id.Email_id);
                t1.setText(db.getvalue(db1, "email"));
                t1 = (TextView) headerView.findViewById(R.id.contactnumber);
                t1.setText(db.getvalue(db1, "mobile"));
            }
            catch (Exception e)
            {
                Toast.makeText(this,""+e,Toast.LENGTH_LONG).show();
            }
            // Intent intent=new Intent(this,home.class);
            // startActivity(intent);
        }
        else
        {
            Intent intent=new Intent(this,email_input.class);
            startActivity(intent);
        }

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
            done=true;
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                data1="";
            } else {

                try {

                    JSONObject obj = new JSONObject(result.getContents());
                    data1=result.getContents();
                    done=true;
                    //  Toast.makeText(this,"m1"+ result.getContents(), Toast.LENGTH_LONG).show();
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
        if(data.equals(""))
        {

        }
        else {
            //   Toast.makeText(this, "tried to change", Toast.LENGTH_LONG).show();
            on_scan frag = new on_scan();
            Bundle b = new Bundle();
            b.putString("user", data);
            frag.setArguments(b);

            FragmentManager fragmentManager = getSupportFragmentManager();
            f1.removeAllViews();
            fragmentManager.beginTransaction().replace(R.id.fl1, frag).addToBackStack(null).commit();
        }
    }
    private View.OnClickListener findtext = new View.OnClickListener() {
        public void onClick(View v) {
            //  qrScan.initiateScan();
            //data.getStr
            done=false;
            new home.AsyncLogin().execute();
            //    String str_result= new RunInBackGround().execute();
            //    while(!done)
            //   call(data1);
            //   Toast.makeText(v.getContext(),"hello",Toast.LENGTH_LONG).show();
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
    public boolean checkpermissionstat()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==Storagepermession)
        {
            return true;
        }
        else
        {
            requestperm();
            return false;
        }
    }
    public boolean requestperm()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},Storagepermession);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //  super.onBackPressed();
            //Intent goToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            //goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
            //startActivity(goToMainActivity);
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
    public void m1(View v){
        Intent i1=new Intent(this,MainActivity.class);
        startActivity(i1);
        // Toast.makeText(this,"hi",Toast.LENGTH_LONG).show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.profile) {
            // f1.removeAllViews();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,(Fragment) new user_details_card()).addToBackStack(null).commit();
        } else if (id == R.id.details) {
            f1.removeAllViews();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,(Fragment) new add_user_details()).addToBackStack(null).commit();
        }
        else if (id == R.id.scan_qr1) {
            new home.AsyncLogin().execute();


        }
        else if (id == R.id.Delete) {
            db1 = db.getWritableDatabase();
            db.delete_table2(db1);


        }
        else if(id== R.id.Showdetails1)
        {
            try {
                f1.removeAllViews();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fl1, (Fragment) new allowed_websites()).addToBackStack(null).commit();
            }
            catch (Exception e)
            {
                Toast.makeText(this,""+e,Toast.LENGTH_LONG).show();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void check_for_expiry_fields()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
        String todays_date = df.format(c);
        db1 = db.getWritableDatabase();
        Cursor results=db.get_all_fields_with_expiry(db1);
        List<String> Expired = new ArrayList<String>();


        List<String> checker = new ArrayList<String>();
        String Expiry_date1="11/05/2010";
        Boolean temp=compare_todays_date_with_exp_date(Expiry_date1,todays_date);
        checker.add(""+exp_day+""+exp_mon+""+""+exp_yr+""+""+current_day+""+current_mon+""+""+current_yr+""+temp+"\n");
        Expiry_date1="11/05/2020";
        temp=compare_todays_date_with_exp_date(Expiry_date1,todays_date);
        checker.add(""+exp_day+""+exp_mon+""+""+exp_yr+""+current_day+""+current_mon+""+""+current_yr+""+temp+"\n");
        Expiry_date1="28/01/2020";
        temp=compare_todays_date_with_exp_date(Expiry_date1,todays_date);
        checker.add(""+exp_day+""+exp_mon+""+""+exp_yr+""+current_day+""+current_mon+""+""+current_yr+""+temp+"\n");
        Expiry_date1="28/05/2020";
        temp=compare_todays_date_with_exp_date(Expiry_date1,todays_date);
        checker.add(""+exp_day+""+exp_mon+""+""+exp_yr+""+current_day+""+current_mon+""+""+current_yr+""+temp+"\n");
        Expiry_date1="11/05/2021";
        temp=compare_todays_date_with_exp_date(Expiry_date1,todays_date);
        checker.add(""+exp_day+""+exp_mon+""+""+exp_yr+""+current_day+""+current_mon+""+""+current_yr+""+temp+"\n");

        while (results.moveToNext()) {
            String field=results.getString(0);
            String Expiry_date="11/05/2010";
            //String Expiry_date=results.getString(6);
            if(compare_todays_date_with_exp_date(Expiry_date,todays_date))
            Expired.add(field+" exp is :"+Expiry_date);

        }
        Toast.makeText(this,""+checker.toString(),Toast.LENGTH_LONG).show();
    }
    Boolean compare_todays_date_with_exp_date(String exp_date,String current_date)
    {


        exp_day=Integer.parseInt(exp_date.substring(0,2));
        exp_mon=Integer.parseInt(exp_date.substring(3,5));
        exp_yr=Integer.parseInt(exp_date.substring(6,10));
        current_day=Integer.parseInt(current_date.substring(0,2));
        current_mon=Integer.parseInt(current_date.substring(3,5));
        current_yr=Integer.parseInt(current_date.substring(6,10));
        //true meaning it is valid
        if(current_yr==exp_yr)
        {
            if(current_mon==exp_mon)
            {
                if(current_day==exp_day)return true;
                else if(current_day<exp_day)return true;
                return false;

            }else if(current_mon<exp_mon) return true;
            return false;

        }if(current_yr<exp_yr) return true;
        return false;



        //Toast.makeText(this,""+exp_day+""+exp_mon+""+exp_yr,Toast.LENGTH_LONG).show();

        //return true;
    }

}

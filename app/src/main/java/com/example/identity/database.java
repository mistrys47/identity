package com.example.identity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "identity.db";
    public static final String TABLE_NAME1 = "userdetails";
    public static final String COL1 = "field_name";
    public static final String COL2 = "value";
    public static final String COL3 = "verified";
    public static final String COL4 = "verified_by";
    public static final String COL5 = "transaction_id";
    public static final String COL6 = "expiry_date";
    public static final String COL7 = "verifier_url";
    public static final String COL8 = "expiry_date";
    public static final String COL9 = "last_verified_value";
    public static final String TABLE_NAME2 = "serviceproviders";
    public static final String COL21 = "id";
    public static final String COL22 = "name";
    public static final String COL23 = "url";

    public database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME1 +" (field_name TEXT,value TEXT,verified TEXT,verified_by TEXT,transaction_id TEXT,verifier_url TEXT,expiry_date TEXT,last_verified_value TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME2 +" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,data TEXT,url TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }

    public boolean insert1(SQLiteDatabase db,String field_name,String url,String value,String verifier,String verified,String expiry_date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,field_name);
        contentValues.put(COL2,value);
        contentValues.put(COL3,verified);
        contentValues.put(COL4,verifier);
        contentValues.put(COL5,"");
        contentValues.put(COL7,url);
        contentValues.put(COL8,expiry_date);
        long x=db.insert(TABLE_NAME1,null,contentValues);
        if(x==-1)
        {
            return false;
        }
        return true;
    }

    public boolean checkemail(SQLiteDatabase db){
        Cursor cursor = db.query(TABLE_NAME1,new String[]{"field_name","verified" },"field_name=? AND verified=?",new String[]{"email","true"},null,null,null  );
        if(cursor.getCount()>0)
        {
            return true;
        }
        else return false;
    }
    public boolean checkmobile(SQLiteDatabase db){
        Cursor cursor = db.query(TABLE_NAME1,new String[]{"field_name","verified" },"field_name=? AND verified=?",new String[]{"mobile","true"},null,null,null  );
        if(cursor.getCount()>0)
        {
            return true;
        }
        else return false;
    }
    public int checkfield(SQLiteDatabase db,String s){
        Cursor cursor = db.query(TABLE_NAME1,new String[]{"field_name","verified"},"field_name=? AND verified=?",new String[]{s,"true"},null,null,null  );
        if(cursor.getCount()>0)
        {
            return 1;
        }
        cursor = db.query(TABLE_NAME1,new String[]{"field_name","verified"},"field_name=? AND verified=?",new String[]{s,"false"},null,null,null  );
        if(cursor.getCount()>0)
        {
            return 0;
        }
        cursor = db.query(TABLE_NAME1,new String[]{"field_name","verified"},"field_name=? AND verified=?",new String[]{s,"expired"},null,null,null  );
        if(cursor.getCount()>0)
        {
            return 2;
        }
        return -1;
    }
    public String getvalue(SQLiteDatabase db,String s){
        Cursor cursor=db.query(TABLE_NAME1,new String[]{"value"},"field_name=?",new String[]{s},null,null,null  );
        String s1="";
        while(cursor.moveToNext())
        {
            s1=cursor.getString(0);
        }
        return s1;
    }
    public String getkey(SQLiteDatabase db,String s){
        Cursor cursor=db.query(TABLE_NAME1,new String[]{"transaction_id"},"field_name=?",new String[]{s},null,null,null  );
        String s1="";
        while(cursor.moveToNext())
        {
            s1=cursor.getString(0);
        }
        return s1;
    }
    public String getexpiry(SQLiteDatabase db,String s){
        Cursor cursor=db.query(TABLE_NAME1,new String[]{"expiry_date"},"field_name=?",new String[]{s},null,null,null  );
        String s1="";
        while(cursor.moveToNext())
        {
            s1=cursor.getString(0);
        }
        return s1;
    }
    public Cursor getfields(SQLiteDatabase db){
        Cursor cursor=db.rawQuery("select field_name,value,expiry_date from userdetails",null);
        return cursor;
    }
    public String gettransaction_id(SQLiteDatabase db,String s){
        Cursor cursor=db.query(TABLE_NAME1,new String[]{"transaction_id"},"field_name=?",new String[]{s},null,null,null  );
        String s1="";

        while(cursor.moveToNext())
        {
            s1=cursor.getString(0);
        }
        return s1;

        //return s1;
    }
    public String geturl1(SQLiteDatabase db,String s){
        Cursor cursor=db.query(TABLE_NAME1,new String[]{"verifier_url"},"field_name=?",new String[]{s},null,null,null  );
        String s1="";

        while(cursor.moveToNext())
        {
            s1=cursor.getString(0);
        }
        return s1;
        //return s1;
    }
    public String getverifier_name(SQLiteDatabase db,String s){
        Cursor cursor=db.query(TABLE_NAME1,new String[]{"verified_by"},"field_name=?",new String[]{s},null,null,null  );
        String s1="";
        while(cursor.moveToNext())
        {
            s1=cursor.getString(0);
        }
        return s1;
    }
    public String getverifier_url(SQLiteDatabase db,String s){
        Cursor cursor=db.query(TABLE_NAME1,new String[]{"verifier_url"},"field_name=?",new String[]{s},null,null,null  );
        String s1="";
        while(cursor.moveToNext())
        {
            s1=cursor.getString(0);
        }
        return s1;
    }
    public Cursor getrevertfields(SQLiteDatabase db){
        Cursor cursor=db.query(TABLE_NAME1, new String[]{"field_name","value","expiry_date"}, COL9 + " != ?", new String[]{""}, null, null, null);
        return cursor;
    }
    public String getlast_verified_value(SQLiteDatabase db,String s){
        Cursor cursor=db.query(TABLE_NAME1,new String[]{"last_verified_value"},"field_name=?",new String[]{s},null,null,null  );
        String s1="";
        while(cursor.moveToNext())
        {
            s1=cursor.getString(0);
        }
        return s1;
    }
    public boolean updatevalue(SQLiteDatabase db,String field,String value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,value);
        contentValues.put(COL3,"false");
        long re=db.update(TABLE_NAME1,
                contentValues,
                "field_name" + " = ? ",
                new String[]{field});
        if(re == -1)
            return false;
        else
            return true;
    }
    public boolean update(SQLiteDatabase db,String field,String value,String fn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(field,value);
        long re=db.update(TABLE_NAME1,
                contentValues,
                "field_name" + " = ? ",
                new String[]{fn});
        if(re == -1)
            return false;
        else
            return true;
    }
    public boolean deletetable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        return true;
    }
    public Cursor getuserdetails(SQLiteDatabase db){
        Cursor c=db.rawQuery("select * from " +TABLE_NAME1,null);
        return c;
    }

    public boolean insert2(SQLiteDatabase db,String name,String url,String data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL22,name);
        contentValues.put(COL23,url);
        contentValues.put("data",data);
        long x = db.insert(TABLE_NAME2,null,contentValues);
        if(x==-1)
            return false;
        return true;
    }
    public boolean check_serviceprovider(SQLiteDatabase db,String  s){
        Cursor cursor = db.query(TABLE_NAME2,new String[]{"name"},"name=?",new String[]{s},null,null,null  );
        if(cursor.getCount()>0)
            return true;
        return false;
    }
    public Cursor getwebsitedetails(SQLiteDatabase db){
        Cursor c=db.rawQuery("select * from " +TABLE_NAME2,null);
        return c;
    }
    public boolean updatevalue1(SQLiteDatabase db,String field,String value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,value);
        contentValues.put(COL3,"true");
        long re=db.update(TABLE_NAME1,
                contentValues,
                "field_name" + " = ? ",
                new String[]{field});
        if(re == -1)
            return false;
        else
            return true;
    }
    public boolean update1(SQLiteDatabase db,String field,String value)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL3,value);
        long re=db.update(TABLE_NAME1,
                contentValues,
                "field_name" + " = ? ",
                new String[]{field});
        if(re == -1)
            return false;
        else
            return true;
    }
    public boolean update_db1(SQLiteDatabase db,String field,String value,String s)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(field,value);
        long re=db.update(TABLE_NAME1,
                contentValues,
                "field_name" + " = ? ",
                new String[]{s});
        if(re == -1)
            return false;
        else
            return true;
    }
    public boolean insert11(SQLiteDatabase db,String field_name,String url,String value,String verifier,String verified,String key,String expiry_date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,field_name);
        contentValues.put(COL2,value);
        contentValues.put(COL3,verified);
        contentValues.put(COL4,verifier);
        contentValues.put(COL5,key);
        contentValues.put(COL7,url);
        contentValues.put(COL8,expiry_date);
        long x=db.insert(TABLE_NAME1,null,contentValues);
        if(x==-1)
        {
            return false;
        }
        return true;
    }

    public boolean update2(SQLiteDatabase db,String field,String value)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL9,"");
        contentValues.put(COL5,value);
        long re=db.update(TABLE_NAME1,
                contentValues,
                "field_name" + " = ? ",
                new String[]{field});
        if(re == -1)
            return false;
        else
            return true;
    }
    public void delete_table2(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("CREATE TABLE " + TABLE_NAME2 +" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,data TEXT,url TEXT)");
    }

    public Cursor get_all_fields_with_expiry(SQLiteDatabase db)
    {
        Cursor res = db.rawQuery("select * from "+TABLE_NAME1,null);
        return res;
    }
    public Cursor get_all_service_providers_url(SQLiteDatabase db)
    {
        Cursor res = db.rawQuery("select data,url from "+TABLE_NAME2,null);
        return res;
    }
    public Cursor get_all_service_providers_data(SQLiteDatabase db)
    {
        Cursor res = db.rawQuery("select data from "+TABLE_NAME2,null);
        return res;
    }


    public boolean update_field_to_expired(SQLiteDatabase db,String field){
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL3,"expired");
        long re=db.update(TABLE_NAME1, contentValues, "field_name" + " = ? ", new String[]{field});
        if(re == -1)
            return false;
        else
            return true;
    }
}









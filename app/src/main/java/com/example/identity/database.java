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

    public database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME1 +" (field_name TEXT,value TEXT,verified TEXT,verified_by TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(db);
    }

    public boolean insert1(SQLiteDatabase db,String field_name,String value,String verifier) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,field_name);
        contentValues.put(COL2,value);
        contentValues.put(COL3,"true");
        contentValues.put(COL4,verifier);
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
    public boolean deletetable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        return true;
    }
    public Cursor getuserdetails(SQLiteDatabase db){
        Cursor c=db.rawQuery("select * from " +TABLE_NAME1,null);
        return c;
    }
}









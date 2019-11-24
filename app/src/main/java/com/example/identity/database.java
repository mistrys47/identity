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
    public static final String TABLE_NAME2 = "serviceproviders";
    public static final String COL21 = "id";
    public static final String COL22 = "name";
    public database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME1 +" (field_name TEXT,value TEXT,verified TEXT,verified_by TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME2 +" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
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
    public boolean updatevalue(SQLiteDatabase db,String field,String value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,value);
        contentValues.put(COL3,"false");
        long re=db.update(TABLE_NAME1,
                contentValues,
                "field_name " + " = ? ",
                new String[]{field});
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
    public boolean insert2(SQLiteDatabase db,String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL22,name);
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
}









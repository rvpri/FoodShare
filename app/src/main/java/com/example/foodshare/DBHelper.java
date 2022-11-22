package com.example.foodshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table users(email TEXT primary key , username TEXT,phoneno TEXT not null, password TEXT not null, pickupaddress TEXT not null )");
        DB.execSQL("create Table donationdetails(email TEXT not null,food TEXT not null, date TEXT not null,time TEXT not null, serves TEXT not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists users");
        DB.execSQL("drop Table if exists donationdetails");
    }

    public Boolean insertuserdata1(String email, String username,String phoneno, String password, String pickupaddress)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("Username", username);
        contentValues.put("Phoneno",phoneno);
        contentValues.put("Password", password);
        contentValues.put("PickupAddress", pickupaddress);
        long result=DB.insert("users", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean checkemail (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where email=?", new String[] {email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }

    public Boolean checkpassword(String email,String password)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where email=? and password=?", new String[] {email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }

    public Boolean insertdonationdata(String email,String food,String date, String time, String serves)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("food", food);
        contentValues.put("date",date);
        contentValues.put("time", time);
        contentValues.put("serves", serves);
        long result=DB.insert("donationdetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getdata (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from donationdetails where email=? ", new String[] {email});
        return cursor;

    }
    public Cursor retreivedata (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where email=? ", new String[] {email});
        return cursor;

    }
    public Cursor totalserves ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor1 = DB.rawQuery("Select SUM(serves)  from donationdetails",null);
        return cursor1;

    }
    public Boolean updateuserdata(String email,String name,String phone, String password, String address) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", name);
        contentValues.put("phoneno", phone);
        contentValues.put("password", password);
        contentValues.put("pickupAddress", address);
        long result = DB.update("users", contentValues, "email=?", new String[]{email});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}



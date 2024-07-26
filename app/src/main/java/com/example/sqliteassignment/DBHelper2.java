package com.example.sqliteassignment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper2 extends SQLiteOpenHelper {

    public DBHelper2 (Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Studentdetails(name TEXT primary key, idno TEXT, gender TEXT, course TEXT)");        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Studentdetails");
    }

    public Boolean insertUserData(String name, String idno, String gender, String course){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("idno", idno);
        contentValues.put("gender", gender);
        contentValues.put("course", course);

        long result = db.insert("Studentdetails", null, contentValues);

        return result != -1;
    }

    public Boolean updateUserData(String name, String idno, String gender, String course){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idno", idno);
        contentValues.put("gender", gender);
        contentValues.put("course", course);

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from Studentdetails where name = ?", new String[]{name});

        if (cursor.getCount() > 0){
            long result = db.update("Studentdetails", contentValues, "name=?", new String[]{name});
            return result != -1;
        } else {
            return false;
        }
    }

    public Boolean deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from Studentdetails where name = ?", new String[]{name});

        if (cursor.getCount() > 0){
            long result = db.delete("Studentdetails", "name=?", new String[]{name});
            return result != -1;
        } else {
            return false;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from Studentdetails", null);

        return cursor;
    }
}


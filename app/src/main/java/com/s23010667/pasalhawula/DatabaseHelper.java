package com.s23010667.pasalhawula;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME= "user_details";
    public static final String COL_1="ID";
    public static final String COL_2="SchoolName";
    public static final String COL_3="PersonName";
    public static final String COL_4="Email";
    public static final String COL_5="MobileNo";
    public static final String COL_6="Password";
    public static final String COL_7="ConfirmPW";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+ "SchoolName TEXT, PersonName TEXT, Email TEXT, MobileNo TEXT, Password TEXT, ConfirmPW TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //insert Registration Data
    public boolean insertData(String SchoolName,String PersonName ,String Email,String MobileNo ,String Password ,String ConfirmPW ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, SchoolName);
        contentValues.put(COL_3, PersonName);
        contentValues.put(COL_4, Email);
        contentValues.put(COL_5, MobileNo);
        contentValues.put(COL_6, Password);
        contentValues.put(COL_7, ConfirmPW);

        long results = db.insert(TABLE_NAME, null, contentValues);
        if (results == -1)
            return false;
        else
            return true;
    }
}

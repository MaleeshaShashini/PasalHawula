package com.s23010667.pasalhawula;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

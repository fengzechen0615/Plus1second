package com.example.francisfeng.plus1second;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kumbaya on 2017/5/15.
 */

public class database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="schedule.db";
    public database(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE schedule(date CHAR(10), time CHAR(20) unique, course CHAR(30), things CHAR(30))");
        db.execSQL("CREATE TABLE todoList(date CHAR(10), time CHAR(20) unique, course CHAR(30), things CHAR(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.example.francisfeng.plus1second.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stiles on 16/4/12.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASENAME = "schedule.db";
    public final static String TABLENAME = "class";
    public final static String ID = "id";
    public final static String CLASSNAME = "class_name";
    public final static String TEACHERNAME = "teacher_name";
    public final static String CLASSROOM = "classroom";
    public final static String WEEK = "week";
    public final static String START = "start";
    public final static String LENGTH = "length";

//    public final static String TABLENAME1 = "event";
//    public final static String ID1 = "id";
//    public final static String DATE="date";
//    public final static String THINGS = "things";
//    public final static String START_HOUR = "start_hour";
//    public final static String START_MINU = "start_minu";
//    public final static String END_HOUR = "end_hour";
//    public final static String END_MINU = "end_minu";


    public final static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + TABLENAME + " (" +
                ID + " integer primary key autoincrement, " +
                CLASSNAME + " text, " +
                TEACHERNAME + " text, " +
                CLASSROOM + " text, " +
                WEEK + " integer, " +
                START + " integer, " +
                LENGTH + " integer);";
        sqLiteDatabase.execSQL(sql);

//        sql = "create table if not exists " + TABLENAME1 + " (" +
//                ID1 + " text primary key autoincrement, " +
//                DATE + " text, " +
//                THINGS + " text, " +
//                START_HOUR + " integer, " +
//                START_MINU + " integer, " +
//                END_HOUR + "integer, " +
//                END_MINU + "integer)";
//        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

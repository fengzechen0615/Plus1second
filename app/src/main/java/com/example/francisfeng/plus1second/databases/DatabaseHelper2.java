package com.example.francisfeng.plus1second.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ray on 2017/6/2.
 */

public class DatabaseHelper2 extends SQLiteOpenHelper {

    public final static String DATABASENAME = "schedule.db";
        public final static String TABLENAME = "event";
        public final static String ID = "id";
        public final static String DATE="date";
        public final static String THINGS = "things";
        public final static String START_HOUR = "start_hour";
        public final static String START_MINU = "start_minu";
        public final static String END_HOUR = "end_hour";
        public final static String END_MINU = "end_minu";

        public final static int VERSION = 1;

        public DatabaseHelper2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, DATABASENAME, null, VERSION);
        }


        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql = "create table if not exists " + TABLENAME + " (" +
                    ID + " text primary key autoincrement, " +
                    DATE + " text, " +
                    THINGS + " text, " +
                    START_HOUR + " integer, " +
                    START_MINU + " integer, " +
                    END_HOUR + "integer, " +
                    END_MINU + "integer)";
            sqLiteDatabase.execSQL(sql);
        }


        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
}

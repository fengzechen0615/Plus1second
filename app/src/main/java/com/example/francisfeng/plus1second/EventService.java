package com.example.francisfeng.plus1second;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.francisfeng.plus1second.databases.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by ray on 2017/5/31.
 */

public class EventService {
    private SQLiteDatabase Rdb;
    private SQLiteDatabase Wdb;

    public EventService(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        Rdb = helper.getReadableDatabase();
        Wdb = helper.getWritableDatabase();
    }

    public void save(Event e) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ID1, e.getdate()+e.getthings());
        values.put(DatabaseHelper.DATE,e.getdate());
        values.put(DatabaseHelper.THINGS,e.getthings());
        values.put(DatabaseHelper.START_HOUR,e.getstarthour());
        values.put(DatabaseHelper.START_MINU,e.getstartminu());
        values.put(DatabaseHelper.END_HOUR,e.getendhour());
        values.put(DatabaseHelper.END_MINU,e.getendminu());
        Wdb.insert(DatabaseHelper.TABLENAME2, "id", values);
    }

    public ArrayList<Event> findAll(String date) {
        ArrayList<Event> list = new ArrayList<>();

        Cursor cursor = Rdb.query(DatabaseHelper.TABLENAME2, null, "date=?", new String[]{String.valueOf(date)}, null, null, null);

        int idIndex = cursor.getColumnIndex(DatabaseHelper.ID1);
        int DateIndex = cursor.getColumnIndex(DatabaseHelper.DATE);
        int ThingsIndex = cursor.getColumnIndex(DatabaseHelper.THINGS);
        int starthourIndex = cursor.getColumnIndex(DatabaseHelper.START_HOUR);
        int startminuIndex = cursor.getColumnIndex(DatabaseHelper.START_MINU);
        int endhourIndex = cursor.getColumnIndex(DatabaseHelper.END_HOUR);
        int endminuIndex = cursor.getColumnIndex(DatabaseHelper.END_MINU);

        String id;
        String Date;
        String Things;
        int start_hour;
        int end_hour;
        int start_minu;
        int end_minu;


        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            id = cursor.getString(idIndex);
            Date = cursor.getString(DateIndex);
            Things=cursor.getString(ThingsIndex);
            start_hour = cursor.getInt(starthourIndex);
            start_minu = cursor.getInt(startminuIndex);
            end_hour = cursor.getInt(endhourIndex);
            end_minu = cursor.getInt(endminuIndex);

            Event e = new Event(Date, Things, start_hour, start_minu, end_hour, end_minu);
            e.id = id;
            list.add(e);
        }

        cursor.close();
        return list;
    }

    public ArrayList<Event> findById(String id) {

        ArrayList<Event> list = new ArrayList<>();

        Cursor cursor = Rdb.query(DatabaseHelper.TABLENAME2, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

        int idIndex = cursor.getColumnIndex(DatabaseHelper.ID1);
        int DateIndex = cursor.getColumnIndex(DatabaseHelper.DATE);
        int ThingsIndex = cursor.getColumnIndex(DatabaseHelper.THINGS);
        int starthourIndex = cursor.getColumnIndex(DatabaseHelper.START_HOUR);
        int startminuIndex = cursor.getColumnIndex(DatabaseHelper.START_MINU);
        int endhourIndex = cursor.getColumnIndex(DatabaseHelper.END_HOUR);
        int endminuIndex = cursor.getColumnIndex(DatabaseHelper.END_MINU);

        String id1;
        String Date;
        String Things;
        int start_hour;
        int end_hour;
        int start_minu;
        int end_minu;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            id1 = cursor.getString(idIndex);
            Date = cursor.getString(DateIndex);
            Things=cursor.getString(ThingsIndex);
            start_hour = cursor.getInt(starthourIndex);
            start_minu = cursor.getInt(startminuIndex);
            end_hour = cursor.getInt(endhourIndex);
            end_minu = cursor.getInt(endminuIndex);

            Event e = new Event(Date, Things, start_hour, start_minu, end_hour, end_minu);
            e.id = id1;
            list.add(e);
        }
        return list;

    }

    public void deleteById(String id) {
        Wdb.delete(DatabaseHelper.TABLENAME2, "id=?", new String[] {String.valueOf(id)});
    }

}

package com.example.francisfeng.plus1second;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.francisfeng.plus1second.databases.DatabaseHelper2;

import java.util.ArrayList;

/**
 * Created by ray on 2017/6/2.
 */

public class EventService {
    private SQLiteDatabase Rdb;
    private SQLiteDatabase Wdb;
    public EventService(Context context) {
        DatabaseHelper2 helper = new DatabaseHelper2(context, "schedule.db", null, 1);
        Rdb = helper.getReadableDatabase();
        Wdb = helper.getWritableDatabase();
    }

    public void save(Event e) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper2.ID, e.getdate());
        values.put(DatabaseHelper2.START_HOUR,e.getstarthour());
        values.put(DatabaseHelper2.START_MINU,e.getstartminu());
        values.put(DatabaseHelper2.END_HOUR,e.getendhour());
        values.put(DatabaseHelper2.END_MINU,e.getendminu());
        values.put(DatabaseHelper2.THINGS,e.getthings());
        Wdb.insert(DatabaseHelper2.TABLENAME, "id", values);
    }

//    public void deleteByTime(String time) {
//        Wdb.delete(DatabaseHelper2.TABLENAME, "time=?", new String[] {String.valueOf(time)});
//    }

    public ArrayList<Event> findAll() {
        ArrayList<Event> list = new ArrayList<>();
        Cursor cursor = Rdb.query(DatabaseHelper2.TABLENAME, null, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(DatabaseHelper2.ID);
        int DateIndex = cursor.getColumnIndex(DatabaseHelper2.DATE);
        int ThingsIndex = cursor.getColumnIndex(DatabaseHelper2.THINGS);
        int starthourIndex = cursor.getColumnIndex(DatabaseHelper2.START_HOUR);
        int startminuIndex = cursor.getColumnIndex(DatabaseHelper2.START_MINU);
        int endhourIndex = cursor.getColumnIndex(DatabaseHelper2.END_HOUR);
        int endminuIndex = cursor.getColumnIndex(DatabaseHelper2.END_MINU);

        int id;
        String Date;
        String Things;
        int start_hour;
        int end_hour;
        int start_minu;
        int end_minu;


        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            id = cursor.getInt(idIndex);
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

    public void deleteById(int id) {
        Wdb.delete(DatabaseHelper2.TABLENAME, "id=?", new String[] {String.valueOf(id)});
    }

    public Event findById(int id) {
        Event e;
        Cursor cursor = Rdb.query(DatabaseHelper2.TABLENAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

        int DateIndex = cursor.getColumnIndex(DatabaseHelper2.DATE);
        int ThingsIndex = cursor.getColumnIndex(DatabaseHelper2.THINGS);
        int starthourIndex = cursor.getColumnIndex(DatabaseHelper2.START_HOUR);
        int startminuIndex = cursor.getColumnIndex(DatabaseHelper2.START_MINU);
        int endhourIndex = cursor.getColumnIndex(DatabaseHelper2.END_HOUR);
        int endminuIndex = cursor.getColumnIndex(DatabaseHelper2.END_MINU);

        String Date;
        String Things;
        int start_hour;
        int end_hour;
        int start_minu;
        int end_minu;

        cursor.moveToFirst();

        Date = cursor.getString(DateIndex);
        start_hour = cursor.getInt(starthourIndex);
        start_minu = cursor.getInt(startminuIndex);
        end_hour = cursor.getInt(endhourIndex);
        end_minu = cursor.getInt(endminuIndex);
        Things=cursor.getString(ThingsIndex);

        e =new Event(Date, Things, start_hour, start_minu, end_hour, end_minu);
        cursor.close();

        return e;
    }

    public Event findByTime(String time) {
        Event e;
        Cursor cursor = Rdb.query(DatabaseHelper2.TABLENAME, null, "time=?", new String[]{String.valueOf(time)}, null, null, null);
        int idIndex = cursor.getColumnIndex(DatabaseHelper2.ID);
        int DateIndex = cursor.getColumnIndex(DatabaseHelper2.DATE);
        int ThingsIndex = cursor.getColumnIndex(DatabaseHelper2.THINGS);
        int starthourIndex = cursor.getColumnIndex(DatabaseHelper2.START_HOUR);
        int startminuIndex = cursor.getColumnIndex(DatabaseHelper2.START_MINU);
        int endhourIndex = cursor.getColumnIndex(DatabaseHelper2.END_HOUR);
        int endminuIndex = cursor.getColumnIndex(DatabaseHelper2.END_MINU);

        String Date;
        String Things;
        int start_hour;
        int end_hour;
        int start_minu;
        int end_minu;

        cursor.moveToFirst();

        Date = cursor.getString(DateIndex);
        start_hour = cursor.getInt(starthourIndex);
        start_minu = cursor.getInt(startminuIndex);
        end_hour = cursor.getInt(endhourIndex);
        end_minu = cursor.getInt(endminuIndex);
        Things=cursor.getString(ThingsIndex);

        e =new Event(Date, Things, start_hour, start_minu, end_hour, end_minu);
        cursor.close();

        return e;
    }


}

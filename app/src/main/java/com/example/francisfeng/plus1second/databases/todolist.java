package com.example.francisfeng.plus1second.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.francisfeng.plus1second.database;
import com.example.francisfeng.plus1second.node.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumbaya on 2017/5/15.
 */

public class todolist {
    private database helper;

    public todolist(Context context){
        helper = new database(context);
    }

    public void add(String date,String time,String course,String thing){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into todolist (date,time,course,thing) values(?,?,?,?)", new Object[]{
                date, time, course, thing});
        db.close();
    }

    public void update(String date,String time,String course,String thing){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL("update todolist set date = ?, course = ?, thing = ? where time = ?", new Object[]{
                date, course, thing, time});
        db.close();
    }

    public void delete(String time){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from todolist where time =?", new Object[]{time});
        db.close();
    }

    public boolean search(String time){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from todolist where time =?", new String[]{time});
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }
    public List<Node> findAll(){
        SQLiteDatabase db =  helper.getWritableDatabase();
        List<Node> todolist = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from todolist", null);
        while(cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String course = cursor.getString(cursor.getColumnIndex("course"));
            String thing = cursor.getString(cursor.getColumnIndex("thing"));
            Node n = new Node(date, time, course, thing);
            todolist.add(n);
        }
        cursor.close();
        db.close();
        return todolist;
    }
}


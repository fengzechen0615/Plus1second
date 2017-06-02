package com.example.francisfeng.plus1second;

/**
 * Created by ray on 2017/6/2.
 */

public class Event {
    public int id;
    private String date;
    private int start_hour;
    private int end_hour;
    private int start_minu;
    private int end_minu;
    private String things;

    public Event(){}
    public Event(String date, String things, int start_hour, int start_minu, int end_hour, int end_minu)
    {
        this.date=date;
        this.start_hour = start_hour;
        this.start_minu = start_minu;
        this.end_hour = end_hour;
        this.end_minu = end_minu;
        this.things=things;
    }
    public String getdate()
    {
        return this.date;
    }

    public int getstarthour()
    {
        return start_hour;
    }

    public int getstartminu()
    {
        return start_minu;
    }

    public int getendhour()
    {
        return end_hour;
    }

    public int getendminu()
    {
        return end_minu;
    }

    public String getthings()
    {
        return this.things;
    }

    public void setthings(String things) {
        this.things = things;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public void setStart_minu(int start_minu) {
        this.start_minu = start_minu;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public void setEnd_minu(int end_minu) {
        this.end_minu = end_minu;
    }
}

package com.example.francisfeng.plus1second.system_time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kumbaya on 2017/5/15.
 */

public class getTime {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curtime = new Date(System.currentTimeMillis());
    public String str = format.format(curtime);
}

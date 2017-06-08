package com.example.francisfeng.plus1second;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.francisfeng.plus1second_test.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by francisfeng on 31/05/2017.
 */

public class Month extends AppCompatActivity {

    private MonthCalendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.month);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cal = (MonthCalendar)findViewById(R.id.cal);

        final List<DayFinish> list = new ArrayList<>();

        cal.setOnClickListener(new MonthCalendar.onClickListener() {
            @Override
            public void onLeftRowClick() {
                Toast.makeText(Month.this, "Last Month", Toast.LENGTH_SHORT).show();
                cal.monthChange(-1);
                new Thread(){
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(1000);
                            Month.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cal.setRenwu(list);
                                }
                            });
                        }catch (Exception e){
                        }
                    }
                }.start();
            }

            @Override
            public void onRightRowClick() {
                Toast.makeText(Month.this, "Next Month", Toast.LENGTH_SHORT).show();
                cal.monthChange(1);
                new Thread(){
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(1000);
                            Month.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cal.setRenwu(list);
                                }
                            });
                        }catch (Exception e){
                        }
                    }
                }.start();
            }

            @Override
            public void onTitleClick(String monthStr, Date month) {
                Toast.makeText(Month.this, "click title："+monthStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onWeekClick(int weekIndex, String weekStr) {
                Toast.makeText(Month.this, "click week："+weekStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDayClick(int day, String dayStr, DayFinish finish) {
                Toast.makeText(Month.this, "click date："+dayStr, Toast.LENGTH_SHORT).show();
                Log.w("", "click date:"+dayStr);
                lead(dayStr);
                Intent intent = new Intent(Month.this, day_new.class);
                startActivity(intent);
            }
        });

        Button button1 = (Button)findViewById(R.id.buttonchange);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Month.this, Schedule.class);
                startActivity(intent);
            }
        });

    }

    public void lead(String dayStr)
    {
        day_new d=new day_new();
        AddEvent a=new AddEvent();
        a.str=dayStr;
        d.st=dayStr;
    }

    class DayFinish{
        int day;
        int all;
        int finish;
        public DayFinish(int day, int all,int finish) {
            this.day = day;
            this.all = all;
            this.finish = finish;
        }
    }
}

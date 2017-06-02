package com.example.francisfeng.plus1second;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.francisfeng.plus1second_test.R;

import java.util.ArrayList;


/**
 * Created by francisfeng on 02/06/2017.
 */

public class AddEvent extends AppCompatActivity {

    private LinearLayout container;
    private ArrayList<LinearLayout> list;
    private EventService eventService;
    public String str;
    private static final String[] hour = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11","12","13","14",
            "15","16","17","18","19","20","21","22","23","24"};
    private static final String[] minu = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11","12","13","14",
            "15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34",
            "35","36","37","38","39","40", "41","42","43","44","45","46","47","48","49","50","51","52","53","54",
            "55","56","57","58","59","60"};
    private static final String[] hour1 = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11","12","13","14",
            "15","16","17","18","19","20","21","22","23","24"};
    private static final String[] minu1 = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11","12","13","14",
            "15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34",
            "35","36","37","38","39","40", "41","42","43","44","45","46","47","48","49","50","51","52","53","54",
            "55","56","57","58","59","60"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        eventService = new EventService(getBaseContext());
        Button add_detail_btn = (Button) findViewById(R.id.add_detail_btn);
        container = (LinearLayout) findViewById(R.id.container);
        list = new ArrayList<>();
        add_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout temp = createLayout();
                container.addView(temp);
            }
        });


        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        Button del_btn = (Button) findViewById(R.id.del_last_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //读取所有课程细节信息,存入数据库
                if (list.size() == 0 || list == null) {
//                    Toast.makeText(AddClass.this, "没输入课程信息呢!", Toast.LENGTH_LONG).show();
                } else {
                    //得到课程信息,储存到数据库
                saveEventInfo();
                AddEvent.this.onBackPressed();
                }
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() == 0 || list == null) {
//                    Toast.makeText(AddClass.this, "没啥可删的了!", Toast.LENGTH_LONG).show();
                } else {
                    LinearLayout temp = list.get(list.size() - 1);
                    list.remove(list.size() - 1);
                    container.removeView(temp);
                }
            }
        });
    }


    private LinearLayout createLayout() {

        LinearLayout res = new LinearLayout(getBaseContext());
        LinearLayout up = new LinearLayout(getBaseContext());
        LinearLayout down = new LinearLayout(getBaseContext());

        up.setOrientation(LinearLayout.HORIZONTAL);
        down.setOrientation(LinearLayout.HORIZONTAL);
        res.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 20, 0, 0);
        res.setLayoutParams(params);
        res.setGravity(Gravity.TOP| Gravity.CENTER);

        ArrayAdapter<String> adapter_start_hour = new ArrayAdapter<>(this, R.layout.spinner_item, hour);
        adapter_start_hour.setDropDownViewResource(R.layout.dropdown);
        ArrayAdapter<String> adapter_start_minu = new ArrayAdapter<>(this, R.layout.spinner_item, minu);
        adapter_start_minu.setDropDownViewResource(R.layout.dropdown);
        ArrayAdapter<String> adapter_end_hour = new ArrayAdapter<>(this, R.layout.spinner_item, hour1);
        adapter_end_hour.setDropDownViewResource(R.layout.dropdown);
        ArrayAdapter<String> adapter_end_minu = new ArrayAdapter<>(this, R.layout.spinner_item, minu1);
        adapter_start_minu.setDropDownViewResource(R.layout.dropdown);
//        ArrayAdapter<String> adapter_start = new ArrayAdapter<>(this, R.layout.spinner_item, start);
//        adapter_start.setDropDownViewResource(R.layout.dropdown);

        Spinner spinner_start_hour = createSpinner();
        spinner_start_hour.setAdapter(adapter_start_hour);
        Spinner spinner_start_minu = createSpinner();
        spinner_start_minu.setAdapter(adapter_start_minu);
        Spinner spinner_end_hour = createSpinner();
        spinner_end_hour.setAdapter(adapter_end_hour);
        Spinner spinner_end_minu = createSpinner();
        spinner_end_minu.setAdapter(adapter_end_minu);

//        EditText editText = new EditText(getBaseContext());
//        editText.setBackgroundResource(R.drawable.edit_gery);
//        editText.setAlpha(0.75f);
//        editText.setTextColor(Color.rgb(0,0,0));
//        editText.setTextSize(15f);
//
//        EditText editText2 = new EditText(getBaseContext());
//        editText2.setBackgroundResource(R.drawable.edit_gery);
//        editText2.setAlpha(0.75f);
//        editText2.setTextColor(Color.rgb(0,0,0));
//        editText2.setTextSize(15f);

        LinearLayout.LayoutParams layoutParamsWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParamsMatch = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsMatch.setMargins(0,0,0,0);

        TextView textView1 = createText("From");
        TextView textView2 = createText(":");
        TextView textView3 = createText("To");
        TextView textView4 = createText(":");

        up.addView(textView1, layoutParamsWrap);
        up.addView(spinner_end_hour, layoutParamsWrap);
        up.addView(textView2, layoutParamsWrap);
        up.addView(spinner_start_minu, layoutParamsWrap);

        up.addView(textView3, layoutParamsWrap);
        up.addView(spinner_start_hour, layoutParamsWrap);
        up.addView(textView4,layoutParamsWrap);
        up.addView(spinner_end_minu, layoutParamsWrap);

        res.addView(up, layoutParamsWrap);
        res.addView(down, layoutParamsWrap);

        list.add(res);

        return res;
    }

    private Spinner createSpinner() {
        Spinner spinner = new Spinner(getBaseContext());
        spinner.setBackgroundResource(R.drawable.edit_gery);
        spinner.setAlpha(0.75f);
        return spinner;
    }

    private TextView createText(String string) {
        TextView textView = new TextView(getBaseContext());
        textView.setText(string);
        textView.setTextColor(Color.rgb(255,255,255));
        textView.setShadowLayer(2,5,5, Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private void saveEventInfo() {
//        Class c = new Class();
        Event e = new Event();

        EditText editText = (EditText) findViewById(R.id.event_name);
        String event_name = editText.getText().toString();

        e.setdate(str);
        e.setthings(event_name);



        for (LinearLayout curLayout : list) {
            LinearLayout up = (LinearLayout)curLayout.getChildAt(0);
            Spinner spinner = (Spinner)up.getChildAt(1);//取得hour
            int start_hour = spinner.getSelectedItemPosition();
            spinner = (Spinner)up.getChildAt(2);//取得开始
            int start_minu = spinner.getSelectedItemPosition();
            spinner = (Spinner)up.getChildAt(4);
            int end_hour = spinner.getSelectedItemPosition();
            spinner = (Spinner)up.getChildAt(4);
            int end_minu = spinner.getSelectedItemPosition();

//            LinearLayout down = (LinearLayout)curLayout.getChildAt(1);
//            EditText et = (EditText)down.getChildAt(1);
//            String classroom = et.getText().toString();
            //Toast.makeText(AddClass.this, String.valueOf(week), Toast.LENGTH_LONG).show();
            e.setStart_hour(start_hour);
            e.setStart_minu(start_minu);
            e.setEnd_hour(end_hour);
            e.setStart_minu(end_minu);
            //储存进数据库
            eventService.save(e);
        }
    }
}

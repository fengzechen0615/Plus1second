package com.example.francisfeng.plus1second;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francisfeng.plus1second_test.R;

import java.util.ArrayList;

/**
 * Created by francisfeng on 31/05/2017.
 */

public class day_new extends AppCompatActivity{

    private static final int ADD_CLASS_CODE = 1;
    private static final int SHOW_CLASS_CODE = 2;
    private RelativeLayout[] day = new RelativeLayout[1];
    private int per_height;
    private int height;
    private boolean hasMeasured = false;
    private int color_flag = 1;
    private EventService eventService;
    public static String st;
    private int count=0;
//    private RelativeLayout relativeLayout = new RelativeLayout(getBaseContext());

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addeventmenu, menu);
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.day_event);

        //st = eventService.findByTime();
        final LinearLayout event_layout = (LinearLayout)findViewById(R.id.event_layout);
        eventService = new EventService(getBaseContext());

        day[0] = (RelativeLayout) findViewById(R.id.event);

        ViewTreeObserver vto = event_layout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!hasMeasured) {
                    height = day[0].getMeasuredHeight();
                    per_height = height/24;
                    hasMeasured = true;
                    drawEvent();
                }
                return true;
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit) {
            finish();
            return true;
        } else if (id == R.id.add) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), AddEvent.class);
            startActivityForResult(intent, ADD_CLASS_CODE);

        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CLASS_CODE) {
            if (resultCode == RESULT_CANCELED) {
                drawEvent();
            }
        } else if (requestCode == SHOW_CLASS_CODE) {
            if (resultCode == RESULT_CANCELED) {
                drawEvent();
            }
        }
    }

    private ArrayList<Event> list;
    private void drawEvent() {
        for (int i = 0; i < 1; i++) {
            day[i].removeAllViews();
        }
        list=eventService.findAll(st);
        if(!list.isEmpty())
        {
            for (Event cur: list) {
                count++;
                RelativeLayout class_layout = createEventLayout(cur.getstarthour(), cur.getendhour()-cur.getstarthour());
                TextView class_text = createEventInfo(cur.getthings()+"\n ");
                class_text.setGravity(Gravity.CENTER | Gravity.CENTER);
                ShowEventActivity sea=new ShowEventActivity();
                Button id_btn = createIdBtn(cur.id);
                Toast.makeText(day_new.this, "click week："+cur.id, Toast.LENGTH_SHORT).show();
                sea.id=cur.id;
                class_layout.addView(id_btn);
                class_layout.addView(class_text);
                day[0].addView(class_layout);
            }
        }
    }



    private RelativeLayout createEventLayout(int start, int length) {
        RelativeLayout layout = new RelativeLayout(getBaseContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, length * per_height-2);
        if (color_flag == 1) {
            layout.setBackgroundResource(R.drawable.layout_pink);
        } else if (color_flag == 2){
            layout.setBackgroundResource(R.drawable.layout_lightpurple);
        } else if (color_flag == 3) {
            layout.setBackgroundResource(R.drawable.layout_lightblue);
        } else if (color_flag == 4){
            layout.setBackgroundResource(R.drawable.layout_lightgreen);
        } else {
            layout.setBackgroundResource(R.drawable.layout_lightorange);
        }
        color_flag++;
        color_flag %= 5;

        params.setMargins(2, start*per_height+2, 2, 2);
        layout.setLayoutParams(params);
        return layout;
    }

    private Button createIdBtn(final String id) {
        Button btn = new Button(getBaseContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btn.setText(String.valueOf(id));
        btn.setAlpha(0);
        btn.setLayoutParams(params);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.setClass(getBaseContext(), ShowEventActivity.class);
                list=eventService.findById(id);
                startActivityForResult(intent, SHOW_CLASS_CODE);
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
            }
        });
        return btn;
    }

    private TextView createEventInfo(String string) {
        TextView textView = new TextView(getBaseContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setText(string);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(params);
        textView.setTextSize(12);
        return textView;
    }


}

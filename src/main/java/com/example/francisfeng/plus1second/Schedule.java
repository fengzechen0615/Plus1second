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

import com.example.francisfeng.plus1second_test.R;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

    private static final int ADD_CLASS_CODE = 1;
    private static final int SHOW_CLASS_CODE = 2;
    private RelativeLayout[] week = new RelativeLayout[7];
    private int per_height;
    private int height;
    private boolean hasMeasured = false;
    private int color_flag = 1;
    private ClassService classService;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        final LinearLayout main_layout = (LinearLayout)findViewById(R.id.main_layout);
        classService = new ClassService(getBaseContext());

        week[0] = (RelativeLayout) findViewById(R.id.mon);
        week[1] = (RelativeLayout) findViewById(R.id.tue);
        week[2] = (RelativeLayout) findViewById(R.id.wed);
        week[3] = (RelativeLayout) findViewById(R.id.thu);
        week[4] = (RelativeLayout) findViewById(R.id.fri);
        week[5] = (RelativeLayout) findViewById(R.id.sat);
        week[6] = (RelativeLayout) findViewById(R.id.sun);

        ViewTreeObserver vto = main_layout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!hasMeasured) {
                    height = week[0].getMeasuredHeight();
                    per_height = height/12;
                    hasMeasured = true;
                    drawClass();
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
//            Intent intent = new Intent(Schedule.this, AddClass.class);
//            startActivity(intent);
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), AddClass.class);
            startActivityForResult(intent, ADD_CLASS_CODE);

        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CLASS_CODE) {
            if (resultCode == RESULT_CANCELED) {
                drawClass();
            }
        } else if (requestCode == SHOW_CLASS_CODE) {
            if (resultCode == RESULT_CANCELED) {
                drawClass();
            }
        }
    }

    private void drawClass() {
        for (int i = 0; i < 7; i++) {
            week[i].removeAllViews();
        }
        ArrayList<Class> classList = classService.findAll();
        if (classList != null) {
            for (Class cur: classList) {
                RelativeLayout class_layout = createClassLayout(cur.getStart(), cur.getLength());
                TextView class_text = createClassInfo(cur.getClass_name()+"\n /@ "+cur.getClassroom());
                class_text.setGravity(Gravity.TOP | Gravity.LEFT);
                Button id_btn = createIdBtn(cur.id);
                class_layout.addView(id_btn);
                class_layout.addView(class_text);
                week[cur.getWeek()].addView(class_layout);
            }
        }
    }

    private RelativeLayout createClassLayout(int start, int length) {
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

    private Button createIdBtn(final int id) {
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
                intent.setClass(getBaseContext(), ShowClassActivity.class);
                startActivityForResult(intent, SHOW_CLASS_CODE);
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
            }
        });
        return btn;
    }

    private TextView createClassInfo(String string) {
        TextView textView = new TextView(getBaseContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setText(string);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(params);
        textView.setTextSize(12);
        return textView;
    }


}

package com.example.francisfeng.plus1second;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.francisfeng.plus1second_test.R;

/**
 * Created by stiles on 16/4/15.
 */
public class ShowClassActivity extends Activity {
    private static final String[] weeks = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private int id;
    private ClassService classService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_class);
        classService = new ClassService(getBaseContext());
        Button del_btn = (Button) findViewById(R.id.del_btn);
        Intent intent = getIntent();
        id = intent.getIntExtra("id" , -1);
        TextView show_class_name = (TextView) findViewById(R.id.show_class_name);
        TextView show_teacher_name = (TextView) findViewById(R.id.show_teacher_name);
        TextView show_place = (TextView) findViewById(R.id.show_place);
        TextView show_time = (TextView) findViewById(R.id.show_time);
        Class c = classService.findById(id);
        show_class_name.setText("Course Name: " + c.getClass_name());
        show_teacher_name.setText("Teacher: " + c.getTeacher_name());
        show_place.setText("Classroom: " + c.getClassroom());
        String time = "Course Time:\n\n";
        time += weeks[c.getWeek()]+" "+"No.";
        int cur = c.getStart()+1;
        for (int i = 0; i < c.getLength(); i++, cur++) {
            time += " " + cur;
        }
        time += "Lesson";
        show_time.setText(time);

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classService.deleteById(id);
                ShowClassActivity.this.onBackPressed();
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });
    }


}

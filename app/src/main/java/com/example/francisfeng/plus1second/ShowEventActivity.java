package com.example.francisfeng.plus1second;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francisfeng.plus1second_test.R;

import java.util.ArrayList;

/**
 * Created by francisfeng on 31/05/2017.
 */

public class ShowEventActivity extends Activity{
    public static String id;
    private EventService eventService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event);
        eventService = new EventService(getBaseContext());
        Button del_btn = (Button) findViewById(R.id.e_del_btn);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        TextView show_event = (TextView) findViewById(R.id.e_show_event);
        TextView show_time = (TextView) findViewById(R.id.e_show_time);
        ArrayList<Event> e = eventService.findById(id);

        show_event.setText("Event: \n" + e.get(0).getthings());
        String time = "Event Time:\n\n";
        int starthour = e.get(0).getstarthour();
        int startminu = e.get(0).getstartminu();
        int endhour = e.get(0).getendhour();
        int endminu = e.get(0).getendminu();
        time += starthour + ":" +startminu;
        time += " - ";
        time += endhour + ":" +endminu;
        show_time.setText(time);

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventService.deleteById(id);
                ShowEventActivity.this.onBackPressed();
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });

    }
}

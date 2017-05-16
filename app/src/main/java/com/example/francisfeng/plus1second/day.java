package com.example.francisfeng.plus1second;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.francisfeng.plus1second_test.R;

public class day extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button button1 = (Button)findViewById(R.id.back);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void add_click(View v) {
        addRow();
    }

    public void addRow() {
        TableLayout table = (TableLayout) findViewById(R.id.table2);
        table.setStretchAllColumns(true);
        TableRow row = new TableRow(day.this);
        row.setBackgroundColor(Color.WHITE);

        for (int i = 0; i < 3; i++) {
            EditText textview = new EditText(day.this);
            textview.setText("");
            row.addView(textview);
        }
        table.addView(row, new TableLayout.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
    }


    public void addRow1() {
        TableLayout table = (TableLayout) findViewById(R.id.table2);
        table.setStretchAllColumns(true);
        TableRow row = new TableRow(day.this);
        row.setBackgroundColor(Color.WHITE);

        for (int i = 0; i < 3; i++) {
            EditText textview = new EditText(day.this);
            textview.setText("");
            row.addView(textview);
        }
        table.addView(row, new TableLayout.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
    }

    public void addevent(View view) {
        addRow1();
    }

}

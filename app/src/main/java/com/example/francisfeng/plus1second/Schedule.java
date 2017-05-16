package com.example.francisfeng.plus1second;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.francisfeng.plus1second_test.R;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
}

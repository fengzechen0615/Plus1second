package com.example.francisfeng.plus1second;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.francisfeng.plus1second_test.R;

/**
 * Created by francisfeng on 15/05/2017.
 */

public class titlelayout extends LinearLayout {
    public titlelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button buttonback = (Button)findViewById(R.id.back);
        Button buttonedit = (Button)findViewById(R.id.edit);

        buttonback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

        buttonedit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You clicked Edit button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

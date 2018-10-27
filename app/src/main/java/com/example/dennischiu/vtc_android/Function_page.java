package com.example.dennischiu.vtc_android;

import com.example.dennischiu.vtc_android.Finger.Finger;
import com.example.dennischiu.vtc_android.TimePicker.MainTimePicker;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Function_page extends AppCompatActivity {

    ConstraintLayout mConstraintLayout1;
    ConstraintLayout mConstraintLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_page);

        mConstraintLayout1 = (ConstraintLayout) findViewById(R.id.function_1);
        mConstraintLayout2 = (ConstraintLayout) findViewById(R.id.function_2);

        mConstraintLayout1.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setClass(this, MainTimePicker.class);
            startActivity(intent);
        });
        mConstraintLayout2.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setClass(this, Finger.class);
            startActivity(intent);
        });
    }
}

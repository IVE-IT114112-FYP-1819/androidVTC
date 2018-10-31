package com.example.dennischiu.vtc_androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Second_page extends AppCompatActivity {

    TextView mTextView_Skip;
    TextView mTextView_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        mTextView_Skip = findViewById(R.id.tv_skip);
        mTextView_Next = findViewById(R.id.tv_next);


        mTextView_Skip.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setClass(Second_page.this,Information_setting.class);
            startActivity(intent);
        });

        mTextView_Next.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setClass(Second_page.this,Third_page.class);
            startActivity(intent);
        });
    }
}

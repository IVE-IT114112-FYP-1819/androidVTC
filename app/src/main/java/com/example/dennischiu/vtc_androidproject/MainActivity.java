package com.example.dennischiu.vtc_androidproject;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    private static final int GOTO_MAIN_ACTIVITY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 4000);




    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Information_setting.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }

        }
    };

}

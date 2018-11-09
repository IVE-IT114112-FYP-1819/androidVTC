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

    private LocationManager mLocationManager;
    private static final int GOTO_MAIN_ACTIVITY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 4000);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        requestPermission();

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

    private void requestPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS};
        ActivityCompat.requestPermissions(this, permissions, 1);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Prompt")
                   .setMessage("Please open location")
                   .setPositiveButton("GO to setting", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                           startActivityForResult(intent, 0);
                       }
                   })
                   .show();
        } else {
            Log.d("Test", "Location is opening");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestPermission();
    }
}

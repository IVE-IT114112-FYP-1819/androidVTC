package com.example.dennischiu.vtc_androidproject;

import com.example.dennischiu.vtc_androidproject.Finger.Function_page;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mTextViewState;
    private ImageView mRemarkImageView;

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View bottomSheet = findViewById(R.id.bottom_sheet);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mTextViewState = findViewById(R.id.text_view_state);

        Button buttonToFunction = findViewById(R.id.btn_function_button);
        Button buttonExpand = findViewById(R.id.btn_information);
        Button buttonClose = findViewById(R.id.btn_close);

        mRemarkImageView = (ImageView) findViewById(R.id.item_remarks);

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        requestPermission();

        buttonToFunction.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Function_page.class);
            startActivity(intent);
        });

        buttonClose.setOnClickListener(v -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });

        buttonExpand.setOnClickListener(v -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        mRemarkImageView.setOnClickListener(v -> {
            mRemarkImageView.setImageResource(R.mipmap.details_click);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mTextViewState.setText("Collapsed");

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        mTextViewState.setText("Dragging...");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mTextViewState.setText("Expanded");
                        mRemarkImageView.setImageResource(R.mipmap.details_click);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        mTextViewState.setText("Hidden");
                        mRemarkImageView.setImageResource(R.mipmap.info);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        mTextViewState.setText("Setting...");
                        mRemarkImageView.setImageResource(R.mipmap.info);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

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

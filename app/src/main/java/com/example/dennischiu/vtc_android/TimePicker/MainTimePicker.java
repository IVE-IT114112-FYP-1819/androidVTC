package com.example.dennischiu.vtc_android.TimePicker;


import com.example.dennischiu.vtc_android.R;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class MainTimePicker extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        mTextView = (TextView) findViewById(R.id.textView);

        Button buttonTimePicker = findViewById(R.id.btn_time_picker);
        buttonTimePicker.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");

        });
        Button buttonCancelAlarm = findViewById(R.id.btn_cancel);
        buttonCancelAlarm.setOnClickListener(v -> {
            cancelAlarm();

        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);

    }

    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for :  ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        mTextView.setText(timeText);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {

        Intent intent = new Intent(MainTimePicker.this, AlertReceiver.class);
        intent.setAction("repeating");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainTimePicker.this, 1, intent, 0);
        //        long firstime = SystemClock.elapsedRealtime();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,firstime, c.getTimeInMillis() , pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainTimePicker.this, AlertReceiver.class);
        intent.setAction("repeating");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainTimePicker.this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm canceled");


    }
}

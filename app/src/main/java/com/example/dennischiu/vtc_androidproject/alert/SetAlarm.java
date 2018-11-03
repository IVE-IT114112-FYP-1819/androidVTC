package com.example.dennischiu.vtc_androidproject.alert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.dennischiu.vtc_androidproject.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static android.support.constraint.Constraints.TAG;

public class SetAlarm extends AppCompatActivity {

    Button setTime_button;
    NumberPicker hour_numberPicker, mins_numberPicker;
    int hourSet, minSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert);

        setTime_button = (Button) findViewById(R.id.setTime_button);
        hour_numberPicker = (NumberPicker) findViewById(R.id.hour_numberPicker);
        mins_numberPicker = (NumberPicker) findViewById(R.id.mins_numberPicker);

        hour_numberPicker.setMinValue(0);
        hour_numberPicker.setMaxValue(24);
        hour_numberPicker.setValue(0);

        mins_numberPicker.setMinValue(0);
        mins_numberPicker.setMaxValue(60);
        mins_numberPicker.setValue(30);

        setTime_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourSet = hour_numberPicker.getValue();
                minSet = mins_numberPicker.getValue();

                Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00")); //取得時間
                cal.add(Calendar.HOUR, hourSet);
                cal.add(Calendar.MINUTE, minSet);
                cal.set(Calendar.SECOND, 0);
                add_alarm(SetAlarm.this, cal); //註冊鬧鐘
                finish();
            }
        });

        ComponentName receiver = new ComponentName(this, BootUpReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    public static void add_alarm(Context context, Calendar cal) {
        Log.d(TAG, "alarm add time: " + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + " " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));

        Intent intent = new Intent(context, AlarmReceiver.class);
        // 以日期字串組出不同的 category 以添加多個鬧鐘
        intent.addCategory("ID." + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + "-" + String.valueOf((cal.get(Calendar.HOUR_OF_DAY) )) + "." + String.valueOf(cal.get(Calendar.MINUTE)) + "." + String.valueOf(cal.get(Calendar.SECOND)));
        String AlarmTimeTag = "Alarmtime " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(cal.get(Calendar.MINUTE)) + ":" + String.valueOf(cal.get(Calendar.SECOND));

        intent.putExtra("title", "activity_app");
        intent.putExtra("time", AlarmTimeTag);

        PendingIntent pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);       //註冊鬧鐘
    }

    /***    取消(與系統註冊的)鬧鐘    ***/
    private static void cancel_alarm(Context context, Calendar cal) {
        Log.d(TAG, "alarm cancel time: " + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + " " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));

        Intent intent = new Intent(context, AlarmReceiver.class);
        // 以日期字串組出不同的 category 以添加多個鬧鐘
        intent.addCategory("ID." + String.valueOf(cal.get(Calendar.MONTH)) + "." + String.valueOf(cal.get(Calendar.DATE)) + "-" + String.valueOf((cal.get(Calendar.HOUR_OF_DAY) )) + "." + String.valueOf(cal.get(Calendar.MINUTE)) + "." + String.valueOf(cal.get(Calendar.SECOND)));
        String AlarmTimeTag = "Alarmtime " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(cal.get(Calendar.MINUTE)) + ":" + String.valueOf(cal.get(Calendar.SECOND));

        intent.putExtra("title", "activity_app");
        intent.putExtra("time", AlarmTimeTag);

        PendingIntent pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pi);    //取消鬧鐘，只差在這裡
    }
}

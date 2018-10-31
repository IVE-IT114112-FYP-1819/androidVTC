package com.example.dennischiu.vtc_androidproject.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        /* 同一個接收者可以收多個不同行為的廣播，所以可以判斷收進來的行為為何，再做不同的動作 */
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            /* 收到廣播後要做的事 */

            //建立通知發布鬧鐘
            Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00")); //取得時間
            for(int i = 0; i < 10; i++){
                cal.add(Calendar.MINUTE, 1);    //加一分鐘
                cal.set(Calendar.SECOND, 0);    //設定秒數為0
                SetAlarm.add_alarm(context, cal);        //註冊鬧鐘
            }
        }
    }
}
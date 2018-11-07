package com.example.dennischiu.vtc_androidproject.alert;

import com.example.dennischiu.vtc_androidproject.Finger.Function_page;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getMessage1Notification();
        notificationHelper.getManager().notify(1, nb.build());

        Bundle bData = intent.getExtras();
        if(bData.get("title").equals("activity_app"))
        {
            Intent alarm2cal = new Intent(context, Function_page.class);
            context.startActivity(alarm2cal);
        }
    }




}

package com.example.dennischiu.vtc_androidproject.alert;

import com.example.dennischiu.vtc_androidproject.Finger.Function_page;
import com.example.dennischiu.vtc_androidproject.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {


    MediaPlayer musicPlay;

    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1000;
    private static final int LOCATION_UPDATE_MIN_TIME = 50;
    private LocationManager mLocationManager;
    private String messages;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getMessage1Notification();
        notificationHelper.getManager().notify(1, nb.build());

        Bundle bData = intent.getExtras();
        if (bData.get("title").equals("activity_app")) {

//            musicPlay = MediaPlayer.create(context, R.raw.entire);
//            musicPlay.start();
//
//            Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
//            v.vibrate(60000);

            Intent alarm2cal = new Intent(context, Function_page.class);
            context.startActivity(alarm2cal);


        }
    }
}

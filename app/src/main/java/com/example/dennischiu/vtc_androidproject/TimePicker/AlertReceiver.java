package com.example.dennischiu.vtc_androidproject.TimePicker;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification();
        notificationHelper.getManager().notify(1, nb.build());

        if (intent.getAction().equals("repeating")) {
            Toast.makeText(context, "repeating", Toast.LENGTH_LONG).show();
            Vibrator v =(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
            v.vibrate(10000);
        } else {
            Toast.makeText(context, "alarm", Toast.LENGTH_LONG).show();
        }
    }

//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        Ringtone r = RingtoneManager.getRingtone(context, notification);
//        r.play();
//        r.stop();
}
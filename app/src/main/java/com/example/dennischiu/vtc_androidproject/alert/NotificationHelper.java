package com.example.dennischiu.vtc_androidproject.alert;


import com.example.dennischiu.vtc_androidproject.R;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String messageID = "messageID";
    public static final String messageName = "message 1";


    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createMessages();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createMessages() {
        NotificationChannel message =
                new NotificationChannel(messageID, messageName, NotificationManager.IMPORTANCE_DEFAULT);
        message.enableLights(true);
        message.enableVibration(true);
        message.setLightColor(R.color.flower_blue);
        message.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(message);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return mManager;
    }

    public NotificationCompat.Builder getMessage1Notification() {
        return new NotificationCompat.Builder(getApplicationContext(), messageID).setContentTitle("Your alarm clock is Ringing!!")
                                                                                  .setContentText("please enter the app")
                                                                                  .setSmallIcon(R.mipmap.warning);
    }



}

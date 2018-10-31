package com.example.dennischiu.vtc_androidproject.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bData = intent.getExtras();
        if(bData.get("title").equals("activity_app"))
        {
            Intent alarm2cal = new Intent(context, CalActivity.class);
            context.startActivity(alarm2cal);
        }
    }




}

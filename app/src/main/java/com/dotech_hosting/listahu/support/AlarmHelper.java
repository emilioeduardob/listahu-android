package com.dotech_hosting.listahu.support;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.dotech_hosting.listahu.services.UpdateService;

/**
 * Created by emilio on 10/30/15.
 */
public class AlarmHelper {
    public static void setUpdatesAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(context, UpdateService.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, serviceIntent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0,
                AlarmManager.INTERVAL_HALF_DAY, alarmIntent);
    }
}

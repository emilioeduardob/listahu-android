package com.dotech_hosting.listahu.broadcast_receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dotech_hosting.listahu.services.UpdateService;

/**
 * Created by emilio on 11/2/15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    public static boolean isAlarmSetUp(Context context) {
        return getPendingIntent(context, PendingIntent.FLAG_NO_CREATE) != null;
    }

    private static PendingIntent getPendingIntent(Context context, int flags) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, flags);
    }

    public static void setUpAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                AlarmManager.INTERVAL_HALF_DAY,
                AlarmManager.INTERVAL_HALF_DAY,
                getPendingIntent(context, 0));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarm received!");
        Intent i = new Intent(context, UpdateService.class);
        context.startService(i);
    }
}

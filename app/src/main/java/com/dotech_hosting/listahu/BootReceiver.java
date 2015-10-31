package com.dotech_hosting.listahu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.dotech_hosting.listahu.services.CallDetectService;
import com.dotech_hosting.listahu.services.UpdateService;
import com.dotech_hosting.listahu.support.AlarmHelper;

/**
 * Created by emilio on 10/29/15.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, CallDetectService.class);
        context.startService(i);

        AlarmHelper.setUpdatesAlarm(context);
    }

}

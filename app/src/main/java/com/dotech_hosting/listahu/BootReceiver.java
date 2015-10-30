package com.dotech_hosting.listahu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dotech_hosting.listahu.services.CallDetectService;

/**
 * Created by emilio on 10/29/15.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, CallDetectService.class);
        context.startService(i);
    }
}

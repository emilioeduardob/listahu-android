package com.dotech_hosting.listahu.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.dotech_hosting.listahu.support.SyncManager;

/**
 * Created by emilio on 10/30/15.
 */
public class UpdateService extends IntentService {

    private static final String TAG = UpdateService.class.getSimpleName();

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Syncing data");
        new SyncManager(getApplicationContext()).sync();
    }
}

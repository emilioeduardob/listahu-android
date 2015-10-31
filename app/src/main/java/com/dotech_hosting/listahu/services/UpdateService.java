package com.dotech_hosting.listahu.services;

import android.app.IntentService;
import android.content.Intent;

import com.dotech_hosting.listahu.support.SyncManager;

/**
 * Created by emilio on 10/30/15.
 */
public class UpdateService extends IntentService {
    public UpdateService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        new SyncManager(getApplicationContext()).sync();
    }
}

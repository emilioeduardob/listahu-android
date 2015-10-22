package com.dotech_hosting.listahu;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

/**
 * Created by emilio on 10/22/15.
 */
public class MainApp extends Application {
    /**
     * Tag used on log messages.
     */
    static final String TAG = "MainApp";
    private static Bus mBus;
    private static Context mContext;
    private static RealmManager realmManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        realmManager = new RealmManager(getApplicationContext());
        realmManager.setupRealm();
    }



    public static Bus getBus() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getContext() {
        return mContext;
    }

    public static RealmManager getRealmManager() {
        return realmManager;
    }

}

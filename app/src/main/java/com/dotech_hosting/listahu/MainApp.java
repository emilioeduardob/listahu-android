package com.dotech_hosting.listahu;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;
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
        RealmManager.setupRealm(getApplicationContext());

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
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

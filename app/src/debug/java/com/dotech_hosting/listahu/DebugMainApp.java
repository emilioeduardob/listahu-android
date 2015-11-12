package com.dotech_hosting.listahu;

import com.dotech_hosting.listahu.MainApp;
import com.facebook.stetho.Stetho;

/**
 * Created by emilio on 11/11/15.
 */
public class DebugMainApp extends MainApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

package com.dotech_hosting.listahu.services;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by emilio on 10/22/15.
 */
public class CallHelper {
    private static final String TAG = CallHelper.class.getSimpleName();
    private final TelephonyManager tm;
    private Context ctx;
    private CallStateListener callStateListener = new CallStateListener();

    public CallHelper(Context context) {
        ctx = context;
        tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public void start() {
        tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public void stop() {
        tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
    }
}

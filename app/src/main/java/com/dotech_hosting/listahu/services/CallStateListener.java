package com.dotech_hosting.listahu.services;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.dotech_hosting.listahu.support.AppHelpers;

/**
 * Created by emilio on 10/22/15.
 */
public class CallStateListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                AppHelpers.showToast("Incoming message from " + incomingNumber);
                break;
        }
    }

}

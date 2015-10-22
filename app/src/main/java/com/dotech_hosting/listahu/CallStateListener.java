package com.dotech_hosting.listahu;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by emilio on 10/22/15.
 */
public class CallStateListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                // called when someone is ringing to this phone

                Toast.makeText(MainApp.getContext(),
                        "Incoming: " + incomingNumber,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}

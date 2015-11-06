package com.dotech_hosting.listahu.services;

import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dotech_hosting.listahu.RealmManager;
import com.dotech_hosting.listahu.models.Denuncia;
import com.dotech_hosting.listahu.support.AppHelpers;

import io.realm.Realm;

/**
 * Created by emilio on 10/22/15.
 */
public class CallStateListener extends PhoneStateListener {
    private static final String TAG = CallStateListener.class.getSimpleName();

    @Override
    public void onCallStateChanged(int state, final String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkNumber(incomingNumber);
                    }
                }, 2500);

                break;
        }
    }

    private void checkNumber(String incoming_number) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            final Denuncia denuncia = new RealmManager(realm).isReported(incoming_number);

            if (denuncia != null) {
                new AppHelpers(denuncia).NotifyUser();
            }

        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
}

package com.dotech_hosting.listahu.services;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.dotech_hosting.listahu.RealmManager;
import com.dotech_hosting.listahu.models.Denuncia;
import com.dotech_hosting.listahu.support.AppHelpers;

import io.realm.Realm;

/**
 * Created by emilio on 10/22/15.
 */
public class CallStateListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                checkNumber(incomingNumber);
                break;
        }
    }

    private void checkNumber(String incoming_number) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            Denuncia denuncia = new RealmManager(realm).isReported(incoming_number);

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

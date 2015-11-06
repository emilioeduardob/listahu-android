package com.dotech_hosting.listahu.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.dotech_hosting.listahu.RealmManager;
import com.dotech_hosting.listahu.models.Denuncia;
import com.dotech_hosting.listahu.support.AppHelpers;

import io.realm.Realm;

/**
 * Created by emilio on 10/22/15.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String incoming_number;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        incoming_number = msgs[i].getOriginatingAddress();
                        checkNumber(incoming_number);
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
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

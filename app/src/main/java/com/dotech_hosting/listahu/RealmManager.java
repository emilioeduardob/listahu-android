package com.dotech_hosting.listahu;

import android.content.Context;
import android.util.Log;


import com.dotech_hosting.listahu.models.Denuncia;

import javax.crypto.spec.DESedeKeySpec;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by emilio on 7/28/15.
 */
public class RealmManager {

    private static final String TAG = RealmManager.class.getSimpleName();
    private final Realm mRealm;

    public RealmManager(Realm realm) {
        mRealm = realm;
    }

    public static void setupRealm(Context context) {
        // Setup
        RealmConfiguration realmConfig = getRealmConfiguration(context);

        Realm.setDefaultConfiguration(realmConfig);

        try {
            Realm realm = Realm.getDefaultInstance();
            realm.close();
        }
        catch (RealmMigrationNeededException exception) {
            Log.d(TAG, "New version! Destroy everything");
            Realm.deleteRealm(realmConfig);
        }

    }

    public static RealmConfiguration getRealmConfiguration(Context mContext) {
        return new RealmConfiguration.Builder(mContext)
                .name("listahu.realm")
                .schemaVersion(1)
                .build();
    }

    public static void clearRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.clear(Denuncia.class);
        realm.commitTransaction();
        realm.close();
    }


    public Denuncia isReported(String incomingNumber) {
        if (incomingNumber.matches("\\+.*")) {
            incomingNumber = incomingNumber.substring(1);
        }
        Denuncia result = mRealm.where(Denuncia.class)
                .equalTo("numero", incomingNumber)
                .findFirst();

        return result;
    }
}



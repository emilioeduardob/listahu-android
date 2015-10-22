package com.dotech_hosting.listahu;

import android.content.Context;
import android.util.Log;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by emilio on 7/28/15.
 */
public class RealmManager {

    private static final String TAG = RealmManager.class.getSimpleName();
    private final Context mContext;

    public RealmManager(Context context) {
        mContext = context;
    }

    public void setupRealm() {
        // Setup
        RealmConfiguration realmConfig = getRealmConfiguration();

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

    public RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration.Builder(mContext)
                .name("visor.realm")
                .schemaVersion(1)
                        //.migration(new MyMigration())
                .build();
    }

    public void clearRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
//        realm.clear(Promocion.class);
        realm.commitTransaction();
        realm.close();
    }

}



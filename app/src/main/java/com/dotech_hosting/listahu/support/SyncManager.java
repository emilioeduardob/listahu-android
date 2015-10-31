package com.dotech_hosting.listahu.support;

import android.content.Context;
import android.util.Log;

import com.dotech_hosting.listahu.APIBackend;
import com.dotech_hosting.listahu.ApiService;
import com.dotech_hosting.listahu.models.Denuncia;
import com.dotech_hosting.listahu.models.DenunciaWrapper;
import com.pixplicity.easyprefs.library.Prefs;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by emilio on 10/24/15.
 */
public class SyncManager {

    private static final String TAG = SyncManager.class.getSimpleName();
    private final Context mContext;

    public SyncManager(Context context) {
       mContext = context;
    }

    // Get denuncias from page 1
    public void sync() {
        getPage(1);
    }

    public void getPage(final int page) {
        Log.d(TAG, "Getting page " + page);
        ApiService api = APIBackend.getApiInstance();
        Call<DenunciaWrapper> call = api.getDenuncias(page, null);
        call.enqueue(new Callback<DenunciaWrapper>() {
            @Override
            public void onResponse(final Response<DenunciaWrapper> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            processResults(realm, response, page);
                        }
                    });
                    realm.close();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Couldn't sync data. ", t);
            }

        });
    }

    private void processResults(Realm realm, Response<DenunciaWrapper> response, int page) {
        DenunciaWrapper data = response.body();

        if (!data.results.isEmpty()) {

            Denuncia denuncia = realm.where(Denuncia.class)
                    .equalTo("id", data.results.get(0).getId())
                    .findFirst();

            if (denuncia == null) {
                realm.copyToRealmOrUpdate(data.results);
                Prefs.putInt("page", page);
                if (data.next != null) {
                    getPage(page + 1);
                }
            }
        }
    }
}

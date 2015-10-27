package com.dotech_hosting.listahu.services;

import android.content.Context;
import android.util.Log;

import com.dotech_hosting.listahu.APIBackend;
import com.dotech_hosting.listahu.ApiService;
import com.dotech_hosting.listahu.MainActivity;
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
public class SyncService {

    private static final String TAG = SyncService.class.getSimpleName();
    private final Context mContext;

    public SyncService(Context context) {
       mContext = context;
    }

    public void sync() {
        int page = Prefs.getInt("page", 1);
        getPage(page);
    }

    public void getPage(final int page) {
        Log.d(TAG, "Getting page " + page);
        ApiService api = APIBackend.getApiInstance();
        Call<DenunciaWrapper> call = api.getDenuncias(page);
        call.enqueue(new Callback<DenunciaWrapper>() {
            @Override
            public void onResponse(final Response<DenunciaWrapper> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            DenunciaWrapper data = response.body();
                            realm.copyToRealmOrUpdate(data.results);
                            Prefs.putInt("page", page);
                            if (data.next != null) {
                                getPage(page + 1);
                            }
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
}

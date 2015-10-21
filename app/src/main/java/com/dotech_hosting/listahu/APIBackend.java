package com.dotech_hosting.listahu;

import retrofit.RestAdapter;

/**
 * Created by emilio on 10/21/15.
 */


public class APIBackend {
    // CONSTANTS
    private static final String TAG = APIBackend.class.getSimpleName();

    public static ApiService getApiInstance() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.API_URL);

        RestAdapter restAdapter = builder.build();

        return restAdapter.create(ApiService.class);
    };


}

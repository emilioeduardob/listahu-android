package com.dotech_hosting.listahu;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by emilio on 11/11/15.
 */
public class RetrofitUtils {
    public static OkHttpClient getClient() {
        return new OkHttpClient();
    }
}

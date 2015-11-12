package com.dotech_hosting.listahu;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by emilio on 11/11/15.
 */
public class RetrofitUtils {
    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        return client;
    }
}

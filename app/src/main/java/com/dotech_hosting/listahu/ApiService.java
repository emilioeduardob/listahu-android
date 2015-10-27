package com.dotech_hosting.listahu;

import com.dotech_hosting.listahu.models.DenunciaWrapper;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by emilio on 10/21/15.
 */
public interface ApiService {
    @GET("/api/v1/denuncias")
    Call<DenunciaWrapper> getDenuncias(@Query("page") int page);
}

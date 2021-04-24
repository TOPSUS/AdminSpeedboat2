package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface KapalService {
    @GET(Endpoint.KAPAL_LIST)
    Call<Response> showKapal(@Header("Authorization") String token);

    @GET(Endpoint.KAPAL_VIEW)
    Call<Response> viewKapal(@Header("Authorization") String token, @Path(value = "kapal", encoded = true) int id);

    @DELETE(Endpoint.KAPAL_DELETE)
    Call<Response> deleteKapal(@Header("Authorization") String token, @Path(value = "kapal", encoded = true) int id);
}

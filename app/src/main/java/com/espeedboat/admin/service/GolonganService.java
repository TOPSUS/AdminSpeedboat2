package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GolonganService {
    @GET(Endpoint.GOLONGAN_BY_PELABUHAN)
    Call<Response> golonganPelabuhan(@Header("Authorization") String token, @Path(value = "id", encoded = true) String id);
}

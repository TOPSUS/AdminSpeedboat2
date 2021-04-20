package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PelabuhanService {
    @GET(Endpoint.LIST_NAMA_PELABUHAN)
    Call<Response> listNamaPelabuhan(@Header("Authorization") String token);
}

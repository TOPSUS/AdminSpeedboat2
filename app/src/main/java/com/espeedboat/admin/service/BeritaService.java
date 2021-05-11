package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BeritaService {
    @GET(Endpoint.BERITA_LIST)
    Call<Response> showBerita(@Header("Authorization") String token);
}

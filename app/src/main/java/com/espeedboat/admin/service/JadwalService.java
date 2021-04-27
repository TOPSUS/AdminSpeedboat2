package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface JadwalService {
    @GET(Endpoint.JADWAL_LIST)
    Call<Response> listJadwal(@Header("Authorization") String token);
}

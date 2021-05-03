package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JadwalService {
    @GET(Endpoint.JADWAL_LIST)
    Call<Response> listJadwal(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST(Endpoint.JADWAL_CREATE)
    Call<Response> createJadwal(@Header("Authorization") String token,
                              @Field("kapal") int id_kapal, @Field("asal") int id_asal,
                              @Field("tujuan") int id_tujuan, @Field("tanggal") String tanggal,
                              @Field("waktu") String waktu, @Field("estimasi") String estimasi,
                              @Field("harga") String harga);
}

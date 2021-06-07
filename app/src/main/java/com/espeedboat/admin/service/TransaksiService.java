package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TransaksiService {
    @GET(Endpoint.TRANSAKSI_LIST_PROSES)
    Call<Response> getTransaksiProses(@Header("Authorization") String token);

    @GET(Endpoint.TRANSAKSI_LIST_SELESAI)
    Call<Response> getTransaksiSelesai(@Header("Authorization") String token);

    @GET(Endpoint.TRANSAKSI_DETAIL)
    Call<Response> getTransaksiDetail(@Header("Authorization") String token,
                                      @Path(value = "id", encoded = true) Integer id);

    @GET(Endpoint.TRANSAKSI_APPROVE)
    Call<Response> approveTransaksi(@Header("Authorization") String token,
                                    @Path(value = "id", encoded = true) Integer id);

    @GET(Endpoint.TRANSAKSI_TIKET)
    Call<Response> showTiket(@Header("Authorization") String token,
                             @Path(value = "kode_tiket", encoded = true) String kode_tiket);

    @GET(Endpoint.TRANSAKSI_TIKET_APPROVE)
    Call<Response> approveTiket(@Header("Authorization") String token,
                                @Path(value = "id", encoded = true) Integer id);

    @GET(Endpoint.TRANSAKSI_IS_TIKET)
    Call<Response> isTiket(@Header("Authorization") String token,
                           @Path(value = "kode_tiket", encoded = true) String kode_tiket);
}

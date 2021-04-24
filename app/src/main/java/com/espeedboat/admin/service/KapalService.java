package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface KapalService {
    @GET(Endpoint.KAPAL_LIST)
    Call<Response> showKapal(@Header("Authorization") String token);

    @GET(Endpoint.KAPAL_VIEW)
    Call<Response> viewKapal(@Header("Authorization") String token, @Path(value = "kapal", encoded = true) int id);

    @DELETE(Endpoint.KAPAL_DELETE)
    Call<Response> deleteKapal(@Header("Authorization") String token, @Path(value = "kapal", encoded = true) int id);

//    @Multipart
    @FormUrlEncoded
    @POST(Endpoint.KAPAL_CREATE)
    Call<Response> createKapal(@Header("Authorization") String token,
                               @Field("nama") String nama,
                               @Field("kapasitas") int kapasitas,
                               @Field("deskripsi") String deskripsi,
                               @Field("contact") String contact,
                               @Field("tipe") String tipe,
                               @Field("golongan") String golongan,
                               @Field("tanggal_beroperasi") String tanggal_beroperasi);
}

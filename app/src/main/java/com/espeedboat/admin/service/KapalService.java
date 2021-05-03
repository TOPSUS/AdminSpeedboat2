package com.espeedboat.admin.service;

import androidx.annotation.Nullable;

import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KapalService {
    @GET(Endpoint.KAPAL_LIST)
    Call<Response> showKapal(@Header("Authorization") String token);

    @GET(Endpoint.KAPAL_VIEW)
    Call<Response> viewKapal(@Header("Authorization") String token, @Path(value = "kapal", encoded = true) int id);

    @GET(Endpoint.KAPAL_DELETE)
    Call<Response> deleteKapal(@Header("Authorization") String token, @Path(value = "kapal", encoded = true) int id);

    @Multipart
    @POST(Endpoint.KAPAL_UPDATE)
    Call<Response> updateKapal(@Header("Authorization") String token,
                               @Path(value = "kapal", encoded = true) int id,
                               @Part("nama") RequestBody nama,
                               @Part("kapasitas") RequestBody  kapasitas,
                               @Part("deskripsi") RequestBody deskripsi,
                               @Part("contact") RequestBody contact,
                               @Part("tipe") RequestBody tipe,
                               @Part("golongan") RequestBody golongan,
                               @Part("tanggal_beroperasi") RequestBody tanggal_beroperasi,
                               @Part MultipartBody.Part body);

    @Multipart
    @POST(Endpoint.KAPAL_CREATE)
    Call<Response> createKapalPhoto(@Header("Authorization") String token,
                                    @Part("nama") RequestBody nama,
                                    @Part("kapasitas") RequestBody  kapasitas,
                                    @Part("deskripsi") RequestBody deskripsi,
                                    @Part("contact") RequestBody contact,
                                    @Part("tipe") RequestBody tipe,
                                    @Part("golongan") RequestBody golongan,
                                    @Part("tanggal_beroperasi") RequestBody tanggal_beroperasi,
                                    @Part MultipartBody.Part body);
}

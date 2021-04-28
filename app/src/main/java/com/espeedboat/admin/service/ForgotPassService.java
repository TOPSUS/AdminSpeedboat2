package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ForgotPassService {
    @FormUrlEncoded
    @POST(Endpoint.GET_CODES)
    Call<Response> postCode(@Field("email") String email);

    @FormUrlEncoded
    @POST(Endpoint.CEK_CODES)
    Call<Response> postCekCode(@Field("kode") String kode);

    @FormUrlEncoded
    @POST(Endpoint.FORGOT_PASS)
    Call<Response> postPass(@Field("confirmpass") String confirmpass, @Field("newpass") String newpass, @Field("email") String email);
}

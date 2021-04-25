package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface EditProfileService {
    @GET(Endpoint.EDIT_USER)
    Call<Response> editProfile(@Header("Authorization") String token, @Path(value = "id", encoded = true) int id);

    @Multipart
    @POST(Endpoint.UPDATE_USER)
    Call<Response> updateProfile(@Header("Authorization") String token,
                                     @Path(value = "id", encoded = true) int id,
                                     @Part("nama") RequestBody nama,
                                     @Part("alamat") RequestBody alamat,
                                     @Part("jeniskelamin") RequestBody jeniskelamin,
                                     @Part("nohp") RequestBody nohp,
                                     @Part("email") RequestBody email,
                                     @Part MultipartBody.Part body);
}

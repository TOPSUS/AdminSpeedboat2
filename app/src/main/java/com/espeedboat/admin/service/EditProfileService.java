package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface EditProfileService {
    @GET(Endpoint.EDIT_USER)
    Call<Response> editProfile(@Header("Authorization") String token, @Path(value = "user", encoded = true) int id);
}

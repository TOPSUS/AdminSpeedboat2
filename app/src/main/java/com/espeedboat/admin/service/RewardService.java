package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RewardService {
    @GET(Endpoint.REWARD_INDEX)
    Call<Response> getRewards(@Header("Authorization") String token);

    @DELETE(Endpoint.REWARD_DELETE)
    Call<Response> deleteReward(@Header("Authorization") String token, @Path(value = "reward", encoded = true) int id);

    @Multipart
    @POST(Endpoint.REWARD_CREATE)
    Call<Response> createReward(@Header("Authorization") String token,
                                    @Part("reward") RequestBody reward,
                                    @Part("berlaku") RequestBody  berlaku,
                                    @Part("minimal_point") RequestBody minimal_point,
                                    @Part("id_kapal") RequestBody id_kapal,
                                    @Part MultipartBody.Part body);

    @GET(Endpoint.USER_REWARD_INDEX)
    Call<Response> getUserRewards(@Header("Authorization") String token, @Path(value = "id", encoded = true) int id);

    @GET(Endpoint.USER_REWARD_SEND)
    Call<Response> sendUserRewards(@Header("Authorization") String token, @Path(value = "id_detail", encoded = true) int id);

    @GET(Endpoint.USER_REWARD_DONE)
    Call<Response> doneUserRewards(@Header("Authorization") String token, @Path(value = "id_detail", encoded = true) int id);
}

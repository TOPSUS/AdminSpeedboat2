package com.espeedboat.admin.service;

import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.utils.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewService {
    @GET(Endpoint.REVIEW_LIST)
    Call<Response> getReviews(@Header("Authorization") String token,
                              @Query("id_pembelian") Integer id_pembelian);

    @GET(Endpoint.REVIEW_DETAIL)
    Call<Response> getReviewDetail(@Header("Authorization") String token,
                                   @Path(value = "id", encoded = true) Integer id);
}

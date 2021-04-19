package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("auth")
    @Expose
    private Auth auth;
    @SerializedName("kapal")
    @Expose
    private List<Kapal> kapal = null;
    @SerializedName("review_list")
    @Expose
    private List<ReviewList> reviewList = null;
    @SerializedName("review_summary")
    @Expose
    private ReviewSummary reviewSummary;
    @SerializedName("review_detail")
    @Expose
    private ReviewDetail reviewDetail;
    @SerializedName("review_detail_order")
    @Expose
    private ReviewDetailOrder reviewDetailOrder;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public List<ReviewList> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewList> reviewList) {
        this.reviewList = reviewList;
    }

    public ReviewSummary getReviewSummary() {
        return reviewSummary;
    }

    public void setReviewSummary(ReviewSummary reviewSummary) {
        this.reviewSummary = reviewSummary;
    }

    public List<Kapal> getKapal() {
        return kapal;
    }

    public void setKapal(List<Kapal> kapal) {
        this.kapal = kapal;
    }

    public ReviewDetail getReviewDetail() {
        return reviewDetail;
    }

    public void setReviewDetail(ReviewDetail reviewDetail) {
        this.reviewDetail = reviewDetail;
    }

    public ReviewDetailOrder getReviewDetailOrder() {
        return reviewDetailOrder;
    }

    public void setReviewDetailOrder(ReviewDetailOrder reviewDetailOrder) {
        this.reviewDetailOrder = reviewDetailOrder;
    }
}

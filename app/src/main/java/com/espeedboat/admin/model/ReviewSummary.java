package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewSummary {
    @SerializedName("total_review")
    @Expose
    private Integer totalReview;
    @SerializedName("total_score")
    @Expose
    private Integer totalScore;
    @SerializedName("review_summary_score")
    @Expose
    private ReviewSummaryScore reviewSummaryScore;

    public Integer getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Integer totalReview) {
        this.totalReview = totalReview;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public ReviewSummaryScore getReviewSummaryScore() {
        return reviewSummaryScore;
    }

    public void setReviewSummaryScore(ReviewSummaryScore reviewSummaryScore) {
        this.reviewSummaryScore = reviewSummaryScore;
    }

}

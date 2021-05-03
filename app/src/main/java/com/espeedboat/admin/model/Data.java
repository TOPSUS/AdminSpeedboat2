package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("auth")
    @Expose
    private Auth auth;
    @SerializedName("list_kapal")
    @Expose
    private List<Kapal> listKapal = null;
    @SerializedName("kapal")
    @Expose
    private Kapal kapal;
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
    @SerializedName("pelabuhan")
    @Expose
    private List<Pelabuhan> pelabuhan = null;
    @SerializedName("golongan")
    @Expose
    private List<Golongan> golongan = null;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("list_jadwal")
    @Expose
    private List<Jadwal> listJadwal = null;
    @SerializedName("dropdown")
    @Expose
    private List<Dropdown> dropdown = null;
    @SerializedName("jadwal")
    @Expose
    private Jadwal jadwal;

    public List<Pelabuhan> getPelabuhan() {
        return pelabuhan;
    }

    public void setPelabuhan(List<Pelabuhan> pelabuhan) {
        this.pelabuhan = pelabuhan;
    }

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

    public List<Kapal> getListKapal() {
        return listKapal;
    }

    public void setListKapal(List<Kapal> kapal) {
        this.listKapal = kapal;
    }

    public Kapal getKapal() {
        return kapal;
    }

    public void setKapal(Kapal kapal) {
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

    public List<Golongan> getGolongan() {
        return golongan;
    }

    public void setGolongan(List<Golongan> golongan) {
        this.golongan = golongan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Jadwal> getListJadwal() {
        return listJadwal;
    }

    public void setListJadwal(List<Jadwal> listJadwal) {
        this.listJadwal = listJadwal;
    }

    public List<Dropdown> getDropdown() {
        return dropdown;
    }

    public void setDropdown(List<Dropdown> dropdown) {
        this.dropdown = dropdown;
    }

    public Jadwal getJadwal() {
        return jadwal;
    }

    public void setJadwal(Jadwal jadwal) {
        this.jadwal = jadwal;
    }
}

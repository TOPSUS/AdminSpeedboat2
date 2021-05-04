package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dashboard {
    @SerializedName("rateReview")
    @Expose
    private Float rateReview;
    @SerializedName("transaksiDone")
    @Expose
    private Integer transaksiDone;
    @SerializedName("transaksiCount")
    @Expose
    private Integer transaksiCount;
    @SerializedName("totalPendapatan")
    @Expose
    private Integer totalPendapatan;
    @SerializedName("rekapPendapatan")
    @Expose
    private List<Integer> rekapPendapatan = null;
    @SerializedName("transaksi_list")
    @Expose
    private List<Transaksi> transaksiList = null;

    public Float getRateReview() {
        return rateReview;
    }

    public void setRateReview(Float rateReview) {
        this.rateReview = rateReview;
    }

    public Integer getTransaksiDone() {
        return transaksiDone;
    }

    public void setTransaksiDone(Integer transaksiDone) {
        this.transaksiDone = transaksiDone;
    }

    public Integer getTransaksiCount() {
        return transaksiCount;
    }

    public void setTransaksiCount(Integer transaksiCount) {
        this.transaksiCount = transaksiCount;
    }

    public Integer getTotalPendapatan() {
        return totalPendapatan;
    }

    public void setTotalPendapatan(Integer totalPendapatan) {
        this.totalPendapatan = totalPendapatan;
    }

    public List<Integer> getRekapPendapatan() {
        return rekapPendapatan;
    }

    public void setRekapPendapatan(List<Integer> rekapPendapatan) {
        this.rekapPendapatan = rekapPendapatan;
    }

    public List<Transaksi> getTransaksiList() {
        return transaksiList;
    }

    public void setTransaksiList(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
    }
}

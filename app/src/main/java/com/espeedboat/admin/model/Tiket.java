package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tiket {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("kode_tiket")
    @Expose
    private String kodeTiket;
    @SerializedName("nomor_id")
    @Expose
    private String nomorId;
    @SerializedName("status_tiket")
    @Expose
    private String statusTiket;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("status_order")
    @Expose
    private String statusOrder;
    @SerializedName("asal")
    @Expose
    private String asal;
    @SerializedName("tujuan")
    @Expose
    private String tujuan;
    @SerializedName("tanggal_berangkat")
    @Expose
    private String tanggalBerangkat;
    @SerializedName("tanggal_sampai")
    @Expose
    private String tanggalSampai;
    @SerializedName("jam_berangkat")
    @Expose
    private String jamBerangkat;
    @SerializedName("jam_sampai")
    @Expose
    private String jamSampai;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKodeTiket() {
        return kodeTiket;
    }

    public void setKodeTiket(String kodeTiket) {
        this.kodeTiket = kodeTiket;
    }

    public String getNomorId() {
        return nomorId;
    }

    public void setNomorId(String nomorId) {
        this.nomorId = nomorId;
    }

    public String getStatusTiket() {
        return statusTiket;
    }

    public void setStatusTiket(String statusTiket) {
        this.statusTiket = statusTiket;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getTanggalBerangkat() {
        return tanggalBerangkat;
    }

    public void setTanggalBerangkat(String tanggalBerangkat) {
        this.tanggalBerangkat = tanggalBerangkat;
    }

    public String getTanggalSampai() {
        return tanggalSampai;
    }

    public void setTanggalSampai(String tanggalSampai) {
        this.tanggalSampai = tanggalSampai;
    }

    public String getJamBerangkat() {
        return jamBerangkat;
    }

    public void setJamBerangkat(String jamBerangkat) {
        this.jamBerangkat = jamBerangkat;
    }

    public String getJamSampai() {
        return jamSampai;
    }

    public void setJamSampai(String jamSampai) {
        this.jamSampai = jamSampai;
    }
}

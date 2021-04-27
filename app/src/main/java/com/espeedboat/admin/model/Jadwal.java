package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jadwal {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama_kapal")
    @Expose
    private String namaKapal;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("waktu")
    @Expose
    private String waktu;
    @SerializedName("estimasi_waktu")
    @Expose
    private Integer estimasiWaktu;
    @SerializedName("nama_tujuan")
    @Expose
    private String namaTujuan;
    @SerializedName("nama_asal")
    @Expose
    private String namaAsal;
    @SerializedName("kode_tujuan")
    @Expose
    private String kodeTujuan;
    @SerializedName("kode_asal")
    @Expose
    private String kodeAsal;
    @SerializedName("harga")
    @Expose
    private Integer harga;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaKapal() {
        return namaKapal;
    }

    public void setNamaKapal(String namaKapal) {
        this.namaKapal = namaKapal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public Integer getEstimasiWaktu() {
        return estimasiWaktu;
    }

    public void setEstimasiWaktu(Integer estimasiWaktu) {
        this.estimasiWaktu = estimasiWaktu;
    }

    public String getNamaTujuan() {
        return namaTujuan;
    }

    public void setNamaTujuan(String namaTujuan) {
        this.namaTujuan = namaTujuan;
    }

    public String getNamaAsal() {
        return namaAsal;
    }

    public void setNamaAsal(String namaAsal) {
        this.namaAsal = namaAsal;
    }

    public String getKodeTujuan() {
        return kodeTujuan;
    }

    public void setKodeTujuan(String kodeTujuan) {
        this.kodeTujuan = kodeTujuan;
    }

    public String getKodeAsal() {
        return kodeAsal;
    }

    public void setKodeAsal(String kodeAsal) {
        this.kodeAsal = kodeAsal;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }
}

package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Kapal {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("kapasitas")
    @Expose
    private Integer kapasitas;
    @SerializedName("tipe")
    @Expose
    private String tipe;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("golongan")
    @Expose
    private Golongan golongan;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("tanggal_beroperasi")
    @Expose
    private String tanggalBeroperasi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(Integer kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Golongan getGolongan() {
        return golongan;
    }

    public void setGolongan(Golongan golongan) {
        this.golongan = golongan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTanggalBeroperasi() {
        return tanggalBeroperasi;
    }

    public void setTanggalBeroperasi(String tanggalBeroperasi) {
        this.tanggalBeroperasi = tanggalBeroperasi;
    }
}

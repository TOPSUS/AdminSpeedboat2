package com.espeedboat.admin.model;

import com.espeedboat.admin.utils.Endpoint;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Berita {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("created")
    @Expose
    private String created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String nama) {
        this.judul = judul;
    }

    public String getFotoBerita() {
        return foto;
    }

    public String getFotoBeritaUrl() {
        return Endpoint.URL_ADMIN+"/storage/image_berita_pelabuhan/"+foto;
    }

    public void setFotoBerita(String foto) {
        this.foto = foto;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}

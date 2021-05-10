package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reward {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("speedboat")
    @Expose
    private String speedboat;
    @SerializedName("berlaku")
    @Expose
    private String berlaku;
    @SerializedName("minimal_point")
    @Expose
    private String minimalPoint;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("foto")
    @Expose
    private String foto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpeedboat() {
        return speedboat;
    }

    public void setSpeedboat(String speedboat) {
        this.speedboat = speedboat;
    }

    public String getBerlaku() {
        return berlaku;
    }

    public void setBerlaku(String berlaku) {
        this.berlaku = berlaku;
    }

    public String getMinimalPoint() {
        return minimalPoint;
    }

    public void setMinimalPoint(String minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

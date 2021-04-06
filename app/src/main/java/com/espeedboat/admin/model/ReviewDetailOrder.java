package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewDetailOrder {
    @SerializedName("from_place")
    @Expose
    private String fromPlace;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("from_time")
    @Expose
    private String fromTime;
    @SerializedName("to_place")
    @Expose
    private String toPlace;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("to_time")
    @Expose
    private String toTime;
    @SerializedName("speedboat")
    @Expose
    private String speedboat;
    @SerializedName("person")
    @Expose
    private String person;
    @SerializedName("price")
    @Expose
    private Integer price;

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getSpeedboat() {
        return speedboat;
    }

    public void setSpeedboat(String speedboat) {
        this.speedboat = speedboat;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

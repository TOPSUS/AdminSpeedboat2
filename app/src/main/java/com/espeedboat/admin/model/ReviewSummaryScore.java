package com.espeedboat.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewSummaryScore {
    @SerializedName("1")
    @Expose
    private Integer score_1;
    @SerializedName("2")
    @Expose
    private Integer score_2;
    @SerializedName("3")
    @Expose
    private Integer score_3;
    @SerializedName("4")
    @Expose
    private Integer score_4;
    @SerializedName("5")
    @Expose
    private Integer score_5;

    public Integer get1() {
        return score_1;
    }

    public void set1(Integer _1) {
        this.score_1 = _1;
    }

    public Integer get2() {
        return score_2;
    }

    public void set2(Integer _2) {
        this.score_2 = _2;
    }

    public Integer get3() {
        return score_3;
    }

    public void set3(Integer _3) {
        this.score_3 = _3;
    }

    public Integer get4() {
        return score_4;
    }

    public void set4(Integer _4) {
        this.score_4 = _4;
    }

    public Integer get5() {
        return score_5;
    }

    public void set5(Integer _5) {
        this.score_5 = _5;
    }
}

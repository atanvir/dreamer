package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecasterData {

    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("totalRating")
    @Expose
    private Float totalRating;
    @SerializedName("avgRating")
    @Expose
    private Float avgRating;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pricePerQues")
    @Expose
    private String pricePerQues;

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Float totalRating) {
        this.totalRating = totalRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPricePerQues() {
        return pricePerQues;
    }

    public void setPricePerQues(String pricePerQues) {
        this.pricePerQues = pricePerQues;
    }
}

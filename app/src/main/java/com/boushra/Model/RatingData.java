package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingData {
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("ratingMessage")
    @Expose
    private String ratingMessage;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("userData")
    @Expose
    private UserData userData;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRatingMessage() {
        return ratingMessage;
    }

    public void setRatingMessage(String ratingMessage) {
        this.ratingMessage = ratingMessage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}

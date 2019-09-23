package com.boushra.Model.ForecasterRating;

import com.boushra.Model.ForecasterData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("forecasterId")
    @Expose
    private String forecasterId;
    @SerializedName("userId")
    @Expose
    private UserId userId;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("ratingMessage")
    @Expose
    private String ratingMessage;
    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    @SerializedName("forecasterData")
    @Expose
    private ForecasterData forecasterData;

    public ForecasterData getForecasterData() {
        return forecasterData;
    }

    public void setForecasterData(ForecasterData forecasterData) {
        this.forecasterData = forecasterData;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForecasterId() {
        return forecasterId;
    }

    public void setForecasterId(String forecasterId) {
        this.forecasterId = forecasterId;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }




    public String getRatingMessage() {
        return ratingMessage;
    }

    public void setRatingMessage(String ratingMessage) {
        this.ratingMessage = ratingMessage;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}

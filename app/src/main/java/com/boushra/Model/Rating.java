package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("forecasterId")
    @Expose
    private String forecasterId;
    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("ratingMessage")
    @Expose
    private String ratingMessage;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;

    @SerializedName("isRate")
    @Expose
    private Boolean isRate;

    public Boolean getRate() {
        return isRate;
    }

    public void setRate(Boolean rate) {
        isRate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getForecasterId() {
        return forecasterId;
    }

    public void setForecasterId(String forecasterId) {
        this.forecasterId = forecasterId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

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
}

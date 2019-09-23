package com.boushra.Model.MyBooking;

import com.boushra.Model.ForecasterData;
import com.boushra.Model.RatingData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data
{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("bookingStatus")
    @Expose
    private String bookingStatus;
    @SerializedName("forecasterId")
    @Expose
    private String forecasterId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("ratingByUser")
    @Expose
    private List<RatingByUser> ratingByUser = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("forecasterData")
    @Expose
    private ForecasterData forecasterData;
    @SerializedName("isRate")
    @Expose
    private Boolean isRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getForecasterId() {
        return forecasterId;
    }

    public void setForecasterId(String forecasterId) {
        this.forecasterId = forecasterId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<RatingByUser> getRatingByUser() {
        return ratingByUser;
    }

    public void setRatingByUser(List<RatingByUser> ratingByUser) {
        this.ratingByUser = ratingByUser;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ForecasterData getForecasterData() {
        return forecasterData;
    }

    public void setForecasterData(ForecasterData forecasterData) {
        this.forecasterData = forecasterData;
    }

    public Boolean getRate() {
        return isRate;
    }

    public void setRate(Boolean rate) {
        isRate = rate;
    }
}

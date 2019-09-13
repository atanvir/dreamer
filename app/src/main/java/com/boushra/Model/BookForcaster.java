package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookForcaster {

    @SerializedName("forecasterId")
    @Expose
    private String forecasterId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("voiceRecording")
    @Expose
    private String voiceRecording;
    @SerializedName("question")
    @Expose
    private String question;


    @SerializedName("langCode")
    @Expose
    private String langCode;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;
    @SerializedName("Data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getForecasterId() {
        return forecasterId;
    }

    public void setForecasterId(String forecasterId) {
        this.forecasterId = forecasterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getVoiceRecording() {
        return voiceRecording;
    }

    public void setVoiceRecording(String voiceRecording) {
        this.voiceRecording = voiceRecording;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

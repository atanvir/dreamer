package com.boushra.Model.ForecasterRating;

import com.boushra.Model.ForecasterRating.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecasterRating {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("response_message")
    @Expose
    private String responseMessage;

    @SerializedName("Data")
    @Expose
    private List<Data> data = null;

    @SerializedName("forecasterId")
    @Expose
    private String forecasterId;


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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getForecasterId() {
        return forecasterId;
    }

    public void setForecasterId(String forecasterId) {
        this.forecasterId = forecasterId;
    }
}

package com.boushra.Model;

import com.boushra.Model.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForcasterList {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("response_message")
    @Expose
    private String responseMessage;
    @SerializedName("Data")
    @Expose
    private List<Data> data = null;

    @SerializedName("langCode")
    @Expose
    private String langCode;

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}

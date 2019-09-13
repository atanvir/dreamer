package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPassword {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("Data")
    @Expose
    private Data data;

    @SerializedName("langCode")
    @Expose
    private String langCode;


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}

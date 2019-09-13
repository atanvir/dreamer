package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSetting {

    @SerializedName("userId")
    @Expose
    private String userId;


    @SerializedName("notificationStatus")
    @Expose
    private Boolean notificationStatus;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("_id")
    @Expose
    private String id;


    @SerializedName("langCode")
    @Expose
    private String langCode;



    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


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




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

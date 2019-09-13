package com.boushra.Model;

import com.boushra.Model.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLogin {
    @SerializedName("socialType")
    @Expose
    private String socialType;
    @SerializedName("socialId")
    @Expose
    private String socialId;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("name")
    @Expose
    private String name;

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
    @SerializedName("loginStatus")
    @Expose
    private Integer loginStatus;

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

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }


    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

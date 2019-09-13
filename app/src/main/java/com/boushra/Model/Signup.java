package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Signup {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthPlace")
    @Expose
    private String birthPlace;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;


    @SerializedName("type")
    @Expose
    private String type;


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("response_message")
    @Expose
    private String response_message;

    @SerializedName("Data")
    @Expose
    private com.boushra.Model.Data data;

    @SerializedName("langCode")
    @Expose
    private String langCode;

    @SerializedName("userId")
    @Expose
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public com.boushra.Model.Data getData() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

}
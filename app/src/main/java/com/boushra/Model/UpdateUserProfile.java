package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateUserProfile {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("birthPlace")
    @Expose
    private String birthPlace;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;

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

    @SerializedName("username")
    @Expose
    private String username;






    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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
}

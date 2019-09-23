package com.boushra.Model.ForecasterRating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserId {

    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("username")
    @Expose
    private String username;



    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

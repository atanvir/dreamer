package com.boushra.Model.MyBooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingByUser {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

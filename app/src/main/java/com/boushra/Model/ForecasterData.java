package com.boushra.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecasterData implements Parcelable {

    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("totalRating")
    @Expose
    private Float totalRating;
    @SerializedName("avgRating")
    @Expose
    private Float avgRating;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pricePerQues")
    @Expose
    private String pricePerQues;

    protected ForecasterData(Parcel in) {
        profilePic = in.readString();
        if (in.readByte() == 0) {
            totalRating = null;
        } else {
            totalRating = in.readFloat();
        }
        if (in.readByte() == 0) {
            avgRating = null;
        } else {
            avgRating = in.readFloat();
        }
        name = in.readString();
        pricePerQues = in.readString();
    }

    public static final Creator<ForecasterData> CREATOR = new Creator<ForecasterData>() {
        @Override
        public ForecasterData createFromParcel(Parcel in) {
            return new ForecasterData(in);
        }

        @Override
        public ForecasterData[] newArray(int size) {
            return new ForecasterData[size];
        }
    };

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Float totalRating) {
        this.totalRating = totalRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPricePerQues() {
        return pricePerQues;
    }

    public void setPricePerQues(String pricePerQues) {
        this.pricePerQues = pricePerQues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profilePic);
        if (totalRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(totalRating);
        }
        if (avgRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(avgRating);
        }
        dest.writeString(name);
        dest.writeString(pricePerQues);
    }
}

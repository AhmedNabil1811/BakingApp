package com.example.android.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("description")
    private String description;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("videoURL")
    private String videoURl;
    @SerializedName("thumbnailURL")
    private String thumbnailUrl;

    protected Step(Parcel in) {
        id = in.readInt();
        description = in.readString();
        shortDescription = in.readString();
        videoURl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoUrl() {
        return videoURl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(shortDescription);
        dest.writeString(videoURl);
        dest.writeString(thumbnailUrl);
    }
}

package com.chahat.odeum.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chahat on 1/9/17.
 */

public class ImagesObject implements Parcelable {

    @SerializedName("aspect_ratio") double aspectRatio;
    @SerializedName("file_path") String filePath;
    @SerializedName("height") int height;
    @SerializedName("vote_average") double voteAverage;
    @SerializedName("vote_count") int voteCount;
    @SerializedName("width") int width;

    protected ImagesObject(Parcel in) {
        aspectRatio = in.readDouble();
        filePath = in.readString();
        height = in.readInt();
        voteAverage = in.readDouble();
        voteCount = in.readInt();
        width = in.readInt();
    }

    public static final Creator<ImagesObject> CREATOR = new Creator<ImagesObject>() {
        @Override
        public ImagesObject createFromParcel(Parcel in) {
            return new ImagesObject(in);
        }

        @Override
        public ImagesObject[] newArray(int size) {
            return new ImagesObject[size];
        }
    };

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(aspectRatio);
        parcel.writeString(filePath);
        parcel.writeInt(height);
        parcel.writeDouble(voteAverage);
        parcel.writeInt(voteCount);
        parcel.writeInt(width);
    }
}

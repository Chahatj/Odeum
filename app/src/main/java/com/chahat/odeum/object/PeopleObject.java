package com.chahat.odeum.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 31/8/17.
 */

public class PeopleObject implements Parcelable {

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("known_for")
    private List<MovieObject> knownFor;

    protected PeopleObject(Parcel in) {
        profilePath = in.readString();
        adult = in.readByte() != 0;
        id = in.readInt();
        name = in.readString();
        popularity = in.readDouble();
        knownFor = in.createTypedArrayList(MovieObject.CREATOR);
    }

    public static final Creator<PeopleObject> CREATOR = new Creator<PeopleObject>() {
        @Override
        public PeopleObject createFromParcel(Parcel in) {
            return new PeopleObject(in);
        }

        @Override
        public PeopleObject[] newArray(int size) {
            return new PeopleObject[size];
        }
    };

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public List<MovieObject> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<MovieObject> knownFor) {
        this.knownFor = knownFor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(profilePath);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(popularity);
        parcel.writeTypedList(knownFor);
    }
}

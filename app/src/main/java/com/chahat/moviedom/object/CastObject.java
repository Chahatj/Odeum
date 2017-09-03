package com.chahat.moviedom.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chahat on 2/9/17.
 */

public class CastObject  implements Parcelable{

    @SerializedName("id")
    private int id;
    @SerializedName("character")
    private String character;
    @SerializedName("credit_id")
    private String creditId;
    @SerializedName("gender")
    private int gender;
    @SerializedName("name")
    private String  name;
    @SerializedName("order")
    private int order;
    @SerializedName("profile_path")
    private String profilePath;

    private CastObject(Parcel in) {
        id = in.readInt();
        character = in.readString();
        creditId = in.readString();
        gender = in.readInt();
        name = in.readString();
        order = in.readInt();
        profilePath = in.readString();
    }

    public static final Creator<CastObject> CREATOR = new Creator<CastObject>() {
        @Override
        public CastObject createFromParcel(Parcel in) {
            return new CastObject(in);
        }

        @Override
        public CastObject[] newArray(int size) {
            return new CastObject[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(character);
        parcel.writeString(creditId);
        parcel.writeInt(gender);
        parcel.writeString(name);
        parcel.writeInt(order);
        parcel.writeString(profilePath);
    }
}

package com.chahat.moviedom.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chahat on 29/8/17.
 */

public class MovieCastObject implements Parcelable{

    private String character,name,profile,creditId;
    private int castId,id;

    public MovieCastObject(){

    }

    protected MovieCastObject(Parcel in) {
        character = in.readString();
        name = in.readString();
        profile = in.readString();
        creditId = in.readString();
        castId = in.readInt();
        id = in.readInt();
    }

    public static final Creator<MovieCastObject> CREATOR = new Creator<MovieCastObject>() {
        @Override
        public MovieCastObject createFromParcel(Parcel in) {
            return new MovieCastObject(in);
        }

        @Override
        public MovieCastObject[] newArray(int size) {
            return new MovieCastObject[size];
        }
    };

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(character);
        parcel.writeString(name);
        parcel.writeString(profile);
        parcel.writeString(creditId);
        parcel.writeInt(castId);
        parcel.writeInt(id);
    }
}

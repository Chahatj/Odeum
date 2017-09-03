package com.chahat.moviedom.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chahat on 29/8/17.
 */

public class MovieVideoObject implements Parcelable {

    private String id,key,name,site,type;

    public MovieVideoObject(){

    }

    private MovieVideoObject(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        type = in.readString();
    }

    public static final Creator<MovieVideoObject> CREATOR = new Creator<MovieVideoObject>() {
        @Override
        public MovieVideoObject createFromParcel(Parcel in) {
            return new MovieVideoObject(in);
        }

        @Override
        public MovieVideoObject[] newArray(int size) {
            return new MovieVideoObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(type);
    }
}

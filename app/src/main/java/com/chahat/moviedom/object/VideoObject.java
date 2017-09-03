package com.chahat.moviedom.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chahat on 2/9/17.
 */

public class VideoObject implements Parcelable {

    @SerializedName("id") String id;
    @SerializedName("key") String key;
    @SerializedName("name") String name;
    @SerializedName("site") String site;
    @SerializedName("size") int size;
    @SerializedName("type") String  type;
    @SerializedName("iso_639_1") String iso639;
    @SerializedName("iso_3166_1") String iso3166;

    protected VideoObject(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readInt();
        type = in.readString();
        iso639 = in.readString();
        iso3166 = in.readString();
    }

    public static final Creator<VideoObject> CREATOR = new Creator<VideoObject>() {
        @Override
        public VideoObject createFromParcel(Parcel in) {
            return new VideoObject(in);
        }

        @Override
        public VideoObject[] newArray(int size) {
            return new VideoObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String  id) {
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIso639() {
        return iso639;
    }

    public void setIso639(String iso639) {
        this.iso639 = iso639;
    }

    public String getIso3166() {
        return iso3166;
    }

    public void setIso3166(String iso3166) {
        this.iso3166 = iso3166;
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
        parcel.writeInt(size);
        parcel.writeString(type);
        parcel.writeString(iso639);
        parcel.writeString(iso3166);
    }
}

package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowCreatedByObject {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private int gender;
    @SerializedName("profile_path")
    private String profilePath;

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}

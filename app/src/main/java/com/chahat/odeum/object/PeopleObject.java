package com.chahat.odeum.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 31/8/17.
 */

public class PeopleObject {

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
}

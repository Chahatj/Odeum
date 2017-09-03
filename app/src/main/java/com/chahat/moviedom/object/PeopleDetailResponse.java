package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chahat on 1/9/17.
 */

public class PeopleDetailResponse {

    @SerializedName("adult") boolean adult;
    @SerializedName("biography") String biography;
    @SerializedName("birthday") String birthday;
    @SerializedName("deathday") String deathday;
    @SerializedName("gender") int gender;
    @SerializedName("homepage") String homepage;
    @SerializedName("id") int id;
    @SerializedName("imdb_id") String imdbId;
    @SerializedName("name") String name;
    @SerializedName("place_of_birth") String birthPlace;
    @SerializedName("popularity") double popularity;
    @SerializedName("profile_path") String profilePath;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}

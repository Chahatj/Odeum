package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 1/9/17.
 */

public class PeopleMovieResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private List<MovieObject> peopleMoviesList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieObject> getPeopleMoviesList() {
        return peopleMoviesList;
    }

    public void setPeopleMoviesList(List<MovieObject> peopleMoviesList) {
        this.peopleMoviesList = peopleMoviesList;
    }
}

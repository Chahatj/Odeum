package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 1/9/17.
 */

public class ProfileImageResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("profiles")
    private List<ImagesObject> imageList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ImagesObject> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImagesObject> imageList) {
        this.imageList = imageList;
    }
}

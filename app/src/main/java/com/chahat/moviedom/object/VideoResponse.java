package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 2/9/17.
 */

public class VideoResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<VideoObject> videoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoObject> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoObject> videoList) {
        this.videoList = videoList;
    }
}

package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 1/9/17.
 */

public class PeopleTvShowResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private
    List<TvShowObject> peopleTvShowList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TvShowObject> getPeopleTvShowList() {
        return peopleTvShowList;
    }

    public void setPeopleTvShowList(List<TvShowObject> peopleTvShowList) {
        this.peopleTvShowList = peopleTvShowList;
    }
}

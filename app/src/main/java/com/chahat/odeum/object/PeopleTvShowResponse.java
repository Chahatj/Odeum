package com.chahat.odeum.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 1/9/17.
 */

public class PeopleTvShowResponse {

    @SerializedName("id") int id;
    @SerializedName("cast")
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

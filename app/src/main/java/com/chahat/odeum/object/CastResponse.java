package com.chahat.odeum.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 2/9/17.
 */

public class CastResponse {

    @SerializedName("id") int id;
    @SerializedName("cast") List<CastObject> castList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CastObject> getCastList() {
        return castList;
    }

    public void setCastList(List<CastObject> castList) {
        this.castList = castList;
    }
}

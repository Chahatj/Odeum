package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chahat on 2/9/17.
 */

public class ProductionCompanyObject {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

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
}

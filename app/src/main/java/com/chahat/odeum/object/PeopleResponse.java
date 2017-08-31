package com.chahat.odeum.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 31/8/17.
 */

public class PeopleResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<PeopleObject> result;

    @SerializedName("total_results")
    private int totalResult;

    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<PeopleObject> getResult() {
        return result;
    }

    public void setResult(List<PeopleObject> result) {
        this.result = result;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

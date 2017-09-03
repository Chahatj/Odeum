package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 1/9/17.
 */

public class TvShowResponse {

    @SerializedName("page") int page;
    @SerializedName("results") List<TvShowObject> results;
    @SerializedName("total_results") int totalResults;
    @SerializedName("total_pages") int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TvShowObject> getResults() {
        return results;
    }

    public void setResults(List<TvShowObject> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

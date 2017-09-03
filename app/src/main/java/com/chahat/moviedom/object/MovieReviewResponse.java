package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 3/9/17.
 */

public class MovieReviewResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<MovieReviewObject> reviewList;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieReviewObject> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<MovieReviewObject> reviewList) {
        this.reviewList = reviewList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}

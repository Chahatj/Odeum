package com.chahat.moviedom.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowDetailObject {

    @SerializedName("backdrop_path") String backdropPath;
    @SerializedName("created_by") List<TvShowCreatedByObject> createdByList;
    @SerializedName("episode_run_time") List<Integer> episodeRunTimeList;
    @SerializedName("first_air_date") String firstAirDate;
    @SerializedName("genres") List<GenresObject> genresList;
    @SerializedName("homepage") String homepage;
    @SerializedName("id") int id;
    @SerializedName("in_production") boolean inProduction;
    @SerializedName("languages") List<String> languagesList;
    @SerializedName("last_air_date") String lastAirDate;
    @SerializedName("name") String name;
    @SerializedName("networks") List<TvShowNetworksObject> networkList;
    @SerializedName("number_of_episodes") int numberOfEpisodes;
    @SerializedName("number_of_seasons") int numberOfSeasons;
    @SerializedName("origin_country") List<String> countryList;
    @SerializedName("original_language") String language;
    @SerializedName("original_name") String originalName;
    @SerializedName("overview") String overview;
    @SerializedName("popularity") double popularity;
    @SerializedName("poster_path") String posterPath;
    @SerializedName("production_companies") List<ProductionCompanyObject> productionCompanyList;
    @SerializedName("status") String status;
    @SerializedName("type") String type;
    @SerializedName("vote_average") double voteAverage;
    @SerializedName("vote_count") int voteCount;
    @SerializedName("seasons") List<TvSeasonObject> tvSeasonList;

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<TvShowCreatedByObject> getCreatedByList() {
        return createdByList;
    }

    public void setCreatedByList(List<TvShowCreatedByObject> createdByList) {
        this.createdByList = createdByList;
    }

    public List<Integer> getEpisodeRunTimeList() {
        return episodeRunTimeList;
    }

    public void setEpisodeRunTimeList(List<Integer> episodeRunTimeList) {
        this.episodeRunTimeList = episodeRunTimeList;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<GenresObject> getGenresList() {
        return genresList;
    }

    public void setGenresList(List<GenresObject> genresList) {
        this.genresList = genresList;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public List<String> getLanguagesList() {
        return languagesList;
    }

    public void setLanguagesList(List<String> languagesList) {
        this.languagesList = languagesList;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TvShowNetworksObject> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(List<TvShowNetworksObject> networkList) {
        this.networkList = networkList;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<String> countryList) {
        this.countryList = countryList;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionCompanyObject> getProductionCompanyList() {
        return productionCompanyList;
    }

    public void setProductionCompanyList(List<ProductionCompanyObject> productionCompanyList) {
        this.productionCompanyList = productionCompanyList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public List<TvSeasonObject> getTvSeasonList() {
        return tvSeasonList;
    }

    public void setTvSeasonList(List<TvSeasonObject> tvSeasonList) {
        this.tvSeasonList = tvSeasonList;
    }
}

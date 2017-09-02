package com.chahat.odeum.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 2/9/17.
 */

public class SeasonObject {

    @SerializedName("air_date") String airDate;
    @SerializedName("episode_count") int episodeCount;
    @SerializedName("id") int id;
    @SerializedName("poster_path") String posterPath;
    @SerializedName("season_number") int seasonNumber;
    @SerializedName("name") String name;
    @SerializedName("overview") String overview;
    @SerializedName("episodes") List<EpisodeObject> episodeList;

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<EpisodeObject> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<EpisodeObject> episodeList) {
        this.episodeList = episodeList;
    }
}

package com.chahat.moviedom.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chahat on 2/9/17.
 */

public class EpisodeObject implements Parcelable {

    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episode_number")
    private int episodeNumber;
    @SerializedName("name")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("id")
    private int id;
    @SerializedName("production_code")
    private String productionCode;
    @SerializedName("season_number")
    private int seasonNumber;
    @SerializedName("still_path")
    private String stillPath;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    private EpisodeObject(Parcel in) {
        airDate = in.readString();
        episodeNumber = in.readInt();
        name = in.readString();
        overview = in.readString();
        id = in.readInt();
        productionCode = in.readString();
        seasonNumber = in.readInt();
        stillPath = in.readString();
        voteAverage = in.readDouble();
        voteCount = in.readInt();
    }

    public static final Creator<EpisodeObject> CREATOR = new Creator<EpisodeObject>() {
        @Override
        public EpisodeObject createFromParcel(Parcel in) {
            return new EpisodeObject(in);
        }

        @Override
        public EpisodeObject[] newArray(int size) {
            return new EpisodeObject[size];
        }
    };

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(airDate);
        parcel.writeInt(episodeNumber);
        parcel.writeString(name);
        parcel.writeString(overview);
        parcel.writeInt(id);
        parcel.writeString(productionCode);
        parcel.writeInt(seasonNumber);
        parcel.writeString(stillPath);
        parcel.writeDouble(voteAverage);
        parcel.writeInt(voteCount);
    }
}

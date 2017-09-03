package com.chahat.moviedom.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by chahat on 1/9/17.
 */

public class TvShowObject implements Parcelable {

    @SerializedName("poster_path") String posterPath;
    @SerializedName("popularity") double popularity;
    @SerializedName("id") int id;
    @SerializedName("backdrop_path") String backdropPath;
    @SerializedName("vote_average") double voteAverage;
    @SerializedName("overview") String overview;
    @SerializedName("first_air_date") String firstAirDate;
    @SerializedName("origin_country") ArrayList<String> originCountry;
    @SerializedName("genre_ids") ArrayList<Integer> genreIds;
    @SerializedName("original_language") String originLanguage;
    @SerializedName("vote_count") int voteCount;
    @SerializedName("name") String name;
    @SerializedName("original_name") String originalName;

    protected TvShowObject(Parcel in) {
        posterPath = in.readString();
        popularity = in.readDouble();
        id = in.readInt();
        backdropPath = in.readString();
        voteAverage = in.readDouble();
        overview = in.readString();
        firstAirDate = in.readString();
        originCountry = in.createStringArrayList();
        originLanguage = in.readString();
        voteCount = in.readInt();
        name = in.readString();
        originalName = in.readString();
    }

    public static final Creator<TvShowObject> CREATOR = new Creator<TvShowObject>() {
        @Override
        public TvShowObject createFromParcel(Parcel in) {
            return new TvShowObject(in);
        }

        @Override
        public TvShowObject[] newArray(int size) {
            return new TvShowObject[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public ArrayList<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(ArrayList<String> originCountry) {
        this.originCountry = originCountry;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginLanguage() {
        return originLanguage;
    }

    public void setOriginLanguage(String originLanguage) {
        this.originLanguage = originLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeDouble(popularity);
        parcel.writeInt(id);
        parcel.writeString(backdropPath);
        parcel.writeDouble(voteAverage);
        parcel.writeString(overview);
        parcel.writeString(firstAirDate);
        parcel.writeStringList(originCountry);
        parcel.writeString(originLanguage);
        parcel.writeInt(voteCount);
        parcel.writeString(name);
        parcel.writeString(originalName);
    }
}

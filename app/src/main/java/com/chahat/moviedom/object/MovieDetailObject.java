package com.chahat.moviedom.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chahat on 24/8/17.
 */

public class MovieDetailObject implements Parcelable {

    @SerializedName("adult") private boolean adult;
    @SerializedName("backdrop_path") private String backdropPath;
    @SerializedName("budget") private int budget;
    @SerializedName("genres") private List<GenresObject> genresList;
    @SerializedName("id") private int id;
    @SerializedName("imdb_id") private String imdb_id;
    @SerializedName("original_title") private String originalTitle;
    @SerializedName("overview") private String overview;
    @SerializedName("production_companies") private List<ProductionCompanyObject> companyList;
    @SerializedName("release_date") private String releaseDate;
    @SerializedName("revenue") private int revenue;
    @SerializedName("runtime") private int runtime;
    @SerializedName("tagline") private String tagline;
    @SerializedName("title") private String title;
    @SerializedName("vote_average") private double voteAverage;
    @SerializedName("poster_path") private String posterPath;

    private MovieDetailObject(Parcel in) {
        adult = in.readByte() != 0;
        backdropPath = in.readString();
        budget = in.readInt();
        id = in.readInt();
        imdb_id = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        revenue = in.readInt();
        runtime = in.readInt();
        tagline = in.readString();
        title = in.readString();
        voteAverage = in.readDouble();
        posterPath = in.readString();
    }

    public static final Creator<MovieDetailObject> CREATOR = new Creator<MovieDetailObject>() {
        @Override
        public MovieDetailObject createFromParcel(Parcel in) {
            return new MovieDetailObject(in);
        }

        @Override
        public MovieDetailObject[] newArray(int size) {
            return new MovieDetailObject[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<GenresObject> getGenresList() {
        return genresList;
    }

    public void setGenresList(List<GenresObject> genresList) {
        this.genresList = genresList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<ProductionCompanyObject> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<ProductionCompanyObject> companyList) {
        this.companyList = companyList;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(backdropPath);
        parcel.writeInt(budget);
        parcel.writeInt(id);
        parcel.writeString(imdb_id);
        parcel.writeString(originalTitle);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeInt(revenue);
        parcel.writeInt(runtime);
        parcel.writeString(tagline);
        parcel.writeString(title);
        parcel.writeDouble(voteAverage);
        parcel.writeString(posterPath);
    }
}

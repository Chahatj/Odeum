package com.chahat.odeum.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chahat on 29/8/17.
 */

public class MovieReviewObject implements Parcelable {

    private String id,author,content,url;

    public MovieReviewObject(){

    }

    protected MovieReviewObject(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<MovieReviewObject> CREATOR = new Creator<MovieReviewObject>() {
        @Override
        public MovieReviewObject createFromParcel(Parcel in) {
            return new MovieReviewObject(in);
        }

        @Override
        public MovieReviewObject[] newArray(int size) {
            return new MovieReviewObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }
}

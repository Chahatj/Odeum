package com.chahat.odeum.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chahat on 24/8/17.
 */

public class ApiClient {

    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    public static final String YOU_TUBE_IMAGE = "http://img.youtube.com/vi/";
    public static final String YOU_TUBE_VIDEO = "https://www.youtube.com/watch?v=";

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

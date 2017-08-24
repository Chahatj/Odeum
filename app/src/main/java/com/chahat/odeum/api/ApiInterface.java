package com.chahat.odeum.api;

import com.chahat.odeum.object.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chahat on 24/8/17.
 */

public interface ApiInterface {

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String API_KEY,@Query("page") int page);
}
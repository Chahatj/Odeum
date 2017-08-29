package com.chahat.odeum.api;

import com.chahat.odeum.object.MovieResponse;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chahat on 24/8/17.
 */

public interface ApiInterface {

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovie(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovie(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("movie/{id}")
    Call<ResponseBody> getMovieDetail(@Path("id") int id,@Query("api_key") String API_KEY);
    @GET("movie/{id}/videos")
    Call<ResponseBody> getMovieTrailer(@Path("id") int id,@Query("api_key") String API_KEY);
    @GET("movie/{id}/credits")
    Call<ResponseBody> getMovieCast(@Path("id") int id,@Query("api_key") String API_KEY);
    @GET("movie/{id}/reviews")
    Call<ResponseBody> getMovieReview(@Path("id") int id,@Query("api_key") String API_KEY);
}

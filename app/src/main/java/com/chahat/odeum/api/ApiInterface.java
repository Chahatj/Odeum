package com.chahat.odeum.api;

import com.chahat.odeum.fragment.PeopleMoviesFragment;
import com.chahat.odeum.object.MovieResponse;
import com.chahat.odeum.object.PeopleDetailResponse;
import com.chahat.odeum.object.PeopleMovieResponse;
import com.chahat.odeum.object.PeopleResponse;
import com.chahat.odeum.object.PeopleTvShowResponse;
import com.chahat.odeum.object.ProfileImageResponse;
import com.chahat.odeum.object.TvShowDetailObject;
import com.chahat.odeum.object.TvShowResponse;
import com.google.gson.annotations.SerializedName;

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
    @GET("movie/{id}/similar")
    Call<MovieResponse> getSimilarMovie(@Path("id") int id,@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("person/popular")
    Call<PeopleResponse> getPopularPeople(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("person/{person_id}")
    Call<PeopleDetailResponse> getpeopleDetail(@Path("person_id") int id,@Query("api_key") String API_KEY);
    @GET("person/{person_id}/images")
    Call<ProfileImageResponse> getPeopleImages(@Path("person_id") int id,@Query("api_key") String API_KEY);
    @GET("person/{person_id}/movie_credits")
    Call<PeopleMovieResponse> getPeopleMovieList(@Path("person_id") int id,@Query("api_key") String API_KEY);
    @GET("person/{person_id}/tv_credits")
    Call<PeopleTvShowResponse> getPeopleTvShowList(@Path("person_id") int id, @Query("api_key") String API_KEY);
    @GET("tv/airing_today")
    Call<TvShowResponse> getAiringTodayShow(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("tv/on_the_air")
    Call<TvShowResponse> getOnTheAirShow(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("tv/popular")
    Call<TvShowResponse> getPopularTvShow(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("tv/top_rated")
    Call<TvShowResponse> getTopRatedTvShow(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("tv/{tv_id}")
    Call<TvShowDetailObject> getTvShowDetail(@Path("tv_id") int id,@Query("api_key") String API_KEY);
}

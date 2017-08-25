package com.chahat.odeum.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.R;
import com.chahat.odeum.adapter.MovieAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.MovieDetailObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.chahat.odeum.BuildConfig.API_KEY;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.movie_image) ImageView movieImage;
    @BindView(R.id.movie_poster) ImageView movie_poster;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_date) TextView tv_date;
    @BindView(R.id.tv_genre) TextView tv_genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("Id",0);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getMovieDetail(id,API_KEY);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    MovieDetailObject movieDetailObject = new MovieDetailObject();
                    movieDetailObject.setAdult(jsonObject.getBoolean("adult"));
                    movieDetailObject.setBackdropPath(jsonObject.getString("backdrop_path"));
                    movieDetailObject.setBudget(jsonObject.getInt("budget"));
                    movieDetailObject.setId(jsonObject.getInt("id"));
                    movieDetailObject.setImdb_id(jsonObject.getString("imdb_id"));
                    movieDetailObject.setOriginalTitle(jsonObject.getString("original_title"));
                    movieDetailObject.setOverview(jsonObject.getString("overview"));
                    movieDetailObject.setPosterPath(jsonObject.getString("poster_path"));
                    movieDetailObject.setReleaseDate(jsonObject.getString("release_date"));
                    movieDetailObject.setRevenue(jsonObject.getInt("revenue"));
                    movieDetailObject.setRuntime(jsonObject.getInt("runtime"));
                    movieDetailObject.setTagline(jsonObject.getString("tagline"));
                    movieDetailObject.setTitle(jsonObject.getString("title"));
                    movieDetailObject.setVoteAverage(jsonObject.getDouble("vote_average"));

                    JSONArray jsonArray = jsonObject.getJSONArray("genres");
                    String genres=null;
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        int id = jsonObject1.getInt("id");
                        String name = jsonObject1.getString("name");
                        genres = genres +", "+ name;
                    }
                    movieDetailObject.setGenresName(genres);

                    JSONArray jsonArray1 = jsonObject.getJSONArray("production_companies");
                    String company=null;
                    for (int i=0;i<jsonArray1.length();i++){
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        int id = jsonObject1.getInt("id");
                        String name = jsonObject1.getString("name");
                        company = company +", "+ name;
                    }


                    if (movieDetailObject.getPosterPath()!=null){
                        Picasso.with(getApplicationContext()).load(ApiClient.IMAGE_URL+movieDetailObject.getPosterPath()).into(movie_poster);
                    }
                    if (movieDetailObject.getBackdropPath()!=null){
                        Picasso.with(getApplicationContext()).load(ApiClient.IMAGE_URL+movieDetailObject.getBackdropPath()).into(movieImage);
                    }

                    String[] date = movieDetailObject.getReleaseDate().split("-");
                    int hour = movieDetailObject.getRuntime()/60;
                    int minute = movieDetailObject.getRuntime()%60;

                    tv_date.setText(date[0]+" \u25CF "+hour+" hr "+minute+" min");
                    tv_title.setText(movieDetailObject.getTitle());

                    if (movieDetailObject.getGenresName()!=null){
                        tv_genre.setText(movieDetailObject.getGenresName().substring(1));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MovieDetail",t.toString());
            }
        });
    }
}

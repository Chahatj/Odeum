package com.chahat.odeum.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.R;
import com.chahat.odeum.adapter.MovieAdapter;
import com.chahat.odeum.adapter.MovieDetailViewAdapter;
import com.chahat.odeum.adapter.ViewPagerAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.fragment.NowplayingFragment;
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

public class MovieDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener,View.OnClickListener {

    @BindView(R.id.movie_image) ImageView movieImage;
    @BindView(R.id.movie_poster) ImageView movie_poster;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_date) TextView tv_date;
    @BindView(R.id.tv_genre) TextView tv_genre;
    @BindView(R.id.sliding_layout) TabLayout tabs;
    @BindView(R.id.view_pager) ViewPager pager;
    CharSequence[] Titles = {"INFO","CAST","REVIEW"};
    int Numboftabs = 3;
    private  MovieDetailViewAdapter adapter;
    @BindView(R.id.app_bar_layout) AppBarLayout appBarLayout;
    @BindView(R.id.coordinateLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.imageViewBack) ImageView imageViewBack;
    private static final String SAVE_ID = "id";
    private static final String SAVE_IMAGEURL_POSTER = "imageURLPoster";
    private static final String SAVE_IMAGEURL_BACK = "imageURLBack";
    private static final String SAVE_DATE = "date";
    private static final String SAVE_TITLE = "title";
    private static final String SAVE_GENRE = "genre";
    private static final String SAVE_TRANSITION_NAME = "transitionName";
    private int id;
    private String imageURLPoster;
    private String imageURLBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        appBarLayout.addOnOffsetChangedListener(this);
        imageViewBack.setOnClickListener(this);

        if (savedInstanceState==null){
            Intent intent = getIntent();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (intent.hasExtra(NowplayingFragment.ACTIVITY_NAME)){
                    if (intent.getStringExtra(NowplayingFragment.ACTIVITY_NAME).equals(NowplayingFragment.TAG)){
                        movie_poster.setTransitionName(getString(R.string.transition_photo));
                    }else {
                        movie_poster.setTransitionName(getString(R.string.similar_transition_photo));
                    }
                }
            }

            if (intent!=null && intent.hasExtra("Id") && intent.hasExtra("ImageURL")){
                id = intent.getIntExtra("Id",0);
                imageURLPoster = intent.getStringExtra("ImageURL");
                Picasso.with(this).load(ApiClient.IMAGE_URL+imageURLPoster).into(movie_poster);
                getMovieData(id);
            }

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                movie_poster.setTransitionName(savedInstanceState.getString(SAVE_TRANSITION_NAME));
            }
            imageURLPoster = savedInstanceState.getString(SAVE_IMAGEURL_POSTER);
            imageURLBack = savedInstanceState.getString(SAVE_IMAGEURL_BACK);
            Picasso.with(this).load(ApiClient.IMAGE_URL + imageURLPoster).into(movie_poster);
            id = savedInstanceState.getInt(SAVE_ID);
            tv_date.setText(savedInstanceState.getString(SAVE_DATE));
            tv_title.setText(savedInstanceState.getString(SAVE_TITLE));
            tv_genre.setText(savedInstanceState.getString(SAVE_GENRE));
            Picasso.with(this).load(ApiClient.IMAGE_URL + imageURLBack).into(movieImage);
        }

            adapter =  new MovieDetailViewAdapter(getSupportFragmentManager(),Titles,Numboftabs,id);
            pager.setAdapter(adapter);
            tabs.setupWithViewPager(pager);

    }

    public void getMovieData(int id){
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
                    StringBuilder genres = new StringBuilder();
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        int id = jsonObject1.getInt("id");
                        String name = jsonObject1.getString("name");
                        genres.append(name).append(", ");
                    }
                    genres.deleteCharAt(genres.length()-2);

                    movieDetailObject.setGenresName(genres.toString());

                    JSONArray jsonArray1 = jsonObject.getJSONArray("production_companies");
                    String company=null;
                    for (int i=0;i<jsonArray1.length();i++){
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        int id = jsonObject1.getInt("id");
                        String name = jsonObject1.getString("name");
                        company = company +", "+ name;
                    }

                    if (movieDetailObject.getBackdropPath()!=null){
                        imageURLBack = movieDetailObject.getBackdropPath();
                        Picasso.with(getApplicationContext()).load(ApiClient.IMAGE_URL+imageURLBack).into(movieImage);
                    }

                    String[] date = movieDetailObject.getReleaseDate().split("-");
                    int hour = movieDetailObject.getRuntime()/60;
                    int minute = movieDetailObject.getRuntime()%60;

                    tv_date.setText(date[0]+" \u25CF "+hour+" hr "+minute+" min");
                    tv_title.setText(movieDetailObject.getTitle());

                    if (movieDetailObject.getGenresName()!=null){
                        tv_genre.setText(movieDetailObject.getGenresName());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset > -800)
        {
           tabs.setPadding(0,0,0,0);
        }
        else
        {
            if (Build.VERSION.SDK_INT >= 21) {

                // Set the status bar to dark-semi-transparentish
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                // Set paddingTop of toolbar to height of status bar.
                // Fixes statusbar covers toolbar issue
                tabs.setPadding(0, getStatusBarHeight(), 0, 0);
            }
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SAVE_IMAGEURL_POSTER,imageURLPoster);
        outState.putString(SAVE_IMAGEURL_BACK,imageURLBack);
        outState.putInt(SAVE_ID,id);
        outState.putString(SAVE_DATE,tv_date.getText().toString());
        outState.putString(SAVE_TITLE,tv_title.getText().toString());
        outState.putString(SAVE_GENRE,tv_genre.getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outState.putString(SAVE_TRANSITION_NAME,movie_poster.getTransitionName());
        }
        super.onSaveInstanceState(outState);
    }
}

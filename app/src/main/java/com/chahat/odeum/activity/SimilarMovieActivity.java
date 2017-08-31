package com.chahat.odeum.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chahat.odeum.R;
import com.chahat.odeum.adapter.MovieAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.MovieObject;
import com.chahat.odeum.object.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.odeum.BuildConfig.API_KEY;

public class SimilarMovieActivity extends AppCompatActivity implements MovieAdapter.LoadListner,MovieAdapter.OnItemClickListner {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.noResult) LinearLayout noResultLayout;
    private MovieAdapter movieAdapter;
    public static final String ACTIVITY_NAME = "activityname";
    public static final String TAG = "TransitionPhoto";
    private int id;
    private static final String SAVE_ID = "id";
    private static final String SAVE_RECYCLER_STATE = "recyclerState";
    private static final String SAVE_LIST = "list";
    private static final String SAVEINSTANCE_PAGES = "pages";
    private static final String SAVEINSTANCE_CURRENT_PAGE = "page";
    private Parcelable mRecyclerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_movie);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.similar_movies));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        movieAdapter = new MovieAdapter(this,this,this);
        recyclerView.setAdapter(movieAdapter);

        if (savedInstanceState==null){
            Intent intent = getIntent();

            if (intent!=null && intent.hasExtra("Id")){
                id = intent.getIntExtra("Id",1);
                getSimilarMovies(1);
            }
        }else {
            id = savedInstanceState.getInt(SAVE_ID);
            mRecyclerState = savedInstanceState.getParcelable(SAVE_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
            movieAdapter.setMovieList((ArrayList)savedInstanceState.getParcelableArrayList(SAVE_LIST));
            movieAdapter.setTotalPages(savedInstanceState.getInt(SAVEINSTANCE_PAGES));
            movieAdapter.setCurrentPage(savedInstanceState.getInt(SAVEINSTANCE_CURRENT_PAGE));
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putInt(SAVE_ID,id);
        outState.putParcelable(SAVE_RECYCLER_STATE,mRecyclerState);
        outState.putParcelableArrayList(SAVE_LIST, (ArrayList<? extends Parcelable>) movieAdapter.getMovieList());
        outState.putParcelable(SAVE_RECYCLER_STATE,mRecyclerState);
        outState.putInt(SAVEINSTANCE_PAGES,movieAdapter.getTotalPages());
        outState.putInt(SAVEINSTANCE_CURRENT_PAGE,movieAdapter.getCurrentPage());
    }

    private void getSimilarMovies(final int page){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiInterface.getSimilarMovie(id,API_KEY,page);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieObject> list = response.body().getMovieList();
                if (list.size()!=0){
                    showResult();
                    if (page==1){
                        movieAdapter.setMovieList(list);
                        movieAdapter.setCurrentPage(1);
                    }else if (page<=response.body().getTotalPages()){
                        movieAdapter.addMovieList(list);
                    }
                    movieAdapter.setTotalPages(response.body().getTotalPages());
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void showError(){
        recyclerView.setVisibility(View.GONE);
        noResultLayout.setVisibility(View.VISIBLE);
    }

    private void showResult(){
        recyclerView.setVisibility(View.VISIBLE);
        noResultLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int id, ImageView sharedView,String imageUrl) {
        Bundle bundle = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedView.setTransitionName(getString(R.string.transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(this,sharedView,sharedView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageUrl);
        intent.putExtra(ACTIVITY_NAME,TAG);
        startActivity(intent,bundle);
    }

    @Override
    public void loadMorePages(int page) {
        getSimilarMovies(page);
    }
}

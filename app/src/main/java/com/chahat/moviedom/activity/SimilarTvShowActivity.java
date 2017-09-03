package com.chahat.moviedom.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chahat.moviedom.Interface.LoadPagesInterface;
import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.adapter.TvShowAdapter;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.api.ApiInterface;
import com.chahat.moviedom.fragment.AiringTodayFragment;
import com.chahat.moviedom.object.TvShowObject;
import com.chahat.moviedom.object.TvShowResponse;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.moviedom.BuildConfig.API_KEY;
import static com.chahat.moviedom.utils.Constants.INTENT_ACTIVITY;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_CURRENT_PAGE;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_ID;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_LIST;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_PAGES;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_RECYCLERSTATE;

public class SimilarTvShowActivity extends AppCompatActivity implements SharedItemClickListner,LoadPagesInterface{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.noResult)
    LinearLayout noResultLayout;
    @BindView(R.id.adView)
    AdView adView;
    private TvShowAdapter tvShowAdapter;
    private int id;
    private Parcelable mRecyclerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_tv_show);
        ButterKnife.bind(this);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        toolbar.setTitle(getResources().getString(R.string.similar_tv_shows));
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
        tvShowAdapter = new TvShowAdapter(this,this,this);
        recyclerView.setAdapter(tvShowAdapter);

        if (savedInstanceState==null){
            Intent intent = getIntent();

            if (intent!=null && intent.hasExtra("Id")){
                id = intent.getIntExtra("Id",1);
                getSimilarTvShows(1);
            }
        }else {
            id = savedInstanceState.getInt(SAVEINSTANCE_ID);
            mRecyclerState = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
            tvShowAdapter.setTvShowList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST));
            tvShowAdapter.setTotalPages(savedInstanceState.getInt(SAVEINSTANCE_PAGES));
            tvShowAdapter.setCurrentPage(savedInstanceState.getInt(SAVEINSTANCE_CURRENT_PAGE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putInt(SAVEINSTANCE_ID,id);
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE,mRecyclerState);
        outState.putParcelableArrayList(SAVEINSTANCE_LIST, (ArrayList<? extends Parcelable>) tvShowAdapter.getTvShowList());
        outState.putInt(SAVEINSTANCE_PAGES,tvShowAdapter.getTotalPages());
        outState.putInt(SAVEINSTANCE_CURRENT_PAGE,tvShowAdapter.getCurrentPage());
    }

    private void getSimilarTvShows(final int page){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowResponse> call = apiInterface.getSimilarTvShow(id,API_KEY,page);

        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                List<TvShowObject> list = response.body().getResults();
                if (list.size()!=0){
                    showResult();
                    if (page==1){
                        tvShowAdapter.setTvShowList(list);
                        tvShowAdapter.setCurrentPage(1);
                    }else if (page<=response.body().getTotalPages()){
                        tvShowAdapter.addTvShowList(list);
                    }
                    tvShowAdapter.setTotalPages(response.body().getTotalPages());
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
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
    public void loadPage(int page) {
        getSimilarTvShows(page);
    }

    @Override
    public void onItemClick(int id, ImageView imageView, String imageURL) {
        Bundle bundle = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(this,imageView,imageView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(this, TvShowDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(INTENT_ACTIVITY, AiringTodayFragment.TAG);
        startActivity(intent,bundle);
    }
}

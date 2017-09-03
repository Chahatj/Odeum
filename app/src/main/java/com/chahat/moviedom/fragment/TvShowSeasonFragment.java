package com.chahat.moviedom.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chahat.moviedom.R;
import com.chahat.moviedom.adapter.TvShowSeasonAdapter;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.api.ApiInterface;
import com.chahat.moviedom.object.EpisodeObject;
import com.chahat.moviedom.object.SeasonObject;
import com.chahat.moviedom.object.TvSeasonObject;
import com.chahat.moviedom.object.TvShowDetailObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.moviedom.BuildConfig.API_KEY;
import static com.chahat.moviedom.utils.Constants.INSTANCE_ID;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_ID;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_LIST;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_RECYCLERSTATE;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowSeasonFragment extends Fragment {

    private int id;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.noResult) LinearLayout noResultLayout;
    private TvShowSeasonAdapter showSeasonAdapter;
    private Parcelable mRecyclerState;

    public static TvShowSeasonFragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        TvShowSeasonFragment tvShowSeasonFragment = new TvShowSeasonFragment();
        tvShowSeasonFragment.setArguments(bundle);
        return tvShowSeasonFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            id = getArguments().getInt(INSTANCE_ID);
        }else {
            id= savedInstanceState.getInt(SAVEINSTANCE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show_season,container,false);
        ButterKnife.bind(this,view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        showSeasonAdapter = new TvShowSeasonAdapter(getContext());
        recyclerView.setAdapter(showSeasonAdapter);

        if (savedInstanceState==null) {
            getSeasonDetail(id);
        }else {
            mRecyclerState = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE);
            showSeasonAdapter.setEpisodeList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST));
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE,mRecyclerState);
        outState.putInt(SAVEINSTANCE_ID,id);
        outState.putParcelableArrayList(SAVEINSTANCE_LIST, (ArrayList<? extends Parcelable>) showSeasonAdapter.getEpisodeList());
    }

    private void getSeasonDetail(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowDetailObject> call = apiInterface.getTvShowDetail(id,API_KEY);
        call.enqueue(new Callback<TvShowDetailObject>() {
            @Override
            public void onResponse(Call<TvShowDetailObject> call, Response<TvShowDetailObject> response) {

                List<TvSeasonObject> seasonList = response.body().getTvSeasonList();

                getSeasonEpisodes(seasonList);
            }

            @Override
            public void onFailure(Call<TvShowDetailObject> call, Throwable t) {

            }
        });
    }

    private void getSeasonEpisodes(List<TvSeasonObject> seasonList){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        for (final TvSeasonObject season:seasonList){
                Call<SeasonObject> call = apiInterface.getSeasonList(id,season.getSeasonNumber(),API_KEY);
                call.enqueue(new Callback<SeasonObject>() {
                    @Override
                    public void onResponse(Call<SeasonObject> call, Response<SeasonObject> response) {
                        if (response!=null){
                            List<EpisodeObject> episodeList = response.body().getEpisodeList();
                            if (episodeList!=null && episodeList.size()!=0){
                                showResult();
                                showSeasonAdapter.addEpisodeList(episodeList);
                            }else {
                                showError();
                            }
                        }else {
                            showError();
                        }

                    }

                    @Override
                    public void onFailure(Call<SeasonObject> call, Throwable t) {
                        showError();
                    }
                });
            }
    }

    private void showError(){
        recyclerView.setVisibility(View.GONE);
        noResultLayout.setVisibility(View.VISIBLE);
    }

    private void showResult(){
        recyclerView.setVisibility(View.VISIBLE);
        noResultLayout.setVisibility(View.GONE);
    }
}

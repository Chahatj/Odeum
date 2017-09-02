package com.chahat.odeum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chahat.odeum.R;
import com.chahat.odeum.adapter.TvShowSeasonAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.EpisodeObject;
import com.chahat.odeum.object.SeasonObject;
import com.chahat.odeum.object.TvSeasonObject;
import com.chahat.odeum.object.TvShowDetailObject;
import com.chahat.odeum.object.TvShowResponse;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.odeum.BuildConfig.API_KEY;
import static com.chahat.odeum.utils.Constants.INSTANCE_ID;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowSeasonFragment extends Fragment {

    private int id;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private TvShowSeasonAdapter showSeasonAdapter;

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

        getSeasonDetail(id);

        return view;
    }

    private void getSeasonDetail(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowDetailObject> call = apiInterface.getTvShowDetail(id,API_KEY);
        call.enqueue(new Callback<TvShowDetailObject>() {
            @Override
            public void onResponse(Call<TvShowDetailObject> call, Response<TvShowDetailObject> response) {

                int numberOfSeason = response.body().getNumberOfSeasons();
                List<TvSeasonObject> seasonList = response.body().getTvSeasonList();

                getSeasonEpisodes(numberOfSeason,seasonList);
            }

            @Override
            public void onFailure(Call<TvShowDetailObject> call, Throwable t) {

            }
        });
    }

    private void getSeasonEpisodes(int numberOfSeasons,List<TvSeasonObject> seasonList){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        for (final TvSeasonObject season:seasonList){
            Log.d("TAG","Season: "+season.getSeasonNumber());
                Call<SeasonObject> call = apiInterface.getSeasonList(id,season.getSeasonNumber(),API_KEY);
                call.enqueue(new Callback<SeasonObject>() {
                    @Override
                    public void onResponse(Call<SeasonObject> call, Response<SeasonObject> response) {
                            List<EpisodeObject> episodeList = response.body().getEpisodeList();
                            showSeasonAdapter.addEpisodeList(episodeList);
                            Log.d("TAG",episodeList.get(0).getName());
                    }

                    @Override
                    public void onFailure(Call<SeasonObject> call, Throwable t) {
                        Log.d("TAG",t.toString());
                    }
                });
            }
    }
}

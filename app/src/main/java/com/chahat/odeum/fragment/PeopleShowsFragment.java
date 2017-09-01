package com.chahat.odeum.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chahat.odeum.Interface.SharedItemClickListner;
import com.chahat.odeum.R;
import com.chahat.odeum.adapter.MovieAdapter;
import com.chahat.odeum.adapter.TvShowAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.PeopleMovieResponse;
import com.chahat.odeum.object.PeopleTvShowResponse;
import com.chahat.odeum.object.TvShowResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.odeum.BuildConfig.API_KEY;
import static com.chahat.odeum.utils.Constants.SAVEINSTANCE_CURRENT_PAGE;
import static com.chahat.odeum.utils.Constants.SAVEINSTANCE_ID;
import static com.chahat.odeum.utils.Constants.SAVEINSTANCE_LIST;
import static com.chahat.odeum.utils.Constants.SAVEINSTANCE_PAGES;
import static com.chahat.odeum.utils.Constants.SAVEINSTANCE_RECYCLERSTATE;

/**
 * Created by chahat on 1/9/17.
 */

public class PeopleShowsFragment extends Fragment implements SharedItemClickListner {

    private int id;
    private static final String INSTANCE_ID = "id";
    private TvShowAdapter tvShowAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Parcelable mRecyclerState;

    public static PeopleShowsFragment newInstance(int id){
        PeopleShowsFragment peopleShowsFragment = new PeopleShowsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        peopleShowsFragment.setArguments(bundle);
        return peopleShowsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            id = getArguments().getInt(INSTANCE_ID);
        }else {
            id = savedInstanceState.getInt(SAVEINSTANCE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_people_shows,container,false);

        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        tvShowAdapter = new TvShowAdapter(getContext(),null,this);
        tvShowAdapter.setCurrentPage(1);
        tvShowAdapter.setTotalPages(1);
        recyclerView.setAdapter(tvShowAdapter);

        if (savedInstanceState==null) {
            getPeopleTvShowList(id);
        }else {
            tvShowAdapter.setTvShowList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST));
            mRecyclerState = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE,mRecyclerState);
        outState.putParcelableArrayList(SAVEINSTANCE_LIST,(ArrayList<? extends Parcelable>) tvShowAdapter.getTvShowList());
        outState.putInt(SAVEINSTANCE_ID,id);
    }

    private void getPeopleTvShowList(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PeopleTvShowResponse> call = apiInterface.getPeopleTvShowList(id,API_KEY);
        call.enqueue(new Callback<PeopleTvShowResponse>() {
            @Override
            public void onResponse(Call<PeopleTvShowResponse> call, Response<PeopleTvShowResponse> response) {
                tvShowAdapter.setTvShowList(response.body().getPeopleTvShowList());
            }

            @Override
            public void onFailure(Call<PeopleTvShowResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(int id, ImageView imageView, String imageURL) {

    }
}

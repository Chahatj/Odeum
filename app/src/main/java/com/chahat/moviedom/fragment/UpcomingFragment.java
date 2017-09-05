package com.chahat.moviedom.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chahat.moviedom.Interface.LoadPagesInterface;
import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.activity.MovieDetailActivity;
import com.chahat.moviedom.adapter.UpcomingMovieAdapter;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.api.ApiInterface;
import com.chahat.moviedom.object.MovieObject;
import com.chahat.moviedom.object.MovieResponse;
import com.chahat.moviedom.utils.NetworkConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.moviedom.BuildConfig.API_KEY;

/**
 * Created by chahat on 24/8/17.
 */

public class UpcomingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,LoadPagesInterface,SharedItemClickListner {

    private SwipeRefreshLayout swipeRefreshLayout;
    private UpcomingMovieAdapter movieAdapter;
    private Parcelable mRecyclerState;
    private static final String SAVEINSTANCE_RECYCLERSTATE = "RecyclerState";
    private static final String SAVEINSTANCE_LIST = "movielist";
    private static final String SAVEINSTANCE_PAGES = "pages";
    private static final String SAVEINSTANCE_CURRENT_PAGE = "page";
    private RecyclerView recyclerView;
    private static final String ACTIVITY_NAME = "activityname";
    private static final String TAG = "TransitionPhoto";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_upcoming,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        movieAdapter = new UpcomingMovieAdapter(getContext(),this,this);
        recyclerView.setAdapter(movieAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        if (savedInstanceState ==null){
            if (NetworkConnection.checkNetworkConnectivity(getContext())){
                fetchData(1);
            }
        }else {
            mRecyclerState = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE);
            movieAdapter.setMovieList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST));
            movieAdapter.setTotalPages(savedInstanceState.getInt(SAVEINSTANCE_PAGES));
            movieAdapter.setCurrentPage(savedInstanceState.getInt(SAVEINSTANCE_CURRENT_PAGE));
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    private void fetchData(final int page){
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiInterface.getUpcomingMovies(API_KEY,page,"US");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieObject> movies = response.body().getMovieList();
                if (page==1){
                    movieAdapter.setMovieList(movies);
                    movieAdapter.setCurrentPage(1);
                }else if (page<=response.body().getTotalPages()){
                    movieAdapter.addMovieList(movies);
                }
                movieAdapter.setTotalPages(response.body().getTotalPages());
                Log.d("UpcomingFragment",response.body().getTotalPages()+"");
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("UpcomingFragment",t.toString());
            }
        });

    }

    @Override
    public void onRefresh() {
        fetchData(1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE,mRecyclerState);
        outState.putInt(SAVEINSTANCE_PAGES,movieAdapter.getTotalPages());
        outState.putInt(SAVEINSTANCE_CURRENT_PAGE,movieAdapter.getCurrentPage());
        outState.putParcelableArrayList(SAVEINSTANCE_LIST,(ArrayList<? extends Parcelable>) movieAdapter.getMovieList());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(int id, ImageView sharedView,String imageURL) {
        Bundle bundle = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedView.setTransitionName(getString(R.string.transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),sharedView,sharedView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(ACTIVITY_NAME,TAG);
        startActivity(intent,bundle);
    }

    @Override
    public void loadPage(int page) {
        fetchData(page);
    }
}

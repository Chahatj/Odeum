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
import com.chahat.moviedom.adapter.MovieReviewAdapter;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.api.ApiInterface;
import com.chahat.moviedom.object.MovieReviewResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.moviedom.BuildConfig.API_KEY;

/**
 * Created by chahat on 25/8/17.
 */

public class ReviewFragment extends Fragment {

    private static final String TAG = "ReviewFragment";
    private int id;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.noResult)
    LinearLayout noResultLayout;
    private MovieReviewAdapter reviewAdapter;
    private static final String SAVE_ID = "id";
    private static final String SAVE_LIST = "list";
    private static final String SAVE_RECYCLER_STATE = "recyclerState";
    private Parcelable mRecyclerState;

    public static ReviewFragment newInstance(int id){
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG,id);
        reviewFragment.setArguments(bundle);
        return reviewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review,container,false);
        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        reviewAdapter= new MovieReviewAdapter(getContext());
        recyclerView.setAdapter(reviewAdapter);

        if (savedInstanceState==null){
            getMovieReview(id);
        }else {
            reviewAdapter.setReviewList((ArrayList)savedInstanceState.getParcelableArrayList(SAVE_LIST));
            mRecyclerState = savedInstanceState.getParcelable(SAVE_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState==null){
            id = getArguments().getInt(TAG);
        }else {
            id = savedInstanceState.getInt(SAVE_ID);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putInt(SAVE_ID,id);
        outState.putParcelableArrayList(SAVE_LIST, (ArrayList<? extends Parcelable>) reviewAdapter.getReviewList());
        outState.putParcelable(SAVE_RECYCLER_STATE,mRecyclerState);
    }

    private void getMovieReview(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieReviewResponse> call = apiInterface.getMovieReview(id,API_KEY);

        call.enqueue(new Callback<MovieReviewResponse>() {
            @Override
            public void onResponse(Call<MovieReviewResponse> call, Response<MovieReviewResponse> response) {
                if (response!=null){
                    MovieReviewResponse movieReviewResponse = response.body();
                    if (movieReviewResponse.getReviewList()!=null && movieReviewResponse.getReviewList().size()!=0){
                        showResult();
                        reviewAdapter.setReviewList(movieReviewResponse.getReviewList());
                    }else {
                        showError();
                    }
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<MovieReviewResponse> call, Throwable t) {

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
}

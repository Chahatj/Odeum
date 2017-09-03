package com.chahat.moviedom.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
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
import android.widget.LinearLayout;

import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.activity.PeopleDetailActivity;
import com.chahat.moviedom.adapter.CastAdapter;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.api.ApiInterface;
import com.chahat.moviedom.object.CastObject;
import com.chahat.moviedom.object.CastResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.moviedom.BuildConfig.API_KEY;
import static com.chahat.moviedom.utils.Constants.INTENT_ACTIVITY;

/**
 * Created by chahat on 25/8/17.
 */

public class CastFragment extends Fragment implements SharedItemClickListner {

    private static final String TAG = "CastFragment";
    private int id;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.noResult) LinearLayout noResultLayout;
    private CastAdapter movieCastAdapter;
    private static final String SAVE_ID = "id";
    private static final String SAVE_LIST = "list";
    private static final String SAVE_RECYCLER_STATE = "recyclerstate";
    private Parcelable mRecyclerState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast,container,false);
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        movieCastAdapter = new CastAdapter(getContext(),this);
        recyclerView.setAdapter(movieCastAdapter);

        if (savedInstanceState==null){
            getMovieCast(id);
        }else {
            movieCastAdapter.setCastList((ArrayList)savedInstanceState.getParcelableArrayList(SAVE_LIST));
            mRecyclerState = savedInstanceState.getParcelable(SAVE_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    public static CastFragment newInstance(int id){
        CastFragment castFragment = new CastFragment();
        Bundle bundle= new Bundle();
        bundle.putInt(TAG,id);
        castFragment.setArguments(bundle);
        return castFragment;
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
        outState.putParcelableArrayList(SAVE_LIST, (ArrayList<? extends Parcelable>) movieCastAdapter.getCastList());
        outState.putParcelable(SAVE_RECYCLER_STATE,mRecyclerState);
    }

    private void getMovieCast(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CastResponse> call = apiInterface.getMovieCast(id,API_KEY);

        call.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if (response!=null) {
                    List<CastObject> castList = response.body().getCastList();
                    if (castList != null && castList.size() != 0) {
                        showResult();
                        movieCastAdapter.setCastList(castList);
                    } else {
                        showError();
                    }
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
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
    public void onItemClick(int id, ImageView imageView, String imageURL) {
        Bundle bundle = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.people_movies_transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,imageView.getTransitionName()).toBundle();
        }
        Intent intent  = new Intent(getContext(), PeopleDetailActivity.class);
        intent.putExtra(PopularPeopleFragment.INTENT_ID,id);
        intent.putExtra(PopularPeopleFragment.INTENT_IMAGE,imageURL);
        intent.putExtra(INTENT_ACTIVITY,TAG);
        startActivity(intent,bundle);
    }
}

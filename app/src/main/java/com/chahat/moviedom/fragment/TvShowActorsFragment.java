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
import static com.chahat.moviedom.utils.Constants.INSTANCE_ID;
import static com.chahat.moviedom.utils.Constants.INTENT_ACTIVITY;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_ID;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_LIST;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_RECYCLERSTATE;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowActorsFragment extends Fragment implements SharedItemClickListner{

    private int id;
    private static final String TAG = "CastFragment";
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.noResult) LinearLayout noResultLayout;
    private CastAdapter castAdapter;
    private Parcelable mRecyclerState;

    public static TvShowActorsFragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        TvShowActorsFragment tvShowActorsFragment = new TvShowActorsFragment();
        tvShowActorsFragment.setArguments(bundle);
        return tvShowActorsFragment;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putInt(SAVEINSTANCE_ID,id);
        outState.putParcelableArrayList(SAVEINSTANCE_LIST, (ArrayList<? extends Parcelable>) castAdapter.getCastList());
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE,mRecyclerState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show_actor,container,false);
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        castAdapter = new CastAdapter(getContext(),this);
        recyclerView.setAdapter(castAdapter);

        if (savedInstanceState==null){
            getTvShowCast(id);
        }else {
            castAdapter.setCastList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST));
            mRecyclerState = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }
        return view;
    }

    private void getTvShowCast(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CastResponse> call = apiInterface.getCastList(id,API_KEY);

        call.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {

                List<CastObject> castList = response.body().getCastList();
                if (castList!=null && castList.size()!=0) {
                    showResult();
                    castAdapter.setCastList(castList);
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

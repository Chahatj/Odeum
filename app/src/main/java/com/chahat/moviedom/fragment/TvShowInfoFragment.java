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
import android.widget.TextView;

import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.activity.SimilarTvShowActivity;
import com.chahat.moviedom.activity.TvShowDetailActivity;
import com.chahat.moviedom.adapter.SimilarTvShowAdapter;
import com.chahat.moviedom.adapter.VideoAdapter;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.api.ApiInterface;
import com.chahat.moviedom.object.TvShowCreatedByObject;
import com.chahat.moviedom.object.TvShowDetailObject;
import com.chahat.moviedom.object.TvShowNetworksObject;
import com.chahat.moviedom.object.TvShowResponse;
import com.chahat.moviedom.object.VideoResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.moviedom.BuildConfig.API_KEY;
import static com.chahat.moviedom.utils.Constants.INSTANCE_ID;
import static com.chahat.moviedom.utils.Constants.INTENT_ACTIVITY;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_CREATEDBY;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_FIRST_AIR_DATE;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_ID;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_LAST_AIR_DATE;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_LIST_SIMILAR;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_LIST_TRAILER;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_NETWORKS;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_OVERVIEW;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_RATING;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_RECYCLERSTATE_SIMILAR;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_RECYCLERSTATE_TRAILER;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_SHOWSTATUS;
import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_SHOWTYPE;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowInfoFragment extends Fragment implements SharedItemClickListner,View.OnClickListener {

    private int id;
    @BindView(R.id.tv_rating) TextView textViewRating;
    @BindView(R.id.tv_overview) TextView textViewOverview;
    @BindView(R.id.tv_first_air_date) TextView textViewFirstAirDate;
    @BindView(R.id.tv_last_air_date) TextView textViewLastAirDate;
    @BindView(R.id.tv_networks) TextView textViewNetworks;
    @BindView(R.id.tv_createdBy) TextView textViewCreatedBy;
    @BindView(R.id.tv_show_type) TextView textViewShowType;
    @BindView(R.id.tv_status) TextView textViewStatus;
    @BindView(R.id.recyclerViewTrailer) RecyclerView recyclerViewTrailer;
    @BindView(R.id.recyclerViewSimilarShow) RecyclerView recyclerViewSimilarShow;
    @BindView(R.id.textViewAll) TextView textViewAll;
    @BindView(R.id.noResult) LinearLayout noResultLayout;
    @BindView(R.id.noResultSimilar) LinearLayout noResultSimilar;
    private VideoAdapter videoAdapter;
    private SimilarTvShowAdapter tvShowAdapter;
    public static final String TAG = "TvShowInfoFragment";
    private Parcelable mRecyclerStateTvShows,mRecyclerStateTrailor;

    public static TvShowInfoFragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        TvShowInfoFragment tvShowInfoFragment = new TvShowInfoFragment();
        tvShowInfoFragment.setArguments(bundle);
        return tvShowInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show_info,container,false);
        ButterKnife.bind(this,view);
        textViewAll.setOnClickListener(this);

        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewTrailer.setNestedScrollingEnabled(false);
        videoAdapter = new VideoAdapter(getContext());
        recyclerViewTrailer.setAdapter(videoAdapter);

        recyclerViewSimilarShow.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewSimilarShow.setNestedScrollingEnabled(false);
        tvShowAdapter = new SimilarTvShowAdapter(getContext(),this);
        recyclerViewSimilarShow.setAdapter(tvShowAdapter);

        if (savedInstanceState==null){
            getTvShowInfo(id);
            getTvShowVideo(id);
            getSimilarTvShow(id);
        }else {
            videoAdapter.setVideoList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST_TRAILER));
            tvShowAdapter.setTvShowList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST_SIMILAR));
            mRecyclerStateTvShows = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE_SIMILAR);
            mRecyclerStateTrailor = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE_TRAILER);
            recyclerViewTrailer.getLayoutManager().onRestoreInstanceState(mRecyclerStateTrailor);
            recyclerViewSimilarShow.getLayoutManager().onRestoreInstanceState(mRecyclerStateTvShows);
            textViewRating.setText(savedInstanceState.getString(SAVEINSTANCE_RATING));
            textViewOverview.setText(savedInstanceState.getString(SAVEINSTANCE_OVERVIEW));
            textViewShowType.setText(savedInstanceState.getString(SAVEINSTANCE_SHOWTYPE));
            textViewStatus.setText(savedInstanceState.getString(SAVEINSTANCE_SHOWSTATUS));
            textViewNetworks.setText(savedInstanceState.getString(SAVEINSTANCE_NETWORKS));
            textViewCreatedBy.setText(savedInstanceState.getString(SAVEINSTANCE_CREATEDBY));
            textViewFirstAirDate.setText(savedInstanceState.getString(SAVEINSTANCE_FIRST_AIR_DATE));
            textViewLastAirDate.setText(savedInstanceState.getString(SAVEINSTANCE_LAST_AIR_DATE));
        }

        return view;
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
        mRecyclerStateTvShows = recyclerViewSimilarShow.getLayoutManager().onSaveInstanceState();
        mRecyclerStateTrailor = recyclerViewTrailer.getLayoutManager().onSaveInstanceState();
        outState.putInt(SAVEINSTANCE_ID,id);
        outState.putString(SAVEINSTANCE_RATING,textViewRating.getText().toString());
        outState.putString(SAVEINSTANCE_OVERVIEW,textViewOverview.getText().toString());
        outState.putString(SAVEINSTANCE_FIRST_AIR_DATE,textViewFirstAirDate.getText().toString());
        outState.putString(SAVEINSTANCE_LAST_AIR_DATE,textViewLastAirDate.getText().toString());
        outState.putString(SAVEINSTANCE_SHOWTYPE,textViewShowType.getText().toString());
        outState.putString(SAVEINSTANCE_SHOWSTATUS,textViewStatus.getText().toString());
        outState.putString(SAVEINSTANCE_NETWORKS,textViewNetworks.getText().toString());
        outState.putString(SAVEINSTANCE_CREATEDBY,textViewCreatedBy.getText().toString());
        outState.putParcelableArrayList(SAVEINSTANCE_LIST_TRAILER, (ArrayList<? extends Parcelable>) videoAdapter.getVideoList());
        outState.putParcelableArrayList(SAVEINSTANCE_LIST_SIMILAR, (ArrayList<? extends Parcelable>) tvShowAdapter.getTvShowList());
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE_TRAILER,mRecyclerStateTrailor);
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE_SIMILAR,mRecyclerStateTvShows);
    }

    private void getTvShowInfo(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowDetailObject> call = apiInterface.getTvShowDetail(id,API_KEY);
        call.enqueue(new Callback<TvShowDetailObject>() {
            @Override
            public void onResponse(Call<TvShowDetailObject> call, Response<TvShowDetailObject> response) {
                TvShowDetailObject tvShowDetailObject = response.body();

                textViewRating.setText(String.valueOf(tvShowDetailObject.getVoteAverage()));
                textViewOverview.setText(tvShowDetailObject.getOverview());
                textViewShowType.setText(tvShowDetailObject.getType());
                textViewStatus.setText(tvShowDetailObject.getStatus());

                if (tvShowDetailObject.getNetworkList().size()!=0){
                    StringBuilder network = new StringBuilder();
                    for (TvShowNetworksObject networksObject:tvShowDetailObject.getNetworkList()){
                        network.append(networksObject.getName()).append(", ");
                    }
                    textViewNetworks.setText(network.deleteCharAt(network.length()-2).toString());
                }


                if (tvShowDetailObject.getCreatedByList().size()!=0){
                    StringBuilder created = new StringBuilder();
                    for(TvShowCreatedByObject createdBy : tvShowDetailObject.getCreatedByList()){
                        created.append(createdBy.getName()).append(", ");
                    }
                    textViewCreatedBy.setText(created.deleteCharAt(created.length()-2).toString());
                }


                try {
                    Date d = new SimpleDateFormat("yyyy-MM-dd").parse(tvShowDetailObject.getFirstAirDate());
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(d);
                    textViewFirstAirDate.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+calendar.get(Calendar.DAY_OF_MONTH)+", "+calendar.get(Calendar.YEAR));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    Date d = new SimpleDateFormat("yyyy-MM-dd").parse(tvShowDetailObject.getLastAirDate());
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(d);
                    textViewLastAirDate.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+calendar.get(Calendar.DAY_OF_MONTH)+", "+calendar.get(Calendar.YEAR));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TvShowDetailObject> call, Throwable t) {

            }
        });
    }

    private void getTvShowVideo(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VideoResponse> call = apiInterface.getVideos(id,API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.body().getVideoList().size()!=0){
                    showResult();
                    videoAdapter.setVideoList(response.body().getVideoList());
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
            }
        });
    }

    private void getSimilarTvShow(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowResponse> call = apiInterface.getSimilarTvShow(id,API_KEY,1);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.body().getResults()!=null && response.body().getResults().size()!=0){
                    showResultSimilar();
                    tvShowAdapter.setTvShowList(response.body().getResults());
                }else {
                    showErrorSimilar();
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }

    private void showError(){
        recyclerViewTrailer.setVisibility(View.GONE);
        noResultLayout.setVisibility(View.VISIBLE);
    }

    private void showResult(){
        recyclerViewTrailer.setVisibility(View.VISIBLE);
        noResultLayout.setVisibility(View.GONE);
    }

    private void showErrorSimilar(){
        recyclerViewSimilarShow.setVisibility(View.GONE);
        noResultSimilar.setVisibility(View.VISIBLE);
    }

    private void showResultSimilar(){
        recyclerViewSimilarShow.setVisibility(View.VISIBLE);
        noResultSimilar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int id, ImageView imageView, String imageURL) {
        Bundle bundle = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.similar_transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,imageView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(INTENT_ACTIVITY,TAG);
        startActivity(intent,bundle);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), SimilarTvShowActivity.class);
        intent.putExtra("Id",id);
        startActivity(intent);
    }
}

package com.chahat.odeum.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chahat.odeum.Interface.SharedItemClickListner;
import com.chahat.odeum.R;
import com.chahat.odeum.activity.MovieDetailActivity;
import com.chahat.odeum.activity.TvShowDetailActivity;
import com.chahat.odeum.adapter.SimilarTvShowAdapter;
import com.chahat.odeum.adapter.TvShowAdapter;
import com.chahat.odeum.adapter.VideoAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.TvShowCreatedByObject;
import com.chahat.odeum.object.TvShowDetailObject;
import com.chahat.odeum.object.TvShowNetworksObject;
import com.chahat.odeum.object.TvShowResponse;
import com.chahat.odeum.object.VideoResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.odeum.BuildConfig.API_KEY;
import static com.chahat.odeum.utils.Constants.INSTANCE_ID;
import static com.chahat.odeum.utils.Constants.INTENT_ACTIVITY;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowInfoFragment extends Fragment implements SharedItemClickListner {

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
        getTvShowInfo(id);

        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewTrailer.setNestedScrollingEnabled(false);
        videoAdapter = new VideoAdapter(getContext());
        recyclerViewTrailer.setAdapter(videoAdapter);

        recyclerViewSimilarShow.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewSimilarShow.setNestedScrollingEnabled(false);
        tvShowAdapter = new SimilarTvShowAdapter(getContext(),this);
        recyclerViewSimilarShow.setAdapter(tvShowAdapter);

        getTvShowVideo(id);
        getSimilarTvShow(id);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            id = getArguments().getInt(INSTANCE_ID);
        }
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
}

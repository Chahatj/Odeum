package com.chahat.odeum.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.chahat.odeum.activity.SimilarMovieActivity;
import com.chahat.odeum.adapter.VideoAdapter;
import com.chahat.odeum.adapter.SimilarMovieAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.MovieDetailObject;
import com.chahat.odeum.object.MovieObject;
import com.chahat.odeum.object.MovieResponse;
import com.chahat.odeum.object.MovieVideoObject;
import com.chahat.odeum.object.VideoResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.odeum.BuildConfig.API_KEY;

/**
 * Created by chahat on 25/8/17.
 */

public class InfoFragment extends Fragment implements SharedItemClickListner,View.OnClickListener {

    private int id;
    private static final String TAG = "InfoFragment";
    @BindView(R.id.tv_rating) TextView textViewRating;
    @BindView(R.id.tv_overview) TextView textViewOverview;
    @BindView(R.id.tv_releaseDate) TextView textViewReleaseDate;
    @BindView(R.id.tv_producedBy) TextView textViewProducedBy;
    @BindView(R.id.tv_budget) TextView textViewBudget;
    @BindView(R.id.tv_revenue) TextView textViewRevenue;
    @BindView(R.id.recyclerViewTrailer) RecyclerView recyclerViewTrailer;
    @BindView(R.id.recyclerViewMovies) RecyclerView recyclerViewMovies;
    @BindView(R.id.textViewAll) TextView textViewAll;
    @BindView(R.id.noResult) LinearLayout noResultLayout;
    @BindView(R.id.noResultSimilar) LinearLayout noResultSimilar;
    private VideoAdapter movieVideoAdapter;
    private SimilarMovieAdapter similarMovieAdapter;
    public static final String ACTIVITY_NAME = "activityname";
    private static final String SAVE_ID = "id";
    private static final String SAVE_RATING = "rating";
    private static final String SAVE_OVERVIEW = "overview";
    private static final String SAVE_RELEASE_DATE = "releaseDate";
    private static final String SAVE_PRODUCED = "produced";
    private static final String SAVE_BUDGET = "budget";
    private static final String SAVE_REVENUE = "revenue";
    private static final String SAVE_RECYCLER_TRAILOR = "trailor";
    private static final String SAVE_RECYCLER_MOVIES = "movies";
    private static final String SAVE_STATE_MOVIES = "recyclerStateMovies";
    private static final String SAVE_STATE_TRAILOR = "recyclerStateTrailor";
    private Parcelable mRecyclerStateTrailor,mRecyclerStateMovies;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info,container,false);
        ButterKnife.bind(this,view);
        textViewAll.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewTrailer.setLayoutManager(layoutManager);
        recyclerViewTrailer.setNestedScrollingEnabled(false);
        movieVideoAdapter = new VideoAdapter(getContext());
        recyclerViewTrailer.setAdapter(movieVideoAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewMovies.setLayoutManager(linearLayoutManager);
        recyclerViewMovies.setNestedScrollingEnabled(false);
        similarMovieAdapter = new SimilarMovieAdapter(getContext(),this);
        recyclerViewMovies.setAdapter(similarMovieAdapter);

        if (savedInstanceState==null){
            getMovieInfo(id);
            getMovieTrailer(id);
            getSimilarMovie(id);
        }else {
            textViewRating.setText(savedInstanceState.getString(SAVE_RATING));
            textViewOverview.setText(savedInstanceState.getString(SAVE_OVERVIEW));
            textViewReleaseDate.setText(savedInstanceState.getString(SAVE_RELEASE_DATE));
            textViewProducedBy.setText(savedInstanceState.getString(SAVE_PRODUCED));
            textViewBudget.setText(savedInstanceState.getString(SAVE_BUDGET));
            textViewRevenue.setText(savedInstanceState.getString(SAVE_REVENUE));
            movieVideoAdapter.setVideoList((ArrayList)savedInstanceState.getParcelableArrayList(SAVE_RECYCLER_TRAILOR));
            similarMovieAdapter.setMovieList((ArrayList)savedInstanceState.getParcelableArrayList(SAVE_RECYCLER_MOVIES));
            mRecyclerStateTrailor = savedInstanceState.getParcelable(SAVE_STATE_TRAILOR);
            recyclerViewTrailer.getLayoutManager().onRestoreInstanceState(mRecyclerStateTrailor);
            mRecyclerStateMovies = savedInstanceState.getParcelable(SAVE_STATE_MOVIES);
            recyclerViewMovies.getLayoutManager().onRestoreInstanceState(mRecyclerStateMovies);
        }

        return view;
    }

    public static InfoFragment newInstance(int id){
        InfoFragment infoFragment = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG,id);
        infoFragment.setArguments(bundle);
        return infoFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerStateMovies = recyclerViewMovies.getLayoutManager().onSaveInstanceState();
        mRecyclerStateTrailor = recyclerViewTrailer.getLayoutManager().onSaveInstanceState();
        outState.putInt(SAVE_ID,id);
        outState.putString(SAVE_RATING,textViewRating.getText().toString());
        outState.putString(SAVE_OVERVIEW,textViewOverview.getText().toString());
        outState.putString(SAVE_RELEASE_DATE,textViewReleaseDate.getText().toString());
        outState.putString(SAVE_PRODUCED,textViewProducedBy.getText().toString());
        outState.putString(SAVE_BUDGET,textViewBudget.getText().toString());
        outState.putString(SAVE_REVENUE,textViewRevenue.getText().toString());
        outState.putParcelableArrayList(SAVE_RECYCLER_TRAILOR, (ArrayList<? extends Parcelable>) movieVideoAdapter.getVideoList());
        outState.putParcelableArrayList(SAVE_RECYCLER_MOVIES, (ArrayList<? extends Parcelable>) similarMovieAdapter.getMovieList());
        outState.putParcelable(SAVE_STATE_TRAILOR,mRecyclerStateTrailor);
        outState.putParcelable(SAVE_STATE_MOVIES,mRecyclerStateMovies);
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

    private void getMovieInfo(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getMovieDetail(id,API_KEY);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    MovieDetailObject movieDetailObject = new MovieDetailObject();
                    movieDetailObject.setAdult(jsonObject.getBoolean("adult"));
                    movieDetailObject.setBackdropPath(jsonObject.getString("backdrop_path"));
                    movieDetailObject.setBudget(jsonObject.getInt("budget"));
                    movieDetailObject.setId(jsonObject.getInt("id"));
                    movieDetailObject.setImdb_id(jsonObject.getString("imdb_id"));
                    movieDetailObject.setOriginalTitle(jsonObject.getString("original_title"));
                    movieDetailObject.setOverview(jsonObject.getString("overview"));
                    movieDetailObject.setPosterPath(jsonObject.getString("poster_path"));
                    movieDetailObject.setReleaseDate(jsonObject.getString("release_date"));
                    movieDetailObject.setRevenue(jsonObject.getInt("revenue"));
                    movieDetailObject.setRuntime(jsonObject.getInt("runtime"));
                    movieDetailObject.setTagline(jsonObject.getString("tagline"));
                    movieDetailObject.setTitle(jsonObject.getString("title"));
                    movieDetailObject.setVoteAverage(jsonObject.getDouble("vote_average"));

                    JSONArray jsonArray = jsonObject.getJSONArray("genres");
                    StringBuilder genres = new StringBuilder();
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        int id = jsonObject1.getInt("id");
                        String name = jsonObject1.getString("name");
                        genres.append(name).append(", ");
                    }
                    genres.deleteCharAt(genres.length()-2);

                    movieDetailObject.setGenresName(genres.toString());

                    JSONArray jsonArray1 = jsonObject.getJSONArray("production_companies");
                    StringBuilder company=new StringBuilder();
                    for (int i=0;i<jsonArray1.length();i++){
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        int id = jsonObject1.getInt("id");
                        String name = jsonObject1.getString("name");
                        company.append(name).append(", ");
                    }
                    company.deleteCharAt(company.length()-2);
                    textViewProducedBy.setText(company.toString());
                    String[] date = movieDetailObject.getReleaseDate().split("-");
                    int hour = movieDetailObject.getRuntime()/60;
                    int minute = movieDetailObject.getRuntime()%60;

                    try {
                        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(movieDetailObject.getReleaseDate());
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(d);
                        textViewReleaseDate.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+calendar.get(Calendar.DAY_OF_MONTH)+", "+calendar.get(Calendar.YEAR));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    textViewRating.setText(String.valueOf(movieDetailObject.getVoteAverage()));
                    textViewOverview.setText(movieDetailObject.getOverview());
                    textViewBudget.setText("$ "+String.valueOf(movieDetailObject.getBudget()));
                    textViewRevenue.setText("$ "+String.valueOf(movieDetailObject.getRevenue()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MovieDetail",t.toString());
            }
        });
    }

    private void getMovieTrailer(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VideoResponse> call = apiInterface.getMovieTrailer(id,API_KEY);

        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.body().getVideoList()!=null && response.body().getVideoList().size()!=0){
                    showResult();
                    movieVideoAdapter.setVideoList(response.body().getVideoList());
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

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
        recyclerViewMovies.setVisibility(View.GONE);
        noResultSimilar.setVisibility(View.VISIBLE);
    }

    private void showResultSimilar(){
        recyclerViewMovies.setVisibility(View.VISIBLE);
        noResultSimilar.setVisibility(View.GONE);
    }

    private void getSimilarMovie(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiInterface.getSimilarMovie(id,API_KEY,1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieObject> list = response.body().getMovieList();
                if (list.size()!=0){
                    showResultSimilar();
                    similarMovieAdapter.setMovieList(list);
                }else {
                    showErrorSimilar();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                showErrorSimilar();
            }
        });
    }

    @Override
    public void onItemClick(int id, ImageView sharedView,String imageURL) {
        Bundle bundle = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedView.setTransitionName(getString(R.string.similar_transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),sharedView,sharedView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(ACTIVITY_NAME,TAG);
        startActivity(intent,bundle);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), SimilarMovieActivity.class);
        intent.putExtra("Id",id);
        startActivity(intent);
    }
}

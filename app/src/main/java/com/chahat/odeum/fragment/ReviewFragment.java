package com.chahat.odeum.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chahat.odeum.R;
import com.chahat.odeum.adapter.MovieReviewAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.MovieReviewObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class ReviewFragment extends Fragment {

    private static final String TAG = "ReviewFragment";
    private int id;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.noResult) LinearLayout noResultLayout;
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
        Call<ResponseBody> call = apiInterface.getMovieReview(id,API_KEY);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    Log.d(TAG,res);
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    if (jsonArray.length()!=0){
                        showResult();
                        List<MovieReviewObject> movieReviewList = new ArrayList<MovieReviewObject>();
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            MovieReviewObject movieReviewObject = new MovieReviewObject();
                            movieReviewObject.setId(jsonObject1.getString("id"));
                            movieReviewObject.setAuthor(jsonObject1.getString("author"));
                            movieReviewObject.setContent(jsonObject1.getString("content"));
                            movieReviewObject.setUrl(jsonObject1.getString("url"));

                            movieReviewList.add(movieReviewObject);
                        }
                        reviewAdapter.setReviewList(movieReviewList);
                    }else {
                        showError();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG,e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showError();
                Log.d(TAG,t.toString());
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

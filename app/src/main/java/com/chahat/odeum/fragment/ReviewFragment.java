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
    private MovieReviewAdapter reviewAdapter;

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

        getMovieReview(id);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getArguments().getInt(TAG);
    }

    private void getMovieReview(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getMovieReview(id,API_KEY);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

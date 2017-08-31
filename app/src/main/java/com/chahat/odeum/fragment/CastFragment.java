package com.chahat.odeum.fragment;

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
import android.widget.LinearLayout;

import com.chahat.odeum.R;
import com.chahat.odeum.adapter.MovieCastAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.MovieCastObject;

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

public class CastFragment extends Fragment {

    private static final String TAG = "CastFragment";
    private int id;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.noResult) LinearLayout noResultLayout;
    private MovieCastAdapter movieCastAdapter;
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
        movieCastAdapter = new MovieCastAdapter(getContext());
        recyclerView.setAdapter(movieCastAdapter);

        if (savedInstanceState==null){
            getMovieCast(id);
        }else {
            movieCastAdapter.setMovieCastList((ArrayList)savedInstanceState.getParcelableArrayList(SAVE_LIST));
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
        outState.putParcelableArrayList(SAVE_LIST, (ArrayList<? extends Parcelable>) movieCastAdapter.getMovieCastList());
        outState.putParcelable(SAVE_RECYCLER_STATE,mRecyclerState);
    }

    private void getMovieCast(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getMovieCast(id,API_KEY);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("cast");

                    if (jsonArray.length()!=0){
                        showResult();
                        List<MovieCastObject> castList = new ArrayList<MovieCastObject>();

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            MovieCastObject movieCastObject = new MovieCastObject();
                            movieCastObject.setId(jsonObject1.getInt("id"));
                            movieCastObject.setCastId(jsonObject1.getInt("cast_id"));
                            movieCastObject.setName(jsonObject1.getString("name"));
                            movieCastObject.setCharacter(jsonObject1.getString("character"));
                            movieCastObject.setCreditId(jsonObject1.getString("credit_id"));
                            movieCastObject.setProfile(jsonObject1.getString("profile_path"));

                            castList.add(movieCastObject);
                        }
                        movieCastAdapter.setMovieCastList(castList);
                    }else {
                        showError();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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
}

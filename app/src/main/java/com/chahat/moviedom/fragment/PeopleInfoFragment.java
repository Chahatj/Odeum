package com.chahat.moviedom.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chahat.moviedom.R;
import com.chahat.moviedom.adapter.PeopleImageAdapter;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.api.ApiInterface;
import com.chahat.moviedom.object.ImagesObject;
import com.chahat.moviedom.object.PeopleDetailResponse;
import com.chahat.moviedom.object.ProfileImageResponse;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.moviedom.BuildConfig.API_KEY;

/**
 * Created by chahat on 1/9/17.
 */

public class PeopleInfoFragment extends Fragment {

    private int id;
    private static final String INSTANCE_ID = "id";
    @BindView(R.id.textViewOverview) TextView textViewOverview;
    @BindView(R.id.textViewBorn) TextView textViewBorn;
    @BindView(R.id.textViewBirthplace) TextView textViewBirthplace;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private PeopleImageAdapter imageAdapter;
    private static final String SAVE_BIO = "biography";
    private static final String SAVE_ID = "id";
    private static final String SAVE_BORN = "born";
    private static final String SAVE_BIRTHPLACE = "birthplace";
    private static final String SAVE_LIST = "list";
    private static final String SAVE_RECYCLER_STATE = "recyclerState";
    private Parcelable mRecyclerState;

    public static PeopleInfoFragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        PeopleInfoFragment peopleInfoFragment = new PeopleInfoFragment();
        peopleInfoFragment.setArguments(bundle);
        return peopleInfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            id = getArguments().getInt(INSTANCE_ID);
        }else {
            id = savedInstanceState.getInt(SAVE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_people_info,container,false);
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new PeopleImageAdapter(getContext());
        recyclerView.setAdapter(imageAdapter);

        if (savedInstanceState==null){
            getPeopleInfo(id);
            getPeopleImages(id);
        }else {
            textViewOverview.setText(savedInstanceState.getString(SAVE_BIO));
            textViewBorn.setText(savedInstanceState.getString(SAVE_BORN));
            textViewBirthplace.setText(savedInstanceState.getString(SAVE_BIRTHPLACE));
            imageAdapter.setImagesList((ArrayList)savedInstanceState.getParcelableArrayList(SAVE_LIST));
            mRecyclerState = savedInstanceState.getParcelable(SAVE_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    private void getPeopleInfo(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PeopleDetailResponse> call = apiInterface.getpeopleDetail(id,API_KEY);
        call.enqueue(new Callback<PeopleDetailResponse>() {
            @Override
            public void onResponse(Call<PeopleDetailResponse> call, Response<PeopleDetailResponse> response) {
                PeopleDetailResponse detailResponse = response.body();
                textViewOverview.setText(detailResponse.getBiography());
                textViewBirthplace.setText(detailResponse.getBirthPlace());

                if (detailResponse.getBirthday()!=null){
                    try {
                        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(detailResponse.getBirthday());
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(d);
                        textViewBorn.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH )+" "+calendar.get(Calendar.DAY_OF_MONTH)+", "+calendar.get(Calendar.YEAR));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<PeopleDetailResponse> call, Throwable t) {

            }
        });
    }

    private void getPeopleImages(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileImageResponse> call = apiInterface.getPeopleImages(id,API_KEY);
        call.enqueue(new Callback<ProfileImageResponse>() {
            @Override
            public void onResponse(Call<ProfileImageResponse> call, Response<ProfileImageResponse> response) {
                List<ImagesObject> list = response.body().getImageList();
                imageAdapter.setImagesList(list);
            }

            @Override
            public void onFailure(Call<ProfileImageResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_ID,id);
        outState.putString(SAVE_BIO,textViewOverview.getText().toString());
        outState.putString(SAVE_BORN,textViewBorn.getText().toString());
        outState.putString(SAVE_BIRTHPLACE,textViewBirthplace.getText().toString());
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVE_RECYCLER_STATE,mRecyclerState);
        outState.putParcelableArrayList(SAVE_LIST, (ArrayList<? extends Parcelable>) imageAdapter.getImagesList());
    }
}

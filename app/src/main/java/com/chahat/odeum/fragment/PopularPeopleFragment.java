package com.chahat.odeum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chahat.odeum.Interface.LoadPagesInterface;
import com.chahat.odeum.R;
import com.chahat.odeum.adapter.PopularPeopleAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.PeopleObject;
import com.chahat.odeum.object.PeopleResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.odeum.BuildConfig.API_KEY;

/**
 * Created by chahat on 31/8/17.
 */

public class PopularPeopleFragment extends Fragment implements LoadPagesInterface{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    PopularPeopleAdapter popularPeopleAdapter;

    public static PopularPeopleFragment newInstance(){
        return new PopularPeopleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_popular_people,container,false);
        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        popularPeopleAdapter = new PopularPeopleAdapter(getContext(),this);
        recyclerView.setAdapter(popularPeopleAdapter);

        if (savedInstanceState==null){
            getPopularPeople(1);
        }

        return view;
    }

    private void getPopularPeople(final int page){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PeopleResponse> call = apiInterface.getPopularPeople(API_KEY,page);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                List<PeopleObject> list = response.body().getResult();
                if (page == 1){
                    popularPeopleAdapter.setPeopleList(list);
                    popularPeopleAdapter.setCurrentPage(1);
                }else if (page<=response.body().getTotalPages()){
                    popularPeopleAdapter.addPeopleList(list);
                }
                popularPeopleAdapter.setTotalPages(response.body().getTotalPages());
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadPage(int page) {
        getPopularPeople(page);
    }
}

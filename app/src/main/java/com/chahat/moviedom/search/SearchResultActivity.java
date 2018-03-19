package com.chahat.moviedom.search;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chahat.moviedom.Interface.LoadPagesInterface;
import com.chahat.moviedom.Interface.SharedItemClickListner;
import com.chahat.moviedom.R;
import com.chahat.moviedom.activity.MainActivity;
import com.chahat.moviedom.activity.MovieDetailActivity;
import com.chahat.moviedom.activity.PeopleDetailActivity;
import com.chahat.moviedom.activity.TvShowDetailActivity;
import com.chahat.moviedom.adapter.MovieAdapter;
import com.chahat.moviedom.adapter.PopularPeopleAdapter;
import com.chahat.moviedom.adapter.TvShowAdapter;
import com.chahat.moviedom.api.ApiClient;
import com.chahat.moviedom.api.ApiInterface;
import com.chahat.moviedom.fragment.AiringTodayFragment;
import com.chahat.moviedom.fragment.NowplayingFragment;
import com.chahat.moviedom.fragment.PopularPeopleFragment;
import com.chahat.moviedom.object.MovieObject;
import com.chahat.moviedom.object.MovieResponse;
import com.chahat.moviedom.object.PeopleObject;
import com.chahat.moviedom.object.PeopleResponse;
import com.chahat.moviedom.object.TvShowObject;
import com.chahat.moviedom.object.TvShowResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.moviedom.BuildConfig.API_KEY;
import static com.chahat.moviedom.fragment.NowplayingFragment.ACTIVITY_NAME;
import static com.chahat.moviedom.fragment.PopularPeopleFragment.INTENT_ID;
import static com.chahat.moviedom.fragment.PopularPeopleFragment.INTENT_IMAGE;
import static com.chahat.moviedom.utils.Constants.INTENT_ACTIVITY;

public class SearchResultActivity extends AppCompatActivity implements LoadPagesInterface, SharedItemClickListner, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view_result) RecyclerView recyclerViewResult;
    @BindView(R.id.empty_view_search) LinearLayout emptyView;
    @BindView(R.id.text_view_went_wrong) TextView textViewWentWrong;
    @BindView(R.id.refresh) SwipeRefreshLayout swipeRefreshLayout;

    private MovieAdapter movieAdapter;
    private TvShowAdapter tvShowAdapter;
    private PopularPeopleAdapter popularPeopleAdapter;
    private String query;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewResult.setLayoutManager(manager);

        int currentFragment = MainActivity.currentFragment;

        if (currentFragment!=-1){
            switch (currentFragment){
                case 0:
                    movieAdapter = new MovieAdapter(this,this,this);
                    recyclerViewResult.setAdapter(movieAdapter);
                    break;
                case 1:
                    tvShowAdapter = new TvShowAdapter(this,this,this);
                    recyclerViewResult.setAdapter(tvShowAdapter);
                    break;
                case 2:
                    popularPeopleAdapter = new PopularPeopleAdapter(this,this,this);
                    recyclerViewResult.setAdapter(popularPeopleAdapter);
                    break;
            }
        }

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            boolean resultFound = false;

            query = intent.getStringExtra(SearchManager.QUERY);
            if (actionBar!=null){
                actionBar.setTitle(query);
            }

            fetchData(1);

            /*ApprovedLoanResponse approvedLoanResponse = dataManager.getApprovedLoanResponse();

            if (approvedLoanResponse==null || approvedLoanResponse.getData()==null || approvedLoanResponse.getData().size()<0){
                emptyView.setVisibility(View.VISIBLE);
                TextView textView = findViewById(R.id.text_view_went_wrong);
                textView.setText(getString(R.string.no_result_found));
                return;
            }
            List<ApprovedLoanObject> list = approvedLoanResponse.getData();

            for (ApprovedLoanObject object : list){

                if (object.getLoan_request().getBorrower().getProfile().getFull_name().toLowerCase().contains(query)
                        || object.getLoan_request().getBorrower().getProfile().getMobile_number().toLowerCase().contains(query)){
                    searchAdapter.addApprovedLoanObject(object);
                    resultFound = true;
                }
            }
*/
            /*if (!resultFound){
                emptyView.setVisibility(View.VISIBLE);
                TextView textView = (TextView) findViewById(R.id.text_view_went_wrong);
                textView.setText(getString(R.string.no_result_found));
            }else {
                emptyView.setVisibility(View.GONE);
            }*/
        }
    }

    private void fetchData(final int page){
        emptyView.setVisibility(View.GONE);
        textViewWentWrong.setText(getString(R.string.oops_something_went_wrong));
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        int currentFragment = MainActivity.currentFragment;

        switch (currentFragment){
            case 0:
                fetchMovieData(page,apiInterface);
                break;
            case 1:
                fetchTvShowData(page,apiInterface);
                break;
            case 2:
                fetchPopularPeopleData(page,apiInterface);
                break;
        }

    }

    private void fetchMovieData(final int page, ApiInterface apiInterface){
        Call<MovieResponse> call = apiInterface.searchMovies(API_KEY,query,page,"US");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieObject> movies = response.body().getMovieList();
                if (movies!=null && !movies.isEmpty()){
                    if (page==1){
                        movieAdapter.setMovieList(movies);
                        movieAdapter.setCurrentPage(1);
                    }else if (page<=response.body().getTotalPages()){
                        movieAdapter.addMovieList(movies);
                    }
                    movieAdapter.setTotalPages(response.body().getTotalPages());
                }else {
                    emptyView.setVisibility(View.VISIBLE);
                    textViewWentWrong.setText("No Result Found");
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                emptyView.setVisibility(View.VISIBLE);
                textViewWentWrong.setText(getString(R.string.oops_something_went_wrong));
            }
        });
    }

    private void fetchTvShowData(final int page, ApiInterface apiInterface){
        Call<TvShowResponse> call = apiInterface.searchTvShow(API_KEY,query,page);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                List<TvShowObject> list = response.body().getResults();
                if (list!=null && !list.isEmpty()){
                    if (page==1){
                        tvShowAdapter.setTvShowList(list);
                        tvShowAdapter.setCurrentPage(1);
                    }else if (page<=response.body().getTotalPages()){
                        tvShowAdapter.addTvShowList(list);
                    }
                    tvShowAdapter.setTotalPages(response.body().getTotalPages());
                }else {
                    emptyView.setVisibility(View.VISIBLE);
                    textViewWentWrong.setText("No Result Found");
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                emptyView.setVisibility(View.VISIBLE);
                textViewWentWrong.setText(getString(R.string.oops_something_went_wrong));
            }
        });
    }

    private void fetchPopularPeopleData(final int page, ApiInterface apiInterface){
        Call<PeopleResponse> call = apiInterface.searchPopularPeople(API_KEY,query,page,"US");
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                List<PeopleObject> list = response.body().getResult();
                if (list!=null && !list.isEmpty()){
                    if (page == 1){
                        popularPeopleAdapter.setPeopleList(list);
                        popularPeopleAdapter.setCurrentPage(1);
                    }else if (page<=response.body().getTotalPages()){
                        popularPeopleAdapter.addPeopleList(list);
                    }
                    popularPeopleAdapter.setTotalPages(response.body().getTotalPages());
                }else {
                    emptyView.setVisibility(View.VISIBLE);
                    textViewWentWrong.setText("No Result Found");
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                emptyView.setVisibility(View.VISIBLE);
                textViewWentWrong.setText(getString(R.string.oops_something_went_wrong));
            }
        });
    }


    @Override
    public void loadPage(int page) {
        fetchData(page);
    }

    @Override
    public void onItemClick(int id, ImageView imageView, String imageURL) {

        int currentFragment = MainActivity.currentFragment;

        switch (currentFragment){
            case 0:
                openMovieActivity(id,imageView,imageURL);
                break;
            case 1:
                openTvShowActivity(id,imageView,imageURL);
                break;
            case 2:
                openPopularPeopleActivity(id,imageView,imageURL);
                break;
        }
    }

    private void openMovieActivity(int id, ImageView imageView, String imageURL){
        Bundle bundle = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(this,imageView,imageView.getTransitionName()).toBundle();

        }
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(ACTIVITY_NAME, NowplayingFragment.TAG);
        startActivity(intent,bundle);
    }

    private void openTvShowActivity(int id, ImageView imageView, String imageURL){
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(this,imageView,imageView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(this, TvShowDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(INTENT_ACTIVITY, AiringTodayFragment.TAG);
        startActivity(intent,bundle);
    }

    private void openPopularPeopleActivity(int id, ImageView imageView, String imageURL){
        Bundle bundle = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(this,imageView,imageView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(this,PeopleDetailActivity.class);
        intent.putExtra(INTENT_ID,id);
        intent.putExtra(INTENT_IMAGE,imageURL);
        intent.putExtra(INTENT_ACTIVITY, PopularPeopleFragment.TAG);
        startActivity(intent,bundle);
    }

    @Override
    public void onRefresh() {
        fetchData(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

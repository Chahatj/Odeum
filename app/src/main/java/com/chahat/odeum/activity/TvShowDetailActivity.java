package com.chahat.odeum.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.R;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.object.GenresObject;
import com.chahat.odeum.object.TvShowDetailObject;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.odeum.BuildConfig.API_KEY;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private int id;
    private String imageURL;
    private String imageURLBack;
    @BindView(R.id.app_bar_layout) AppBarLayout appBarLayout;
    @BindView(R.id.tv_show_image) ImageView tvShowImage;
    @BindView(R.id.imageViewBack) ImageView imageViewBack;
    @BindView(R.id.tv_date) TextView textViewDate;
    @BindView(R.id.tv_title) TextView textViewTitle;
    @BindView(R.id.tv_genre) TextView textViewGenre;
    @BindView(R.id.tv_show_poster) ImageView imageViewPoster;
    @BindView(R.id.sliding_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);
        ButterKnife.bind(this);
        imageViewBack.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageViewPoster.setTransitionName(getString(R.string.transition_photo));
        }

        if (savedInstanceState==null){
            Intent intent = getIntent();
            if (intent!=null && intent.hasExtra("Id") && intent.hasExtra("ImageURL")){
                id = intent.getIntExtra("Id",0);
                imageURL = intent.getStringExtra("ImageURL");
            }
        }

        Picasso.with(this).load(ApiClient.IMAGE_URL+imageURL).into(imageViewPoster);
        getTvShowDetail(id);
    }

    private void getTvShowDetail(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowDetailObject> call = apiInterface.getTvShowDetail(id,API_KEY);

        call.enqueue(new Callback<TvShowDetailObject>() {
            @Override
            public void onResponse(Call<TvShowDetailObject> call, Response<TvShowDetailObject> response) {
                imageURLBack = response.body().getBackdropPath();
                Picasso.with(getApplicationContext()).load(ApiClient.IMAGE_URL+imageURLBack).into(tvShowImage);
                textViewTitle.setText(response.body().getName());

                List<GenresObject> genresList = response.body().getGenresList();
                StringBuilder stringBuilder = new StringBuilder();
                for (GenresObject genresObject: genresList){
                    stringBuilder.append(genresObject.getName()).append(", ");
                }
                textViewGenre.setText(stringBuilder.deleteCharAt(stringBuilder.length()-2).toString());

                String[] date = response.body().getFirstAirDate().split("-");

                if (response.body().getEpisodeRunTimeList().size()!=0){
                    int hour = response.body().getEpisodeRunTimeList().get(0)/60;
                    int minute = response.body().getEpisodeRunTimeList().get(0)%60;
                    textViewDate.setText(date[0]+" \u25CF "+hour+" hr "+minute+" min");
                }else {
                    textViewDate.setText(date[0]+" \u25CF "+"N/A");
                }

            }

            @Override
            public void onFailure(Call<TvShowDetailObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }
}

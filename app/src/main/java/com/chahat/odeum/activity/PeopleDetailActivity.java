package com.chahat.odeum.activity;

import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chahat.odeum.R;
import com.chahat.odeum.adapter.PeopleDetailViewAdapter;
import com.chahat.odeum.api.ApiClient;
import com.chahat.odeum.api.ApiInterface;
import com.chahat.odeum.fragment.PopularPeopleFragment;
import com.chahat.odeum.object.PeopleDetailResponse;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chahat.odeum.BuildConfig.API_KEY;

public class PeopleDetailActivity extends AppCompatActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    private int id;
    private String imageURL,imageURLBack;
    @BindView(R.id.app_bar_layout) AppBarLayout appBarLayout;
    @BindView(R.id.imageViewBack) ImageView imageViewBack;
    @BindView(R.id.imageViewProfile) ImageView imageViewProfile;
    @BindView(R.id.imageViewBackProfile) ImageView imageViewBackProfile;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.sliding_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;
    private PeopleDetailViewAdapter viewAdapter;
    private CharSequence[] titles = {"INFO","MOVIES","TV SHOWS"};
    private int numOfTabs = 3;
    private static final String SAVE_ID = "id";
    private static final String SAVE_IMAGE = "imageurl";
    private static final String SAVE_IMAGE_BACK = "imageurlback";
    private static final String SAVE_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);
        ButterKnife.bind(this);
        appBarLayout.addOnOffsetChangedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageViewProfile.setTransitionName(getString(R.string.transition_photo));
        }
        imageViewBack.setOnClickListener(this);

        if (savedInstanceState==null){
            Intent intent = getIntent();
            if (intent!=null && intent.hasExtra(PopularPeopleFragment.INTENT_ID) && intent.hasExtra(PopularPeopleFragment.INTENT_IMAGE)){
                id = intent.getIntExtra(PopularPeopleFragment.INTENT_ID,0);
                imageURL = intent.getStringExtra(PopularPeopleFragment.INTENT_IMAGE);
                getPeopleDetail(id);
            }
        }else {
            id = savedInstanceState.getInt(SAVE_ID);
            imageURL = savedInstanceState.getString(SAVE_IMAGE);
            imageURLBack = savedInstanceState.getString(SAVE_IMAGE_BACK);
            tvName.setText(savedInstanceState.getString(SAVE_NAME));
            Picasso.with(PeopleDetailActivity.this).load(ApiClient.IMAGE_URL+imageURLBack).into(imageViewBackProfile);
        }

        Picasso.with(this).load(ApiClient.IMAGE_URL+imageURL).into(imageViewProfile);
        viewAdapter = new PeopleDetailViewAdapter(getSupportFragmentManager(),titles,numOfTabs,id);
        viewPager.setAdapter(viewAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getPeopleDetail(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PeopleDetailResponse> call = apiInterface.getpeopleDetail(id,API_KEY);
        call.enqueue(new Callback<PeopleDetailResponse>() {
            @Override
            public void onResponse(Call<PeopleDetailResponse> call, Response<PeopleDetailResponse> response) {
                PeopleDetailResponse detailResponse = response.body();
                tvName.setText(detailResponse.getName());
                imageURLBack  = detailResponse.getProfilePath();
                Picasso.with(PeopleDetailActivity.this).load(ApiClient.IMAGE_URL+imageURLBack).into(imageViewBackProfile);
            }

            @Override
            public void onFailure(Call<PeopleDetailResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_ID,id);
        outState.putString(SAVE_IMAGE,imageURL);
        outState.putString(SAVE_IMAGE_BACK,imageURLBack);
        outState.putString(SAVE_NAME,tvName.getText().toString());
    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset > -800)
        {
            tabLayout.setPadding(0,0,0,0);
        }
        else
        {
            if (Build.VERSION.SDK_INT >= 21) {

                // Set the status bar to dark-semi-transparentish
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                // Set paddingTop of toolbar to height of status bar.
                // Fixes statusbar covers toolbar issue
                tabLayout.setPadding(0, getStatusBarHeight(), 0, 0);
            }
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

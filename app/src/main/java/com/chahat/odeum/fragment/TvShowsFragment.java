package com.chahat.odeum.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chahat.odeum.R;
import com.chahat.odeum.adapter.TvShowsPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 1/9/17.
 */

public class TvShowsFragment extends Fragment {

    @BindView(R.id.sliding_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;
    private TvShowsPagerAdapter pagerAdapter;
    private final CharSequence[] titles = {"AIRING TODAY","ON THE AIR","POPULAR","TOP RATED"};
    private final int numOfTabs = 4;

    public static TvShowsFragment newInstance(){
        return new TvShowsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_shows,container,false);
        ButterKnife.bind(this,view);

        pagerAdapter = new TvShowsPagerAdapter(getChildFragmentManager(),titles,numOfTabs);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }

        return view;
    }
}

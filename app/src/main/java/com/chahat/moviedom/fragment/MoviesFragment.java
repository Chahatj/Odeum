package com.chahat.moviedom.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chahat.moviedom.R;
import com.chahat.moviedom.adapter.ViewPagerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 24/8/17.
 */

public class MoviesFragment extends Fragment {

    ViewPager pager;
    ViewPagerAdapter adapter;
    TabLayout tabs;
    CharSequence Titles[]={"NOW PLAYING","UPCOMING","POPULAR","TOP RATED"};
    int Numboftabs = 4;
    @BindView(R.id.adView) AdView adView;

    public static MoviesFragment newInstance(){
        return new MoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies,container,false);
        ButterKnife.bind(this,view);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        adapter =  new ViewPagerAdapter(getChildFragmentManager(),Titles,Numboftabs);

        pager = (ViewPager) view.findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
        tabs = (TabLayout) view.findViewById(R.id.sliding_layout);
        tabs.setupWithViewPager(pager);

        if (getActivity().getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else {
            tabs.setTabMode(TabLayout.MODE_FIXED);
        }

        return view;
    }

}

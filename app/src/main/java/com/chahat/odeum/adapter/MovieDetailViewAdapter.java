package com.chahat.odeum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chahat.odeum.fragment.CastFragment;
import com.chahat.odeum.fragment.InfoFragment;
import com.chahat.odeum.fragment.ReviewFragment;

/**
 * Created by chahat on 25/8/17.
 */

public class MovieDetailViewAdapter extends FragmentStatePagerAdapter {

    private CharSequence[] titles;
    private int numOfTabs;

    public MovieDetailViewAdapter(FragmentManager fm,CharSequence[] titles,int numOfTabs){
        super(fm);
        this.titles = titles;
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new InfoFragment();
        }else if (position==1){
            return new CastFragment();
        }else {
            return new ReviewFragment();
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

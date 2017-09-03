package com.chahat.moviedom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chahat.moviedom.fragment.CastFragment;
import com.chahat.moviedom.fragment.InfoFragment;
import com.chahat.moviedom.fragment.ReviewFragment;

/**
 * Created by chahat on 25/8/17.
 */

public class MovieDetailViewAdapter extends FragmentStatePagerAdapter {

    private CharSequence[] titles;
    private int numOfTabs;
    private int id;

    public MovieDetailViewAdapter(FragmentManager fm,CharSequence[] titles,int numOfTabs,int id){
        super(fm);
        this.titles = titles;
        this.numOfTabs = numOfTabs;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return InfoFragment.newInstance(id);
        }else if (position==1){
            return CastFragment.newInstance(id);
        }else {
            return ReviewFragment.newInstance(id);
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

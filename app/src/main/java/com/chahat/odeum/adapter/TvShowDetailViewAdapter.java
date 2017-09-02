package com.chahat.odeum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chahat.odeum.fragment.TvShowActorsFragment;
import com.chahat.odeum.fragment.TvShowInfoFragment;
import com.chahat.odeum.fragment.TvShowSeasonFragment;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowDetailViewAdapter extends FragmentStatePagerAdapter {

    private CharSequence[] titles;
    private int numOfTabs;
    private int id;

    public TvShowDetailViewAdapter(FragmentManager fm,CharSequence[] titles,int numOfTabs,int id) {
        super(fm);
        this.titles = titles;
        this.numOfTabs = numOfTabs;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return TvShowInfoFragment.newInstance(id);
        }else if (position==1){
            return TvShowActorsFragment.newInstance(id);
        }else {
            return TvShowSeasonFragment.newInstance(id);
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

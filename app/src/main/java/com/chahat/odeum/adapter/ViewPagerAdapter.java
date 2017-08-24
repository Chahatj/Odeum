package com.chahat.odeum.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chahat.odeum.fragment.NowplayingFragment;
import com.chahat.odeum.fragment.PopularFrament;
import com.chahat.odeum.fragment.TopratedFragment;
import com.chahat.odeum.fragment.UpcomingFragment;

/**
 * Created by chahat on 5/12/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    private int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            NowplayingFragment tab1 = new NowplayingFragment();
            return tab1;
        }
        else if (position == 1)       // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            UpcomingFragment tab2 = new UpcomingFragment();
            return tab2;
        }else if (position == 2){
            PopularFrament tab3 = new PopularFrament();
            return tab3;
        }else {
            TopratedFragment tab4 = new TopratedFragment();
            return tab4;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}

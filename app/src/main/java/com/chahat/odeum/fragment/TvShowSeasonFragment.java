package com.chahat.odeum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chahat.odeum.R;

import static com.chahat.odeum.utils.Constants.INSTANCE_ID;

/**
 * Created by chahat on 2/9/17.
 */

public class TvShowSeasonFragment extends Fragment {

    private int id;

    public static TvShowSeasonFragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        TvShowSeasonFragment tvShowSeasonFragment = new TvShowSeasonFragment();
        tvShowSeasonFragment.setArguments(bundle);
        return tvShowSeasonFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            id = getArguments().getInt(INSTANCE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show_season,container,false);
        return view;
    }
}

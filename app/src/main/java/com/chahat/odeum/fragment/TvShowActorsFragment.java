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

public class TvShowActorsFragment extends Fragment{

    private int id;

    public static TvShowActorsFragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        TvShowActorsFragment tvShowActorsFragment = new TvShowActorsFragment();
        tvShowActorsFragment.setArguments(bundle);
        return tvShowActorsFragment;
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
        View view = inflater.inflate(R.layout.fragment_tv_show_actor,container,false);
        return view;
    }
}

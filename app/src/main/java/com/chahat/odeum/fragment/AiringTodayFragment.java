package com.chahat.odeum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chahat.odeum.R;

/**
 * Created by chahat on 1/9/17.
 */

public class AiringTodayFragment extends Fragment {

    public static AiringTodayFragment newInstance(){
        return new AiringTodayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airing_today,container,false);
        return view;
    }
}

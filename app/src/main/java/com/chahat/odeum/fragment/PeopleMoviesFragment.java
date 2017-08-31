package com.chahat.odeum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chahat.odeum.Interface.SharedItemClickListner;
import com.chahat.odeum.R;

/**
 * Created by chahat on 1/9/17.
 */

public class PeopleMoviesFragment extends Fragment {

    private int id;
    private static final String INSTANCE_ID = "id";

    public static PeopleMoviesFragment newInstance(int id){
        PeopleMoviesFragment peopleMoviesFragment = new PeopleMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        peopleMoviesFragment.setArguments(bundle);
        return peopleMoviesFragment;
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
        View view = inflater.inflate(R.layout.fragment_people_movies,container,false);
        return view;
    }
}

package com.chahat.odeum.fragment;

import android.content.Intent;
import android.content.IntentSender;
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

public class PeopleInfoFragment extends Fragment {

    private int id;
    private static final String INSTANCE_ID = "id";

    public static PeopleInfoFragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        PeopleInfoFragment peopleInfoFragment = new PeopleInfoFragment();
        peopleInfoFragment.setArguments(bundle);
        return peopleInfoFragment;
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

        View view = inflater.inflate(R.layout.fragment_people_info,container,false);
        return view;
    }
}

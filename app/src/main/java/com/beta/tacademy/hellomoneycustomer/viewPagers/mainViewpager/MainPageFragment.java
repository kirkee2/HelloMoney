package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {
    MainPageViewPagerObject mainPageViewPagerObject;
    TextView region;
    TextView apt;
    TextView type;
    TextView currentQuotation;
    TextView leftTime;

    public MainPageFragment() {
        // Required empty public constructor
    }

    public MainPageFragment(MainPageViewPagerObject mainPageViewPagerObject) {
        this.mainPageViewPagerObject = mainPageViewPagerObject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);


        return view;
    }

}

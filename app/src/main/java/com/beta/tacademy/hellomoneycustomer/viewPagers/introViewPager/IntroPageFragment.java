package com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.MainActivity;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageFragment;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

public class IntroPageFragment extends Fragment {

    private ImageView imageView;
    private int page;

    public IntroPageFragment() {
        this.page = 0;
    }

    public static IntroPageFragment newInstance(int page) {
        IntroPageFragment fragment = new IntroPageFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt("page");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_page, container, false);
        imageView = (ImageView) view.findViewById(R.id.introImage);

        if(page == 0){
            imageView.setBackgroundResource(R.drawable.test);
        }else if(page == 1){
            imageView.setBackgroundResource(R.drawable.test);
        }else if(page == 2){
            imageView.setBackgroundResource(R.drawable.test);
        }else{
            imageView.setBackgroundResource(R.drawable.test);
        }

        return view;
    }
}

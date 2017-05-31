package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroPageFragment;

import java.util.ArrayList;


public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<MainPageViewPagerObject> items;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }


    public void init() {
        for(int i = 0 ; i <3 ; i++){
            items.add(new MainPageViewPagerObject(0,1,3,"12:12:3","경기도","고양시","장항동","현대타운빌","1000평",3));
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return new MainPageFragment(items.get(position));
    }

    @Override
    public int getCount() {
        return items.size();
    }
}

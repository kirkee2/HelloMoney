package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<MainPageViewPagerObject> items;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }

    public void init(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList) {
        items = mainPageViewPagerObjectArrayList;

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return MainPageFragment.newInstance(items.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}

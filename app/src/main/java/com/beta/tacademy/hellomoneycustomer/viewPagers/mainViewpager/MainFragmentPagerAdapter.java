package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;


public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {
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
        return MainPageFragment.newInstance(items.get(position),position);
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

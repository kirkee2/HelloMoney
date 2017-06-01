package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager.IntroPageFragment;

import java.util.ArrayList;


public class MyQuotationFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Integer> items;

    public MyQuotationFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }

    public void init() {
        for(int i = 0 ; i <2 ; i++){
            items.add(i);
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return MyQuotationFragment.newInstance(items.get(position));
    }

    @Override
    public int getCount() {
        return items.size();
    }
}

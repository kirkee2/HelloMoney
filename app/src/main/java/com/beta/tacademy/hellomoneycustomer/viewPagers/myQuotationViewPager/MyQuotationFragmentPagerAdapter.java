package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageFragment;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import java.util.ArrayList;

/**
 * Created by kirkee on 2017. 6. 1..
 */

public class MyQuotationFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<MyQuotationViewPagerObject> items;

    public MyQuotationFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }


    public void init() {
        for(int i = 0 ; i <3 ; i++){

        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}

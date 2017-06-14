package com.beta.tacademy.hellomoneycustomer.viewPagers.introViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by kirkee on 2017. 5. 22..
 */

public class IntroFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Integer> items = new ArrayList<>();

    public IntroFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void init() {
        for(int i = 0 ; i <4 ; i++){
            items.add(i);
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return IntroPageFragment.newInstance(items.get(position));
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

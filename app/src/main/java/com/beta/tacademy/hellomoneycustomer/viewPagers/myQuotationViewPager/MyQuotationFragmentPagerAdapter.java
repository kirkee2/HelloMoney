package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import java.util.ArrayList;


public class MyQuotationFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<MainPageViewPagerObject> itemOne;
    ArrayList<MainPageViewPagerObject> itemTwo;

    public MyQuotationFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        itemOne = new ArrayList<>();
        itemTwo = new ArrayList<>();
    }

    public void initOne(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList){
        itemOne = mainPageViewPagerObjectArrayList;
    }

    public void initTwo(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList){
        itemTwo = mainPageViewPagerObjectArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return MyQuotationFragment.newInstance(itemOne);
        }else{
            return MyQuotationFragment.newInstance(itemTwo);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "진행 중";
        }else{
            return "완료";
        }
    }
}
